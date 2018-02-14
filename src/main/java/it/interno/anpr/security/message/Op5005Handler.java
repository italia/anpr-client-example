package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._5000anprservice.AnprPortType5000;
import it.interno.anpr.ws._5000anprservice.AnprService5000;
import it.interno.anpr.ws._5000mutazione.Richiesta5005;
import it.interno.anpr.ws._5000mutazione.Risposta5005;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op5005Handler extends RequestHandler implements IMessageHandler<Risposta5005>  {
	private static Log LOGGER = LogFactory.getLog(Op5005Handler.class);
	
	public Op5005Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta5005 sendRequest(){
		AnprService5000 port = new AnprService5000();

		try {
			AnprPortType5000 wsAnprPort = port.getAnprServicePort5000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta5005 request = (Richiesta5005) jaxbXMLToObject(xmlSource, Richiesta5005.class);
			
			LOGGER.info("WS5005 invoke...");
			return wsAnprPort.mutazioneResidenza(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta5005 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
