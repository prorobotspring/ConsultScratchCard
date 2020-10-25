/**
 * Creado el 14/01/2008
 */
package ve.com.digitel.bssint.messages;


/**
 * @author Diego Leal <br>
 *         BSS - CC Areas Tecnicas - Integracion
 * 
 * Interfaz que contiene las constantes para los codigos de los mensajes de error
 */
public class ConstantMessageCode {

	/* -------------- Error no categorizado -------------- */
	/**
	 * Error no categorizado "0001"
	 */
	public static final String UNDEFINED_ERROR = "0001";

	/**
	 * Ejecucion satisfactoria
	 */
	public static final String SUCCESS_CODE = "0";

	/* -------------- Errores de tipo RuntimeException -------------- */
	/**
	 * ARITHMETIC_EXCEPTION = "1000";
	 */
	public static final String ARITHMETIC_EXCEPTION = "1000";

	/**
	 * ARRAY_STORE_EXCEPTION = "1001";
	 */
	public static final String ARRAY_STORE_EXCEPTION = "1001";

	/**
	 * CLASS_CAST_EXCEPTION = "1002";
	 */
	public static final String CLASS_CAST_EXCEPTION = "1002";

	/**
	 * NEGATIVE_ARRAY_SIZE_EXCEPTION = "1003";
	 */
	public static final String NEGATIVE_ARRAY_SIZE_EXCEPTION = "1003";

	/**
	 * NULL_POINTER_EXCEPTION = "1004";
	 */
	public static final String NULL_POINTER_EXCEPTION = "1004";

	/**
	 * NO_SUCH_ELEMENT_EXCEPTION = "1005";
	 */
	public static final String NO_SUCH_ELEMENT_EXCEPTION = "1005";

	/**
	 * INDEX_OUT_OF_BOUNDS_EXCEPTION = "1006";
	 */
	public static final String INDEX_OUT_OF_BOUNDS_EXCEPTION = "1006";

	/**
	 * ILLEGAL_ARGUMENT_EXCEPTION = "1007";
	 */
	public static final String ILLEGAL_ARGUMENT_EXCEPTION = "1007";

	/**
	 * RUNTIME_EXCEPTION = "1999";
	 */
	public static final String RUNTIME_EXCEPTION = "1999";

	/* -------------- Errores de tipo Exception -------------- */
	/**
	 * SQL_EXCEPTION = "2000";
	 */
	public static final String SQL_EXCEPTION = "2000";

	/**
	 * IO_EXCEPTION = "2001";
	 */
	public static final String IO_EXCEPTION = "2001";

	/**
	 * CLASS_NOT_FOUND_EXCEPTION = "2002";
	 */
	public static final String CLASS_NOT_FOUND_EXCEPTION = "2002";

	/**
	 * INSTANTIATION_EXCEPTION = "2003";
	 */
	public static final String INSTANTIATION_EXCEPTION = "2003";

	/**
	 * NO_SUCH_METHOD__EXCEPTION = "2004";
	 */
	public static final String NO_SUCH_METHOD__EXCEPTION = "2004";

	/**
	 * PARSER_CONF_EXCEPTION = "2005";
	 */
	public static final String PARSER_CONF_EXCEPTION = "2005";

	/**
	 * SAX_EXCEPTION = "2006";
	 */
	public static final String SAX_EXCEPTION = "2006";

	/**
	 * ILLEGAL_ACCESS_EXCEPTION = "2007";
	 */
	public static final String ILLEGAL_ACCESS_EXCEPTION = "2007";

	/**
	 * SAX_EXCEPTION = "2008";
	 */
	public static final String JMS_EXCEPTION = "2008";

	/**
	 * ILLEGAL_ACCESS_EXCEPTION = "2009";
	 */
	public static final String INTERRUPTED_EXCEPTION = "2009";

	/**
	 * JDOM_EXCEPTION = "2010";
	 */
	public static final String JDOM_EXCEPTION = "2010";

	/**
	 * FILE_NOT_FOUND_EXCEPTION = "2011";
	 */
	public static final String FILE_NOT_FOUND_EXCEPTION = "2011";

	/**
	 * REMOTE_EXCEPTION = "2012";
	 */
	public static final String REMOTE_EXCEPTION = "2012";

	/**
	 * NAMING_EXCEPTION = "2013";
	 */
	public static final String NAMING_EXCEPTION = "2013";

	/**
	 * CREATE_EXCEPTION = "2014";
	 */
	public static final String CREATE_EXCEPTION = "2014";

	/**
	 * PARSE_EXCEPTION = "2015";
	 */
	public static final String PARSE_EXCEPTION = "2015";

	/**
	 * TRANSFORMER_EXCEPTION = "2016";
	 */
	public static final String TRANSFORMER_EXCEPTION = "2016";

	/**
	 * TRANSFORMER_FACTORY_EXCEPTION = "2017";
	 */
	public static final String TRANSFORMER_FACTORY_EXCEPTION = "2017";
	
	/**
	 * SERVICE_EXCEPTION = "2017";
	 */
	public static final String SERVICE_EXCEPTION = "2018";;
	
	public static final String HTTPEXCEPTION = "2019";
	
	/**
	 * EXCEPTION = "2999";
	 */
	public static final String EXCEPTION = "2999";
	
	/**
	 * EXCEPTION_2 = "2998";
	 */
	public static final String EXCEPTION_2 = "2998";

	/* -------------- Errores propios de los Desarrollos -------------- */
	/**
	 * Codigo de error para el caso de recibir un archivo XML Invalido
	 */
	public static final String XML_INVALIDO = "6000";
	
	public static final String SUCCESS = "RTBS_SLS-OM0001";
	
	public static final String FORMATO_IN_FAILURE = "RTBS_SLS_OM0002";
	
	public static final String DB_INF_NOTFOUND = "RTBS_SLS_OM0003";
	
	public static final String DATA_OUT_FAILURE = "RTBS_SLS_EM0001";
	
	public static final String COMMUNICATION_FAILURE = "RTBS_SLS_EM0002";
	
	public static final String GENERIC_ERROR = "RTBS_SLS_EM0050";

}
