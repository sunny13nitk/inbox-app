package io.javabrains.inbox.srv.intf;

import java.util.List;
import java.util.UUID;

import io.javabrains.inbox.model.EmailListItem;
import io.javabrains.inbox.pojo.EmailUIRead;

public interface IMessagesSrv
{
	public List<EmailListItem> getFormattedMessagesbyUserAndFolder(String userId, String Folder);

	public EmailUIRead getEmailDetailsbyID(UUID id);
}
