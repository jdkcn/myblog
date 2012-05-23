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
package com.jdkcn.myblog.web.page.admin;

import java.util.List;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.rendering.Decorated;
import com.jdkcn.myblog.Constants;
import com.jdkcn.myblog.condition.EntryCondition;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.service.EntryService;
import com.jdkcn.myblog.util.PaginationSupport;
import com.jdkcn.myblog.util.Range;
import com.jdkcn.myblog.util.Sorter;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: Entries.java 431 2011-05-16 09:05:31Z rory.cn $
 */
@Show("/WEB-INF/templates/adm/entries.mvel")
@Decorated
@At("/adm/entries")
public class Entries extends AdminLayout{

	private EntryService entryService;
	
	private PaginationSupport<Entry> ps;
	
	private Integer p;
	
	private Integer size;
	
	private List<Entry> entries;
	
	@Inject
	public Entries(EntryService entryService) {
		this.entryService = entryService;
	}
	
	public List<Entry> getEntries() {
		return entries;
	}

	public PaginationSupport<Entry> getPs() {
		return ps;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Get
	public void get() {
		EntryCondition condition = new EntryCondition();
		if (size == null) {
			size = Constants.DEFAULT_PAGE_SIZE;
		}
		int start = 0;
		if (p != null && p > 1) {
			start = (p - 1) * size;
		}
		Range range = new Range(start, size);
		ps = entryService.search(condition, range, new Sorter().desc("createDate"));
		entries = ps.getItems();
	}
}
