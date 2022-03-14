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

import io.javabrains.inbox.model.EmailListItem;
import io.javabrains.inbox.model.EmailListItemKey;
import io.javabrains.inbox.repo.EmailListItemRepo;

@SpringBootApplication
@RestController
public class InboxApp
{
	@Autowired
	private EmailListItemRepo emailListRepo;

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
		for (int i = 0; i < 10; i++)
		{
			EmailListItemKey emKey = new EmailListItemKey("sunny13nitk", "Inbox", Uuids.timeBased());
			EmailListItem emailListItem = new EmailListItem(emKey, Arrays.asList("sunny13nitk"), "Subject : " + i,
					false, null);
			emailItems.add(emailListItem);

		}
		emailListRepo.saveAll(emailItems);
	}

}
