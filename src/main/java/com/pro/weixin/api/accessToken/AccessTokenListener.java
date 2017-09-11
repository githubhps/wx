package com.pro.weixin.api.accessToken;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AccessTokenListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		new AccessTokenThread().start();
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
