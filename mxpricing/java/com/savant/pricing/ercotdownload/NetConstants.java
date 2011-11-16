/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	NetConstants.java
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
public class NetConstants
{
    // Secure Socket Setup constants
    public static String SOCKET_SSL_CONTEXT     = "SSL";
    public static String SOCKET_SSL_KEY_FACTORY = "SunX509";
    public static String SOCKET_SSL_KEY_STORE   = "pkcs12";
    public static String SOCKET_SSL_HANDLER_TAG
        = "java.protocol.handler.pkgs";
    public static String SOCKET_SSL_HANDLER_VALUE
        = "com.sun.net.ssl.internal.www.protocol" ;
    
    // http protocol version
    public static String HTTP_VERION_1_1 = "HTTP/1.1";
    public static String HTTP_VERION_1_0 = "HTTP/1.0";
    
    // http connection methods
    public static String METHOD_GET  = "GET";
    public static String METHOD_POST = "POST";
    
    // ports
    public static int PORT_HTTP  = 80;
    public static int PORT_HTTPS = 443;
    
    // cipher suites
    public static String CS_SSL_RSA_3DES_EDE_CBC_SHA = "SSL_RSA_WITH_3DES_EDE_CBC_SHA";
        
    // defaults
    public static int    DEFAULT_PORT         = PORT_HTTPS;
    public static String DEFAULT_CIPHER_SUITE = CS_SSL_RSA_3DES_EDE_CBC_SHA;
    public static String DEFAULT_METHOD       = METHOD_POST;
    public static String DEFAULT_HTTP_VERSION = HTTP_VERION_1_1;
    
    // Certificate file
    public static String PROP_CERT_FILE = System.getProperty( "java.home" )      +
                                          System.getProperty( "file.separator" ) +
                                          "lib"                                  +
                                          System.getProperty( "file.separator" ) +
                                          "client.cert.properties";
    
    public static String PROP_NAME_KEY_STORE          = "key.store";
    public static String PROP_NAME_KEY_STORE_ESIID_SEARCH    = "key.store.esiid";
    public static String PROP_NAME_KEY_STORE_PASSCODE = "key.store.passcode";
}

/*
*$Log: NetConstants.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/07 12:11:47  rraman
*new package and classes added for esiid download
*
*
*/