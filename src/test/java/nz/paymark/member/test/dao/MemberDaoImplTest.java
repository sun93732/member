package nz.paymark.member.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.util.Assert.notNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import nz.paymark.client.shared.web.exception.RecordNotFoundException;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.model.enumerator.MemberRoles;
import nz.paymark.member.test.model.MemberModelTest;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class MemberDaoImplTest {

	@Resource
	private MemberDao dao;
	public final static String aUUID2 = "65d3df1c-defb-11e4-b6a7-645106a22f51";
	public final static String mockUserId = "7cf47fe2-c3dd-4c6b-9895-7ba767ba529c";
	public final static String mockOrganisationId = "9cf27fe4-c1dd-4c6b-1892-7ba767ba902c";
	public final static String[] emails = { "example@paymark.co.nz",
			                                "example@anz.co.nz",
			                                "example@paymark.co.nz",
			                                "example@bnz.co.nz" };

	@SuppressWarnings("unused")
	private Member testMember1, testMember2, testMember3, testMember4, testMember5,
			testMember6;

	private Member unsavedMember;

	@Before
	public void createFixtureData() {
		testMember1 = createAndSaveMember(mockUserId, mockOrganisationId, MemberRoles.EXPENSE_SUBSCRIBER);
		unsavedMember = new Member().withUserId("1cf89fe3-c3dd-4c6b-9027-7ba767ba810c").withOrganisationId("6cf09fe2-c3dd-4c6b-2189-7ba767ba906c")
				.withRole(MemberRoles.EXPENSE_SUBMITTER);
		
		testMember2 = createAndSaveMember("1cf28fe3-c3dd-4c6b-6930-7ba767ba218c", "7cf09fe2-c3dd-4c6b-8716-7ba767ba216c", MemberRoles.EXPENSE_SUBSCRIBER);;
		testMember3 = createAndSaveMember("1cf85fe3-c3dd-4c6b-1296-7ba767ba317c", "8cf09fe2-c3dd-4c6b-0818-7ba767ba718c", MemberRoles.EXPENSE_SUBMITTER);;
	}

	public Member createAndSaveMember(String userId, String organisationId,
			MemberRoles role) {
		Member member = new Member().withUserId(userId).withOrganisationId(organisationId)
				.withRole(role);
		member = dao.createMember(member);
		notNull(member.getId()); 
		return member;
	}

	@Test
	@Transactional
	public void testGetMemberById() {
		Member actual = dao.getMember(testMember1.getId());
		assertEquals(testMember1.getId(), actual.getId());
	}

	@Test
	@Transactional
	public void testGetMemberDetailsById() {
		Member actual = dao.getMember(testMember1.getId());
		assertEquals(testMember1.getUserId(), actual.getUserId());
		assertEquals(testMember1.getOrganisationId(), actual.getOrganisationId());
		assertEquals(testMember1.getRole(), actual.getRole());
	}

	@Test(expected = NullPointerException.class)
	@Transactional
	public void testGetMemberFailOnNullMemberId() {
		dao.getMember((String) null);
	}

	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void testCreateMemberWithNullMemberRole() {
		unsavedMember.setRole(null);
		dao.createMember(unsavedMember);
	}

	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void testCreateMemberWithNullUserId() {
		unsavedMember.setUserId(null);
		dao.createMember(unsavedMember);
	}
	
	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void testCreateMemberWithNullOrganisationId() {
		unsavedMember.setOrganisationId(null);
		dao.createMember(unsavedMember);
	}
	
	@Test
	@Transactional
	public void testUpdateMember() {
		testMember1.setRole(MemberRoles.EXPENSE_SUBSCRIBER);
		Member actual = dao.updateMember(testMember1);

		assertNotNull(actual);
		assertEquals(testMember1.getId(), actual.getId());
		assertEquals(testMember1.getRole(), actual.getRole());
	}

	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void testUpdateMemberFailOnNonUUIDMemberId() {
		testMember1.setId(MemberModelTest.notUUID);
		dao.updateMember(testMember1);
	}
	
	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void testUpdateMemberFailOnNonUUIDUserId() {
		testMember1.setUserId(MemberModelTest.notUUID);
		dao.updateMember(testMember1);
	}
	
	@Test(expected = ConstraintViolationException.class)
	@Transactional
	public void testUpdateMemberFailOnNonUUIDOrganisationId() {
		testMember1.setOrganisationId(MemberModelTest.notUUID);
		dao.updateMember(testMember1);
	}

	@Test
	@Transactional
	public void testSearchByMemberRole() {
		List<Member> actual = dao.searchMembersByCriteria(new MemberSearchCriteria()
				.withRole(testMember1.getRole()));
		assertEquals(2, actual.size());
	}

	@Test
	@Transactional
	public void testSearchByUserId() {
		List<Member> actual = dao.searchMembersByCriteria(new MemberSearchCriteria()
				.withUserId(testMember1.getUserId()));
		assertEquals(1, actual.size());
	}

	@Test
	@Transactional
	public void testSearchByOrganisationId() {
		List<Member> actual = dao.searchMembersByCriteria(new MemberSearchCriteria()
				.withOrganisationId(testMember1.getOrganisationId()));
		assertEquals(1, actual.size());
	}

	
	@Test
	@Transactional
	public void testSearchByMultipleCriteria() {
		List<Member> actual = dao.searchMembersByCriteria(new MemberSearchCriteria()
				.withUserId(testMember2.getUserId())
				.withOrganisationId(testMember2.getOrganisationId())
				.withRole(testMember2.getRole()));

		assertNotNull(actual);
		assertEquals(1, actual.size());
	}

	@Test
	@Transactional
	public void testSearchNothingReturned() {
		List<Member> actual = dao.searchMembers(Collections.emptyList());
		assertNotNull(actual);
		assertEquals(0, actual.size());
	}

	@Test
	@Transactional
	public void testDeleteMember() {
		String id = testMember1.getId();
		dao.deleteMember(id);
		assertNull(dao.getMember(id));
	}

	@Test(expected = RecordNotFoundException.class)
	@Transactional
	public void testDeleteMemberFailOnNonExistantUser() {
		dao.deleteMember(UUID.randomUUID().toString());
	}
}