<%
	if(session.getAttribute("userName")==null)
	{
		if(session.getLastAccessedTime()>0)
		{
%>
<jsp:include page="/jsp/sessionout.jsp"/>
<%
		}
		else
		{
%>
<jsp:include page="/jsp/index.jsp"/>
<%
		}
	}
	else
	{
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.savant.pricing.calculation.dao.PricingDAO"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import="com.savant.pricing.valueobjects.TaxRatesVO"%>
<%@ page import="com.savant.pricing.transferobjects.PricingDashBoard"%>
<%@ page import="com.savant.pricing.transferobjects.DealLevers"%>
<%@ page import="com.savant.pricing.common.chart.AnnualChart"%>
<%@ page import="com.savant.pricing.common.chart.ChargePieChart"%>
<%@ page import="com.savant.pricing.common.chart.SensitivityChart"%>
<%@ page import="com.savant.pricing.presentation.pricing.AttractiveIndex"%>
<%@ page import="com.savant.pricing.calculation.dao.DealLeversDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.DealLeversVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunHeaderVO"%>
<%@ page import="com.savant.pricing.dao.PreferenceTermsDAO"%>
<%@ page import="com.savant.pricing.dao.PriceRunCustomerDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerTermsVO"%>
<%@ page import="com.savant.pricing.dao.PreferenceProductsDAO"%>
<%@ page import="com.savant.pricing.dao.GasPriceDAO"%>
<%@ page import="com.savant.pricing.dao.CustEnergyComponentsDAO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.EnergyComponentsVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CustEnergyComponentsVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCostCapitalVO"%>
<%@ page import="com.savant.pricing.dao.PriceRunCostCapitalDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.DashboardForm" /><jsp:setProperty name="frm" property="*" />
<% 
	NumberFormat dnf = NumberUtil.doubleFraction(); 
	NumberFormat tnf = NumberUtil.tetraFraction();
	NumberFormat nf = new DecimalFormat("0.00000##");
	NumberFormat df = NumberFormat.getIntegerInstance();
	AnnualChart objAnnualChart = new AnnualChart();
	ChargePieChart piechart = new ChargePieChart();
	SensitivityChart sensitivitychart = new SensitivityChart();
	AttractiveIndex objAttractiveIndex = new AttractiveIndex();
	DealLeversDAO objDealLeversDAO = new DealLeversDAO();
	PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
	PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
	GasPriceDAO objGasPriceDAO = new GasPriceDAO();
	PriceRunCostCapitalDAO objPriceRunCostCapitalDAO = new PriceRunCostCapitalDAO();
	
	HashMap hmCostCap = new HashMap();
	Date dateGas = null;
	
	double total$ =0;
	double total$KWh =0;
	double oHtotal$ =0;
	double oHtotal$KWh =0;
	
	float Interestrate = 0;
	float costlc = 0;
	float mcpe = 0;
	float planlong = 0;
	float maxmtm = 0;
	float arfloat = 0;
	float isomargin = 0;
	float tdspfloat = 0;
	
	String priceRunId =null;
	String priceRunRefNo = null;
	HashMap hmTDSPs = new HashMap();
	HashMap hmCongestionZones = new HashMap();
	HashMap hmEsiids = new HashMap();
	HashMap pivalues = new HashMap();
	String esiId = "";
	List listDealLevers;
	Date fwdDate = null;   
	float avgGasPrice = 0;
	boolean isLockAllDealEnable = false;
	boolean overRide = false;
	int browserHt = 0;

	if( request.getParameter("PriceRunRefNo")!=null)
	{	
		priceRunRefNo = request.getParameter("PriceRunRefNo");
	}
	
	if( request.getParameter("priceRunId")!=null)
	{	
		priceRunId = request.getParameter("priceRunId");
		frm.setRunRefId(priceRunId);
	}
	if(request.getParameter("strESIID")!=null)
	{
		esiId = request.getParameter("strESIID");
	}

	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	SimpleDateFormat gs = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
	SimpleDateFormat mnthFormat = new SimpleDateFormat("MMM yyyy");
	PricingDAO objPricingDAO = new PricingDAO();
	PriceRunHeaderVO objPriceRunHeader = null;
	PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
	PriceRunCustomerVO objPriceRunCustomerVO = null;

	PricingDashBoard objPricingDashBoard = null;
	
	hmCostCap = objPriceRunCostCapitalDAO.getHmPrcCost(priceRunRefNo);
	if(hmCostCap.get(new Integer(1))!=null)
		Interestrate = (((Float)hmCostCap.get(new Integer(1))).floatValue())/100;
	if(hmCostCap.get(new Integer(2))!=null)
		costlc = (((Float)hmCostCap.get(new Integer(2))).floatValue())/100;		
	if(hmCostCap.get(new Integer(3))!=null)
		mcpe = (((Float)hmCostCap.get(new Integer(3))).floatValue())/100;
	if(hmCostCap.get(new Integer(4))!=null)
		planlong = (((Float)hmCostCap.get(new Integer(4))).floatValue())/100;
	if(hmCostCap.get(new Integer(5))!=null)
		maxmtm = (((Float)hmCostCap.get(new Integer(5))).floatValue())/100;
	if(hmCostCap.get(new Integer(6))!=null)
		arfloat = (((Float)hmCostCap.get(new Integer(6))).floatValue());
	if(hmCostCap.get(new Integer(7))!=null)
		isomargin = (((Float)hmCostCap.get(new Integer(7))).floatValue());
	if(hmCostCap.get(new Integer(8))!=null)
		tdspfloat = (((Float)hmCostCap.get(new Integer(8))).floatValue());
	
	if(request.getParameter("term")==null)
	{
		if(request.getParameter("hidTerm")!=null && !request.getParameter("hidTerm").equalsIgnoreCase(""))
		{
		frm.setTxtContMnth(request.getParameter("hidTerm"));
		}
		else
		{
		List lstPreferenceTerms = (List)objPreferenceTermsDAO.getAllPreferenceTerms(Integer.parseInt(priceRunId));
		if(lstPreferenceTerms!=null&&lstPreferenceTerms.size()>0)
		frm.setTxtContMnth(((PriceRunCustomerTermsVO)lstPreferenceTerms.get(0)).getTerm()+"");
		else
		frm.setTxtContMnth(1+"");
		}
	}
	else
	{
		frm.setTxtContMnth(request.getParameter("term"));
	}
	HashMap mapdealvalue;
	if(priceRunId != null)
	{
		objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(Integer.parseInt(priceRunId));
		objPricingDashBoard = objPricingDAO.getDashBoardDetails(Integer.parseInt(priceRunId),Integer.parseInt(frm.getTxtContMnth()),esiId);
		
		objPriceRunHeader = objPriceRunCustomerVO.getPriceRunRef();
		dateGas = objPriceRunCustomerVO.getPriceRunRef().getGasPriceDate();
		fwdDate = objPriceRunHeader.getFwdCurveDate();
		avgGasPrice = objPriceRunHeader.getGasPrice();
	}
	int esiIdCount = objPricingDAO.getAllEsiIds(Integer.parseInt(priceRunId)).size();
	float contractkWh = objPricingDashBoard.getContractkWh();
	Vector chartDetails = objPricingDashBoard.getVecAnnualEnergyDetails();
	
	String fileName = objAnnualChart.annualchart(session,chartDetails,new PrintWriter(out));
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
	
	List deallevers =objDealLeversDAO.getDealLevers(Integer.parseInt(priceRunId),Integer.parseInt(frm.getTxtContMnth()));
	Iterator iter = deallevers.iterator();
	mapdealvalue =  new HashMap();
	while(iter.hasNext())
	{
		DealLevers objdeallevers = (DealLevers)iter.next();
		mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Float(objdeallevers.getValue()));
	}
	
		Collection objtaxcollection=new PricingDAO().getTaxRates();
	            Iterator ite = objtaxcollection.iterator();
	            float gr = 0;
	            float ct = 0;
	            float slt = 0;
	             while(ite.hasNext())
	             {
	                 TaxRatesVO rates = (TaxRatesVO)ite.next(); 
	                 switch (rates.getTaxType().getTaxTypeIdentifier())
	                 {
	                 case 1:
	                     ct = rates.getTaxRate();
	                     break;
	                 case 2:
	                     slt = rates.getTaxRate();
	                     break;
	                 case 3:
	                     gr = rates.getTaxRate();
	                     break;
	                 default:
	                     break;
	                 }
	             }
	     float tdspcharge$ =0;
	     float total = 0;
	     float fixedPrice = 0;
		 float mcpeadder = 0;
		 float diff = 0;
	
	hmTDSPs = objPricingDAO.getAllTDSPs(Integer.parseInt(priceRunId));
	hmCongestionZones = objPricingDAO.getAllCongestionZones(Integer.parseInt(priceRunId));
	hmEsiids = objPricingDAO.getAllEsiIds(Integer.parseInt(priceRunId));
	
  	String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
	String menuFtr[]= menupath.split("For");
	menupath = menupath.replaceFirst(menuFtr[1].trim(),(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null)?"All Customers":objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()+" - "+objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName());
	session.removeAttribute("home");
  	session.setAttribute("home",menupath);
  	
	pageContext.setAttribute("hmTDSPs",hmTDSPs);
	pageContext.setAttribute("hmCongestionZones",hmCongestionZones);
	pageContext.setAttribute("hmEsiids",hmEsiids);
	
	List lstSenValue = objPricingDAO.getSensitivityGraphDetails(Integer.parseInt(priceRunId.trim()),"",Integer.parseInt(frm.getTxtContMnth().trim()));
	int minValueIndex = objPricingDAO.getMinValue(lstSenValue);
	listDealLevers = objDealLeversDAO.getAllDealLevers();
	Iterator itr = listDealLevers.iterator();
	if(itr.hasNext())
	{
		DealLeversVO objDealLeversVO = (DealLeversVO)itr.next();
		overRide = objDealLeversVO.isOverRide();
	}
	float longterm = 0;
	longterm = ((Float)(mapdealvalue.get(new Integer(8)))).floatValue();
	float shapPremium = objPricingDashBoard.getShapingPremium();
	shapPremium = objPricingDashBoard.getShapingPremium()*((longterm+100)/100);
    objPricingDashBoard.setShapingPremium(shapPremium);

	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 260;
	else
		browserHt = 180;

	CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
	HashMap hmRes = new HashMap();
	HashMap hmValid = new HashMap();
	HashMap hmNotValid = new HashMap();
	hmRes = objCustEnergyComponentsDAO.getEngCompo(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
	hmValid = (HashMap)hmRes.get("valid");
	hmNotValid = (HashMap)hmRes.get("notvalid");
	

	double totalCostCapital = 0 ;
	double supplierMargin  = 0 ;
	double floatOnAr = 0 ;
	double floatOnTdsp = 0 ;
	double isoCrdit = 0 ;
	
	supplierMargin =(objPricingDashBoard.getEnergyCharge()*(1+planlong)*((Integer.parseInt(frm.getTxtContMnth().trim())/2.0)+.5)*(maxmtm*(costlc/12)));
	floatOnAr =(objPricingDashBoard.getAncillaryServices() + (objPricingDashBoard.getEnergyCharge()*(1+planlong)))*Interestrate*(arfloat/365.0);
	floatOnTdsp =(objPricingDashBoard.getTdspCharges() * Interestrate * (tdspfloat/365.0));
	isoCrdit =((objPricingDashBoard.getAncillaryServices()+(objPricingDashBoard.getEnergyCharge()*mcpe))*Interestrate*(isomargin/365.0));
	
	totalCostCapital = supplierMargin+floatOnAr+floatOnTdsp+isoCrdit;

%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/slider.js"></script>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" >
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<link rel="icon" href="<%=request.getContextPath()%>/images/animated_favicon1.gif" type="image/gif" >
<script>
 var localESIID = '';
   var tdspcharge =0;
   var total =0;
   var fixed =0;
   var costCapital =0;
   var processFlag = true;
function scrollDiv(){
	var a = document.getElementById('dvMultOffHead');
	var b = document.getElementById('dvMultOffVal');
	a.scrollRight = b.scrollRight;
	a.scrollLeft = b.scrollLeft;
}
function display()
{
if( document.getElementById('applytax').checked == true)
  {
   document.getElementById('tax2').style.display = 'block';
  document.getElementById('tax3').style.display = 'block';
   document.getElementById('tax4').style.display = 'none';
    document.getElementById('tax5').style.display = 'none';
  }
  else
  {
  
  document.getElementById('tax2').style.display = 'none';
  document.getElementById('tax3').style.display = 'none';
   document.getElementById('tax4').style.display = 'block';
    document.getElementById('tax5').style.display = 'block';
  }
}
function showHideSpldash(tableId,imgId,divlist){
	var table = document.getElementById(tableId);
	var img = document.getElementById(imgId);
	var div = document.getElementById(divlist);
	var showImg = "<%=request.getContextPath()%>/images/show.gif";
	var hideImg = "<%=request.getContextPath()%>/images/hide.gif";
	
   if (table.style.display == "none")
	{
		table.style.display = "block";
		img.src = hideImg;
		img.title = "Hide";		
	}
	else
	{
		table.style.display = "none";
		img.src = showImg;
		img.title = "Show";		
	}
	loaddefault();
	
}
function navtab(clicked, a, b){
	var dash = document.getElementById('tblDashBoard');
	var graph = document.getElementById('tblGraph');
	var multoff = document.getElementById('tblMultOff');
	
	clicked.className = 'subtab_on';
	a.className = 'subtab_off';
	b.className = 'subtab_off';
	if(clicked.id == 'set1'){
		dash.style.display = 'block';
		graph.style.display = 'none';
		multoff.style.display = 'none';
	}
	else if(clicked.id == 'set2'){
		dash.style.display = 'none';
		graph.style.display = 'block';
		multoff.style.display = 'none';
	}
	else if(clicked.id == 'set3'){
		dash.style.display = 'none';
		graph.style.display = 'none';
		multoff.style.display = 'block';
	}
}

function incmonth(type){
	var txt = document.forms[0].txtContMnth;
	if(type == "increment"){
		if(parseInt(txt.value,10) == 60)
		{
			return;
		}
		else{
			txt.value = parseInt(txt.value,10) + 1 ;
		}
	}
	else{
		if(parseInt(txt.value,10) == 1)
			return;
		else
			txt.value = parseInt(txt.value,10) - 1 ;	
	}
	servletaccess('month');
}
var A_TPL3h = {
		'b_vertical' : false,
		'b_watch': true,
		'n_controlWidth': 70,
		'n_controlHeight': 12,
		'n_sliderWidth': 13,
		'n_sliderHeight': 11,
		'n_pathLeft' : 1,
		'n_pathTop' : 0,
		'n_pathLength' :  57,
		's_imgControl': '<%=request.getContextPath()%>/images/sldr2h_bg.gif',
		's_imgSlider': '<%=request.getContextPath()%>/images/sldr2h_sl.gif',
		'n_zIndex': 1
	}

function callLoadCombo()
{
	var temp = document.forms[0];
	var tdsp = temp.tdsps.value;
	var zone = temp.congestionZones.value;
	if(parseInt(tdsp,10)<0)
	return false;
	
	var url = '<%=request.getContextPath()%>/servlet/dashBoardComboContent';
	var param = 'TDSP='+tdsp+'&Zone='+zone+'&priceRunId='+<%=priceRunId%>+'&divTopCurPos='+divTopCurPos+'&divLeftCurPos='+divLeftCurPos;
	if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadCombo});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadCombo});
				}
			}
}
function loadCombo(req){
var a =req.responseText.split("@#@#");
document.getElementById('congestion').innerHTML = a[0];
document.getElementById('cmbesiid').innerHTML = a[1];
localESIID = a[2];
}
function callEsiIdCombo()
{
	var temp = document.forms[0];
	var Zones = temp.congestionZones.value;
	var tdsp = temp.tdsps.value;
	if(parseInt(Zones,10)<0)
	return;
	var url = '<%=request.getContextPath()%>/servlet/dashBoardComboESIID';
	var param = 'Zone='+Zones+'&TDSP='+tdsp+'&priceRunId='+<%=priceRunId%>+'&divTopCurPos='+divTopCurPos+'&divLeftCurPos='+divLeftCurPos;
	if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadComboESIID});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadComboESIID});
				}
			}
}
function loadComboESIID(req){
var a =req.responseText.split("@@@");
document.getElementById('cmbesiid').innerHTML = a[0];
localESIID = a[1];
}
function servletaccess(save)
	{
	
		if(!processFlag)
		{
		  alert('Wait..');
		}
		else
		{
		processFlag = false;
	    document.getElementById('contractfail').style.display = 'none';
		var temp = document.forms[0];
		var totCostCap = '<%=priceRunRefNo%>';
		
		var term = temp.txtContMnth.value;
		var zone = temp.congestionZones.value;
		var tdsp = temp.tdsps.value;
		var timeanddate = new Date();
		var objSourceElement = temp.esiids;
		var esiId = new Array();
		var x = 0;
         for (var i = 0; i < objSourceElement.length; i++) 
             {
           		if (objSourceElement.options[i].selected) 
               		{
             			 if(objSourceElement.options[i].value != 0)
             			 {
             			 	esiId[x] = objSourceElement.options[i].value;
             			 	x++;
             			 }
               		}
	         }
	         
		if(save == 'true')
		{
		var Cust = temp.sliderValueCust.value;
		var addl = temp.sliderValueAddl.value;
		var Agnt = temp.sliderValueAgnt.value;
		var agg = temp.sliderValueAgg.value;
		var bW = temp.sliderValueBW.value;
		var other = temp.sliderValueOther.value;
		var margin = temp.sliderValueMargin.value;
		var lngterm = temp.sliderValueShp.value;
		var param = 'Term='+term+'&Save='+save+'&esiId='+esiId+'&Zone='+zone+'&TDSP='+tdsp+'&Cust='+Cust+'&addl='+addl+'&Agnt='+Agnt+'&agg='+agg+'&bW='+bW+'&other='+other+'&margin='+margin+'&date&time='+timeanddate+'&priceRunId='+<%=priceRunId%>+'&longterm='+lngterm+'&costcapital='+totCostCap;
		}
		else
		{
		  var param = 'Term='+term+'&esiId='+esiId+'&Zone='+zone+'&TDSP='+tdsp+'&Save='+save+'&date&time='+timeanddate+'&priceRunId='+<%=priceRunId%>+'&costcapital='+totCostCap;
		}
			var url = '<%=request.getContextPath()%>/servlet/dashBoardContent';
						
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
			}
			else if (window.ActiveXObject) // IE
			{
			    showload('yes');
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
				}
			}
			}
	}

	function showResponse(req)
	{
	  showload('no');
	try{
		processFlag = true;
		var a =req.responseText.split("@@@");		
		var temp = document.forms[0];
		temp.sliderValueCust.value = a[3];
		temp.sliderValueAddl.value = a[4];
		temp.sliderValueAgnt.value = a[5];
		temp.sliderValueAgg.value = a[6];
		temp.sliderValueBW.value = a[7];
		temp.sliderValueOther.value = a[8];
		temp.sliderValueMargin.value = a[9];
		temp.sliderValueShp.value = a[24];
		
	    A_SLIDERS[0].f_setValue(a[3]);
		A_SLIDERS[1].f_setValue(a[4]*10);
		A_SLIDERS[2].f_setValue(a[24]);
		A_SLIDERS[3].f_setValue(a[5]*100000);
		A_SLIDERS[4].f_setValue(a[6]*10000);
		A_SLIDERS[5].f_setValue(a[7]*10000);
		A_SLIDERS[6].f_setValue(a[8]*10000);
		A_SLIDERS[7].f_setValue(a[9]*10000);
		
		myProgBar.setBar(1.0);       
        myProgBar.setBar(parseFloat(a[10]),true);  
        myProgBar.setBar(parseFloat(1-a[10]),false); 
        myProgBar.setCol('#00CC00');

        document.getElementById('slogan').innerText = a[11];
        document.getElementById('totalEsiid').innerText = a[12];
		document.getElementById('annualkwh').innerText = a[13];
		document.getElementById('annualkw').innerText = a[14];
		document.getElementById('loadfactor').innerText = a[15];
		document.getElementById('Dashcontent').innerHTML = a[0];
		document.getElementById('ContractKWH').innerText = a[1];
		document.getElementById('piechart').innerHTML = a[2];
		document.getElementById('annualchart').innerHTML = a[16];
		document.getElementById('sensitivityChart').innerHTML = a[22];
		if(a[23]!="")
			document.getElementById('minValueMsg').innerText = a[23]+" ¢/kWh).";
		else
			document.getElementById('minValueMsg').innerHTML= "&nbsp;";
		  localESIID = a[21];
          tdspcharge =a[17];
          total =a[18];
		  fixed =a[19];		  
		 
		  costCapital = a[25];
		  
		document.getElementById('comboEsiid').style.display = 'none';
		var showImg = "<%=request.getContextPath()%>/images/show.gif";
		document.getElementById('imgShowHide3').src = showImg;
		document.getElementById('imgShowHide3').title = "Show";
		
          
		}
		catch(err){
		
			alert(err.description);
		}
		processFlag = true;
		setColor();
		
	}
  function checkEnter(e,message,id)
   { 
      var characterCode;
          e = event
          characterCode = e.keyCode ;
     if( (characterCode>=48) && (characterCode<=57) || characterCode == 13 || characterCode == 46)
     {
     	if(characterCode == 13 && message=="term")
        	 { 
	    		 servletaccess('month')
			     return false;
	         }
    	  else if(message=="term" && characterCode == 46)
    	     {
        	 	return false; 
	         }
	        else if( message == "lever" && characterCode == 46)
	         {
		         var dot = document.getElementById(id).value.split(".");
		         if(dot.length>1)
		         {
			         return false;
		         }
		         else
		         {
		         	return true;
		         }
	         }
	         else
	         {
	         return true;
	         }
	  }
	  else
	  {
	  return false;
	  }
   }

 function callcpe()
 {
 	if(!processFlag)
	{
	  alert('Wait..');
	}
	else
	{
    var temp = document.forms[0];
    temp.formActions.value = "cpe";
    temp.priceRunId.value = '<%=priceRunId%>';
    temp.submit();
    }
 } 
 function showload(mess)
 {
 	if(mess=='yes')
 		document.getElementById('loadimage').style.display = 'block';
	 else
 		document.getElementById('loadimage').style.display = 'none';
 }
 function overrideChk(chk)
{	
	if(!processFlag)
	{
	  alert('Wait..');
	}
	else
	{
	processFlag = false;
	var ele = chk.checked;
    var url = "<%=request.getContextPath()%>/servlet/updateOverride";
    var param = "override="+ele+'&priceRunId='+<%=priceRunId%>;
    var req = new ActiveXObject("Microsoft.XMLHTTP");
    if (req)
    {
        req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: empty});
    }
    }
}

/* don't delete the method because this is response for overrideChk() method */
function empty(req)
{
	processFlag = true;
	servletaccess('false');
   
}

function loadOverRide()
{
	
	var temp = document.forms[0];
	temp.overrideCheck.checked = <%=overRide%>;
	setColor();

}
function setColor()
{
	try
	{
		
	if(<%=hmNotValid.containsKey(new Integer(1))%>)
		document.getElementById('1').style.backgroundColor='#FFDDDD';

	if(<%=hmNotValid.containsKey(new Integer(2))%>)
		document.getElementById('2').style.backgroundColor='#FFDDDD';
	
	if(<%=hmNotValid.containsKey(new Integer(3))%>)
		document.getElementById('3').style.backgroundColor='#FFDDDD';	
	
	if(<%=hmNotValid.containsKey(new Integer(4))%>)
		document.getElementById('4').style.backgroundColor='#FFDDDD';	
		
	if(<%=hmNotValid.containsKey(new Integer(5))%>)
		document.getElementById('5').style.backgroundColor='#FFDDDD';	
	
	if(<%=hmNotValid.containsKey(new Integer(6))%>)
		document.getElementById('6').style.backgroundColor='#FFDDDD';
	
	if(<%=hmNotValid.containsKey(new Integer(7))%>)
		document.getElementById('7').style.backgroundColor='#FFDDDD';
	
	if(<%=hmNotValid.containsKey(new Integer(8))%>)
		document.getElementById('8').style.backgroundColor='#FFDDDD';
	
	if(<%=hmNotValid.containsKey(new Integer(9))%>)
		document.getElementById('9').style.backgroundColor='#FFDDDD';	
	
	if(<%=hmNotValid.containsKey(new Integer(10))%>)
		document.getElementById('10').style.backgroundColor='#FFDDDD';	
	
	if(<%=hmNotValid.containsKey(new Integer(11))%>)
		document.getElementById('11').style.backgroundColor='#FFDDDD';		
	
	if(<%=hmNotValid.containsKey(new Integer(12))%>)
		document.getElementById('12').style.backgroundColor='#FFDDDD';
	
	if(<%=hmNotValid.containsKey(new Integer(13))%>)
		document.getElementById('13').style.backgroundColor='#FFDDDD';
	}catch(err)
	{
		alert(err.description);
	}
}
function callpage(pagename)
{
	if(!processFlag)
	{
	  alert('Wait..');
	}
	else
	{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/'+pagename+'.do?priceRunId=<%=frm.getRunRefId()%>&PriceRunRefNo=<%=priceRunRefNo%>';
	var objSourceElement = temp.esiids;
	if(localESIID=="")
	{
		var esiId = new Array();
		var x=0;
	         for (var i = 1; i <objSourceElement.length;i++) 
	             {
	             	 if(objSourceElement.options[i].value != 0)
	             	  {
	             		esiId[x] = objSourceElement.options[i].value;
	             		x++;
	             	  }
	               }
	               localESIID = esiId;
	  }
	temp.strESIID.value = localESIID;
	temp.hidTerm.value = temp.txtContMnth.value;
	temp.submit();
	}catch(err)
	{
	alert(err.description);
	}
	}
}

var divTopCurPos = "";
var divLeftCurPos = "";
function loaddefault()
{
	var curleft = curtop = 0;
    var obj = document.getElementById('imgShowHide3');
       if (obj.offsetParent)
          {
            curleft = obj.offsetLeft;
            curtop = obj.offsetTop;
            while (obj = obj.offsetParent)
            {
              curleft += obj.offsetLeft
              curtop += obj.offsetTop
            }
          }
var pic = document.getElementById('imgShowHide3');
var w = pic.offsetWidth;

	document.getElementById('comboEsiid').style.top = curtop - 117;	
	document.getElementById('comboEsiid').style.left = curleft - w  ;
	divTopCurPos = curtop - 117;
	divLeftCurPos = curleft - w ;
//setColor();
}
</script>
<script src="PATH TO SCRIPT/progressbar.js" type="text/javascript"></script>
</head>
<div id ="loadimage" style="overflow:auto; position:absolute; top:68px; left:891; display:none; " ><img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle"></div>
<body onload='loadOverRide();loaddefault();'> 
<html:form action="dashBoard"  method="post"> <html:hidden property="runRefId"/> <html:hidden property ="formActions"/> 
<input type='hidden' name='strESIID'/> 
<input type="hidden" name="custID"/> 
<input type='hidden' name='hidTerm'/> 
<input type='hidden' name='priceRunId'/> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <!-- Menu Start --> 
      <jsp:include page="../menu.jsp"/> 
      <!-- Menu End --> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title" > Dashboard </td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="83" class="subtab_on" id="set1">Dashboard</td> 
          <td width="100" class="subtab_off" ><a href="javascript:callpage('aggLoadProfile');">Aggregated&nbsp;LP</a></td> 
          <td> <table  border="0" cellspacing="0" cellpadding="0" width="100%"> 
              <tr> 
                <td width="235">&nbsp</td> 
                <td width="20"><input type="checkbox" name="overrideCheck" id='checkValue' onClick ="overrideChk(this);"></td> 
                <td width="1">&nbsp;:</td> 
                <td style="font:'Times New Roman', Times, serif;color:#0033CC;"><b>&nbsp;Deal Adjustments Override</b></td> 
              </tr> 
            </table></td> 
        </tr> 
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr > 
          <td width ="400" class='error' id = 'contractfail' style="display:none">Error in exporting Price Quote details.</td> 
          <td align='right' class='fieldata' style="color:#0033CC;" id ='minValueMsg'><%if(minValueIndex<=3)minValueIndex = 3;%>
          <%=("Note: "+minValueIndex+" month term has the lowest price ("+dnf.format(((Double)lstSenValue.get(minValueIndex-1)).doubleValue()*100)+" ¢/kWh).")%>&nbsp;</td> 
        </tr> 
      </table>
      <div style="height:expression((document.body.clientHeight - <%=browserHt%>) + 'px'); width:100%; overflow:auto;">  
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr id="tblDashBrd"> 
          <td> <fieldset id="fsPriceInfo"> 
            <legend style="color:#0033CC">Pricing Info - Price RunId : <%=priceRunRefNo%></legend> 
            <table width="100%" cellpadding="0" cellspacing="0"> 
              <tr> 
                <td width="117" class="fieldtitle">Customer Name </td> 
                <td width="4" class="fieldtitle">:</td> 
                <td width="217" class="fieldata"><%= objPricingDashBoard.getCustomerName()%></td> 
                <td width="112" class="fieldtitle">Annual kWh </td> 
                <td width="4" class="fieldtitle">:</td> 
                <td width="143" class="fieldata" id = "annualkwh"><%=df.format(objPricingDashBoard.getAnnualkWh())%> </td> 
                <td width="171" class="fieldtitle">Start Month</td> 
                <td width="4" class="fieldtitle">:</td> 
                <td width="250" class="fieldata"><%= mnthFormat.format(objPricingDashBoard.getContractStartMonth())%></td> 
              </tr> 
              <tr> 
                <td class="fieldtitle">Contract kWh </td> 
                <td class="fieldtitle">:</td> 
                <td class="fieldata" id = "ContractKWH"><%=df.format(objPricingDashBoard.getContractkWh())%></td> 
                <td class="fieldtitle">Annual kW </td> 
                <td class="fieldtitle">:</td> 
                <td class="fieldata" id = "annualkw"><%=dnf.format(objPricingDashBoard.getMaxDemandkW())%></td> 
                <td  class="fieldata" colspan="5" style="color:#0033CC;">Forward Price Curve Imported on <%=fwdDate==null?"":gs.format(fwdDate)%> </td> 
              </tr> 
              <tr> 
                <td class="fieldtitle">Total ESI ID </td> 
                <td class="fieldtitle">:</td> 
                <td class="fieldata" id = "totalEsiid"><%= objPricingDashBoard.getNoOfEsiIds()%> of <%=esiIdCount%></td> 
                <td class="fieldtitle">Load Factor %</td> 
                <td class="fieldtitle">:</td> 
                <td class="fieldata" id = "loadfactor"><%=dnf.format(objPricingDashBoard.getLoadFactorPercentage())%></td> 
                <td class="fieldata" colspan="5" style="color:#0033CC;">12-Month&nbsp;Natural&nbsp;Gas&nbsp;Forward&nbsp;Price&nbsp;on&nbsp;<%=dateGas==null?"":gs.format(dateGas)%>:&nbsp;<%=dnf.format(avgGasPrice)%>&nbsp;$/MMBtu</td> 
              </tr> 
            </table> 
            </fieldset> 
            <table width="100%" cellpadding="0" cellspacing="0"> 
              <tr> 
                <td><a href="javascript:showHideSpldash('fsPriceInfo','imgShowHide2','comboEsiid')"><img src="<%=request.getContextPath()%>/images/hide.gif" name="imgShowHide" width="50" height="10" border="0" id="imgShowHide2"></a></td> 
              </tr> 
            </table> 
            <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="tblSearch"> 
              <tr> 
                <td width="113" class="search">Contract Months </td> 
                <td width="4" class="search">:</td> 
                <td width="83" class="search"><a href="javascript:incmonth('decrement')"><img src="<%=request.getContextPath()%>/images/reduce.gif" width="11" height="11" align="absmiddle" border="0"></a> <html:text property="txtContMnth" styleClass="textbox" size="4" onkeypress="return checkEnter(event,'term','')" value="<%= frm.getTxtContMnth()%>"/> <a href="javascript:incmonth('increment')"><img src="<%=request.getContextPath()%>/images/increase.gif" width="11" height="11" border="0" align="absmiddle"></a></td> 
                <td width="46" class="search">TDSP</td> 
                <td width="4" class="search">:</td> 
                <td width="113" class="search"> <html:select property="tdsps" styleClass="Combo" onchange="callLoadCombo();servletaccess('TDSP')"> <html:option value="0">Select one</html:option> <html:options collection="hmTDSPs" property="key" labelProperty="value"/> </html:select></td> 
                <td width="114" class="search">Congestion Zone </td> 
                <td width="4" class="search">:</td> 
                <td width="121" class="search" id="congestion"> <html:select property="congestionZones" styleClass="Combo" onchange="callEsiIdCombo();servletaccess('Zone')"> <html:option value="0">Select one</html:option> <html:options collection="hmCongestionZones" property="key" labelProperty="value"/> </html:select></td> 
                <td width="87" class="search"> Select ESIIDs</td> 
                <td width="62" class="search">:<a href="javascript:showHideSpl('comboEsiid','imgShowHide3')"><img src="<%=request.getContextPath()%>/images/show.gif" name="imgShowHide3" width="50" height="10" border="0" id="imgShowHide2" align="middle"></a></td> 
                <td width="222"  class="search" id ="cmbesiid"> <div id ="comboEsiid" style="overflow:auto; position:absolute; top:115px; left:780px; display:none;" ><html:select property="esiids" multiple="true" styleClass="Combo" size="10"onchange="servletaccess('false');" style='background-color:transparent;filter: alpha(opacity=65);-moz-opacity: 0.65; opacity: 0.65;'
> <html:option value="0">All</html:option> <html:options collection="hmEsiids" property="key" labelProperty="value"/> </html:select> </div></td> 
              </tr> 
            </table> 
            <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblDashBoard"> 
              <tr> 
                <td width="332" id="Dashcontent" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:1px solid #000; border-top:1px solid #000; border-right:1px solid #000; border-left:1px solid #000;" > 
                    <tr> 
                      <td height="18" colspan="2" align="center" class="tblheader">Components</td> 
                      <td width="70" class="tblheader" align="center">$</td> 
                      <td width="42" class="tblheader" align="center">$/kWh</td> 
                    </tr> 
                    <tr> 
                      <td  colspan="4" class="tbltitlebold" height="19">Energy Cost Components </td> 
                    </tr> 
					 
                    <tr id="1"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18" > Energy</td> 
                      <%total$ += objPricingDashBoard.getEnergyCharge();
                      if(contractkWh!=0)
                        total$KWh +=(objPricingDashBoard.getEnergyCharge()/contractkWh);
                      %> 
                      <td class="tbldata_dashboard" align="right"><%= dnf.format(objPricingDashBoard.getEnergyCharge())%></td> 
                      <td class="tbldata_dashboard" align="right"><%= ((contractkWh==0&&objPricingDashBoard.getEnergyCharge()==0)?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getEnergyCharge()/contractkWh))%></td> 
                    </tr> 
                    <tr id="2"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Shaping Premium </td> 
                      <%total$ += objPricingDashBoard.getShapingPremium();
                       if(contractkWh!=0)
                        total$KWh += contractkWh==0?0.0:(objPricingDashBoard.getShapingPremium()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard" align="right"><%= dnf.format(objPricingDashBoard.getShapingPremium())%></td> 
                      <td class="tbldata_dashboard" align="right"><%= (contractkWh==0&&objPricingDashBoard.getShapingPremium()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getShapingPremium()/contractkWh))%></td> 
                    </tr> 
                    <tr id="13"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18" > Cost of Capital</td> 
                      <%total$ += totalCostCapital;
                      if(contractkWh!=0)
                        total$KWh +=(totalCostCapital/contractkWh);
                      %> 
                      <td class="tbldata_dashboard" align="right"><%= dnf.format(totalCostCapital)%></td> 
                      <td class="tbldata_dashboard" align="right"><%= ((contractkWh==0&&totalCostCapital==0)?"0.0000":contractkWh==0?"-":tnf.format(totalCostCapital/contractkWh))%></td> 
                    </tr> 
                    <tr id="3"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Volatility Premium</td> 
                      <%total$ += (objPricingDashBoard.getVolatilityPremium());
                       if(contractkWh!=0)
                        total$KWh += (objPricingDashBoard.getVolatilityPremium()/(contractkWh));
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getVolatilityPremium())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&objPricingDashBoard.getVolatilityPremium()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getVolatilityPremium()/(contractkWh)))%></div></td> 
                    </tr> 
					 <tr id="4"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Ancillary Services </td> 
                      <%total$ += objPricingDashBoard.getAncillaryServices();
                       if(contractkWh!=0)
                        total$KWh += (objPricingDashBoard.getAncillaryServices()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getAncillaryServices())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%=(contractkWh==0&&objPricingDashBoard.getAncillaryServices()==0?"0.0000":contractkWh==0?"-": tnf.format(objPricingDashBoard.getAncillaryServices()/contractkWh))%></div></td> 
                    </tr> 
				    <tr id="5"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Intrazonal Congestion </td> 
                      <%total$ += objPricingDashBoard.getIntraZonalCongestion();
                       if(contractkWh!=0)
                        total$KWh += (objPricingDashBoard.getIntraZonalCongestion()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getIntraZonalCongestion())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&objPricingDashBoard.getIntraZonalCongestion()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getIntraZonalCongestion()/contractkWh))%></div></td> 
                    </tr> 
                    <tr id="6"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Fees &amp; Regulatory </td> 
                      <%total$ += objPricingDashBoard.getFeesAndRegulatory();
                       if(contractkWh!=0)
                        total$KWh += (objPricingDashBoard.getFeesAndRegulatory()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getFeesAndRegulatory())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&objPricingDashBoard.getFeesAndRegulatory()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getFeesAndRegulatory()/contractkWh))%></div></td> 
                    </tr> 
                    <tr class="tbltitlebold"> 
                      <td  colspan="2" class="tbldata_dashboard" height="19">Sub-Total</td> 
                      <td class="tbldata_dashboard"><div align="right"><%=dnf.format(total$)%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%=tnf.format(total$KWh)%></div></td> 
                    </tr> 
                    <tr> 
                      <td  colspan="4" class="tbltitlebold" height="19">OH &amp; Deal Components </td> 
                    </tr> 
                    <tr id="7"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Customer Charge </td> 
                      <%oHtotal$ = objPricingDashBoard.getCustomerCharge()*((Float)(mapdealvalue.get(new Integer(1)))).floatValue();
                       if(contractkWh!=0)
                        oHtotal$KWh = (objPricingDashBoard.getCustomerCharge()*((Float)(mapdealvalue.get(new Integer(1)))).floatValue()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getCustomerCharge()*((Float)(mapdealvalue.get(new Integer(1)))).floatValue())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&objPricingDashBoard.getCustomerCharge()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getCustomerCharge()*((Float)(mapdealvalue.get(new Integer(1)))).floatValue()/contractkWh))%></div></td> 
                    </tr> 
                    <tr id="8"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Additional&nbsp;Volatility&nbsp;Premium </td> 
                      <% double avp = objPricingDashBoard.getAdditionalVolatilityPremium()*((Float)(mapdealvalue.get(new Integer(7)))).floatValue()/100;
                      oHtotal$ += avp ;
                       if(contractkWh!=0)
                        oHtotal$KWh += (avp/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(avp)%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&avp==0?"0.0000":contractkWh==0?"-":tnf.format(avp/contractkWh))%></div></td> 
                    </tr> 
                    <tr id="9"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Sales Agent Fee </td> 
                      <% 
                       pivalues.put("sales",new Double (objPricingDashBoard.getSalesAgentFee()*((Float)(mapdealvalue.get(new Integer(4)))).floatValue()));%> 
                      <%oHtotal$ +=objPricingDashBoard.getSalesAgentFee()*((Float)(mapdealvalue.get(new Integer(4)))).floatValue();
                       if(contractkWh!=0)
                        oHtotal$KWh += (objPricingDashBoard.getSalesAgentFee()*((Float)(mapdealvalue.get(new Integer(4)))).floatValue()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getSalesAgentFee()*((Float)(mapdealvalue.get(new Integer(4)))).floatValue())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&objPricingDashBoard.getSalesAgentFee()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getSalesAgentFee()*((Float)(mapdealvalue.get(new Integer(4)))).floatValue()/contractkWh))%></div></td> 
                    </tr> 
                    <tr id="10"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Aggregator Fee </td> 
                      <%oHtotal$ +=objPricingDashBoard.getAggregatorFee()*((Float)(mapdealvalue.get(new Integer(5)))).floatValue();
                       if(contractkWh!=0)
                        oHtotal$KWh += (objPricingDashBoard.getAggregatorFee()*((Float)(mapdealvalue.get(new Integer(5)))).floatValue()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getAggregatorFee()*((Float)(mapdealvalue.get(new Integer(5)))).floatValue())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&objPricingDashBoard.getAggregatorFee()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getAggregatorFee()*((Float)(mapdealvalue.get(new Integer(5)))).floatValue()/contractkWh))%></div></td> 
                    </tr> 
                    <tr id="11"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Bandwidth Charge </td> 
                      <%oHtotal$ +=objPricingDashBoard.getBandwidthCharge()*((Float)(mapdealvalue.get(new Integer(6)))).floatValue();
                       if(contractkWh!=0)
                        oHtotal$KWh += (objPricingDashBoard.getBandwidthCharge()*((Float)(mapdealvalue.get(new Integer(6)))).floatValue()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getBandwidthCharge()*((Float)(mapdealvalue.get(new Integer(6)))).floatValue())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%= (contractkWh==0&&objPricingDashBoard.getBandwidthCharge()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getBandwidthCharge()*((Float)(mapdealvalue.get(new Integer(6)))).floatValue()/contractkWh))%></div></td> 
                    </tr> 
                    <tr id="12"> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">Other Fee </td> 
                      <%oHtotal$ +=objPricingDashBoard.getOtherFee()*((Float)(mapdealvalue.get(new Integer(2)))).floatValue();
                       if(contractkWh!=0)
                        oHtotal$KWh += (objPricingDashBoard.getOtherFee()*((Float)(mapdealvalue.get(new Integer(2)))).floatValue()/contractkWh);
                       %> 
                      <td class="tbldata_dashboard"><div align="right"><%= dnf.format(objPricingDashBoard.getOtherFee()*((Float)(mapdealvalue.get(new Integer(2)))).floatValue())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%=(contractkWh==0&&objPricingDashBoard.getOtherFee()==0?"0.0000":contractkWh==0?"-": tnf.format(objPricingDashBoard.getOtherFee()*((Float)(mapdealvalue.get(new Integer(2)))).floatValue()/contractkWh))%></div></td> 
                    </tr> 
                    <tr class="tbltitlebold"> 
                      <td  colspan="2" class="tbldata_dashboard" height="19">Sub-Total</td> 
                      <td class="tbldata_dashboard"><div align="right"><%=dnf.format(oHtotal$)%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%=tnf.format(oHtotal$KWh)%></div></td> 
                    </tr> 
                    <tr class="tbltitlebold"> 
                      <td  colspan="2" class="tbldata_dashboard" height="19">Total Cost and Overhead</td> 
                      <td class="tbldata_dashboard"><div align="right"><%=dnf.format(oHtotal$+total$)%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%=tnf.format(oHtotal$KWh+total$KWh)%></div></td> 
                    </tr> 

                    <tr class="tbltitlebold"> 
                      <td  colspan="2" class="tbldata_dashboard" height="19">Margin</td> 
                      <% pivalues.put("margin",new Double (objPricingDashBoard.getMargin()*((Float)(mapdealvalue.get(new Integer(3)))).floatValue()));%> 
                      <td class="tbldata_dashboard"><div align="right"><%=dnf.format(objPricingDashBoard.getMargin()*((Float)(mapdealvalue.get(new Integer(3)))).floatValue())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right"><%=(contractkWh==0&&objPricingDashBoard.getMargin()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getMargin()*((Float)(mapdealvalue.get(new Integer(3)))).floatValue()/contractkWh))%></div></td> 
                    </tr> 
                    <tr id="fxdPrice" class="tbltitlebold"> 
                      <td  colspan="2" class="tbldata_dashboard" height="19" style="color:#009900; font-weight: bold;">Fixed Price to Customer</td> 
                      <%
                      double fptc= objPricingDashBoard.getMargin()*((Float)(mapdealvalue.get(new Integer(3)))).floatValue()+oHtotal$+total$;
                       pivalues.put("Fixed",new Double (fptc));
                       fixedPrice = (float)fptc;
                       
                       diff = objPricingDashBoard.getEnergyCharge()-objPricingDashBoard.getEnergyChargeWithOutLoss();
                       System.out.println("diff in page :" + diff);
                       %> 
                      <td class="tbldata_dashboard" style="color:#009900;"><div align="right"><strong><%=dnf.format(fptc)%></strong></div></td> 
                      <td class="tbldata_dashboard" style="color:#009900;"><div align="right"><strong><%=(contractkWh==0&&fptc==0?"0.0000":contractkWh==0?"-":tnf.format(fptc/contractkWh))%></strong></div></td> 
                    </tr> 
                    <tr class="tbltitlebold"> 
                      <td  colspan="2" class="tbldata_dashboard" height="19" style="color:#009900; font-weight: bold;">Retail Adder for MCPE </td> 
                      <td class="tbldata_dashboard" style="color:#009900;"><div align="right"><%=dnf.format(fptc-totalCostCapital-avp-objPricingDashBoard.getShapingPremium()-objPricingDashBoard.getEnergyCharge()+diff)%></div></td> 
                      <td class="tbldata_dashboard" style="color:#009900;"><div align="right"><%=(contractkWh==0&&((fptc-totalCostCapital-avp-objPricingDashBoard.getShapingPremium()-objPricingDashBoard.getEnergyCharge()+diff)==0)?"0.0000":contractkWh==0?"-":tnf.format((fptc-totalCostCapital-avp-objPricingDashBoard.getShapingPremium()-objPricingDashBoard.getEnergyCharge()+diff)/contractkWh))%></div></td> 
                    </tr> 
                    <tr> 
                      <td  colspan="2" class="tbldata_dashboard" height="18">TDSP Charges </td> 
                      <% pivalues.put("TDSPCharge",new Double (objPricingDashBoard.getTdspCharges()));
                      tdspcharge$ = objPricingDashBoard.getTdspCharges();
                      %> 
                      <td class="tbldata_dashboard"><div align="right" ><%= dnf.format(objPricingDashBoard.getTdspCharges())%></div></td> 
                      <td class="tbldata_dashboard"><div align="right" ><%= (contractkWh==0&&objPricingDashBoard.getTdspCharges()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getTdspCharges()/contractkWh))%></div></td> 
                    </tr> 
                    <tr class="tbltitlebold"> 
                      <td  colspan="2" class="tbldata_dashboard" height="19">Bundled Cost </td> 
                      <td class="tbldata_dashboard" align="right"><%=dnf.format(fptc+objPricingDashBoard.getTdspCharges())%></td> 
                      <td class="tbldata_dashboard" align="right"><%=(contractkWh==0&&(fptc+objPricingDashBoard.getTdspCharges())==0?"0.0000":contractkWh==0?"-":tnf.format((fptc+objPricingDashBoard.getTdspCharges())/contractkWh))%></td> 
                    </tr> 
                    <tr class="tbltitlebold"> 
                     <td  colspan="2" class="tbldata_dashboard" height="19">Bundled&nbsp;Cost&nbsp;W/Taxes</td> 
                     <%if(objPriceRunCustomerVO.isTaxExempt()){%>
                      <td class="tbldata_dashboard" align="right"><%=dnf.format((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))%></td> 
                      <td class="tbldata_dashboard" align="right"><%=(contractkWh==0&&((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))==0?"0.0000":contractkWh==0?"-":tnf.format(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))/contractkWh))%></td> 
 					<%}else{%>
					  <td class="tbldata_dashboard" align="right"><%=dnf.format((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)))%></td> 
                      <td class="tbldata_dashboard" align="right"><%=(contractkWh==0&&((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)))==0)?"0.0000":contractkWh==0?"-":tnf.format((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100))/contractkWh)%></td> 
                      <%
                      }
                      total = (float)(fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)*(1+(slt/100)+(ct/100)));
                      %> 
					  <%
						 if(hmNotValid.containsKey(new Integer(1)))
							fixedPrice =fixedPrice - objPricingDashBoard.getEnergyCharge();
						if(hmNotValid.containsKey(new Integer(2)))
							fixedPrice =fixedPrice -  objPricingDashBoard.getShapingPremium();
						if(hmNotValid.containsKey(new Integer(3)))
							fixedPrice =fixedPrice -  objPricingDashBoard.getVolatilityPremium();
						if(hmNotValid.containsKey(new Integer(4)))
							fixedPrice =fixedPrice -  objPricingDashBoard.getAncillaryServices();
						if(hmNotValid.containsKey(new Integer(5)))
							fixedPrice =fixedPrice -  objPricingDashBoard.getIntraZonalCongestion();
						if(hmNotValid.containsKey(new Integer(6)))
							fixedPrice =fixedPrice -  objPricingDashBoard.getFeesAndRegulatory();
						
						if(hmNotValid.containsKey(new Integer(7)))
							{
								fixedPrice =fixedPrice -  objPricingDashBoard.getCustomerCharge()*((Float)(mapdealvalue.get(new Integer(1)))).floatValue();
							}
						if(hmNotValid.containsKey(new Integer(8)))
							{
							fixedPrice =fixedPrice -   objPricingDashBoard.getAdditionalVolatilityPremium()*((Float)(mapdealvalue.get(new Integer(7)))).floatValue()/100;
							}
						if(hmNotValid.containsKey(new Integer(9)))
							{
							fixedPrice =fixedPrice -  objPricingDashBoard.getSalesAgentFee()*((Float)(mapdealvalue.get(new Integer(4)))).floatValue();
							}
						if(hmNotValid.containsKey(new Integer(10)))
							{
							fixedPrice =fixedPrice -  objPricingDashBoard.getAggregatorFee()*((Float)(mapdealvalue.get(new Integer(5)))).floatValue();
							}
						if(hmNotValid.containsKey(new Integer(11)))
							{
							fixedPrice =fixedPrice -  objPricingDashBoard.getBandwidthCharge()*((Float)(mapdealvalue.get(new Integer(6)))).floatValue();
							}
						if(hmNotValid.containsKey(new Integer(12)))
							{
							fixedPrice =fixedPrice -  objPricingDashBoard.getOtherFee()*((Float)(mapdealvalue.get(new Integer(2)))).floatValue();
							}
						if(hmNotValid.containsKey(new Integer(13)))
							{
							fixedPrice =fixedPrice -  (float)totalCostCapital;
							}
						try{
							 if(hmValid.containsKey(new Integer(3)))
								mcpeadder += objPricingDashBoard.getVolatilityPremium();
							 if(hmValid.containsKey(new Integer(4)))
								mcpeadder += objPricingDashBoard.getAncillaryServices();
							 if(hmValid.containsKey(new Integer(5)))
							    mcpeadder += objPricingDashBoard.getIntraZonalCongestion();
							 if(hmValid.containsKey(new Integer(6)))
						        mcpeadder += objPricingDashBoard.getFeesAndRegulatory();
							  if(hmValid.containsKey(new Integer(7)))
								{
								 mcpeadder += objPricingDashBoard.getCustomerCharge()*((Float)mapdealvalue.get(new Integer(1))).floatValue();
				                }
								 if(hmValid.containsKey(new Integer(9)))
								{
									mcpeadder += objPricingDashBoard.getSalesAgentFee()*((Float)mapdealvalue.get(new Integer(4))).floatValue();
								}
								if(hmValid.containsKey(new Integer(10)))
								{
									mcpeadder += objPricingDashBoard.getAggregatorFee()*((Float)mapdealvalue.get(new Integer(5))).floatValue();
								}
								if(hmValid.containsKey(new Integer(11)))
								{
									mcpeadder += objPricingDashBoard.getBandwidthCharge()*((Float)mapdealvalue.get(new Integer(6))).floatValue();
								}
								if(hmValid.containsKey(new Integer(12)))
								{
									mcpeadder += objPricingDashBoard.getOtherFee()*((Float)mapdealvalue.get(new Integer(2))).floatValue();
								}
							
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}

						%>
                    </tr> 
                  </table></td> 
                <td width="661" valign="top"><table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
                    <tr> 
                      <td width="258" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #000;"> 
                          <tr> 
                            <td  valign="top" colspan="2" class="tblheader" height="18"> Deal Adjustments</td> 
                            <td  valign="top" class="tblheader" height="18" align='center' onclick="servletaccess('true')" style="color:#0033CC; font-weight: bold;cursor:hand">Go</td> 
                          </tr> 
                          <tr > 
                            <td width="117" height="18"  class="fieldata">Cust&nbsp;Ch&nbsp;$/mnth</td> 
                            <td width="85"><script language="JavaScript">
	var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueCust',
		'n_minValue' : 1,
		'n_maxValue' : 50,
		'n_value' : <%=((Float)(mapdealvalue.get(new Integer(1)))).floatValue()%>,
		'n_step' : 1,
		'div_value' : 1,
		'f_precision' : 1
	}
	new slider(A_INIT3h, A_TPL3h);
       </script></td> 
                            <td width="53" align="right" id="txtcust"> <input name="sliderValue" align="right" id="sliderValueCust" type="Text" size="2" onkeypress="return checkEnter(event,'lever','sliderValueCust')" onChange="A_SLIDERS[0].f_setValue(this.value);"  class="tboxfullplain" value=<%=((Float)(mapdealvalue.get(new Integer(1)))).floatValue()%>> </td> 
                          </tr> 
                          <tr> 
                            <td height="18" class="fieldata">Addl.&nbsp;Volatilty %</td> 
                            <td><script language="JavaScript">
       var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueAddl',
		'n_minValue' : 0,
		'n_maxValue' : 100,
		'n_value' : <%=((Float)(mapdealvalue.get(new Integer(7)))).floatValue()*10%>,
		'n_step' : 1,
		'div_value' : 10,
		'f_precision' : 1
	}
	
	new slider(A_INIT3h, A_TPL3h);
       </script></td> 
                            <td width="53" id ="txtaddl" align="right"> <input name="sliderValue1" type="Text" class="tboxfullplain" id="sliderValueAddl" onkeypress="return checkEnter(event,'lever','sliderValueAddl')" onChange="A_SLIDERS[1].f_setValue(this.value*10);" value=<%=dnf.format(((Float)(mapdealvalue.get(new Integer(7)))).floatValue())%> size="2"  align="right"></td> 
                          </tr> 
						     <tr> 
                            <td height="18" class="fieldata">Weather&nbsp;Premium %</td> 
                            <td><script language="JavaScript">
       var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueShp',
		'n_minValue' : 0,
		'n_maxValue' : 1000,
		'n_value' :<%=((Float)(mapdealvalue.get(new Integer(8)))).floatValue()%>,
		'n_step' : 50,
		'div_value' : 1,
		'f_precision' : 1
	}
	
	new slider(A_INIT3h, A_TPL3h);
       </script></td> 
                            <td width="53" id ="txtShp" align="right"> <input name="sliderValue7" type="Text" class="tboxfullplain" id="sliderValueShp" onkeypress="return checkEnter(event,'lever','sliderValueShp')" onChange="A_SLIDERS[2].f_setValue(this.value);" value=<%=((Float)(mapdealvalue.get(new Integer(8)))).floatValue()%> size="2"  align="right"></td> 
                          </tr> 
                          <tr> 
                            <td  colspan="2">&nbsp; </td> 
                            <td class="fieldata" style="color:#0033CC; font-weight: bold;">$/kWh</td> 
                          </tr> 


						  <tr> 
                            <td height="18" class="fieldata">Sales&nbsp;Agent&nbsp;Fee</td> 
                            <td><script language="JavaScript">
	
	var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueAgnt',
		'n_minValue' : 0,
		'n_maxValue' : 2000,
		'n_value' : <%=((Float)(mapdealvalue.get(new Integer(4)))).floatValue()*100000%>,
		'n_step' : 1,
		'div_value' : 100000,
		'f_precision' : 5
	}
	new slider(A_INIT3h, A_TPL3h);
</script></td> 
                            <td width="53" id ='txtagnt' align="right"> <input name="sliderValue2"  align="right" type="Text" class="tboxfullplain" id="sliderValueAgnt" onkeypress="return checkEnter(event,'lever','sliderValueAgnt')" onChange="A_SLIDERS[3].f_setValue(this.value*100000);" value=<%=nf.format(((Float)(mapdealvalue.get(new Integer(4)))).floatValue())%> size="2"></td> 
                          </tr> 
                          <tr> 
                            <td height="18" class="fieldata">Aggregator&nbsp;Fee</td> 
                            <td><script language="JavaScript">
	
	var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueAgg',
		'n_minValue' : 0,
		'n_maxValue' : 500,
		'n_value' : <%=((Float)(mapdealvalue.get(new Integer(5)))).floatValue()*10000%>,
		'n_step' : 1,
		'div_value' : 10000,
		'f_precision' : 5
	}
	new slider(A_INIT3h, A_TPL3h);
</script></td> 
                            <td width="53" id ='txtagg' align="right"> <input name="sliderValue3" type="Text"  align="middle" class="tboxfullplain" id="sliderValueAgg" onkeypress="return checkEnter(event,'lever','sliderValueAgg')" onChange="A_SLIDERS[4].f_setValue(this.value*10000);" value=<%=nf.format(((Float)(mapdealvalue.get(new Integer(5)))).floatValue())%> size="2"></td> 
                          </tr> 
                          <tr> 
                            <td class="fieldata" height="18" >Bandwidth</td> 
                            <td><script language="JavaScript">
	var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueBW',
		'n_minValue' : 0,
		'n_maxValue' : 200,
		'n_value' :<%=((Float)(mapdealvalue.get(new Integer(6)))).floatValue()*10000%>,
		'n_step' : 1,
		'div_value' : 10000,
		'f_precision' : 5
	}
	new slider(A_INIT3h, A_TPL3h);
</script></td> 
                            <td width="53" id ='txtbw' align="right"> <input name="sliderValue4" type="Text"  align="right" class="tboxfullplain" id="sliderValueBW" onkeypress="return checkEnter(event,'lever','sliderValueBW')" onChange="A_SLIDERS[5].f_setValue(this.value*10000);" value=<%=nf.format(((Float)(mapdealvalue.get(new Integer(6)))).floatValue())%> size="2"></td> 
                          </tr> 
                          <tr> 
                            <td class="fieldata" height="18">Other&nbsp;Fee</td> 
                            <td><script language="JavaScript">
	
	var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueOther',
		'n_minValue' : 0,
		'n_maxValue' : 200,
		'n_value' : <%=((Float)(mapdealvalue.get(new Integer(2)))).floatValue()*10000%>,
		'n_step' : 1,
		'div_value' : 10000,
		'f_precision' : 5
	}
	new slider(A_INIT3h, A_TPL3h);
</script></td> 
                            <td width="53" id ='txtother' align="right"> <input name="sliderValue5" type="Text"  align="right" class="tboxfullplain" id="sliderValueOther" onkeypress="return checkEnter(event,'lever','sliderValueOther')" onChange="A_SLIDERS[6].f_setValue(this.value*10000);" value=<%=nf.format(((Float)(mapdealvalue.get(new Integer(2)))).floatValue())%> size="2"></td> 
                          </tr> 
                          <tr > 
                            <td class="fieldata">Margin</td> 
                            <td><script language="JavaScript">
	
	var A_INIT3h = {
		's_form' : 0,
		's_name': 'sliderValueMargin',
		'n_minValue' : 0,
		'n_maxValue' : 200,
		'n_value' : <%=((Float)(mapdealvalue.get(new Integer(3)))).floatValue()*10000%>,
		'n_step' : 1,
		'div_value' : 10000,
		'f_precision' : 5
	}
	new slider(A_INIT3h, A_TPL3h);
</script></td> 
                            <td width="53" id ='txtmargin' align="right" > <input name="sliderValue6" type="Text"  align="right" class="tboxfullplain" id="sliderValueMargin" onkeypress="return checkEnter(event,'lever','sliderValueMargin')" onChange="A_SLIDERS[7].f_setValue(this.value*10000);" value=<%=nf.format(((Float)(mapdealvalue.get(new Integer(3)))).floatValue())%> size="2"></td> 
                          </tr> 
                          <tr> 
                            <td  colspan="3"  valign="top" class="top_line" ><b> Attractiveness Index</b> </td> 
                          </tr> 
                          <tr> 
                            <td  valign="top" colspan="3"  align="center" class=""> <% double index = objAttractiveIndex.attaractiveIndex(contractkWh,objPricingDashBoard.getMargin()*((Float)(mapdealvalue.get(new Integer(3)))).floatValue(),objPricingDashBoard.getMargin()*((Float)(mapdealvalue.get(new Integer(3)))).floatValue()+oHtotal$+total$,objPricingDashBoard.getSalesAgentFee()*((Float)(mapdealvalue.get(new Integer(4)))).floatValue(),objPricingDashBoard.getLoadFactorPercentage());%> 
                              <script type="text/javascript">
var myProgBar = new progressBar(
			1,         //border thickness
			'#000000', //border colour
			'#F51049', //background colour
			'#FF0000', //bar colour
			150,       //width of bar (excluding border)
			10,        //height of bar (excluding border)
			1          //direction of progress: 1 = right, 2 = down, 3 = left, 4 = up
		);
           
		myProgBar.setBar(1.0);       
        myProgBar.setBar(<%=index%>,true);
        myProgBar.setBar(<%=1.00-index%>,false); 
        myProgBar.setCol('#00CC00'); 
	</script> </td> 
                          </tr> 
                          <tr> 
                            <td align="center" height="17" valign="middle"  class="tbldata_dashboard" style="font-weight:bold; " id='slogan' colspan="3"><%= objAttractiveIndex.getSlogan(1-index)%></td> 
                          </tr> 
                          <tr> 
                            <td  id="piechart" valign="top" colspan="3"><%String filenamepieChart = piechart.chart(session,pivalues,new PrintWriter(out));
String pieURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenamepieChart; %> 
                              <img src="<%= pieURL%>"  border=0 usemap="#<%=filenamepieChart%>"></td> 
                          </tr> 
                        </table></td> 
                      <td width="395" valign="top"><table width="100%"   border="0" cellpadding="0" cellspacing="0"> 
                          <tr> 
                            <td width="367"  valign="top" class="tbldata_dashboard" id='sensitivityChart' > <% 
                             String filenamesensitivityChart = sensitivitychart.chart(session,lstSenValue,new PrintWriter(out));
String lineURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenamesensitivityChart; %> 
                              <img src="<%= lineURL%>"  border=0 usemap="#<%=filenamesensitivityChart%>"> </td> 
                          </tr> 
                          <tr> 
                            <td width="400" valign="top"  class="tbldata_dashboard" id='annualchart'><img src="<%=graphURL%>"  border=0 usemap="#<%=fileName%>"></td> 
                          </tr> 
                        </table></td> 
                    </tr> 
                  </table></td> 
              </tr> 
            </table></td> 
        </tr> 
      </table>
      </div> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="btnbg"><input name="Submit" type="button" class="button" value="Back" onClick="window.open('<%=request.getContextPath()%>/jsp/pricerun/runresult.jsp','_self')"> 
            <input name="Submit5" type="button" class="button" value="Lock&nbsp;Deal" onclick="callContract('no')" > 
            <% if(objPreferenceProductsDAO.checkOtherThanEPP(objPriceRunCustomerVO.getPriceRunCustomerRefId()))
            {
            %> 
            <input name="Submit5" type="button" class="button" value="Lock&nbsp;All&nbsp;Deal" onclick="callContract('allDeal')" > 
            <%}%> 
            <input name="Submit3" type="button" class="button" value="Next" onClick="callpage('aggLoadProfile')"> 
            <input name="Submit2" type="button" class="button" value="Price Quote" onClick="callcpe()"> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
<script>



function callContract(message)
{
if(!processFlag)
 {
  alert('Wait..');
 }
  else
	{
	   processFlag = false;
	   var term = document.forms[0].txtContMnth.value;
	   var zone = document.forms[0].congestionZones.value;
	   var tdsp = document.forms[0].tdsps.value;
	   var objSourceElement = document.forms[0].esiids;
	   var totCostCap = <%=totalCostCapital%>;
	   var costCap=costCapital;
	   
	   if(tdspcharge==0)
	   {
	   tdspcharge = '<%=tdspcharge$%>';
	   total = '<%=total%>';
	   fixed = '<%=fixedPrice%>';
	   costCap = totCostCap;
	   }
	   var esiId = new Array();
	        var x = 0;
	         for (var i = 0; i < objSourceElement.length; i++) 
	             {
	           		if (objSourceElement.options[i].selected) 
	               		{
	             			 if(objSourceElement.options[i].value != 0)
	             			 {
	             			 	esiId[x] = objSourceElement.options[i].value;
	             			 	x++;
	             			 }
	               		}
		         }
	
	   var param = 'Term='+term+'&TDSPCharge='+tdspcharge+'&fixed='+fixed+'&esiId='+esiId+'&Zone='+zone+'&TDSP='+tdsp+'&product=fixed&priceRunId='+<%=priceRunId%>+'&message='+message+'&date='+new Date()+'&costcapital='+totCostCap+'&trmCapital='+costCap;
	   var url = '<%=request.getContextPath()%>/servlet/createContact';
	   showload('yes');
				if (window.XMLHttpRequest) // Non-IE browsers
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showContractResponse});
				}
				else if (window.ActiveXObject) // IE
				{
					req = new ActiveXObject("Microsoft.XMLHTTP");
					if (req)
					{
						req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showContractResponse});
					}
				}
	}
}
function showContractResponse(req)
{
 processFlag = true;
 showload('no');
 var result = req.responseText;
 if(result == 'Success')
 {
      document.getElementById('contractfail').style.display = 'block';
      document.getElementById('contractfail').className = 'message';
      document.getElementById('contractfail').innerText = "Price Quote Details are exported to database.";
  }
  else 
  {
  document.getElementById('contractfail').className = 'error';
  document.getElementById('contractfail').style.display = 'block';
  document.getElementById('contractfail').innerText = "  Error in exporting Price Quote details.";
  if(result != 'Failure')
    document.getElementById('contractfail').innerText = result;
   }
   
}

</script> 
</html:form> 
<iframe src="" style="display:none " id="iframe"> </iframe> 
</body>
</html:html>
<%}%>
