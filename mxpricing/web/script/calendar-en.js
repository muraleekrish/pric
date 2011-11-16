// ** I18N

// Calendar EN language
// Author: Mihai Bazon, <mihai_bazon@yahoo.com>
// Encoding: any
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names
Mira.Calendar._DN = new Array
("Sunday",
 "Monday",
 "Tuesday",
 "Wednesday",
 "Thursday",
 "Friday",
 "Saturday",
 "Sunday");

// Please note that the following array of short day names (and the same goes
// for short month names, _SMN) isn't absolutely necessary.  We give it here
// for exemplification on how one can customize the short day names, but if
// they are simply the first N letters of the full name you can simply say:
//
//   Mira.Calendar._SDN_len = N; // short day name length
//   Mira.Calendar._SMN_len = N; // short month name length
//
// If N = 3 then this is not needed either since we assume a value of 3 if not
// present, to be compatible with translation files that were written before
// this feature.

// short day names
Mira.Calendar._SDN = new Array
("Sun",
 "Mon",
 "Tue",
 "Wed",
 "Thu",
 "Fri",
 "Sat",
 "Sun");

// First day of the week. "0" means display Sunday first, "1" means display
// Monday first, etc.
Mira.Calendar._FD = 0;

// full month names
Mira.Calendar._MN = new Array
("January",
 "February",
 "March",
 "April",
 "May",
 "June",
 "July",
 "August",
 "September",
 "October",
 "November",
 "December");

// short month names
Mira.Calendar._SMN = new Array
("Jan",
 "Feb",
 "Mar",
 "Apr",
 "May",
 "Jun",
 "Jul",
 "Aug",
 "Sep",
 "Oct",
 "Nov",
 "Dec");

// tooltips
Mira.Calendar._TT_en = Mira.Calendar._TT = {};
Mira.Calendar._TT["INFO"] = "About the calendar";

Mira.Calendar._TT["ABOUT"] =
"Date selection:\n" +
"- Use the \xab, \xbb buttons to select year\n" +
"- Use the " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " buttons to select month\n" +
"- Hold mouse button on any of the above buttons for faster selection.";
Mira.Calendar._TT["ABOUT_TIME"] = "\n\n" +
"Time selection:\n" +
"- Click on any of the time parts to increase it\n" +
"- or Shift-click to decrease it\n" +
"- or click and drag for faster selection.";

Mira.Calendar._TT["PREV_YEAR"] = "Prev. year (hold for menu)";
Mira.Calendar._TT["PREV_MONTH"] = "Prev. month (hold for menu)";
Mira.Calendar._TT["GO_TODAY"] = "Go Today (hold for history)";
Mira.Calendar._TT["NEXT_MONTH"] = "Next month (hold for menu)";
Mira.Calendar._TT["NEXT_YEAR"] = "Next year (hold for menu)";
Mira.Calendar._TT["SEL_DATE"] = "Select date";
Mira.Calendar._TT["DRAG_TO_MOVE"] = "Drag to move";
Mira.Calendar._TT["PART_TODAY"] = " (today)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Mira.Calendar._TT["DAY_FIRST"] = "Display %s first";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Mira.Calendar._TT["WEEKEND"] = "0,6";

Mira.Calendar._TT["CLOSE"] = "Close";
Mira.Calendar._TT["TODAY"] = "Today";
Mira.Calendar._TT["TIME_PART"] = "(Shift-)Click or drag to change value";

// date formats
Mira.Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Mira.Calendar._TT["TT_DATE_FORMAT"] = "%a, %b %e";

Mira.Calendar._TT["WK"] = "wk";
Mira.Calendar._TT["TIME"] = "Time:";

Mira.Calendar._TT["E_RANGE"] = "Outside the range";

/* Preserve data */
	if(Mira.Calendar._DN) Mira.Calendar._TT._DN = Mira.Calendar._DN;
	if(Mira.Calendar._SDN) Mira.Calendar._TT._SDN = Mira.Calendar._SDN;
	if(Mira.Calendar._SDN_len) Mira.Calendar._TT._SDN_len = Mira.Calendar._SDN_len;
	if(Mira.Calendar._MN) Mira.Calendar._TT._MN = Mira.Calendar._MN;
	if(Mira.Calendar._SMN) Mira.Calendar._TT._SMN = Mira.Calendar._SMN;
	if(Mira.Calendar._SMN_len) Mira.Calendar._TT._SMN_len = Mira.Calendar._SMN_len;
	Mira.Calendar._DN = Mira.Calendar._SDN = Mira.Calendar._SDN_len = Mira.Calendar._MN = Mira.Calendar._SMN = Mira.Calendar._SMN_len = null
