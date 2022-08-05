package hyjung.shop_management.repository.impl;

import hyjung.shop_management.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl{

    private final EntityManager em;

    //@Override
    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    //@Override
    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    //@Override
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    //@Override
    public List<Order> findByUserId(String userId) {
        return em.createQuery("select o from Order o where o.member.userId =: userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
