package com.klzan.core.exception;

public class PayException extends CommonException {

	private static final long serialVersionUID = -7932081571645903812L;

	public PayException() {
		super();
	}

	public PayException(String errorMsg) {
		super(errorMsg);
	}

	public PayException(String errorCode, Object... messageArguments) {
		super(errorCode, messageArguments);
	}

	public PayException(String errorCode, String message, Object... messageArguments) {
		super(errorCode, message, messageArguments);
	}

	public PayException(String errorCode, String message, Throwable cause, Object... messageArguments) {
		super(errorCode, message, cause, messageArguments);
	}

	public PayException(String errorCode, Throwable cause, Object... messageArguments) {
		super(errorCode, cause, messageArguments);
	}

	public PayException(String message, Throwable cause) {
		super(message, cause);
	}

	public PayException(Throwable cause) {
		super(cause);
	}


}
