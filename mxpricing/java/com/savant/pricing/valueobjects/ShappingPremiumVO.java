/*
 * Created on May 17, 2007
 * 
 * Class Name ShappingPremiumVO.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.valueobjects;


/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShappingPremiumVO {
    
    private int shappingPremiumId;
    private String shappingPremiumType;
    private boolean apply;
    
    public ShappingPremiumVO()
    {
    }

    public boolean isApply() {
        return apply;
    }
    public void setApply(boolean apply) {
        this.apply = apply;
    }
    public int getShappingPremiumId() {
        return shappingPremiumId;
    }
    public String getShappingPremiumType() {
        return shappingPremiumType;
    }
   
    public void setShappingPremiumId(int shappingPremiumId) {
        this.shappingPremiumId = shappingPremiumId;
    }
    public void setShappingPremiumType(String shappingPremiumType) {
        this.shappingPremiumType = shappingPremiumType;
    }
}


/*
*$Log: ShappingPremiumVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/05/18 03:38:08  jnadesan
*initial commit
*
*
*/