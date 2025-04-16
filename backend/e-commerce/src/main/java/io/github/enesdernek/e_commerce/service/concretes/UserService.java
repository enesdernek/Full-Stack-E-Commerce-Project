package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.dto.UserDtoAuthIU;
import io.github.enesdernek.e_commerce.dto.UserDtoIU;
import io.github.enesdernek.e_commerce.exception.BadRequestException;
import io.github.enesdernek.e_commerce.exception.UsernameOrPasswordFalseException;
import io.github.enesdernek.e_commerce.jwt.AuthResponse;
import io.github.enesdernek.e_commerce.jwt.JwtService;
import io.github.enesdernek.e_commerce.model.Cart;
import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.model.User;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
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
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	

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
			if (userRepository.existsByUsername(userDtoIU.getUsername())) {
			    throw new BadRequestException("This username already exists.");
			}
		}
				
		User user = new User();
			
		user.setUsername(userDtoIU.getUsername());
		
		user.setEmail(userDtoIU.getEmail());
		
		user.setPassword(passwordEncoder.encode(userDtoIU.getPassword()));
		
		Cart cart = new Cart();
	    cart.setTotalPrice(BigDecimal.ZERO); 
	    cart.setCartItems(new ArrayList<>()); 

	    cart.setUser(user); 	    
	    user.setCart(cart); 
		
		User savedUser = userRepository.save(user);
				
		UserDto dtoUser = new UserDto();
		
		BeanUtils.copyProperties(savedUser, dtoUser);
		
		return dtoUser;
		
	}


	@Override
	public UserDto deleteByUserId(Long userId) {
		User deletedUser = this.userRepository.findById(userId).get();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(deletedUser, userDto);
		this.userRepository.deleteById(userId);
		return userDto;
	}


	@Override
	public void addProductToFavoriteListByUserIdAndProductId(Long userId, Long productId) {
		
		User user = this.userRepository.findById(userId).get();
		
		Product product = this.productRepository.findById(productId).get();
		
		List<Product>favoritedProducts = user.getFavoritedProducts();
		
		if (favoritedProducts.stream().anyMatch(item -> item.getProductId().equals(productId))) {
	        throw new BadRequestException("This product is already in the favorited products list.");
	    }
		
		favoritedProducts.add(product);
		
		user.setFavoritedProducts(favoritedProducts);
		
		this.userRepository.save(user);
		
	}
	
	@Override
	public void deleteProductFromFavoriteListByUserIdAndProductId(Long userId, Long productId) {
		
		User user = this.userRepository.findById(userId).get();
		
		Product product = this.productRepository.findById(productId).get();
		
		List<Product>favoritedProducts = user.getFavoritedProducts();
		
		boolean productFound = favoritedProducts.removeIf(item -> item.getProductId().equals(productId));
	    
	    if (!productFound) {
	        throw new BadRequestException("This product is not in the favorited products list.");
	    }
		
		favoritedProducts.remove(product);
		
		user.setFavoritedProducts(favoritedProducts);
		
		this.userRepository.save(user);
		
	}


	@Override
	public List<ProductDto> getFavoritedProductsListByUserId(Long userId) {
		
		User user = this.userRepository.findById(userId).get();
		
		List<Product>favoritedProducts = user.getFavoritedProducts();
		
		List<ProductDto> productDtos = new ArrayList<ProductDto>();
				
		for(Product product : favoritedProducts) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(product.getCategory(), categoryDto);
			productDto.setCategoryDto(categoryDto);
			
			productDtos.add(productDto);
		}
		
		return productDtos;
	}

}
