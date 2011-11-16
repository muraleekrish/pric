/*
 * 
 * MMPricingServlet.java    Aug 21, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
*/

package com.savant.pricing.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 */
public class MMPricingServlet extends HttpServlet
{
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletOutputStream servletoutputstream = null;
        try
        {
            File file = new File("D:////pricingdata//jasper//PriceMatrix.pdf");
            byte[] bytes = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytes);
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found.");
                e.printStackTrace();
            }
            catch (IOException e1)
            {
                System.out.println("Error Reading The File.");
                e1.printStackTrace();
            }
            response.setHeader("Content-Disposition", "attachment;filename=\"" + "MMPrice.pdf" + "\";");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            servletoutputstream = response.getOutputStream();
            servletoutputstream.write(bytes, 0, bytes.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            servletoutputstream.flush();
            servletoutputstream.close();
        }
         
    }
}


/*
*$Log: MMPricingServlet.java,v $
*Revision 1.2  2008/11/21 09:47:11  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/08/23 07:38:06  jnadesan
*file name validated
*
*Revision 1.1  2007/08/21 06:17:53  jnadesan
*Initial commit
*
*
*/