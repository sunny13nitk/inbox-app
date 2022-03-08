package io.javabrains.inbox.UI.pojo;

import java.util.ArrayList;
import java.util.List;

import io.javabrains.inbox.model.FolderByUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMsgContainer
{
	private String userName;
	private List<FolderByUser> userFolders = new ArrayList<FolderByUser>();

}
