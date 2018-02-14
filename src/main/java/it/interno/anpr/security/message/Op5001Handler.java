	package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._5000anprservice.AnprPortType5000;
import it.interno.anpr.ws._5000anprservice.AnprService5000;
import it.interno.anpr.ws._5000mutazione.Richiesta5001;
import it.interno.anpr.ws._5000mutazione.Risposta5001;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op5001Handler extends RequestHandler implements IMessageHandler<Risposta5001>  {
	private static Log LOGGER = LogFactory.getLog(Op5001Handler.class);
	
	public Op5001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta5001 sendRequest(){
		AnprService5000 port = new AnprService5000();

		try {
			AnprPortType5000 wsAnprPort = port.getAnprServicePort5000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta5001 request = (Richiesta5001) jaxbXMLToObject(xmlSource, Richiesta5001.class);
			
			LOGGER.info("WS5001 invoke...");
			return wsAnprPort.mutazioneFamigliaConvivenza(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta5001 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
