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
package com.jdkcn.myblog.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.jdkcn.myblog.domain.Category;
import com.jdkcn.myblog.domain.Category.Type;
import com.jdkcn.myblog.exception.DuplicateException;
import com.jdkcn.myblog.service.CategoryService;
/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory, Ye</a>
 * @since May 10, 2010 11:03:56 AM
 * @version $Id: CategoryServiceImpl.java 427 2011-05-13 09:28:19Z rory.cn $
 */
public class CategoryServiceImpl implements CategoryService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	
	

	/**
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getRoots(Type type) {
		Query query = null;
		if (type != null) {
			query = entityManagerProvider.get().createQuery("from Category where type = :type and parent is null");
			query.setParameter("type", type);
		} else {
			query = entityManagerProvider.get().createQuery("from Category where parent is null");
		}
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	public Category getByName(String name, String parentId) {
		Query query = null;
		if (StringUtils.isBlank(parentId)) {
			query = entityManagerProvider.get().createQuery("from Category where name = :name and parent is null");
		} else {
			query = entityManagerProvider.get().createQuery("from Category where name = :name and parent.id = :parentId");
			query.setParameter("parentId", parentId);
		}
		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<Category> categories = query.getResultList();
		if (categories.isEmpty()) {
			return null;
		}
		return categories.get(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Transactional
	public Category saveOrUpdate(Category category) {
		String parentId = null;
		if (category.getParent() != null) {
			parentId = category.getParent().getId();
		}
		if (StringUtils.isBlank(category.getId())) {
			if (category.getOrder() == null) {
				category.setOrder(getMaxOrder(parentId) + 1);
			}
			category.setCount(0);
		}
		Category exist = getByName(category.getName(), parentId);
		if (exist != null && !StringUtils.equals(category.getId(), exist.getId())) {
			throw new DuplicateException("category in parentId[" + parentId + "] duplicate with name:" + category.getName());
		}
		Category savedCategory = entityManagerProvider.get().merge(category);
		if (category.getParent() != null) {
			category.getParent().getChildren().add(savedCategory);
		}
		return savedCategory;
	}

	/**
	 * {@inheritDoc}
	 */
	public Category get(String id) {
		return entityManagerProvider.get().find(Category.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer getMaxOrder(String parentId) {
		Integer order = null;
		Query query = null;
		if (StringUtils.isNotBlank(parentId)) {
			query = entityManagerProvider.get().createQuery("select max(cate.order) from Category cate where cate.parent.id = :parentId");
			query.setParameter("parentId", parentId);
		} else {
			query = entityManagerProvider.get().createQuery("select max(cate.order) from Category cate where cate.parent is null");
		}
		order = (Integer) query.getSingleResult();
		return order == null ? 0 : order;
	}
}
