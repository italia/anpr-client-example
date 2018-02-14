package it.interno.anpr.security.pki;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import it.interno.anpr.security.keystore.KeyStorePostazione;
import it.interno.anpr.security.keystore.KeystoreHandler;

/** Classe per la creazione della firma digitale 
 * @author gferraro
 *
 */
public class Signer {

	/** Generatore di firma in formato PKCS7 su una stringa di dati
	 * @param ks - Keystore
	 * @param data - stream di dati 
	 * @return stream firmato
	 * @throws Exception
	 */
	public static byte[] signPKCS7_BC(KeystoreHandler ks, String data) throws Exception {
		return signPKCS7_BC(ks, data.getBytes());
	}

	/** Generatore di firma in formato PKCS7 su una stringa di byte
	 * @param ks - Keystore
	 * @param stream- stream di dati
	 * @return stream firmato
	 * @throws Exception
	 */
	public static byte[] signPKCS7_BC(KeystoreHandler ks, byte[] stream) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		List certList = new ArrayList();
		CMSTypedData msg = new CMSProcessableByteArray(stream); // Data to sign

		X509CertificateHolder cert = new X509CertificateHolder(ks.getCertificate().getEncoded());
		certList.add(cert); // Adding the X509 Certificate

		Store certs = new JcaCertStore(certList);

		CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
		// Initializing the the BC's Signer
		ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA256withRSA").setProvider("BC")
				.build(ks.getPrivateKey());

		gen.addSignerInfoGenerator(
				new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
						.build(sha1Signer, cert));
		// adding the certificate
		gen.addCertificates(certs);
		// Getting the signed data
		CMSSignedData sigData = gen.generate(msg, true);

		return sigData.getEncoded();
	}


	/** Firma in formato PKCS7 dell'IdPostazione, con il certificato di Postazione
	 * @param item - String contentente Id Postazione
	 * @return stream firmato in PKCS7
	 * @throws Exception
	 */
	public static byte[] signPostazione(String item) throws Exception {
		KeyStorePostazione ksPostazione = new KeyStorePostazione();
		return Signer.signPKCS7_BC(ksPostazione, item);
	}
}
