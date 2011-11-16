/*
 * Created on May 21, 2007
 * 
 * Class Name MarketPriceVO.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MarketPriceVO implements Serializable {
    private Date marketPeriod;
    private float price ;
    public MarketPriceVO()
    {
    }
    public Date getMarketPeriod() {
        return marketPeriod;
    }
    public float getPrice() {
        return price;
    }
    public void setMarketPeriod(Date marketPeriod) {
        this.marketPeriod = marketPeriod;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}


/*
*$Log: MarketPriceVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/05/21 11:45:54  jnadesan
*entry for market price
*
*
*/