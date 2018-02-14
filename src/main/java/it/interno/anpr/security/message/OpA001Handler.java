package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws.a000aire.RichiestaA001;
import it.interno.anpr.ws.a000aire.RispostaA001;
import it.interno.anpr.ws.a000anprservice.AnprPortTypeA000;
import it.interno.anpr.ws.a000anprservice.AnprServiceA000;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class OpA001Handler extends RequestHandler implements IMessageHandler<RispostaA001>  {
	private static Log LOGGER = LogFactory.getLog(OpA001Handler.class);
	
	public OpA001Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public RispostaA001 sendRequest(){
		AnprServiceA000 port = new AnprServiceA000();

		try {
			AnprPortTypeA000 wsAnprPort = port.getAnprServicePortA000();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			RichiestaA001 request = (RichiestaA001) jaxbXMLToObject(xmlSource, RichiestaA001.class);
			
			LOGGER.info("WSA001 invoke...");
			return wsAnprPort.aireIscrizioneNascita(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(RispostaA001 resp) {
		if ((resp.getRispostaComune() != null) || (resp.getRispostaKO() != null) || (resp.getRispostaMultipla1001() != null)) 
			return true;
		else 
			return false;
	}
}
