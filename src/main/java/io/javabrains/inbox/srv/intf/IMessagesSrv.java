package io.javabrains.inbox.srv.intf;

import java.util.List;

import io.javabrains.inbox.model.EmailListItem;

public interface IMessagesSrv
{
	public List<EmailListItem> getFormattedMessagesbyUserAndFolder(String userId, String Folder);
}
