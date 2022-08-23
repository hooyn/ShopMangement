package hyjung.shop_management.controller;

import hyjung.shop_management.request.JoinRequest;
import hyjung.shop_management.request.LoginRequest;
import hyjung.shop_management.response.ApiResponse;
import hyjung.shop_management.service.inner.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Member API"})
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/api/user")
    public ApiResponse join(@RequestBody JoinRequest request){
        String userId = request.getUserId();
        String userPw = request.getUserPw();
        String username = request.getUsername();

        return memberService.saveMember(userId, userPw, username);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest request){
        return memberService.login(request.getUserId(), request.getUserPw());
    }

    @ApiOperation(value = "사용자 조회")
    @GetMapping("/api/user")
    public ApiResponse selectMemberAll(){
        return memberService.findMemberAll();
    }


}
