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
package com.jdkcn.myblog.domain;

import static com.jdkcn.myblog.Constants.PREFIX;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author <a href="mailto:rory.cn@gmail.com">Rory, Ye</a>
 * @since May 1, 2010 10:51:04 PM
 * @version $Id: Entry.java 419 2011-05-09 15:45:42Z Rory.CN@gmail.com $
 */
@Entity(name = PREFIX + ".Entry")
@Table(name = BaseDomain.TABLE_PREFIX + "entry")
public class Entry extends BaseDomain {

    private static final long serialVersionUID = -2803962521862241671L;

    private String title;

    private String content;

    private String excerpt;

    private String password;

    private String name;

    private Set<Category> categories = new LinkedHashSet<Category>();

    private Type type;

    private User author;

    private Blog blog;

    private Integer commentCount;

    private List<String> pingUrls;

    private List<String> pingedUrls;
    
    private Date createDate;

    private Date createDateGMT;
    
    private Date publishDate;

    private Date publishDateGMT;

    private Date updateDate;

    private Date updateDateGMT;

    private Status status;

    private CommentStatus commentStatus;

    private PingStatus pingStatus;

    @Enumerated
    @Column(name = "comment_status", length = 20, nullable = false)
    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    @Enumerated
    @Column(name = "ping_status", length = 20, nullable = false)
    public PingStatus getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(PingStatus pingStatus) {
        this.pingStatus = pingStatus;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date_gmt", nullable = false)
	public Date getCreateDateGMT() {
		return createDateGMT;
	}

	public void setCreateDateGMT(Date createDateGMT) {
		this.createDateGMT = createDateGMT;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_date_gmt")
    public Date getPublishDateGMT() {
        return publishDateGMT;
    }

    public void setPublishDateGMT(Date publishDateGMT) {
        this.publishDateGMT = publishDateGMT;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date_gmt", nullable = false)
    public Date getUpdateDateGMT() {
        return updateDateGMT;
    }

    public void setUpdateDateGMT(Date updateDateGMT) {
        this.updateDateGMT = updateDateGMT;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_date")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", nullable = false)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "comment_count")
    public Integer getCommentCount() {
        return commentCount;
    }

    @Column(name = "name", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
    	name = BaseDomain.TABLE_PREFIX + "entry_pingurl",
    	joinColumns = @JoinColumn(name = "entry_id")
    )
    @Column(name = "pingurl")
    public List<String> getPingUrls() {
		return pingUrls;
	}

	public void setPingUrls(List<String> pingUrls) {
		this.pingUrls = pingUrls;
	}

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
			name = BaseDomain.TABLE_PREFIX + "entry_pingedurl",
			joinColumns = @JoinColumn(name = "entry_id")
	)
	@Column(name = "pingedurl")
	public List<String> getPingedUrls() {
		return pingedUrls;
	}

	public void setPingedUrls(List<String> pingedUrls) {
		this.pingedUrls = pingedUrls;
	}


    @Column(name = "password", length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(name = "title", nullable = false, length = 1024)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content", nullable = false)
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TextType")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToMany
    @JoinTable(name = BaseDomain.TABLE_PREFIX + "entry_category", joinColumns = @JoinColumn(name = "entry_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "excerpt", nullable = false)
    @org.hibernate.annotations.Type(type = "org.hibernate.type.TextType")
    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    
    public enum Status {
        DRAFT("draft"), PUBLISH("publish");

        private String code;

        private Status(String code) {
            this.code = code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return this.code;
        }
    }
    
    public enum Type {
        ENTRY("entry"), PAGE("page");

        private String code;

        private Type(String code) {
            this.code = code;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.code;
        }
    }
    
    public enum CommentStatus {
    	
        OPEN("open"), CLOSED("closed");

        private String code;

        private CommentStatus(String code) {
            this.code = code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return this.code;
        }
    }
    
    public enum PingStatus {

        OPEN("open"), CLOSED("closed");

        private String code;

        private PingStatus(String code) {
            this.code = code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return this.code;
        }
    }
}
