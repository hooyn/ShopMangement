package hyjung.shop_management.service;

import hyjung.shop_management.domain.Member;
import hyjung.shop_management.dto.MemberDto;
import hyjung.shop_management.repository.MemberRepository;
import hyjung.shop_management.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ApiResponse saveMember(String userId, String userPw, String username) {
        if(!userId.isBlank() && !userPw.isBlank() && !username.isBlank()){
            boolean checkUserIdDuplicate = checkUserIdDuplicate(userId);

            if(checkUserIdDuplicate){
                Member member = new Member(userId, userPw, username);
                member.encodePassword(passwordEncoder);
                Long id = memberRepository.save(member);
                return new ApiResponse(true, HttpStatus.OK.value(), id, "회원이 등록되었습니다.");
            } else {
                return new ApiResponse(false, HttpStatus.BAD_REQUEST.value(), null, "아이디가 이미 사용중입니다.");
            }

        } else {
            return new ApiResponse(false, HttpStatus.BAD_REQUEST.value(), null, "필수 입력사항을 입력해주세요.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse findMemberAll() {
        List<Member> member = memberRepository.findAll();

        List<MemberDto> data = member.stream().map(m -> {
            return new MemberDto(m.getId(), m.getUsername());
        }).collect(Collectors.toList());

        return new ApiResponse(true, HttpStatus.OK.value(), data, "모든 회원이 조회되었습니다.");
    }

    @Override
    public boolean checkUserIdDuplicate(String userId) {
        Member member = memberRepository.findByUserId(userId);
        System.out.println(member);
        if(member!=null){
            return false;
        } else {
            return true;
        }
    }
}
