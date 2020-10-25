/**
 * Creado el 12/05/2008
 */
package ve.com.digitel.bssint;

import java.util.Iterator;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

/**
 * Clase que permite la conversion de objetos tipo ReturnValue a Doc
 * 
 * @author Diego Leal
 * <br>	   BSS - CC Areas Tecnicas - Integracion
 *
 */
public class XMLReturnValue {

	private static final String BODY = "body";
	private static final String INFO = "info";
	private static final String REFERENCE = "reference";
	private static final String COND_CODE = "condCode";
	private static final String TRACE_ID = "TraceId";
	private static final String HEADER = "header";
	private static final String RESPONSE = "response";
	private static final String INTEGRACION_FCFLOWS = "http://www.digitelcorp.com.ve/integracion/FCFlows";
	private static final String FC = "fc";

	/**
	 * 
	 */
	private XMLReturnValue() {
	}

	/**
	 * 
	 * @param returnValue
	 * @return
	 */
	public static Document toDocument(ReturnValue returnValue) {

		final Namespace fcNamespace = Namespace.getNamespace(FC, INTEGRACION_FCFLOWS);

		Element rootElement = new Element(RESPONSE, fcNamespace);

		final Element header = new Element(HEADER, fcNamespace);

		header.addContent(buildElement(TRACE_ID, fcNamespace, null)).addContent(
				buildElement(COND_CODE, fcNamespace, returnValue.getCondCode())).addContent(
				buildElement(REFERENCE, fcNamespace, returnValue.getReasonCode())).addContent(
				buildElement(INFO, fcNamespace, returnValue.getInfo()));

		rootElement.addContent(header);

		final Element body = new Element(BODY, fcNamespace);

		Iterator it = returnValue.getVto().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			body.addContent(buildElement(e.getKey().toString(), fcNamespace, e.getValue().toString()));
		}
		rootElement.addContent(body);

		return new Document(rootElement);
	}

	/**
	 * 
	 * @param name
	 * @param fcNamespace
	 * @param text
	 * @return
	 */
	private static Element buildElement(String name, Namespace fcNamespace, String text) {

		final Element element = new Element(name, fcNamespace);

		if (text != null)
			element.addContent(text);

		return element;
	}
}
