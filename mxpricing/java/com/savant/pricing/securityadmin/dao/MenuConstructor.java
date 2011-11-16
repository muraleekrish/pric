package com.savant.pricing.securityadmin.dao;

/*
 * Created on May 26, 2006
 *
 * Class : MenuConstructor.java
 *
 * Copyright (c) 2006 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * You shall not disclose or use this Confidential Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;



/**
 * @author Kanagaraj Duraisamy
 *
 */

public class MenuConstructor
{
    Vector vectConverted = new Vector();    
    boolean firstMenu = false;
    String firstMenuItem = "";
    private String getValidMenuIDs(String userID)
    {
        String str = "0";
        StringBuffer sb = new StringBuffer("");        
        UserDAO objUserDAO = new UserDAO();
        Session objSession = null;
        try
        {
            objSession = HibernateUtil.getSession();
            List objList = objUserDAO.getUserResources(userID);
            Iterator itr = objList.iterator();
            int resourceId = 0;
            Vector vecMenuIDs = new Vector();
            while(itr.hasNext())
            {
                resourceId = ((Integer)itr.next()).intValue();             
                Query objQuery = objSession.createSQLQuery("select aa.Menu_Item_ID from (select * from PRC_Menu_Items where Menu_Item_ID = ? union select * from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID = ?) union select * from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID = ?)) union select * from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID = ?))) union select * from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID in (select Parent_Menu_Item from PRC_Menu_Items where Menu_Item_ID = ?))))) aa order by Menu_Item_ID");
                objQuery.setInteger(0,resourceId);
                objQuery.setInteger(1,resourceId);
                objQuery.setInteger(2,resourceId);
                objQuery.setInteger(3,resourceId);
                objQuery.setInteger(4,resourceId);
                List obj = objQuery.list();
                Iterator innerItr = obj.iterator();
                
                while(innerItr.hasNext())
                {
                    int menuID = ((Integer)innerItr.next()).intValue();
                    
                    if(BuildConfig.DMODE)                        
                        System.out.print("Retrived Menu Item: " + menuID);
                    
                    if(!vecMenuIDs.contains(menuID + ""))
                    {
                        if(BuildConfig.DMODE)                            
                            System.out.println("\tNew Menu Found: " + menuID);
                        vecMenuIDs.add(menuID + "");
                    }
                }
            }
            if(vecMenuIDs.size() > 0)
            {
                if(BuildConfig.DMODE)                    
                    System.out.println("Menu Exists: " + sb);
                
                for(int i=0; i < vecMenuIDs.size(); i++)
                {
                    sb.append(vecMenuIDs.get(i) + ", ");
                }
                str = sb.substring(0, (sb.lastIndexOf(", ")));
            }        
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            objSession.close();
        }

        return str;
    }
    
    private LinkedHashMap getMenuHierarchy(String userID) 
    { 
        LinkedHashMap bizSystemServiceMap = new LinkedHashMap();
        Session objSession = null;
        try
        {
            objSession = HibernateUtil.getSession();
            String validMenuIDs = getValidMenuIDs(userID);
            if(BuildConfig.DMODE)            
                System.out.println("validMenuIDs : " +validMenuIDs);
            String newQuery = "";
            /*Query objQuery=null;*/
            /*if(validMenuIDs.length()>0)*/
            newQuery = "select f.Menu_Item_ID, Menu_Path, Menu_Item_Display_Name, Parent_Menu_Item, Menu_Order from PRC_Menu_Items f left outer join  PRC_Grp_MenuItem rf on f.Menu_Item_ID = rf.Menu_Item_ID where f.Menu_Item_ID IN("+ validMenuIDs +") order by Parent_Menu_Item,menu_order";
          /*  else
            newQuery = "select f.Menu_Item_ID, Menu_Path, Menu_Item_Display_Name, Parent_Menu_Item, Menu_Order from PRC_Menu_Items f left outer join  PRC_Grp_MenuItem rf on f.Menu_Item_ID = rf.Menu_Item_ID order by Parent_Menu_Item,menu_order";
           */ 
            if(BuildConfig.DMODE)
                System.out.println("New Query:"+newQuery);
            
            Query objQuery = objSession.createSQLQuery(newQuery);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                int functionID = ((Integer)innerRow[0]).intValue();
                String menuPath = (String)innerRow[1];
                String displayMenuName = (String)innerRow[2];
                int parentFunctionID = ((Integer)innerRow[3]).intValue();
                int menuOrder = ((Integer)innerRow[4]).intValue();
                MenuItemDetails objMenuItemDetails = new MenuItemDetails();
                objMenuItemDetails.setMenuName(displayMenuName);
                objMenuItemDetails.setMenuPath(menuPath);
                objMenuItemDetails.setParentFunctionID(parentFunctionID);                
                objMenuItemDetails.setMenuOrder(menuOrder);
                if (functionID == parentFunctionID)
                {
                    bizSystemServiceMap.put(new Integer(functionID),objMenuItemDetails);
                } 
                else
                {
                    putChild(functionID, parentFunctionID,objMenuItemDetails, bizSystemServiceMap);
                }
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            objSession.close();
        }
        return bizSystemServiceMap;	     
    } 
    private void putChild(int id, int parentFunctionID,MenuItemDetails objMenuItemDetails, HashMap root) 
    {
        Object obj = root.get(new Integer(parentFunctionID));
        MenuItemDetails parentBizSysElement = null;
        if (obj != null) 
        {
            parentBizSysElement = (MenuItemDetails) obj;
            parentBizSysElement.getChildMap().put(new Integer(id),objMenuItemDetails);
        } 
        else
        {
            Iterator it  = root.entrySet().iterator();
            while(it.hasNext())
            {
                MenuItemDetails objChildMenuItemDetails = (MenuItemDetails)((Map.Entry) it.next()).getValue();
                HashMap child = objChildMenuItemDetails.getChildMap();
                putChild(id, parentFunctionID,objMenuItemDetails, child);
            }
        }
    }    
    
    private String constructTopMenuScript(HashMap hm_root)
    {
        if(hm_root == null || hm_root.size() == 0)
        {
            return "";
        }
        StringBuffer sbScript = new StringBuffer("");
        sbScript.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  align=\"left\">");
        sbScript.append("<tr>");
        Iterator it = hm_root.entrySet().iterator();
        int i=0;
    
        while(it.hasNext())
        {
            MenuItemDetails objMenuItemDetails = (MenuItemDetails)((Map.Entry) it.next()).getValue();
            if(BuildConfig.DMODE)            
                System.out.println("Top Menu Name : "+ objMenuItemDetails.getMenuName());  
             /*   
            if(objMenuItemDetails.getMenuName().equalsIgnoreCase("Global Settings"))
                    sbScript.append("<td id=\"m"+(i)+"\" class=\"\" onMouseOver=\"buttonMouseover(event, 'dv"+(i)+"')\"><img src='/pricing/images/GlobalSettings_Off.gif' width='100' height='22' border='0' id='global' ></td>");
            if(objMenuItemDetails.getMenuName().equalsIgnoreCase("Pricing Configuration"))
                    sbScript.append("<td id=\"m"+(i)+"\" class=\"\" onMouseOver=\"buttonMouseover(event, 'dv"+(i)+"')\"><img src='/pricing/images/PricingConfiguration_Off.gif' border='0' id='pricingConfig'></td>");
            if(objMenuItemDetails.getMenuName().equalsIgnoreCase("Pricing"))
                    sbScript.append("<td id=\"m"+(i)+"\" class=\"\" onMouseOver=\"buttonMouseover(event, 'dv"+(i)+"')\"><img src='/pricing/images/Pricing_Off.gif' border='0' id='pricing' ></td>");
            if(objMenuItemDetails.getMenuName().equalsIgnoreCase("Security Admin"))
                    sbScript.append("<td id=\"m"+(i)+"\" class=\"\" onMouseOver=\"buttonMouseover(event, 'dv"+(i)+"')\"><img src='/pricing/images/SecurityAdminOff.gif' border='0' id='security' ></td>");
                    */
            sbScript.append("<td id=\"m"+(i+1)+"\" class=\"main_menu\" onMouseOver=\"buttonMouseover(event, 'dv"+(i+1)+"')\">&nbsp;&nbsp;&nbsp;"+objMenuItemDetails.getMenuName()+"&nbsp;</td>");
            i++;
        }
        sbScript.append("</tr>");        
        sbScript.append("</table>");        
        return sbScript.toString();
    }
    
    private void constructMenuScripts(Vector vecSortedList, int levelO, Vector vecScripts, int[] prefix,String contextName)
    {
        int suffix = 0;
        int levelN = 0;
        
        StringBuffer script = new StringBuffer("");
        StringBuffer sbPrefix = new StringBuffer("");
        String strPrefix = "";
        
        if(prefix.length > 0)
        {
            for (int i = 0; i < prefix.length; i++)
            {
                sbPrefix.append(prefix[i] + "_");
            }
            strPrefix = sbPrefix.toString().substring(0, sbPrefix.lastIndexOf("_"));
        }
        if(BuildConfig.DMODE)        
            System.out.println("Array Name : " + strPrefix + "\n");
        
        while(vecSortedList.size() > 0)
        {
            MenuItemDetails item = (MenuItemDetails)vecSortedList.firstElement();
            if(BuildConfig.DMODE)            
                System.out.println(item.getMenuName() + " " + item.getMenuPath() + " " + item.getMenuLevel());
            
            levelN = item.getMenuLevel();
            
            if(vecSortedList.size() == 2)
            {
                if(BuildConfig.DMODE)                
                    System.out.println("Another Target");
            }   
            
            if(levelN == levelO)
            {
             
                if(item.getMenuPath()!= null && !firstMenu)
                {
                    firstMenu =true;
                    firstMenuItem = item.getMenuPath();
                }
                suffix++;
                script.append("<a class=\"menuItem\" href=\""+contextName+item.getMenuPath()+"\">"+item.getMenuName()+"</a>");
                if(vecSortedList.size() > 0)
                {
                    MenuItemDetails mi = (MenuItemDetails)vecSortedList.firstElement();
                    if(BuildConfig.DMODE)                    
                        System.out.println("First Item Removed Normal : " + mi.getMenuName() + " " + mi.getMenuLevel());
                    vecSortedList.remove(0);                
                }
            }
            else if(levelN > levelO)
            {
                int[] prefixN = new int[prefix.length + 1];
                
                for(int i=0; i < prefix.length; i++)
                {
                    prefixN[i] = prefix[i];    
                }
                
                prefixN[prefix.length] = suffix;
                if(BuildConfig.DMODE)                
                {
                    System.out.print("Current Prefix Array : " );
                    for(int i=0; i < prefix.length; i++)
                    {
                        System.out.print(prefix[i] + " ");
                    }
                }
                if(BuildConfig.DMODE)
                {
                    System.out.print("\nNew Prefix Array : " );
                    for(int i=0; i < prefixN.length; i++)
                    {
                        System.out.print(prefixN[i] + " ");
                    }
                }
                
                this.constructMenuScripts(vecSortedList, levelN, vecScripts, prefixN,contextName);
                
                int i = script.lastIndexOf("<a ");
                int j = script.lastIndexOf("\">");                
                if(i != -1)
                {
                    if(BuildConfig.DMODE)                        
                        System.out.println("Replacing Old script");
                    
                    String strPrefixN = "";
                    StringBuffer sbPrefixN = new StringBuffer("");
                    if(prefixN.length > 1)
                    {
                        for (int k = 1; k < prefixN.length; k++)
                        {
                            sbPrefixN.append(prefixN[k] + "_");
                        }
                        strPrefixN = sbPrefixN.toString().substring(0, sbPrefixN.toString().lastIndexOf("_"));
                    }
                    
                    script.replace(i, j+2, "<a class=\"menuItem\" href=\"#\" onMouseOver=\"menuItemMouseover(event, 'dv"+strPrefixN+"')\"><span class=\"menuItemText\">");
                    script.replace(script.lastIndexOf("</a>"),script.lastIndexOf("</a>")+4, "</span><span class=\"menuItemArrow\"><IMG SRC=\" "+contextName+"/images/arrows.gif\" border=\"0\"></span></a>");
                }
            }
            else if(levelN < levelO)
            {
                if(BuildConfig.DMODE)                
                    System.out.println("strPrefix: " + strPrefix);
                
                if(strPrefix.length() > 2)
                {
                    
                    vecScripts.add("<div id=\"dv"+strPrefix.substring(2)+"\" class=\"menu\" onmouseover=\"menuMouseover(event);\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>");
                    vecScripts.add(script);
                    vecScripts.add("</td></tr></table></div>");
                    
                    //System.out.println("New Element Added : " + vecScripts.lastElement());
                }
                return;
            }
        }
    }
    
    private void convertToVector(HashMap hm_root,int level)
    {
        Iterator it = hm_root.entrySet().iterator();
        int levelN = level+1;
        while(it.hasNext())
        {
            MenuItemDetails objMenuItemDetails = (MenuItemDetails)((Map.Entry) it.next()).getValue();
            objMenuItemDetails.setMenuLevel(level);
            
            vectConverted.add(objMenuItemDetails);
            convertToVector(objMenuItemDetails.getChildMap(),levelN);
        }
    }
    
    public String getConstructedMenuScripts(String userID,String contextName) 
    {
        String strScripts ="";
        String menuPath ="";
        UserDAO objUserDAO = new UserDAO();
        Vector vecScripts = new Vector();
        
        HashMap hm_root = getMenuHierarchy(userID);
        
        if(BuildConfig.DMODE)
            System.out.println("Hm:"+hm_root);
        
        
        strScripts = constructTopMenuScript(hm_root);
        
        this.convertToVector(hm_root,0);
        
        MenuItemDetails extraMenuDtls = new MenuItemDetails();
        extraMenuDtls.setMenuLevel(-1);
        vectConverted.add(extraMenuDtls);
        
        this.constructMenuScripts(vectConverted, -1, vecScripts, new int[]{},contextName);
        
        StringBuffer sbScripts = new StringBuffer("");
        for(int i=0; i<vecScripts.size(); i++)
        {
            if(BuildConfig.DMODE)            
                System.out.println(vecScripts.get(i));
            sbScripts.append(vecScripts.get(i));
        }        
        
        if(BuildConfig.DMODE)            
            System.out.println("Inner Menus : "+sbScripts.toString());
        
        strScripts += sbScripts.toString();
        menuPath = objUserDAO.getFirstMenuPath(userID);
        if(!menuPath.trim().equalsIgnoreCase(""))
        {
            firstMenuItem = menuPath;
        }
        String s =strScripts +"###"+firstMenuItem;

        if(BuildConfig.DMODE)            
            System.out.println("S@@@@@@@:"+s);
        return s;
    }
    
  
}


