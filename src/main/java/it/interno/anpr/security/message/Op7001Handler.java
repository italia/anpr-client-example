package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws._7001anprservice.AnprPortType7001;
import it.interno.anpr.ws._7001anprservice.AnprService7001;
import it.interno.anpr.ws._7001scaricotabelle.Richiesta7001;
import it.interno.anpr.ws._7001scaricotabelle.Risposta7001;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class Op7001Handler extends RequestHandler implements IMessageHandler<Risposta7001>  {
	private static Log LOGGER = LogFactory.getLog(Op7001Handler.class);
	
	public Op7001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta7001 sendRequest(){
		AnprService7001 port = new AnprService7001();

		try {
			AnprPortType7001 wsAnprPort = port.getAnprServicePort7001();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			Richiesta7001 request = (Richiesta7001) jaxbXMLToObject(xmlSource, Richiesta7001.class);
			
			LOGGER.info("WS7001 invoke...");
			return wsAnprPort.scaricaTabelle(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(Risposta7001 resp) {
		if ((resp.getRispostaMultipla7001() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
