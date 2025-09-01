package com.study.springboot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Getter
@Setter
@EqualsAndHashCode(of = "userNo")
@ToString
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String userId;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String userPw;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String userName;

    @Column(length = 3, nullable = false)
    private String job;

    private int coin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updDate;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no")
    private List<MemberAuth> authList = new ArrayList<MemberAuth>();

    public void addAuth(MemberAuth auth) {
        authList.add(auth);
    }

    public void removeAuth(MemberAuth auth) {
        authList.clear();
    }
}
