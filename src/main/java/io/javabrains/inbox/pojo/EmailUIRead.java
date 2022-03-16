package io.javabrains.inbox.pojo;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailUIRead
{
	private UUID id;

	private String subject;

	private String from;

	private String to;

	private String body;
}
