package io.github.enesdernek.e_commerce.service.abstracts;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.dto.UserDtoAuthIU;
import io.github.enesdernek.e_commerce.dto.UserDtoIU;
import io.github.enesdernek.e_commerce.jwt.AuthResponse;

public interface IUserService {
		
	AuthResponse authenticate(UserDtoAuthIU userDtoAuthIU);

    UserDto register(UserDtoIU userDtoIU) throws BadRequestException;
    
    UserDto deleteByUsername(String username);
    
    void addProductToFavoriteListByUsernameAndProductId(String username,Long productId);
    
    void deleteProductFromFavoriteListByUsernameAndProductId(String username, Long productId);
    
    List<ProductDto>getFavoritedProductsListByUsername(String username,int pageNo,int pageSize);
    
    ProductDto rateProductByUsernameAndProductId(String username, Long orderId, Long productId,double rating);
    
    UserDto getCurrentUser(String username);
    
    
}
