package io.javabrains.inbox.srv.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.javabrains.inbox.model.FolderByUser;
import io.javabrains.inbox.srv.intf.IFoldersByUserSrv;

@Service
public class FoldersByUserSrv implements IFoldersByUserSrv
{

	@Override
	public List<FolderByUser> getDefaultFoldersforUser(String userId)
	{
		List<FolderByUser> defaultFolders = null;
		if (StringUtils.hasText(userId))
		{
			defaultFolders = Arrays.asList(new FolderByUser(userId, "Inbox", "blue"),
					new FolderByUser(userId, "Sent Items", "green"), new FolderByUser(userId, "Important", "red")

			);
		}

		return defaultFolders;
	}

}
