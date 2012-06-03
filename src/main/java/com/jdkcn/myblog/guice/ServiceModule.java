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

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.jdkcn.myblog.service.BlogService;
import com.jdkcn.myblog.service.CategoryService;
import com.jdkcn.myblog.service.CommentService;
import com.jdkcn.myblog.service.EntryService;
import com.jdkcn.myblog.service.TagService;
import com.jdkcn.myblog.service.UserService;
import com.jdkcn.myblog.service.impl.BlogServiceImpl;
import com.jdkcn.myblog.service.impl.CategoryServiceImpl;
import com.jdkcn.myblog.service.impl.CommentServiceImpl;
import com.jdkcn.myblog.service.impl.EntryServiceImpl;
import com.jdkcn.myblog.service.impl.TagServiceImpl;
import com.jdkcn.myblog.service.impl.UserServiceImpl;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CategoryService.class).to(CategoryServiceImpl.class).in(Singleton.class);
		bind(BlogService.class).to(BlogServiceImpl.class).in(Singleton.class);
		bind(EntryService.class).to(EntryServiceImpl.class).in(Singleton.class);
		bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
		bind(CommentService.class).to(CommentServiceImpl.class).in(Singleton.class);
		bind(TagService.class).to(TagServiceImpl.class).in(Singleton.class);
	}

}
