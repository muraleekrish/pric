/*
 * Created on Dec 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.util.servlets;

import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import com.savant.pricing.timer.TimerLoader;
/**
 * @author jvediyappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StartupServlet extends HttpServlet {
    static Logger log = Logger.getLogger(StartupServlet.class);
    public void init(ServletConfig config) throws ServletException{
    	super.init(config);
    	log.info("- MXenergy startupServlet initialized.");
        ServletContext ctx = config.getServletContext();
    	File timerConfig = new File(ctx.getRealPath(config.getInitParameter("timerconfig")));
    	TimerLoader loader = new TimerLoader(timerConfig);
    	try {
    		loader.init();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}	
	}
    public void destroy(ServletConfig config) throws ServletException{
        ServletContext ctx = config.getServletContext();
        log.info(" - MXenergy startupServlet Destroyed.");
        
    }
    
}
