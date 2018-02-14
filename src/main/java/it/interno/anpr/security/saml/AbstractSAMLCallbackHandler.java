package it.interno.anpr.security.saml;

import java.security.cert.X509Certificate;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.saml.bean.ConditionsBean;
import org.apache.wss4j.common.saml.bean.KeyInfoBean.CERT_IDENTIFIER;
import org.apache.wss4j.common.saml.bean.SubjectConfirmationDataBean;
import org.joda.time.DateTime;
import org.w3c.dom.Element;

/**
 * Implementazione di base di un Callback Handler per la creazione di un
 * assertion SAML
 * 
 * @author gferraro
 *
 */
public abstract class AbstractSAMLCallbackHandler implements CallbackHandler {

	public enum Statement {
		AUTHN, ATTR, AUTHZ
	}

	protected String subjectName = null;
	protected String subjectQualifier = null;
	protected String confirmationMethod = null;
	protected X509Certificate[] certs;
	protected Statement statement = Statement.AUTHN;
	protected CERT_IDENTIFIER certIdentifier = CERT_IDENTIFIER.X509_CERT;
	protected byte[] ephemeralKey = null;
	protected String issuer = null;
	protected String subjectNameIDFormat = null;
	protected String subjectLocalityIpAddress = null;
	protected String subjectLocalityDnsAddress = null;
	protected DateTime sessionNotOnOrAfter = null;
	protected DateTime authenticationInstant;

	public DateTime getAuthenticationInstant() {
		return authenticationInstant;
	}

	public void setAuthenticationInstant(DateTime authenticationInstant) {
		this.authenticationInstant = authenticationInstant;
	}

	protected String resource = null;
	protected List<Object> customAttributeValues = null;
	protected ConditionsBean conditions = null;
	protected SubjectConfirmationDataBean subjectConfirmationData = null;
	private Crypto issuerCrypto;
	private String issuerName;
	private String issuerPassword;
	private Element assertionAdviceElement;

	public void setSubjectConfirmationData(SubjectConfirmationDataBean subjectConfirmationData) {
		this.subjectConfirmationData = subjectConfirmationData;
	}

	public void setConditions(ConditionsBean conditionsBean) {
		this.conditions = conditionsBean;
	}

	public void setConfirmationMethod(String confMethod) {
		confirmationMethod = confMethod;
	}

	public void setSessionNotOnOrAfter(DateTime sessionNotOnOrAfter) {
		this.sessionNotOnOrAfter = sessionNotOnOrAfter;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public void setCertIdentifier(CERT_IDENTIFIER certIdentifier) {
		this.certIdentifier = certIdentifier;
	}

	public void setCerts(X509Certificate[] certs) {
		this.certs = certs;
	}

	public byte[] getEphemeralKey() {
		return ephemeralKey;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public void setSubjectNameIDFormat(String subjectNameIDFormat) {
		this.subjectNameIDFormat = subjectNameIDFormat;
	}

	public void setSubjectLocality(String ipAddress, String dnsAddress) {
		this.subjectLocalityIpAddress = ipAddress;
		this.subjectLocalityDnsAddress = dnsAddress;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setCustomAttributeValues(List<Object> customAttributeValues) {
		this.customAttributeValues = customAttributeValues;
	}

	

	

	public Crypto getIssuerCrypto() {
		return issuerCrypto;
	}

	public void setIssuerCrypto(Crypto issuerCrypto) {
		this.issuerCrypto = issuerCrypto;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getIssuerPassword() {
		return issuerPassword;
	}

	public void setIssuerPassword(String issuerPassword) {
		this.issuerPassword = issuerPassword;
	}

	public Element getAssertionAdviceElement() {
		return assertionAdviceElement;
	}

	public void setAssertionAdviceElement(Element assertionAdviceElement) {
		this.assertionAdviceElement = assertionAdviceElement;
	}
}