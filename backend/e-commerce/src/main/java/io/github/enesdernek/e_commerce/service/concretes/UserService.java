package io.github.enesdernek.e_commerce.service.concretes;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.dto.UserDtoAuthIU;
import io.github.enesdernek.e_commerce.dto.UserDtoIU;
import io.github.enesdernek.e_commerce.exception.UsernameOrPasswordFalseException;
import io.github.enesdernek.e_commerce.jwt.AuthResponse;
import io.github.enesdernek.e_commerce.jwt.JwtService;
import io.github.enesdernek.e_commerce.model.User;
import io.github.enesdernek.e_commerce.repository.UserRepository;
import io.github.enesdernek.e_commerce.service.abstracts.IUserService;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtService jwtService;
	

	public AuthResponse authenticate(UserDtoAuthIU userDtoAuthIU) {
		try {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDtoAuthIU.getUsername(),userDtoAuthIU.getPassword());
			authenticationProvider.authenticate(auth);
			
			User user = userRepository.findByUsername(userDtoAuthIU.getUsername());
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			String token = jwtService.generateToken(user);
			
			return new AuthResponse(userDto,token);
			
		}catch(Exception e) {
			throw new UsernameOrPasswordFalseException("Username or password is incorrect.");
		}
		
	}
	
	
	public UserDto register(UserDtoIU userDtoIU) throws BadRequestException {
		
		List<User>users = this.userRepository.findAll();
		
		for(User user: users) {
			if(user.getUsername().equals(userDtoIU.getUsername())) {
				throw new BadRequestException("This username already exists.");
			}
		}
				
		User user = new User();
			
		user.setUsername(userDtoIU.getUsername());
		
		user.setEmail(userDtoIU.getEmail());
		
		user.setPassword(passwordEncoder.encode(userDtoIU.getPassword()));
		
		User savedUser = userRepository.save(user);
				
		UserDto dtoUser = new UserDto();
		
		BeanUtils.copyProperties(savedUser, dtoUser);
		
		return dtoUser;
		
	}

}
