package io.github.enesdernek.e_commerce.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="cart_item")
public class CartItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_item_id")
	private Long cartItemId;
	
	@Column(name="quantity",nullable = false)
	private int quantity;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
