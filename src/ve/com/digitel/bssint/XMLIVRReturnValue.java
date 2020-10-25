/**
 * Creado el 12/05/2008
 */
package ve.com.digitel.bssint;

import org.jdom.Document;
import org.jdom.Element;

/**
 * Clase que permite la conversion de objetos tipo ReturnValue a Doc
 * 
 * @author Diego Leal <br>
 *         BSS - CC Areas Tecnicas - Integracion
 * 
 */
public class XMLIVRReturnValue {

	private static final String BODY = "body";
	private static final String INFO = "info";
	private static final String REFERENCE = "reference";
	private static final String COND_CODE = "condCode";
	private static final String TRACE_ID = "TraceId";
	private static final String HEADER = "header";
	private static final String RESPONSE = "RTBS_IBBLS_OUT";
	private static final String INTEGRACION_FCFLOWS = "http://www.digitelcorp.com.ve/integracion/FCFlows";
	private static final String FC = "fc";

	/**
	 * 
	 */
	private XMLIVRReturnValue() {
	}

	/**
	 * 
	 * @param returnValue
	 * @return
	 */
	public static Document toDocument(ReturnValue returnValue) {

		/*
		 * <?xml version="1.0" encoding="UTF-8"?> <!-- XML de salida ejemplo del
		 * servicio RTBS_IBBLS-> <RTBS_IBBLS_OUT> <OperationMessage> <Code>
		 * </Code> <Message> </Message> </OperationMessage> </RTBS_IBBLS_OUT>
		 */

		// final Namespace fcNamespace = Namespace.getNamespace(FC,
		// INTEGRACION_FCFLOWS);
		Element rootElement = new Element(RESPONSE);

		rootElement.addContent(buildElement("Code", returnValue.getCondCode()));
		
		rootElement.addContent(buildElement("Message", returnValue.getReasonCode()));


		return new Document(rootElement);
	}

	/**
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	private static Element buildElement(String name, String text) {

		final Element element = new Element(name);

		if (text != null)
			element.addContent(text);

		return element;
	}

	public static void main(String[] args) {
		// System.out.println(new
		// org.jdom.output.XMLOutputter().outputString(XMLIVRReturnValue.toDocument(new
		// ReturnValue("123","message","",new HashMap()))));
	}
}
