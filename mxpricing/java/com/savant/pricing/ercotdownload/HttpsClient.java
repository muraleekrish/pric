/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	HttpsClient.java
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * The HttpsClient class provides services to enable a HTTPS connection.
 *
 * <PRE>
 * <IMG SRC = /images/javadoclogo.gif></IMG>
 * </PRE>
 *
 * 2000 Andersen Consulting LLP. All Rights Reserved.<BR>
 * Confidential Information of Andersen Consulting LLP.<BR><BR>
 *
 * @author:   Brian Lucas
 * @version:  1.0
 *
 * <PRE>
 * Revision History:
 * Date             Revised By      Description of Change
 * ----------       ----------      -----------------------------------------
 * 09/23/2000       Brian Lucas     Reorganized
 * 01/03/2001       Brian Lucas     Added User-Agent
 * </PRE>
 */
public class HttpsClient
{
    private Socket      m_SSLSocket;
    private URL         m_Url;
    private String      m_KeyStoreFile;
    private String      m_KeyStorePasscode;
    private boolean     m_ResponseHeaderRead = false;
    private String      m_ServerStatus       = new String( "" );
    private Hashtable   m_ResponseHeaders    = new Hashtable();    
    private Hashtable   m_RequestHeaders     = new Hashtable();
    private String      m_Method             = NetConstants.DEFAULT_METHOD;    
    private String      m_CipherSuite        = NetConstants.DEFAULT_CIPHER_SUITE;
    private String      CR                   = System.getProperty( "line.separator" );        

    /**
      * The constructor creates a SecureSocket class, which will supply the
      * SSL capabilites for the HTTPS client.
      *
      * @param     url target address.
      * @throws    SecurityException
      * @throws    IOException
      */ 	
    public HttpsClient( URL url ) throws SecurityException, IOException
    {
        m_Url = url;
    }

    /**
      * Returns the status line returned by the server
      *
      * @return    Server's status line.
      */ 	
    public String getServerStatus()
    {
        return m_ServerStatus;
    }
    
    /**
      * Returns the reply 'value' associated with the input 'key'
      *
      * @param     key Unique HTTP header 'tag'.
      * @return    'Value' associated with input 'key'.
      */ 	
    public String getHeader( String key )
    {
        return ( String ) m_ResponseHeaders.get( key );
    }
    
    /**
      * Returns all reply headers
      *
      * @return    All HTTP reply headers
      */ 	
    public Hashtable getAllHeaders()
    {
        return m_ResponseHeaders;
    }
    
    /**
      * Add the input 'key' and 'value' pairs to the request header
      *
      * @param     key    Unique HTTP header 'tag'.
      * @param     value 'Value' associated with input 'key'.
      */ 	
    public void setRequestHeader( String key, String value )
    {
        m_RequestHeaders.put( key, value );
    }
    
    /**
      * Overrides the default request method
      *
      * @param     method HTTP request type.  Either 'POST' or 'GET'.
      */ 	
    public void setRequestMethod( String method )
    {
        m_Method = method;
    }

    /**
      * Overrides the default cipher suite
      *
      * @param     CipherSuite to use for connection.
      */ 	
    public void setCipherSuite( String inCipherSuite )
    {
        m_CipherSuite = inCipherSuite;
    }
    
    /**
      * Returns the cipher suite
      *
      * @return    CipherSuite being used for current connection.
      */ 	
    public String getCipherSuite()
    {
        return m_CipherSuite;
    }

    /**
      * Sets the keystore file to use for ssl processing.
      *
      * @param     inKeyStoreFile KeyStore file location.
      */ 	
    public void setKeyStoreFile( String inKeyStoreFile )
    {
        m_KeyStoreFile = inKeyStoreFile;
    }
    
    /**
      * Returns the keystore file being used by ssl
      *
      * @return    KeyStore file location. 
      */ 	
    public String getKeyStoreFile()
    {
        return m_KeyStoreFile;
    }

    /**
      * Sets the keystore passcode
      *
      * @param     inKeyStorePasscode Password for KeyStore file.
      */ 	
    public void setKeyStorePasscode( String inKeyStorePasscode )
    {
        m_KeyStorePasscode = inKeyStorePasscode;
    }
    
    /**
      * Returns the keystore password
      *
      * @return    Password for KeyStore file.   
      */ 	
    public String getKeyStorePasscode()
    {
        return m_KeyStorePasscode;
    }

    /**
      *  This method initiates ssl connection to host
      * 
      * @throws    IOException
      */ 	
    public void connect() throws IOException
    {
        // create connection
        SecureSocket ss = new SecureSocket();
        
        // open connection
        ss.openSecureConnection( m_Url.getHost(),
                                 getCipherSuite(),
                                 getKeyStoreFile(),
                                 getKeyStorePasscode() );
        m_SSLSocket = ss.getSecureConnection();
        
        // submit request
        OutputStream os = getOutputStream();
        os.write( formatHTTPHeader().getBytes() );
    }
    
    /**
      * Method used to parse reply header information including server status
      */ 	
    private void readResponseHeader( InputStream is ) throws IOException
    {
        int     c;
        String  headerLine = new String();
        boolean go = true;            

        // first line is server status info
        while(( c =  is.read() ) != 13 )
        {
            m_ServerStatus = m_ServerStatus.concat( String.valueOf(( char ) c ));
        }

        // reade 'lf' after 'cr'
        c =  is.read();
        
        while( go )
        {
            // read nex character
            c =  is.read();

            // if 'cr' found, header info is over
            if( c != 13 )
            {
                // char is part of header
                headerLine = headerLine.concat( String.valueOf(( char ) c ));
                
                // read until 'cr' is found
                while(( c =  is.read() ) != 13 )
                {
                    headerLine = headerLine.concat( String.valueOf(( char ) c ));
                }
            
                // reade 'lf' after 'cr'
                c =  is.read();

                // split header into key-value pair and store
                int index = headerLine.indexOf( ":" );
                m_ResponseHeaders.put( headerLine.substring( 0, index ),
                                       headerLine.substring( index + 1 ).trim() );
                
                // init var for next header
                headerLine = "";
            }
            
            else
            {
                // reade 'lf' after 'cr'
                c =  is.read();                
                
                go = false;
            }
        }
    }

    /**
      *  This method returns the output stream for the HTTP request
      *
      * @return    Stream object used to 'write' request.
      * @throws    IOException
      */ 	
    public OutputStream getOutputStream() throws IOException
    {
        return m_SSLSocket.getOutputStream();
    }

    /**
      *  This method returns the input stream for the HTTP reply
      *
      * @return    is  Stream object used to 'read' response.
      * @throws    IOException
      */ 	
    public InputStream getInputStream() throws IOException
    {
        InputStream is = m_SSLSocket.getInputStream();
        
        if( !m_ResponseHeaderRead )
        {
            m_ResponseHeaderRead = true;
            readResponseHeader( is );
        }
        
        return is;
    }

    /**
      * This method creates the initial HTTP request line and the standard
      * HTTP/1.1 header line.
      * 
      * @return    HTTP Header
      */ 	
    private String formatHTTPHeader()
    {
        // format http request line
        String header = m_Method + " " + m_Url.getFile() + " " + NetConstants.DEFAULT_HTTP_VERSION + CR +
                        "HOST: " + m_Url.getHost() + ":" + NetConstants.DEFAULT_PORT + CR;
      
        // add user agent
        if( !m_RequestHeaders.containsKey( "User-Agent" ))
        {
            // retrieve system props
            Properties props = System.getProperties();
            
            // add user agent header
            m_RequestHeaders.put( "User-Agent",
                                  "Accenture Programmatic Client (1.0; " + 
                                  props.get( "os.name" ) + " " + 
                                  props.get( "os.version" ) + "; " +
                                  getClass().getName() + ")" );
            m_RequestHeaders.put( "Legal-Notice", 
                                  "Property of Accenture - All Rights Reserved" );
        }
        
        // add custom http headers
        Enumeration e = m_RequestHeaders.keys();
        while( e.hasMoreElements() )
        {
            String key = ( String ) e.nextElement();
            header = header.concat( key + ": " + 
                                    ( String ) m_RequestHeaders.get( key ) + CR );
        }
        
        return header + CR;
    }
}


/*
*$Log: HttpsClient.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.1  2007/04/07 12:11:47  rraman
*new package and classes added for esiid download
*
*
*/