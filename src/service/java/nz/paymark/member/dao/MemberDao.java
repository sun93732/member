package nz.paymark.member.dao;

import java.util.List;

import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.shared.dao.DataAccessObject;
import nz.paymark.shared.models.validation.Validates;

public interface MemberDao extends DataAccessObject<Member>, Validates<Member> {
	public Member getMember(String id);
	public List<Member> searchMembers(List<String> ids);
	public List<Member> searchMembersByCriteria(MemberSearchCriteria criteria);
	public Member createMember(Member member);
	public Member updateMember(Member member);
	public void deleteMember(String id);
}