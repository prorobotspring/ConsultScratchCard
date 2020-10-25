package ve.com.digitel.bssint;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Objeto para el manejo de response
 * 
 * @author Diego Leal
 * <br>	   BSS - CC Areas Tecnicas - Integracion
 *
 */
public class ReturnValue implements Serializable {

	/**
	 * long
	 */
	private static final long serialVersionUID = 1L;

	private String condCode;
	
	private String reasonCode;
	
	private String info;
	
	private Map vto;

	public final static ReturnValue SUCCESS = new ReturnValue("0", "Proceso completado satisfactoriamente", "", new HashMap());
	
	public final static ReturnValue FAILURE = new ReturnValue("1", "Error durante la ejecucion de la aplicacion", "", new HashMap());

	/**
	 * 
	 * @return
	 */
	public String getCondCode() {
		return condCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 
	 * @return
	 */
	public Map getVto() {
		return vto;
	}

	/**
	 * 
	 * @param string
	 * @param reasonCode
	 * @param info
	 * @param vto
	 */
	public ReturnValue(String condCode, String reasonCode, String info, Map vto) {
		super();
		this.condCode = condCode;
		this.reasonCode = reasonCode;
		this.info = info;
		this.vto = vto;
	}

	/**
	 * 
	 * Sobrecarga del metodo @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer str = new StringBuffer(getClass().getName() + "{");
		str.append(" | condCode: ");
		str.append(this.condCode);
		str.append(" | reasonCode: ");
		str.append(this.reasonCode);
		str.append(" | info: ");
		str.append(this.info);
		str.append(" | vto: ");
		str.append(this.vto);

		str.append("}");
		return str.toString();
	}

}
