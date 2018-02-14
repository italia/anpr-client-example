package it.interno.anpr.security.wss;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import it.interno.anpr.config.ConfigHandler;

/** Callback handler per la gestione della password dei Keystore
 * @author gferraro
 *
 */
public class ClientKeystorePasswordCallback implements CallbackHandler {

	/** Impostazione della password del Keystore
	 * 
	 */
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
			pc.setPassword(ConfigHandler.getParam(ConfigHandler.PASS_KEYSTORE_COMUNE));
		}
		return;
	}
}
