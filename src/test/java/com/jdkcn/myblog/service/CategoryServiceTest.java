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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.jdkcn.myblog.domain.Category;
import com.jdkcn.myblog.domain.Category.Type;
import com.jdkcn.myblog.exception.DuplicateException;

public class CategoryServiceTest extends AbstractServiceTest {
	
	private CategoryService categoryService;

	@Override
	public void before() {
		super.before();
		categoryService = injector.getInstance(CategoryService.class);
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		Category category1 = new Category();
		category1.setType(Type.ENTRY);
		category1.setName("Programming");
		category1 = categoryService.saveOrUpdate(category1);

		Category category2 = new Category();
		category2.setName("Java");
		category2.setType(Type.ENTRY);
		category2.setParent(category1);

		categoryService.saveOrUpdate(category2);

		Category javaCategory = categoryService.getByName("Java", category1.getId());

		assertEquals("Java", javaCategory.getName());
		assertEquals("Programming", javaCategory.getParent().getName());

		assertNotNull(javaCategory.getParent());

		assertEquals("Programming", categoryService.getByName("Programming", null).getName());

		Category proCate = categoryService.get(category1.getId());
		assertEquals(1, proCate.getChildren().size());
	}

	@Test
	public void testCategoryOrder() throws Exception {
		Category category1 = new Category();
		category1.setType(Type.ENTRY);
		category1.setName("Programming");
		category1 = categoryService.saveOrUpdate(category1);

		Category category2 = new Category();
		category2.setName("Java");
		category2.setType(Type.ENTRY);
		category2.setParent(category1);
		categoryService.saveOrUpdate(category2);

		Category category3 = new Category();
		category3.setName("Python");
		category3.setType(Type.ENTRY);
		category3.setParent(category1);
		categoryService.saveOrUpdate(category3);

		assertEquals(1, category2.getOrder().intValue());
		assertEquals(2, category3.getOrder().intValue());
	}

	@Test
	public void testChildren() throws Exception {
		Category category1 = new Category();
		category1.setType(Type.ENTRY);
		category1.setName("Programming");
		category1 = categoryService.saveOrUpdate(category1);

		Category category2 = new Category();
		category2.setName("Java");
		category2.setType(Type.ENTRY);
		category2.setParent(category1);
		categoryService.saveOrUpdate(category2);

		Category category3 = new Category();
		category3.setName("Python");
		category3.setType(Type.ENTRY);
		category3.setParent(category1);
		categoryService.saveOrUpdate(category3);

		Category programmingCategory = categoryService.getByName("Programming", null);
		assertNotNull(programmingCategory);
		assertEquals(2, programmingCategory.getChildren().size());
	}
	
	@Test(expected = DuplicateException.class)
	public void testDuplicate() throws Exception {
		Category category1 = new Category();
		category1.setType(Type.ENTRY);
		category1.setName("Programming");
		category1 = categoryService.saveOrUpdate(category1);

		Category category2 = new Category();
		category2.setName("Java");
		category2.setType(Type.ENTRY);
		category2.setParent(category1);
		categoryService.saveOrUpdate(category2);

		Category category3 = new Category();
		category3.setName("Java");
		category3.setType(Type.ENTRY);
		category3.setParent(category1);
		categoryService.saveOrUpdate(category3);
		
	}
	
	@Test
	public void testNotDuplicate() throws Exception {
		Category category1 = new Category();
		category1.setType(Type.ENTRY);
		category1.setName("Programming");
		category1 = categoryService.saveOrUpdate(category1);
		
		Category category2 = new Category();
		category2.setName("Java");
		category2.setType(Type.ENTRY);
		category2.setParent(category1);
		categoryService.saveOrUpdate(category2);
		
		Category category3 = new Category();
		category3.setName("Programming");
		category3.setType(Type.ENTRY);
		category3.setParent(category1);
		category3 = categoryService.saveOrUpdate(category3);
		
		String categoryId = category3.getId();
		Category updateCategory = categoryService.get(categoryId);
		updateCategory.setDescription("New Description");
		categoryService.saveOrUpdate(updateCategory);
	}
	
	
	
	@Test(expected = DuplicateException.class)
	public void testDuplicate2() throws Exception {
		Category category1 = new Category();
		category1.setType(Type.ENTRY);
		category1.setName("Programming");
		category1 = categoryService.saveOrUpdate(category1);
		
		Category category2 = new Category();
		category2.setType(Type.ENTRY);
		category2.setName("Programming");
		category2 = categoryService.saveOrUpdate(category2);
		
	}

}
