package ve.com.digitel.bssint.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Clase que se encarga de crear un XML a partir de un Map
 * 
 * @author Diego Leal
 * <br>	   BSS - Aplicaciones Web
 *
 */
public class XMLOutputter {
	/**
	 * Constante que permite abrir el root label
	 */
	private static final String START_ROOT_TAG = "<xml>";
	
	/**
	 * Constante que permite cerrar el root label
	 */
	private static final String END_ROOT_TAG = "</xml>";
	
	/**
	 * Constante que permite abrir el label final de un elemento en el XML
	 */
	private static final String LTC = "</";

	/**
	 * Constante que permite cerrar el label inicial de un elemento en el XML
	 */
	private static final String GT = ">";

	/**
	 * Constante que permite abrir el label inicial de un elemento en el XML
	 */
	private static final String LT = "<";

	/**
	 * Constante que representa el encabezado del XML
	 */
	private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	/**
	 * Se encarga de transformar un HashMap en un String bajo formato de un archivo
	 * XML. La transformación se realiza armando cada elemento del xml colocando como 
	 * label el valor del key en el Map y el valor del label corresponde al value del Map
	 * 
	 * @param map
	 * 		HashMap a ser transformado en un String con formato de un archivo XML
	 * @return xmlString
	 * 		String que representa el HashMap transformado bajo formato de archivo XML
	 */
	public String MapToXml(Map map) {

		String element = "";
		String elementSet = "";

		Iterator iter = map.entrySet().iterator();

		// iteración sobre cada elemento del Map
		while (iter != null && iter.hasNext()) {

			Map.Entry e = (Map.Entry) iter.next();

			/*
			 * arma cada elemento del xml colocando como label el valor del key en el Map
			 * y el valor del label corresponde al value del Map 
			 */
			element = LT + e.getKey() + GT + e.getValue() + LTC + e.getKey() + GT;

			elementSet = element + elementSet;
		}
		// arma el string concatenando las diferentes partes que componen el XML
		String xmlString = HEADER + START_ROOT_TAG + elementSet + END_ROOT_TAG;

		//System.out.println(xmlString);
		return xmlString;
	}

}