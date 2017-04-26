package ar.com.almundo.callcenter.call;

public class CallHandlingException extends RuntimeException {
	
	public CallHandlingException() {
		super();
	}

	public CallHandlingException(Throwable e) {
		super(e);
	}
}
