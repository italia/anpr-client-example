package it.interno.anpr.security.keystore;

import it.interno.anpr.config.ConfigHandler;

/** Gestore Keystore con certificato del Comune, referenziato nel file di properties
 * @author gferraro
 *
 */
public class KeyStoreComune extends KeystoreHandler {

	public KeyStoreComune() {
		this.setPathKeyStore(ConfigHandler.getParam(ConfigHandler.PATH_KEYSTORE_COMUNE));
		this.setPassKeyStore(ConfigHandler.getParam(ConfigHandler.PASS_KEYSTORE_COMUNE));
	}

}
