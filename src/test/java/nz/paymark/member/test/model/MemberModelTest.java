package nz.paymark.member.test.model;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import nz.paymark.member.model.Member;
import nz.paymark.member.model.enumerator.MemberRoles;
import nz.paymark.shared.models.validation.SimpleValidator;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class MemberModelTest {

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
    
    /**
	 * UUID with invalid formatting
	 */
	public final static String bungUUID = "871632ba:defb:11e4:b726:645106a22f51";
	
	/**
	 * UUID misformatted by just one character
	 */
	public final static String borkUUID = "876c9f6a-defb1-1e4-a214-645106a22f51";

    private Member testMember;
    private String memberID = UUID.randomUUID().toString();
    private String userId = UUID.randomUUID().toString();
    private String organisationId = UUID.randomUUID().toString();
    private MemberRoles memberRole = MemberRoles.EXPENSE_SUBMITTER;

    public static Member createMember(String memberId,
            String userId, String organisationId,
            MemberRoles memberRole) {
    	return new Member().withId(memberId).withUserId(userId).withOrganisationId(organisationId).withRole(memberRole);
    }

    @Before
    public void createValidMember() {
    	testMember = createMember(memberID, userId,
        		organisationId, memberRole);
    }

    @Test
    public void testMemberModel() {
        assertEquals(memberID, testMember.getId());
        assertEquals(userId, testMember.getUserId());
        assertEquals(organisationId, testMember.getOrganisationId());
        assertEquals(memberRole, testMember.getRole());
    }
    
    @Test
	public void testValidMember() {
		validate(testMember);
	}
    
    @Test(expected = ConstraintViolationException.class)
	public void testIdMisformattedUUID() {
    	testMember.setId(bungUUID);
		validate(testMember);
	}
    
    @Test(expected = ConstraintViolationException.class)
	public void testIdMalformattedUUID() {
    	testMember.setId(borkUUID);
		validate(testMember);
	}

    @Test(expected = ConstraintViolationException.class)
    public void testIdNotUUID() {
    	testMember.setId(notUUID);
		validate(testMember);
		
		testMember.setUserId(notUUID);
		validate(testMember);
		
		testMember.setOrganisationId(notUUID);
		validate(testMember);
    }
   
    public void validate(Member member) {
        validator.validate(member);
    }
}