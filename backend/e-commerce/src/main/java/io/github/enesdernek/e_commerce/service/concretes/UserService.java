package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import io.github.enesdernek.e_commerce.model.Order;
import io.github.enesdernek.e_commerce.model.OrderItem;
import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.model.ProductRating;
import io.github.enesdernek.e_commerce.model.User;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.repository.OrderRepository;
import io.github.enesdernek.e_commerce.repository.ProductRatingRepository;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
import io.github.enesdernek.e_commerce.repository.UserRepository;
import io.github.enesdernek.e_commerce.service.abstracts.IUserService;

@Service
public class UserService implements IUserService {

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

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRatingRepository productRatingRepository;

	public Long getAuthenticatedUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof String) {
				String username = (String) principal;

				return this.userRepository.findByUsername(username).getUserId();
			}
		}
		return null;
	}

	public AuthResponse authenticate(UserDtoAuthIU userDtoAuthIU) {
		try {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDtoAuthIU.getUsername(), userDtoAuthIU.getPassword());
			authenticationProvider.authenticate(auth);

			User user = userRepository.findByUsername(userDtoAuthIU.getUsername());
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			String token = jwtService.generateToken(user);

			return new AuthResponse(userDto, token);

		} catch (Exception e) {
			throw new UsernameOrPasswordFalseException("Username or password is incorrect.");
		}

	}

	public UserDto register(UserDtoIU userDtoIU) throws BadRequestException {

		List<User> users = this.userRepository.findAll();

		for (User user : users) {
			if (userRepository.existsByUsername(userDtoIU.getUsername())) {
				throw new BadRequestException("This username already exists.");
			}
		}

		User user = new User();

		user.setUsername(userDtoIU.getUsername());

		user.setEmail(userDtoIU.getEmail());

		user.setPassword(passwordEncoder.encode(userDtoIU.getPassword()));

		Cart cart = new Cart();
		cart.setCartItems(new ArrayList<>());

		cart.setUser(user);
		user.setCart(cart);

		User savedUser = userRepository.save(user);

		UserDto dtoUser = new UserDto();

		BeanUtils.copyProperties(savedUser, dtoUser);

		return dtoUser;

	}

	@Override
	public UserDto deleteByUsername(String username) {
		User deletedUser = this.userRepository.findByUsername(username);
		Long userId = deletedUser.getUserId();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(deletedUser, userDto);
		this.userRepository.deleteById(userId);
		return userDto;
	}

	@Override
	public void addProductToFavoriteListByUsernameAndProductId(String username, Long productId) {

		User user = this.userRepository.findByUsername(username);

		Product product = this.productRepository.findById(productId).get();

		List<Product> favoritedProducts = user.getFavoritedProducts();

		if (favoritedProducts.stream().anyMatch(item -> item.getProductId().equals(productId))) {
			throw new BadRequestException("This product is already in the favorited products list.");
		}

		favoritedProducts.add(product);

		product.setFavCount(product.getFavCount() + 1);
		this.productRepository.save(product);

		user.setFavoritedProducts(favoritedProducts);

		this.userRepository.save(user);

	}

	@Override
	public void deleteProductFromFavoriteListByUsernameAndProductId(String username, Long productId) {

		User user = this.userRepository.findByUsername(username);

		Product product = this.productRepository.findById(productId).get();

		List<Product> favoritedProducts = user.getFavoritedProducts();

		boolean productFound = favoritedProducts.removeIf(item -> item.getProductId().equals(productId));

		if (!productFound) {
			throw new BadRequestException("This product is not in the favorited products list.");
		}

		favoritedProducts.remove(product);

		product.setFavCount(product.getFavCount() - 1);
		this.productRepository.save(product);

		user.setFavoritedProducts(favoritedProducts);

		this.userRepository.save(user);

	}

	@Override
	public List<ProductDto> getFavoritedProductsListByUsername(String username) {

		User user = this.userRepository.findByUsername(username);

		List<Product> favoritedProducts = user.getFavoritedProducts();

		List<ProductDto> productDtos = new ArrayList<ProductDto>();

		for (Product product : favoritedProducts) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(product.getCategory(), categoryDto);
			productDto.setCategoryDto(categoryDto);

			productDtos.add(productDto);
		}

		return productDtos;
	}

	@Override
	public ProductDto rateProductByUsernameAndProductId(String username, Long orderId, Long productId,double rating) {
		
		if(rating>5 || rating<1) {
			throw new BadRequestException("rating can not be larger than 5 or smaller than 1");
		}

		User user = this.userRepository.findByUsername(username);

		Order order = this.orderRepository.findById(orderId).get();
		
		if (!order.getUser().getUsername().equals(username)) {
	        throw new BadRequestException("This order does not belong to the user");
	    }

		boolean isProductInOrder = order.getOrderItems().stream()
	            .anyMatch(item -> item.getProduct().getProductId().equals(productId));
	    if (!isProductInOrder) {
	        throw new BadRequestException("Product not in the order");
	    }
	    
	    Product product = this.productRepository.findById(productId).get();

	    boolean alreadyRated = product.getProductRatings().stream()
	            .anyMatch(pr -> pr.getUser().getUsername().equals(username));
	    if (alreadyRated) {
	        throw new BadRequestException("User already rated this product");
	    }
	    
	    ProductRating productRating = new ProductRating();
	    productRating.setProduct(product);
	    productRating.setUser(user);
	    productRating.setRating(rating);

	    this.productRatingRepository.save(productRating);
	    
	    double currentTotal = product.getRating() * product.getTotalRatingCount();
	    int newCount = product.getTotalRatingCount() + 1;
	    double newAverage = (currentTotal + rating) / newCount;
	    
	    product.setTotalRatingCount(newCount);
	    product.setRating(newAverage);
	    
	    Product updatedProduct = this.productRepository.save(product);
		
	    ProductDto productDto = new ProductDto();
	    BeanUtils.copyProperties(updatedProduct, productDto);
	    
	    CategoryDto categoryDto = new CategoryDto();
	    BeanUtils.copyProperties(updatedProduct.getCategory(), categoryDto);
	    productDto.setCategoryDto(categoryDto);
	    
	    return productDto;

	}

}
