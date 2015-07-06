package nz.paymark.member.test.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.notNull;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import nz.paymark.logging.LoggerFactory;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.dao.impl.MemberDaoImpl;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.test.model.MemberModelTest;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Ignore
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class MemberDaoImplTest {
	
	private static Logger logger = LoggerFactory.getLogger();
	
	@Resource
	private Member createTestMember;
	private static final String MEMBER_ID = UUID.randomUUID().toString();
	private static final String ORG_ID = "Test_Organization";
	private static final String ROLE = "Test_Role";
	private static final String USER_ID = "Test_User";
	
	@Mock
    private MemberSearchCriteria searchCriteria;
	@Mock
	private EntityManager em;
	
	@InjectMocks
    private MemberDaoImpl memberDao;
	
	/***
	 * First method that will be executed before any test method
	 * It will create the test member in DB
	 */
	
	@Before
	public void setUpClass(){
		/*try{
			createTestMember = createAndSaveMember(MEMBER_ID, ORG_ID, ROLE, MemberStatus.CREATED, USER_ID, memberDao);
		}catch (ConstraintViolationException e) {
			for (ConstraintViolation<?> v : e.getConstraintViolations()) {
				logger.error("Constraint violation! {} : {} = {}",
						v.getPropertyPath(), v.getInvalidValue());
			}
			throw e;
		} catch (Exception e) {
			logger.error("Error creating fixture data: {}", e.getMessage());
			throw e;
		}*/
	}
	
	/*public Member createAndSaveMember(String Member_Id, String Org_Id, String Role, MemberStatus Status, String User_Id, MemberDao memberDao){
		Member createdMember = MemberModelTest.createMember(Member_Id, Org_Id, Role, Status, User_Id);
		when(em.merge(createdMember)).thenReturn(createdMember);
		createdMember = memberDao.createMember(createdMember);
		notNull(createdMember.getId());
		return createdMember;
	}*/
	
	//@Test
	//@Transactional
	/*public void testGetMember() {
		when(em.find(Member.class,createTestMember.getId())).thenReturn(createTestMember);
		Optional<Member> retFromDB = memberDao.findMemberById(createTestMember.getId());
		assertEquals(createTestMember.getId(), retFromDB.get().getId());
	}
	
	@Test
	@Transactional
	public void testGetMemberWithRetrieveDetails() {
		when(em.find(Member.class,createTestMember)).thenReturn(createTestMember);
		Optional<Member> retFromDB = memberDao.findMemberById(createTestMember.getId());
		assertEquals(retFromDB, Optional.empty());
	}
	
	@Test(expected = NullPointerException.class)
	@Transactional
	public void testGetMemberFailOnNullMemberId() {
		when(em.find(Member.class,createTestMember)).thenReturn(createTestMember);
		memberDao.findMemberById((String) null);
	}
	
	@Test
	@Transactional
	public void testCreateMember() {
		when(em.merge(createTestMember)).thenReturn(createTestMember);
		Member actual = memberDao.createMember(createTestMember);
		assertNotNull(actual);
		assertNotNull(actual.getId());
	}
	
	@Test
	@Transactional
	public void testUpdateMember() throws InterruptedException {
		createTestMember.setStatus(MemberStatus.PROCESSING);
		when(em.merge(createTestMember)).thenReturn(createTestMember);
		Member actual = memberDao.updateMember(createTestMember);

		assertNotNull(actual);
		assertEquals(createTestMember.getId(), actual.getId());
		assertEquals(createTestMember.getStatus(), actual.getStatus());
	}
	
	@Test
	@Transactional
	public void testDeleteMember() {
		String id = UUID.randomUUID().toString();
		when(em.find(Member.class,id)).thenReturn(createTestMember);
		memberDao.deleteMember(id);
	}*/
}
