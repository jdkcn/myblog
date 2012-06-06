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
package com.jdkcn.myblog.condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;


import org.apache.commons.lang.StringUtils;

import com.jdkcn.myblog.Constants;
import com.jdkcn.myblog.domain.Category;
import com.jdkcn.myblog.domain.Entry.Status;
import com.jdkcn.myblog.hibernate.Condition;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: EntryCondition.java 417 2011-05-08 09:03:26Z rory.cn $
 */
public class EntryCondition implements Condition {
	
	private final Map<String, Object> queryParamMap = new HashMap<String, Object>();

	private String keyword;
	
	private Category category;
	
	private List<Category> categories;
	
	private Status excludeStatus;
	
	private List<Status> includeStatuses;
	
	public Status getExcludeStatus() {
		return excludeStatus;
	}

	public void setExcludeStatus(Status excludeStatus) {
		this.excludeStatus = excludeStatus;
	}

	public List<Status> getIncludeStatuses() {
		if (includeStatuses == null) {
			includeStatuses = new ArrayList<Status>();
		}
		return includeStatuses;
	}

	public void setIncludeStatuses(List<Status> includeStatuses) {
		this.includeStatuses = includeStatuses;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isBlank() {
		if (StringUtils.isNotBlank(keyword)) {
			return false;
		}
		if (category != null) {
			return false;
		}
		if (categories != null && !categories.isEmpty()) {
			return false;
		}
		if (excludeStatus != null) {
			return false;
		}
		if (includeStatuses != null && !includeStatuses.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String decorateHql() {
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("from ").append(Constants.PREFIX).append(".Entry");
		boolean hasCondition = false;
		if (!isBlank()) {
			hqlBuilder.append(" where");
		}
		if (StringUtils.isNotBlank(keyword)) {
			if (hasCondition) {
				hqlBuilder.append(" and");
			}
			hqlBuilder.append(" (title like :title or content like :content)");
			queryParamMap.put("title", "%" + keyword + "%");
			queryParamMap.put("content", "%" + keyword + "%");
			hasCondition = true;
		}
		if (category != null) {
			if (hasCondition) {
				hqlBuilder.append(" and");
			}
			hqlBuilder.append(" category.id = :categoryId");
			queryParamMap.put("categoryId", category.getId());
			hasCondition = true;
		}
		if (excludeStatus != null) {
			if (hasCondition) {
				hqlBuilder.append(" and");
			}
			hqlBuilder.append(" status != :excludeStatus");
			queryParamMap.put("excludeStatus", excludeStatus);
			hasCondition = true;
		}
		if (!getIncludeStatuses().isEmpty()) {
			if (hasCondition) {
				hqlBuilder.append(" and");
			}
			hqlBuilder.append(" status in :includeStatuses");
			queryParamMap.put("includeStatuses", getIncludeStatuses());
			hasCondition = true;
		}
		return hqlBuilder.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setParameters(Query query) {
		if (!queryParamMap.isEmpty()) {
			for (Map.Entry<String, Object> entry : queryParamMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

}
