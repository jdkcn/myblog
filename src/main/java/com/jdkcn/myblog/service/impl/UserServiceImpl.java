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
package com.jdkcn.myblog.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.jdkcn.myblog.domain.User;
import com.jdkcn.myblog.exception.DuplicateException;
import com.jdkcn.myblog.service.UserService;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: UserServiceImpl.java 427 2011-05-13 09:28:19Z rory.cn $
 */
public class UserServiceImpl implements UserService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public User saveOrUpdate(User user) {
		User exist = getByUsername(user.getUsername());
		if (exist != null && !StringUtils.equals(user.getId(), exist.getId())) {
			throw new DuplicateException(User.class.getName(), "username", user.getUsername());
		}
		exist = getByEmail(user.getEmail());
		if (exist != null && !StringUtils.equals(user.getId(), exist.getId())) {
			throw new DuplicateException(User.class.getName(), "email", user.getEmail());
		}
		return entityManagerProvider.get().merge(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public User get(String id) {
		return entityManagerProvider.get().find(User.class, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public User getByUsername(String username) {
		Query query = entityManagerProvider.get().createQuery("from myblog.User u where u.username = :username");
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public User getByEmail(String email) {
		Query query = entityManagerProvider.get().createQuery("from myblog.User u where u.email = :email");
		query.setParameter("email", email);
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

}
