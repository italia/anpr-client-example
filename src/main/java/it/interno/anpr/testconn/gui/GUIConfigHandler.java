/**
 * 
 */
package it.interno.anpr.testconn.gui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;

/**
 * @author mttfranci
 *
 */
public class GUIConfigHandler {

	public static String readInputTemplate( String operation ) throws Exception {
		String basePath = "request/stub/"+operation+".xml";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = ClassHelper.getDefaultClassLoader().getResourceAsStream( basePath );
		StreamIO.pipeStream( is , baos, StreamIO.MODE_CLOSE_BOTH );
		return baos.toString(); 
	}
	
}
