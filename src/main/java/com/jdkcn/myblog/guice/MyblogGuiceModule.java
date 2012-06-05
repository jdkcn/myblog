/**
 * Copyright (c) 2005-2012, Rory Ye
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the Jdkcn.com nor the names of its contributors may
 *       be used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jdkcn.myblog.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;
import com.google.sitebricks.SitebricksModule;
import com.jdkcn.myblog.web.page.Home;
import com.jdkcn.myblog.web.page.Signout;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory, Ye</a>
 * @since May 10, 2010 11:28:26 AM
 * @version $Id: MyblogGuiceModule.java 413 2011-05-04 13:36:16Z rory.cn $
 */
public class MyblogGuiceModule extends SitebricksModule {
	
	private final Logger logger = LoggerFactory.getLogger(MyblogGuiceModule.class);

    
    @Override
    protected void configureSitebricks() {
    	install(new ServiceModule());
    	bind(MyblogInitializer.class).in(Singleton.class);
    	Properties properties = loadProperties("/config.properties");
    	Names.bindProperties(binder(), properties);
    	scan(Home.class.getPackage());
    	at("/signout").serve(Signout.class).in(RequestScoped.class);
    }
    
    
    /**
     * 
     * @param name
     *            the properties file name
     * @return the java.util.Properties
     */
    private Properties loadProperties(final String name) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = MyblogGuiceModule.class.getResourceAsStream(name);
            if (is == null) {
            	logger.error("load properties file[" + name + "] error,not found file.");
            } else {
            	properties.load(is);
            }
        } catch (IOException ex) {
        	logger.error("load properties file[" + name + "] error", ex);
            throw new RuntimeException(ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return properties;
    }

}
