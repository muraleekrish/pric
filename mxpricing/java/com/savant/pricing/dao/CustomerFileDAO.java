/*
 * 
 * CustomerFileDAO.java    Aug 7, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-0 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
 */
package com.savant.pricing.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.CustomerFilesVO;

/**
 * 
 */
public class CustomerFileDAO
{
    private static Logger logger = Logger.getLogger(CustomerFileDAO.class);
    
    public boolean addOrupdateCustomerFile(CustomerFilesVO objCustomerFilesVO, byte[] fileContent) throws SQLException
    {
        Session objSession = null;
        boolean updateResult = false;
        CallableStatement cstmnt = null;
        try
        {
            logger.info("ADDING OR UPDATING CUSTOMER FILE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call UploadFile (?,?,?,?,?,?)}");
            cstmnt.setInt(1, objCustomerFilesVO.getProspectiveCust().getProspectiveCustomerId());
            cstmnt.setString(2, objCustomerFilesVO.getFileName());
            cstmnt.setInt(3, objCustomerFilesVO.getFileType().getFileTypeIdentifier());
            cstmnt.setBytes(4,fileContent);
            cstmnt.setString(5,objCustomerFilesVO.getCreatedBy());
            cstmnt.setString(6,objCustomerFilesVO.getDescription());
            cstmnt.execute();
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("CUSTOMER FILE IS UPDATED");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD OR UPDATE THE CUSTOMER FILE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            if(e.getCause().getMessage().startsWith("Cannot insert duplicate key row in object 'PRC_Cust_files"))
               throw new HibernateException("Cannot insert duplicate key row in object 'PRC_Cust_files'");
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING ADD OR UPDATE THE CUSTOMER FILE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if(cstmnt != null)
            {
                cstmnt.close();
            }
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return updateResult;
    }
    
    public byte[] getFileAlt(int custId, String fileName) throws SQLException
    {
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs =null;
        byte[] byteVal = {0};
        try
        {
            logger.info("GETTING FILE ALT BY CUSTOMER ID AND FILE NAME");
            objSession = HibernateUtil.getSession();
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call GetFile (?,?)}");
            cstmnt.setInt(1,custId);
            cstmnt.setString(2,fileName);
            rs = cstmnt.executeQuery();
            while(rs.next())
            {
                byteVal = rs.getBytes("File_Content");
            }
            logger.info("GOT FILE ALT BY CUSTOMER ID AND FILE NAME");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET FILE BY CUSTOMER ID AND FILE NAME", e);
            e.printStackTrace();
        }
        finally
        {
            if(cstmnt != null)
            {
                cstmnt.close();
            }
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return byteVal;
    }

    public boolean deleteCustomerFile(CustomerFilesVO objCustomerFilesAltVO) throws SQLException
    {
        Session objSession       = null;
        boolean updateResult     = false;
        CallableStatement cstmnt = null;
        try
        {
            logger.info("DELETING CUSTOMER FILE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call DeleteFile(?,?,?)}");
            cstmnt.setInt(1, objCustomerFilesAltVO.getProspectiveCust().getProspectiveCustomerId());
            cstmnt.setString(2, objCustomerFilesAltVO.getFileName());
            cstmnt.setInt(3,objCustomerFilesAltVO.getFileType().getFileTypeIdentifier());
            cstmnt.execute();
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("CUSTOMER FILE IS DELETED");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE CUSTOMER FILE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        catch (SQLException e) 
        {
            logger.error("SQL EXCEPTION DURING DELETE THE CUSTOMER FILE", e);
            e.printStackTrace();
        }
        finally
        {
            if(cstmnt != null)
            {
                cstmnt.close();
            }
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return updateResult;
    }
    
    public List getFile(int prospectiveCustId,String fileName,int fileTypeId)
    {
        Session objSession = null;
        List lstresult = null;
        Criteria objcriteria = null;
        try
        {
            logger.info("GETTING FILE BY CUSTOMER ID, FILE NAME AND FILE TYPE ID");
            objSession = HibernateUtil.getSession();
            objcriteria = objSession.createCriteria(CustomerFilesVO.class);
            objcriteria.add(Restrictions.eq("prospectiveCust.prospectiveCustomerId", new Integer(prospectiveCustId))).add(Restrictions.like("fileName",fileName)).add(Restrictions.like("fileType.fileTypeIdentifier",new Integer(fileTypeId)));
            lstresult = objcriteria.list();
            logger.info("GOT FILE BY CUSTOMER ID, FILE NAME AND FILE TYPE ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET FILE BY CUSTOMER ID, FILE NAME AND FILE TYPE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lstresult;
    }
    
    public List getFileTypesByCustomer(int prospectiveCustId)
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING FILE TYPES BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select distinct(filesVO.fileType.fileTypeIdentifier) from CustomerFilesVO as filesVO where filesVO.prospectiveCust.prospectiveCustomerId = ? ").setInteger(0, prospectiveCustId).list();
            logger.info("GOT FILE TYPES BY CUSTOMER ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET FILE TYPES BY CUSTOMER ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    
    public List getAllFilesByFiletypes(int prospectiveCustId,int filetypeId)
    {
        List lstCust = null;
        Session objSession = null;
        Criteria objcriteria = null;
        try
        {
            logger.info("GETTING ALL FILES BY CUSTOMER ID AND FILE TYPES");
            objSession = HibernateUtil.getSession();
            objcriteria = objSession.createCriteria(CustomerFilesVO.class);
            objcriteria.add(Restrictions.eq("prospectiveCust.prospectiveCustomerId", new Integer(prospectiveCustId)));
            objcriteria.add(Restrictions.eq("fileType.fileTypeIdentifier",new Integer(filetypeId)));
            lstCust = objcriteria.list();
            logger.info("GOT ALL FILES BY CUSTOMER ID AND FILE TYPES");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL FILES BY CUSTOMER ID AND FILE TYPES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lstCust;
    }
    
    public LinkedHashMap getAllFilesbyCustomer(int prospectiveCustId)
    {
        logger.info("GETTING ALL FILES BY CUSTOMER ID");
        LinkedHashMap lhm = new LinkedHashMap();
        List lstfileTypes = getFileTypesByCustomer(prospectiveCustId);
        Iterator ite = lstfileTypes.iterator();
        while(ite.hasNext())
        {
            int fileTypeId = ((Integer)ite.next()).intValue();
            List files = getAllFilesByFiletypes(prospectiveCustId,fileTypeId);
            lhm.put(new Integer(fileTypeId),files);
        }
        logger.info("GOT ALL FILES BY CUSTOMER ID");
        return lhm;
    }
    
    public static void main(String[] args)
    {
        try
        {
        CustomerFileDAO objCustomerFileDAO = new CustomerFileDAO();
        //System.out.println(objCustomerFileDAO.getAllFilesbyCustomer(485));
        /*CustomerFilesVO objCustomerFilesVO = new CustomerFilesVO();
        objCustomerFilesVO = objCustomerFileDAO.getFile(485,"Input Master table.xls");
        File dsd=new File("d:\\"+objCustomerFilesVO);
        dsd.createNewFile();
        
        FileOutputStream testfile = new FileOutputStream("d:\\Test1.xls");
        testfile.write(objCustomerFilesVO.getFileContent());*/
        
        /*FileOutputStream testfile = null;
        byte[] res =  objCustomerFileDAO.getFileAlt(650,"pricing.war");
        testfile = new FileOutputStream("d://jothi.war");
        testfile.write(res);*/
        /*byte[] fileData = {0,1};
        System.out.println("before import");
        
        CustomerFilesVO objCustomerFilesVO = new CustomerFilesVO();
        objCustomerFilesVO.setCreatedBy("jothi");
        objCustomerFilesVO.setCreatedDate(new Date());
        objCustomerFilesVO.setDescription("testing");
        objCustomerFilesVO.setFileName("CDR PIZZA INN.xls");
        ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
        objProspectiveCustomerVO.setProspectiveCustomerId(485);
        CustomerFileTypesVO objCustomerFileTypesVO = new CustomerFileTypesVO();
        objCustomerFileTypesVO.setFileTypeIdentifier(2);
        objCustomerFilesVO.setProspectiveCust(objProspectiveCustomerVO);
        objCustomerFilesVO.setFileType(objCustomerFileTypesVO);
        objCustomerFilesVO.setFileContent(fileData);
        objCustomerFileDAO.addOrupdateCustomerFile(objCustomerFilesVO);
        System.out.println("added");
        */
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
/*
 *$Log: CustomerFileDAO.java,v $
 *Revision 1.2  2008/11/21 09:46:19  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.1  2007/12/07 06:18:35  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/10/30 05:51:43  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:17  jnadesan
 *initail MXEP commit
 *
 *Revision 1.10  2007/09/04 10:17:01  sramasamy
 *CallableStatement object is closed for addorupdatecustomer(), getfilealt() and deleteCustomerfile().
 *
 *Revision 1.9  2007/09/04 05:23:55  spandiyarajan
 *removed unwanted imports
 *
 *Revision 1.8  2007/09/03 14:09:42  sramasamy
 *Log message is added for log file.
 *
 *Revision 1.7  2007/08/23 07:27:53  jnadesan
 *imports organized
 *
 *Revision 1.6  2007/08/16 12:52:45  jnadesan
 *removed unwantde file
 *
 *Revision 1.5  2007/08/16 09:29:31  rraman
 *File checked for existence
 *
 *Revision 1.4  2007/08/14 13:15:49  jnadesan
 *procedure created to upload ,delete files
 *
 *Revision 1.3  2007/08/10 15:23:22  jnadesan
 *files updated if same files came
 *
 *Revision 1.2  2007/08/07 10:45:48  jnadesan
 *entry for customer file upload
 *
 *Revision 1.1  2007/08/07 10:02:53  jnadesan
 *entry for customer file upload
 *
 *
 */