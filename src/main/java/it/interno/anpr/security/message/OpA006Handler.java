package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws.a000aire.RichiestaA006;
import it.interno.anpr.ws.a000aire.RispostaA006;
import it.interno.anpr.ws.a000anprservice.AnprPortTypeA000;
import it.interno.anpr.ws.a000anprservice.AnprServiceA000;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class OpA006Handler extends RequestHandler implements IMessageHandler<RispostaA006>  {
	private static Log LOGGER = LogFactory.getLog(OpA006Handler.class);
	
	public OpA006Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public RispostaA006 sendRequest(){
		AnprServiceA000 port = new AnprServiceA000();

		try {
			AnprPortTypeA000 wsAnprPort = port.getAnprServicePortA000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			RichiestaA006 request = (RichiestaA006) jaxbXMLToObject(xmlSource, RichiestaA006.class);
			
			LOGGER.info("WSA006 invoke...");
			return wsAnprPort.aireMutazione(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(RispostaA006 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
