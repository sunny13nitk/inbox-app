package io.javabrains.inbox.srv.intf;

import java.util.List;

import io.javabrains.inbox.model.FolderByUser;

public interface IFoldersByUserSrv
{
	public List<FolderByUser> getDefaultFoldersforUser(String userId);
}
