package ve.com.digitel.bssint.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ve.com.digitel.bssint.EnvironmentUtil;

/**
 * Permite la conexión a la BD así como también ejecutar 
 * diferentes acciones sobre la data que se encuentra en dicha BD.
 *
 */
public class ConexionBaseDatos {

	private Context initial;

	private ResultSet rs = null;

	private PreparedStatement pst = null;

	private DataSource ds = null;

	private Connection conexion = null;

	/**
	 * Permite la conexion con la BD.
	 * Verifica en el config.properties las constantes que son necesarias
	 * para la conexion con la BD como lo son: INITIAL_CONTEXT_FACTORY, PROVIDER_URL y DataSource
	 * @throws NamingException En caso de error.
	 * @throws SQLException En caso de error.
	 */
	public void conectar() throws NamingException, SQLException {

		ResourceBundle rsr = EnvironmentUtil
				.getResourceAsProperties("config.properties");

		Properties env = new Properties();

		env.put(Context.INITIAL_CONTEXT_FACTORY, rsr
				.getObject("INITIAL_CONTEXT_FACTORY"));
		env.put(Context.PROVIDER_URL, rsr.getObject("PROVIDER_URL"));

		initial = new InitialContext(env);

		ds = (DataSource) initial
				.lookup(rsr.getObject("ldap.dataSource").toString());

		conexion = ds.getConnection();

	}

	/**
	 * Obtiene el parametro query que contiene la consulta a la BD.
	 * Ejecuta el parametro query.
	 * @param query Con la consulta a la BD. 
	 * @return Resultset Contiene todos los registros devueltos por el sql   
	 * @throws SQLException En caso de Error.
	 */
	public ResultSet consultar(String query) throws SQLException {

		if (conexion != null) {
			pst = conexion.prepareStatement(query);
			rs = pst.executeQuery();
		}
		return rs;
	}

	/**
	 * Obtiene el parametro query que contiene la insercion a la BD.
	 * Ejecuta el parametro query.
	 * @param query Con la insercion a la BD.
	 * @throws SQLException En caso de Error.
	 */
	public void insertar(String query) throws SQLException {

		if (conexion != null) {
			pst = conexion.prepareStatement(query);
			pst.executeUpdate();
		}
	}

	/**
	 * Obtiene el parametro query que contiene la modificación a la BD.
	 * Ejecuta el parametro query.
	 * @param query Con la modificación a la BD.
	 * @throws SQLException En caso de Error.
	 */
	public void modificar(String query) throws SQLException {

		if (conexion != null) {
			pst = conexion.prepareStatement(query);
			pst.executeUpdate();
		}
	}

	/**
	 * Obtiene el parametro query que contiene la eliminacion a la BD.
	 * Ejecuta el parametro query. 
	 * @param query Con la eliminacion a la BD.
	 * @throws SQLException En caso de Error.
	 */
	public void eliminar(String query) throws SQLException {

		if (conexion != null) {
			pst = conexion.prepareStatement(query);
			pst.executeUpdate();
		}
	}

	/**
	 * Cierra la conexion con la BD.
	 * @throws SQLException En caso de Error.
	 */
	public void desconectar() throws SQLException {

		if (rs != null) {
			rs.close();
		}

		if (pst != null) {
			pst.close();
		}

		if (conexion != null) {
			conexion.close();
		}
	}

	/**
	 * Permite el manejo de las instrucciones y actualizaciones 
	 * en la BD sin necesidad de hacer commit.
	 * @throws SQLException En caso de Error.
	 */
	public void autoCommitFalse() throws SQLException {

		if (conexion != null) {
			conexion.setAutoCommit(false);
		}
	}

	/**
	 * Permite realizar el commit a la BD. Se actualizan los cambios realizados sobre la BD.
	 * @throws SQLException En caso de Error.
	 */
	public void commit() throws SQLException {

		if (conexion != null) {
			conexion.commit();
			conexion.setAutoCommit(true);
		}
	}

}
