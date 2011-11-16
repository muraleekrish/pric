/*
 * Created on Aug 13, 2008
 *
 * ClassName	:  	EX13A.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.inputs.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
class EX13A
{
   public static void main(String args[])
    {
        try{
            FileInputStream  in = null;
            FileOutputStream out = null;
            FilePermission fp = null;
            try {
                in = new FileInputStream("C:\\Documents and Settings\\tannamalai\\Desktop\\Savant Sample Epsilon and NG Tabs 20080110.xls");
                out =new FileOutputStream("C:\\Documents and Settings\\tannamalai\\Desktop\\sa.xls");
                int c;
                while (((c = in.read()) != -1) ) {
                    out.write(c);
                }
                fp = new FilePermission("C:\\Documents and Settings\\tannamalai\\Desktop\\sa.xls", "execute");
               
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
}
}


/*
*$Log: EX13A.java,v $
*Revision 1.1  2008/11/17 10:03:46  tannamalai
*final commit
*
*
*/