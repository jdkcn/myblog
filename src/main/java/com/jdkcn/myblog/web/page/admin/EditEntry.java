package com.jdkcn.myblog.web.page.admin;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import com.google.sitebricks.rendering.Decorated;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.service.EntryService;

/**
 * 
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 *
 */
@Show("/WEB-INF/templates/adm/editentry.html")
@Decorated
@At("/adm/entry/:id")
public class EditEntry extends AdminLayout {

	@Inject
	private EntryService entryService;
	
	private Entry entry;

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	
	@Get
	public void get(@Named("id") String id) {
		this.entry = entryService.get(id);
	}
	
	@Post
	public String save() {
		if (StringUtils.isBlank(entry.getExcerpt())) {
			entry.setExcerpt(entry.getContent());
		}
		entryService.saveOrUpdate(entry);
		return "/adm/entries";
	}
	
}
