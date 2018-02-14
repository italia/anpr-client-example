package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws.a000aire.RichiestaA002;
import it.interno.anpr.ws.a000aire.RispostaA002;
import it.interno.anpr.ws.a000anprservice.AnprPortTypeA000;
import it.interno.anpr.ws.a000anprservice.AnprServiceA000;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class OpA002Handler extends RequestHandler implements IMessageHandler<RispostaA002>  {
	private static Log LOGGER = LogFactory.getLog(OpA002Handler.class);
	
	public OpA002Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public RispostaA002 sendRequest(){
		AnprServiceA000 port = new AnprServiceA000();

		try {
			AnprPortTypeA000 wsAnprPort = port.getAnprServicePortA000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			RichiestaA002 request = (RichiestaA002) jaxbXMLToObject(xmlSource, RichiestaA002.class);
			
			LOGGER.info("WSA002 invoke...");
			return wsAnprPort.aireIscrizioneAltriMotivi(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(RispostaA002 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
