package ve.com.digitel.bssint.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;

/**
 * Adaptador basado en sustitucion por nombre.
 * 
 * @author Alfonso Maldonado
 * <br>	   BSS - Aplicaciones Web
 *
 */
public class NameSustitutionCmdAdapter {

	/**
	 * Log de la aplicación.
	 */
	private static Logger logger = BSSIntLogger.getBSSIntLogger(NameSustitutionCmdAdapter.class);

	List componentList = null;

	/**
	 * Compila un comando a traves de la sustitucion por nombre
	 * 
	 * @param cmd 
	 * 		Comando a compilar
	 * @throws Exception 
	 * 		En caso de error
	 */
	public void compileCmd(String cmd) throws Exception {

		String dn = "${";
		String dnEnd = "}";

		try {

			StringTokenizer st = new StringTokenizer(cmd, dn);
			int qty = st.countTokens();
			String type = null;
			CmdComponent cmdC = null;

			if (logger.isDebugEnabled())
				logger.debug("Iniciando proceso de compilacion de comando: " + cmd);

			for (int i = 0; i < qty; i++) {
				type = st.nextToken();

				if (type.indexOf(dnEnd) != -1) {
					String tokenInt = "";

					StringTokenizer stInt = new StringTokenizer(type, dnEnd);

					tokenInt = stInt.nextToken();

					if (logger.isDebugEnabled())
						logger.debug("Componente VARIABLE encontrado: " + tokenInt);

					cmdC = new CmdNameComponent(tokenInt);

					componentList.add(cmdC);

					if (stInt.hasMoreElements()) {

						tokenInt = stInt.nextToken();

						if (logger.isDebugEnabled())
							logger.debug("Componente CONSTANTE encontrado: " + tokenInt);

						cmdC = new CmdConstantComponent(tokenInt);

						componentList.add(cmdC);
					}

				} else {
					if (logger.isDebugEnabled())
						logger.debug("Componente CONSTANTE encontrado: " + type);

					cmdC = new CmdConstantComponent(type);

					componentList.add(cmdC);
				}

			}
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.error("Error de tipo Exception durante la compilacion del comando " + cmd);
			throw e;
		}

	}

	/**
	 * Clase que representa los atributos de los comandos
	 * 
	 * @author Diego Leal
	 * <br>	   BSS - CC Areas Tecnicas - Integracion
	 *
	 */
	public abstract class CmdComponent {

		protected String key;

		/**
		 * Constructor
		 * 
		 * @param key Clave asociada al componente
		 */
		public CmdComponent(String key) {
			this.key = key;
		}

		abstract public String apply(Map context);

	}

	/**
	 * Clase componente, tipo nombre
	 * 
	 * @author Diego Leal
	 * <br>	   BSS - CC Areas Tecnicas - Integracion
	 *
	 */
	public class CmdNameComponent extends CmdComponent {

		/**
		 * Constructor
		 * 
		 * @param name Clave asociada al componente
		 */
		public CmdNameComponent(String name) {
			super(name);
		}

		public String apply(Map context) {
			String name = (String) context.get(key);

			if (name != null)
				name = name.trim();

			return name;
		}

	}

	/**
	 * Clase componente, tipo constante
	 * 
	 * @author Diego Leal
	 * <br>	   BSS - CC Areas Tecnicas - Integracion
	 *
	 */
	public class CmdConstantComponent extends CmdComponent {

		/**
		 * Constructor
		 * 
		 * @param constant Clave asociada al componente
		 */
		public CmdConstantComponent(String constant) {
			super(constant);
		}

		public String apply(Map context) {
			return key;
		}

	}

	/**
	 * Inicializa los adaptadores
	 * 
	 * @param cmd 
	 * 		Identificador de comando
	 * @throws Exception 
	 * 		En caso de error
	 */
	public void init(String cmd) throws Exception {

		componentList = new ArrayList();
		compileCmd(cmd);

	}

	/**
	 * Ensambla un comando usando los parametros de entrada
	 * 
	 * @param context 
	 * 		Parametros de entrada
	 * @return String 
	 * 		Comando ensamblado.
	 */
	public String assembly(Map context) {

		logger.debug("Ensamblando lista de COMPONENTES para la construccion del COMANDO");

		StringBuffer sBuff = new StringBuffer();

		Iterator it = componentList.iterator();

		while (it.hasNext()) {
			sBuff.append(((CmdComponent) (it.next())).apply(context));
		}

		return sBuff.toString();
	}

}
