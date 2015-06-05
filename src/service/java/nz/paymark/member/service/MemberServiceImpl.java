package nz.paymark.member.service;

import java.util.List;
import nz.paymark.member.web.config.Constants;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Component
//@Profile("!" + Constants.SPRING_PROFILE_MOCK)
public class MemberServiceImpl implements MemberService {

	@Override
	public Member createMember(Member member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member updateMember(String memberId, Member member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member getMember(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> getContract(Member example) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
