package cc.dcloud.domain.member.service;

import cc.dcloud.domain.Member;
import cc.dcloud.domain.login.dto.MemberDto;
import cc.dcloud.domain.login.dto.SignUpDto;
import cc.dcloud.domain.login.exception.NotFoundException;
import cc.dcloud.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class SimpleMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public SimpleMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member signUp(SignUpDto signUpDto) {
        return memberRepository.save(Member.ofUser(signUpDto));
    }

    @Override
    public Member signUpAdmin(SignUpDto signUpDto) {
        return memberRepository.save(Member.ofAdmin(signUpDto));
    }

    @Override
    public Member getByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(NotFoundException::new);

        return member;
    }


}
