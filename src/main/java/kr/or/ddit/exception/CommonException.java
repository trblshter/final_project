package kr.or.ddit.exception;

/**
 * 범용적으로 사용할 unchecked exception
 *
 */
public class CommonException extends RuntimeException{

	public CommonException() {
		super();
	}

	public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}
	
}
