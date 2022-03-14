package io.javabrains.inbox.model;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(value = "messages_by_user_folder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailListItem
{
	@PrimaryKey
	private EmailListItemKey key;

	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;

	@CassandraType(type = Name.TEXT)
	private String subject;

	@CassandraType(type = Name.BOOLEAN)
	private boolean isRead;

	@Transient
	private String agoTimeString;
}
