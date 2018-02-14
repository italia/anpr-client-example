package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._4000anprservice.AnprPortType4000;
import it.interno.anpr.ws._4000anprservice.AnprService4000;
import it.interno.anpr.ws._4000estrazione.Richiesta4001;
import it.interno.anpr.ws._4000estrazione.Risposta4001;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op4001Handler extends RequestHandler implements IMessageHandler<Risposta4001>  {
	private static Log LOGGER = LogFactory.getLog(Op4001Handler.class);
	
	public Op4001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta4001 sendRequest(){
		AnprService4000 port = new AnprService4000();

		try {
			AnprPortType4000 wsAnprPort = port.getAnprServicePort4000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta4001 request = (Richiesta4001) jaxbXMLToObject(xmlSource, Richiesta4001.class);
			
			LOGGER.info("WS4001 invoke...");
			return wsAnprPort.estrazioneDatiCittadini(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta4001 resp) {
		if ((resp.getRispostaMultipla4001() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
