package it.interno.anpr.security.wss;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapOutInterceptor;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import it.interno.anpr.util.Utils;

/** Interceptor per il logging del messaggio inviato (Request) 
 * @author gferraro
 *
 */
public class WSLoggingOutInterceptor extends AbstractSoapInterceptor
{

    public WSLoggingOutInterceptor ()
    {
		super(Phase.POST_PROTOCOL_ENDING);
		this.addAfter(SoapOutInterceptor.class.getName());
    }

    /* (non-Javadoc)
     * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
     */
    @Override
    public void handleMessage ( SoapMessage message ) throws Fault
    {
        try
        {
			SOAPMessage soapMsg = message.getContent(SOAPMessage.class);
			if (soapMsg == null) {
			} else {
				String xmlformatted = Utils.prettySOAPMessageAsString(soapMsg);
	            FileOutputStream os = new FileOutputStream ("message_out.xml");
				os.write(xmlformatted.getBytes());;
	            os.close ( );
			}
        }

        catch ( Exception ex )
        {
            ex.printStackTrace ( );
        }

    }

}