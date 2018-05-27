package com.klzan.p2p.util;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.SpringUtils;
import com.klzan.core.util.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 执行定时任务
 */
public class ScheduleRunnable implements Runnable {
	private Object target;
	private Method method;
	private ScheduleJobParams params;

	public ScheduleRunnable(String beanName, String methodName, ScheduleJobParams params) throws NoSuchMethodException, SecurityException {
		this.target = SpringUtils.getBean(beanName);
		this.params = params;

		if(null != params){
			this.method = target.getClass().getDeclaredMethod(methodName, ScheduleJobParams.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if(null != params){
				method.invoke(target, params);
			}else{
				method.invoke(target);
			}
		}catch (Exception e) {
			throw new BusinessProcessException("执行定时任务失败", e);
		}
	}

}
