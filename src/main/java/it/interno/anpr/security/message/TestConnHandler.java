package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws.connessione.RichiestaConnessione;
import it.interno.anpr.ws.connessione.RispostaConnessione;
import it.interno.anpr.ws.testconn.AnprPortTypeTestConn;
import it.interno.anpr.ws.testconn.TestConn;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class TestConnHandler extends RequestHandler implements IMessageHandler<RispostaConnessione>  {
	private static Log LOGGER = LogFactory.getLog(TestConnHandler.class);
	
	public TestConnHandler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public RispostaConnessione sendRequest(){
		TestConn port = new TestConn();

		try {
			AnprPortTypeTestConn wsAnprPort = port.getAnprServicePortTestConn();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			RichiestaConnessione request = (RichiestaConnessione) jaxbXMLToObject(xmlSource, RichiestaConnessione.class);
			
			LOGGER.info("testConnessione invoke...");
			return wsAnprPort.testConnessione(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta",e);
			throw new RuntimeException(e);
		}
	}
	
	public boolean isResponseValid(RispostaConnessione resp) {
		if (resp.getTestataRisposta() != null) 
			return true;
		else 
			return false;
	}

}
