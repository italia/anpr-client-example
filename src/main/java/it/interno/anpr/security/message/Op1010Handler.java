package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.ws._1000anprservice.AnprPortType1000;
import it.interno.anpr.ws._1000anprservice.AnprService1000;
import it.interno.anpr.ws._1000iscrizione.Richiesta1010;
import it.interno.anpr.ws._1000iscrizione.Risposta1010;
import it.interno.anpr.config.ParamHandler;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op1010Handler extends RequestHandler implements IMessageHandler<Risposta1010>  {
	private static Log LOGGER = LogFactory.getLog(Op1010Handler.class);
	
	public Op1010Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta1010 sendRequest(){
		AnprService1000 port = new AnprService1000();

		try {
			AnprPortType1000 wsAnprPort = port.getAnprServicePort1000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta1010 request = (Richiesta1010) jaxbXMLToObject(xmlSource, Richiesta1010.class);
			
			LOGGER.info("WS1010 invoke...");
			return wsAnprPort.iscrizioneSchedaConvivenza(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta1010 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
