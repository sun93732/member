package nz.paymark.member.test.web.controller;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nz.paymark.client.shared.web.exception.BadRequestException;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.model.enumerator.MemberRoles;
import nz.paymark.member.service.MemberServiceImpl;
import nz.paymark.member.web.controller.MemberServiceController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceControllerTest {
	@Mock
	MemberServiceImpl service;

	@InjectMocks
	MemberServiceController controller;

	Member newMember, savedMember;

	@Before
	public void onSetUp() {
		newMember = new Member();
		newMember.setId(UUID.randomUUID().toString());	
		newMember.setUserId(UUID.randomUUID().toString());
		newMember.setOrganisationId(UUID.randomUUID().toString());
		newMember.setRole(MemberRoles.EXPENSE_SUBMITTER);
		newMember.setCreationTime(LocalDateTime.now(Clock.systemUTC()));
		newMember.setModificationTime(newMember.getCreationTime());
		
		savedMember = new Member();
		savedMember.setId(UUID.randomUUID().toString());	
		savedMember.setUserId(UUID.randomUUID().toString());
		savedMember.setOrganisationId(UUID.randomUUID().toString());
		savedMember.setRole(MemberRoles.EXPENSE_SUBMITTER);
		savedMember.setCreationTime(LocalDateTime.now(Clock.systemUTC()));
		savedMember.setModificationTime(newMember.getCreationTime());
	}

	@Test
	public void canCreate() {
		when(service.createMember(any())).thenReturn(savedMember);

		Member controllerMember = controller.handleCreate(null, null, null, newMember);

		verify(service, times(1)).createMember(newMember);

		assertEquals(savedMember.getId(), controllerMember.getId());
		assertEquals(savedMember.getUserId(), controllerMember.getUserId());
		assertEquals(savedMember.getOrganisationId(), controllerMember.getOrganisationId());
		assertEquals(savedMember.getRole(), controllerMember.getRole());
	}

	@Test
	public void canGet() {
		when(service.getMember(any())).thenReturn(savedMember);

		String uuid = UUID.randomUUID().toString();

		Member controllerMember = controller.handleRetrieve(uuid);

		verify(service, times(1)).getMember(uuid);

		assertEquals(savedMember, controllerMember);
	}

	@Test
	public void canSearchMemberById() {
		List<Member> members = new ArrayList<>();
		Member newMemberCreated = new Member();
		newMemberCreated.setId(UUID.randomUUID().toString());	
		newMemberCreated.setUserId(UUID.randomUUID().toString());
		newMemberCreated.setOrganisationId(UUID.randomUUID().toString());
		newMemberCreated.setRole(MemberRoles.EXPENSE_SUBMITTER);
		newMemberCreated.setCreationTime(LocalDateTime.now(Clock.systemUTC()));
		newMemberCreated.setModificationTime(newMember.getCreationTime());
		
		Member savedMemberCreated = new Member();
		savedMemberCreated.setId(UUID.randomUUID().toString());	
		savedMemberCreated.setUserId(UUID.randomUUID().toString());
		savedMemberCreated.setOrganisationId(UUID.randomUUID().toString());
		savedMemberCreated.setRole(MemberRoles.EXPENSE_SUBMITTER);
		savedMemberCreated.setCreationTime(LocalDateTime.now(Clock.systemUTC()));
		savedMemberCreated.setModificationTime(newMember.getCreationTime());
		
		members.add(newMemberCreated);
		members.add(savedMemberCreated);

		List<String> idsToSearchFor = new ArrayList<>();
		idsToSearchFor.add(members.get(0).getId());
		idsToSearchFor.add(members.get(1).getId());

		when(service.searchMembers(any())).thenReturn(members);

		List<Member> userResults = controller.handleSearch(idsToSearchFor);

		verify(service, times(1)).searchMembers(idsToSearchFor);

		assertEquals(2, userResults.size());
	}

	@Test
	public void canSearchMemberByCriteria() {
		List<Member> members = new ArrayList<>();
		Member newMemberCreated = new Member();
		newMemberCreated.setId(UUID.randomUUID().toString());	
		newMemberCreated.setUserId(UUID.randomUUID().toString());
		newMemberCreated.setOrganisationId(UUID.randomUUID().toString());
		newMemberCreated.setRole(MemberRoles.EXPENSE_SUBMITTER);
		newMemberCreated.setCreationTime(LocalDateTime.now(Clock.systemUTC()));
		newMemberCreated.setModificationTime(newMember.getCreationTime());
		
		Member savedMemberCreated = new Member();
		savedMemberCreated.setId(UUID.randomUUID().toString());	
		savedMemberCreated.setUserId(UUID.randomUUID().toString());
		savedMemberCreated.setOrganisationId(UUID.randomUUID().toString());
		savedMemberCreated.setRole(MemberRoles.EXPENSE_SUBMITTER);
		savedMemberCreated.setCreationTime(LocalDateTime.now(Clock.systemUTC()));
		savedMemberCreated.setModificationTime(newMember.getCreationTime());
		
		members.add(newMemberCreated);
		members.add(savedMemberCreated);

		String userId = newMemberCreated.getUserId();
		String organisationId = newMemberCreated.getOrganisationId();
		MemberRoles role = newMemberCreated.getRole();
		LocalDateTime cTime = newMemberCreated.getCreationTime();
		LocalDateTime mTime = newMemberCreated.getModificationTime();

		MemberSearchCriteria criteria = new MemberSearchCriteria(userId,
				organisationId, role);
		when(service.searchMembersByCriteria(any())).thenReturn(members);

		List<Member> memberResults = controller.handleSearchCriteria(
				criteria.getUserId(), criteria.getOrganisationId(),
				criteria.getRole(), cTime,null, mTime, null);

		assertEquals(2, memberResults.size());
	}

	@Test
	public void canDeleteMember() {
		String uuid = UUID.randomUUID().toString();

		controller.handleDelete(uuid);

		verify(service, times(1)).deleteMember(uuid);
	}

	@Test(expected = BadRequestException.class)
	public void throwsExceptionWhenMismatchedIds() {
		controller.handleUpdate(UUID.randomUUID().toString(), savedMember);
	}
}