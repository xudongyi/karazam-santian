package com.klzan.core.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonException extends RuntimeException {
	private static final long serialVersionUID = 2961538593595558005L;

	/**
	 * error code
	 */
	protected String errorCode;

	/**
	 * message arguments
	 */
	protected Object[] messageArguments;

	/**
	 * whether the exception need to be logged.
	 */
	private boolean logEnbaled = true;
	
	public CommonException() {
		super();
	}
	
	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommonException(boolean logEnbaled, String message, Throwable cause) {
		this(message, cause);
		this.logEnbaled = logEnbaled;
	}
	
	public CommonException(String errorMsg) {
		super(errorMsg);
	}

	public CommonException(String errorCode, Object... messageArguments) {
		super();
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
	}
	
	public CommonException(String errorCode, String message, Throwable cause, Object... messageArguments) {
		super(message, cause);
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
	}

	public CommonException(String errorCode, String message, Object... messageArguments) {
		super(message);
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
	}

	public CommonException(String errorCode, Throwable cause, Object... messageArguments) {
		super(cause);
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
	}
	
	
	public CommonException(Throwable cause) {
		super(cause);
	}
	/**
	 * print stacktrace to memory
	 * @param e
	 * @return
	 * @throws IOException
	 */
	public String getStackTraceMessage() throws IOException {
		StringWriter stringWriter = null;
		PrintWriter printWriter = null;
		try {
			stringWriter = new StringWriter();
			printWriter = new PrintWriter(stringWriter);
			this.printStackTrace(printWriter);
			stringWriter.flush();
			return stringWriter.toString();
		} finally {
			if (null != printWriter)
				printWriter.close();
			if (null != stringWriter)
				stringWriter.close();
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object[] getMessageArguments() {
		return messageArguments;
	}
	
	public boolean isLogEnbaled() {
		return this.logEnbaled;
	}
}
