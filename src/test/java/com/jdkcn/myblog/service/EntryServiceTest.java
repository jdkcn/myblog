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
package com.jdkcn.myblog.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.jdkcn.myblog.condition.EntryCondition;
import com.jdkcn.myblog.domain.Blog;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.domain.Entry.CommentStatus;
import com.jdkcn.myblog.domain.Entry.PingStatus;
import com.jdkcn.myblog.domain.Entry.Status;
import com.jdkcn.myblog.domain.Entry.Type;
import com.jdkcn.myblog.domain.User;
import com.jdkcn.myblog.exception.DuplicateException;
import com.jdkcn.myblog.util.PaginationSupport;
import com.jdkcn.myblog.util.Range;
import com.jdkcn.myblog.util.Sorter;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: EntryServiceTest.java 417 2011-05-08 09:03:26Z rory.cn $
 */
public class EntryServiceTest extends AbstractServiceTest {

	private EntryService entryService;

	private Blog blog;

	private User author;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void before() {
		super.before();
		entryService = injector.getInstance(EntryService.class);
		BlogService blogService = injector.getInstance(BlogService.class);
		blog = createBlog("A Simple Myblog.", blogService);

		UserService userService = injector.getInstance(UserService.class);
		author = new User();
		author.setUsername("Tester");
		author.setPassword("******");
		author.setEmail("tester@gmail.com");
		author = userService.saveOrUpdate(author);
	}

	private Blog createBlog(String name, BlogService blogService) {
		Blog blog = new Blog();
		blog.setName(name);
		blog.setDescription(name + "'s description");
		blog = blogService.saveOrUpdate(blog);
		return blog;
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		Entry entry = new Entry();
		entry.setBlog(blog);
		entry.setAuthor(author);
		entry.setType(Type.ENTRY);
		entry.setTitle("Welcome to Myblog 2.0");
		entry.setContent("My first post..");
		entry.setExcerpt("First");
		entry.setCommentStatus(CommentStatus.OPEN);
		entry.setStatus(Status.PUBLISH);
		entry.setPingStatus(PingStatus.OPEN);
		entry.setUpdateDate(new Date());
		entry.setPublishDate(new Date());

		assertNull(entry.getId());
		String entryId = entryService.saveOrUpdate(entry).getId();
		assertNotNull(entryId);

		Entry savedEntry = entryService.get(entryId);
		assertEquals("Welcome to Myblog 2.0", savedEntry.getTitle());
	}

	@Test(expected = DuplicateException.class)
	public void testDuplicateName() throws Exception {
		Entry entry = new Entry();
		entry.setType(Type.ENTRY);
		entry.setBlog(blog);
		entry.setAuthor(author);
		entry.setTitle("Welcome to Myblog 2.0");
		entry.setContent("My first post..");
		entry.setExcerpt("First");
		entry.setName("welcome-to-myblog2");
		entry.setCommentStatus(CommentStatus.OPEN);
		entry.setStatus(Status.PUBLISH);
		entry.setPingStatus(PingStatus.OPEN);
		entry.setUpdateDate(new Date());
		entry.setPublishDate(new Date());

		entryService.saveOrUpdate(entry);

		Entry entry2 = new Entry();
		entry2.setBlog(blog);
		entry2.setType(Type.ENTRY);
		entry2.setAuthor(author);
		entry2.setTitle("Another Welcome");
		entry2.setContent("Another content");
		entry2.setExcerpt("First");
		entry2.setName("welcome-to-myblog2");
		entry2.setCommentStatus(CommentStatus.OPEN);
		entry2.setStatus(Status.PUBLISH);
		entry2.setPingStatus(PingStatus.OPEN);
		entry2.setUpdateDate(new Date());
		entry2.setPublishDate(new Date());

		entryService.saveOrUpdate(entry2);
	}

	@Test
	public void testNotDuplicateName() throws Exception {
		Entry entry = new Entry();
		entry.setType(Type.ENTRY);
		entry.setBlog(blog);
		entry.setAuthor(author);
		entry.setTitle("Welcome to Myblog 2.0");
		entry.setContent("My first post..");
		entry.setExcerpt("First");
		entry.setName("welcome-to-myblog2");
		entry.setCommentStatus(CommentStatus.OPEN);
		entry.setStatus(Status.PUBLISH);
		entry.setPingStatus(PingStatus.OPEN);
		entry.setUpdateDate(new Date());
		entry.setPublishDate(new Date());

		entryService.saveOrUpdate(entry);

		Entry entry2 = new Entry();
		entry2.setBlog(createBlog("Another Blog", injector.getInstance(BlogService.class)));
		entry2.setType(Type.ENTRY);
		entry2.setAuthor(author);
		entry2.setTitle("Another Welcome");
		entry2.setContent("Another content");
		entry2.setExcerpt("First");
		entry2.setName("welcome-to-myblog2");
		entry2.setCommentStatus(CommentStatus.OPEN);
		entry2.setStatus(Status.PUBLISH);
		entry2.setPingStatus(PingStatus.OPEN);
		entry2.setUpdateDate(new Date());
		entry2.setPublishDate(new Date());

		entryService.saveOrUpdate(entry2);
	}

	@Test
	public void testSearch() throws Exception {
		String title = "Title";
		String content = "Content";
		for (int i = 0; i < 30; i++) {
			createEntry(title + i, content + i);
		}
		EntryCondition condition = new EntryCondition();
		PaginationSupport<Entry> ps = entryService.search(condition, new Range(0, 10), new Sorter().asc("publishDate"));
		assertEquals(30, ps.getTotalCount());
		
		title = "Android";
		for (int j = 0; j < 5; j ++) {
			createEntry(title + j, content + j);
		}
		
		condition.setKeyword("Android");
		ps = entryService.search(condition, new Range(0, 5), new Sorter().asc("title"));
		assertEquals(5, ps.getTotalCount());
	}

	private Entry createEntry(String title, String content) {
		Entry entry = new Entry();
		entry.setType(Type.ENTRY);
		entry.setBlog(blog);
		entry.setAuthor(author);
		entry.setTitle(title);
		entry.setContent(content);
		entry.setExcerpt(title);
		entry.setCommentStatus(CommentStatus.OPEN);
		entry.setStatus(Status.PUBLISH);
		entry.setPingStatus(PingStatus.OPEN);
		entry.setUpdateDate(new Date());
		entry.setPublishDate(new Date());

		return entryService.saveOrUpdate(entry);
	}
}
