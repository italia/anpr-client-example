package it.interno.anpr.security.main;

import java.io.File;

import it.interno.anpr.activator.DispatchHandler;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.config.WSTypeHandler;
import it.interno.anpr.config.EnvironmentHandler;

/** Main per l'esecuzione del Client, viene invocato il servizio di Test Connessione
 * @author gferraro
 *
 */
public class RunSenderMessage {

	public static void main(String[] args) {

		try {
			//ConfigSSL.trustAll();
			
			System.setProperty("javax.net.ssl.trustStore", "keystore/cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

			ParamHandler param = new ParamHandler();
			
			if (args.length != 3) {
				System.out.println("Usage: <ENV> <WS> <FILE_REQ>");
				System.out.println("<ENV> = TEST, PRE, FREE, PROD");
				System.out.println("<WS> = TESTCONN, WS1001, WS1002, ... WS3002....");
				System.out.println("<FILE_REQ> = Percorso del file della richiesta");
				System.exit(-1);
			} else {
				if (!EnvironmentHandler.isValid(args[0])) { 
					System.out.println("<ENV> Invalido. Valori ammessi:");
					System.out.println("<ENV> = TEST, PRE, FREE, PROD");
					System.exit(-1);
				}

				param.setEnvironment(args[0]);
				
				WSTypeHandler wsHandler = new WSTypeHandler();
				if (!wsHandler.isValid(args[1])) { 
					System.out.println("<WS> Operation non prevista. Valori ammessi");
					System.out.println("<WS> = TESTCONN, WS1001, WS1002, ... WS3002....");
					System.exit(-1);
				}
				wsHandler.setWSFamily(args[1]);
				param.setWsType(wsHandler);

				File f = new File(args[2]);
				if (!f.exists()) {
					System.out.println("<FILE_REQ> Il file non esiste");
					System.exit(-1);
				}
				
				param.setFileRequest(args[2]);
			}

			DispatchHandler dispatch = new DispatchHandler(param);
			long startTime = System.currentTimeMillis();
			dispatch.execute();
			System.out.println("Tempo impiegato per invocare il servizio <"+(System.currentTimeMillis()-startTime)+"msec>");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
