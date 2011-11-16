// JavaScript Document

//Global Variables
var currentchecked = "None";
var currentcheckcount = 0;
var path="/mxep/";

//Show Hide script for Search palette;
function showHide(plt) {
	var table = document.getElementById(plt);
	var showImg = "../../images/show.gif";
	var hideImg = "../../images/hide.gif";
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
	var showImg = "../../images/show.gif";
	var hideImg = "../../images/hide.gif";
	
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

//new Script

function selectValue(comboObj,value)
{
   for(i=0;i<comboObj.options.length;i++)
   
    {	
       if(comboObj.options[i].value==value)
         {
			comboObj.options[i].selected=true;
		}
	}	
}

function isSimilar(currentcheckedrow)
	{
		if (currentcheckedrow.checked==true)
			{
				if (currentcheckedrow.parentNode.className=="Invalid")
					{
						if(currentchecked == "None"||currentchecked == "Invalid")
							{
								currentchecked = "Invalid";
								currentcheckcount++;
								//var obj=document.getElementById("cmdtable");
								makeValid.style.display = "block";
								makeInValid.style.display = "none";
							}
						else
							{
								alert("Dissimilar selection of Items Not Possible");
								currentcheckedrow.checked = false;
								return;
							}
					}
				else if (currentcheckedrow.parentNode.className=="TableRow1"||currentcheckedrow.parentNode.className=="TableRow2") //The row classnames should not be modified in the future.
					{
						if(currentchecked == "None"||currentchecked == "valid")
							{
								currentchecked = "valid";
								currentcheckcount++;
								makeValid.style.display = "none";
								makeInValid.style.display = "block";
							}
						else
							{
								alert("Dissimilar selection of Items Not Possible");
								currentcheckedrow.checked = false;
								return;
							}
					}
				else if (currentcheckedrow.parentNode.className=="reserve")
					{
						if(currentchecked == "None"||currentchecked == "reserved")
							{
								currentchecked = "reserved";
								currentcheckcount++;
								//var obj=document.getElementById("cmdtable");
								//reserve.style.display = "none";
								//unreserve.style.display = "block";
							}
						else
							{
								alert("Dissimilar selection of Items Not Possible");
								currentcheckedrow.checked = false;
								return;
							}
					}
			}
		else
			{
				currentcheckcount--;
				if(currentcheckcount==0)
					currentchecked = "None";
			}
	}



function chkAll(frmPage)//chkAllMsg(frmPage)
	{
		
		currentchecked = "None";
		currentcheckcount = 0;
		len = frmPage.elements["checkValue"];
		if(len!=undefined)
		{
		  if(frmPage.checkAll.checked==true)
			{
				if(len.length==undefined)
					{
						len.checked=true;
						return;
					}
				for(i=0;i<len.length;i++)
					{
						if(len[i].parentNode.className == "list_data_checked" || len[i].parentNode.className == "list_data_checked")
						{
							len[i].checked=true;
							isSimilar(len[i]);
						}
						else
							len[i].checked =false;

					}
			}
		  else
			{
				if(len.length==undefined)
					{
						len.checked=false;
						return;
					}
				for(i=0;i<len.length;i++)
					{
						len[i].checked=false;
						isSimilar(len[i]);
					}
					currentchecked = "None";
					currentcheckcount = 0;
			}
		}
	}

function getCheckedValueDetail(tabId, frmPage)//chkDetail(tabId, frmPage)
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j=0;
		var val;
		if (obj.children[0].children.length==2)
		{
			if(frmPage.checkValue.checked)
			{
			    val = frmPage.checkValue.value;
			}
		return val;
		} 		
		if (obj.children[0].children.length>2) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.checkValue[j].checked)
							{
							    val = frmPage.checkValue[j].value;
							    break;
							}
						j++;
					}
			}
		return val;
	}
	
function modifyRow(tabId, frmPage)
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j=0;
		
		if(obj.children[0].children.length==1)
		{
		  alert("There is no Item in the List to Modify");
		  return false;
		}
		
		if (obj.children[0].children.length==2)
		{
				if(frmPage.checkValue.checked)
				{
					if(obj.children[0].children[1].children[0].className == "Invalid")
						{
							alert("Invalid Item cannot be Modified");
							return false;
						}
				}
				else
				{
						alert("Please Select an Item from List to Modify");
						count=0;
						return false;
				}
			return true;
		} 
		if (obj.children[0].children.length>2) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.checkValue[j].checked)
							{
								if(obj.children[0].children[i].children[0].className == "Invalid")
									{
										alert("Invalid Item cannot be Modified");
										return false;
									}
							    count++;
							}
						j++;
					}

				if(count > 1)
					{
						alert("Please Select only one Item from List to Modify");
						count=0;
						return false;
					}
				else if(count<1)
					{
						alert("Please Select an Item from List to Modify");
						count=0;
						return false;
					}
			}
  return true;
	}

function getMultipleCheckedValue(tabId, frmPage)
	{
		var obj=document.getElementById(tabId);
		var count = 0;
		var j=0;
		var c=0;
		var val = new Array();
		if (obj.children[0].children.length==2)
		{
			if(frmPage.checkValue.checked)
			{
			    val[0] = frmPage.checkValue.value;
			}
		return val;
		} 		
		if (obj.children[0].children.length>2) 
			{
				for(var i = 1; i < obj.children[0].children.length; i++)
					{
						if(frmPage.checkValue[j].checked)
							{
							    val[c] = frmPage.checkValue[j].value;
							    c++;
							}
						j++;
					}
			}
		return val;
	}	

function deleteRow(tabID, frmPage)
	{
		var count = 0;
		var obj=document.getElementById(tabID);
		var len = obj.children[0].children.length;
		var obj1 = obj.children[0];
		if(obj.children[0].children.length==1)
		{
		  alert("There is no Item in the List to Delete");
		  return false;
		}
		if (obj.children[0].children.length==2)
		{
				if(frmPage.checkValue.checked)
				{
					if(!confirm("Warning! \n\nThe chosen item/s will be Deleted !"))
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
						alert("Please Select an Item from List to Delete");
						count=0;
						return false;
				}
			return true;
		} 
		
		if (obj.children[0].children.length>2) 
		{
			for(var i = 1; i <obj.children[0].children.length ; i++)
			{
    			if(frmPage.checkValue[i-1].checked)
				{
					count++;
				}
			}
			if (count < 1)
			{
				alert("Please Select an Item to Delete");
			}
			if(count>0)
			{
				if(!confirm("Warning! \n\nThe chosen item/s will be Deleted !"))
				{	
					return false;
				}
				else 
				{
					return true;
				}
			}
		}
	}


function sortTable(col)
{
	try{
	direction = 1;
	if(col.sortOrder == 1)
	direction = -1;
	header = col.parentNode;
	tab = header.parentNode;
	for(i=1;i<(header.children.length);i++)
	{
	
		header.children[i].children[0].style.visibility ="hidden" ;
	
		header.children[i].title = "Sort by" + header.children[i].innerText +" in Ascending";
	}

	col.children[0].style.visibility="visible";
	
	if(direction==-1)
	{
		col.children[0].src = path+'images/sort.gif';
		col.title = "Sort by" + col.innerText + " in Ascending";
	}
	else
	{
		col.children[0].src =path+'images/sort_up.gif';
		col.title = "Sort by" + col.innerText + " in Descending";
	}
	
	for(i=0;i<header.cells.length;i++)
	{
		if(header.cells[i]==col)
		{
			colNum = i;
			col.sortOrder=direction;
		}
		else
			header.cells[i].sortOrder=-1;
	}
	}catch(err)
	{
	alert(err.description);
	}
}

function sortByTable(tabName,columnIndex,ascending)//opSort(tabName,columnIndex,ascending)
{
	
	var tab = document.getElementById(tabName);
	alert("test:"+tab.children[0].children[0].children[columnIndex].innerHTML);
	if(ascending=="true")
		tab.children[0].children[0].children[columnIndex].sortOrder = -1;
	else
		tab.children[0].children[0].children[columnIndex].sortOrder = 1;

	sortTable(tab.children[0].children[0].children[columnIndex]);
}


function sortByTable_new(tabName,columnIndex,ascending)//opSort(tabName,columnIndex,ascending)
{
	
	var tab = document.getElementById(tabName);

	
	if(ascending=="true")
		tab.children[0].children[0].children[columnIndex].sortOrder = -1;
	else
		tab.children[0].children[0].children[columnIndex].sortOrder = 1;

	sortTable_new(tab.children[0].children[0].children[columnIndex],columnIndex);
}

function sortTable_new(col,columnIndex)
{
	
	direction = 1;
	if(col.sortOrder == 1)
	direction = -1;


	header = col.parentNode;
	tab = header.parentNode;
    for(i=1;i<(header.children.length);i++)
	{	
		
		header.children[i].children[0].style.visibility ="hidden" ;
		header.children[i].title = "Sort by" + header.children[i].innerText +" in Ascending";
	}
	tab.children[0].children[columnIndex].children[0].style.visibility="visible";

		if(direction==-1)
	{
		col.children[0].src = path+'images/sort.gif';
		col.title = "Sort by" + col.innerText + " in Ascending";
	}
	else
	{
		 
		col.children[0].src =path+'images/sort_up.gif';
		col.title = "Sort by" + col.innerText + " in Descending";
	}
	
	for(i=0;i<header.cells.length;i++)
	{
		if(header.cells[i]==col)
		{
			colNum = i;
			col.sortOrder=direction;
		}
		else
			header.cells[i].sortOrder=-1;
	}
	
}


