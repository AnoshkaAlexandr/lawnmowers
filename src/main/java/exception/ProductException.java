package exception;

@SuppressWarnings("serial")
public class ProductException extends Exception {

	private Exception hiddenException;

	public ProductException(String message) {
		super(message);
	}

	public ProductException(String message, Exception e) {
		super(message);
		hiddenException = e;
	}

	public ProductException(Throwable cause) {
		super(cause);
	}

	public Exception getHiddenException() {
		return hiddenException;
	}
}
