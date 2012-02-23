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
package com.jdkcn.myblog.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory, Ye</a>
 * @since May 12, 2010 2:50:11 PM
 * @version $Id: StringListType.java 405 2011-04-29 04:55:25Z rory.cn $
 * @deprecated use {@link ElementCollection} instead
 */
@Deprecated
public class StringListType implements UserType {

    private static final String SPLITTER = "$$$$$";

    private static final int[] TYPES = new int[] { Types.LONGVARCHAR };

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable,
     *      java.lang.Object)
     */
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        List sourceList = (List) value;
        List targetList = new ArrayList();
        targetList.addAll(sourceList);
        return targetList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
     */
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object,
     *      java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
	public boolean equals(Object one, Object another) throws HibernateException {
        if (one == another)
            return true;
        if (one != null && another != null) {
            List a = (List) one;
            List b = (List) another;
            if (a.size() != b.size())
                return false;
            for (int i = 0; i < a.size(); i++) {
                String str1 = (String) a.get(i);
                String str2 = (String) b.get(i);
                if (!str1.equals(str2))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable() {
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet,
     *      java.lang.String[], java.lang.Object)
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String value = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
        if (value != null) {
            return parse(value);
        } else {
            return null;
        }
    }

    private Object parse(String value) {
        String[] strs = StringUtils.split(value, SPLITTER);
        List<String> quotes = new ArrayList<String>();
        for (int i = 0; i < strs.length; i++) {
            quotes.add(strs[i]);
        }
        return quotes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement,
     *      java.lang.Object, int)
     */
    @SuppressWarnings({ "rawtypes"})
	public void nullSafeSet(PreparedStatement pstmt, Object value, int index) throws HibernateException, SQLException {
        if (value != null) {
            String str = disassemble((List) value);
            StandardBasicTypes.STRING.nullSafeSet(pstmt, str, index);
        } else {
        	StandardBasicTypes.STRING.nullSafeSet(pstmt, value, index, null);
        }
    }

    public String disassemble(@SuppressWarnings("rawtypes") List values) throws HibernateException {
        if (values.isEmpty())
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < values.size() - 1; i++) {
            sb.append(values.get(i)).append(SPLITTER);
        }
        sb.append(values.get(values.size() - 1));
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    @SuppressWarnings("rawtypes")
	public Class returnedClass() {
        return List.class;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    public int[] sqlTypes() {
        return TYPES;
    }

}
