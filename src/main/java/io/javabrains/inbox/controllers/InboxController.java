package io.javabrains.inbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.javabrains.inbox.EnumDefaultFolderNames;
import io.javabrains.inbox.UI.pojo.UserMsgContainer;
import io.javabrains.inbox.repo.EmailListItemRepo;
import io.javabrains.inbox.repo.FolderByUserRepo;
import io.javabrains.inbox.srv.intf.IFoldersByUserSrv;
import io.javabrains.inbox.srv.intf.IMessagesSrv;

@Controller
public class InboxController
{
	@Autowired
	private FolderByUserRepo repoFolderByUser;

	@Autowired
	private EmailListItemRepo repoMsgs;

	@Autowired
	private IMessagesSrv msgSrv;

	@Autowired
	private IFoldersByUserSrv folderSrv;

	@GetMapping("/")
	public String showInbox(@RequestParam(required = false) String folder,
			@AuthenticationPrincipal OAuth2User principal, Model model)
	{
		String folderName;
		if (principal == null || (!StringUtils.hasText(principal.getAttribute("login"))))
			return "index";
		else
		{
			UserMsgContainer usrMsgsCont = new UserMsgContainer();
			usrMsgsCont.setUserName(principal.getAttribute("login"));

			// Load Folders
			usrMsgsCont.setUserFolders(repoFolderByUser.findAllByUserid(usrMsgsCont.getUserName()));
			usrMsgsCont.setDefaultFolders(folderSrv.getDefaultFoldersforUser(usrMsgsCont.getUserName()));

			// Load Default Folder - Inbox Messages
			usrMsgsCont.getCurrEmailItems().clear();
			if (StringUtils.hasText(folder))
			{
				usrMsgsCont.setCurrEmailItems(
						msgSrv.getFormattedMessagesbyUserAndFolder(usrMsgsCont.getUserName(), folder));
				folderName = folder;
			} else
			{
				usrMsgsCont.setCurrEmailItems(msgSrv.getFormattedMessagesbyUserAndFolder(usrMsgsCont.getUserName(),
						EnumDefaultFolderNames.Inbox.name()));
				folderName = EnumDefaultFolderNames.Inbox.toString();
			}

			// Set in Model
			model.addAttribute("usrData", usrMsgsCont);
			model.addAttribute("folderName", folderName);

			return "inbox-page";
		}

	}
}
