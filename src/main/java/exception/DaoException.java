package exception;

@SuppressWarnings("serial")
public class DaoException extends ProductException {

	
	private Exception hiddenException;

	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(String msg, Exception e) {
		super(msg);
		hiddenException = e;
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public Exception getHiddenException() {
		return hiddenException;
	}
	
}
