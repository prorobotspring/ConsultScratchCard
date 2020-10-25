package ve.com.digitel.bssint.log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Manuel Soto
 * 
 */
class SMSClienteSocketServer {

	/**
	 * Longitud máxima del mensaje a enviar
	 */
	private static final int MAX_SMS_LENGTH = 128;
	private static final String ERROR_SMS_NO_ENVIADO = "SMS no enviado";
	/**
	 * Texto enviado por el SocketServer como respuesta en caso de no enviar
	 */
	private static final String SS_SMS_NO_ENVIADO = "<message>not sent</message>";
	/**
	 * Número de líneas que envía el SocketServer al conectar
	 */
	private static final int SS_SOCKET_SERVER_HELLOLINES = 4;
	/**
	 * Comando del SoketServer para salirse y cerrar comunicación
	 */
	private static final String SS_COMMAND_QUIT = "quit\n";
	/**
	 * Comando al SocketServer para que se pueda mandar mas de un SMS
	 */
	private static final String SS_COMMAND_KEEPALIVE = "keepalive\n";
	/**
	 * Caracteres separadores de los MSISDN en el to
	 */
	private static final String SPLIT_PATTERN = "[ ,;]";

	/**
	 * Servidor SocketServer
	 */
	private String ssHost;
	/**
	 * Puerto del SocketServer
	 */
	private int ssPort;
	/**
	 * Usuario del SocketServer 
	 */
	private String ssUser;
	/**
	 * Password del Usuario del SocketServer 
	 */
	private String ssPassword;
	/**
	 * Conexión persistente al SocketServer
	 */
	private Socket ssSocket;
	/**
	 * Escritor dedicado al SocketServer
	 */
	private OutputStreamWriter osw;
	/**
	 * Lector dedicado del SocketServer
	 */
	private BufferedReader isr;
	/**
	 * Timeout para dewscarte de respuesta del SocketServer
	 */
	private int timeOut;

	SMSClienteSocketServer(String host, int port, String user, String password, int timeOut) {
		this.ssHost = host;
		this.ssPort = port;
		this.ssUser = user;
		this.ssPassword = password;
		this.timeOut = timeOut;
	}

	/**
	 * Envía un SMS al conjunto de destinatarios indicados en el toMsisdn
	 * Envía un máximo de MAX_SMS_LENGTH caracteres  
	 * @param from		Indentifica en el Celular el origen del mensaje, puede sed un nombre
	 * @param toMsisdn	Lista de MSISDN separados por SPLIT_PATTERN
	 * @param mensaje	mensaje a enviar
	 * 		Los siguientes caracteres son reemplazados antes de enviar el mensaje:
	 *  		\" por " " 
	 *  		\n por "."
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void sendSMS(String from, String toMsisdn, String mensaje)
			throws UnknownHostException, IOException {
		aseguraConexcion();
		String[] destinos = toMsisdn.split(SPLIT_PATTERN);

		final StringBuffer msg = new StringBuffer(mensaje.trim().replace('"', ' ').replace('\n', '.'));
		
		if (msg.length()>MAX_SMS_LENGTH)
			msg.setLength(MAX_SMS_LENGTH);
		
		// recorre la lista de MSISDN expresados en el destino
		for (int index = 0; index < destinos.length; index++) {

			StringBuffer buffer = new StringBuffer();

			// $mensaje = "sms $dest;$remitente;\"$msg\";$user;$pass\n";
			buffer.append("sms ")
					.append(destinos[index])
					.append(';')
					.append(from)
					.append(";\"")
					.append(msg)
					.append("\";")
					.append(getUser())
					.append(';')
					.append(getPassword())
					.append('\n');
			osw.write(buffer.toString());
			osw.flush();
			
			if (skipNLines(1).equals(SS_SMS_NO_ENVIADO) ){
				throw new IOException(ERROR_SMS_NO_ENVIADO);
			}
		}
		osw.flush();
	}

	/**
	 * Libera la comunicación al SocketServer
	 * @throws IOException
	 */
	public void release() throws IOException {
		osw.write(SS_COMMAND_QUIT);
		osw.flush();
		ssSocket.close();
	}

	/**
	 * Establece la comunicación con el SocketServer en caso de no estar ya
	 * establecida
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void aseguraConexcion() throws UnknownHostException, IOException {
		if (!isOpen())
			openSS();
	}

	/**
	 * Establece la comunicación con el SocketServer
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void openSS() throws UnknownHostException, IOException {
		ssSocket = new Socket(getHost(), getPort());
		ssSocket.setSoTimeout(timeOut);
		
		osw = new OutputStreamWriter(ssSocket.getOutputStream());
		isr = new BufferedReader(new InputStreamReader(ssSocket.getInputStream()));

		skipNLines(SS_SOCKET_SERVER_HELLOLINES);
		osw.write(SS_COMMAND_KEEPALIVE);
	}

	/**
	 * Salta un número de líneas retornando el acumulado de las líneas leidas. 
	 * @param lines
	 *            Número de lineas a saltar
	 * @return lineas leidas
	 * @throws IOException	Normalmente por TimeOut o pérdida de comunicación
	 */
	private String skipNLines(final int lines) throws IOException {
		String dummy;
		StringBuffer response=new StringBuffer();
		int remain=lines;
		if (remain > 0) {
			dummy = isr.readLine();
			while (--remain > 0 && dummy != null) {
				response.append(dummy);
				dummy = isr.readLine();
			}
		}
		return response.toString();
	}

	/**
	 * Indica si se tiene comunicación con el SoketServer y se han establecido el Writer y Reader 
	 * @return
	 */
	private boolean isOpen() {
		return osw != null && ssSocket != null && ssSocket.isConnected() && isr != null;
	}

	/**
	 * Retorna el puerto al SocketServer establecido
	 * @return
	 */
	public int getPort() {
		return ssPort;
	}


	/**
	 * Retorna el Host al SocketServer establecido
	 * @return
	 */
	public String getHost() {
		return ssHost;
	}

	/**
	 * Retorna el usuario al SocketServer establecido
	 * @return
	 */
	public String getUser() {
		return ssUser;
	}

	/**
	 * Retorna el password al SocketServer establecido
	 * @return
	 */
	public String getPassword() {
		return ssPassword;
	}
}
