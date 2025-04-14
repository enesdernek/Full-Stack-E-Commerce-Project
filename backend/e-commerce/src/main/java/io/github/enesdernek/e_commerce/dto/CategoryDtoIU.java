package io.github.enesdernek.e_commerce.dto;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoIU {
	
	@Size(min=2,max=64)
   private String name;
	
}
