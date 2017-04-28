package ar.com.almundo.callcenter.call;

/**
 * Excepción padre de la jerarquía de excepciones relacionadas con atender una llamada
 * @author rafael
 *
 */
public class CallHandlingException extends RuntimeException {
	
	public CallHandlingException() {
		super();
	}

	public CallHandlingException(Throwable e) {
		super(e);
	}
}
