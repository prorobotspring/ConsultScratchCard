/**
 * Creado el 16/01/2008
 */
package ve.com.digitel.bssint.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ve.com.digitel.bssint.EnvironmentUtil;

/**
 * Clase que permite la carga y configuracion del LOG de una aplicacion
 * 
 * @author Diego Leal <br>
 *         BSS - CC Areas Tecnicas - Integracion
 */
public class BSSIntLogger {

	private static final String DEFAULT_CONFIG_FILE_PROPERTY_NAME = "log4j.properties";

	private static boolean isConfigure = false;

	/**
	 * Constructor para BSSIntLogger
	 * @param arg
	 */
	protected BSSIntLogger(String arg) {
	}

	/**
	 * Metodo que permite configurar el LOG con el archivo de propiedades creado por defecto
	 * 
	 * @param clazz Clase loader
	 * @return Objeto Logger cargado
	 */
	public static org.apache.log4j.Logger getBSSIntLogger(Class clazz) {
		if (!isConfigure)
			configurarLogger(DEFAULT_CONFIG_FILE_PROPERTY_NAME);

		return getLoggerName(clazz);
	}

	/**
	 * 
	 * Metodo que carga el properties para la configuracion del LOG
	 * @param propFileName
	 * @param string
	 */
	private static void configurarLogger(String propFileName) {

		try {
			File log4jcfg = EnvironmentUtil.getResourceAsFile(propFileName);

			if (log4jcfg == null) {
				throw new FileNotFoundException("No se encontro el archivo '" + propFileName
						+ "' que define la configuracion del log");
			}

			PropertyConfigurator.configureAndWatch(log4jcfg.getAbsolutePath());

			if (EnvironmentUtil.getApplicationName() != null) {
				Logger logger = getLoggerName(BSSIntLogger.class);
				logger.info("APLICACION: " + EnvironmentUtil.getApplicationName() + " - VERSION: "
						+ EnvironmentUtil.getApplicationVersion());
				logger.info("DESCRIPCION: " + EnvironmentUtil.getApplicationDescription());
			}

			isConfigure = true;

		} catch (IOException e) {
			e.printStackTrace(System.err);
		}

	}

	/**
	 * Obtiene el nombre del logger para la aplicacion. Esta asociado al archivo application.properties
	 * @param clazz
	 * @return
	 */
	private static Logger getLoggerName(Class clazz) {
		return Logger.getLogger(EnvironmentUtil.getApplicationName().concat(".").concat(clazz.getName()));
	}

}
