package cc.dcloud.domain.member.service;

import cc.dcloud.domain.member.Member;
import cc.dcloud.domain.login.dto.SignUpDto;
import cc.dcloud.exception.NotFoundException;
import cc.dcloud.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SimpleMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public SimpleMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Member signUp(SignUpDto signUpDto) {
        return memberRepository.save(Member.ofUser(signUpDto));
    }

    @Override
    @Transactional
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
