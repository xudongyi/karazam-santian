package com.klzan.plugin.message.sms.cloudc253.chuanglan.sms.response;

import java.util.List;

/**
 * @author tianyh 
 * @date 2017年4月15日 下午3:26:51
 * @Title: SmsPullRequest
 * @ClassName: SmsPullRequest
 * @Description:上行明细响应实体类
 */
public class SmsPullRespnse {
	/**
	 * 请求状态
	 */
	private String ret;
	/**
	 * 请求错误描述
	 */
	private String error;
	/**
	 * 上行明细结果
	 */
	private List<Result> result;
	
 
	public String getRet() {
		return ret;
	}
	
	public void setRet(String ret) {
		this.ret = ret;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public List<Result> getResult() {
		return result;
	}
	
	public void setResult(List<Result> result) {
		this.result = result;
	}
	static class Result{
		/**
		 * 上行时间
		 */
		private String moTime;
		/**
		 * 平台通道码
		 */
		private String spCode;
		/**
		 * 上行号码
		 */
		private String mobile;
		/**
		 * 运营商通道码
		 */
		private String descCode;
		/**
		 * 上行内容
		 */
		private String messageContent;
		public String getMoTime() {
			return moTime;
		}
		public void setMoTime(String moTime) {
			this.moTime = moTime;
		}
		public String getSpCode() {
			return spCode;
		}
		public void setSpCode(String spCode) {
			this.spCode = spCode;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getDescCode() {
			return descCode;
		}
		public void setDescCode(String descCode) {
			this.descCode = descCode;
		}
		public String getMessageContent() {
			return messageContent;
		}
		public void setMessageContent(String messageContent) {
			this.messageContent = messageContent;
		}
		
		
}

	
}
