package io.github.enesdernek.e_commerce.service.abstracts;

import java.util.List;

import org.apache.coyote.BadRequestException;

import io.github.enesdernek.e_commerce.dto.OrderDto;
import io.github.enesdernek.e_commerce.dto.OrderDtoIU;

public interface IOrderService {
	
	OrderDto add(String username ,OrderDtoIU orderDtoIU) throws BadRequestException;
	
	List<OrderDto>getAllByUsername(String username);
	
	OrderDto getByOrderIdAndUsername(Long orderId,String username) throws BadRequestException;

}
