/**
 * Copyright (c) 2005-2011, Rory Ye
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

import com.google.inject.servlet.RequestScoped;
import com.google.sitebricks.SitebricksModule;
import com.google.sitebricks.slf4j.Slf4jModule;
import com.jdkcn.myblog.web.page.Home;
import com.jdkcn.myblog.web.page.Signin;
import com.jdkcn.myblog.web.page.Signout;
import com.jdkcn.myblog.web.page.admin.AddEntry;
import com.jdkcn.myblog.web.page.admin.Dashboard;
import com.jdkcn.myblog.web.page.admin.Entries;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory, Ye</a>
 * @since May 11, 2010 10:46:48 PM
 * @version $Id: MyblogSitebricksModule.java 424 2011-05-12 07:42:10Z rory.cn $
 */
public class MyblogSitebricksModule extends SitebricksModule {

    /**
     * {@inheritDoc}
     * 
     * @see com.google.sitebricks.SitebricksModule#configureSitebricks()
     */
    @Override
    protected void configureSitebricks() {
    	install(new Slf4jModule());
    	scan(Home.class.getPackage());
//        at("/").show(Home.class).in(RequestScoped.class);
//        at("/signin").show(Signin.class).in(RequestScoped.class);
//        at("/signout").serve(Signout.class).in(RequestScoped.class);
//        at("/adm").show(Dashboard.class).in(RequestScoped.class);
//        at("/adm/entries").show(Entries.class).in(RequestScoped.class);
//        at("/adm/entry/add").show(AddEntry.class).in(RequestScoped.class);
//        at("/index.html").show(Home.class).in(RequestScoped.class);
//        embed(ChildrenCategory.class).as("ChildrenCategory");
//        at("/adm/login.jspx").show(Login.class).in(RequestScoped.class);
    }
}
