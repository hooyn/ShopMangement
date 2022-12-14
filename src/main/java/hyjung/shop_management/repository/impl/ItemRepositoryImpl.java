package hyjung.shop_management.repository.impl;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl{

    private final EntityManager em;

    //@Override
    public Long save(Item item) {
        em.persist(item);
        return item.getId();
    }

    //@Override
    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    //@Override
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
