package com.klzan.plugin.repayalgorithm;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.model.RepaymentRecord;
import org.apache.commons.collections.map.HashedMap;

import java.math.BigDecimal;
import java.util.*;

public class RepayRecords {
    private List<RepaymentRecord> repayRecords = new ArrayList<>();
    private Map<Investment, List<RepaymentRecord>> recordsMap = new LinkedHashMap<>();

    public List<RepaymentRecord> getRepaymentPlans() {
        return repayRecords;
    }

    /**
     * 生成还款和计划
     * @param borrowing
     * @param borrower
     * @return
     */
    public Repays convertRepayment(Integer borrowing, Integer borrower) {
        Repays repays = new Repays();
        List<Repayment> repayments = new ArrayList<>();
        Map<Integer, Repayment> repaymentMap = new HashedMap();
        List<RepaymentPlan> repaymentPlanList = new ArrayList<>();
        for (Map.Entry<Investment, List<RepaymentRecord>> recordsEntry : recordsMap.entrySet()) {
            Investment investment = recordsEntry.getKey();
            List<RepaymentPlan> repaymentPlen = convertRepaymentPlan(investment);
            repaymentPlanList.addAll(repaymentPlen);
            List<RepaymentRecord> records = recordsEntry.getValue();
            for (RepaymentRecord record : records) {
                Integer period = record.getPeriod();
                if (repaymentMap.containsKey(period)) {
                    Repayment repayment = repaymentMap.get(period);
                    BigDecimal capital = formatAmount(repayment.getCapital().add(record.getCapital()));
                    BigDecimal interest =  formatAmount(repayment.getInterest().add(record.getInterest()));
                    repayment.setCapital(capital);
                    repayment.setInterest(interest);
                    repaymentMap.put(period, repayment);
                } else {
                    Repayment repayment = new Repayment();
                    BigDecimal capital = formatAmount(repayment.getCapital().add(record.getCapital()));
                    BigDecimal interest =  formatAmount(repayment.getInterest().add(record.getInterest()));
                    repayment.setCapital(capital);
                    repayment.setInterest(interest);
                    repayment.setPeriod(period);
                    repayment.setPayDate(record.getPayDate());
                    repayment.setNextPayDate(record.getNextPayDate());
                    repayment.setBorrowing(borrowing);
                    repayment.setBorrower(borrower);
                    repayment.setOrderNo(SnUtils.getOrderNo(borrower, 15, 30));
                    repaymentMap.put(period, repayment);
                }
            }
        }

        for (Map.Entry<Integer, Repayment> repaymentEntry : repaymentMap.entrySet()) {
            repayments.add(repaymentEntry.getValue());
        }

        /**
         * 按照期数排序
         */
        Collections.sort(repayments,new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                Repayment repayment1 = (Repayment) o1;
                Repayment repayment2 = (Repayment) o2;
                return repayment1.getPeriod().compareTo(repayment2.getPeriod());
            }
        });

        repays.addRepayment(repayments);
        repays.addRepaymentPlan(repaymentPlanList);

        return repays;
    }

    public RepaymentRecord getRepaymentPlanByPeriod(Integer period) {
        for (RepaymentRecord repayRecord : repayRecords) {
            if (repayRecord.isCurrentPeriod(period)) {
                return repayRecord;
            }
        }
        return null;
    }

    public void addRecordsByInvestment(Investment investment, List<RepaymentRecord> repaymentPlans) {
        recordsMap.put(investment, repaymentPlans);
    }

    protected void addRecord(RepaymentRecord repayRecord) {
        repayRecords.add(repayRecord);
    }

    /**
     * 获取下一个还款日期
     */
    protected void fillNextDueDate() {
        int size = repayRecords.size();
        for (int i = 0; i < size; i++) {
            RepaymentRecord repayRecord = repayRecords.get(i);
            if (i + 1 < size) {
                RepaymentRecord nextRepayRecord = repayRecords.get(i + 1);
                repayRecord.setNextPayDate(nextRepayRecord.getPayDate());
            }
        }
    }

    /**
     * 获取应还总金额
     *
     * @return
     */
    public RepaymentRecord getTotalPayRecord() {
        RepaymentRecord totalRecord = new RepaymentRecord();
        //应还本金总额
        BigDecimal totalCapital = BigDecimal.ZERO;
        //应还利息总额
        BigDecimal totalprofit = BigDecimal.ZERO;
        for (RepaymentRecord repayRecord : repayRecords) {
            totalCapital = totalCapital.add(formatAmount(repayRecord.getCapital()));
            totalprofit = totalprofit.add(formatAmount(repayRecord.getInterest()));
        }
        totalRecord.setCapital(totalCapital);
        totalRecord.setInterest(totalprofit);
        return totalRecord;
    }

    /**
     * 还款计划
     *
     * @param investment
     * @return
     */
    private List<RepaymentPlan> convertRepaymentPlan(Investment investment) {
        repayRecords = recordsMap.get(investment);
        List<RepaymentPlan> repaymentPlans = new ArrayList<>();
        RepaymentRecord totalReocrd = getTotalPayRecord();
        BigDecimal totalCapital = totalReocrd.getCapital();
        BigDecimal totalProfit = totalReocrd.getInterest();
        BigDecimal sumCapital = BigDecimal.ZERO;
        BigDecimal sumProfit = BigDecimal.ZERO;
        for (int i = 0; i < repayRecords.size(); i++) {
            RepaymentRecord repayRecord = repayRecords.get(i);
            RepaymentRecord newRecord = new RepaymentRecord();
            /** 保留两位小数 两位小数后直接舍去 */
            BigDecimal capital = formatAmount(repayRecord.getCapital());
            BigDecimal profit = formatAmount(repayRecord.getInterest());
            sumCapital = sumCapital.add(capital);
            sumProfit = sumProfit.add(profit);
            if (i == repayRecords.size() - 1) {
                if (totalCapital.compareTo(sumCapital) > 0) {
                    capital = formatAmount(capital.add(totalCapital.subtract(sumCapital)));
                }
                if (totalProfit.compareTo(sumProfit) > 0) {
                    profit = formatAmount(profit.add(totalProfit.subtract(sumProfit)));
                }
            }
            if (investment.getExperience()) {
                /** 如果用的是体验金，回款的时候不返还本金（0元） */
                newRecord.setCapital(BigDecimal.ZERO);
            } else {
                newRecord.setCapital(capital);
            }
            newRecord.setInterest(profit);
            newRecord.setPeriod(repayRecord.getPeriod());
            newRecord.setPayDate(repayRecord.getPayDate());
            newRecord.setNextPayDate(repayRecord.getNextPayDate());
            RepaymentPlan repaymentPlan = new RepaymentPlan(investment, newRecord);
            repaymentPlans.add(repaymentPlan);
        }
        return repaymentPlans;
    }

    /**
     * 格式化金额
     * @param amount
     * @return
     */
    private BigDecimal formatAmount(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

}
