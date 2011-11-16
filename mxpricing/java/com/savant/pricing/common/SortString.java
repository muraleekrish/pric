/*
 * Created on Dec 17, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savant.pricing.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SortString 
{
	public String sortString(String actual)
	{
		StringTokenizer st = new StringTokenizer(actual,",");
		String array[];
		String sorted = "";
		array = new String[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens())
		{
			array[i] = st.nextToken();
			i++;
		}
		List scrambled = Arrays.asList(array);
		Collections.shuffle(scrambled);
		Collections.sort(scrambled, new NaturalOrderComparator());
		Iterator itr = scrambled.iterator();
		while(itr.hasNext())
		{
		    if(sorted.length()>0)
		        sorted = sorted +","+itr.next();
		    else
		        sorted = String.valueOf(itr.next());
		}
		return sorted;
	}
	public static void main(String args[])
	{
	    SortString ss = new SortString();
	    if(BuildConfig.DMODE)
	        System.out.println(ss.sortString("3,12,1,24,14"));
	}
}
