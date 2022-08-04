package hyjung.shop_management.dto;

import lombok.Getter;

@Getter
public class MemberDto {
    private Long member_id;
    private String username;

    public MemberDto(Long member_id, String username) {
        this.member_id = member_id;
        this.username = username;
    }
}
