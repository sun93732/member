package nz.paymark.member.service;

import static nz.paymark.shared.validation.util.ExceptionThrower.throwBadRequestIf;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.model.enumerator.MemberStatus;

import org.springframework.stereotype.Component;

/***
 *
 * @author rohitadvani
 *@description Member Service Implementation for handling different operations for member object
 */

public class MemberServiceImpl implements MemberService {
	
	@Resource private MemberDao memberDao;

	/**
	 * 
	 * @param member - Member object containing attributes (ORG_ID, USER_ID, ROLE ETC.)
	 * @return The member that was created or exception if the member is not created.
	 */
	
	@Override
	public Member createMember(Member member) {
		// TODO Auto-generated method stub
		Member newMember = memberDao.createMember(member);
		return newMember;
	}
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be updated
	 * @param member - Member that needs to be updated
	 * @return The member that was updated or exception if the member is not updated.
	 */

	@Override
	public Member updateMember(Member member) {
		// TODO Auto-generated method stub
		throwBadRequestIf(member.getId() == null, "id", "Id should not be null.");
		return memberDao.updateMember(member);
	}
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be retrieved
	 * @return The retrieved member
	 */

	@Override
	public Optional<Member> findMemberById(String memberId) {
		throwBadRequestIf(memberId == null, "id", "Id should not be null.");
		throwBadRequestIf(memberId.length() == 0 , "id", "Id should not be null.");
		return memberDao.findMemberById(memberId);
	}
	
	/**
	 * 
	 * @param role - For searching members based on role
	 * @param status - For searching members based on status
	 * @param organizationId - For searching members based on organization id
	 * @param userId - For searching members based on user id
	 * @return The retrieved member based on search criteria or null
	 */

	@Override
	public List<Member> searchMember(String role, MemberStatus status,
			String organisationId, String userId) {
		MemberSearchCriteria criteria = new MemberSearchCriteria();
		criteria.setRole(role);
		criteria.setOrganisationId(organisationId);
		criteria.setStatus(status);
		criteria.setUserId(userId);
		return memberDao.searchMembers(criteria);
	}
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be deleted
	 */

	@Override
	public void deleteMember(String memberId) {
		// TODO Auto-generated method stub
		memberDao.deleteMember(memberId);
	}
}
