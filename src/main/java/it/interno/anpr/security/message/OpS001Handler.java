package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws.S001anprservice.AnprPortTypeS001;
import it.interno.anpr.ws.S001anprservice.AnprServiceS001;
import it.interno.anpr.ws.S001subentro.RichiestaS001;
import it.interno.anpr.ws.S001subentro.RispostaS001;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class OpS001Handler extends RequestHandler implements IMessageHandler<RispostaS001>  {
	private static Log LOGGER = LogFactory.getLog(OpS001Handler.class);
	
	public OpS001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public RispostaS001 sendRequest(){
		AnprServiceS001 port = new AnprServiceS001();

		try {
			AnprPortTypeS001 wsAnprPort = port.getAnprServicePortS001();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			RichiestaS001 request = (RichiestaS001) jaxbXMLToObject(xmlSource, RichiestaS001.class);
			
			LOGGER.info("WSS001 invoke...");
			return wsAnprPort.subentroS001(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(RispostaS001 resp) {
		if ((resp.getRispostaOK() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
