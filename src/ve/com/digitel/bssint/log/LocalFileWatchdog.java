package ve.com.digitel.bssint.log;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.helpers.FileWatchdog;
import org.apache.log4j.helpers.OptionConverter;

/**
 * 
 * @author Javier Cabrera - Manuel Soto
 * <br>	   BSS - CC Areas Tecnicas - Integracion
 *
 */
public class LocalFileWatchdog extends FileWatchdog {

	private static final String UNABLE_TO_LOAD_LOGGING_PROPERTY = "Unable to load logging property ";

	/**
	 * Constructor para LocalFileWatchdog
	 * @param filename nombre del archivo
	 */
	public LocalFileWatchdog(String filename) {
		super(filename);
	}

	/**
	 * 
	 * Sobrecarga del metodo @see org.apache.log4j.helpers.FileWatchdog#doOnChange()
	 */
	protected void doOnChange() {
		initializeLogger(this.filename);
	}

	/**
	 * permite la inicializacion del logger, usando un observador para el mismo
	 * @param fileName
	 */
	private void initializeLogger(String fileName) {

		try {
			OptionConverter.selectAndConfigure(new URL("file", "", fileName), null, LogManager.getLoggerRepository());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(UNABLE_TO_LOAD_LOGGING_PROPERTY + ": " + filename, e1);
		}
	}
}
