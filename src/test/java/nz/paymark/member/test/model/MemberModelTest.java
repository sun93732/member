package nz.paymark.member.test.model;

import java.util.UUID;

import javax.annotation.Resource;

import nz.paymark.member.model.Member;
import nz.paymark.shared.validation.SimpleValidator;
import nz.paymark.shared.validation.Validates;
import nz.paymark.tools.testing.config.TestDatabaseConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestDatabaseConfig.class })
public class MemberModelTest implements Validates<Member>{
	@Resource
	private SimpleValidator validator;
	
	private Member testMember;
	
	@Override
	public void validate(final Member arg0) {
		validator.validate(arg0);
	}
	
	public static Member createMember() {
		return new Member();
	}
	
	@Before
	public void createBaseLineValidMember() {
		testMember = createMember(); 
	}
	
	@Test
	public void testValidMember() {
		validate(testMember);
	}
}
