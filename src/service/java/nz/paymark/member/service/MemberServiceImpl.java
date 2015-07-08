package nz.paymark.member.service;

import static nz.paymark.client.shared.web.exception.WebExceptionThrower.throwBadRequestIf;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.OptimisticLockException;

import nz.paymark.client.shared.web.exception.ConflictException;
import nz.paymark.client.shared.web.exception.ForbiddenException;
import nz.paymark.client.shared.web.exception.RecordNotFoundException;
import nz.paymark.client.shared.web.exception.ValidationException;
import nz.paymark.member.api.MemberService;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;

import org.springframework.dao.DataIntegrityViolationException;
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
		throwBadRequestIf(member.getUserId() == null, "userId", "UserId (UUID) should not be null.");
		throwBadRequestIf(member.getOrganisationId() == null, "organisationId", "OrganisationId (UUID) should not be null.");
		throwBadRequestIf(member.getRole() == null, "role", "Member role should not be null.");
		try{
			Member newMember = memberDao.createMember(member);
			return newMember;
		}catch(DataIntegrityViolationException ex){
			throw new ConflictException("Combination of UserId and OrganisationId must be unique");
		}
		
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
		throwBadRequestIf(member.getUserId() == null, "userId", "UserId (UUID) should not be null.");
		throwBadRequestIf(member.getOrganisationId() == null, "organisationId", "OrganisationId (UUID) should not be null.");
		throwBadRequestIf(member.getRole() == null, "role", "Member role should not be null.");
		Member dbMember = memberDao.getMember(member.getId());
		Member updatedMember = new Member();
		if (dbMember == null) {
			throw new RecordNotFoundException();
		}
		
		if (!dbMember.getCreationTime().equals(member.getCreationTime())) {
	           throw new ForbiddenException("Creation time cannot be modified.");
	    }
		try{
			updatedMember = memberDao.updateMember(member);
		}catch(OptimisticLockException e){
			throw new ValidationException("Modified time is inconsistent in the request. Please perform fresh GET before PUT request.");
		}
		return updatedMember;
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
