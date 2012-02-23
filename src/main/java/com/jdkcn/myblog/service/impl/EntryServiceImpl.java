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

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.jdkcn.myblog.condition.EntryCondition;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.domain.Entry.CommentStatus;
import com.jdkcn.myblog.domain.Entry.PingStatus;
import com.jdkcn.myblog.domain.Entry.Status;
import com.jdkcn.myblog.exception.DuplicateException;
import com.jdkcn.myblog.hibernate.Condition;
import com.jdkcn.myblog.service.EntryService;
import com.jdkcn.myblog.util.PaginationSupport;
import com.jdkcn.myblog.util.Range;
import com.jdkcn.myblog.util.Sorter;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: EntryServiceImpl.java 427 2011-05-13 09:28:19Z rory.cn $
 */
public class EntryServiceImpl implements EntryService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public Entry saveOrUpdate(Entry entry) {
		if (StringUtils.isNotBlank(entry.getName())) {
			Entry exist = getByName(entry.getBlog().getId(), entry.getName());
			if (exist != null && !StringUtils.equals(exist.getId(), entry.getId())) {
				throw new DuplicateException("entry in blogId[" + entry.getBlog().getId() + "] duplicate with name:" + entry.getName());
			}
		}
		if (entry.getStatus() == null) {
			entry.setStatus(Status.DRAFT);
		}
		if (entry.getPingStatus() == null) {
			entry.setPingStatus(PingStatus.OPEN);
		}
		if (entry.getCommentStatus() == null) {
			entry.setCommentStatus(CommentStatus.OPEN);
		}
		if (entry.getCreateDate() == null) {
			entry.setCreateDate(new Date());
		}
		if (entry.getUpdateDate() == null) {
			entry.setUpdateDate(new Date());
		}
		if (entry.getPublishDate() == null) {
			entry.setPublishDate(new Date());
		}
		DateTimeZone zoneUTC = DateTimeZone.UTC;
		DateTime dateTime = new DateTime(entry.getPublishDate());
		entry.setPublishDateGMT(dateTime.withZone(zoneUTC).toDate());
		dateTime = new DateTime(entry.getUpdateDate());
		entry.setUpdateDateGMT(dateTime.withZone(zoneUTC).toDate());
		dateTime = new DateTime(entry.getCreateDate());
		entry.setCreateDateGMT(dateTime.withZone(zoneUTC).toDate());
		return entityManagerProvider.get().merge(entry);
	}

	/**
	 * {@inheritDoc}
	 */
	public Entry get(String id) {
		return entityManagerProvider.get().find(Entry.class, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Entry getByName(String blogId, String name) {
		Query query = entityManagerProvider.get().createQuery("from Entry entry where entry.blog.id = :blogId and entry.name = :name");
		query.setParameter("blogId", blogId);
		query.setParameter("name", name);
		List<Entry> entries = query.getResultList();
		if (entries.isEmpty()) {
			return null;
		}
		return entries.get(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public PaginationSupport<Entry> search(Condition condition, Range range, Sorter sorter) {
		if (condition == null) {
			condition = new EntryCondition();
		}
		String hql = condition.decorateHql();
		Query countQuery = entityManagerProvider.get().createQuery("select count(*) " + hql);
		condition.setParameters(countQuery);
		
		Query query = entityManagerProvider.get().createQuery(sorter.decorate(hql));
		condition.setParameters(query);
		
		long totalCount = (Long)countQuery.getSingleResult();
		query.setFirstResult(range.getStart());
		query.setMaxResults(range.getSize());
		@SuppressWarnings("unchecked")
		List<Entry> entries = query.getResultList();
		return new PaginationSupport<Entry>(entries, Integer.parseInt(String.valueOf(totalCount)), range.getStart(), range.getSize());
	}

}
