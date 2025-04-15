package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.model.Cart;
import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
import io.github.enesdernek.e_commerce.service.abstracts.ICartService;

@Service
public class CartService implements ICartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public CartDto getCartByCartId(Long cartId) {
		
		Cart cart = this.cartRepository.findById(cartId).get();
		CartDto cartDto = new CartDto();
				
		BeanUtils.copyProperties(cart, cartDto);
		
		List<ProductDto>productDtos = new ArrayList<>();
		
		
		for(Product product : cart.getProducts()) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(product.getCategory(), categoryDto);
			productDto.setCategoryDto(categoryDto);
			
			productDtos.add(productDto);
		}
		
		cartDto.setProductDtos(productDtos);
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(cart.getUser(), userDto);
		cartDto.setUserDto(userDto);
		
		return cartDto;
	}

	@Override
	public CartDto addProductToCart(Long cartId, Long productId) {
	    
	    Product addedProduct = this.productRepository.findById(productId).orElseThrow();
	    Cart cart = this.cartRepository.findById(cartId).orElseThrow();

	    List<Product> currentProducts = cart.getProducts();

	    if (currentProducts == null) {
	        currentProducts = new ArrayList<>();
	    }

	    // setCart gereksiz — çünkü ManyToMany ilişkide yön Cart üzerinden ilerliyor
	    currentProducts.add(addedProduct);
	    cart.setProducts(currentProducts);

	    BigDecimal totalPrice = currentProducts.stream()
	            .map(Product::getPrice)
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	    cart.setTotalPrice(totalPrice);

	    Cart savedCart = cartRepository.save(cart);

	    CartDto cartDto = new CartDto();
	    UserDto userDto = new UserDto();
	    BeanUtils.copyProperties(cart.getUser(), userDto);

	    cartDto.setUserDto(userDto);

	    List<ProductDto> productDtos = new ArrayList<>();
	    for (Product product : currentProducts) {
	        ProductDto productDto = new ProductDto();
	        BeanUtils.copyProperties(product, productDto);

	        CategoryDto categoryDto = new CategoryDto();
	        BeanUtils.copyProperties(product.getCategory(), categoryDto);

	        productDto.setCategoryDto(categoryDto);

	        productDtos.add(productDto);
	    }

	    BeanUtils.copyProperties(savedCart, cartDto);
	    cartDto.setProductDtos(productDtos);

	    return cartDto;
	}



	

}
