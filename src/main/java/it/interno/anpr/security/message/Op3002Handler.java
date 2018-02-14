package it.interno.anpr.security.message;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._3000anprservice.AnprPortType3000;
import it.interno.anpr.ws._3000anprservice.AnprService3000;
import it.interno.anpr.ws._3000consultazione.Richiesta3002;
import it.interno.anpr.ws._3000consultazione.Risposta3002;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op3002Handler extends RequestHandler implements IMessageHandler<Risposta3002>  {
	private static Log LOGGER = LogFactory.getLog(Op3002Handler.class);
	
	public Op3002Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta3002 sendRequest(){
		AnprService3000 port = new AnprService3000();

		try {
			AnprPortType3000 wsAnprPort = port.getAnprServicePort3000();

			setClient(wsAnprPort);

			InputStream xmlSource = this.getInputPayload();
			Richiesta3002 request = (Richiesta3002) jaxbXMLToObject(xmlSource, Richiesta3002.class);
			
			LOGGER.info("WS3002 invoke...");
			return wsAnprPort.interrogazioneCittadinoFamigliaConvivenza(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta3002 resp) {
		if ((resp.getRisposta3002OK() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
