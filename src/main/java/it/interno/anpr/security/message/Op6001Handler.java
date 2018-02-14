package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._6001anprservice.AnprPortType6001;
import it.interno.anpr.ws._6001anprservice.AnprService6001;
import it.interno.anpr.ws._6001certificazione.Richiesta6001;
import it.interno.anpr.ws._6001certificazione.Risposta6001;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op6001Handler extends RequestHandler implements IMessageHandler<Risposta6001>  {
	private static Log LOGGER = LogFactory.getLog(Op6001Handler.class);
	
	public Op6001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta6001 sendRequest(){
		AnprService6001 port = new AnprService6001();

		try {
			AnprPortType6001 wsAnprPort = port.getAnprServicePort6001();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta6001 request = (Richiesta6001) jaxbXMLToObject(xmlSource, Richiesta6001.class);
			
			LOGGER.info("WS6001 invoke...");
			return wsAnprPort.emissioneCertificato(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta6001 resp) {
		if ((resp.getRisposta6001OK() != null) || (resp.getRisposta6001KO() != null)) 
			return true;
		else 
			return false;
	}
}
