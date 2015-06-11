package nz.paymark.member.test.web.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import nz.paymark.member.api.*;
import nz.paymark.member.web.controller.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceControllerTest {

	@Mock
	private MemberService memberService;

	@InjectMocks
	private MemberServiceController memberController;

	@Test
	public void testCreateMember() {
		
		// Test Case for creating new member
		
		/*
		 * Creating instance for Member
		 * 
		Member member = newMember(false); // Used for passing new member details
		Member expected = newMember(true); // Used for expected return
		*
		*/

		/*when(memberService.createMember(member)).thenReturn(expected);

		Member actual = memberController.handleCreate(member);

		assertNotNull(actual);
		assertEquals(expected, actual);*/
	}

	@Test
	public void testGetMember() {
		/* Test case to retreive the members details*/
		/*Member member = newMember(true);

		when(memberService.getMember(member.getId())).thenReturn(member);

		Member actual = memberController.handleRead(member.getId());

		assertEquals(member, actual);*/
	}

	@Test
	public void testUpdateMember() {
		/* Test case to update the members details*/
	}

	@Test
	public void testDeleteMember() {
		/*
		 * Test case to delete member
		 */
	}

	@Test
	public void testSearchMember() {
		/*
		 * Test case to search a specific member
		 */
	}

	public void testDeleteMemberNotFound() {
		
	}	
}
