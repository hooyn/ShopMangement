package hyjung.shop_management.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LoginRequest {
    @ApiModelProperty(example = "사용자 ID")
    private String userId;

    @ApiModelProperty(example = "사용자 PW")
    private String userPw;
}
