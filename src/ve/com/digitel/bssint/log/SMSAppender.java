package ve.com.digitel.bssint.log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Manuel Soto para Digitel
 * 
 * Provee soporte para trazas de Log4J enviadas a SocketServer para entrega de
 * mensajes SMS
 * 
 */
public class SMSAppender extends AppenderSkeleton {

	private static final String ERROR_TIPO_EXCEPTION = "Error tipo Exception";
	private static final String SPLIT_PATTERN = "[ \t:]";
	private static final String ERROR_HOSTNAME = "No se pudo conectar al SocketServer ";
	private static final String ERROR_LEYENDO_ARCHIVO = "Error de lectura en el archivo: ";
	private static final String ERROR_CLOSE = "Imposible cerrar archivo: ";
	private static final String DESTINATIONS_NOTFOUNT = "No se pudo encontar archivo: ";

	private String server;
	private int port;
	private String user;
	private String password;
	private String destinations;
	private String from;
	private int timeOut = 500; // 500ms

	/**
	 * Envia un SMS con el mensaje generado por la aplicacion
	 * 
	 * @see org.apache.log4j.AppenderSkeleton#append(LoggingEvent)
	 */
	protected void append(LoggingEvent event) {
		try {
			SMSClienteSocketServer sms = new SMSClienteSocketServer(server,
					port, user, password, timeOut);

			String message = (layout != null) ? layout.format(event) : event
					.getMessage().toString();
			sms.sendSMS(getFrom(), getDestinations(), message);
		} catch (MalformedURLException e) {
			final String msg = ERROR_HOSTNAME + getServer();
			LogLog.error(msg, e);
		} catch (UnknownHostException e) {
			final String msg = ERROR_HOSTNAME + getServer();
			LogLog.error(msg, e);
		} catch (IOException e) {
			LogLog.error(ERROR_TIPO_EXCEPTION, e);
		}

	}

	/**
	 * Carga la lista de GSMs a reportar desde un archivo El archivo debe
	 * contener un GSM por linea y cada linea puede estar compuesta por el GSM
	 * un comentario, separado por espacio|tab|:
	 * 
	 * @param destinationsFile
	 */
	public void setDestinationsFile(String destinationsFile) {

		File ifile = new File(destinationsFile);
		try {
			BufferedReader br = new BufferedReader(new FileReader(ifile));
			try {

				StringBuffer destinationsBuilder = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					String parts[] = line.split(SPLIT_PATTERN);
					destinationsBuilder.append(parts[0]).append(' ');
				}
				setDestinations(destinationsBuilder.toString());
				br.close();
			} catch (IOException e) {
				LogLog.error(ERROR_LEYENDO_ARCHIVO + destinationsFile, e);
			} finally {
				try {
					new FileReader(ifile).close();
				} catch (IOException e) {
					LogLog.error(ERROR_CLOSE + destinationsFile, e);
				}
			}
		} catch (FileNotFoundException e) {
			LogLog.error(DESTINATIONS_NOTFOUNT + destinationsFile, e);
		}
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String serverURL) {
		this.server = serverURL;
	}

	public String getDestinations() {
		return destinations;
	}

	public void setDestinations(String destinations) {
		this.destinations = destinations;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SMSAppender() {
		super();
	}


	public void close() {
	}

	public boolean requiresLayout() {
		return true;
	}

	public String getPort() {
		return String.valueOf(port);
	}

	public void setPort(String port) {
		this.port = Integer.valueOf(port).intValue();
	}

	public String getTimeOut() {
		return String.valueOf(timeOut);
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = Integer.valueOf(timeOut).intValue();
	}

}
