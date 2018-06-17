package exception;

@SuppressWarnings("serial")
public class ServiceException extends ProductException {

	private Exception hiddenException;

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String msg, Exception e) {
		super(msg);
		hiddenException = e;
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public Exception getHiddenException() {
		return hiddenException;
	}
}
