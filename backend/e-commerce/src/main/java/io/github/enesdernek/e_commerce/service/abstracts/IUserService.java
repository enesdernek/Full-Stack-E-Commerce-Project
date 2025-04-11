package io.github.enesdernek.e_commerce.service.abstracts;

import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.dto.UserDtoIU;
import io.github.enesdernek.e_commerce.jwt.AuthResponse;

public interface IUserService {
	
	AuthResponse authenticate(UserDtoIU userDtoIU);

	UserDto register(UserDtoIU userDtoIU);
}
