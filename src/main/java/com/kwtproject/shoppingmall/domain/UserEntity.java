package com.kwtproject.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity extends EntityBase {
    // JPA repository 에서 GET 할때 기본 생성자 사용하여 @Builder 제거
    // @Builder 사용시 @NoArgsConstructor & @AllArgsConstructor 같이 사용 혹은 전 필드 생성자를 만들어준다.
    public UserEntity() {}

    @Column(length = 50, nullable = false, unique = true) // ID
    private String username;

    @Column(length = 50, nullable = false) // realName
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "contact")
    private String userContact;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String role;

    @Column(nullable = false, columnDefinition = "char")
    private final String enabled = "1";

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private final List<AuthoritiesEntity> authList = new ArrayList<>();

    public void addAuth(AuthoritiesEntity auth) {
        authList.add(auth);
    }

    public void clearAuthList() {
        authList.clear();
    }
}
