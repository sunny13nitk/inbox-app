package io.javabrains.inbox.srv.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import io.javabrains.inbox.model.EmailListItem;
import io.javabrains.inbox.repo.EmailListItemRepo;
import io.javabrains.inbox.srv.intf.IMessagesSrv;

@Service
public class MessagesSrv implements IMessagesSrv
{

	@Autowired
	private EmailListItemRepo repoMsgs;

	@Override
	public List<EmailListItem> getFormattedMessagesbyUserAndFolder(String userId, String folder)
	{
		List<EmailListItem> messages = null;

		if (StringUtils.hasText(userId) && StringUtils.hasText(folder) && repoMsgs != null)
		{
			messages = repoMsgs.findAllByKey_UseridAndKey_Label(userId, folder);
			if (messages != null)
			{
				PrettyTime pt = new PrettyTime();
				for (EmailListItem emailListItem : messages)
				{
					UUID timeUuid = emailListItem.getKey().getTimeUUID();
					Date emailDateTime = new Date(Uuids.unixTimestamp(timeUuid));
					emailListItem.setAgoTimeString(pt.format(emailDateTime));
				}
			}
		}

		return messages;
	}

}
