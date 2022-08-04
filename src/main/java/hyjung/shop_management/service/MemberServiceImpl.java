package hyjung.shop_management.service;

import hyjung.shop_management.domain.Member;
import hyjung.shop_management.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Long saveMember(Member member) {
        member.encodePassword(passwordEncoder);
        return memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findMemberAll() {
        return memberRepository.findAll();
    }

    @Override
    public boolean checkUserIdDuplicate(String userId) {
        Member member = memberRepository.findByUserId(userId);
        System.out.println(member);
        if(member!=null){
            return false;
        } else {
            return true;
        }
    }
}
