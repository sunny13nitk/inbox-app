package io.javabrains.inbox.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.javabrains.inbox.UI.pojo.UserMsgContainer;
import io.javabrains.inbox.pojo.EmailNew;
import io.javabrains.inbox.repo.EmailListItemRepo;
import io.javabrains.inbox.repo.EmailRepo;
import io.javabrains.inbox.repo.FolderByUserRepo;
import io.javabrains.inbox.srv.intf.IFoldersByUserSrv;
import io.javabrains.inbox.srv.intf.IMessagesSrv;

@Controller
@RequestMapping("/compose")
public class ComposeController
{

	@Autowired
	private FolderByUserRepo repoFolderByUser;

	@Autowired
	private EmailListItemRepo repoMsgs;

	@Autowired
	private IMessagesSrv msgSrv;

	@Autowired
	private EmailRepo repoEmail;

	@Autowired
	private IFoldersByUserSrv folderSrv;

	@GetMapping("/new")
	public String showComposeEmail(@AuthenticationPrincipal OAuth2User principal, Model model)
	{

		if (principal == null || (!StringUtils.hasText(principal.getAttribute("login"))))
			return "index";
		else
		{
			UserMsgContainer usrMsgsCont = new UserMsgContainer();
			usrMsgsCont.setUserName(principal.getAttribute("login"));

			// Load Folders
			usrMsgsCont.setUserFolders(repoFolderByUser.findAllByUserid(usrMsgsCont.getUserName()));
			usrMsgsCont.setDefaultFolders(folderSrv.getDefaultFoldersforUser(usrMsgsCont.getUserName()));

			// Build EMail POJO
			EmailNew message = new EmailNew();
			// message.setId(Uuids.timeBased()); //At Mail Send time
			message.setFrom(usrMsgsCont.getUserName());

			// Set in Model
			model.addAttribute("usrData", usrMsgsCont);
			model.addAttribute("message", message);

			return "compose-page";

		}
	}

	@PostMapping("/sendEmail")
	public String sendMessage(@Valid @ModelAttribute EmailNew message, BindingResult bR)
	{
		if (!bR.hasErrors())
		{
			if (message != null && repoEmail != null)
			{
				if (!StringUtils.hasText(message.getSubject()))
				{

				}

			}
		}
		return "redirect:/";
	}
}
