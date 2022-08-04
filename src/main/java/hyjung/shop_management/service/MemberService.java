package hyjung.shop_management.service;

import hyjung.shop_management.domain.Member;

import java.util.List;

public interface MemberService {

    Long saveMember(Member member);

    Member findMemberById(Long memberId);

    Member findMemberByUserId(String userId);

    List<Member> findMemberAll();

    boolean checkUserIdDuplicate(String userId);
}
