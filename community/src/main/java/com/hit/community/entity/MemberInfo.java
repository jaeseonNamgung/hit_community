package com.hit.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MemberInfo extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String studentId;
    private String password;
    private String nickName;
    private String major;
    private String gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    @Builder
    public MemberInfo(String studentId, String password, String nickName, String major, String gender) {
        this.studentId = studentId;
        this.password = password;
        this.nickName = nickName;
        this.major = major;
        this.gender = gender;
    }

    public void addMember(Member member){
        this.member = member;
        member.addInfo(this);
    }
}