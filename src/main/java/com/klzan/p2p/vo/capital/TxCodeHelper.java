package com.klzan.p2p.vo.capital;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by suhao Date: 2017/3/3 Time: 9:51
 *
 * @version: 1.0
 */
public class TxCodeHelper {
    public final static Map<String, String> map = new HashedMap();

    static {
        map.put("1111", "商户订单支付(直通车)");
        map.put("1112", "商户订单支付(非直通车)");
        map.put("1118", "商户订单支付状态变更通知");
        map.put("1120", "商户订单支付交易查询");
        map.put("1131", "商户订单支付退款");
        map.put("1132", "商户订单支付退款查询");
        map.put("1133", "商户订单支付原路退款");

        map.put("2531", "建立绑定关系(发送验证短信)");
        map.put("2532", "建立绑定关系(验证并绑定)");
        map.put("2502", "查询绑定关系");
        map.put("2503", "解除绑定关系");
        map.put("2541", "快捷支付(发送验证短信)");
        map.put("2542", "快捷支付(验证并支付)");
        map.put("2512", "快捷支付查询");
        map.put("2521", "快捷支付退款");
        map.put("2522", "快捷支付退款查询");

        map.put("1361", "市场订单单笔代收");
        map.put("1362", "市场订单单笔代收交易查询");
        map.put("1363", "市场订单单笔代收结果通知");
        map.put("1330", "市场订单查询");
        map.put("1341", "市场订单结算");
        map.put("1348", "市场订单结算状态变更通知");
        map.put("1350", "市场订单结算查询");

        map.put("1510", "批量代付");
        map.put("1520", "批量代付查询");
        map.put("1550", "批量代付交易对账");

        map.put("4011", "机构支付账户充值");
        map.put("4510", "机构支付账户余额查询");
        map.put("4512", "机构支付账户交易明细查询");
        map.put("4520", "机构支付账户网银充值");
        map.put("4522", "机构支付账户网银充值成功通知");
        map.put("4524", "机构支付账户网银充值查询");

        map.put("2310", "账户验证(三要素)");
        map.put("1810", "下载交易对账单");
    }

    public static String convert(String txCode) {
        if (map.containsKey(txCode)) {
            return txCode + "-" + map.get(txCode);
        }
        return txCode;
    }

}
