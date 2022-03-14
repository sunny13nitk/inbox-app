package io.javabrains.inbox.srv.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.javabrains.inbox.EnumDefaultFolderNames;
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
			defaultFolders = Arrays.asList(new FolderByUser(userId, EnumDefaultFolderNames.Inbox.name(), "blue"),
					new FolderByUser(userId, EnumDefaultFolderNames.Sent.name(), "green"),
					new FolderByUser(userId, EnumDefaultFolderNames.Important.name(), "red")

			);
		}

		return defaultFolders;
	}

}
