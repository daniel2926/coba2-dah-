package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {
    @Autowired
        MemberService memberService;
    @Autowired
        MemberRepository memberRepository ;
        @BeforeEach
        public void beforeEach(){
            memberRepository = new MemoryMemberRepository();
            memberService = new MemberService(memberRepository);
        }


        @Test
        void join() {
            //given
            Member member = new Member();
            member.setName("hello");
            //when
            Long saveId = memberService.join(member);
            //then
            Member findMember = memberRepository.findById(saveId).get();
            Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

        }
        @Test
        public  void DuplicateMemberException() throws Exception{
            //given
            Member member1 = new Member();
            member1.setName("spring");
            Member member2 = new Member();
            member2.setName("spring");

            //when
            memberService.join(member1);
            IllegalStateException ex = assertThrows(IllegalStateException.class,() -> memberService.join(member2));
            Assertions.assertThat(ex.getMessage()).isEqualTo("You are already a member");
//        try{
//            memberService.join(member2);
//        }
//        catch(IllegalStateException e){
//            Assertions.assertThat(ex.getMessage()).isEqualTo("You are already a member");
//
//        }


            //then
        }

        @Test
        void findMembers() {
        }

        @Test
        void findOne() {
        }
    }

