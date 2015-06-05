package nz.paymark.member.web.client;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;

import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.shared.rest.AbstractRestClient;

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
	public List<Member> getContract(Member example) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public Member createMember(Member member) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public Member updateMember(String memberId, Member member) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public Member getMember(String memberId) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

}
