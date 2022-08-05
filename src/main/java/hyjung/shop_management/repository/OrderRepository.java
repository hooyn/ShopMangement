package hyjung.shop_management.repository;

import hyjung.shop_management.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.member.userId =: userId")
    List<Order> findOrderByUserId(@Param("userId") String userId);
}