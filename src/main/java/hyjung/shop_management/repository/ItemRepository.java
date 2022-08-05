package hyjung.shop_management.repository;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
