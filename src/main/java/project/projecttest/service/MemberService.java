package project.projecttest.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.projecttest.domain.Member;
import project.projecttest.domain.Role;
import project.projecttest.dto.MemberDto;
import project.projecttest.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 중복 아이디 검증
     */
//    public void validateDuplicateId(Member member){
//        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());
//        if(!findMembers.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 이름입니다. 다른 이름을 입력해주세요.");
//        }
//    }
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional
    public Long joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findName = memberRepository.findByUsername(username);
        Member member = findName;

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin").equals(username)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return new User(member.getUsername(), member.getPassword() , authorities);
    }
}