/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	SecureSocket.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.ercotdownload;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.savant.pricing.common.BuildConfig;

/**
 * The SecureSocket class provides services to enable a SSL connection.
 *
 * <PRE>
 * <IMG SRC = ../ac_logo.gif></IMG>
 * </PRE>
 *
 * 2000 Andersen Consulting LLP. All Rights Reserved.<BR>
 * Confidential Information of Andersen Consulting LLP.<BR><BR>
 *
 * @author:   Mike Evans
 * @version:  1.0
 *
 */
public class SecureSocket 
{
    private SSLSocket sslSocket;

    /**
    *  The class constructor creates establishes the SSL protocol handler.
    *
    * @return    void.
    */
    public SecureSocket()
    {
        setSSLHandler();
    }

    /**
    * This method registers HTTP as the application provider for the SSL
    * protocol and loads the cryptographic provider (i.e., the classes that
    * implement the cryptographic algorithms used by SSL).  This code sample
    * uses the reference classes supplied by Sun through the JSSE packages.
    * These are not production ready classes and developers should replace them
    * with third party providers that support the JSSE interfaces.
    *
    * @return    void.
    */
    private void setSSLHandler()
    {
        // register https protocol with sun's ssl protocol handler
        System.getProperties().put( NetConstants.SOCKET_SSL_HANDLER_TAG,
                                    NetConstants.SOCKET_SSL_HANDLER_VALUE );

        // load and register sun's java cryptography extensions
        java.security.Security.addProvider(
            new com.sun.net.ssl.internal.ssl.Provider() );
     }

    /**
    *  This metod constructor creates the secure socket connection, sets the
    *  ciphersuite and loads in the user digital certificate to enable client
    *  authentication.
    *
    * @param     hostname               the main element of the website domain
    *                                   name (e.g., ercot of the www.ercot.com
    *                                   website)
    *
    * @param     ciphersuite            the cryptographic algorithms used for
    *                                   the public key encryption (RSA), message
    *                                   encryption (3DES) and message signature
    *                                   (SHA-1) that the SSL handshake expects.
    *                                   (for ERCOT, this parameter is
    *                                   SSL_RSA_WITH_3DES_EDE_CBC_SHA)
    *
    * @param     keystorefile           the encrypted file that contains the
    *                                   user digital certificate and associated
    *                                   private key.  The certificate with the
    *                                   private key is exported from the browser
    *                                   in a PKCS#12 format.
    *
    * @param     keystorepasscode       the password used to protect the
    *                                   private key during the export process
    *                                   from the browser.
    * @return    boolean.
    * @throws    IOException
    */
    public void openSecureConnection( String hostName,
                                      String cipherSuite,
                                      String keyStoreFile,
                                      String keyStorePasscode) throws IOException
    {
   	    // Load in your client certificate through key manager
        // to enable client authentication
   	    SSLSocketFactory factory = null;
   		SSLContext sslContext;
        KeyManagerFactory keyManagerFactory;
        KeyStore keyStore;
   		char[] passphrase = keyStorePasscode.toCharArray();

   	    try
        {
       		sslContext = SSLContext.getInstance( NetConstants.SOCKET_SSL_CONTEXT);
            keyManagerFactory = KeyManagerFactory.getInstance(NetConstants.SOCKET_SSL_KEY_FACTORY);
       		keyStore = KeyStore.getInstance(NetConstants.SOCKET_SSL_KEY_STORE);
       		if(BuildConfig.DMODE)
       		    System.out.println("KeyStoreFile : "+keyStoreFile);
       		
       		//TODO
       		//keyStoreFile = "d:/ercot-tasks/ercotcert/bala.pfx";
       		keyStore.load(new FileInputStream(keyStoreFile), passphrase);
       		keyManagerFactory.init(keyStore, passphrase);
       		sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            throw new IOException(e.getMessage());
        }

        // Connect to the requested web server.
   		factory = sslContext.getSocketFactory();
       	sslSocket = (SSLSocket)factory.createSocket( hostName,
                                                     NetConstants.PORT_HTTPS );

        // Enable the requested cipherSuite
		java.lang.String[] cipherArray = {cipherSuite};
        sslSocket.setEnabledCipherSuites(cipherArray);
        
        // Force the SSL handshake.
        sslSocket.startHandshake();        
    }

    /**
    * This method performs the SSL handshake and returns the secure socket
    * for the calling program to use.
    *
    * @return    SSLSocket - the negotiated secure socket.
    * @throws    IOException
    */
    public SSLSocket getSecureConnection()throws IOException
    {
        // Return the negotiated secure socket
        return sslSocket;
    }
}

/*
*$Log: SecureSocket.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.1  2007/04/07 12:11:47  rraman
*new package and classes added for esiid download
*
*
*/