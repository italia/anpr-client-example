package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._3000anprservice.AnprPortType3000;
import it.interno.anpr.ws._3000anprservice.AnprService3000;
import it.interno.anpr.ws._3000consultazione.Richiesta3007;
import it.interno.anpr.ws._3000consultazione.Risposta3007;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op3007Handler extends RequestHandler implements IMessageHandler<Risposta3007>  {
	private static Log LOGGER = LogFactory.getLog(Op3007Handler.class);
	
	public Op3007Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta3007 sendRequest(){
		AnprService3000 port = new AnprService3000();

		try {
			AnprPortType3000 wsAnprPort = port.getAnprServicePort3000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta3007 request = (Richiesta3007) jaxbXMLToObject(xmlSource, Richiesta3007.class);
			
			LOGGER.info("WS3007 invoke...");
			return wsAnprPort.ricercaIdentificativiAnpr(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta3007 resp) {
		if ((resp.getRisposta3007OK() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
