package it.interno.anpr.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Gestione del file di properties per i parametri di configurazione
 * dell'applicazione
 * 
 * @author gferraro
 * 
 */
public class ConfigHandler {

	private static Log LOGGER = LogFactory.getLog(ConfigHandler.class);
	public static final String ID_OPERATORE = "ID_OPERATORE";
	public static final String ID_SEDE = "ID_SEDE";
	public static final String ID_POSTAZIONE = "ID_POSTAZIONE";
	public static final String ID_APPLICAZIONE = "ID_APPLICAZIONE";
	public static final String ID_TRANSAZIONE = "ID_TRANSAZIONE";
	public static final String PATH_KEYSTORE_COMUNE = "org.apache.ws.security.crypto.merlin.keystore.file";
	public static final String PASS_KEYSTORE_COMUNE = "org.apache.ws.security.crypto.merlin.keystore.password";
	public static final String ALIAS_KEYSTORE_COMUNE = "org.apache.ws.security.crypto.merlin.keystore.alias";
	public static final String PATH_KEYSTORE_POSTAZIONE = "PATH_KEYSTORE_POSTAZIONE";
	public static final String PASS_KEYSTORE_POSTAZIONE = "PASS_KEYSTORE_POSTAZIONE";
	public static final String ALIAS_KEYSTORE_POSTAZIONE = "ALIAS_KEYSTORE_POSTAZIONE";
	public static final String URL_WS = "URL_WS";

	private static Properties prop;
	private static String PathFileKeystore;

	private static Properties getProp() {
		if (prop == null) {
			prop = new Properties();
			setPathFileKeystore("/config/"+ EnvironmentHandler.getEnv()+"_Keystore.properties");
			try {
				InputStream is = String.class.getResourceAsStream(PathFileKeystore);
				prop.load(is);
			} catch (FileNotFoundException e) {
				LOGGER.error("Il file di prop " + PathFileKeystore + " non trovato.");
			} catch (IOException e) {
				LOGGER.error("Errore nel caricamento del file di prop "
						+ PathFileKeystore);
			}
		}

		return prop;
	}

	/**
	 * Restituisce il valore di una propriet� letta dal file di properties
	 * 
	 * @param key
	 *            ID della propriet� da leggere (vedi costanti)
	 * @return valore della propriet�
	 */
	public static String getParam(String key) {

		String propertyValue = getProp().getProperty(key);
		LOGGER.debug("Key: (" + key + "):" + propertyValue);
		return propertyValue;
	}
	
	public static void reload() {
		prop = null;
	}

	public static String getPathFileKeystore() {
		return PathFileKeystore;
	}

	public static void setPathFileKeystore(String pathFileKeystore) {
		PathFileKeystore = pathFileKeystore;
	}

}
