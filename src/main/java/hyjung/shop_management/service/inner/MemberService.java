package hyjung.shop_management.service.inner;

import hyjung.shop_management.domain.Member;
import hyjung.shop_management.response.ApiResponse;

import java.util.Optional;


public interface MemberService {

    ApiResponse saveMember(String userId, String userPw, String username);

    Optional<Member> findMemberById(Long memberId);

    Member findMemberByUserId(String userId);

    ApiResponse findMemberAll();

    boolean checkUserIdDuplicate(String userId);

    ApiResponse login(String userId, String userPw);

    ApiResponse reIssue(String userId, String refreshToken);
}
