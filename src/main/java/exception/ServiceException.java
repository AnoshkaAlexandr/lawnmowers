package exception;

@SuppressWarnings("serial")
public class ServiceException extends ProductException{

	private Exception hiddenException;

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(String msg, Exception e) {
		super(msg);
		hiddenException = e;
	}

	public Exception getHiddenException() {
		return hiddenException;
	}
}

	
	

