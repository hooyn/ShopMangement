package hyjung.shop_management.controller;

import hyjung.shop_management.domain.Member;
import hyjung.shop_management.dto.MemberDto;
import hyjung.shop_management.request.JoinRequest;
import hyjung.shop_management.response.ApiResponse;
import hyjung.shop_management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/user")
    public ApiResponse join(@RequestBody JoinRequest request){
        String userId = request.getUserId();
        String userPw = request.getUserPw();
        String username = request.getUsername();

        if(!userId.isBlank() && !userPw.isBlank() && !username.isBlank()){
            boolean checkUserIdDuplicate = memberService.checkUserIdDuplicate(userId);

            if(!checkUserIdDuplicate){
                Member member = Member.builder()
                        .userId(userId)
                        .userPw(userPw)
                        .username(username)
                        .build();

                Long id = memberService.saveMember(member);
                return new ApiResponse(true, HttpStatus.OK.value(), id, "회원이 등록되었습니다.");
            } else {
                return new ApiResponse(false, HttpStatus.BAD_REQUEST.value(), null, "아이디가 이미 사용중입니다.");
            }

        } else {
            return new ApiResponse(false, HttpStatus.BAD_REQUEST.value(), null, "필수 입력사항을 입력해주세요.");
        }
    }

    @GetMapping("/api/user")
    public ApiResponse selectMemberAll(){
        List<Member> member = memberService.findMemberAll();

        List<MemberDto> data = member.stream().map(m -> {
            return new MemberDto(m.getId(), m.getUsername());
        }).collect(Collectors.toList());

        return new ApiResponse(true, HttpStatus.OK.value(), data, "모든 회원이 조회되었습니다.");
    }
}
