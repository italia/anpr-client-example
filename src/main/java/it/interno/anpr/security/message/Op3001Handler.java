package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._3000anprservice.AnprPortType3000;
import it.interno.anpr.ws._3000anprservice.AnprService3000;
import it.interno.anpr.ws._3000consultazione.Richiesta3001;
import it.interno.anpr.ws._3000consultazione.Risposta3001;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op3001Handler extends RequestHandler implements IMessageHandler<Risposta3001>  {
	private static Log LOGGER = LogFactory.getLog(Op3001Handler.class);
	
	public Op3001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta3001 sendRequest(){
		AnprService3000 port = new AnprService3000();

		try {
			AnprPortType3000 wsAnprPort = port.getAnprServicePort3000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta3001 request = (Richiesta3001) jaxbXMLToObject(xmlSource, Richiesta3001.class);
			
			LOGGER.info("WS3001 invoke...");
			return wsAnprPort.richiestaPaternitaMaternita(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta3001 resp) {
		if ((resp.getRisposta3001OK() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
