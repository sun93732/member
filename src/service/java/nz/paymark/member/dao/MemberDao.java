package nz.paymark.member.dao;

import java.util.List;
import java.util.Optional;

import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.shared.dao.DataAccessObject;

public interface MemberDao extends DataAccessObject<Member>{
	public Optional<Member> findMemberById(String MemberId);
	public List<Member> searchMembers(MemberSearchCriteria searchCriteria);
	public Member createMember(Member Member);
	public Member updateMember(Member Member);
	public void deleteMember(String MemberId);
}