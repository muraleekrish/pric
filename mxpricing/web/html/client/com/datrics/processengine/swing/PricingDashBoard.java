/*
 * Created on Sep 05, 2005
*
* ClassName	:  	PricingDashBoard_new.java
*
* Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
*
* This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
* (Confidential Information).  You shall not disclose or use Confidential
* Information without the express written agreement of Savant Technologies Pvt Ltd. India 
* 
*/
package com.datrics.processengine.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.SpringLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;



/**
* @author bkannusamy
*
*/
public class PricingDashBoard extends JApplet
{
   
   int custChargeScroll = 10;
   int addlVolScroll = 8;
   int salesFeeScroll = 100;
   int aggFeeScroll = 2;
   int bwScroll = 5;
   int otherFeeScroll = 10;
   int marginScroll = 45;
   
   double custChargeScrollRate = custChargeScroll;
   double addlVolScrollRate = addlVolScroll / 10.0;
   double salesFeeScrollRate = salesFeeScroll / 100000.0;
   double aggFeeScrollRate = aggFeeScroll / 10000.0;
   double bwScrollRate = bwScroll / 10000.0;
   double otherFeeScrollRate = otherFeeScroll / 10000.0;
   double marginScrollRate = marginScroll / 10000.0; 
   
   double energyCost = 14755;
   double addOn = 1086;
   double contractkWh = 240551;
   double tdsp = 3508;
   double ptb = 25475;
   double months = 2;
   double accounts = 2;
   double loadFactor = 0;
   double percentageAttained = 0;
   double maximumPoints = 24;
   
   double custCharge = custChargeScrollRate * months * accounts;
   double addlVol = energyCost * addlVolScrollRate;
   double salesFee = contractkWh * salesFeeScrollRate;
   double aggFee = contractkWh * aggFeeScrollRate;
   double bw = contractkWh * bwScrollRate;
   double otherFee = contractkWh * otherFeeScrollRate;
   double margin = contractkWh * marginScrollRate;
   
   
   double teeCost = 0;
   double custSavings = 0;
   
   double teeCostChart = 0;
   double tdspChart = 0;
   double salesFeeChart = 0;
   double marginChart = 0;
   double custSavingsChart = 0;

   double attractivenessIndex = 0;
   String slogan = "";
   
   
   Container contentPane = getContentPane();
   SpringLayout layout = new SpringLayout();//The Spring Layout is used
   
   JLabel piebutton;
   JLabel barbuttonImage;
   JLabel barbutton;    
   
   JButton buttonstatus = new JButton("");
   JButton buttonstatus1 = new JButton("");
   
   JScrollBar scrollbar2;
   JScrollBar scrollbar3;
   JScrollBar scrollbar4;
   JScrollBar scrollbar5;
   JScrollBar scrollbar6;
   JScrollBar scrollbar7;
   JScrollBar scrollbar8;
   
   
   JLabel custChText = new JLabel();
   JLabel custCh$Text = new JLabel();    
   JLabel FeelsText = new JLabel();
   JLabel goToText = new JLabel();
   JLabel volatilityText = new JLabel();
   JLabel VolatiText = new JLabel();
   JLabel KwhText = new JLabel();
   JLabel SalesFeeText = new JLabel();
   JLabel SalesText = new JLabel();
   JLabel AgeFeeText = new JLabel();
   JLabel ageText = new JLabel();
   JLabel BandwidthText = new JLabel();
   JLabel bandText = new JLabel();
   JLabel OtherFeeText = new JLabel();
   JLabel feeText = new JLabel();
   JLabel MarginText = new JLabel();
   JLabel marText = new JLabel();
   JLabel attractiveText = new JLabel();
   JLabel sloganText = new JLabel();
   JLabel attractiveText1 = new JLabel();
   JLabel attractiveText2 = new JLabel();
   JLabel leversText = new JLabel();
   JProgressBar progressBar = new JProgressBar();
   
   DefaultPieDataset  datasetPie = new DefaultPieDataset();
   
   public void init()
   {
       contentPane.setLayout(layout);
       contentPane.setBackground(Color.WHITE);
       
       
       leversText.setFont(new Font("Arial", Font.BOLD,12));
       leversText.setBackground(Color.WHITE);
       leversText.setForeground(Color.BLACK);
       leversText.setPreferredSize(new Dimension(305,20));
       leversText.setText("DEAL LEVERS");
       contentPane.add(leversText);
       layout.putConstraint(SpringLayout.WEST, leversText,10,SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, leversText,  10,SpringLayout.WEST, contentPane);        
       
       
       custChText.setFont(new Font("Arial", Font.PLAIN,12));
       custChText.setBackground(Color.WHITE);
       custChText.setForeground(Color.BLACK);
       custChText.setPreferredSize(new Dimension(100,20));
       custChText.setText("Cust Ch $/mth");
       contentPane.add(custChText);
       layout.putConstraint(SpringLayout.WEST, custChText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, custChText,                30,               SpringLayout.WEST, contentPane);
       
       scrollbar2 = new JScrollBar(Scrollbar.HORIZONTAL,1,0,0,3000);
       Dimension dimension2 = new Dimension(100,20);
       scrollbar2.setPreferredSize(dimension2);
       scrollbar2.setValue(custChargeScroll);
       scrollbar2.addAdjustmentListener(new scrollListenerCustCharge());
       
       contentPane.add(scrollbar2);
       layout.putConstraint(SpringLayout.WEST, scrollbar2,                114,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, scrollbar2,                30,                SpringLayout.WEST, contentPane);
       
       custCh$Text.setText("  "+ getDisplayValue(custChargeScrollRate, "0.0"));
       custCh$Text.setFont(new Font("Arial", Font.PLAIN,10));
       custCh$Text.setBackground(Color.WHITE);
       custCh$Text.setPreferredSize(new Dimension(60,20));
       contentPane.add(custCh$Text);
       layout.putConstraint(SpringLayout.WEST, custCh$Text,                215,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, custCh$Text,                30,                SpringLayout.WEST, contentPane);
       
       volatilityText.setFont(new Font("Arial", Font.PLAIN,12));
       volatilityText.setBackground(Color.WHITE);
       volatilityText.setForeground(Color.BLACK);
       volatilityText.setPreferredSize(new Dimension(100,20));
       volatilityText.setText("Addl. Volatilty");
       contentPane.add(volatilityText);
       layout.putConstraint(SpringLayout.WEST, volatilityText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, volatilityText,               50,                SpringLayout.WEST, contentPane);
       
       scrollbar3 = new JScrollBar(Scrollbar.HORIZONTAL,1,0,0,100);
       
       Dimension dimension3 = new Dimension(100,20);
       scrollbar3.setPreferredSize(dimension3);
       scrollbar3.setValue(addlVolScroll);
       scrollbar3.addAdjustmentListener(new scrollListenerAddlVol());
       contentPane.add(scrollbar3);
       layout.putConstraint(SpringLayout.WEST, scrollbar3,                114,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, scrollbar3,                50,                SpringLayout.WEST, contentPane);
       
       
       VolatiText.setText("  " + getDisplayValue(addlVolScrollRate, "0.0") + "%");
       VolatiText.setFont(new Font("Arial", Font.PLAIN,10));
       VolatiText.setBackground(Color.WHITE);
       VolatiText.setPreferredSize(new Dimension(60,20));
       contentPane.add(VolatiText);
       layout.putConstraint(SpringLayout.WEST, VolatiText,                215,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, VolatiText,                50,                SpringLayout.WEST, contentPane);
       
       KwhText.setFont(new Font("Arial", Font.PLAIN,12));
       KwhText.setBackground(Color.WHITE);
       KwhText.setForeground(Color.BLACK);
       KwhText.setPreferredSize(new Dimension(245,16));
       KwhText.setText("                                                                    $/kWh");
       contentPane.add(KwhText);
       layout.putConstraint(SpringLayout.WEST, KwhText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, KwhText,                70,                SpringLayout.WEST, contentPane);
       
       SalesFeeText.setFont(new Font("Arial", Font.PLAIN,12));
       SalesFeeText.setBackground(Color.WHITE);
       SalesFeeText.setForeground(Color.BLACK);
       SalesFeeText.setPreferredSize(new Dimension(110,20));
       SalesFeeText.setText("Sales Fee");
       contentPane.add(SalesFeeText);
       layout.putConstraint(SpringLayout.WEST, SalesFeeText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, SalesFeeText,               86,                SpringLayout.WEST, contentPane);
       
       scrollbar4 = new JScrollBar(Scrollbar.HORIZONTAL,1,0,0,1000);
       
       Dimension dimension4 = new Dimension(100,20);
       scrollbar4.setPreferredSize(dimension4);
       scrollbar4.setValue(salesFeeScroll);
       scrollbar4.addAdjustmentListener(new scrollListenerSalesFee());
       contentPane.add(scrollbar4);
       layout.putConstraint(SpringLayout.WEST, scrollbar4,                114,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, scrollbar4,                86,                SpringLayout.WEST, contentPane);
       
       SalesText.setText("  "+ getDisplayValue(salesFeeScrollRate, "0.00000####"));
       SalesText.setFont(new Font("Arial", Font.PLAIN,10));
       SalesText.setBackground(Color.WHITE);
       SalesText.setPreferredSize(new Dimension(60,20));
       contentPane.add(SalesText);
       layout.putConstraint(SpringLayout.WEST, SalesText,                215,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, SalesText,                86,                SpringLayout.WEST, contentPane);
       
       
       AgeFeeText.setFont(new Font("Arial", Font.PLAIN,12));
       AgeFeeText.setBackground(Color.WHITE);
       AgeFeeText.setForeground(Color.BLACK);
       AgeFeeText.setPreferredSize(new Dimension(100,20));
       AgeFeeText.setText("Age Fee");
       contentPane.add(AgeFeeText);
       layout.putConstraint(SpringLayout.WEST, AgeFeeText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, AgeFeeText,               106,                SpringLayout.WEST, contentPane);
       
       scrollbar5 = new JScrollBar(Scrollbar.HORIZONTAL,1,0,0,50);
       Dimension dimension5 = new Dimension(100,20);
       scrollbar5.setPreferredSize(dimension5);
       scrollbar5.setValue(aggFeeScroll);
       scrollbar5.addAdjustmentListener(new scrollListenerAggFee());
       contentPane.add(scrollbar5);
       layout.putConstraint(SpringLayout.WEST, scrollbar5,                114,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, scrollbar5,                106,                SpringLayout.WEST, contentPane);
       
       ageText.setText("  " + getDisplayValue(aggFeeScrollRate, "0.00000####"));
       ageText.setFont(new Font("Arial", Font.PLAIN,10));
       ageText.setBackground(Color.WHITE);
       ageText.setPreferredSize(new Dimension(60,20));
       contentPane.add(ageText);
       layout.putConstraint(SpringLayout.WEST, ageText,                215,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, ageText,                106,                SpringLayout.WEST, contentPane);
       
       
       BandwidthText.setFont(new Font("Arial", Font.PLAIN,12));
       BandwidthText.setBackground(Color.WHITE);
       BandwidthText.setForeground(Color.BLACK);
       BandwidthText.setPreferredSize(new Dimension(100,20));
       BandwidthText.setText("BandWidth");
       contentPane.add(BandwidthText);
       layout.putConstraint(SpringLayout.WEST, BandwidthText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, BandwidthText,                126,                SpringLayout.WEST, contentPane);
       
       scrollbar6 = new JScrollBar(Scrollbar.HORIZONTAL,1,0,0,50);
       Dimension dimension6 = new Dimension(100,20);
       scrollbar6.setPreferredSize(dimension6);
       scrollbar6.setValue(bwScroll);
       scrollbar6.addAdjustmentListener(new scrollListenerBW());
       contentPane.add(scrollbar6);
       layout.putConstraint(SpringLayout.WEST, scrollbar6,                114,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, scrollbar6,                126,                SpringLayout.WEST, contentPane);
       
       bandText.setText("  " + getDisplayValue(bwScrollRate, "0.00000####"));
       bandText.setFont(new Font("Arial", Font.PLAIN,10));
       bandText.setBackground(Color.WHITE);
       bandText.setPreferredSize(new Dimension(60,20));
       contentPane.add(bandText);
       layout.putConstraint(SpringLayout.WEST, bandText,215,SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, bandText,126,SpringLayout.WEST, contentPane);
       
       OtherFeeText.setFont(new Font("Arial", Font.PLAIN,12));
       OtherFeeText.setBackground(Color.WHITE);
       OtherFeeText.setForeground(Color.BLACK);
       OtherFeeText.setPreferredSize(new Dimension(100,20));
       OtherFeeText.setText("Other Fee");
       contentPane.add(OtherFeeText);
       layout.putConstraint(SpringLayout.WEST, OtherFeeText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, OtherFeeText,                146,                SpringLayout.WEST, contentPane);
       
       scrollbar7 = new JScrollBar(Scrollbar.HORIZONTAL,1,0,0,50);
       Dimension dimension7 = new Dimension(100,20);
       scrollbar7.setPreferredSize(dimension7);
       scrollbar7.setValue(otherFeeScroll);
       scrollbar7.addAdjustmentListener(new scrollListenerOtherFee());
       contentPane.add(scrollbar7);
       layout.putConstraint(SpringLayout.WEST, scrollbar7,                114,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, scrollbar7,                146,                SpringLayout.WEST, contentPane);
       
       feeText.setText("  " + getDisplayValue(otherFeeScrollRate, "0.00000####"));
       feeText.setFont(new Font("Arial", Font.PLAIN,10));
       feeText.setBackground(Color.WHITE);
       feeText.setPreferredSize(new Dimension(60,20));
       contentPane.add(feeText);
       layout.putConstraint(SpringLayout.WEST, feeText,                215,               SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, feeText,                146,                SpringLayout.WEST, contentPane);
       
       MarginText.setFont(new Font("Arial", Font.PLAIN,12));
       MarginText.setBackground(Color.WHITE);
       MarginText.setForeground(Color.BLACK);
       MarginText.setPreferredSize(new Dimension(100,20));
       MarginText.setText("Margin");
       contentPane.add(MarginText);
       layout.putConstraint(SpringLayout.WEST, MarginText,                10,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, MarginText,                166,                SpringLayout.WEST, contentPane);
       
       scrollbar8 = new JScrollBar(Scrollbar.HORIZONTAL,1,0,0,300);
       Dimension dimension8 = new Dimension(100,20);
       scrollbar8.setPreferredSize(dimension8);
       scrollbar8.setValue(marginScroll);
       scrollbar8.addAdjustmentListener(new scrollListenerMargin());
       contentPane.add(scrollbar8);
       layout.putConstraint(SpringLayout.WEST, scrollbar8,                114,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, scrollbar8,                166,                SpringLayout.WEST, contentPane);
       
       marText.setText("  " + getDisplayValue(marginScrollRate, "0.00000####"));
       marText.setFont(new Font("Arial", Font.PLAIN,10));
       marText.setBackground(Color.WHITE);
       marText.setPreferredSize(new Dimension(60,20));
       contentPane.add(marText);
       layout.putConstraint(SpringLayout.WEST, marText,                215,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, marText,                166,                SpringLayout.WEST, contentPane);        
       
       attractiveText1.setPreferredSize(new Dimension(340,20));
       attractiveText1.setText("________________________________________");
       contentPane.add(attractiveText1);
       layout.putConstraint(SpringLayout.WEST, attractiveText1,10,SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, attractiveText1,  180,SpringLayout.WEST, contentPane);
       
       
       attractiveText.setFont(new Font("Arial", Font.BOLD,12));
       attractiveText.setBackground(Color.WHITE);
       attractiveText.setForeground(Color.BLACK);
       attractiveText.setPreferredSize(new Dimension(120,20));
       attractiveText.setText("Attractiveness Index");
       contentPane.add(attractiveText);
       layout.putConstraint(SpringLayout.WEST, attractiveText,10,SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, attractiveText,  200,SpringLayout.WEST, contentPane);
       
       
       
       /*buttonstatus.setBackground(new Color(0,255,0));
       buttonstatus.setPreferredSize(new Dimension(55,13));
       contentPane.add(buttonstatus);
       layout.putConstraint(SpringLayout.WEST, buttonstatus,                130,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, buttonstatus,                188,                SpringLayout.WEST, contentPane);
       
       buttonstatus1.setBackground(new Color(255,0,0));
       buttonstatus1.setPreferredSize(new Dimension(32,13));
       contentPane.add(buttonstatus1);
       layout.putConstraint(SpringLayout.WEST, buttonstatus1,                180,                SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, buttonstatus1,                188,                SpringLayout.WEST, contentPane);*/
       
       
       setAttractivenessIndex();
       progressBar.setPreferredSize(new Dimension(100,13));
       progressBar.setValue((int)Math.round(attractivenessIndex));
       progressBar.setBackground(new Color(255,0,0));
       progressBar.setForeground(new Color(0,255,0));
       contentPane.add(progressBar);
       layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
       
       
       setAttractivenessIndex();
       sloganText.setFont(new Font("Arial", Font.BOLD,12));
       sloganText.setBackground(Color.WHITE);
       sloganText.setForeground(Color.BLACK);
       sloganText.setPreferredSize(new Dimension(120,20));
       sloganText.setText(slogan);
       contentPane.add(sloganText);
       layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
       
       attractiveText2.setPreferredSize(new Dimension(340,20));
       attractiveText2.setText("________________________________________");
       contentPane.add(attractiveText2);
       layout.putConstraint(SpringLayout.WEST, attractiveText2,10,SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, attractiveText2,  275,SpringLayout.WEST, contentPane);
       
       setChartValues();
       
       datasetPie.setValue("TDSP",tdspChart);
       datasetPie.setValue("Sales Fee",salesFeeChart);
       datasetPie.setValue("TEE Margin",marginChart);
       datasetPie.setValue("Cust Savings",custSavingsChart);
       datasetPie.setValue("TEE Cost",teeCostChart);
       
       JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
       chartPie.setBackgroundPaint(Color.WHITE);
       TextTitle ss = new TextTitle();
       ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
       ss.setText("DISTRIBUTION OF COST ELEMENTS              BEFORE TAXES");
       ss.setFont(new Font("Arial", Font.BOLD,12));
       chartPie.setTitle(ss);
       Image pieimage = chartPie.createBufferedImage(285,230);
       ImageIcon pieImageIcon = new ImageIcon(pieimage);
       piebutton = new JLabel(pieImageIcon);
       contentPane.add(piebutton);
       layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
       layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);
       
   }
   
   /*public void paint(Graphics g) 
   {
       super.paint(g);
       g.drawLine(10,186,290,186);
   }*/
 
   public String getDisplayValue(double d, String format)
   {
       NumberFormat nf = new DecimalFormat(format);
       return nf.format(d);
   }
   
   public void setChartValues()
   {
       teeCost = energyCost + addOn + custCharge + addlVol + salesFee + aggFee + bw + otherFee+ margin;
       //System.out.println("energyCost : " + energyCost);
       //System.out.println("addOn : " + addOn);
       //System.out.println("custCharge : " + custCharge);
       //System.out.println("addlVol : " + addlVol);
       //System.out.println("salesFee : " + salesFee);
       //System.out.println("aggFee : " + aggFee);
       //System.out.println("bw : " + bw);
       //System.out.println("otherFee : " + otherFee);
       //System.out.println("margin : " + margin);
       
       custSavings = ptb - (teeCost + tdsp);
       if(custSavings < 0)
           custSavings = 0;
       
       double total = teeCost + tdsp + salesFee + margin + custSavings;
       
       /*teeCostChart = Math.round((teeCost * 100 / total));
       tdspChart = Math.round((tdsp * 100 / total));
       salesFeeChart =  Math.round((salesFee * 100 / total));
       marginChart = Math.round((margin * 100 / total));
       custSavingsChart = Math.round((custSavings * 100 / total));*/
       
       teeCostChart = teeCost * 100 / total;
       tdspChart = tdsp * 100 / total;
       salesFeeChart = salesFee * 100 / total;
       marginChart = margin * 100 / total;
       custSavingsChart = custSavings * 100 / total;
       
       if(teeCostChart == 0)
           teeCostChart = 0.00001;
       if(tdspChart == 0)
           tdspChart = 0.00001;
       if(salesFeeChart == 0)
           salesFeeChart = 0.00001;
       if(marginChart == 0)
           marginChart = 0.00001;
       if(custSavingsChart == 0)
           custSavingsChart = 0.00001;
       
       //System.out.println("\nteeCost : " + teeCost);
       //System.out.println("tdsp : " + tdsp);
       //System.out.println("salesFee : " + salesFee);
       //System.out.println("margin : " + margin);
       //System.out.println("cust save : " + custSavings);
       
       //System.out.println("\nTotal : " + total);
       
       //System.out.println("\nteeCostChart : " + teeCostChart);
       //System.out.println("tdspChart: " + tdspChart);
       //System.out.println("salesFeeChart : " + salesFeeChart);
       //System.out.println("marginChart : " + marginChart);
       //System.out.println("custSavingsChart : " + custSavingsChart);
       
       //        //System.out.println(" : " + );
   }
   
   // Attractiveness Index
   
   public void setAttractivenessIndex()
   {
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
       
       double marginRatio = (margin/teeCost) * 100;
       
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
       
       if(custSavings != 0)
       {
           double custSavingsRatio = (custSavings / ptb) * 100;
           
           if(custSavingsRatio <= 5)
               points += 1;
           else if(custSavingsRatio <= 10)
               points += 2;
           else if(custSavingsRatio <= 15)
               points += 3;
           else
               points += 4;
       }
       
       if(loadFactor <= 20)
           points += 1;
       else if(loadFactor <= 40)
           points += 2;
       else if(loadFactor <= 60)
           points += 3;
       else
           points += 4;
       
       //System.out.println("Points : " + points);
       
       if(margin != 0)
       {
           percentageAttained = points / maximumPoints;
       }
       
       double index = 1 - percentageAttained;
       
       //System.out.println("index : " + index);
       
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
           
          /* if(index < 0.3)
               slogan = "Yahoo!";
           else if(index < 0.58)
               slogan = "Feels Very Good";
           else if(index < 0.6)
               slogan = "A Good Day's Work";
           else if(index < 0.8)
               slogan = "Try to Do Better";
           else if(index < 0.9)
               slogan = "No Way";
           else
               slogan = "A Complete Dud";*/
       }
       
       //System.out.println("Slogan : " + slogan);
       
       attractivenessIndex = percentageAttained * 100;
       
       //System.out.println("AttractivenessIndex : " + attractivenessIndex);
   }
   
   // customer charge 
   class scrollListenerCustCharge implements AdjustmentListener
   {
       public void adjustmentValueChanged(AdjustmentEvent e)
       {
           custChargeScroll = e.getValue();
           custChargeScrollRate = custChargeScroll;
           custCh$Text.setText("  " + getDisplayValue(custChargeScrollRate, "0.0"));
           custCh$Text.setFont(new Font("Arial", Font.PLAIN,10));
           custCh$Text.setBackground(Color.WHITE);
           custCh$Text.setPreferredSize(new Dimension(60,20));
           contentPane.add(custCh$Text);
           layout.putConstraint(SpringLayout.WEST, custCh$Text,                    215,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, custCh$Text,                    30,                    SpringLayout.WEST, contentPane);
           
           contentPane.remove(piebutton);
           contentPane.remove(progressBar);
           contentPane.remove(sloganText);
           
           // Calculation
           custCharge = custChargeScrollRate * months * accounts;
           
           //System.out.println("custChargeScroll : " + custChargeScroll);
           //System.out.println("custChargeScrollRate : " + custChargeScrollRate);
           //System.out.println("months : " + months);
           //System.out.println("accounts : " + accounts);
           //System.out.println("custCharge : " + custCharge);
           
           setChartValues();
           
           datasetPie.setValue("TDSP", tdspChart);
           datasetPie.setValue("Sales Fee", salesFeeChart);
           datasetPie.setValue("TEE Margin", marginChart);
           datasetPie.setValue("Cust Savings", custSavingsChart);
           datasetPie.setValue("TEE Cost", teeCostChart);
           
           JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
           chartPie.setBackgroundPaint(Color.WHITE);
           TextTitle ss = new TextTitle();
           ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
           ss.setText("DISTRIBUTION OF COST ELEMENTS                    BEFORE TAXES");
           ss.setFont(new Font("Arial", Font.BOLD,12));
           chartPie.setTitle(ss);
           Image pieimage = chartPie.createBufferedImage(285,230);
           ImageIcon pieImageIcon = new ImageIcon(pieimage);
           piebutton = new JLabel(pieImageIcon);
           contentPane.add(piebutton);
           layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);
           
           setAttractivenessIndex();
           progressBar.setPreferredSize(new Dimension(100,13));
           progressBar.setValue((int)Math.round(attractivenessIndex));
           progressBar.setBackground(new Color(255,0,0));
           progressBar.setForeground(new Color(0,255,0));
           contentPane.add(progressBar);
           layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
           
           
           setAttractivenessIndex();
           sloganText.setFont(new Font("Arial", Font.BOLD,12));
           sloganText.setBackground(Color.WHITE);
           sloganText.setForeground(Color.BLACK);
           sloganText.setPreferredSize(new Dimension(120,20));
           sloganText.setText(slogan);
           contentPane.add(sloganText);
           layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
           repaint();
           
       }
   }    
   
   // Addl Volatility
   class scrollListenerAddlVol implements AdjustmentListener
   {
       public void adjustmentValueChanged(AdjustmentEvent e)
       {
           addlVolScroll = e.getValue();
           addlVolScrollRate = addlVolScroll / 10.0;
           
           VolatiText.setText("  " + getDisplayValue(addlVolScrollRate, "0.0") + "%");
           VolatiText.setFont(new Font("Arial", Font.PLAIN,10));
           VolatiText.setBackground(Color.WHITE);
           VolatiText.setPreferredSize(new Dimension(60,20));
           contentPane.add(VolatiText);
           layout.putConstraint(SpringLayout.WEST, VolatiText,                    215,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, VolatiText,                    50,                    SpringLayout.WEST, contentPane);
           
           contentPane.remove(piebutton);
           contentPane.remove(progressBar);
           contentPane.remove(sloganText);
           
           // Calculation
           addlVol = energyCost * addlVolScrollRate;
           
           setChartValues();
           
           datasetPie.setValue("TDSP", tdspChart);
           datasetPie.setValue("Sales Fee", salesFeeChart);
           datasetPie.setValue("TEE Margin", marginChart);
           datasetPie.setValue("Cust Savings", custSavingsChart);
           datasetPie.setValue("TEE Cost", teeCostChart);
           
           JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
           chartPie.setBackgroundPaint(Color.WHITE);
           TextTitle ss = new TextTitle();
           ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
           ss.setText("DISTRIBUTION OF COST ELEMENTS                    BEFORE TAXES");
           ss.setFont(new Font("Arial", Font.BOLD,12));
           chartPie.setTitle(ss);
           Image pieimage = chartPie.createBufferedImage(285,230);
           ImageIcon pieImageIcon = new ImageIcon(pieimage);
           piebutton = new JLabel(pieImageIcon);
           contentPane.add(piebutton);
           layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);
           
           setAttractivenessIndex();
           progressBar.setPreferredSize(new Dimension(100,13));
           progressBar.setValue((int)Math.round(attractivenessIndex));
           progressBar.setBackground(new Color(255,0,0));
           progressBar.setForeground(new Color(0,255,0));
           contentPane.add(progressBar);
           layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
           
           
           setAttractivenessIndex();
           sloganText.setFont(new Font("Arial", Font.BOLD,12));
           sloganText.setBackground(Color.WHITE);
           sloganText.setForeground(Color.BLACK);
           sloganText.setPreferredSize(new Dimension(120,20));
           sloganText.setText(slogan);
           contentPane.add(sloganText);
           layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
           
           repaint();
       }
   }
   //Sales Fee
   class scrollListenerSalesFee implements AdjustmentListener
   {
       public void adjustmentValueChanged(AdjustmentEvent e)
       {
           salesFeeScroll = e.getValue();
           salesFeeScrollRate  = salesFeeScroll / 100000.0;
           
           SalesText.setText("  " + getDisplayValue(salesFeeScrollRate, "0.00000####"));
           SalesText.setFont(new Font("Arial", Font.PLAIN,10));
           SalesText.setBackground(Color.WHITE);
           SalesText.setPreferredSize(new Dimension(60,20));
           contentPane.add(SalesText);
           layout.putConstraint(SpringLayout.WEST, SalesText,                    215,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, SalesText,                    86,                    SpringLayout.WEST, contentPane);
           
           contentPane.remove(piebutton);
           contentPane.remove(progressBar);
           contentPane.remove(sloganText);
           
           // Calculation
           salesFee = contractkWh * salesFeeScrollRate;
           
           setChartValues();
           
           datasetPie.setValue("TDSP", tdspChart);
           datasetPie.setValue("Sales Fee", salesFeeChart);
           datasetPie.setValue("TEE Margin", marginChart);
           datasetPie.setValue("Cust Savings", custSavingsChart);
           datasetPie.setValue("TEE Cost", teeCostChart);
           
           JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
           chartPie.setBackgroundPaint(Color.WHITE);
           TextTitle ss = new TextTitle();
           ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
           ss.setText("DISTRIBUTION OF COST ELEMENTS                    BEFORE TAXES");
           ss.setFont(new Font("Arial", Font.BOLD,12));
           chartPie.setTitle(ss);
           Image pieimage = chartPie.createBufferedImage(285,230);
           ImageIcon pieImageIcon = new ImageIcon(pieimage);
           piebutton = new JLabel(pieImageIcon);
           contentPane.add(piebutton);
           layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);
           
           setAttractivenessIndex();
           progressBar.setPreferredSize(new Dimension(100,13));
           progressBar.setValue((int)Math.round(attractivenessIndex));
           progressBar.setBackground(new Color(255,0,0));
           progressBar.setForeground(new Color(0,255,0));
           contentPane.add(progressBar);
           layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
           
           
           setAttractivenessIndex();
           sloganText.setFont(new Font("Arial", Font.BOLD,12));
           sloganText.setBackground(Color.WHITE);
           sloganText.setForeground(Color.BLACK);
           sloganText.setPreferredSize(new Dimension(120,20));
           sloganText.setText(slogan);
           contentPane.add(sloganText);
           layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
           repaint();
       }
   }
   //Agg Fee
   class scrollListenerAggFee implements AdjustmentListener
   {
       public void adjustmentValueChanged(AdjustmentEvent e)
       {
           aggFeeScroll = e.getValue();
           aggFeeScrollRate = aggFeeScroll / 10000.0;
           ageText.setText("  " + getDisplayValue(aggFeeScrollRate, "0.00000####"));
           ageText.setFont(new Font("Arial", Font.PLAIN,10));
           ageText.setBackground(Color.WHITE);
           ageText.setPreferredSize(new Dimension(60,20));
           contentPane.add(ageText);
           layout.putConstraint(SpringLayout.WEST, ageText,                    215,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, ageText,                    106,                    SpringLayout.WEST, contentPane);
           
           contentPane.remove(piebutton);
           contentPane.remove(progressBar);
           contentPane.remove(sloganText);
           
           // Calculation
           aggFee = contractkWh * aggFeeScrollRate;
           
           //System.out.println("aggFeeScroll : " + aggFeeScroll);
           //System.out.println("aggFeeScrollRate : " + aggFeeScrollRate);
           //System.out.println("contractkWh : " + contractkWh);
           //System.out.println("aggFee : " + aggFee);
           
           setChartValues();
           
           datasetPie.setValue("TDSP", tdspChart);
           datasetPie.setValue("Sales Fee", salesFeeChart);
           datasetPie.setValue("TEE Margin", marginChart);
           datasetPie.setValue("Cust Savings", custSavingsChart);
           datasetPie.setValue("TEE Cost", teeCostChart);
           
           JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
           chartPie.setBackgroundPaint(Color.WHITE);
           TextTitle ss = new TextTitle();
           ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
           ss.setText("DISTRIBUTION OF COST ELEMENTS                    BEFORE TAXES");
           ss.setFont(new Font("Arial", Font.BOLD,12));
           chartPie.setTitle(ss);
           Image pieimage = chartPie.createBufferedImage(285,230);
           ImageIcon pieImageIcon = new ImageIcon(pieimage);
           piebutton = new JLabel(pieImageIcon);
           contentPane.add(piebutton);
           layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);
           
           setAttractivenessIndex();
           progressBar.setPreferredSize(new Dimension(100,13));
           progressBar.setValue((int)Math.round(attractivenessIndex));
           progressBar.setBackground(new Color(255,0,0));
           progressBar.setForeground(new Color(0,255,0));
           contentPane.add(progressBar);
           layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
           
           
           setAttractivenessIndex();
           sloganText.setFont(new Font("Arial", Font.BOLD,12));
           sloganText.setBackground(Color.WHITE);
           sloganText.setForeground(Color.BLACK);
           sloganText.setPreferredSize(new Dimension(120,20));
           sloganText.setText(slogan);
           contentPane.add(sloganText);
           layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
           repaint();
       }
   }
   //BandWidth
   class scrollListenerBW implements AdjustmentListener
   {
       
       public void adjustmentValueChanged(AdjustmentEvent e)
       {
           
           bwScroll = e.getValue();
           
           bwScrollRate = bwScroll / 10000.0;
           bandText.setText("  " + getDisplayValue(bwScrollRate, "0.00000####"));
           bandText.setFont(new Font("Arial", Font.PLAIN,10));
           bandText.setBackground(Color.WHITE);
           bandText.setPreferredSize(new Dimension(60,20));
           contentPane.add(bandText);
           layout.putConstraint(SpringLayout.WEST, bandText,                    215,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, bandText,                    126,                    SpringLayout.WEST, contentPane);
           
           contentPane.remove(piebutton);
           contentPane.remove(progressBar);
           contentPane.remove(sloganText);
           
           // Calculation
           bw = contractkWh * bwScrollRate;
           
           setChartValues();
           
           datasetPie.setValue("TDSP", tdspChart);
           datasetPie.setValue("Sales Fee", salesFeeChart);
           datasetPie.setValue("TEE Margin", marginChart);
           datasetPie.setValue("Cust Savings", custSavingsChart);
           datasetPie.setValue("TEE Cost", teeCostChart);
           
           JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
           chartPie.setBackgroundPaint(Color.WHITE);
           TextTitle ss = new TextTitle();
           ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
           ss.setText("DISTRIBUTION OF COST ELEMENTS                    BEFORE TAXES");
           ss.setFont(new Font("Arial", Font.BOLD,12));
           chartPie.setTitle(ss);
           Image pieimage = chartPie.createBufferedImage(285,230);
           ImageIcon pieImageIcon = new ImageIcon(pieimage);
           piebutton = new JLabel(pieImageIcon);
           contentPane.add(piebutton);
           layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);
           
           setAttractivenessIndex();
           progressBar.setPreferredSize(new Dimension(100,13));
           progressBar.setValue((int)Math.round(attractivenessIndex));
           progressBar.setBackground(new Color(255,0,0));
           progressBar.setForeground(new Color(0,255,0));
           contentPane.add(progressBar);
           layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
           
           
           setAttractivenessIndex();
           sloganText.setFont(new Font("Arial", Font.BOLD,12));
           sloganText.setBackground(Color.WHITE);
           sloganText.setForeground(Color.BLACK);
           sloganText.setPreferredSize(new Dimension(120,20));
           sloganText.setText(slogan);
           contentPane.add(sloganText);
           layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
           repaint();
       }
   }
   //OtherFee
   class scrollListenerOtherFee implements AdjustmentListener
   {
       public void adjustmentValueChanged(AdjustmentEvent e)
       {	
           otherFeeScroll = e.getValue();
           otherFeeScrollRate = otherFeeScroll / 10000.0;
           feeText.setText("  "+ getDisplayValue(otherFeeScrollRate, "0.00000####"));
           feeText.setFont(new Font("Arial", Font.PLAIN,10));
           feeText.setBackground(Color.WHITE);
           feeText.setPreferredSize(new Dimension(60,20));
           contentPane.add(feeText);
           layout.putConstraint(SpringLayout.WEST, feeText,                    215,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, feeText,                    146,                    SpringLayout.WEST, contentPane);
           
           contentPane.remove(piebutton);
           contentPane.remove(progressBar);
           contentPane.remove(sloganText);
           
           // Calculation
           otherFee = contractkWh * otherFeeScrollRate;
           
           setChartValues();
           
           datasetPie.setValue("TDSP", tdspChart);
           datasetPie.setValue("Sales Fee", salesFeeChart);
           datasetPie.setValue("TEE Margin", marginChart);
           datasetPie.setValue("Cust Savings", custSavingsChart);
           datasetPie.setValue("TEE Cost", teeCostChart);            
           
           JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
           chartPie.setBackgroundPaint(Color.WHITE);
           TextTitle ss = new TextTitle();
           ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
           ss.setText("DISTRIBUTION OF COST ELEMENTS                    BEFORE TAXES");
           ss.setFont(new Font("Arial", Font.BOLD,12));
           chartPie.setTitle(ss);
           Image pieimage = chartPie.createBufferedImage(285,230);
           ImageIcon pieImageIcon = new ImageIcon(pieimage);
           piebutton = new JLabel(pieImageIcon);
           contentPane.add(piebutton);
           layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);            
           
           setAttractivenessIndex();
           progressBar.setPreferredSize(new Dimension(100,13));
           progressBar.setValue((int)Math.round(attractivenessIndex));
           progressBar.setBackground(new Color(255,0,0));
           progressBar.setForeground(new Color(0,255,0));
           contentPane.add(progressBar);
           layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
           
           
           setAttractivenessIndex();
           sloganText.setFont(new Font("Arial", Font.BOLD,12));
           sloganText.setBackground(Color.WHITE);
           sloganText.setForeground(Color.BLACK);
           sloganText.setPreferredSize(new Dimension(120,20));
           sloganText.setText(slogan);
           contentPane.add(sloganText);
           layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
           repaint();
       }
       
   }
   class scrollListenerMargin implements AdjustmentListener
   {
       public void adjustmentValueChanged(AdjustmentEvent e)
       {
           marginScroll = e.getValue();
           marginScrollRate = marginScroll / 10000.0;
           marText.setText("  " + getDisplayValue(marginScrollRate, "0.00000####"));
           marText.setFont(new Font("Arial", Font.PLAIN,10));
           marText.setBackground(Color.WHITE);
           marText.setPreferredSize(new Dimension(60,20));
           contentPane.add(marText);
           layout.putConstraint(SpringLayout.WEST, marText,                    215,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, marText,                    166,                    SpringLayout.WEST, contentPane);
           
           contentPane.remove(piebutton);
           contentPane.remove(progressBar);
           contentPane.remove(sloganText);
           
           // Calculation
           margin = contractkWh * marginScrollRate;
           
           setChartValues();
           
           datasetPie.setValue("TDSP", tdspChart);
           datasetPie.setValue("Sales Fee", salesFeeChart);
           datasetPie.setValue("TEE Margin", marginChart);
           datasetPie.setValue("Cust Savings", custSavingsChart);
           datasetPie.setValue("TEE Cost", teeCostChart);
           
           JFreeChart chartPie = ChartFactory.createPieChart("",datasetPie,false,false,false);
           chartPie.setBackgroundPaint(Color.WHITE);
           TextTitle ss = new TextTitle();
           ss.setHorizontalAlignment(HorizontalAlignment.LEFT);
           ss.setText("DISTRIBUTION OF COST ELEMENTS                    BEFORE TAXES");
           ss.setFont(new Font("Arial", Font.BOLD,12));
           chartPie.setTitle(ss);
           Image pieimage = chartPie.createBufferedImage(285,230);
           ImageIcon pieImageIcon = new ImageIcon(pieimage);
           piebutton = new JLabel(pieImageIcon);
           contentPane.add(piebutton);
           layout.putConstraint(SpringLayout.WEST, piebutton,                    10,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, piebutton,                    300,                    SpringLayout.WEST, contentPane);
           
           setAttractivenessIndex();
           progressBar.setPreferredSize(new Dimension(100,13));
           progressBar.setValue((int)Math.round(attractivenessIndex));
           progressBar.setBackground(new Color(255,0,0));
           progressBar.setForeground(new Color(0,255,0));
           contentPane.add(progressBar);
           layout.putConstraint(SpringLayout.WEST, progressBar,                    114,                    SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, progressBar,                    228,                    SpringLayout.WEST, contentPane);
           
           setAttractivenessIndex();
           sloganText.setFont(new Font("Arial", Font.BOLD,12));
           sloganText.setBackground(Color.WHITE);
           sloganText.setForeground(Color.BLACK);
           sloganText.setPreferredSize(new Dimension(120,20));
           sloganText.setText(slogan);
           contentPane.add(sloganText);
           layout.putConstraint(SpringLayout.WEST, sloganText,114,SpringLayout.WEST, contentPane);
           layout.putConstraint(SpringLayout.NORTH, sloganText,  260,SpringLayout.WEST, contentPane);
           repaint();
       }
   }
}
