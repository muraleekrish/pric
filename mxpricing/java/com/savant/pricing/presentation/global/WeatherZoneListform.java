/*
 * Created on Jan 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.global;

import org.apache.struts.action.ActionForm;

 
/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WeatherZoneListform extends ActionForm{
    

    private String weatherZoneName="";
    private String weatherZoneCode="";
    private String description="";
   // private String congestionZoneName="";
    private String sortOrder="ascending";
	private String sortField="";
	private String search="";
	private String maxItems="10";
	private String show="";
	private String startPosition ="1";
	private String page="1";	
	private String pageTop ="0";
	private String formAction = "list";
	private String weatherZoneId=""; 
	private String txtName="";
	private String txtCode="";
	private String searchName="0";
	private String searchCode="0";
	private String congestionZone="";
	private String searchZone="";
	private String zoneId = "";
	
	
    
    public String getSearchZone()
    {
        return searchZone;
    }
    public void setSearchZone(String searchZone)
    {
        this.searchZone = searchZone;
    }
    public String getCongestionZone()
    {
        return congestionZone;
    }
    public void setCongestionZone(String congestionZone)
    {
        this.congestionZone = congestionZone;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getFormAction()
    {
        return formAction;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public String getMaxItems()
    {
        return maxItems;
    }
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    public String getPage()
    {
        return page;
    }
    public void setPage(String page)
    {
        this.page = page;
    }
    public String getPageTop()
    {
        return pageTop;
    }
    public void setPageTop(String pageTop)
    {
        this.pageTop = pageTop;
    }
    public String getSearch()
    {
        return search;
    }
    public void setSearch(String search)
    {
        this.search = search;
    }
    public String getSearchCode()
    {
        return searchCode;
    }
    public void setSearchCode(String searchCode)
    {
        this.searchCode = searchCode;
    }
    public String getSearchName()
    {
        return searchName;
    }
    public void setSearchName(String searchName)
    {
        this.searchName = searchName;
    }
    public String getShow()
    {
        return show;
    }
    public void setShow(String show)
    {
        this.show = show;
    }
    public String getSortField()
    {
        return sortField;
    }
    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
public String getSortOrder()
{
    return sortOrder;
}
public void setSortOrder(String sortOrder)
{
    this.sortOrder = sortOrder;
}
    public String getStartPosition()
    {
        return startPosition;
    }
    public void setStartPosition(String startPosition)
    {
        this.startPosition = startPosition;
    }
    public String getTxtCode()
    {
        return txtCode;
    }
    public void setTxtCode(String txtCode)
    {
        this.txtCode = txtCode;
    }
    public String getTxtName()
    {
        return txtName;
    }
    public void setTxtName(String txtName)
    {
        this.txtName = txtName;
    }
    public String getWeatherZoneCode()
    {
        return weatherZoneCode;
    }
    public void setWeatherZoneCode(String weatherZoneCode)
    {
        this.weatherZoneCode = weatherZoneCode;
    }
    public String getWeatherZoneId()
    {
        return weatherZoneId;
    }
    public void setWeatherZoneId(String weatherZoneId)
    {
        this.weatherZoneId = weatherZoneId;
    }
    public String getWeatherZoneName()
    {
        return weatherZoneName;
    }
    public void setWeatherZoneName(String weatherZoneName)
    {
        this.weatherZoneName = weatherZoneName;
    }
    
    public String getZoneId() 
    {
        return zoneId;
    }
    
    public void setZoneId(String zoneId) 
    {
        this.zoneId = zoneId;
    }
}
