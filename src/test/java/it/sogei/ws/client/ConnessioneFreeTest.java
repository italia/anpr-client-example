package it.sogei.ws.client;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.interno.anpr.activator.DispatchHandler;
import it.interno.anpr.config.ConfigHandler;
import it.interno.anpr.config.EnvironmentHandler;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.config.WSTypeHandler;
import it.interno.anpr.security.ssl.ConfigSSL;

public class ConnessioneFreeTest {
	@BeforeClass
	public static void setTrustStore () throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "keystore/cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
	}
	
	@Before
	public void resetConfig () {
		ConfigHandler.reload();
	}

	@Test
	public void test1_ConnessioneOnFree() throws Exception {
		ParamHandler param = new ParamHandler();
		param.setEnvironment(EnvironmentHandler.FREE);
		WSTypeHandler wsHandler = new WSTypeHandler();
		wsHandler.setWSFamily(WSTypeHandler.TESTCONN);
		param.setWsType(wsHandler);
		param.setFileRequest("request/TestConn/testConn_FREE.req");
		DispatchHandler dispatch = new DispatchHandler(param);
		assert(dispatch.execute());
	}

	@Test
	public void test4_3002OnFree() throws Exception {
		ParamHandler param = new ParamHandler();
		param.setEnvironment(EnvironmentHandler.FREE);
		WSTypeHandler wsHandler = new WSTypeHandler();
		wsHandler.setWSFamily(WSTypeHandler.WS3002);
		param.setWsType(wsHandler);
		param.setFileRequest("request/3002/3002_888002_FREE.req");
		DispatchHandler dispatch = new DispatchHandler(param);
		assert(dispatch.execute());
	}
}
