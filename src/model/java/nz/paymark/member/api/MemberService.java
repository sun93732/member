package nz.paymark.member.api;

import java.util.List;

import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;

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
	Member createMember(Member member);
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be updated
	 * @param member - Member that needs to be updated
	 * @return The member that was updated or exception if the member is not updated.
	 */
	
	Member updateMember(Member member);
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be retrieved
	 * @return The retrieved member
	 */
	
	Member getMember(String id);
	
	/**
	 * Returns a list of the Members found by the search
	 * 
	 * @param criteria A MemberSearchCriteria instance
	 * @return A list of Members
	 */
	
	List<Member> searchMembersByCriteria(MemberSearchCriteria criteria);
	
	/**
	 * Returns a list of the Members found by the search
	 * 
	 * @param ids A list of IDs to search for
	 * @return A list of Members
	 */
	List<Member> searchMembers(List<String> ids);
	
	/**
	 * Delete an existing member
	 * 
	 * @param memberId
	 *            The member to be deleted
	 */
	void deleteMember(String id);
}