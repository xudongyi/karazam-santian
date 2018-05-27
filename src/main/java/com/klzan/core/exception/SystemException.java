package com.klzan.core.exception;

public class SystemException extends CommonException {

	private static final long serialVersionUID = -5853334811483409498L;

	public SystemException() {
		super();
		
	}

	public SystemException(String errorCode, Object... messageArguments) {
		super(errorCode, messageArguments);
		
	}

	public SystemException(String errorCode, String message,
                           Object... messageArguments) {
		super(errorCode, message, messageArguments);
		
	}

	public SystemException(String errorCode, String message, Throwable cause,
                           Object... messageArguments) {
		super(errorCode, message, cause, messageArguments);
		
	}

	public SystemException(String errorCode, Throwable cause,
                           Object... messageArguments) {
		super(errorCode, cause, messageArguments);
		
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public SystemException(String errorMsg) {
		super(errorMsg);
		
	}

}
