package ve.com.digitel.scratch.servlet;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import ve.com.digitel.bssint.EnvironmentUtil;
import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.scratch.to.Usuario;
import ve.com.digitel.bssint.util.ConexionLDAP;

/**
 * Se encarga de validar un usuario que desea 
 * ingresar en la aplicación. Dicho usuario debe
 * ser validado de dos formas: Debe aparecer registrado en el LDAP y a su vez 
 * debe estar registrado en la BD de la aplicación para lograr una validación correcta
 * e ingresar a la aplicación de forma exitosa.
 *
 */
public class AutenticadorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String ldapIP = null;
	private static String ldapPort = null;
	private static String ldapDN = null;

	private static Logger logger = BSSIntLogger
			.getBSSIntLogger(AutenticadorServlet.class);

	/**
	 * Constructor para la clase AutenticadorServlet.
	 * Verifica en el config.properties las constantes que son necesarias
	 * para la conexion con el LDAP como lo son: LDAP_SERVER, LDAP_PORT y LDAP_DN.
	 */
	public AutenticadorServlet() {
		if (ldapIP == null) {
			ResourceBundle rsr = EnvironmentUtil
					.getResourceAsProperties("config.properties");

			ldapIP = rsr.getString("LDAP_SERVER");
			ldapPort = rsr.getString("LDAP_PORT");
			ldapDN = rsr.getString("LDAP_DN");
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = null;

		try {
			MDC.put("traceid", new Long(System.currentTimeMillis()));

			logger.info("Autenticacion de usuario");

			String usuario = request.getParameter("usuario");
			String clave = request.getParameter("cpass");

			Usuario usu = new Usuario();

			usu.setLogin(usuario);
			usu.setClave(clave);

			logger.info("El usuario autenticado es: " + usuario);

			boolean valido = ConexionLDAP.autenticarUsuario(ldapIP, Integer
					.valueOf(ldapPort).intValue(), ldapDN, usu);

			RequestDispatcher dispatcher = null;

			if (valido) {
				
				if (logger.isDebugEnabled())
					logger.debug("Usuario valido");

				session = request.getSession(true);
				
				if (logger.isDebugEnabled())
					logger.debug("Luego de obtener la sesion");

				dispatcher = getServletContext().getRequestDispatcher("/inicio.jsp");
				
				if (logger.isDebugEnabled())
					logger.debug("Luego del dispatcher");
						
				dispatcher.forward(request, response);

			} else {
				
				request.setAttribute("mensaje", "Usuario o Clave invalidos");
				
				logger.info("El usuario es invalido");

				dispatcher = getServletContext().getRequestDispatcher(
						"/index.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			logger.error("Error de tipo Exception. ", e);
		}

	}
}
