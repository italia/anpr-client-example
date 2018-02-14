package it.interno.anpr.security.ssl;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/** Implementazione di un Trust Manager
 * @author gferraro
 *
 */
public class TrustAllX509TrustManager implements X509TrustManager
{

	/** Empty array of certificate authority certificates. */
	private static final X509Certificate[] acceptedIssuers = new X509Certificate[]{ };
	
	/**
	 * Always trust for client SSL chain peer certificate chain with any authType authentication types.
	 *
	 * @param chain the peer certificate chain.
	 * @param authType the authentication type based on the client certificate.
	 */
	public void checkClientTrusted( X509Certificate[] chain, String authType )
	{}
	
	/**
	 * Always trust for server SSL chain peer certificate chain with any authType exchange algorithm types.
	 *
	 * @param chain the peer certificate chain.
	 * @param authType the key exchange algorithm used.
	 */
	public void checkServerTrusted( X509Certificate[] chain, String authType )
	{}
	
	/**
	 * Return an empty array of certificate authority certificates which are trusted for authenticating peers.
	 *
	 * @return a empty array of issuer certificates.
	 */
	public X509Certificate[] getAcceptedIssuers()
	{
	    return ( acceptedIssuers );
	}
}

