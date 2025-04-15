package io.github.enesdernek.e_commerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id")
	private Long cartId;
	
	@Column(name="total_price")
	private BigDecimal totalPrice;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(
	    name = "cart_product", // ilişkiyi tutacak ara tablo
	    joinColumns = @JoinColumn(name = "cart_id"), // bu sınıfın FK'si
	    inverseJoinColumns = @JoinColumn(name = "product_id") // diğer sınıfın FK'si
	)
	private List<Product>products;
	
	

}
