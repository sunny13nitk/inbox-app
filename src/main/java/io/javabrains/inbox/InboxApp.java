package io.javabrains.inbox;

import java.nio.file.Path;

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

import io.javabrains.inbox.model.FolderByUser;
import io.javabrains.inbox.repo.FolderByUserRepo;

@SpringBootApplication
@RestController
public class InboxApp
{

	@Autowired
	private FolderByUserRepo repoFolByUser;

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
	public void loadData()
	{
		repoFolByUser.save(new FolderByUser("sunny13nitk", "Inbox", "blue"));
		repoFolByUser.save(new FolderByUser("sunny13nitk", "Sent", "green"));
		repoFolByUser.save(new FolderByUser("sunny13nitk", "Important", "yellow"));
	}

}
