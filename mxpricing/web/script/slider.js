
function slider (a_init, a_tpl) {

	this.f_setValue  = f_sliderSetValue;
	this.f_getPos    = f_sliderGetPos;
	
	// register in the global collection	
	if (!window.A_SLIDERS)
		window.A_SLIDERS = [];
	this.n_id = window.A_SLIDERS.length;
	window.A_SLIDERS[this.n_id] = this;

	// save config parameters in the slider object
	var s_key;
	if (a_tpl)
		for (s_key in a_tpl)
		{
			this[s_key] = a_tpl[s_key];
		}
	for (s_key in a_init)
	{
		this[s_key] = a_init[s_key];
	}

	this.n_pix2value = this.n_pathLength / (this.n_maxValue - this.n_minValue);
	if (this.n_value == null)
		this.n_value = this.n_minValue;

	// generate the control's HTML
	document.write(
		'<div style="width:' + this.n_controlWidth + 'px;height:' + this.n_controlHeight + 'px;border:0; background-image:url(' + this.s_imgControl + ')" id="sl' + this.n_id + 'base">' +
		'<img src="' + this.s_imgSlider + '" width="' + this.n_sliderWidth + '" height="' + this.n_sliderHeight + '" border="0" style="position:relative;left:' + this.n_pathLeft + 'px;top:' + this.n_pathTop + 'px;z-index:' + this.n_zIndex + ';cursor:hand;visibility:hidden;" name="sl' + this.n_id + 'slider" id="sl' + this.n_id + 'slider" onmousedown="return f_sliderMouseDown(' + this.n_id + ')" /></div>'
	);
	this.e_base   = get_element('sl' + this.n_id + 'base');
	this.e_slider = get_element('sl' + this.n_id + 'slider');
	
	// safely hook document/window events
	if (document.onmousemove != f_sliderMouseMove) {
		window.f_savedMouseMove = document.onmousemove;
		document.onmousemove = f_sliderMouseMove;
	}
	if (document.onmouseup != f_sliderMouseUp) {
		window.f_savedMouseUp = document.onmouseup;
		document.onmouseup = f_sliderMouseUp;
	}
	// preset to the value in the input box if available
	var e_input = this.s_form == null
		? get_element(this.s_name)
		: document.forms[this.s_form]
			? document.forms[this.s_form].elements[this.s_name]
			: null;
	this.f_setValue(e_input && e_input.value != '' ? e_input.value : null, 1);

	this.e_slider.style.visibility = 'visible';
	
}

function f_sliderSetValue (n_value, b_noInputCheck, div_value) {
	//alert(div_value+" div " + n_value);
	
	if (n_value == null)
		n_value = this.n_value == null ? this.n_minValue : this.n_value;
	if (isNaN(n_value))
		return false;
	// round to closest multiple if step is specified
	if (this.n_step)
		n_value = Math.round((n_value - this.n_minValue) / this.n_step) * this.n_step + this.n_minValue;
	// smooth out the result
	if (n_value % 1)
		n_value = Math.round(n_value * 1e5) / 1e5;

	if (n_value < this.n_minValue)
		n_value = this.n_minValue;
	if (n_value > this.n_maxValue)
		n_value = this.n_maxValue;

	this.n_value = n_value;
	//alert(this.n_value + " hai");

	// move the slider
	if (this.b_vertical)
		this.e_slider.style.top  = (this.n_pathTop + this.n_pathLength - Math.round((n_value - this.n_minValue) * this.n_pix2value)) + 'px';
	else
		this.e_slider.style.left = (this.n_pathLeft + Math.round((n_value - this.n_minValue) * this.n_pix2value)) + 'px';

	// save new value
	var e_input;
	if (this.s_form == null) {
		e_input = get_element(this.s_name);
		if (!e_input)
			return b_noInputCheck ? null : f_sliderError(this.n_id, "Can not find the input with ID='" + this.s_name + "'.");
	}
	else {
		var e_form = document.forms[this.s_form];
		if (!e_form)
			return b_noInputCheck ? null : f_sliderError(this.n_id, "Can not find the form with NAME='" + this.s_form + "'.");
		e_input = e_form.elements[this.s_name];
		if (!e_input)
			return b_noInputCheck ? null : f_sliderError(this.n_id, "Can not find the input with NAME='" + this.s_name + "'.");
	}
	e_input.value =( (n_value/this.div_value).toFixed(this.f_precision));
	//e_input.value.toFixed(5);
	//alert(n_value + " e");
}

// get absolute position of the element in the document
function f_sliderGetPos (b_vertical, b_base) {
	var n_pos = 0,
		s_coord = (b_vertical ? 'Top' : 'Left');
	var o_elem = o_elem2 = b_base ? this.e_base : this.e_slider;
	
	while (o_elem) {
		n_pos += o_elem["offset" + s_coord];
		o_elem = o_elem.offsetParent;
	}
	o_elem = o_elem2;

	var n_offset;
	while (o_elem.tagName != "BODY") {
		n_offset = o_elem["scroll" + s_coord];
		if (n_offset)
			n_pos -= o_elem["scroll" + s_coord];
		o_elem = o_elem.parentNode;
	}
	return n_pos;
}

function f_sliderMouseDown (n_id) {
	window.n_activeSliderId = n_id;
	
	//alert('down');
	return false;
}

function f_sliderMouseUp (e_event, b_watching) {
	if (window.n_activeSliderId != null) {
		var o_slider = window.A_SLIDERS[window.n_activeSliderId];
		o_slider.f_setValue(o_slider.n_minValue + (o_slider.b_vertical
			? (o_slider.n_pathLength - parseInt(o_slider.e_slider.style.top) + o_slider.n_pathTop)
			: (parseInt(o_slider.e_slider.style.left) - o_slider.n_pathLeft)) / o_slider.n_pix2value);
		
		if (b_watching)	return;
		window.n_activeSliderId = null;
	}
	if (window.f_savedMouseUp)
		return window.f_savedMouseUp(e_event);
}                                                                                             

function f_sliderMouseMove (e_event) {

	if (!e_event && window.event) e_event = window.event;

	// save mouse coordinates
	if (e_event) {
		window.n_mouseX = e_event.clientX + f_scrollLeft();
		window.n_mouseY = e_event.clientY + f_scrollTop();
		
	}

	// check if in drag mode
	if (window.n_activeSliderId != null) {
		var o_slider = window.A_SLIDERS[window.n_activeSliderId];

		var n_pxOffset;
		if (o_slider.b_vertical) {
			var n_sliderTop = window.n_mouseY - o_slider.n_sliderHeight / 2 - o_slider.f_getPos(1, 1) - 3;
			// limit the slider movement
			if (n_sliderTop < o_slider.n_pathTop)
				n_sliderTop = o_slider.n_pathTop;
			var n_pxMax = o_slider.n_pathTop + o_slider.n_pathLength;
			if (n_sliderTop > n_pxMax)
				n_sliderTop = n_pxMax;
			o_slider.e_slider.style.top = n_sliderTop + 'px';
			n_pxOffset = o_slider.n_pathLength - n_sliderTop + o_slider.n_pathTop;
		}
		else {
			var n_sliderLeft = window.n_mouseX - o_slider.n_sliderWidth / 2 - o_slider.f_getPos(0, 1) - 3;
			// limit the slider movement
			if (n_sliderLeft < o_slider.n_pathLeft)
				n_sliderLeft = o_slider.n_pathLeft;
			var n_pxMax = o_slider.n_pathLeft + o_slider.n_pathLength;
			if (n_sliderLeft > n_pxMax)
				n_sliderLeft = n_pxMax;
			o_slider.e_slider.style.left = n_sliderLeft + 'px';
			n_pxOffset = n_sliderLeft - o_slider.n_pathLeft;
			
		}
		if (o_slider.b_watch)
			 f_sliderMouseUp(e_event, 1);

		return false;
	}
	
	if (window.f_savedMouseMove)
		return window.f_savedMouseMove(e_event);
}

// get the scroller positions of the page
function f_scrollLeft() {
	return f_filterResults (
		window.pageXOffset ? window.pageXOffset : 0,
		document.documentElement ? document.documentElement.scrollLeft : 0,
		document.body ? document.body.scrollLeft : 0
	);
}
function f_scrollTop() {
	return f_filterResults (
		window.pageYOffset ? window.pageYOffset : 0,
		document.documentElement ? document.documentElement.scrollTop : 0,
		document.body ? document.body.scrollTop : 0
	);
}
function f_filterResults(n_win, n_docel, n_body) {
	var n_result = n_win ? n_win : 0;
	if (n_docel && (!n_result || (n_result > n_docel)))
	{
		n_result = n_docel;
	}
	return n_body && (!n_result || (n_result > n_body)) ? n_body : n_result;
}

function f_sliderError (n_id, s_message) {
	alert("Slider #" + n_id + " Error:\n" + s_message);
	window.n_activeSliderId = null;
}

get_element = document.all ?
	function (s_id) { return document.all[s_id] } :
	function (s_id) { return document.getElementById(s_id) };
	
	
	
	var MWJ_progBar = 0;

function getRefToDivNest( divID, oDoc ) {
	if( !oDoc ) { oDoc = document; }
	if( document.layers ) {
		if( oDoc.layers[divID] ) { return oDoc.layers[divID]; } else {
			for( var x = 0, y; !y && x < oDoc.layers.length; x++ ) {
				y = getRefToDivNest(divID,oDoc.layers[x].document); }
			return y; } }
	if( document.getElementById ) { return document.getElementById(divID); }
	if( document.all ) { return document.all[divID]; }
	return document[divID];
}

function progressBar( oBt, oBc, oBg, oBa, oWi, oHi, oDr ) {
	MWJ_progBar++; this.id = 'MWJ_progBar' + MWJ_progBar; this.dir = oDr; this.width = oWi; this.height = oHi; this.amt = 0;
	//write the bar as a layer in an ilayer in two tables giving the border
	document.write( '<table border="0" cellspacing="0" cellpadding="'+oBt+'"><tr><td bgcolor="'+oBc+'">'+
		'<table border="0" cellspacing="0" cellpadding="0"><tr><td height="'+oHi+'" width="'+oWi+'" bgcolor="'+oBg+'">' );
	if( document.layers ) {
		document.write( '<ilayer height="'+oHi+'" width="'+oWi+'"><layer bgcolor="'+oBa+'" name="MWJ_progBar'+MWJ_progBar+'"></layer></ilayer>' );
	} else {
		document.write( '<div style="position:relative;top:0px;left:0px;height:'+oHi+'px;width:'+oWi+'px;">'+
			'<div style="position:absolute;top:0px;left:0px;height:0px;width:0;font-size:1px;background-color:'+oBa+';" id="MWJ_progBar'+MWJ_progBar+'"></div></div>' );
	}
	document.write( '</td></tr></table></td></tr></table>\n' );
	this.setBar = resetBar; //doing this inline causes unexpected bugs in early NS4
	this.setCol = setColour;
}
function resetBar( a, b ) {
	//work out the required size and use various methods to enforce it
	this.amt = ( typeof( b ) == 'undefined' ) ? a : b ? ( this.amt + a ) : ( this.amt - a );
	if( isNaN( this.amt ) ) { this.amt = 0; } if( this.amt > 1 ) { this.amt = 1; } if( this.amt < 0 ) { this.amt = 0; }
	var theWidth = Math.round( this.width * ( ( this.dir % 2 ) ? this.amt : 1 ) );
	var theHeight = Math.round( this.height * ( ( this.dir % 2 ) ? 1 : this.amt ) );
	var theDiv = getRefToDivNest( this.id ); if( !theDiv ) { window.status = 'Progress: ' + Math.round( 100 * this.amt ) + '%'; return; }
	if( theDiv.style ) { theDiv = theDiv.style; theDiv.clip = 'rect(0px '+theWidth+'px '+theHeight+'px 0px)'; }
	var oPix = document.childNodes ? 'px' : 0;
	theDiv.width = theWidth + oPix; theDiv.pixelWidth = theWidth; theDiv.height = theHeight + oPix; theDiv.pixelHeight = theHeight;
	if( theDiv.resizeTo ) { theDiv.resizeTo( theWidth, theHeight ); }
	theDiv.left = ( ( this.dir != 3 ) ? 0 : this.width - theWidth ) + oPix; theDiv.top = ( ( this.dir != 4 ) ? 0 : this.height - theHeight ) + oPix;
}
function setColour( a ) {
	//change all the different colour styles
	var theDiv = getRefToDivNest( this.id ); if( theDiv.style ) { theDiv = theDiv.style; }
	theDiv.bgColor = a; theDiv.backgroundColor = a; theDiv.background = a;
}

