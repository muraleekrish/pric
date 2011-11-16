/*
 * Created on Apr 26, 2007
 * 
 * Class Name MailManager.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.mailer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.savant.pricing.calculation.valueobjects.PriceRunCustomerProductsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerTermsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.MXenergyPriceRun;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.PreferenceProductsDAO;
import com.savant.pricing.dao.PreferenceTermsDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;
import com.savant.pricing.transferobjects.TeamDetails;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MailManager {
    
    private Properties props;
    private boolean successImage = false;
    private boolean failureImage = false;
    private boolean mxenergyLogo = false;
    
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat formatInside = new SimpleDateFormat("yyyy-MM-dd h:mm a");
    private void init()
    {
        try
        {
            this.props = new Properties();
    		InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
    		this.props.load(is);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public MailManager()
    {
        this.init();
    }
    private String mailContent(List objPriceRunCustomerRef)
    {
        String content =  "<table width='881'  border='0' cellspacing='0' cellpadding='3'><tr align='center'>" +
        "<td width='60' class='list_data' bgcolor='#808080' style='color:#FFFFFF'><b>S.&nbsp;No</b></td>" +
        "<td width='157' class='list_data' bgcolor='#808080' style='color:#FFFFFF'><b>Customer Name</b> </td>" +
        "<td width='90' class='list_data' bgcolor='#808080' style='color:#FFFFFF'><b>No. of&nbsp;ESIIDs</b></td>" +
        "<td width='115' class='list_data'bgcolor='#808080' style='color:#FFFFFF'><b>Products</b></td>" +
        "<td width='70' class='list_data' bgcolor='#808080' style='color:#FFFFFF'><b>Term </b></td>" +
        "<td width='64' class='list_data' bgcolor='#808080' style='color:#FFFFFF'><b>Status</b></td>" +
        "<td width='113' class='list_data' bgcolor='#808080' style='color:#FFFFFF'><b>Sales Rep</b></td>" +
        "<td width='134' class='list_data' bgcolor='#808080' style='color:#FFFFFF'><b>Sales Manager</b></td>" +
        "</tr></table><div style='overflow:auto;height:300px;width:900px' id='divList'>" +
        "<table width='881'  border='0' cellspacing='0' cellpadding='3'> ";
        
        //AutoPriceRun objAutoPriceRun = new AutoPriceRun();
        //List lstCustomers = (List)objAutoPriceRun.lastAutomaticRunCustomers();
        PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
        PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
        PriceRunCustomerTermsVO objPriceRunCustomerTermsVO =null;
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        PriceRunCustomerProductsVO objPriceRunCustomerProductsVO = null; 
        
        TeamDetails objTeamDetails = null;
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        
        Iterator itePriceRunCustRef = objPriceRunCustomerRef.iterator();
        PICDAO objPICDAO = new PICDAO();
        
        System.out.println("list of customer size : " + objPriceRunCustomerRef.size());
        int i = 1;
        while(itePriceRunCustRef.hasNext())
        {
            int priceRunCustomerRefId = ((Integer)itePriceRunCustRef.next()).intValue();
            List ltspreferenceproducts = (List)objPreferenceProductsDAO.getAllPreferenceProducts(priceRunCustomerRefId);
            List lstPreferenceTerms = (List)objPreferenceTermsDAO.getAllPreferenceTerms(priceRunCustomerRefId);
            PriceRunCustomerVO objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(priceRunCustomerRefId);
            String strProd = "";
            for(int k=0;k<ltspreferenceproducts.size();k++)
            {
                objPriceRunCustomerProductsVO = (PriceRunCustomerProductsVO)ltspreferenceproducts.get(k);
                if(strProd.equals(""))
                    strProd = String.valueOf(objPriceRunCustomerProductsVO.getProduct().getProductName()).trim();
                else
                    strProd +=", "+ String.valueOf(objPriceRunCustomerProductsVO.getProduct().getProductName()).trim();
            }
            if(strProd.equalsIgnoreCase(""))
                strProd ="&nbsp;";
            String strTerm = "";
            for(int k=0;k<lstPreferenceTerms.size();k++)
            {
                objPriceRunCustomerTermsVO = (PriceRunCustomerTermsVO)lstPreferenceTerms.get(k);
                if(strTerm.equals(""))
                    strTerm = String.valueOf(objPriceRunCustomerTermsVO.getTerm()).trim();
                else
                    strTerm = strTerm+", "+ String.valueOf(objPriceRunCustomerTermsVO.getTerm()).trim();
            }
            if(strTerm.equalsIgnoreCase(""))
                strTerm ="&nbsp;";
            String str="";
            String strManager = "";
            
            System.out.println("customer name :"+objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName());
            objTeamDetails = objProspectiveCustomerDAO.getTeam(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
                       
            str=objTeamDetails.getSalesRep()==null?"":objTeamDetails.getSalesRep().getFirstName()+"&nbsp;";
            str+= objTeamDetails.getSalesRep().getLastName()==null?"":objTeamDetails.getSalesRep().getLastName();
            strManager = objTeamDetails.getSalesManager()==null?"":objTeamDetails.getSalesManager().getFirstName()+"&nbsp;";
            strManager += objTeamDetails.getSalesManager().getLastName()==null?"":objTeamDetails.getSalesManager().getLastName();
            
            String custName = objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName();
            if(strManager==null||strManager.equals(null)||strManager.equals(""))
                strManager = "&nbsp;";
            if(str==null||str.equals(null)||str.equals(""))
                str = "&nbsp;";
            str = str.replaceFirst("null","&nbsp;");
            strManager = strManager.replaceFirst("null","&nbsp;");
            content+="<tr><td width='60' height='36'align ='right' class='list_data'>"+(i++)+"</td>"+
            "<td width='160' class='list_data'align ='left'title='"+custName+"'>"+(custName.length()>26?custName.substring(0,23)+"...":custName)+"</td>" +
            "<td  width='93' class='list_data' align ='right' >"+objPICDAO.getNoOfESIID(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId())+"</td>" +
            "<td width='120' class='list_data' align ='left' title ='"+strProd+"'>"+(strProd.length()>22?strProd.substring(0,17)+"...":strProd)+"</td>" +
            "<td width='70'  class='list_data'align ='right' title ='"+strTerm+"'>"+(strTerm.length()>10?strTerm.substring(0,10)+"...":strTerm)+"</td>" ;
            if(objPriceRunCustomerVO.isRunStatus())
            {
                this.successImage = true;
                content+="<td width='64' class='list_data' align='center' title ='"+(objPriceRunCustomerVO.getReason()==null?"&nbsp;":objPriceRunCustomerVO.getReason())+"'><img src=\"cid:statusOn\" width='16' height='14'></td>" ;
            }
            else
            {
                this.failureImage = true;
                content+="<td width='64' class='list_data' align='center' title ='"+(objPriceRunCustomerVO.getReason()==null?"&nbsp;":objPriceRunCustomerVO.getReason())+"'><img src=\"cid:statusOff\" width='16' height='14'></td>";
            }
            content+="<td  width='113' class='list_data' align ='left' title ='"+str+"'>"+(str.length()>22?str.substring(0,20)+"...":str)+"</td>" +
            "<td  width='134' align ='left' class='list_data'title ='"+strManager+"' >"+(strManager.length()>25?strManager.substring(0,22)+"...":strManager)+"</td></tr>";
            
        }
        content +="</table></div>";
        return content;
    }
    
    
    private HashMap registerEmailIds()
    {
        HashMap hm = new HashMap();
        MXenergyPriceRun objMXenergyPriceRun = new MXenergyPriceRun();
        List lstCustomers = (List)objMXenergyPriceRun.lastAutomaticRunCustomers();
        Iterator iteCust = lstCustomers.iterator();
        while(iteCust.hasNext())
        {
            PriceRunCustomerVO objPriceRunCustomerVO = (PriceRunCustomerVO)iteCust.next();
            int prospectiveCustomerId = objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId();
            TeamDetails objTeamDetails = new ProspectiveCustomerDAO().getTeam(prospectiveCustomerId);
            
            UsersVO salesRep = objTeamDetails.getSalesRep();
            String key = "";
            UsersVO salesManager = objTeamDetails.getSalesManager();
            if(salesRep != null && salesManager!= null && !salesManager.equals(salesRep))
            {
                String emailSalesRep = salesRep.getEmailId();
                String salesRepName = salesRep.getFirstName();
                String salesRepUserId = salesRep.getUserId();
                if(emailSalesRep != null)
                {
                    key = emailSalesRep+","+"SalesRep"+","+salesRepName+","+salesRepUserId;
                    if(BuildConfig.DMODE)
                        System.out.println("Key Sales Rep:"+key);
                    if(hm.containsKey(key))
                    {
                        List objPriceRunCustomerRef = (List)hm.get(key);
                        objPriceRunCustomerRef.add(new Integer(objPriceRunCustomerVO.getPriceRunCustomerRefId()));
                    }
                    else
                    {
                        List objPriceRunCustomerRef = new LinkedList();
                        objPriceRunCustomerRef.add(new Integer(objPriceRunCustomerVO.getPriceRunCustomerRefId()));
                        hm.put(key, objPriceRunCustomerRef);
                    }
                }
            }
            
            UsersVO pricingAnalyst = objTeamDetails.getPricingAnalyst();
            if(salesManager != null && pricingAnalyst != null && !pricingAnalyst.equals(salesManager))
            {
                String emailSalesManager = salesManager.getEmailId();
                String salesManagerName = salesManager.getFirstName();
                String salesManagerUserId = salesManager.getUserId();
                if(emailSalesManager != null)
                {
                    key = emailSalesManager+","+"SalesManager"+","+salesManagerName+","+salesManagerUserId;
                    if(BuildConfig.DMODE)
                        System.out.println("Key SalesManager:"+key);
                    if(hm.containsKey(key))
                    {
                        List objPriceRunCustomerRef = (List)hm.get(key);
                        objPriceRunCustomerRef.add(new Integer(objPriceRunCustomerVO.getPriceRunCustomerRefId()));
                    }
                    else
                    {
                        List objPriceRunCustomerRef = new LinkedList();
                        objPriceRunCustomerRef.add(new Integer(objPriceRunCustomerVO.getPriceRunCustomerRefId()));
                        hm.put(key, objPriceRunCustomerRef);
                    }
                }
            }
        }
        if(BuildConfig.DMODE)
            System.out.println("Hash Map:"+hm);
        return hm;
    }
    
    private String getManagerEmailId(String userId)
    {
        UserDAO objUserDAO = new UserDAO();
        try
        {
            UsersVO objUsersVO = objUserDAO.getUsers(userId);
	        if(objUsersVO.getParentUser() != null)
	        {
	            return objUsersVO.getParentUser().getEmailId();
	        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
    private String getPricingAnalystEmailIds()
    {
        String to = this.props.getProperty("mail.pricinganalyst.address");
        return to;
    }
    private String getPricingCCEmailIds()
    {
        String cc = this.props.getProperty("mail.mxenergy.cc.address");
        return cc;
    }
    private String getPricingBCCEmailIds()
    {
        String bcc = this.props.getProperty("mail.mxenergy.bcc.address");
        return bcc;
    }
    private boolean sendMailSalesRep(String key, List contentList)
    {
        
        boolean isSent = false; 
        try
        {
            Mail mail = new Mail();
            String[] emailElmnts = key.split(",");
            String mailId = emailElmnts[0];
            String salesRepName = emailElmnts[2];
            String salesRepUserId = emailElmnts[3];
            
            String to = mailId;
            String cc = this.getManagerEmailId(salesRepUserId);
            String bcc = this.getPricingBCCEmailIds();
            
            boolean availRecipients = false;
            if(!to.trim().equals(""))
            {
                String[] toIds = to.trim().split(",");
                mail.addRecipients(toIds, "to");
                availRecipients = true;
            }
            if(!cc.trim().equals(""))
            {
                String[] ccIds = cc.trim().split(",");
                mail.addRecipients(ccIds, "cc");
                availRecipients = true;
            }
            if(!bcc.trim().equals(""))
            {
                String[] bccIds = bcc.trim().split(",");
                mail.addRecipients(bccIds, "bcc");
                availRecipients = true;
            }
            if(BuildConfig.DMODE)
            {
                System.out.println("To SalesRep:"+to);
                System.out.println("cc SalesRep:"+cc);
            }
            
            if(availRecipients)
            {
                String subject = this.props.getProperty("mail.defaultSubject");
                subject = salesRepName+"_"+format.format(new Date())+"_"+subject;
                mail.setSubject(subject);
                String greet = this.props.getProperty("mail.defaultgreet.message");
                String sign = this.props.getProperty("mail.sign");
                greet = greet.replaceFirst("rundate",formatInside.format(new Date()));
                Hashtable files = new Hashtable();
                mail.setBodyTextAsHTML(this.prepareHTMLContent(greet,this.mailContent(contentList),"",sign,""));
                if(this.successImage){
                    files.put("statusOn",getBytesFromFile(this.props.getProperty("mail.mxenergy.jasper.statuson")));
                    files.put("mxEnergyLogo",getBytesFromFile(this.props.getProperty("mail.mxenergy.logo.url")));
                }
                if(this.failureImage){
                    files.put("statusOff",getBytesFromFile(this.props.getProperty("mail.mxenergy.jasper.statusoff")));
                    files.put("mxEnergyLogo",getBytesFromFile(this.props.getProperty("mail.mxenergy.logo.url")));
                }
                mail.sendMultipleAttachments(files);
                isSent = mail.send();
            }
        }
        catch(Exception e)
        {
            if (BuildConfig.DMODE)
                System.out.println("Exception in Mail send");
            e.printStackTrace();
        }
        return isSent;
    }
    
    private boolean sendMailSalesManager(String key, List contentList)
    {
        
        boolean isSent = false; 
        try
        {
            Mail mail = new Mail();
            String[] emailElmnts = key.split(",");
            String mailId = emailElmnts[0];
            String salesManagerName = emailElmnts[2];
            String to = mailId;
            String cc = this.getPricingAnalystEmailIds();
            String bcc = this.getPricingBCCEmailIds();
            if(BuildConfig.DMODE){
                System.out.println("To SalesManager:"+to);
                System.out.println("cc SalesManager:"+cc);
            }
            boolean availRecipients = false;
            if(!to.trim().equals("")){
                String[] toIds = to.trim().split(",");
                mail.addRecipients(toIds, "to");
                availRecipients = true;
            }
          /*  if(!cc.trim().equals("")){
                String[] ccIds = cc.trim().split(",");
                mail.addRecipients(ccIds, "cc");
                availRecipients = true;
            }
            if(!bcc.trim().equals(""))
            {
                String[] bccIds = bcc.trim().split(",");
                mail.addRecipients(bccIds, "bcc");
                availRecipients = true;
            }*/
            if(availRecipients)
            {
                String subject = this.props.getProperty("mail.defaultSubject");
                subject = salesManagerName+"_"+format.format(new Date())+"_"+subject;
                mail.setSubject(subject);
                String greet = this.props.getProperty("mail.defaultgreet.message");
                String sign = this.props.getProperty("mail.sign");
                greet = greet.replaceFirst("rundate",formatInside.format(new Date()));
                Hashtable files = new Hashtable();
                mail.setBodyTextAsHTML(this.prepareHTMLContent(greet,this.mailContent(contentList),"",sign,""));
                if(this.successImage){
                    files.put("statusOn",getBytesFromFile(this.props.getProperty("mail.mxenergy.jasper.statuson")));
                    files.put("mxEnergyLogo",getBytesFromFile(this.props.getProperty("mail.mxenergy.logo.url")));
                }
                if(this.failureImage){
                    files.put("statusOff",getBytesFromFile(this.props.getProperty("mail.mxenergy.jasper.statusoff")));
                    files.put("mxEnergyLogo",getBytesFromFile(this.props.getProperty("mail.mxenergy.logo.url")));
                }
                mail.sendMultipleAttachments(files);
                isSent = mail.send();
            }
        }
        catch(Exception e)
        {
            if (BuildConfig.DMODE)
                System.out.println("Exception in Mail send");
            e.printStackTrace();
        }
        return isSent;
    }
    private boolean sendMailPricingAnalyst(List contentList)
    {
        
        boolean isSent = false; 
        try {
            Mail mail = new Mail();
            String to = this.getPricingAnalystEmailIds();
            String cc = this.getPricingCCEmailIds();
            String bcc = this.getPricingBCCEmailIds();
           	boolean availRecipients = false;
            if(!to.trim().equals("")){
                String[] toIds = to.trim().split(",");
                mail.addRecipients(toIds, "to");
                availRecipients = true;
            }
            if(!cc.trim().equals("")){
                String[] ccIds = cc.trim().split(",");
                mail.addRecipients(ccIds, "cc");
                availRecipients = true;
            }
            if(!bcc.trim().equals("")){
                String[] bccIds = bcc.trim().split(",");
                mail.addRecipients(bccIds, "bcc");
                availRecipients = true;
            }
            if(availRecipients)
            {
                String subject = this.props.getProperty("mail.defaultSubject");
                subject = format.format(new Date())+"_"+subject;
                mail.setSubject(subject);
                String greet = this.props.getProperty("mail.defaultgreet.message");
                String sign = this.props.getProperty("mail.sign");
                greet = greet.replaceFirst("rundate",formatInside.format(new Date()));
                Hashtable files = new Hashtable();
                mail.setBodyTextAsHTML(this.prepareHTMLContent(greet,this.mailContent(contentList),"",sign,""));
                if(this.successImage){
                    files.put("statusOn",getBytesFromFile(this.props.getProperty("mail.mxenergy.jasper.statuson")));
                    files.put("mxEnergyLogo",getBytesFromFile(this.props.getProperty("mail.mxenergy.logo.url")));
                }
                if(this.failureImage){
                    files.put("statusOff",getBytesFromFile(this.props.getProperty("mail.mxenergy.jasper.statusoff")));
                    files.put("mxEnergyLogo",getBytesFromFile(this.props.getProperty("mail.mxenergy.logo.url")));
                }
                mail.sendMultipleAttachments(files);
                isSent = mail.send();
            }
        }
        catch(Exception e)
        {
            if(BuildConfig.DMODE)
                System.out.println("Exception in Mail send");
            e.printStackTrace();
        }
        return isSent;
    }
    
    public boolean sendMail()
    {
        HashMap hmEmailIds = this.registerEmailIds();
        Set keySet = hmEmailIds.keySet();
        Iterator itr = keySet.iterator();
        while(itr.hasNext())
        {
            String key = (String)itr.next();
            String[] keyElmnts = key.split(",");
            String compareString = keyElmnts[1];
            if(compareString.equals("SalesRep"))
            {
                sendMailSalesRep(key, (List)hmEmailIds.get(key));
            }
            else if(compareString.equals("SalesManager"))
            {
                sendMailSalesManager(key, (List)hmEmailIds.get(key));
            }
        }
        MXenergyPriceRun objMXenergyPriceRun = new MXenergyPriceRun();
        List lstCustomers = (List)objMXenergyPriceRun.lastAutomaticRunCustomers();
        Iterator iteCust = lstCustomers.iterator();
        List contentList = new LinkedList();
        while(iteCust.hasNext())
        {
           PriceRunCustomerVO objPriceRunCustomerVO = (PriceRunCustomerVO)iteCust.next();
           contentList.add(new Integer(objPriceRunCustomerVO.getPriceRunCustomerRefId()));
        }
        sendMailPricingAnalyst(contentList);
        return true;
    }
    
    public void sentMailFailed()
    {
        boolean isSent = false;
        String to = "";
        String cc = "";
        String bcc = "";
        try
        {
            Mail mail = new Mail();
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
            {
	             to = this.props.getProperty("mail.mxenergy.test.to.address");
	             cc = this.props.getProperty("mail.mxenergy.test.cc.address");
	             bcc = this.props.getProperty("mail.mxenergy.test.bcc.address");
            }
            else
            {
                 to = this.props.getProperty("mail.pricinganalyst.address");
                 cc = this.props.getProperty("mail.mxenergy.cc.address");
                 bcc = this.props.getProperty("mail.mxenergy.bcc.address");
            }
            boolean availRecipients = false;
            if(!to.trim().equals(""))
            {
                String[] toIds = to.trim().split(",");
                mail.addRecipients(toIds, "to");
                availRecipients = true;
            }
           
            if(!cc.trim().equals("")){
                String[] ccIds = cc.trim().split(",");
                mail.addRecipients(ccIds, "cc");
                availRecipients = true;
            }
            if(!bcc.trim().equals("")){
                String[] bccIds = bcc.trim().split(",");
                mail.addRecipients(bccIds, "bcc");
                availRecipients = true;
            }
            if(availRecipients)
            {
                mail.setSubject(this.props.getProperty("mail.defaultSubject"));
                String greet = this.props.getProperty("mail.nocust.message");
                String sign = this.props.getProperty("mail.sign");
                greet = greet.replaceFirst("rundate",formatInside.format(new Date()));
                mail.setBodyTextAsHTML(this.prepareHTMLContent(greet,"","",sign,""));
                Hashtable files = new Hashtable();
                files.put("mxEnergyLogo",getBytesFromFile(this.props.getProperty("mail.mxenergy.logo.url")));
                mail.sendMultipleAttachments(files);
                isSent = mail.send();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if(BuildConfig.DMODE)
                System.out.println("Exception in mail send.");
        }
        
  
    }
    
    public String prepareHTMLContent(String greet, String msg, String request, String sign, String notice) throws IOException
    {
        String html = "<html>" +
        "<head>" +
        "<title>Price Run Report</title>" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" + 
        "<style type=\"text/css\">" +
        ".list_data {"+	"border-right:dotted 1px #CCCCCC;" +
        "border-bottom:dotted 1px #CCCCCC;" +
        "color:#333333;" +
        "padding:5px 5px 3px 2px;" +
        "font-family:Verdana,Arial, Helvetica;" +
        "font-size:10px;" +
        "}"+
        ".style1 {font-style: italic}" +
        ".style7 {" +
        "	font-family: Arial, Helvetica, sans-serif;" +
        "	font-weight: bold;" +
        "}" +
        "</style>" +
        "</head>" +
        "<body>" +
		"<table width=\"100%\" border=\"1\" bordercolor=\"black\"   cellspacing=\"0\" cellpadding=\"0\">"+ 
		"	<tr><td><table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+ 
		"		<tr>"+
		"			<td style=\"background: gray;\" width='45%'>&nbsp;</td>"+ 
		"			<td width=\"55%\" style=\"background: red;\">&nbsp;</td>"+
		"	</tr>"+
		"		  </table>"+
		"	</tr></td>"+
		"	<tr><td><table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+ 
		"		<tr>"+
		"			<td align='center' colspan='2'><img src=\"cid:mxEnergyLogo\" alt=\"MXenergy\"> </td>"+
		
		"		</tr>"+
		"		   </table>"+
		"	</tr></td>"+
		"	<tr><td align=\"center\">" +
		"		<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+  
		"			<tr><td><pre>" + greet + "</pre></td></tr>" +
        "			<tr><td align='center'<pre>" + msg + "</pre></td></tr>"+
		"			<tr><td><pre>" + notice + "</pre></td></tr>" +
        "			<tr><td><pre class=\"style1\">" + request+ "</pre></td></tr>" +
    		"	   	</table>"+
        " 	</td></tr>"+
		"</table>"+
        "</body>" +
        "</html>" ;
        return html;
    }
    public  byte[] getBytesFromFile(String filename) throws IOException
    {
        
        File file = new File(filename);
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            if(BuildConfig.DMODE)
                System.out.print("out of memory");
        }
        byte[] bytes = new byte[(int)length];
        
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        is.close();
        return bytes;
    }

    public static void main(String[] args) {
        MailManager obj = new MailManager();
        obj.sentMailFailed();
        //obj.sentMailFailed();
        if(BuildConfig.DMODE)
            System.out.println("mail sent");
    }
}


/*
*$Log: MailManager.java,v $
*Revision 1.7  2008/02/14 05:44:10  tannamalai
*auto run mail problem solved
*
*Revision 1.6  2008/01/07 05:08:32  tannamalai
*bcc added to auto run mail list
*
*Revision 1.5  2007/12/27 07:01:33  tannamalai
*separate mail ids given for testing and production environment
*
*Revision 1.4  2007/12/12 13:32:32  jvediyappan
*indentation.
*
*Revision 1.3  2007/12/12 09:56:54  jvediyappan
*filename changed.
*
*Revision 1.2  2007/12/07 11:07:20  jvediyappan
*character lendth is reduced based on the field lendth.
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/28 14:08:40  jvediyappan
*email ids are configured for auto run service.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.22  2007/08/02 07:29:41  sramasamy
*method access type changed
*
*Revision 1.21  2007/07/30 06:22:08  spandiyarajan
*throw exception
*
*Revision 1.20  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.19  2007/07/05 12:41:57  jnadesan
*showing manager problem solved
*
*Revision 1.18  2007/07/05 11:37:25  sramasamy
*showing sales rep and sales manager  problem handled
*
*Revision 1.17  2007/07/03 10:04:49  kduraisamy
*unwanted TMODE removed.
*
*Revision 1.16  2007/07/03 09:01:08  kduraisamy
*getTeam() placed inside the ProspectiveCustomerDAO.
*
*Revision 1.15  2007/07/02 13:38:55  jnadesan
*exception handled
*
*Revision 1.14  2007/07/02 12:58:23  kduraisamy
*getTeam() method altered.
*
*Revision 1.13  2007/06/21 13:03:37  kduraisamy
*sales Rep Manager mails error rectified.
*
*Revision 1.12  2007/06/16 05:40:07  kduraisamy
*email Ids null error handled.
*
*Revision 1.11  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.10  2007/06/11 07:06:38  kduraisamy
*error rectified.
*
*Revision 1.9  2007/06/07 09:33:04  kduraisamy
*multiple mail for a run added.
*
*Revision 1.8  2007/06/05 11:03:55  jnadesan
*methods are changed to take preference products after price run
*
*Revision 1.7  2007/06/05 06:20:58  jnadesan
*pricerun customer id sent instead of customer id
*
*Revision 1.6  2007/05/04 03:57:09  jnadesan
*mail date format changed
*
*Revision 1.5  2007/05/03 12:19:48  jnadesan
*mail table format changed
*
*Revision 1.4  2007/04/30 09:09:21  jnadesan
*space added if no preference set for that customer
*
*Revision 1.3  2007/04/30 04:41:29  jnadesan
*attaching images as per status
*
*Revision 1.2  2007/04/28 06:57:24  jnadesan
*mail content added for no customer
*
*Revision 1.1  2007/04/28 05:20:04  jnadesan
*mail work finished
*
*
*/