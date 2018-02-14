package it.interno.anpr.security.keystore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class KeystoreHandler {
	private static Log LOGGER = LogFactory.getLog(KeystoreHandler.class);
	
	private KeyStore keystore;

	private String pathKeyStore;
	private String passKeyStore;
	private String aliasCertificate;

	/** Fornisce l'oggetto Keystore, caricandolo dal file fornito come path
	 * @return Keystore
	 * @throws Exception
	 */
	protected KeyStore getKeyStore() throws Exception {
	    FileInputStream fis;
		try {
			fis = new FileInputStream(getPathKeyStore());
		} catch (FileNotFoundException e) {
			LOGGER.error("KeyStore non trovato in " + getPathKeyStore());
			throw(e);
		}
		
		try {
		    keystore = KeyStore.getInstance("PKCS12");
		    keystore.load(fis, getPassKeyStore().toCharArray());
		} catch (FileNotFoundException e) {
			LOGGER.error("Errore nella lettura del KeyStore " + getPathKeyStore());
			throw(e);
		}
	    
	    return keystore;
	}

	private String getPathKeyStore() {
		return pathKeyStore;
	}

	/** Permette di impostare il path del file contenente il Keystore
	 * @param pathKeyStore
	 */
	public void setPathKeyStore(String pathKeyStore) {
		this.pathKeyStore = pathKeyStore;
	}

	private String getPassKeyStore() {
		return passKeyStore;
	}

	/** Permette di impostare la password di protezione del Keystore
	 * @param passKeyStore
	 */
	public void setPassKeyStore(String passKeyStore) {
		this.passKeyStore = passKeyStore;
	}

	/** Fornisce la lista degli Alias presenti all'interno del Keystore
	 * @return Lista degli Alias presenti all'interno del Keystore
	 * @throws Exception
	 */
	public Enumeration<String> getListAlias() throws Exception {
		return getKeyStore().aliases();
	}

	/** Compone una string contenente la lista degli alias del Keystore
	 * @return Dump
	 * @throws Exception
	 */
	public String dumpListAlias() throws Exception {
		String dump = "";
		
		for (Enumeration<String> aliases = getKeyStore().aliases(); aliases.hasMoreElements();) {
			String alias = aliases.nextElement();
			dump = dump + "<" + alias + "> " + getKeyStore().isKeyEntry(alias) + "\n";
		}	
		
		return dump;
	}
	
	
	/** Fornisce il primo alias estratto dal Keystore
	 * @return Alias
	 * @throws Exception
	 */
	public String getAliasCertificate() throws Exception {
		if (aliasCertificate == null) 
			aliasCertificate = getKeyStore().aliases().nextElement();
		
		return aliasCertificate;
	}
	
	/** Fornisce l'oggetto X509Certificate dell'unico alias presente nel Keystore
	 * @return Certificate
	 * @throws Exception
	 */
	public Certificate getCertificate() throws Exception {
		return getKeyStore().getCertificate(getAliasCertificate());
	}

	/** Estrae la Private Key dal Keystore
	 * @return Private Key
	 * @throws Exception
	 */
	public PrivateKey getPrivateKey() throws Exception {
		return (PrivateKey) getKeyStore().getKey(getAliasCertificate(), getPassKeyStore().toCharArray());
	}

}
