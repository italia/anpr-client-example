package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._2000anprservice.AnprPortType2000;
import it.interno.anpr.ws._2000anprservice.AnprService2000;
import it.interno.anpr.ws._2000cancellazione.Richiesta2011;
import it.interno.anpr.ws._2000cancellazione.Risposta2011;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op2011Handler extends RequestHandler implements IMessageHandler<Risposta2011>  {
	private static Log LOGGER = LogFactory.getLog(Op2011Handler.class);
	
	public Op2011Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta2011 sendRequest(){
		AnprService2000 port = new AnprService2000();

		try {
			AnprPortType2000 wsAnprPort = port.getAnprServicePort2000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta2011 request = (Richiesta2011) jaxbXMLToObject(xmlSource, Richiesta2011.class);
			
			LOGGER.info("WS2011 invoke...");
			return wsAnprPort.annullamentoCancellazione(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta2011 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
