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
package com.jdkcn.myblog.web.page;

import java.util.LinkedList;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.jdkcn.myblog.Constants;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.hibernate.Condition;
import com.jdkcn.myblog.service.EntryService;
import com.jdkcn.myblog.service.impl.EntryServiceImpl;
import com.jdkcn.myblog.util.PaginationSupport;
import com.jdkcn.myblog.util.Range;
import com.jdkcn.myblog.util.Sorter;
import com.jdkcn.myblog.web.page.admin.Entries;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: EntriesPageTest.java 433 2011-05-16 11:45:33Z rory.cn $
 */
public class EntriesPageTest {

	@Test
	public void testEntriesPage() throws Exception {

		EntryService entryServiceMock = EasyMock.createMock(EntryServiceImpl.class);

		List<Entry> list = new LinkedList<Entry>();
		list.add(new Entry());
		Capture<Range> captureRange = new Capture<Range>();

		PaginationSupport<Entry> ps = new PaginationSupport<Entry>(list, list.size());
		expect(entryServiceMock.search(EasyMock.anyObject(Condition.class), EasyMock.capture(captureRange), EasyMock.anyObject(Sorter.class))).andReturn(ps)
				.once();

		Entries entries = new Entries(entryServiceMock);

		replay(entryServiceMock);

		entries.get();

		Assert.assertEquals(1, entries.getEntries().size());
		Assert.assertEquals(Constants.DEFAULT_PAGE_SIZE, captureRange.getValue().getSize());
		verify(entryServiceMock);

	}
	
	@Test
	public void testEntriesPageSearch() throws Exception {
		
		EntryService entryServiceMock = EasyMock.createMock(EntryServiceImpl.class);
		
		List<Entry> list = new LinkedList<Entry>();
		list.add(new Entry());
		Capture<Range> captureRange = new Capture<Range>();
		
		PaginationSupport<Entry> ps = new PaginationSupport<Entry>(list, list.size());
		expect(entryServiceMock.search(EasyMock.anyObject(Condition.class), EasyMock.capture(captureRange), EasyMock.anyObject(Sorter.class))).andReturn(ps)
		.once();
		
		Entries entries = new Entries(entryServiceMock);
		entries.setP(2);
		entries.setSize(5);
		
		replay(entryServiceMock);
		
		entries.get();
		
		Assert.assertEquals(1, entries.getEntries().size());
		Assert.assertEquals(5, captureRange.getValue().getSize());
		Assert.assertEquals(5, captureRange.getValue().getStart());
		Assert.assertEquals(10, captureRange.getValue().getEnd());
		verify(entryServiceMock);
		
	}
}
