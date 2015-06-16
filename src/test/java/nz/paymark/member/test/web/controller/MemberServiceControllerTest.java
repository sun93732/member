package nz.paymark.member.test.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.model.enumerator.MemberStatus;
import nz.paymark.member.service.MemberServiceImpl;
import nz.paymark.member.web.controller.MemberServiceController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RunWith(MockitoJUnitRunner.class)
public class MemberServiceControllerTest {

	@Mock
	private MemberServiceImpl memberService;
	
	@Mock
	private MemberSearchCriteria criteria;
	
	@InjectMocks
	private MemberServiceController memberController;
	
	private String memberId = "b2c17a86-d46d-4c4c-8c3d-3890d0bd094a";

	@Test
	public void testCreateMember() {
		
		Member expected = new Member();
	
		when(memberService.createMember(expected)).thenReturn(expected);

		Member actual = memberController.handleCreate(null, null, null, expected).getBody();
        assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetMember() {
		
		Member expected = new Member();
		expected.setId(memberId);
		Optional<Member> expectedOpt = Optional.of(expected);

		when(memberService.findMemberById(expected.getId())).thenReturn(expectedOpt);

		Member actual = memberController.handleRetrieve(null, null, null, memberId).getBody();
		
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdateMember() {
	
		Member expected = new Member();
		expected.setId(memberId);
		expected.setOrganisationId("sample");
		expected.setStatus(MemberStatus.COMPLETE);
		expected.setUserId(memberId);
		when(memberService.findMemberById(expected.getId())).thenReturn(Optional.of(expected));
		
		when(memberService.updateMember(expected)).thenReturn(expected);
		Member actual = memberController.handleUpdate(memberId, expected).getBody();
		
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testDeleteMember() {
		Member expected = new Member();
		expected.setId(memberId);
		expected.setOrganisationId("ClearPoint");
		expected.setRole("Admin");
		expected.setStatus(MemberStatus.COMPLETE);
		expected.setUserId(memberId);
		when(memberService.findMemberById(expected.getId())).thenReturn(Optional.of(expected));
		memberController.handleDelete(memberId);

		verify(memberService, times(1)).deleteMember(memberId);
		
	}

	@Test
	public void testSearchMember() {
	
		List<Member> expected = new ArrayList<Member>();
		Member member1  = new Member();
		member1.setId(memberId);
		member1.setOrganisationId("ClearPoint");
		member1.setRole("Admin");
		member1.setStatus(MemberStatus.COMPLETE);
		member1.setUserId(memberId);
		
		Member member2 = new Member();
		String memberId2 =  UUID.randomUUID().toString();
		member2.setId(memberId2);
		member2.setOrganisationId("Paymark");
		member2.setRole("Admin");
		member2.setStatus(MemberStatus.COMPLETE);
		member2.setUserId(memberId2);
		
		expected.add(member2);
		expected.add(member1);
		when(memberService.searchMember(null, null, null, null)).thenReturn(expected);
		
		when(criteria.getOrganisationId()).thenReturn(null);
		when(criteria.getRole()).thenReturn(null);
		when(criteria.getStatus()).thenReturn(null);
		when(criteria.getUserId()).thenReturn(null);
		List<Member> actual = memberController.handleSearch(null, null, criteria).getBody();
		assertNotNull(actual);
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testDeleteMemberNotFound() {
		
		String randomId = UUID.randomUUID().toString();
		
		when(memberService.findMemberById(randomId)).thenReturn(Optional.empty());
		ResponseEntity<String> resp = memberController.handleDelete(randomId);
		assertEquals(resp.getStatusCode(), HttpStatus.NOT_FOUND);
	}	
}
