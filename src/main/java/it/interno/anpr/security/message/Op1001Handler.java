package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.ws._1000anprservice.AnprPortType1000;
import it.interno.anpr.ws._1000anprservice.AnprService1000;
import it.interno.anpr.ws._1000iscrizione.Richiesta1001;
import it.interno.anpr.ws._1000iscrizione.Risposta1001;
import it.interno.anpr.config.ParamHandler;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op1001Handler extends RequestHandler implements IMessageHandler<Risposta1001>  {
	private static Log LOGGER = LogFactory.getLog(Op1001Handler.class);
	
	public Op1001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta1001 sendRequest(){
		AnprService1000 port = new AnprService1000();

		try {
			AnprPortType1000 wsAnprPort = port.getAnprServicePort1000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta1001 request = (Richiesta1001) jaxbXMLToObject(xmlSource, Richiesta1001.class);
			
			LOGGER.info("WS1001 invoke...");
			return wsAnprPort.iscrizioneNascita(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta1001 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null) || (resp.getRispostaMultipla1001() != null)) 
			return true;
		else 
			return false;
	}
}
