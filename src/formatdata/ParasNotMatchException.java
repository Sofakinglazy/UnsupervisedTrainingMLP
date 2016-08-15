package formatdata;

public class ParasNotMatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParasNotMatchException() {
	}

	public ParasNotMatchException(String message) {
		super(message);
	}

	public ParasNotMatchException(Throwable cause) {
		super(cause);
	}

	public ParasNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParasNotMatchException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
