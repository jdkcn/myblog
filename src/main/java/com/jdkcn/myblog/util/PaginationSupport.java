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
package com.jdkcn.myblog.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: PaginationSupport.java 417 2011-05-08 09:03:26Z rory.cn $
 */
public class PaginationSupport<T> {
	
	// 默认每页显示条数
	public static final int DEFAULT_PAGE_SIZE = 10;

	// 默认开始记录条数
	public static final int DEFAULT_START = 0;

	// 默认显示页码最大数
	public static final int DEFAULT_MAX_INDEX_PAGES = 10;

	// 开始记录条数
	private int start = 0;

	// 每页显示条数
	private int pageSize = DEFAULT_PAGE_SIZE;

	// 总记录数
	private int totalCount;

	// 总页数
	private int totalPage;

	// 当前页
	private int currentPage;

	// 返回结果集
	private List<T> items = new LinkedList<T>();

	public PaginationSupport() {
		setTotalCount(0);
		setStart(0);
		setItems(new LinkedList<T>());
	}

	public PaginationSupport(List<T> items, int totalCount) {
		setPageSize(DEFAULT_PAGE_SIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStart(0);
	}

	public PaginationSupport(List<T> items, int totalCount, int start) {
		setPageSize(DEFAULT_PAGE_SIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStart(start);
	}

	public PaginationSupport(List<T> items, int totalCount, int start, int pageSize) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setStart(start);
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		if (items == null) {
			this.items = new LinkedList<T>();
		} else {
			this.items = items;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0) {
				count++;
			}
			this.totalPage = count;
		} else {
			this.totalCount = 0;
		}
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		if (totalCount <= 0) {
			this.start = 0;
		} else if (start >= totalCount) {
			this.start = totalCount;
		}else if (start < 0) {
			this.start = 0;
		} else {
			this.start = start;
		}
		this.currentPage = start / pageSize + 1;
	}

	public int getNextIndex() {
		int nextIndex = getStart() + pageSize;
		return nextIndex >= totalCount ? getStart() : nextIndex;
	}

	public int getPreviousIndex() {
		int previousIndex = getStart() - pageSize;
		return previousIndex < 0 ? DEFAULT_START : previousIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
