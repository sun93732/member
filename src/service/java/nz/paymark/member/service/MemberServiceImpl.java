package nz.paymark.member.service;

import static nz.paymark.client.shared.web.exception.WebExceptionThrower.throwBadRequestIf;

import java.util.List;

import javax.annotation.Resource;

import nz.paymark.client.shared.web.exception.RecordNotFoundException;
import nz.paymark.member.api.MemberService;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;

import org.springframework.stereotype.Component;

/***
 *
 * @author rohitadvani
 *@description Member Service Implementation for handling different operations for member object
 */

@Component
public class MemberServiceImpl implements MemberService {
	
	@Resource
	MemberDao memberDao;

	/**
	 * 
	 * @param member - Member object containing attributes (ORG_ID, USER_ID, ROLE ETC.)
	 * @return The member that was created or exception if the member is not created.
	 */
	
	@Override
	public Member createMember(Member member) {
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
		throwBadRequestIf(member.getId() == null, "id", "Id should not be null.");
		Member dbMember = memberDao.getMember(member.getId());
		if (dbMember == null) {
			throw new RecordNotFoundException();
		}
		return memberDao.updateMember(member);
	}
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be retrieved
	 * @return The retrieved member
	 */

	@Override
	public Member getMember(String id) throws RecordNotFoundException {
		throwBadRequestIf(id == null, "id", "Id should not be null.");
		throwBadRequestIf(id.length() == 0 , "id", "Id should not be null.");
		Member member = memberDao.getMember(id);
        if (member == null)
            throw new RecordNotFoundException();
        return member;
	}
	
	/**
	 * Returns a list of the Members found by the search
	 * 
	 * @param ids A list of IDs to search for
	 * @return A list of Members
	 */

	@Override
	public List<Member> searchMembers(List<String> ids) {
		return memberDao.searchMembers(ids);
	}
	
	/**
	 * Returns a list of the Members found by the search
	 * 
	 * @param criteria A MemberSearchCriteria instance
	 * @return A list of Members
	 */
	
	@Override
    public List<Member> searchMembersByCriteria(
            MemberSearchCriteria criteria) {
        return memberDao.searchMembersByCriteria(criteria);
    }
	
	/**
	 * 
	 * @param memberId - Member Id of the member that needs to be deleted
	 */

	@Override
	public void deleteMember(String id) {
		memberDao.deleteMember(id);
	}
}
