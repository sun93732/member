package nz.paymark.member.test.dao;

import javax.annotation.Resource;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.enumerator.MemberStatus;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })*/
public class MemberDaoImplTest {
	
	@Resource
	private MemberDao dao;
	@Resource
	private MemberService service;
	private static final String ORG_ID = "Test_Organization";
	private static final String ROLE = "Test_Role";
	private static final MemberStatus STATUS = MemberStatus.CREATED;
	private static final String USER_ID = "Test_User";
	private Member createTestMember = new Member();
	
	/***
	 * First method that will be executed before any test method
	 * It will create the test member in DB
	 */
	
	/*@Before
	public void setUpClass(){
		createTestMember.setOrganisationId(ORG_ID);
		createTestMember.setRole(ROLE);
		createTestMember.setStatus(STATUS);
		createTestMember.setUserId(USER_ID);
		
		 Creating Test member in DB 
		
		//member = service.createMember(createTestMember);
	}*/

	public void createAndSaveMember() {
		//validate(testMember1);
		/*Payment p = PaymentModelTest.createPayment(status, creationTime,
				modificationTime, card, threeDomainSecure, merchant,
				transaction);
		p = dao.createPayment(p);
		notNull(p.getId());
		return p;*/
	}

	//@Test
	//@Transactional
	public void testGetMember() {
		//validate(testMember1);
		/*Payment actual = dao.getPayment(testPayment1.getId());
		assertEquals(testPayment1.getId(), actual.getId());*/
	}

	//@Test
	//@Transactional
	public void testCreateMember() {
		//validate(testMember1);
		/*Member actual = dao.createMember(unsavedMember1);
		assertNotNull(actual);
		assertNotNull(actual.getId());*/
	}
	
	//@Test
	//@Transactional
	public void testUpdateMember() throws InterruptedException {
		//validate(testMember1);
		/*testMember3.setStatus(MemberStatus.PROCESSING);
		Member actual = dao.updateMember(testMember3);

		assertNotNull(actual);
		assertEquals(testMember3.getId(), actual.getId());
		assertEquals(testMember3.getStatus(), actual.getStatus());*/
	}

	//@Test
	//@Transactional
	public void testDeleteMember() {
		//validate(testMember1);
		/*String id = testMember5.getId();
		dao.deleteMember(id);

		try {
			dao.getMember(id);
		} catch (RecordNotFoundException e) {
			return;
		}
		fail();*/
	}
}
