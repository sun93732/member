package nz.paymark.member.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.enumerator.MemberStatus;
import nz.paymark.member.service.MemberServiceImpl;
import nz.paymark.member.web.config.ServiceConfig;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MockitoJUnitRunner.class)
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
	private static final String ORG_ID = "Test_Organization";
	private static final String ROLE = "Test_Role";
	private static final MemberStatus STATUS = MemberStatus.CREATED;
	private static final String USER_ID = "Test_User";

	/*
	 * Private variables
	 */
	
	@Mock
    private MemberDao dao;
	
	@Mock
	private EntityManager em;
    
    @InjectMocks
    private MemberServiceImpl memberService;
    
	private Member createTestMember, returnedMember;
	
	/***
	 * First method that will be executed before any test method
	 * It will create the test member in DB
	 */
	
	@Before
	public void setUpClass(){
		
		/* Creating Test member in DB */
		
		 returnedMember = new Member();
	     when(dao.createMember(any())).thenReturn(returnedMember);
	     createTestMember = memberService.createMember(new Member());
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
		
		assertEquals(returnedMember, createTestMember);
		
		createTestMember.setId(UUID.randomUUID().toString());
		createTestMember.setOrganisationId(ORG_ID);
		createTestMember.setRole(ROLE);
		createTestMember.setStatus(STATUS);
		createTestMember.setUserId(USER_ID);
		
		when(dao.createMember(createTestMember)).thenReturn(returnedMember);
		createTestMember = memberService.createMember(new Member());
		
		when(dao.findMemberById(createTestMember.getId())).thenReturn(Optional.of(createTestMember));
		Optional<Member> retFromDB = memberService.findMemberById(createTestMember.getId());
		
		/* Asserting both member objects */
		
		assertEquals(returnedMember.getOrganisationId(),retFromDB.get().getOrganisationId());
		assertEquals(returnedMember.getRole(),retFromDB.get().getRole());
		assertEquals(returnedMember.getStatus(),retFromDB.get().getStatus());
		assertEquals(returnedMember.getUserId(),retFromDB.get().getUserId());
	}
	
	/***
	 * @Test Test case for testing update member service
	 */

	public void testUpdateMember() {
		when(dao.findMemberById(createTestMember.getId())).thenReturn(Optional.of(createTestMember));
		Optional<Member> expected = memberService.findMemberById(createTestMember.getId());
		expected.get().setStatus(MemberStatus.PROCESSING);
		
		when(dao.updateMember(createTestMember)).thenReturn(createTestMember);
		Member actual = memberService.updateMember(expected.get());
		assertEquals(MemberStatus.PROCESSING, actual.getStatus());
	}
	
	/***
	 * @Test Test case for retrieving member
	 */

	public void testGetMember() {
		when(dao.findMemberById(createTestMember.getId())).thenReturn(Optional.of(createTestMember));
		Optional<Member> actual = memberService.findMemberById(createTestMember.getId());
		assertEquals(MemberStatus.PROCESSING, actual.get().getStatus());
		assertEquals(ORG_ID, actual.get().getOrganisationId());
		assertEquals(ROLE, actual.get().getRole());
		assertEquals(USER_ID, actual.get().getUserId());
	}
	
	/***
	 * @Test Test case for deleting member
	 */

	public void testDeleteMember() {
		when(dao.findMemberById(createTestMember.getId())).thenReturn(Optional.empty());
		memberService.deleteMember(createTestMember.getId());
		
		when(dao.findMemberById(createTestMember.getId())).thenReturn(Optional.of(createTestMember));
		memberService.findMemberById(createTestMember.getId());
	}
	
	/***
	 * @Test Test case for searching members
	 */

	public void testSearchMembersWithCriteria() {
		when(dao.findMemberById(createTestMember.getId())).thenReturn(Optional.of(createTestMember));
		memberService.searchMember(createTestMember.getRole(), createTestMember.getStatus(), null, null);
	}
	
	/***
	 * Last method that will be executed after all test method
	 * are executed.
	 * It will delete the test member created in DB
	 */
	
	@After
	public void tearDownClass(){
		when(dao.findMemberById(createTestMember.getId())).thenReturn(Optional.empty());
		memberService.deleteMember(createTestMember.getId());	
	}
}
