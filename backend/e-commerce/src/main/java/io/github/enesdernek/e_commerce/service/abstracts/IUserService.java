package io.github.enesdernek.e_commerce.service.abstracts;

import java.util.List;

import org.apache.coyote.BadRequestException;

import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.dto.UserDtoAuthIU;
import io.github.enesdernek.e_commerce.dto.UserDtoIU;
import io.github.enesdernek.e_commerce.jwt.AuthResponse;

public interface IUserService {
	
	AuthResponse authenticate(UserDtoAuthIU userDtoAuthIU);

    UserDto register(UserDtoIU userDtoIU) throws BadRequestException;
    
    UserDto deleteByUserId(Long userId);
    
    void addProductToFavoriteListByUserIdAndProductId(Long userId,Long productId);
    
    void deleteProductFromFavoriteListByUserIdAndProductId(Long userId, Long productId);
    
    List<ProductDto>getFavoritedProductsListByUserId(Long userId);
    
}
