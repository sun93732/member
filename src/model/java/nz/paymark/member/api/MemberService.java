package nz.paymark.member.api;

import java.util.List;

import nz.paymark.member.model.Member;
import nz.paymark.shared.model.Partner;

public interface MemberService {
	Member createMember(final Member member);
	Member updateMember(final String memberId, final Member member);
	Member getMember(final String memberId);
	List<Member> getContract(final Member example);
}
