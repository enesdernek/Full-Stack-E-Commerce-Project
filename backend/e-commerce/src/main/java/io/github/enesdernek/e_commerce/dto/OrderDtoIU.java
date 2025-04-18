package io.github.enesdernek.e_commerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoIU {
	
	private LocalDateTime date;
	
	private String deliveryAdress;
		
	
}
