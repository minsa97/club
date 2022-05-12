package com.mctlhg.club.security;

import com.mctlhg.club.entity.ClubMember;
import com.mctlhg.club.entity.ClubMemberRole;
import com.mctlhg.club.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {
    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){
        // 1~80 까지는 USER만 지정
        // 81~90 까지는 USER, MANAGER 로 지정
        // 91~100 까지는 USER, MANAGER, ADMIN 으로 지정
        IntStream.rangeClosed(1,100).forEach(i->{
            ClubMember clubMember=ClubMember.builder()
                    .email("user"+i+"@gmail.com")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();
            //default role
            clubMember.addMemberRole(ClubMemberRole.USER);
            if(i>80){
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i>90){
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(clubMember);
        });
    }

    @Test
    public void testRead(){
        Optional<ClubMember> result=repository.findByEmail("user95@gmail.com", false);
        ClubMember clubMember=result.get();
        System.out.println(clubMember);
    }
}
