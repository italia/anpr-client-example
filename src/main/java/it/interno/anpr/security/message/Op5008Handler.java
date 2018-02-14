package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._5000anprservice.AnprPortType5000;
import it.interno.anpr.ws._5000anprservice.AnprService5000;
import it.interno.anpr.ws._5000mutazione.Richiesta5008;
import it.interno.anpr.ws._5000mutazione.Risposta5008;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op5008Handler extends RequestHandler implements IMessageHandler<Risposta5008>  {
	private static Log LOGGER = LogFactory.getLog(Op5008Handler.class);
	
	public Op5008Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta5008 sendRequest(){
		AnprService5000 port = new AnprService5000();

		try {
			AnprPortType5000 wsAnprPort = port.getAnprServicePort5000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta5008 request = (Richiesta5008) jaxbXMLToObject(xmlSource, Richiesta5008.class);
			
			LOGGER.info("WS5008 invoke...");
			return wsAnprPort.mutazioneScheda(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta5008 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
