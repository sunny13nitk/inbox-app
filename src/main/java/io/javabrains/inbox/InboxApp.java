package io.javabrains.inbox;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import io.javabrains.inbox.model.Email;
import io.javabrains.inbox.model.EmailListItem;
import io.javabrains.inbox.model.EmailListItemKey;
import io.javabrains.inbox.repo.EmailListItemRepo;
import io.javabrains.inbox.repo.EmailRepo;

@SpringBootApplication
@RestController
public class InboxApp
{
	@Autowired
	private EmailListItemRepo emailListRepo;

	@Autowired
	private EmailRepo emailRepo;

	public static void main(String[] args)
	{
		SpringApplication.run(InboxApp.class, args);
	}

	@RequestMapping("/user")
	public String user(@AuthenticationPrincipal OAuth2User principal)
	{
		System.out.println(principal);
		return principal.getAttribute("login");
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties)
	{
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

	@PostConstruct
	public void init()
	{
		// Loading EMails
		List<EmailListItem> emailItems = new ArrayList<EmailListItem>();
		List<Email> emails = new ArrayList<Email>();
		for (int i = 0; i < 10; i++)
		{
			EmailListItemKey emKey = new EmailListItemKey("sunny13nitk", "Inbox", Uuids.timeBased());
			EmailListItem emailListItem = new EmailListItem(emKey, Arrays.asList("sunny13nitk"), "Subject : " + i,
					false, null);
			emailItems.add(emailListItem);

			Email email = new Email();
			email.setId(emKey.getTimeUUID());
			email.setBody("Body " + i);
			email.setFrom("sunny13nitk");
			email.setSubject(emailListItem.getSubject());
			email.setTo(emailListItem.getTo());

			emails.add(email);

		}
		emailListRepo.saveAll(emailItems);
		emailRepo.saveAll(emails);

	}

}
