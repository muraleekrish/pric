/*
 * Created on Apr 18, 2007
 *
 * ClassName	:  	CustomerPreferenceProductsVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.valueobjects;

import java.io.Serializable;

import com.savant.pricing.valueobjects.ProductsVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomerPreferenceProductsVO implements Serializable
{

    public CustomerPreferenceProductsVO()
    {
    }
    private ProspectiveCustomerVO customer;
    private ProductsVO product;
    

    public ProspectiveCustomerVO getCustomer()
    {
        return customer;
    }
    public void setCustomer(ProspectiveCustomerVO customer)
    {
        this.customer = customer;
    }
    public ProductsVO getProduct()
    {
        return product;
    }
    public void setProduct(ProductsVO product)
    {
        this.product = product;
    }
}

/*
*$Log: CustomerPreferenceProductsVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/18 06:47:39  kduraisamy
*preference products added.
*
*
*/