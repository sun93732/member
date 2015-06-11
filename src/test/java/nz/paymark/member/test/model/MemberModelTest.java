package nz.paymark.member.test.model;

import javax.annotation.Resource;

import nz.paymark.member.model.Member;
import nz.paymark.shared.validation.SimpleValidator;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })*/
public class MemberModelTest{
	@Resource
	private SimpleValidator validator;
	
	private Member testMember;
	
	public static Member createMember() {
		return new Member();
	}
	
	//@Before
	public void createBaseLineValidMember() {
		//testMember = createMember(); 
	}
	
	//@Test
	public void testValidMember() {
		//validate(testMember);
	}
}
