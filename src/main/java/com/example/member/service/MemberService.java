package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(MemberDTO memberDTO) {
        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setPassword(memberDTO.getPassword());
        return memberRepository.save(member);
    }

    public Optional<Member> getMember(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void updateMember(Long id, MemberDTO memberDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setPassword(memberDTO.getPassword());
        memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
