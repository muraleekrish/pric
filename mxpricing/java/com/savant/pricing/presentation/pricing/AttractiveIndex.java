/*
 * Created on Mar 1, 2007 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import com.savant.pricing.common.BuildConfig;

/**
 * @author jnadesan TODO To change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class AttractiveIndex
{

    public double attaractiveIndex(double contractkWh, double margin,
            double teeCost, double salesFee, double loadFactor)
    {
        int maximumPoints = 20;
        double percentageAttained = 0.0;
        if(BuildConfig.DMODE)
            System.out.println(percentageAttained + ")()(() " + margin);
        int points = 0;
        if(contractkWh <= 100000)
            points += 1;
        else if(contractkWh <= 1000000)
            points += 2;
        else if(contractkWh <= 10000000)
            points += 3;
        else
            points += 4;

        if(margin <= 1000)
            points += 1;
        else if(margin <= 5000)
            points += 2;
        else if(margin <= 25000)
            points += 3;
        else
            points += 4;

        double marginRatio = (margin / teeCost) * 100;

        if(marginRatio <= 5)
            points += 1;
        else if(marginRatio <= 10)
            points += 2;
        else if(marginRatio <= 15)
            points += 3;
        else
            points += 4;

        double salesRatio = (salesFee / margin) * 100;

        if(salesRatio <= 10)
            points += 1;
        else if(salesRatio <= 20)
            points += 2;
        else if(salesRatio <= 30)
            points += 3;
        else
            points += 4;

        if(loadFactor <= 20)
            points += 1;
        else if(loadFactor <= 40)
            points += 2;
        else if(loadFactor <= 60)
            points += 3;
        else
            points += 4;
        if(margin != 0)
        {
            percentageAttained = (double) points / maximumPoints;
        } else
        {
            percentageAttained = 0;
        }
        if(BuildConfig.DMODE)
            System.out.println("percentageAttained  :" + percentageAttained);
        return percentageAttained;
    }

    public String getSlogan(double index)
    {
        String slogan = "";
        if(index > 0.1 && index <= 1)
        {
            if(index < 0.3)
                slogan = "Yahoo!";
            else if(index < 0.5)
                slogan = "Feels Very Good";
            else if(index < 0.7)
                slogan = "A Good Day's Work";
            else if(index < 0.9)
                slogan = "Try to Do Better";
            else if(index < 1)
                slogan = "No Way";
            else
                slogan = "A Complete Dud";
        }
        return slogan;
    }
}