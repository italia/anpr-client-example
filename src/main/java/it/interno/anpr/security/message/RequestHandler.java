package it.interno.anpr.security.message;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Security;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.interno.anpr.config.ConfigHandler;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.security.wss.WSLoggingInInterceptor;
import it.interno.anpr.security.wss.WSLoggingOutInterceptor;

public class RequestHandler {

	private static Log LOGGER = LogFactory.getLog(RequestHandler.class);
	protected ParamHandler param;

	public RequestHandler(ParamHandler param) {
		Security.addProvider(new BouncyCastleProvider());
		this.param = param;
	}

	protected InputStream getInputPayload() throws Exception {
		InputStream payload = null;
		if ( this.param.getFileRequest() != null ) {
			payload = new FileInputStream( new File( this.param.getFileRequest() ) );
		} else if ( this.param.getDataRequest() != null ) {
			payload = new ByteArrayInputStream( this.param.getDataRequest().getBytes() );
		}
		return payload;
	}
	
	protected void setClient(Object wsPort) {
		Client client = ClientProxy.getClient(wsPort);
		client.getInInterceptors().add(new WSLoggingInInterceptor());
		client.getInInterceptors().add(new LoggingInInterceptor());

		client.getOutInterceptors().add(new WSLoggingOutInterceptor());
		client.getOutInterceptors().add(new LoggingOutInterceptor());
		
		BindingProvider binding = (BindingProvider) wsPort;
		binding.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				ConfigHandler.getParam(ConfigHandler.URL_WS)+param.getWsType().getUri());
		LOGGER.info("ENDPOINT=" + binding.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));

		((BindingProvider) wsPort).getRequestContext().put("ws-security.callback-handler",
				"it.interno.anpr.security.wss.ClientKeystorePasswordCallback");

		((BindingProvider) wsPort).getRequestContext().put("ws-security.signature.properties",
				ConfigHandler.getPathFileKeystore());

		((BindingProvider) wsPort).getRequestContext().put("ws-security.signature.username", ConfigHandler.getParam(ConfigHandler.ALIAS_KEYSTORE_COMUNE));

	}
	
	protected Object jaxbXMLToObject(InputStream xmlSource, Class refClass)
		throws JAXBException {
		try {
			JAXBContext context = JAXBContext.newInstance(refClass);
			Unmarshaller un = context.createUnmarshaller();
			return un.unmarshal(xmlSource);
		} catch (JAXBException e) {
			LOGGER.error("errore nella conversione XML to Object", e);
			throw (e);
		}
	} 
}
