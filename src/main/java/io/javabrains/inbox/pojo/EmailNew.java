package io.javabrains.inbox.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailNew
{
	@NotBlank
	private String subject;

	@NotBlank
	@Size(min = 5)
	private String from;

	@NotBlank
	private String to;

	@NotBlank
	private String body;

}
