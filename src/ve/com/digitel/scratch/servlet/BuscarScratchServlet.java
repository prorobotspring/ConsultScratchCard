package ve.com.digitel.scratch.servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import ve.com.digitel.bssint.EnvironmentUtil;
import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.bssint.log.LogWrapper;
import ve.com.digitel.bssint.messages.ConstantMessageCode;
import ve.com.digitel.bssint.messages.ErrorMessageHandler;

/**
 * Servlet implementation class for Servlet: BuscarScratchServlet
 * 
 */
public class BuscarScratchServlet extends javax.servlet.http.HttpServlet
implements javax.servlet.Servlet {

	static final long serialVersionUID = 1L;

	private static org.apache.log4j.Logger logger = BSSIntLogger
	.getBSSIntLogger(BuscarScratchServlet.class);

	private Context initial;

	private String inicialContextFactory = null;

	private String providerUrl = null;

	private String dataSource = null;
	
	String scratch = new String ();

	RequestDispatcher dispatcher = null;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */

	public BuscarScratchServlet() {

		try {
			ResourceBundle rsr = EnvironmentUtil
			.getResourceAsProperties("config.properties");

			inicialContextFactory = rsr
			.getString("application.initial_context_factory");

			providerUrl = rsr.getString("application.provider_url");

			dataSource = rsr.getString("csc.dataSource");

			// Query = rsr.getString("csc.sql");

			init();

		} catch (Exception e) {

			logger.error(LogWrapper.formatMessage(
					ConstantMessageCode.EXCEPTION, ErrorMessageHandler
					.getMsg(ConstantMessageCode.EXCEPTION)), e);
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,

			HttpServletResponse response) throws ServletException, IOException {

		DataSource ds = null;

		Connection conn = null;

		Statement st = null;

		ResultSet rs = null;
		

		try {

			if (logger.isDebugEnabled())
				logger.debug("Inicio del TRY");



			ds = (DataSource) initial.lookup(dataSource);

			conn = ds.getConnection();

			st = conn.createStatement();

			if (conn!= null){

				if (logger.isDebugEnabled())
					logger.debug("Variables declaradas");

				String query = "SELECT SUBSCRIBER_ID, TO_CHAR(RECHARGE_DATE_TIME, 'dd/mm/yyyy HH12:MI AM') RECHARGE_DATE_TIME FROM RECHARGE_HISTORY_HIST	WHERE BATCH_NUMBER = TO_NUMBER(decode(length('"
					.concat((String) request.getParameter("scratch"))
					.concat("'),10,substr('")
					.concat((String) request.getParameter("scratch"))
					.concat("',1,6),9,substr('")
					.concat((String) request.getParameter("scratch"))
					.concat(
					"',1,7)))AND SERIAL_NUMBER = TO_NUMBER(decode(length('")
					.concat((String) request.getParameter("scratch")).concat(
					"'),10,substr('").concat(
							(String) request.getParameter("scratch")).concat(
							"',7,4),9,'").concat(
									(String) request.getParameter("scratch")).concat(
									"')) GROUP BY SUBSCRIBER_ID, RECHARGE_DATE_TIME");

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query);

				rs = st.executeQuery(

						query);




				if (logger.isDebugEnabled())
					logger.debug("Codigo " + (String) request.getParameter("scratch") + " consultado en la base de datos");

				if (rs.next()) {

					List lista = new ArrayList();

					do {

						Map map = new HashMap();
						map.put("SUBSCRIBER_ID", rs.getString(1));
						map.put("RECHARGE_DATE_TIME", rs.getString(2));
						
						scratch = map.get("SUBSCRIBER_ID").toString();

						if (logger.isDebugEnabled()) {
							logger.debug("map.get(\"SUBSCRIBER_ID\").toString(): "
									+ map.get("SUBSCRIBER_ID").toString());
							logger
							.debug("map.get(\"RECHARGE_DATE_TIME\").toString(): "
									+ map.get("RECHARGE_DATE_TIME")
									.toString());
							
								logger.info("El codigo (Scratch) " + (String) request.getParameter("scratch") + " ya fue quemado en el GSM: " + rs.getString(1) + " el dia: " + rs.getString(2));
								logger.info("Fin del Proceso");

						}

						lista.add(map);
					} while (rs.next());

					request.setAttribute("lista", lista);

					dispatcher = getServletContext().getRequestDispatcher(
					"/Response.jsp");
					dispatcher.forward(request, response);
					
				} else {

					request.setAttribute("mensaje",
					"El serial de tarjeta \"" + (String) request.getParameter("scratch") + "\" no ha sido utilizado ó no es un código válido. Verifique e intente de nuevo.");
					logger.info("El serial de tarjeta \"" + (String) request.getParameter("scratch") + "\" no ha sido utilizado ó no es un código válido");

					dispatcher = getServletContext().getRequestDispatcher(
					"/inicio.jsp");
					dispatcher.forward(request, response);
				}
			}
			else{
				request.setAttribute("mensaje",
				"Falló la conexión con la Base de Datos, intente de nuevo");
				logger.info("Falló la conexión con la Base de Datos");

				dispatcher = getServletContext().getRequestDispatcher(
				"/inicio.jsp");
				dispatcher.forward(request, response);
			}

			

		} catch (Exception e) {
			logger.error("Error de tipo Exception", e);
			Map map = new HashMap();
			map.put("Exception", e);
			request.setAttribute("map", map);
			dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request, response);
			
		}finally {
			try {
				
				if (rs != null)
					rs.close();;
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
				
			} catch (SQLException e) {
				logger.error("Error cerrando la conexion con la base de datos.", e);
				Map map = new HashMap();
				map.put("Exception", e);
				request.setAttribute("map", map);
				dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
				dispatcher.forward(request, response);
			}
		}

	}

	public void init() {

		try {

			if (logger.isDebugEnabled())
				logger.debug("Metodo de inicializacion");

			Properties env = new Properties();

			env.put(Context.INITIAL_CONTEXT_FACTORY, inicialContextFactory);
			env.put(Context.PROVIDER_URL, providerUrl);

			initial = new InitialContext(env);

		} catch (Exception e) {

			logger.error(LogWrapper.formatMessage(
					ConstantMessageCode.EXCEPTION, ErrorMessageHandler
					.getMsg(ConstantMessageCode.EXCEPTION)), e);

		}
	}
}