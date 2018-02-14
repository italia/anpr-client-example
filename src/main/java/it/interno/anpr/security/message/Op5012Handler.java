package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._5000anprservice.AnprPortType5000;
import it.interno.anpr.ws._5000anprservice.AnprService5000;
import it.interno.anpr.ws._5000mutazione.Richiesta5012;
import it.interno.anpr.ws._5000mutazione.Risposta5012;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op5012Handler extends RequestHandler implements IMessageHandler<Risposta5012>  {
	private static Log LOGGER = LogFactory.getLog(Op5012Handler.class);
	
	public Op5012Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta5012 sendRequest(){
		AnprService5000 port = new AnprService5000();

		try {
			AnprPortType5000 wsAnprPort = port.getAnprServicePort5000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta5012 request = (Richiesta5012) jaxbXMLToObject(xmlSource, Richiesta5012.class);
			
			LOGGER.info("WS5012 invoke...");
			return wsAnprPort.annullamentoMutazione(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta5012 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
