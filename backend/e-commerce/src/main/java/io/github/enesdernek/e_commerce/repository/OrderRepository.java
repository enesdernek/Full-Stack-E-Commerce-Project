package io.github.enesdernek.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.enesdernek.e_commerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query(value="Select * from order where user_id=:userId order by order_id desc",nativeQuery = true)
	List<Order>getAllByUserIdOrderByOrderIdDesc(Long userId);

}
