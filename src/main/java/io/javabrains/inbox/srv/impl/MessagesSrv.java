package io.javabrains.inbox.srv.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import io.javabrains.inbox.model.Email;
import io.javabrains.inbox.model.EmailListItem;
import io.javabrains.inbox.pojo.EmailUIRead;
import io.javabrains.inbox.repo.EmailListItemRepo;
import io.javabrains.inbox.repo.EmailRepo;
import io.javabrains.inbox.srv.intf.IMessagesSrv;

@Service
public class MessagesSrv implements IMessagesSrv
{

	@Autowired
	private EmailListItemRepo repoEmailList;

	@Autowired
	private EmailRepo repoEmail;

	@Override
	public List<EmailListItem> getFormattedMessagesbyUserAndFolder(String userId, String folder)
	{
		List<EmailListItem> messages = null;

		if (StringUtils.hasText(userId) && StringUtils.hasText(folder) && repoEmailList != null)
		{
			messages = repoEmailList.findAllByKey_UseridAndKey_Label(userId, folder);
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

	@Override
	public EmailUIRead getEmailDetailsbyID(UUID id)
	{
		EmailUIRead detailEmail = null;

		if (id != null && repoEmail != null)
		{
			Optional<Email> emailO = repoEmail.findById(id);
			if (emailO.isPresent())
			{
				Email email = emailO.get();
				detailEmail = new EmailUIRead(email.getId(), email.getSubject(), email.getFrom(),
						String.join(";", email.getTo()), email.getBody());
			}
		}

		return detailEmail;
	}

}
