package ar.com.almundo.callcenter.call;

/**
 * Excepción que representa la ausencia de un handler en el patrón chain of responsibility implementado por la clase CallEmployeeHandler
 * @author rafael
 *
 */
public class NoCallHandlerException extends CallHandlingException {

	public NoCallHandlerException() {
		super();
	}	
}
