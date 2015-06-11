package nz.paymark.member.web.client;

import java.util.List;
import java.util.Optional;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.enumerator.MemberStatus;
import nz.paymark.shared.rest.AbstractRestClient;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;

public class MemberClient extends AbstractRestClient<Member> implements MemberService{

	@Value("${member.service.partner}")
	private String headerPartner;
	
	@Value("${member.service.auth}")
	private String headerAuth;
	
	@Value("${member.service.contract.get}")
	private String getUrl;
	
	@Override
	protected String getHeaderPartner() {
		return headerPartner;
	}

	@Override
	protected String getHeaderAuth() {
		return headerAuth;
	}

	@Override
	protected String getBaseUrl() {
		return getUrl;
	}

	@Override
	public Member createMember(Member member) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public Member updateMember(Member member) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public Optional<Member> findMemberById(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> searchMember(String role, MemberStatus status,
			String orgnizationid, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMember(String memberId) {
		// TODO Auto-generated method stub	
	}
}
