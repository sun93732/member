package nz.paymark.member.api;

import java.util.List;
import java.util.Optional;

import nz.paymark.member.model.Member;
import nz.paymark.member.model.enumerator.MemberStatus;

/***
 * 
 * @author rohitadvani
 * @description Service responsible for handling different operations for member object
 *
 */

public interface MemberService {
	/**
	 * 
	 * @param member - Member object containing attributes (ORG_ID, USER_ID, ROLE ETC.)
	 * @return The member that was created or exception if the member is not created.
	 */
	Member createMember(final Member member);
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be updated
	 * @param member - Member that needs to be updated
	 * @return The member that was updated or exception if the member is not updated.
	 */
	
	Member updateMember(final Member member);
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be retrieved
	 * @return The retrieved member
	 */
	
	Optional<Member> findMemberById(final String memberId);
	
	/**
	 * 
	 * @param role - For searching members based on role
	 * @param status - For searching members based on status
	 * @param organizationId - For searching members based on organization id
	 * @param userId - For searching members based on user id
	 * @return The retrieved member based on search criteria or null
	 */
	
	List<Member> searchMember(final String role, final MemberStatus status, final String orgnizationid, final String userId);
	
	/**
	 * Delete an existing member
	 * 
	 * @param memberId
	 *            The member to be deleted
	 */
	void deleteMember(String memberId);
}