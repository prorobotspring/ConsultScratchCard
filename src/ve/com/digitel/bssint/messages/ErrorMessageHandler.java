/**
 * Creado el 14/01/2008
 */
package ve.com.digitel.bssint.messages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ve.com.digitel.bssint.EnvironmentUtil;


/**
 * Clase que permite obtener el mensaje de error correspondiente a un codigo.
 * 
 * @author Diego Leal <br>
 *         BSS - CC Areas Tecnicas - Integracion
 */
public class ErrorMessageHandler {

	/**
	 * Nombre del recurso que contiene los mensajes de error
	 */
	private static final String MESSAGE_FILE = "messagelist.properties";

	/**
	 * Recurso que contiene los mensajes de error
	 */
	private static ResourceBundle rsr;

	/**
	 * Prefijo usado para determinar que un codigo de error es de msg
	 */
	public static final String MESSAGE_CODE_PREFIX = "msg-";

	/**
	 * Inicializa el recurso
	 */
	static {
		try {
			rsr = EnvironmentUtil.getResourceAsProperties(MESSAGE_FILE);
		} catch (Throwable e) {
			rsr = null;
		}
	}

	/**
	 * Permite obtener un mensaje de error, dado un codigo Internamente, al
	 * mensaje de error se le concatena el prefijo msg-. Por lo cual: si se le
	 * pasa el codigo "001" ... el metodo busca en el mensaje de error
	 * identificado por "msg-001".
	 * 
	 * @param errorCode
	 *            codigo de error
	 * @return El mensaje de error
	 */
	public static String getMsg(String errorCode) {

		if (rsr == null) {
			throw new IllegalStateException(
					"No se pudo levantar el archivo con los mensajes de error");
		}

		try {
			return rsr.getString(MESSAGE_CODE_PREFIX + errorCode);
		} catch (MissingResourceException e) {
			return "Error no categorizado. Por favor reportar esto al equipo de Integracion.";
		}
	}

	/**
	 * Permite obtener un mensaje de error, dado un codigo Internamente, al
	 * mensaje de error se le concatena el prefijo msg-. Por lo cual: si se le
	 * pasa el codigo "001" ... el metodo busca en el mensaje de error
	 * identificado por "msg-001".
	 * 
	 * @param errorCode
	 *            codigo de error
	 * @return El mensaje de error
	 */
	public static String getMsg(int errorCode) {

		if (rsr == null) {
			throw new IllegalStateException(
					"No se pudo levantar el archivo con los mensajes de error");
		}

		try {
			return rsr.getString(MESSAGE_CODE_PREFIX + errorCode);
		} catch (MissingResourceException e) {
			return "Error no categorizado. Por favor reportar esto al equipo de Integracion.";
		}
	}

	/**
	 * Permite obtener un mensaje de error, dado un codigo Internamente, al
	 * mensaje de error se le concatena el prefijo msg-. Por lo cual: si se le
	 * pasa el codigo "001" ... el metodo busca en el mensaje de error
	 * identificado por "msg-001".
	 * 
	 * @param errorCode
	 *            codigo de error
	 * @return El mensaje de error
	 */
	public static String getMsg(String errorCode, String tail) {

		if (rsr == null) {
			throw new IllegalStateException(
					"No se pudo levantar el archivo con los mensajes de error");
		}

		try {
			return rsr.getString(MESSAGE_CODE_PREFIX + errorCode).concat(tail);
		} catch (MissingResourceException e) {
			return "Error no categorizado. Por favor reportar esto al equipo de Integracion.";
		}
	}

	/**
	 * Permite obtener un mensaje de error, dado un codigo Internamente, al
	 * mensaje de error se le concatena el prefijo msg-. Por lo cual: si se le
	 * pasa el codigo "001" ... el metodo busca en el mensaje de error
	 * identificado por "msg-001".
	 * 
	 * @param errorCode
	 *            codigo de error
	 * @return El mensaje de error
	 */
	public static String getMsg(int errorCode, String tail) {

		if (rsr == null) {
			throw new IllegalStateException(
					"No se pudo levantar el archivo con los mensajes de error");
		}

		try {
			return rsr.getString(MESSAGE_CODE_PREFIX + errorCode).concat(tail);
		} catch (MissingResourceException e) {
			return "Error no categorizado. Por favor reportar esto al equipo de Integracion.";
		}
	}

	/**
	 * Permite construir un mensaje de error a partir de la cadena entrante.
	 * Metodo util para simular el uso del Manejador de Errores para mensajes no
	 * mapeados en el properties
	 * 
	 * @param msg
	 *            Mensaje de error
	 * @return El mensaje de error
	 */
	public static String buildMsg(String msg) {

		return msg;

	}
}
