package io.github.enesdernek.e_commerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoIU {
	
	@NotNull
	@NotBlank
	@Size(min=3,max=18)
	private String username;
	
	@NotNull
	@NotBlank
	@Email
	private String email;
    
	@NotNull
	@NotBlank
	@Size(min=8,max=128)
	private String password;
	
	
}
