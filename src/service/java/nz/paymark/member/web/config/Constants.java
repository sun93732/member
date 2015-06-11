package nz.paymark.member.web.config;

import nz.paymark.member.model.enumerator.MemberStatus;

public final class Constants {
	
	private Constants() {
	}

	public static final String SPRING_PROFILE_MOCK = "mock";
	public static final String ORG_ID = "Test_Organization";
	public static final String ROLE = "Test_Role";
	public static final MemberStatus STATUS = MemberStatus.TEST_STATUS;
	public static final String USER_ID = "Test_User";
}
