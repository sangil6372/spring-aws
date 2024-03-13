package com.sangil.springaws.authentication.oauth.dto;

import com.sangil.springaws.user.domain.User;
import lombok.Getter;

import java.io.Serializable;

// SessionUser에는 인증된 사용자 정보만 필요
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
