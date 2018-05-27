package com.klzan.p2p.task;

import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.util.ScheduleJobParams;
import com.klzan.plugin.pay.IDetailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 提现订单定时查询任务
 */
@Component("withdrawOrderQueryTask")
public class WithdrawOrderQueryTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BusinessService businessService;

	public void orderQuery(ScheduleJobParams params){
		String orderNo = params.getParams();
		logger.info("task order query method is runing, orderNo[{}]", orderNo);
		IDetailResponse response = businessService.query("02", orderNo);
		logger.info(JsonUtils.toJson(response));
	}

}
