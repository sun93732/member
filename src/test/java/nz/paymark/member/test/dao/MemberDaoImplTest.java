package nz.paymark.member.test.dao;

import javax.annotation.Resource;

import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.shared.validation.SimpleValidator;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })*/
public class MemberDaoImplTest /*implements Validates<Member>*/ {
	@Resource
	private SimpleValidator validator;

	@Resource
	private MemberDao dao;

	private Member testMember1;

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
