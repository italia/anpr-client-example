package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.ws._1000anprservice.AnprPortType1000;
import it.interno.anpr.ws._1000anprservice.AnprService1000;
import it.interno.anpr.ws._1000iscrizione.Richiesta1013;
import it.interno.anpr.ws._1000iscrizione.Risposta1013;
import it.interno.anpr.config.ParamHandler;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op1013Handler extends RequestHandler implements IMessageHandler<Risposta1013>  {
	private static Log LOGGER = LogFactory.getLog(Op1013Handler.class);
	
	public Op1013Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta1013 sendRequest(){
		AnprService1000 port = new AnprService1000();

		try {
			AnprPortType1000 wsAnprPort = port.getAnprServicePort1000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta1013 request = (Richiesta1013) jaxbXMLToObject(xmlSource, Richiesta1013.class);
			
			LOGGER.info("WS1013 invoke...");
			return wsAnprPort.annullamentoIscrizione(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta1013 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
