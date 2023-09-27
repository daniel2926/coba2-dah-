package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional

public class MemberService {
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    public Long join(Member member){
        Long start = System.currentTimeMillis();
        try {
            validDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
        } finally {
            Long finish = System.currentTimeMillis();
            Long TimeMs = finish - start;
            System.out.println("join " + TimeMs + "ms");
        }
    }

    private void validDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> { throw new IllegalStateException("You are already a member");});
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();

    }
    public Optional<Member> findOne(Long memberID){
        return memberRepository.findById(memberID);
    }
}
