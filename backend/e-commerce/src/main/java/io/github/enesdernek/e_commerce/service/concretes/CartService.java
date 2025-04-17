package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import io.github.enesdernek.e_commerce.repository.CartItemRepository;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
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
	
	@Override
	public CartDto getCartByCartId(Long cartId) {
		
		Cart cart = this.cartRepository.findById(cartId).get();
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
	public CartDto addProductToCart(Long cartId, Long productId,int quantity) {
	    
	    Product addedProduct = this.productRepository.findById(productId).orElseThrow();
	    Cart cart = this.cartRepository.findById(cartId).orElseThrow();

	    List<CartItem> currentCartItems = cart.getCartItems();

	    if (currentCartItems == null) {
	    	currentCartItems = new ArrayList<>();
	    }
	    
	    boolean itemUpdated = false;

	   
	    for (CartItem item : currentCartItems) {
	        if (item.getProduct().getProductId().equals(productId)) {
	            item.setQuantity(item.getQuantity() + quantity);
	            itemUpdated = true;
	            break;
	        }
	    }
	    
	    if (!itemUpdated) {
	        CartItem cartItem = new CartItem();
	        cartItem.setProduct(addedProduct);
	        cartItem.setQuantity(quantity);
	        cartItem.setCart(cart);
	        currentCartItems.add(cartItem);
	    }

	    cart.setCartItems(currentCartItems);
	    
	    BigDecimal totalPrice = BigDecimal.ZERO;
	    for (CartItem item : currentCartItems) {
	        BigDecimal itemTotal = item.getProduct().getPrice()
	                .multiply(BigDecimal.valueOf(item.getQuantity()));
	        totalPrice = totalPrice.add(itemTotal);
	    }
	    
	    cart.setTotalPrice(totalPrice);
	    

	    Cart savedCart = cartRepository.save(cart);
	    
	    ///////////////////////////////////////

	    CartDto cartDto = new CartDto();
	    UserDto userDto = new UserDto();
	    BeanUtils.copyProperties(cart.getUser(), userDto);

	    cartDto.setUserDto(userDto);
	    
	    //////////////////////////////////////

	    List<CartItemDto> cartItemDtos = new ArrayList<>();

	    
	    for (CartItem item : currentCartItems) {
	        CartItemDto cartItemDto = new CartItemDto();
	        BeanUtils.copyProperties(item, cartItemDto);
	        
	        ProductDto productDto = new ProductDto();
	        BeanUtils.copyProperties(item.getProduct(), productDto);
	        cartItemDto.setProductDto(productDto);
	        
	        CategoryDto categoryDto = new CategoryDto();
	        
	        BeanUtils.copyProperties(item.getProduct().getCategory(), categoryDto);
	        
	        cartItemDto.getProductDto().setCategoryDto(categoryDto);
	        
	        cartItemDtos.add(cartItemDto);
	    }

	    BeanUtils.copyProperties(savedCart, cartDto);
	    cartDto.setCartItemDtos(cartItemDtos);
	    cartDto.setTotalPrice(totalPrice);

	    return cartDto;
	}
	
	@Override
	public CartDto changeItemQuantity(Long cartId, Long cartItemId,int quantity) {
			
		 if (quantity < 0) {
		        throw new IllegalArgumentException("Quantity cannot be less than 0");
		    }

		    CartItem cartItem = cartItemRepository.findById(cartItemId)
		            .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

		    Cart cart = cartRepository.findById(cartId)
		            .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

		    if (quantity == 0) {
		      
		        cart.getCartItems().removeIf(item -> item.getCartItemId().equals(cartItemId));
		        cartItemRepository.deleteById(cartItemId);
		        
		    } else {
		       
		        cartItem.setQuantity(quantity);
		        cartItemRepository.save(cartItem);
		    }

		    BigDecimal newTotalPrice = BigDecimal.ZERO;
		    List<CartItem> cartItems = cart.getCartItems();

		    for (CartItem item : cartItems) {
		        BigDecimal price = item.getProduct().getPrice(); // BigDecimal
		        BigDecimal qty = BigDecimal.valueOf(item.getQuantity());
		        newTotalPrice = newTotalPrice.add(price.multiply(qty));
		    }

		    cart.setTotalPrice(newTotalPrice);
		    cartRepository.save(cart);
		    
		    CartDto cartDto = new CartDto();
		    BeanUtils.copyProperties(cart, cartDto);
		    
		    List<CartItemDto>cartItemDtos = new ArrayList<CartItemDto>();
		    
		    for(CartItem cartItem_ : cart.getCartItems()) {
		    	 CartItemDto cartItemDto = new CartItemDto();
				 BeanUtils.copyProperties(cartItem_, cartItemDto);
				 
				 ProductDto productDto = new ProductDto();
				 BeanUtils.copyProperties(cartItem_.getProduct(), productDto);
				 
				 CategoryDto categoryDto = new CategoryDto();
				 BeanUtils.copyProperties(cartItem_.getProduct().getCategory(), categoryDto);
				 
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
	public void deleteAllItemsByCartId(Long cartId) {
		 
		Cart cart = this.cartRepository.findById(cartId)
			    .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));
		
	    cart.getCartItems().clear();
	
		this.cartRepository.save(cart);
		
	}

	


	

}
