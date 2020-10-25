package ve.com.digitel.bssint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Clase utilitaria para el manejo de variables y datos de ambiente
 * 
 * @author Diego Leal <br>
 *         BSS - CC Areas Tecnicas - Integracion
 */
public class EnvironmentUtil {

	public static final String APPLICATION_DESCRIPTION = "application.description";

	public static final String APPLICATION_VERSION = "application.version";

	public static final String APPLICATION_NAME = "application.name";

	private static final String APPLICATIONS = "applications";

	private static final String DIGITEL = "digitel";

	private static final String USER_HOME = "user.home";

	private static final String FILE_SEPARATOR = "file.separator";

	private static final String INIT_CONFIG = "initconfig";

	private static String APP_DIGITEL_HOME;

	private static ResourceBundle rsr;

	static {

		APP_DIGITEL_HOME = System.getProperty(USER_HOME) + System.getProperty(FILE_SEPARATOR)
				+ DIGITEL + System.getProperty(FILE_SEPARATOR) + APPLICATIONS
				+ System.getProperty(FILE_SEPARATOR);

		try {

			rsr = ResourceBundle.getBundle(INIT_CONFIG);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna un recurso como un objeto URL
	 * 
	 * @param resourceName
	 *            nombre del recurso
	 * @return URL
	 */
	public static URL getResource(String resourceName) {

		File file = getResourceAsFile(resourceName);

		URL url;
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return url;
	}

	/**
	 * Retorna un recurso especifico como un ResourceBundle
	 * 
	 * @param resourceName
	 *            nombre del recurso
	 * @return ResourceBundle
	 */
	public static ResourceBundle getResourceAsProperties(String resourceName) {

		try {
			PropertyResourceBundle p = new PropertyResourceBundle(new FileInputStream(
					getResourceAsFile(resourceName)));
			return p;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}

	/**
	 * Retorna la ruta absoluta usando variables de ambiente
	 * 
	 * @param resourceName
	 * @return Ruta absoluta
	 */
	public static String getAbsoluteResourceName(String resourceName) {
		return APP_DIGITEL_HOME + rsr.getObject(APPLICATION_NAME)
				+ System.getProperty(FILE_SEPARATOR) + resourceName;
	}

	/**
	 * Retorna un recurso especifico como un InputStream
	 * 
	 * @param resourceName
	 *            nombre del recurso
	 * @return InputStream
	 */
	public static InputStream getResourceAsStream(String resourceName) {

		try {
			InputStream p = new FileInputStream(getResourceAsFile(resourceName));

			return p;

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}

	/**
	 * Retorna un recurso especifico como un File
	 * 
	 * @param resourceName
	 *            nombre del recurso
	 * @return File
	 */
	public static File getResourceAsFile(String resourceName) {

		String var = getAbsoluteResourceName(resourceName);

		return new File(var);
	}

	/**
	 * Retorna el nombre de la aplicacion
	 * 
	 * @return String
	 */
	public static String getApplicationName() {
		return rsr.getObject(APPLICATION_NAME).toString();
	}

	/**
	 * Retorna la version de la aplicacion
	 * 
	 * @return String
	 */
	public static String getApplicationVersion() {
		return rsr.getObject(APPLICATION_VERSION).toString();
	}

	/**
	 * Retorna la descripcion de la aplicacion
	 * 
	 * @return String
	 */
	public static String getApplicationDescription() {
		return rsr.getObject(APPLICATION_DESCRIPTION).toString();
	}

	/**
	 * 
	 * @param recurso
	 * @return
	 * @throws Exception
	 */
	public static String getStringResource(String recurso) throws Exception {

		StringBuffer buf = new StringBuffer(5000);
		byte[] data = new byte[5000];

		InputStream in = EnvironmentUtil.getResourceAsStream(recurso);

		try {
			if (in != null) {
				while (true) {
					int len = in.read(data);
					if (len != -1) {
						buf.append(new String(data, 0, len));
					} else {
						break;
					}
				}
				return buf.toString();
			} else {
				System.out.println("11| Ruta incorrecta de la plantilla o recurso estatico: "
						+ recurso + "");
				throw new Exception(EnvironmentUtil.class.getName()
						+ ".getResource: 11| Ruta incorrecta de la plantilla o recurso estatico: "
						+ recurso);
			}

		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new Exception(e);
				}
			}
		}
	}

}