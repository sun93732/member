package nz.paymark.member.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import nz.paymark.client.shared.web.exception.RecordNotFoundException;
import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.model.enumerator.MemberRole;
import nz.paymark.member.test.model.MemberModelTest;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class MemberServiceTest {
	
	@Resource 
	private MemberService service;
	
	/*
	 * Shared and injected data
	 */	
	private Member member;
	private Member createTestMember = new Member();
	
	/***
	 * First method that will be executed before any test method
	 * It will create the test member in DB
	 */
	
	@Before
	public void setUpClass(){
		createTestMember.setId(MemberModelTest.aUUID);
		createTestMember.setUserId(MemberModelTest.aUUID);
		createTestMember.setOrganisationId(MemberModelTest.aUUID);
		createTestMember.setRole(MemberRole.EXPENSE_SUBSCRIBER);
		
		/* Creating Test member in DB */
		
		member = service.createMember(createTestMember);
	}
	
	
	@Test
	@Transactional
	public void testCreateMember() {
		
		/* Retrieving recently created member from DB */
		
		Member expected = service.getMember(member.getId());
		
		/* Asserting both member objects */
		
		assertEquals(member.getUserId(), expected.getUserId());
		assertEquals(member.getOrganisationId(), expected.getOrganisationId());
		assertEquals(member.getRole(), expected.getRole());

	}
	
	@Test
	@Transactional
	public void testUpdateMember() {
		Member expected = service.getMember(member.getId());
		expected.setRole(MemberRole.EXPENSE_SUBMITTER);

		Member actual = service.updateMember(expected);
		assertEquals(actual.getUserId(), expected.getUserId());
		assertEquals(actual.getOrganisationId(), expected.getOrganisationId());
		assertEquals(actual.getRole(), expected.getRole());
	}
	
	@Test
	@Transactional
	public void testGetMember() {
		Member expected = member;
		Member actual = service.getMember(expected.getId());
		assertEquals(actual.getUserId(), expected.getUserId());
		assertEquals(actual.getOrganisationId(), expected.getOrganisationId());
		assertEquals(actual.getRole(), expected.getRole());
	}
	
	@Test(expected = RecordNotFoundException.class)
	@Transactional
	public void testDeleteMember() {

		service.deleteMember(member.getId());
		
		Member checkMember = service.getMember(member.getId()); 
		assertNull(checkMember);
	}
	
	@Test
	@Transactional
	public void testSearchMembersWithCriteria() {
		MemberSearchCriteria criteria = new MemberSearchCriteria();
		criteria.setId(member.getId());
		List<Member> resList = service.searchMembersByCriteria(criteria);
		assertEquals(1, resList.size());
		Member actual = resList.get(0);
		assertEquals(actual.getUserId(), member.getUserId());
		assertEquals(actual.getOrganisationId(), member.getOrganisationId());
		assertEquals(actual.getRole(), member.getRole());
		

		List<String> filterList = new ArrayList<String>();
		filterList.add(member.getId());
		resList = service.searchMembers(filterList);
		assertEquals(1, resList.size());
		actual = resList.get(0);
		assertEquals(actual.getUserId(), member.getUserId());
		assertEquals(actual.getOrganisationId(), member.getOrganisationId());
		assertEquals(actual.getRole(), member.getRole());
		
		service.deleteMember(member.getId());
		List<Member> retFromDB = resList = service.searchMembers(filterList);
		assertEquals(0, retFromDB.size());
	}
}