/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	ZipCodesDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.securityadmin.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.PricingException;
import com.savant.pricing.securityadmin.valueobject.BOSSUsersVO;
import com.savant.pricing.securityadmin.valueobject.MenuItemsVO;
import com.savant.pricing.securityadmin.valueobject.RolesVO;
import com.savant.pricing.securityadmin.valueobject.UserGroupVO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;
import com.savant.pricing.transferobjects.TeamDetails;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserDAO
{
    private static Logger logger = Logger.getLogger(UserDAO.class);
    
    public boolean addUsers(UsersVO usersVO, RolesVO objRolesVO) throws ConstraintViolationException,HibernateException
    {
        boolean addResult = false;
        Session objSession = null;
        try
        {
            logger.info("ADDING A NEW USER");
            objSession = HibernateUtil.getSession();
            // To save the user
            objSession.beginTransaction();
            usersVO.setPassword(this.encrypt(usersVO.getPassword()));
            objSession.save(usersVO);
            objSession.getTransaction().commit();
            
            // To save the Role for that user
            objSession.beginTransaction();
            UserGroupVO objUserGroupVO = new UserGroupVO();
            objUserGroupVO.setUserVo(usersVO);
            objUserGroupVO.setRole(objRolesVO);
            objSession.save(objUserGroupVO);
            objSession.getTransaction().commit();
            addResult= true;
            logger.info("NEW USER ADDED");
        }
        catch (ConstraintViolationException e)
        {
            logger.error("CONSTRAINT VIOLATION EXCEPTION DURING ADD A NEW USER", e);
            objSession.getTransaction().rollback();
            throw new ConstraintViolationException("User Id already exist",new SQLException(),usersVO.getUserId());
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD A NEW USER", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new HibernateException("User does not saved.");
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return addResult;
    }
    private String encrypt(String original) throws IllegalStateException
    {
        return new BASE64Encoder().encodeBuffer(original.getBytes());
    }
    private String decrypt(String encrypted) throws IllegalStateException, IOException
    {
        byte[] original = new BASE64Decoder().decodeBuffer(encrypted);
        return new String(original);
    }
    
    public String defineCurrentGreeting()
    {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting = "";
        if (hour >= 0 && hour < 12)
            greeting = "Good Morning";
        else
            if (hour >= 12 && hour < 16)
                greeting = "Good Afternoon";
            else
                if (hour >= 16 && hour < 24)
                    greeting = "Good Evening";
        return greeting;
    }
    
    
    public boolean Authentication(String userId, String password)throws Exception
    {
        if (BuildConfig.DMODE)
            System.out.println("USER AUTHENTICATION - STARTS");
        boolean result = false;
        Session objSession = null;
        try
        {
            logger.info("AUTHENTICATING USER " + userId);
            objSession = HibernateUtil.getSession();
            UsersVO objUsersVO = this.getUsers(userId);
            if(objUsersVO !=null && objUsersVO.getPassword().equals(password))
            {
                result = true;
                logger.info("USER IS AUTHENTICATED");
            }
            else
            {
                logger.info("USER IS NOT AUTHENTICATED");
            }
        }
        catch(Exception sqle)
        {
            logger.error("GENERAL EXCEPTION DURING AUTHENTICATE THE USER", sqle);
            sqle.printStackTrace();
            throw new Exception(sqle.toString());
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return result;
    }
    
    
    public UsersVO getUsers(String userId) throws Exception
    {
        UsersVO objUsersVO = null;
        Criteria objCriteria = null;
        Session objSession = null;
        try
        {
            logger.info("GET USER DETAILS OF " + userId);
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UsersVO.class);
            objCriteria.add(Restrictions.idEq(userId));
            List lstCust = objCriteria.list();
            if(lstCust.size()>0)
            {
                objUsersVO = (UsersVO)lstCust.get(0);
                objUsersVO.setPassword(this.decrypt(objUsersVO.getPassword()));
            }
            logger.info("GET USERS ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET A USER", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET A USER", e);
            e.printStackTrace();
            throw new Exception(e.toString());
        }
        finally
        {  
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objUsersVO;
    }
    
    public UserGroupVO getUserGrp11(String userId) throws Exception
    {
        UserGroupVO objUserGrpVO = null;
        Criteria objCriteria = null;
        Session objSession = null;
        try
        {
            logger.info("GET USERGROUP DETAILS OF " + userId);
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UserGroupVO.class);
           // objCriteria.add(Restrictions.idEq())
            List lstCust = objCriteria.list();
            if(lstCust.size()>0)
            {
                objUserGrpVO = (UserGroupVO)lstCust.get(0);
            }
            logger.info("GET USERS ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET A USERGROUP", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET A USERGROUP", e);
            e.printStackTrace();
            throw new Exception(e.toString());
        }
        finally
        {  
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objUserGrpVO;
    }
    
    public HashMap getAllUsers(Filter filterUserId,int userTypeId, String parentTypeId, String sortBy, boolean ascending, int startIndex, int displayCount)
    {        
        Criteria objCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETING ALL USERS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UsersVO.class);
            
            if(filterUserId != null)
            {
                objCriteria.add(Restrictions.like(filterUserId.getFieldName(),filterUserId.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterUserId.getSpecialFunction())));
            }
            
            if(userTypeId != 0)
            {
                objCriteria.add(Restrictions.eq("userTypes.userTypeId", new Integer(userTypeId)));
            }
            
            if(parentTypeId.length() > 0)
            {
                objCriteria.add(Restrictions.like("parentUser.userId", parentTypeId,MatchMode.EXACT));
            }        
            
            totRecordCount = new Integer(objCriteria.list().size());
            if(ascending)
            {
                objCriteria.addOrder(Order.asc(sortBy));
            }
            else
            {
                objCriteria.addOrder(Order.desc(sortBy));
            }
            objCriteria.setFirstResult(startIndex);
            objCriteria.setMaxResults(displayCount);
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL USERS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL USERS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmResult;
    }
    
    public boolean updateUser(UsersVO  userVO, RolesVO objRolesVO)
    {        
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("UPDATING THE USER");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            if(BuildConfig.DMODE)
                System.out.println("userVO:"+userVO.getUserId());
            userVO.setPassword(this.encrypt(userVO.getPassword()));
            objSession.saveOrUpdate(userVO);
            updateResult = true;
            objSession.getTransaction().commit();
            
            if(objRolesVO != null)
            {
                //To update the Role for that user
                objSession.beginTransaction();
                objSession.createQuery("delete from UserGroupVO as userGroupVo where userGroupVo.userVo.userId = ?").setString(0, userVO.getUserId()).executeUpdate();
                UserGroupVO objUserGroupVO = new UserGroupVO();
                objUserGroupVO.setUserVo(userVO);
                objUserGroupVO.setRole(objRolesVO);
                objSession.save(objUserGroupVO);
                objSession.getTransaction().commit();
            }
            logger.info("USER IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE USER", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }        
        return updateResult;   
    }
    
    public boolean deleteUser(UsersVO usersVO) throws SQLException
    {
        boolean updateResult = false;
        Criteria objCriteria = null;
        Session objSession = null;
        List prcCustList = null;
        System.out.println("Inside User delete method");
        try
        {
            logger.info("DELETING THE USER");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class);
            objCriteria.add(Restrictions.eq("salesRep.userId",usersVO.getUserId()));
            prcCustList = objCriteria.list();
            System.out.println("List Size :" + prcCustList.size());
            if(prcCustList.size()==0){
                System.out.println("Before Deleting UserGroup");
                objSession.createQuery("delete from UserGroupVO as userGroupVo where userGroupVo.userVo.userId = ?").setString(0, usersVO.getUserId()).executeUpdate();
                System.out.println("after Deleting UserGroup and before Deleting User");
                objSession.delete(usersVO);
                System.out.println("after Deleting User");
                updateResult = true; 
            }
            else{
                throw new PricingException("USER INUSE");
            }
            objSession.getTransaction().commit();
            logger.info("USER IS DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE A USER", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            throw new HibernateException("");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING DELETE A USER", e);
            e.printStackTrace(); 
            throw new SQLException();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return updateResult;
    }
    public String getFirstMenuPath(String userId)
    {
        String menuPath = "";
        String script = "select menu.Menu_Path from dbo.PRC_Grp_MenuItem as grpmenu, dbo.PRC_Menu_Items as menu "+
        "where grpmenu.Grp_ID in (select  Grp_ID from dbo.PRC_User_Grps where User_ID = ?)" +
        " and menu.Menu_Item_ID in (select Menu_Item_ID from dbo.PRC_Menu_Items where Menu_Item_Name like 'Prospective Customer%')" +
        " and grpmenu.Menu_Item_ID = menu.Menu_Item_ID order by menu.Menu_Item_Name";
        
        Session objSession = null;
        try
        {
            logger.info("GETTING FIRST MENU PATH FOR THE USER " + userId);
            
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery(script); 
            objQuery.setString(0, userId);
            Iterator itr = objQuery.list().iterator();
            if(itr.hasNext())
            {
                menuPath = (String)itr.next();
            }
        }
        catch(Exception sqle)
        {
            logger.error("GENERAL EXCEPTION DURING GETFIRSTMENUPATH", sqle);
            sqle.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return menuPath;
    }
    private int getMenuId(String menuName)
    {
        MenuItemsVO objMenuItemsVO = null;
        Criteria objCriteria = null;
        Session objSession = null;
        int menuItemId = 0;
        try
        {
            logger.info("GETTING MENU ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MenuItemsVO.class);
            objCriteria.add(Restrictions.eq("menuItemName",menuName));
            List lstMenuItems = objCriteria.list();
            if(lstMenuItems.size()>0&&lstMenuItems!=null)
            {
                objMenuItemsVO = (MenuItemsVO)lstMenuItems.get(0);
                menuItemId = objMenuItemsVO.getMenuItemID();
            }
            logger.info("GET MENU ID ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GETMENUID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GETMENUID", e);
            e.printStackTrace();
        }
        finally
        {  
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return menuItemId;
    }
    
    public boolean isUserElgible(String userId,String menuName)
    {
        int menuId = this.getMenuId(menuName);
        boolean result = false;
        Session objSession = null;
        try
        {
            logger.info("CHECK THE USER " + userId + " IS ELIGIBLE");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT [Menu_Item_ID], [Grp_ID] FROM [PRC_Grp_MenuItem] where Grp_ID in (SELECT [Grp_ID]FROM [PRC_User_Grps] where [User_ID] = ? )and Menu_Item_ID = ?"); 
            objQuery.setString(0, userId);
            objQuery.setInteger(1, menuId);
            Iterator itr = objQuery.list().iterator();
            if(itr.hasNext())
            {
                result = true;
                logger.info("USER '" + userId + "' IS ELIGIBLE");
            }
            else
            {
                logger.info("USER IS NOT ELIGIBLE");
            }
        }
        catch(Exception sqle)
        {
            logger.error("GENERAL EXCEPTION DURING CHECK THE USER IS ELIGIBLE", sqle);
            sqle.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return result;
    }
    
    public List getUserResources(String userId)
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING USER RESOURCES FOR USER " + userId);
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select menuitemsvo.menuItem.menuItemID from UserGroupVO userGrp, RoleMenuItemVO as menuitemsvo where userGrp.userVo.userId = ? and userGrp.role.groupId = menuitemsvo.role.groupId");
            objQuery.setString(0, userId);
            objList = objQuery.list();
            logger.info("GET USER RESOURCES ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE RESOURCE FOR A USER", e);
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
    
    public Collection getParentTypeUsers(int userTypeId)
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING PARENT TYPE USERS");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select users.userId, users.firstName, users.lastName from UserTypesVO as userTypes, UsersVO as users  where userTypes.userTypeId = ? and userTypes.parentUserType.userTypeId = users.userTypes.userTypeId order by users.firstName");
            objQuery.setInteger(0, userTypeId);
            objList = objQuery.list();
            Iterator itr = objList.iterator();
            objList = new ArrayList();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                UsersVO objUsersVO = new UsersVO();
                objUsersVO.setUserId(String.valueOf(innerRow[0]));
                objUsersVO.setFirstName(String.valueOf(innerRow[1]));
                objUsersVO.setLastName(String.valueOf(innerRow[2]));
                objList.add(objUsersVO);
            }
            logger.info("GET PARENT TYPE USERS ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PARENT TYPE USERS", e);
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
    
    public List getAllUsers()
    {       
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL USERS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UsersVO.class).add(Restrictions.eq("valid",new Boolean(true))).addOrder(Order.asc("firstName"));
            objCriteria.addOrder(Order.asc("userId"));
            objList = objCriteria.list();
            logger.info("GET ALL USERS ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL USERS", e);
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
    
    public List getChildPersons(String userId, boolean withSelfUser)
    {       
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL USERS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UsersVO.class).add(Restrictions.eq("valid",new Boolean(true)));
            if(withSelfUser)
                objCriteria.add(Restrictions.or(Restrictions.eq("userId",userId),Restrictions.eq("parentUser.userId",userId)));
            else
                objCriteria.add(Restrictions.eq("parentUser.userId",userId));
            objCriteria.addOrder(Order.asc("userId"));
            objList = objCriteria.list();
            logger.info("GET ALL USERS ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL USERS", e);
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
    
    public List getAllBOSSUsers()
    {       
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL BOSS USERS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(BOSSUsersVO.class).add(Restrictions.eq("valid",new Integer(1))).addOrder(Order.asc("userId"));
            objList = objCriteria.list();
            logger.info("GET ALL BOSS USERS ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL BOSS USERS", e);
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
    public BOSSUsersVO getBOSSUser(String userId)
    {
    
        BOSSUsersVO objBOSSUsersVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING BOSS USER FOR USER " + userId);
            objSession = HibernateUtil.getSession();
            objBOSSUsersVO = (BOSSUsersVO)objSession.get(BOSSUsersVO.class,userId);
            logger.info("GET BOSS USER ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE BOSS USER", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE BOSS USER", e);
            e.printStackTrace();
        }
        finally
        {  
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objBOSSUsersVO;
    }
    
    public TeamDetails getTeam(String salesRepuserId)
    {
        TeamDetails objTeamDetails = new TeamDetails();
        try
        {
            UserDAO objUserDAO = new UserDAO();
            RolesDAO objRolesDAO = new RolesDAO();
            UsersVO objSalesRep = objUserDAO.getUsers(salesRepuserId);
            String roleName = objRolesDAO.getRoleName(objSalesRep.getUserId());
            UsersVO objSalesManager = null;
            
            if(roleName.equals("SALES REP"))
            {
                if(objSalesRep != null)
                {
                    objSalesManager = objSalesRep.getParentUser();
                }
                objTeamDetails.setSalesRep(objSalesRep);
                objTeamDetails.setSalesManager(objSalesManager);
                objTeamDetails.setPricingAnalyst(objSalesManager.getParentUser());
            }
            else if(roleName.equals("SALES MANAGER"))
            {
                objTeamDetails.setSalesRep(objSalesRep);
                objTeamDetails.setSalesManager(objSalesRep);
                objSalesManager = objSalesRep;
                objTeamDetails.setPricingAnalyst(objSalesManager.getParentUser());
            }
            else
            {
                objTeamDetails.setSalesRep(objSalesRep);
                objTeamDetails.setSalesManager(objSalesRep);
                objTeamDetails.setPricingAnalyst(objSalesRep);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return objTeamDetails;
    }
    public String getTeamMembers(String userId)
    {
        String team = "";
        if(!userId.equals(""))
        {
            try
            {                    
                TeamDetails objteam = new TeamDetails();
                objteam = getTeam(userId);
                String salesMgr = objteam.getSalesManager().getUserId().trim();
                String salesAnalyst = objteam.getPricingAnalyst().getUserId().trim();
                team += salesMgr+"@#$"+salesAnalyst;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }  
        return team;
    }
    
    public static void main(String args[]) throws Exception
    {
        UserDAO usersDAO = new UserDAO();
        List lstResults = usersDAO.getChildPersons("rep", true);
        Iterator ite = lstResults.iterator();
        while(ite.hasNext())
        {
            UsersVO objUsersVO = (UsersVO)ite.next();
            System.out.println(objUsersVO.getUserId());
        }
    }
}

/*
*$Log: UserDAO.java,v $
*Revision 1.2  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.47  2007/10/24 10:50:47  jnadesan
*while adding new person in pricing customer also added into sales commission
*
*Revision 1.46  2007/09/17 10:57:56  spandiyarajan
*added 1 more parameter in getChildpersons method.
*
*Revision 1.45  2007/09/13 12:04:57  kduraisamy
*menthod added to retrive their customers
*
*Revision 1.44  2007/09/07 10:04:21  jnadesan
*method added to get team
*
*Revision 1.43  2007/09/04 05:24:18  spandiyarajan
*removed unwanted imports
*
*Revision 1.42  2007/08/31 14:54:57  sramasamy
*Log message is added for log file.
*
*Revision 1.41  2007/08/29 07:23:55  spandiyarajan
*makepdf initially commited
*
*Revision 1.40  2007/08/24 13:34:30  kduraisamy
*calling menu item in all pages problem solved.
*
*Revision 1.39  2007/07/31 12:30:11  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.38  2007/07/30 06:05:45  spandiyarajan
*throw exception - database connection failed
*
*Revision 1.37  2007/07/02 12:42:11  jnadesan
*null value checked
*
*Revision 1.36  2007/06/21 13:04:34  kduraisamy
*roleName wise mail added.
*
*Revision 1.35  2007/06/16 05:45:12  kduraisamy
*indentation.
*
*Revision 1.34  2007/06/15 05:31:34  spandiyarajan
*display first menu page is prospective customer
*
*Revision 1.33  2007/06/14 14:15:57  kduraisamy
*management level validation added.
*
*Revision 1.31  2007/06/14 09:02:05  spandiyarajan
*name chaged
*
*Revision 1.30  2007/06/12 12:59:27  spandiyarajan
**** empty log message ***
*
*Revision 1.29  2007/06/12 06:47:03  kduraisamy
*userIds are taken from BOSS.
*
*Revision 1.28  2007/06/12 06:27:33  kduraisamy
*userIds are taken from BOSS.
*
*Revision 1.27  2007/05/30 11:10:34  spandiyarajan
*throw the bug in authentication method
*
*Revision 1.26  2007/05/25 10:06:19  jnadesan
*unwanted lines removed
*
*Revision 1.25  2007/05/18 07:57:09  jnadesan
*getting user changed
*
*Revision 1.24  2007/04/30 05:46:14  spandiyarajan
*last name added in getuserdao
*
*Revision 1.23  2007/04/25 05:49:30  spandiyarajan
*sort order added for list
*
*Revision 1.22  2007/04/23 12:10:44  kduraisamy
*greetings time changed.
*
*Revision 1.21  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.20  2007/04/23 05:33:00  kduraisamy
*greetings time changed.
*
*Revision 1.19  2007/04/21 13:24:20  kduraisamy
*defineCurrentGreeting() added.
*
*Revision 1.18  2007/04/18 09:11:39  spandiyarajan
*added last name in select query
*
*Revision 1.17  2007/04/13 05:01:12  kduraisamy
*unwanted println commented.
*
*Revision 1.16  2007/04/12 10:15:35  spandiyarajan
*pwd case sensitive added.
*
*Revision 1.15  2007/04/12 10:14:00  spandiyarajan
*pwd case sensitive added.
*
*Revision 1.14  2007/04/10 07:16:07  kduraisamy
*parentTypeUsers() method changed.
*
*Revision 1.13  2007/04/08 05:45:43  kduraisamy
*unique key added for Roles.
*
*Revision 1.12  2007/04/06 13:34:06  spandiyarajan
*altered the getalluers method for filter
*
*Revision 1.11  2007/04/06 12:53:25  spandiyarajan
*getAllUsers() filter added.
*
*Revision 1.9  2007/04/06 04:19:57  kduraisamy
*unwanted println removed.
*
*Revision 1.8  2007/03/22 11:51:18  kduraisamy
*getParenttypeuser() added.
*
*Revision 1.7  2007/03/22 08:56:11  kduraisamy
*getParenttypeuser() added.
*
*Revision 1.6  2007/03/14 09:03:52  kduraisamy
*indentation and rollBack added.
*
*Revision 1.5  2007/03/10 12:22:02  srajappan
*security admin code changed
*
*Revision 1.4  2007/03/09 14:11:02  srajappan
*security admin actions added
*
*Revision 1.3  2007/03/08 04:54:30  srajappan
*role base menu added
*
*Revision 1.2  2007/03/02 11:51:42  kduraisamy
*menu added
*
*Revision 1.1  2007/02/23 05:11:28  srajappan
*security admin DAO added
*
*Revision 1.2  2007/02/02 06:07:51  kduraisamy
*imports organized.
*
*Revision 1.1  2007/02/02 06:06:03  kduraisamy
*initial commit.
*
*
*/