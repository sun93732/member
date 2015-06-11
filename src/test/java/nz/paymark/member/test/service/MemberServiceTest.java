package nz.paymark.member.test.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.enumerator.MemberStatus;
import nz.paymark.member.web.config.Constants;
import nz.paymark.member.web.config.ServiceConfig;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class, ServiceConfig.class})

/***
 * 
 * @author rohitadvani
 * @description Test case to test different member services
 *
 */

public class MemberServiceTest {
	
	@Resource
	private MemberService service;

	/*
	 * Private variables
	 */
	private Member member;
	private Member createTestMember = new Member();
	
	/***
	 * First method that will be executed before any test method
	 * It will create the test member in DB
	 */
	
	@Before
	public void setUpClass(){
		createTestMember.setOrganisationId(Constants.ORG_ID);
		createTestMember.setRole(Constants.ROLE);
		createTestMember.setStatus(Constants.STATUS);
		createTestMember.setUserId(Constants.USER_ID);
		
		/* Creating Test member in DB */
		
		member = service.createMember(createTestMember);
	}
	
	@Test
	public void testStack(){
		testCreateMember();
		testUpdateMember();
		testGetMember();
		testDeleteMember();
		testSearchMembersWithCriteria();
	}
	
	/***
	 * @Test Test case for testing create member service
	 */
	
	public void testCreateMember() {
		
		/* Retrieving recently created member from DB */
		
		Optional<Member> retFromDB = service.findMemberById(member.getId());
		
		/* Asserting both member objects */
		
		assertEquals(member.getOrganisationId(),retFromDB.get().getOrganisationId());
		assertEquals(member.getRole(),retFromDB.get().getRole());
		assertEquals(member.getStatus(),retFromDB.get().getStatus());
		assertEquals(member.getUserId(),retFromDB.get().getUserId());
	}
	
	/***
	 * @Test Test case for testing update member service
	 */

	public void testUpdateMember() {
		Optional<Member> expected = service.findMemberById(member.getId());
		expected.get().setStatus(MemberStatus.PROCESSING);

		Member actual = service.updateMember(expected.get());
		assertEquals(MemberStatus.PROCESSING, actual.getStatus());
	}
	
	/***
	 * @Test Test case for retrieving member
	 */

	public void testGetMember() {
		Optional<Member> actual = service.findMemberById(member.getId());
		assertEquals(MemberStatus.PROCESSING, actual.get().getStatus());
		assertEquals(Constants.ORG_ID, actual.get().getOrganisationId());
		assertEquals(Constants.ROLE, actual.get().getRole());
		assertEquals(Constants.USER_ID, actual.get().getUserId());
	}
	
	/***
	 * @Test Test case for deleting member
	 */

	public void testDeleteMember() {
		List<Member> memberList = new ArrayList<Member>();
		Optional<Member> retFromDB = service.findMemberById(member.getId()); // It should return 1 records
		memberList.add(retFromDB.get());
		assertEquals(1, memberList.size());
		memberList.remove(retFromDB.get());

		service.deleteMember(member.getId());
		
		retFromDB = service.findMemberById(member.getId()); // It should return 0 records
		assertEquals(false, retFromDB.isPresent());
		
		/* Creating Test member again in DB */
		
		member = service.createMember(createTestMember);	
	}
	
	/***
	 * @Test Test case for searching members
	 */

	public void testSearchMembersWithCriteria() {
		List<Member> actual = service.searchMember(member.getRole(), member.getStatus(), null, null);
		assertEquals(1, actual.size());
		
		service.deleteMember(member.getId());
		List<Member> retFromDB = service.searchMember(member.getRole(), member.getStatus(), null, null); // It should return 0 records
		assertEquals(0, retFromDB.size());
		
		/* Creating Test member again in DB */
		
		member = service.createMember(createTestMember);
		
		
	}
	
	/***
	 * Last method that will be executed after all test method
	 * are executed.
	 * It will delete the test member created in DB
	 */
	
	@After
	public void tearDownClass(){
		/* Deleting the Test member from database */
		
		service.deleteMember(member.getId());	
	}
}
