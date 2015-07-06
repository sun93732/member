package nz.paymark.member.test.model;

import java.util.UUID;

import javax.annotation.Resource;

import nz.paymark.member.model.Member;
import nz.paymark.shared.models.validation.SimpleValidator;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
@Ignore
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class MemberModelTest{
	
	@Resource
	private SimpleValidator validator;
	/**
	 * this is a valid UUID for testing.
	 */
	public final static String aUUID = "b2c17a86-d46d-4c4c-8c3d-3890d0bd094a";

	/**
	 * This is a string that's 36 long, but complete nonsense.
	 */
	public final static String notUUID = "DERPHERPDERPHERPDERPHERPDERPHERP";
	
	
	private Member createTestMember;
	private String MEMBER_ID = UUID.randomUUID().toString();
	private String ORG_ID = "Test_Organization";
	private String ROLE = "Test_Role";
	private String USER_ID = "Test_User";
	
	/*public static Member createMember(String Member_Id, String Org_Id, String Role, MemberStatus status, String User_Id) {
		return new Member().withId(Member_Id).withOrganisationId(Org_Id).withRole(Role).withStatus(status).withUserId(User_Id);
	}
	
	@Before
	public void createValidMember() {
		createTestMember = createMember(MEMBER_ID, ORG_ID, ROLE, MemberStatus.CREATED, USER_ID); 
	}
	
	@Test
	public void testMemberModel() {
		assertEquals(ORG_ID, createTestMember.getOrganisationId());
		assertEquals(ROLE, createTestMember.getRole());
		assertEquals(MemberStatus.CREATED, createTestMember.getStatus());
		assertEquals(USER_ID, createTestMember.getUserId());
	}*/
}
