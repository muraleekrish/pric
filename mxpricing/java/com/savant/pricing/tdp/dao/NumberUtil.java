/*
 * Created on Feb 2, 2006
 */

package com.savant.pricing.tdp.dao;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * @author Kanagavelan Subramanian
 *
 */

public class NumberUtil
{

    public NumberUtil()
    {
        
    }

    public static NumberFormat getFormatter(Locale locale, int minNoOfIntegers, int maxNoOfIntegers, int minNoOfFractions, int maxNoOfFractions, boolean useGrouping, boolean parseIntOnly, boolean isCurrency)
    {
        NumberFormat formatter = null;
        
        if(isCurrency)
        {
            formatter = NumberFormat.getCurrencyInstance();
        }
        else
        {
            formatter = NumberFormat.getNumberInstance();
        }
        
        formatter.setCurrency(Currency.getInstance(locale));
        
        formatter.setMinimumIntegerDigits(minNoOfIntegers);
        
        formatter.setMaximumIntegerDigits(maxNoOfIntegers);        
        
        formatter.setMinimumFractionDigits(minNoOfFractions);
        
        formatter.setMaximumFractionDigits(maxNoOfFractions);
        
        formatter.setGroupingUsed(useGrouping);
        
        formatter.setParseIntegerOnly(parseIntOnly);
        
        return formatter;
    }
    
    public static NumberFormat getCurrencyFormatter()
    {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        
        currencyFormatter.setCurrency(Currency.getInstance(Locale.US));
        
        currencyFormatter.setMaximumFractionDigits(2);	// 50
        
        currencyFormatter.setMaximumIntegerDigits(50);        
        
        currencyFormatter.setGroupingUsed(true);
        
        currencyFormatter.setParseIntegerOnly(false);
        
        currencyFormatter.setMinimumFractionDigits(2);
        
        currencyFormatter.setMinimumIntegerDigits(1);
        
        return currencyFormatter;
    }
    
    public static NumberFormat getkWhFormatter()
    {
        NumberFormat kWhFormatter = NumberFormat.getNumberInstance();
        
        kWhFormatter.setMaximumFractionDigits(2); // 50
        
        kWhFormatter.setMaximumIntegerDigits(50);        
        
        kWhFormatter.setGroupingUsed(true);
        
        kWhFormatter.setParseIntegerOnly(false);
        
        kWhFormatter.setMinimumFractionDigits(2);
        
        kWhFormatter.setMinimumIntegerDigits(1);
        
        return kWhFormatter;
    }

    public static NumberFormat getRealNumberFormatter()
    {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance();
        
        numberFormatter.setMaximumFractionDigits(3); //50
        
        numberFormatter.setMaximumIntegerDigits(50);        
        
        numberFormatter.setGroupingUsed(true);
        
        numberFormatter.setParseIntegerOnly(false);
        
        numberFormatter.setMinimumFractionDigits(2);
        
        numberFormatter.setMinimumIntegerDigits(1);
        
        return numberFormatter;
    }

    public static NumberFormat getWholeNumberFormatter()
    {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance();
        
        numberFormatter.setMaximumFractionDigits(0); //50
        
        numberFormatter.setMaximumIntegerDigits(50);        
        
        numberFormatter.setGroupingUsed(true);
        
        numberFormatter.setParseIntegerOnly(true);	// Fractional parts, if any are not considered while parsing
        
        numberFormatter.setMinimumFractionDigits(0);
        
        numberFormatter.setMinimumIntegerDigits(1);
        
        return numberFormatter;
    }
    
    public static void main(String[] args)
    {
        NumberUtil.getCurrencyFormatter();
    }
}

/***
 $Log: NumberUtil.java,v $
 Revision 1.1  2007/12/07 06:18:35  jvediyappan
 initial commit.

 Revision 1.1  2007/11/26 05:33:55  tannamalai
 latest changes for tdp

 Revision 1.2  2007/10/26 09:06:50  amahesh
 removed system.out.println statements

 Revision 1.1  2006/04/28 06:17:32  jshanmugam
 initial commit

 Revision 1.2  2006/03/16 08:49:47  ksubramanian
 getFormatter method modified

 Revision 1.1  2006/02/22 09:17:53  ppalaniappan
 Initial Commit

 ***/
