package io.github.enesdernek.e_commerce.jwt;

import io.github.enesdernek.e_commerce.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private UserDto userDto;
	
	private String token;
}
