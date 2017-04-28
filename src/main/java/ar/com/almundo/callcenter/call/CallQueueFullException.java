package ar.com.almundo.callcenter.call;

/**
 * Excepción que significa una cola de llamadas completa al atender una llamada sin que haya empleados libres
 * @author rafael
 *
 */
public class CallQueueFullException extends CallHandlingException {

	public CallQueueFullException() {
		super();
	}	
}
