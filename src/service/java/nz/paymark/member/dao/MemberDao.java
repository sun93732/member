package nz.paymark.member.dao;

import java.util.List;
import java.util.Optional;

import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.shared.dao.DataAccessObject;

public interface MemberDao {
	public Optional<Member> findMemberById(String id);
	public List<Member> searchMembers(MemberSearchCriteria searchCriteria);
	public Member createMember(Member member);
	public Member updateMember(Member member);
	public void deleteMember(String id);
}