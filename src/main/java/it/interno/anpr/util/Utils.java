package it.interno.anpr.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Utils {

	private static final int INDENT=4;
	
	public static String prettyXML(byte[] xml) {
		return prettyXML(new String(xml));
	}

	public static String prettyXML(String xml) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(xml));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", INDENT);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e); // simple exception handling, please review it
	    }

	}

	public static String prettySOAPMessageAsString(SOAPMessage soapMessage) {
		try {

			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();

			// Set formatting

			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			Source sc = soapMessage.getSOAPPart().getContent();

			ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(streamOut);
			tf.transform(sc, result);

			String strMessage = streamOut.toString();
			return strMessage;
		} catch (Exception e) {
			System.out.println("Exception in getSOAPMessageAsString " + e.getMessage());
			return null;
		}
	}
}
