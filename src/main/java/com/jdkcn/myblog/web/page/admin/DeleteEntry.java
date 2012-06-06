package com.jdkcn.myblog.web.page.admin;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import com.jdkcn.myblog.domain.Entry;
import com.jdkcn.myblog.service.EntryService;
import com.jdkcn.myblog.util.FlashMap;

/**
 * 
 * @author <a href="mailto:rory.cn@gmail.com">Rory</a>
 *
 */
@At("/adm/entry/delete/:id")
@Service
public class DeleteEntry extends AdminLayout {

	@Inject
	private EntryService entryService;
	
	@Get
	@Post
	public Reply<?> delete(@Named("id") String id) {
		Entry entry = entryService.get(id);
		if (entry == null) {
			FlashMap.setErrorMessage("Entry not found");
		}
		entry.setStatus(Entry.Status.DELETED);
		entryService.saveOrUpdate(entry);
		return Reply.saying().redirect(request.getContextPath() + "/adm/entries");
	}
	
}
