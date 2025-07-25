package io.github.enesdernek.e_commerce.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoIU {
	
	private LocalDateTime date;
	
	private String deliveryAdress;
	
	private String phoneNumber;
	
	private String city;
	
	private String district;
		
}
