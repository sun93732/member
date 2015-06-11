package nz.paymark.member.model;

import nz.paymark.member.model.enumerator.MemberStatus;

public class MemberSearchCriteria {
	
	private String role;
	private String userId;
	private String organisationId;
	private MemberStatus status;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
	}
	public MemberStatus getStatus() {
		return status;
	}
	public void setStatus(MemberStatus status) {
		this.status = status;
	}

}
