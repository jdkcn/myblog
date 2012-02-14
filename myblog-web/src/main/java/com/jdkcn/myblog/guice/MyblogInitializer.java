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

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jdkcn.myblog.Constants;
import com.jdkcn.myblog.domain.Blog;
import com.jdkcn.myblog.domain.User;
import com.jdkcn.myblog.service.BlogService;
import com.jdkcn.myblog.service.UserService;

public class MyblogInitializer {
	
	private final UserService userService;
	
	private final String username;
	
	private final String password;
	
	private final String email;
	
	private final BlogService blogService;
	
	@Inject MyblogInitializer(UserService userService, BlogService blogService, @Named("admin.username") String username,
			@Named("admin.password") String password, @Named("admin.email") String email){
		this.userService = userService;
		this.blogService = blogService;
		this.username = username;
		this.password = password;
		this.email = email;
		checkInit();
	}

	private Blog createDefaultBlog(User user) {
		Blog blog = new Blog();
		blog.setName("Another Myblog");
		blog.setDefaultPageSize(Constants.DEFAULT_PAGE_SIZE);
		blog.setDescription("Another Myblog.");
		blog.addUrl("http://code.google.com/p/myblog");
		blog.setOwner(user);
		return blogService.saveOrUpdate(blog);
	}
	
	/**
	 * create the default user
	 */
	private User createDefaultUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setNickname(username);
		return userService.saveOrUpdate(user);
	}
	
	public void checkInit() {
		User user = userService.getByUsername("admin");
		if (user == null) {
			user = createDefaultUser();
		}
		List<Blog> blogs = blogService.getAllBlogs();
		if (blogs.isEmpty()) {
			createDefaultBlog(user);
		}
	}

}
