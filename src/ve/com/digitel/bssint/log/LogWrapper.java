package ve.com.digitel.bssint.log;


/**
 * 
 * @author Manuel Soto <br>
 * 
 */
public class LogWrapper {

	public static String formatMessage(String codigoMensaje, String textoMensaje) {

		StringBuffer sb = new StringBuffer("Codigo de Error: ").append(
				codigoMensaje).append(" Mensaje de Error: ")
				.append(textoMensaje);

		return sb.toString();
	}

//	public static String info(String traceId, String codigoError,
//			String mensajeError, Throwable rootCause) {
//		return formatMessage(traceId, codigoError, mensajeError, rootCause);
//	}
//
//	public static String error(String traceId, String codigoError,
//			String mensajeError, Throwable rootCause) {
//		return formatMessage(traceId, codigoError, mensajeError, rootCause);
//	}
//
//	public static String fatal(String traceId, String codigoError,
//			String mensajeError, Throwable rootCause) {
//		return formatMessage(traceId, codigoError, mensajeError, rootCause);
//	}
//
//	public static void info(Logger logger, String traceId, String codigoError,
//			String mensajeError, Throwable rootCause) {
//		logger.info(info(traceId, codigoError, mensajeError, rootCause),
//				rootCause);
//	}
//
//	public static void error(Logger logger, String traceId, String codigoError,
//			String mensajeError, Throwable rootCause) {
//		logger.error(error(traceId, codigoError, mensajeError, rootCause),
//				rootCause);
//	}
//
//	public static void fatal(Logger logger, String traceId, String codigoError,
//			String mensajeError, Throwable rootCause) {
//		logger.fatal(fatal(traceId, codigoError, mensajeError, rootCause),
//				rootCause);
//	}

}
