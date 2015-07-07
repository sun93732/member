package nz.paymark.member.web.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.paymark.client.shared.rest.AbstractRestClient;
import nz.paymark.member.api.MemberService;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

public class MemberClient extends AbstractRestClient<Member> implements
		MemberService {

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
		return createEntity(member);
	}

	@Override
	public Member updateMember(Member member) {
		return updateEntity(member.getId(), member);
	}

	@Override
	public Member getMember(String memberId) {
		return getEntity(memberId);
	}

	@Override
	public List<Member> searchMembers(List<String> ids) {
		HttpEntity<?> request = new HttpEntity<>(getHeaders());
		List<String> requestParameters = new ArrayList<String>();
		if (ids != null && ids.size() > 0) {
			requestParameters.add("id="
					+ StringUtils.collectionToDelimitedString(ids, ","));
		}
		HttpEntity<Member[]> response = getRestTemplate().exchange(
				getUrl
						+ "?"
						+ StringUtils.collectionToDelimitedString(
								requestParameters, "&"), HttpMethod.GET,
				request, Member[].class);
		return Arrays.asList(response.getBody());
	}

	@Override
	public List<Member> searchMembersByCriteria(MemberSearchCriteria criteria) {
		HttpEntity<?> request = new HttpEntity<>(getHeaders());
		List<String> requestParameters = new ArrayList<String>();
		if (criteria.getId() != null) {
			requestParameters.add("id" + criteria.getId());
		}

		if (criteria.getUserId() != null) {
			requestParameters.add("userId" + criteria.getUserId());
		}

		if (criteria.getOrganisationId() != null) {
			requestParameters.add("organisationId"
					+ criteria.getOrganisationId());
		}

		if (criteria.getRole() != null) {
			requestParameters.add("role" + criteria.getRole());
		}

		HttpEntity<Member[]> response = getRestTemplate().exchange(
				getUrl
						+ "?"
						+ StringUtils.collectionToDelimitedString(
								requestParameters, "&"), HttpMethod.GET,
				request, Member[].class);
		return Arrays.asList(response.getBody());
	}

	@Override
	public void deleteMember(String memberId) {
		deleteEntity(memberId);
	}
}
