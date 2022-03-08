package io.javabrains.inbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import io.javabrains.inbox.UI.pojo.UserMsgContainer;
import io.javabrains.inbox.repo.FolderByUserRepo;
import io.javabrains.inbox.srv.intf.IFoldersByUserSrv;

@Controller
public class InboxController
{
	@Autowired
	private FolderByUserRepo repoFolderByUser;

	@Autowired
	private IFoldersByUserSrv folderSrv;

	@GetMapping("/")
	public String showInbox(@AuthenticationPrincipal OAuth2User principal, Model model)
	{
		if (principal == null || (!StringUtils.hasText(principal.getAttribute("login"))))
			return "index";
		else
		{
			UserMsgContainer usrMsgsCont = new UserMsgContainer();
			usrMsgsCont.setUserName(principal.getAttribute("login"));
			usrMsgsCont.setUserFolders(repoFolderByUser.findAllByUserid(usrMsgsCont.getUserName()));
			usrMsgsCont.setDefaultFolders(folderSrv.getDefaultFoldersforUser(usrMsgsCont.getUserName()));
			model.addAttribute("usrData", usrMsgsCont);

			return "inbox-page";
		}

	}
}
