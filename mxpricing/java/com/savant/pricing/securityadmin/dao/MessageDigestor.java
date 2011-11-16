/*
 * Created on Jun 28, 2005
 *
 * ClassName	:  	MessageDigestor.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India
 *
 */
package com.savant.pricing.securityadmin.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;



/**
 * @author dsomasundaram
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessageDigestor
{
    private static MessageDigestor instance;
    private static Logger logger = Logger.getLogger(MessageDigestor.class);
    
    public synchronized String encrypt(String plainText)
    {
        String hashedText = "";
        MessageDigest md;
        try
        {
            logger.info("ENCRYPTING THE PLAIN TEXT");
            md = MessageDigest.getInstance("SHA");
            md.update(plainText.getBytes("UTF-8"));
            byte hash[] = md.digest();
            hashedText = (new BASE64Encoder()).encode(hash);
            logger.info("PLAIN TEXT IS ENCRYPTED");
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error("NO SUCH ALGORITHM EXCEPTION DURING ENCRYPT THE PLAIN TEXT", e);
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error("UNSUPPORTED ENCODING EXCEPTION DURING ENCRYPT THE PLAIN TEXT", e);
            e.printStackTrace();
        }
        return hashedText;
    }

    public static synchronized MessageDigestor getInstance()
    {
        if(instance == null)
            instance = new MessageDigestor();
        return instance;
    }
}

/*
*$Log: MessageDigestor.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/08/31 14:50:19  sramasamy
*Log message is added for log file.
*
*Revision 1.1  2007/02/23 05:11:28  srajappan
*security admin DAO added
*
*Revision 1.2  2006/07/31 13:10:46  srajappan
*empty block removed
*
*Revision 1.1  2006/05/23 04:29:43  srajappan
*password encryption method
*
*Revision 1.1  2005/12/20 10:06:08  srajappan
*Initial Commit of TriEaglePortal
*
*Revision 1.1  2005/06/29 13:09:40  dsomasundaram
*Class to obtain hash value for plaintext
*
*
*/