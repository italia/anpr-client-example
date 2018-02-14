package it.interno.anpr.security.keystore;

import it.interno.anpr.config.ConfigHandler;

/** Gestore Keystore con certificato di Postazione, referenziato nel file di properties
 *
 */
public class KeyStorePostazione extends KeystoreHandler {

	public KeyStorePostazione() {
		this.setPathKeyStore(ConfigHandler.getParam(ConfigHandler.PATH_KEYSTORE_POSTAZIONE));
		this.setPassKeyStore(ConfigHandler.getParam(ConfigHandler.PASS_KEYSTORE_POSTAZIONE));
	}

}
