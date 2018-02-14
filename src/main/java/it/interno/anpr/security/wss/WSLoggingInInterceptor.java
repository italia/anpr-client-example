package it.interno.anpr.security.wss;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import it.interno.anpr.util.Utils;


public class WSLoggingInInterceptor extends AbstractSoapInterceptor
{

    public WSLoggingInInterceptor ()
    {
        super(Phase.POST_STREAM);
    }

    /** (non-Javadoc)
     * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
     */
    @Override
    public void handleMessage ( SoapMessage message ) throws Fault
    {

    	
    	try {
			System.out.println("Esecuzione InInterceptor...");
			InputStream in = message.getContent(InputStream.class);
			byte payload[] = IOUtils.readBytesFromStream(in);
			String xmlformatted = Utils.prettyXML(payload);
			System.out.println("Messaggio di Risposta: ");
			System.out.println(xmlformatted);
	        ByteArrayInputStream bin = new ByteArrayInputStream(payload);
	        message.setContent(InputStream.class, bin);

            FileOutputStream os = new FileOutputStream ("message_in.xml");
			os.write(xmlformatted.getBytes());;
			os.flush();
            os.close ( );

    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

/*        try
        {
			SOAPMessage soapMsg = message.getContent(SOAPMessage.class);
			if (soapMsg == null) {
			} else {
	            FileOutputStream os = new FileOutputStream ("message_in.xml");
				soapMsg.writeTo(os);
				os.flush();
	            os.close ( );
			}
        }
*/
        catch ( Exception ex )
        {
            ex.printStackTrace ( );
        }

    }

}