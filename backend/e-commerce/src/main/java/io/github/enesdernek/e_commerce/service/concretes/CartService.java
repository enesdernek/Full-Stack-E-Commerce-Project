package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.dto.CartItemDto;
import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.exception.NotFoundException;
import io.github.enesdernek.e_commerce.model.Cart;
import io.github.enesdernek.e_commerce.model.CartItem;
import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.model.User;
import io.github.enesdernek.e_commerce.repository.CartItemRepository;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
import io.github.enesdernek.e_commerce.repository.UserRepository;
import io.github.enesdernek.e_commerce.service.abstracts.ICartService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CartService implements ICartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public CartDto getCartByUsername(String username) {
		
		User user = this.userRepository.findByUsername(username);
		
		Cart cart = this.cartRepository.findById(user.getCart().getCartId()).get();
		CartDto cartDto = new CartDto();
				
		BeanUtils.copyProperties(cart, cartDto);
		
		List<CartItemDto>cartItemDtos = new ArrayList<>();
		
		
		for(CartItem cartItem : cart.getCartItems()) {
			CartItemDto cartItemDto = new CartItemDto();
			BeanUtils.copyProperties(cartItem, cartItemDto);
			
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(cartItem.getProduct(), productDto);
			
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(cartItem.getProduct().getCategory(), categoryDto);
			
			productDto.setCategoryDto(categoryDto);
			
			cartItemDto.setProductDto(productDto);
				
			cartItemDtos.add(cartItemDto);
		}
		
		cartDto.setCartItemDtos(cartItemDtos);
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(cart.getUser(), userDto);
		cartDto.setUserDto(userDto);
		
		return cartDto;
	}

	
	@Override
	public CartDto addProductToCart(String username, Long productId, int quantity) throws BadRequestException {

	    Product addedProduct = this.productRepository.findById(productId)
	            .orElseThrow(() -> new BadRequestException("Product not found"));
	    
	    User user = this.userRepository.findByUsername(username);
	    
	    
	    Cart cart = this.cartRepository.findById(user.getCart().getCartId())
	            .orElseThrow(() -> new BadRequestException("Cart not found"));

	    if (addedProduct.getStockQuantity() < quantity) {
	        throw new BadRequestException("Not enough stock for product: " + addedProduct.getName());
	    }

	    List<CartItem> currentCartItems = cart.getCartItems();
	    if (currentCartItems == null) {
	        currentCartItems = new ArrayList<>();
	    }

	    boolean itemUpdated = false;

	    List<CartItem> updatedCartItems = new ArrayList<>();

	    for (CartItem item : currentCartItems) {
	        if (item.getProduct().getProductId().equals(productId)) {
	            int newQuantity = item.getQuantity() + quantity;
	            if (addedProduct.getStockQuantity() < newQuantity) {
	                throw new BadRequestException("Not enough stock to add the requested quantity for product: " + addedProduct.getName());
	            }
	            item.setQuantity(newQuantity);
	            itemUpdated = true;
	        }
	        updatedCartItems.add(item);
	    }

	    if (!itemUpdated) {
	        CartItem newItem = new CartItem();
	        newItem.setProduct(addedProduct);
	        newItem.setQuantity(quantity);
	        newItem.setCart(cart);
	        updatedCartItems.add(newItem);
	    }

	
	    cart.getCartItems().clear();
	    for (CartItem item : updatedCartItems) {
	        item.setCart(cart); 
	        cart.getCartItems().add(item);
	    }

	    Cart savedCart = this.cartRepository.save(cart);

	    CartDto cartDto = new CartDto();
	    BeanUtils.copyProperties(savedCart, cartDto);

	    UserDto userDto = new UserDto();
	    BeanUtils.copyProperties(savedCart.getUser(), userDto);
	    cartDto.setUserDto(userDto);

	    List<CartItemDto> cartItemDtos = new ArrayList<>();
	    for (CartItem item : savedCart.getCartItems()) {
	        CartItemDto cartItemDto = new CartItemDto();
	        BeanUtils.copyProperties(item, cartItemDto);

	        ProductDto productDto = new ProductDto();
	        BeanUtils.copyProperties(item.getProduct(), productDto);

	        CategoryDto categoryDto = new CategoryDto();
	        BeanUtils.copyProperties(item.getProduct().getCategory(), categoryDto);
	        productDto.setCategoryDto(categoryDto);

	        cartItemDto.setProductDto(productDto);
	        cartItemDtos.add(cartItemDto);
	    }

	    cartDto.setCartItemDtos(cartItemDtos);
	    return cartDto;
	}


	
	@Override
	public CartDto changeItemQuantity(String username, Long cartItemId, int quantity) {
	    if (quantity < 0) {
	        throw new IllegalArgumentException("Quantity cannot be less than 0");
	    }
	    
	    User user = this.userRepository.findByUsername(username);

	    CartItem cartItem = cartItemRepository.findById(cartItemId)
	            .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

	    Cart cart = cartRepository.findById(user.getCart().getCartId())
	            .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

	    Product product = cartItem.getProduct();

	    if (quantity == 0) {
	        product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());
	        productRepository.save(product);  

	        cart.getCartItems().removeIf(item -> item.getCartItemId().equals(cartItemId));
	        cartItemRepository.deleteById(cartItemId);
	    } else {
	        
	        if (product.getStockQuantity() < quantity) {
	            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
	        }

	       
	        product.setStockQuantity(product.getStockQuantity() + (cartItem.getQuantity() - quantity));
	        productRepository.save(product); 

	        cartItem.setQuantity(quantity);
	        cartItemRepository.save(cartItem);
	    }

	    BigDecimal newTotalPrice = BigDecimal.ZERO;
	    for (CartItem item : cart.getCartItems()) {
	        BigDecimal itemTotal = item.getProduct().getDiscountedPrice()
	                .multiply(BigDecimal.valueOf(item.getQuantity()));
	        newTotalPrice = newTotalPrice.add(itemTotal);
	    }

	    cartRepository.save(cart); 

	    CartDto cartDto = new CartDto();
	    BeanUtils.copyProperties(cart, cartDto);
	    
	    UserDto userDto = new UserDto();
	    BeanUtils.copyProperties(cart.getUser(), userDto);
	    cartDto.setUserDto(userDto);
	    
	    List<CartItemDto> cartItemDtos = new ArrayList<>();
	    
	    for(CartItem ci : cart.getCartItems()) {
	    	
	    	CartItemDto cartItemDto = new CartItemDto();
	    	BeanUtils.copyProperties(ci, cartItemDto);
	    	
	    	ProductDto productDto = new ProductDto();
	    	BeanUtils.copyProperties(ci.getProduct(), productDto);
	    	
	    	CategoryDto categoryDto = new CategoryDto();
	    	BeanUtils.copyProperties(ci.getProduct().getCategory(), categoryDto);
	    	
	    	productDto.setCategoryDto(categoryDto);
	    	cartItemDto.setProductDto(productDto);
	    	
	    	cartItemDtos.add(cartItemDto);
	    	
	    }
	    
	    cartDto.setCartItemDtos(cartItemDtos);
	    
	    return cartDto;
	}

	@Override
	public void deleteAllItemsByUsername(String username) {
		
		User user = this.userRepository.findByUsername(username);
		 
		Cart cart = this.cartRepository.findById(user.getCart().getCartId())
			    .orElseThrow(() -> new NotFoundException("Cart not found with id: " + user.getCart().getCartId()));
		
	    cart.getCartItems().clear();
	
		this.cartRepository.save(cart);
		
	}

	
	

	


	

}
