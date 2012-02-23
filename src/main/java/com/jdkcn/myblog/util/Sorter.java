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
package com.jdkcn.myblog.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 * @version $Id: Sorter.java 440 2011-05-23 15:53:05Z Rory.CN@gmail.com $
 */
public class Sorter implements Serializable {

	private static final long serialVersionUID = 757552624922780352L;

	/**
	 * sorter type
	 */
	private static enum Type {

		/**
		 * ascending
		 */
		ASCENDING,

		/**
		 * descending
		 */
		DESCENDING
	}

	/**
	 * The sort object for each property sort.
	 */
	public static class Sort implements Serializable {

		private static final long serialVersionUID = -1599386645244047497L;

		public String property;

		public Type type;

		/**
		 * @param property
		 *            sort property
		 * @param type
		 *            the sort type
		 */
		private Sort(String property, Type type) {
			this.property = property;
			this.type = type;
		}

		public boolean isAscending() {
			return type == Type.ASCENDING;
		}

		@Override
		public String toString() {
			return property + " " + (type == Type.ASCENDING ? "asc" : "desc");
		}
	}

	private List<Sort> sorts = new ArrayList<Sort>();

	public List<Sort> getSorts() {
		return sorts;
	}

	/**
	 * ascending
	 * 
	 * @param property
	 *            sort property
	 * @return this
	 */
	public Sorter asc(String property) {
		for (Sort sort : sorts) {
			if (StringUtils.equals(property, sort.property)) {
				sorts.remove(sort);
				break;
			}
		}
		sorts.add(new Sort(property, Type.ASCENDING));
		return this;
	}

	/**
	 * descending
	 * 
	 * @param property
	 *            sort property
	 * @return this
	 */
	public Sorter desc(String property) {
		for (Sort sort : sorts) {
			if (StringUtils.equals(property, sort.property)) {
				sorts.remove(sort);
				break;
			}
		}
		sorts.add(new Sort(property, Type.DESCENDING));
		return this;
	}

	/**
	 * add order in hql
	 * 
	 * @param hql
	 *            hql
	 * @return decorated hql
	 */
	public String decorate(String hql) {

		if (sorts.isEmpty()) {
			return hql;
		}

		StringBuilder queryBuilder = new StringBuilder(hql);
		if (StringUtils.containsIgnoreCase(hql, "order by")) {
			for (Sort sort : sorts) {
				queryBuilder.append(",").append(sort.toString());
			}
		} else {
			queryBuilder.append(" order by ");
			for (Sort sort : sorts) {
				queryBuilder.append(sort.toString()).append(",");
			}
			queryBuilder.deleteCharAt(queryBuilder.length() - 1);
		}
		return queryBuilder.toString();
	}
}
