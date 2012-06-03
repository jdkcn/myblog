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

import java.util.List;

import com.jdkcn.myblog.domain.Category;
import com.jdkcn.myblog.exception.DuplicateException;

import static com.jdkcn.myblog.domain.Category.Type;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory, Ye</a>
 * @since May 10, 2010 11:01:40 AM
 * @version $Id: CategoryService.java 407 2011-04-29 16:30:38Z Rory.CN@gmail.com $
 */
public interface CategoryService {

	/**
	 * Get category roots by {@link Type} type.
	 * @param type The {@link Type}
	 * @return Root categories
	 */
    List<Category> getRoots(Type type);
    
    /**
     * Get category by name and parentId, assume all children category's name is unique.
     * @param name category name
     * @param parentId parent's Id
     * @return Category with the name or null if not found.
     */
    Category getByName(String name, String parentId);

    /**
     * save or update the category.
     * @param category category to save or update
     * @return the persisted category
     * @throws DuplicateException if the category's parent already has a child with this category's name
     */
    Category saveOrUpdate(Category category);

    /**
     * Get category by id.
     * @param id the Id
     * @return category with this id or null if not found
     */
    Category get(String id);

    /**
     * Get the max order number under the {@code parentId}
     * @param parentId the parent's Id
     * @return the max order in parent's children
     */
    Integer getMaxOrder(String parentId);
}
