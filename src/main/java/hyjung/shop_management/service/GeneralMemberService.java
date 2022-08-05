package hyjung.shop_management.service;

import hyjung.shop_management.domain.Member;
import hyjung.shop_management.dto.MemberDto;
import hyjung.shop_management.jwt.JwtTokenProvider;
import hyjung.shop_management.repository.MemberRepository;
import hyjung.shop_management.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;


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

    @Override
    @Transactional
    public ApiResponse login(String userId, String userPw) {
        Member member;
        try{
            member = memberRepository.findByUserId(userId);
        } catch (Exception e){
            member = null;
        }
        if(member==null){
            return new ApiResponse(false, HttpStatus.NOT_ACCEPTABLE.value(), null, "등록되어 있지 않은 아이디 입니다.");

        }
        if(!passwordEncoder.matches(userPw, member.getUserPw())){
            return new ApiResponse(false, HttpStatus.NOT_ACCEPTABLE.value(), null, "비밀번호가 틀렸습니다.");

        }

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(member.getId()));
        map.put("token", jwtTokenProvider.createToken(userId));
        map.put("refreshToken", jwtTokenProvider.createRefreshToken());

        String refreshToken = map.get("refreshToken");
        member.changeRefreshToken(refreshToken);
        return new ApiResponse(true, HttpStatus.OK.value(), map, "로그인에 성공하였습니다.");
    }

    @Override
    @Transactional
    public ApiResponse reIssue(String userId, String refreshToken) {
        Member member;
        try{
            member = memberRepository.findByUserId(userId);
        } catch (Exception e){
            return new ApiResponse(false, HttpStatus.NOT_ACCEPTABLE.value(), null, "등록되어 있지 않은 아이디 입니다.");
        }

        if(member.getRefreshToken()!=null && member.getRefreshToken()==refreshToken){
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(member.getId()));
            map.put("token", jwtTokenProvider.createToken(userId));
            map.put("refreshToken", jwtTokenProvider.createRefreshToken());
            
            member.changeRefreshToken(map.get("refreshToken"));
            return new ApiResponse(true, HttpStatus.OK.value(), map, "리프레시 토큰 업데이트가 성공적입니다.");
        } else {
            return new ApiResponse(false, HttpStatus.NOT_ACCEPTABLE.value(), null, "저장되어있는 토큰이 없습니다.");
        }
    }


}
