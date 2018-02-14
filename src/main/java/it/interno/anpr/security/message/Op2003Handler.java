package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.ws._2000anprservice.AnprPortType2000;
import it.interno.anpr.ws._2000anprservice.AnprService2000;
import it.interno.anpr.ws._2000cancellazione.Richiesta2003;
import it.interno.anpr.ws._2000cancellazione.Risposta2003;
import it.interno.anpr.config.ParamHandler;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op2003Handler extends RequestHandler implements IMessageHandler<Risposta2003>  {
	private static Log LOGGER = LogFactory.getLog(Op2003Handler.class);
	
	public Op2003Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta2003 sendRequest(){
		AnprService2000 port = new AnprService2000();

		try {
			AnprPortType2000 wsAnprPort = port.getAnprServicePort2000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta2003 request = (Richiesta2003) jaxbXMLToObject(xmlSource, Richiesta2003.class);
			
			LOGGER.info("WS2003 invoke...");
			return wsAnprPort.cancellazioneAltriMotivi(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta2003 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
