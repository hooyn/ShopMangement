package hyjung.shop_management.repository;

import hyjung.shop_management.domain.Member;

import java.util.List;

public interface MemberRepository {

    Long save(Member member);

    Member findById(Long id);

    Member findByUserId(String userId);

    List<Member> findAll();
}
