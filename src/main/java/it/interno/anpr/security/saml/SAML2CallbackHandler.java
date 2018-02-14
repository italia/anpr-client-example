package it.interno.anpr.security.saml;

import it.interno.anpr.config.ConfigHandler;
import it.interno.anpr.security.pki.Signer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.bean.AttributeBean;
import org.apache.wss4j.common.saml.bean.AttributeStatementBean;
import org.apache.wss4j.common.saml.bean.AuthenticationStatementBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.common.saml.bean.SubjectConfirmationDataBean;
import org.apache.wss4j.common.saml.bean.SubjectLocalityBean;
import org.joda.time.DateTime;
	
/** Callback Handler per la creazione di una SAML 2 assertion.
 * @author gferraro
 *
 */
public class SAML2CallbackHandler extends AbstractSAMLCallbackHandler {
	private static Log LOGGER = LogFactory.getLog(SAML2CallbackHandler.class);
	
	private Crypto crypto;
	
	    /** Construttore per l'inizializzazione del motore di Crypto
	     * @throws Exception
	     */
	    public SAML2CallbackHandler() throws Exception {
	    	if (certs == null) {
//	            crypto = CryptoFactory.getInstance("clientKeystore.properties");
	           crypto = CryptoFactory.getInstance(ConfigHandler.getPathFileKeystore());
	            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
	            cryptoType.setAlias(ConfigHandler.getParam(ConfigHandler.ALIAS_KEYSTORE_COMUNE));
	            certs = crypto.getX509Certificates(cryptoType);
	        }
	        
	        subjectName = ConfigHandler.getParam(ConfigHandler.ID_OPERATORE);
	        subjectQualifier = null;
	        confirmationMethod = SAML2Constants.CONF_BEARER;
	    }
	    
	    /** Handle per la creazione dell'assertion SAML 2.0
	     * @param callbacks - lista dei callbacks attivati
	     */
	    public void handle(Callback[] callbacks)
	        throws IOException, UnsupportedCallbackException {
	        for (int i = 0; i < callbacks.length; i++) {
	            if (callbacks[i] instanceof SAMLCallback) {
	                SAMLCallback callback = (SAMLCallback) callbacks[i];
	                callback.setIssuer("XS");

	                DateTime now = new DateTime();
	                DateTime limit = now.plusMinutes(5);
	                SubjectBean subjectBean = 
	                    new SubjectBean(
	                        subjectName, subjectQualifier, confirmationMethod
	                    );

                    SubjectConfirmationDataBean subjectConfirmationData = new SubjectConfirmationDataBean();
                    subjectConfirmationData.setNotAfter(limit);
                    subjectConfirmationData.setNotBefore(now);
                    subjectBean.setSubjectConfirmationData(subjectConfirmationData);
                    callback.setSubject(subjectBean);

                    AuthenticationStatementBean authenticationStatementBean = new AuthenticationStatementBean();
                    authenticationStatementBean.setSessionNotOnOrAfter(limit);
                    authenticationStatementBean.setAuthenticationInstant(now);
                    SubjectLocalityBean subjectLocality = new SubjectLocalityBean();
                    subjectLocality.setIpAddress(InetAddress.getLocalHost().getHostAddress());
                    authenticationStatementBean.setSubjectLocality(subjectLocality);
                    List <AuthenticationStatementBean> authenticationStatementData = new ArrayList<>();
                    authenticationStatementData.add(authenticationStatementBean);
                    callback.setAuthenticationStatementData(authenticationStatementData);
                    
	                // Attribute definitions
	                AttributeBean idSede = new AttributeBean();
                    idSede.setQualifiedName("IdSede");
                    idSede.setNameFormat(SAML2Constants.ATTRNAME_FORMAT_UNSPECIFIED);
                    List<Object> idSedeValue = new ArrayList<>();
                    idSedeValue.add(ConfigHandler.getParam(ConfigHandler.ID_SEDE));
                    idSede.setAttributeValues(idSedeValue);
	                
	                AttributeBean idPostazione = new AttributeBean();
                    idPostazione.setQualifiedName("IdPostazione");
                    idPostazione.setNameFormat(SAML2Constants.ATTRNAME_FORMAT_UNSPECIFIED);
                    List<Object> idPostazioneValue = new ArrayList<>();
                    idPostazioneValue.add(ConfigHandler.getParam(ConfigHandler.ID_POSTAZIONE));
                    idPostazione.setAttributeValues(idPostazioneValue);
	                
	                AttributeBean idPostazioneFirmato = new AttributeBean();
	                idPostazioneFirmato.setQualifiedName("IdPostazioneFirmato");
	                idPostazioneFirmato.setNameFormat(SAML2Constants.ATTRNAME_FORMAT_UNSPECIFIED);
                    List<Object> idPostazioneFirmatoValue = new ArrayList<>();
        			
                    String pkcs7;
					try {
						pkcs7 = Base64.encodeBase64String(Signer.signPostazione(ConfigHandler.getParam(ConfigHandler.ID_POSTAZIONE)));
					} catch (Exception e) {
						LOGGER.error("Errore nella firma dell'IdPostazione");
						e.printStackTrace();
						throw new IOException(e);
					}
					
        			idPostazioneFirmatoValue.add(pkcs7);
        			idPostazioneFirmato.setAttributeValues(idPostazioneFirmatoValue);
	                
	                AttributeBean idApplicazione = new AttributeBean();
	                idApplicazione.setQualifiedName("IdApplicazione");
	                idApplicazione.setNameFormat(SAML2Constants.ATTRNAME_FORMAT_UNSPECIFIED);
                    List<Object> idApplicazioneValue = new ArrayList<>();
                    idApplicazioneValue.add(ConfigHandler.getParam(ConfigHandler.ID_APPLICAZIONE));
                    idApplicazione.setAttributeValues(idApplicazioneValue);
	                
	                List<AttributeBean> attributeList = new ArrayList<AttributeBean>();
	                attributeList.add(idSede);
	                attributeList.add(idPostazione);
	                attributeList.add(idPostazioneFirmato);
	                attributeList.add(idApplicazione);
	                
	                AttributeStatementBean attrBean = new AttributeStatementBean();
	                attrBean.setSamlAttributes(attributeList);
	                callback.setAttributeStatementData(Collections.singletonList(attrBean));
	    
	                
	                // Data for signer
	                callback.setIssuerCrypto(crypto);
	                callback.setIssuerKeyName(ConfigHandler.getParam(ConfigHandler.ALIAS_KEYSTORE_COMUNE));
	                callback.setIssuerKeyPassword(ConfigHandler.getParam(ConfigHandler.PASS_KEYSTORE_COMUNE));
	                callback.setSignAssertion(true);
	            } else {
	                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
	            }
	        }
	    }
}	    