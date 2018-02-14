package it.interno.anpr.security.message;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.ws.cieanprservice.AnprPortTypeCIE;
import it.interno.anpr.ws.cieanprservice.AnprServiceCIE;
import it.interno.anpr.ws.cie0servizi.RichiestaCIE2;
import it.interno.anpr.ws.cie0servizi.RispostaEsitoCIE2;
import it.interno.anpr.config.ParamHandler;

/** Gestore per l'invocazione del servizio Test Connessione
 * @author gferraro
 *
 */
public class OpCIE2Handler extends RequestHandler implements IMessageHandler<RispostaEsitoCIE2>  {
	private static Log LOGGER = LogFactory.getLog(OpCIE2Handler.class);
	
	public OpCIE2Handler(ParamHandler param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public RispostaEsitoCIE2 sendRequest(){
		AnprServiceCIE port = new AnprServiceCIE();

		try {
			AnprPortTypeCIE wsAnprPort = port.getAnprServicePortCIE();

			setClient(wsAnprPort);

			InputStream xmlSource = new FileInputStream(param.getFileRequest());
			RichiestaCIE2 request = (RichiestaCIE2) jaxbXMLToObject(xmlSource, RichiestaCIE2.class);
			
			LOGGER.info("WSCIE2 invoke...");
			return wsAnprPort.interrogazioneDatiAnagrafici(request);

		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
	}

	public boolean isResponseValid(RispostaEsitoCIE2 resp) {
		if ((resp.getRispostaCIE2() != null) || (resp.getRispostaKO() != null)) 
			return true;
		else 
			return false;
	}
}
