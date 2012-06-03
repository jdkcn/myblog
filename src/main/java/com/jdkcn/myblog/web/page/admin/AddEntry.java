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
package com.jdkcn.myblog.web.page.admin;

import static com.jdkcn.myblog.Constants.CURRENT_USER;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import com.google.sitebricks.rendering.Decorated;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.domain.User;
import com.jdkcn.myblog.service.BlogService;
import com.jdkcn.myblog.service.EntryService;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: AddEntry.java 427 2011-05-13 09:28:19Z rory.cn $
 */
@Show("/WEB-INF/templates/adm/addentry.html")
@Decorated
@At("/adm/entry/add")
public class AddEntry extends AdminLayout{

	@Inject
	private EntryService entryService;
	
	@Inject
	private BlogService blogService;
	
	@Inject
	private HttpSession httpSession;
	
	private Entry entry = new Entry();
	
	private String blogId;
	
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	@Get
	public void Get() {
	}
	
	@Post
	public String save() {
		if (StringUtils.isNotBlank(entry.getName())) {
			Entry exist = entryService.getByName(blogId, entry.getName());
			if (exist != null) {
				return "/adm/entry/add";
			}
		}
		entry.setBlog(blogService.get(blogId));
		if (StringUtils.isBlank(entry.getExcerpt())) {
			entry.setExcerpt(entry.getContent());
		}
		entry.setType(Entry.Type.ENTRY);
		entry.setStatus(Entry.Status.PUBLISH);
		entry.setAuthor((User)httpSession.getAttribute(CURRENT_USER));
		entryService.saveOrUpdate(entry);
		return "/adm/entries";
	}
}
