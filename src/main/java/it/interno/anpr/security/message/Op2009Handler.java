package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._2000anprservice.AnprPortType2000;
import it.interno.anpr.ws._2000anprservice.AnprService2000;
import it.interno.anpr.ws._2000cancellazione.Richiesta2009;
import it.interno.anpr.ws._2000cancellazione.Risposta2009;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op2009Handler extends RequestHandler implements IMessageHandler<Risposta2009>  {
	private static Log LOGGER = LogFactory.getLog(Op2009Handler.class);
	
	public Op2009Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta2009 sendRequest(){
		AnprService2000 port = new AnprService2000();

		try {
			AnprPortType2000 wsAnprPort = port.getAnprServicePort2000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta2009 request = (Richiesta2009) jaxbXMLToObject(xmlSource, Richiesta2009.class);
			
			LOGGER.info("WS2009 invoke...");
			return wsAnprPort.cancellazioneArchiviazioneSchedaConvivenza(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta2009 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
