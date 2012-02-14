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
package com.jdkcn.myblog.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.jdkcn.myblog.domain.Blog;
import com.jdkcn.myblog.domain.Comment;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.domain.Entry.Type;
import com.jdkcn.myblog.domain.User;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: CommentServiceTest.java 440 2011-05-23 15:53:05Z Rory.CN@gmail.com $
 */
public class CommentServiceTest extends AbstractServiceTest {

	private CommentService commentService;
	
	private Blog blog;
	
	private Entry entry;
	
	private User author;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void before() {
		super.before();
		commentService = injector.getInstance(CommentService.class);
		BlogService blogService = injector.getInstance(BlogService.class);
		blog = new Blog();
		blog.setName("Test blog");
		blog.addUrl("http://jdkcn.com");
		blog = blogService.saveOrUpdate(blog);
		
		UserService userService = injector.getInstance(UserService.class);
		author = new User();
		author.setUsername("Tester");
		author.setEmail("tester@gmail.com");
		author.setPassword("******");
		author = userService.saveOrUpdate(author);
		
		EntryService entryService = injector.getInstance(EntryService.class);
		entry = new Entry();
		entry.setBlog(blog);
		entry.setTitle("Test post");
		entry.setType(Type.ENTRY);
		entry.setContent("Test content");
		entry.setExcerpt("Test excerpt");
		entry.setAuthor(author);
		entry = entryService.saveOrUpdate(entry);
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		Comment comment = new Comment();
		comment.setEntry(entry);
		comment.setAuthor(author);
		comment.setContent("Test comment");
		comment.setEmail("tester@gmail.com");
		comment.setIp("127.0.0.1");
		assertNull(comment.getId());
		String commentId = commentService.saveOrUpdate(comment).getId();
		assertNotNull(commentId);
		Comment savedComment = commentService.get(commentId);
		assertEquals("tester@gmail.com", savedComment.getEmail());
	}
}
