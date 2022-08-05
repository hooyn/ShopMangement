package hyjung.shop_management.repository;

import hyjung.shop_management.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.PersistenceContext;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(String userId);
}
