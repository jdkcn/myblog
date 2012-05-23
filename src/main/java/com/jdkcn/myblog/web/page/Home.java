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
package com.jdkcn.myblog.web.page;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.jdkcn.myblog.domain.Blog;
import com.jdkcn.myblog.domain.Category;
import com.jdkcn.myblog.domain.Category.Type;
import com.jdkcn.myblog.guice.MyblogInitializer;
import com.jdkcn.myblog.service.CategoryService;

@Show("/WEB-INF/templates/home.mvel")
@At("/")
public class Home {
	
	@Inject
	private MyblogInitializer myblogInitializer;
	
	@Inject
	private CategoryService categoryService;

	private String message;
	
	private Blog blog;
	
	public Blog getBlog() {
		return blog;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Get
	public void get() {
		myblogInitializer.checkInit();
		message = "Welcome to myblog 2.0";
		blog = new Blog();
		blog.setName("Myblog 2.0");
		blog.setTheme("prototype");
		Category exist = categoryService.getByName("Test Entry Category", null);
		if (exist == null) {
			exist = new Category("Test Entry Category", Type.ENTRY);
			categoryService.saveOrUpdate(exist);
		}
		categoryService.getRoots(Type.ENTRY);
	}
}
