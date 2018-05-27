package com.klzan.core.exception;

public class BusinessProcessException extends CommonException {

	private static final long serialVersionUID = -7932081571645903812L;
	
	public BusinessProcessException() {
		super();
	}

	public BusinessProcessException(String errorMsg) {
		super(errorMsg);
	}

	public BusinessProcessException(String errorCode, Object... messageArguments) {
		super(errorCode, messageArguments);
	}

	public BusinessProcessException(String errorCode, String message, Object... messageArguments) {
		super(errorCode, message, messageArguments);
	}

	public BusinessProcessException(String errorCode, String message, Throwable cause, Object... messageArguments) {
		super(errorCode, message, cause, messageArguments);
	}

	public BusinessProcessException(String errorCode, Throwable cause, Object... messageArguments) {
		super(errorCode, cause, messageArguments);
	}

	public BusinessProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessProcessException(Throwable cause) {
		super(cause);
	}


}
