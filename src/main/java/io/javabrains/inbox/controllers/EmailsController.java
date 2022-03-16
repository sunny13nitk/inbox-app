package io.javabrains.inbox.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.javabrains.inbox.EnumDefaultFolderNames;
import io.javabrains.inbox.UI.pojo.UserMsgContainer;
import io.javabrains.inbox.repo.FolderByUserRepo;
import io.javabrains.inbox.srv.intf.IFoldersByUserSrv;
import io.javabrains.inbox.srv.intf.IMessagesSrv;

@Controller
@RequestMapping("/emails")
public class EmailsController
{
	@Autowired
	private FolderByUserRepo repoFolderByUser;

	@Autowired
	private IMessagesSrv msgSrv;

	@Autowired
	private IFoldersByUserSrv folderSrv;

	@GetMapping("/{id}")
	public String showEmailDetails(@PathVariable UUID id, @AuthenticationPrincipal OAuth2User principal, Model model)
	{
		String viewName = "inbox-page";

		if (principal == null || (!StringUtils.hasText(principal.getAttribute("login"))))
			viewName = "index";
		else
		{
			if (id != null)
			{
				viewName = "email-page";
				UserMsgContainer usrMsgsCont = new UserMsgContainer();
				usrMsgsCont.setUserName(principal.getAttribute("login"));

				// Load Folders
				usrMsgsCont.setUserFolders(repoFolderByUser.findAllByUserid(usrMsgsCont.getUserName()));
				usrMsgsCont.setDefaultFolders(folderSrv.getDefaultFoldersforUser(usrMsgsCont.getUserName()));

				// Load Default Folder - Inbox Messages
				usrMsgsCont.getCurrEmailItems().clear();
				usrMsgsCont.setCurrEmailItems(msgSrv.getFormattedMessagesbyUserAndFolder(usrMsgsCont.getUserName(),
						EnumDefaultFolderNames.Inbox.name()));

				// Get The Email Details by ID
				model.addAttribute("email", msgSrv.getEmailDetailsbyID(id));
				// Set in Model
				model.addAttribute("usrData", usrMsgsCont);
			}
		}

		return viewName;
	}

}
