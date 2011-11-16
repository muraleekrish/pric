/*
 * Created on Feb 2, 2006
 */

package com.savant.pricing.common;

import java.text.NumberFormat;

/**
 * @author Srajappan
 *
 */

public class NumberUtil
{

    
    public static NumberFormat  tetraFraction()
    {
        NumberFormat formater = NumberFormat.getNumberInstance();
        formater.setMaximumFractionDigits(4);
        formater.setMinimumFractionDigits(4);
        formater.setMinimumIntegerDigits(1);
        return formater;
    }
    public static NumberFormat  doubleFraction()
    {
        NumberFormat formater = NumberFormat.getNumberInstance();
        formater.setMaximumFractionDigits(2);
        formater.setMinimumFractionDigits(2);
        formater.setMinimumIntegerDigits(1);
        return formater;
    }
   
}

/***
 $Log: NumberUtil.java,v $
 Revision 1.1  2007/12/07 06:18:44  jvediyappan
 initial commit.

 Revision 1.1  2007/10/30 05:51:56  jnadesan
 Initial commit.

 Revision 1.1  2007/10/26 15:19:28  jnadesan
 initail MXEP commit

 Revision 1.5  2007/08/23 07:26:40  jnadesan
 imports organized

 Revision 1.4  2007/03/14 11:45:51  kduraisamy
 imports organized.

 Revision 1.3  2007/02/16 08:53:42  srajappan
 format fraction changed to 4

 Revision 1.2  2007/02/15 12:48:32  kduraisamy
 double and tetra fraction methods added.

 Revision 1.1  2007/02/15 12:43:33  kduraisamy
 initial commit.

 Revision 1.17  2006/10/09 12:17:20  jnadesan
 unwanted print removed

 Revision 1.16  2006/08/28 07:50:08  srajappan
 2 int part method added

 Revision 1.15  2006/08/10 04:27:03  srajappan
 single fraction method added

 Revision 1.14  2006/08/08 09:17:37  srajappan
 single fraction method added

 Revision 1.13  2006/07/31 13:08:22  srajappan
 unwanted import removed

 Revision 1.12  2006/07/27 07:23:39  srajappan
 new format added

 Revision 1.11  2006/07/11 04:33:46  srajappan
 numberformat changed to 2 decimal format

 Revision 1.10  2006/07/07 04:19:10  jvediyappan
 number format methode added

 Revision 1.9  2006/06/19 07:01:16  srajappan
 number format decimal place changed

 Revision 1.8  2006/05/17 10:14:31  srajappan
 minimum integer added

 Revision 1.7  2006/05/12 09:13:23  srajappan
 zero supression made

 Revision 1.6  2006/05/10 12:22:12  srajappan
 import organized

 Revision 1.4  2006/05/10 11:32:31  srajappan
 import organized

 Revision 1.3  2006/05/10 09:21:45  srajappan
 minimum digits modified

 Revision 1.2  2006/05/05 10:54:20  srajappan
 yearly completed

 Revision 1.1  2006/05/02 05:11:18  srajappan
 initial commit


 ***/
