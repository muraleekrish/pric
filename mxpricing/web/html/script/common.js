// JavaScript Document

//Global Variables
var currentchecked = "None";
var currentcheckcount = 0;

//Show Hide script for Search palette;
function showHide(plt) {
	var table = document.getElementById(plt);
	var showImg = "images/show.gif";
	var hideImg = "images/hide.gif";
	var img = document.getElementById('imgShowHide');
	if(table.style.display == "none") {
		table.style.display = "block";
		img.src = hideImg;
		img.title = "Hide"
	}
	else {
		table.style.display = "none";
		img.src = showImg;
		img.title = "Show"
	}
}

// Show Hide Script for handling two in a page.
function showHideSpl(tableId,imgId){
	var table = document.getElementById(tableId);
	var img = document.getElementById(imgId);
	var showImg = "images/show.gif";
	var hideImg = "images/hide.gif";
	
	if (table.style.display == "none")
	{
		table.style.display = "block";
		img.src = hideImg;
		img.title = "Hide"
	}
	else
	{
		table.style.display = "none";
		img.src = showImg;
		img.title = "Show"
	}

}

function showHideSpl1(tableId,imgId,dvList){
	var table = document.getElementById(tableId);
	var img = document.getElementById(imgId);
	var dvl = document.getElementById(dvList);
	var showImg = "images/show.gif";
	var hideImg = "images/hide.gif";
	
	if (table.style.display == "none")
	{
		table.style.display = "block";
		dvl.style.height= "120px";
		img.src = hideImg;
		img.title = "Hide"
	}
	else
	{
		table.style.display = "none";
		dvl.style.height= "250px";
		img.src = showImg;
		img.title = "Show"
	}

}

// Script for Check & UnCheck All checkbox's
function checkAll(obj, check){
   var checkboxes = obj.getElementsByTagName("input");
   for (var i = 0;i < checkboxes.length;i++){
      if (checkboxes[i].type == "checkbox"){
         checkboxes[i].checked = check;
      }
   }
}

// Start Tab Menu

function tab(tabname, url) {
	var tabA = document.getElementById('t1');
	var tabB = document.getElementById('t2');
	var tabC = document.getElementById('t3');
	var tabD = document.getElementById('t4');
	var tabE = document.getElementById('t5');
	var tabF = document.getElementById('t6');
	var tabG = document.getElementById('t7');
	var tabH = document.getElementById('t8');
	
	var menuA = window.top.mainFrame.document.getElementById('tblMenuPricing');
	var menuB = window.top.mainFrame.document.getElementById('tblMenuCustomer');
	var menuC = window.top.mainFrame.document.getElementById('tblMenuSysSetting');
	var menuD = window.top.mainFrame.document.getElementById('tblMenuSecAdmin');
	
	if(tabname == 't1') {
		tabA.className = 'tab_on';
		tabB.className = 'tab_off';
		tabC.className = 'tab_off';
		tabD.className = 'tab_off';
		tabE.className = 'tab_off';
		tabF.className = 'tab_off';
		tabG.className = 'tab_off';
		tabH.className = 'tab_off';
		document.getElementById('imgTabALft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_on_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_off_rgt.gif';
		window.open(url,'mainFrame');
	}
	else if(tabname == 't2') {
		tabA.className = 'tab_off';
		tabB.className = 'tab_on';
		tabC.className = 'tab_off';
		tabD.className = 'tab_off';
		tabE.className = 'tab_off';
		tabF.className = 'tab_off';
		tabG.className = 'tab_off';
		tabH.className = 'tab_off';
		document.getElementById('imgTabALft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_on_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_off_rgt.gif';
		window.open(url,'mainFrame');
	}
	else if(tabname == 't3') {
		tabA.className = 'tab_off';
		tabB.className = 'tab_off';
		tabC.className = 'tab_on';
		tabD.className = 'tab_off';
		tabE.className = 'tab_off';
		tabF.className = 'tab_off';
		tabG.className = 'tab_off';
		tabH.className = 'tab_off';
		document.getElementById('imgTabALft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_on_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_off_rgt.gif';
		window.open(url,'mainFrame');
	}
	else if(tabname == 't4') {
		tabA.className = 'tab_off';
		tabB.className = 'tab_off';
		tabC.className = 'tab_off';
		tabD.className = 'tab_on';
		tabE.className = 'tab_off';
		tabF.className = 'tab_off';
		tabG.className = 'tab_off';
		tabH.className = 'tab_off';
		document.getElementById('imgTabALft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_on_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_off_rgt.gif';
		window.open(url,'mainFrame');
	}
	else if(tabname == 't5') {
		tabA.className = 'tab_off';
		tabB.className = 'tab_off';
		tabC.className = 'tab_off';
		tabD.className = 'tab_off';
		tabE.className = 'tab_on';
		tabF.className = 'tab_off';
		tabG.className = 'tab_off';
		tabH.className = 'tab_off';
		document.getElementById('imgTabALft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_on_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_off_rgt.gif';
		window.open(url,'mainFrame');
	}
	else if(tabname == 't6') {
		tabA.className = 'tab_off';
		tabB.className = 'tab_off';
		tabC.className = 'tab_off';
		tabD.className = 'tab_off';
		tabE.className = 'tab_off';
		tabF.className = 'tab_on';
		tabG.className = 'tab_off';
		tabH.className = 'tab_off';
		document.getElementById('imgTabALft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_on_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_off_rgt.gif';
		window.open(url,'mainFrame');
	}
	else if(tabname == 't7') {
		tabA.className = 'tab_off';
		tabB.className = 'tab_off';
		tabC.className = 'tab_off';
		tabD.className = 'tab_off';
		tabE.className = 'tab_off';
		tabF.className = 'tab_off';
		tabG.className = 'tab_on';
		tabH.className = 'tab_off';
		document.getElementById('imgTabALft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_on_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_off_rgt.gif';
		window.open(url,'mainFrame');
	}
	else if(tabname == 't8') {
		tabA.className = 'tab_off';
		tabB.className = 'tab_off';
		tabC.className = 'tab_off';
		tabD.className = 'tab_off';
		tabE.className = 'tab_off';
		tabF.className = 'tab_off';
		tabG.className = 'tab_off';
		tabH.className = 'tab_on';
		document.getElementById('imgTabALft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabARgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabBLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabBRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabCLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabCRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabDLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabDRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabELft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabERgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabFLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabFRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabGLft').src = 'images/tab_off_lft.gif';
		document.getElementById('imgTabGRgt').src = 'images/tab_off_rgt.gif';
		document.getElementById('imgTabHLft').src = 'images/tab_on_lft.gif';
		document.getElementById('imgTabHRgt').src = 'images/tab_on_rgt.gif';
		window.open(url,'mainFrame');
	}
}

function subtab(clicked,a,b) {
	clicked.className = 'subtab_on';
	a.className = 'subtab_off';
	b.className = 'subtab_off';
}

// End Tab Menu

function mkScroll(h,d) {
	var head = document.getElementById(h);
	var data = document.getElementById(d);
	head.scrollRight = data.scrollRight;
	head.scrollLeft = data.scrollLeft;
}










