package nz.paymark.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import nz.paymark.member.model.enumerator.MemberStatus;
import nz.paymark.shared.model.AbstractRestModel;
import nz.paymark.shared.validation.annotation.IsUUID;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "member", schema="member")/* schema here in MySQL is the database name*/
public class Member extends AbstractRestModel{
	
	private static final String MEMBER_TABLE_ROLE_COLUMN = "role";
	private static final String MEMBER_TABLE_STATUS_COLUMN = "status";
	private static final String MEMBER_TABLE_USER_ID_COLUMN = "user_id";
	private static final String MEMBER_TABLE_ORG_ID_COLUMN = "organisation_id";
	private static final String MEMBER_TABLE_MEMBER_ID_COLUMN = "id";
	
	@IsUUID
	private String id;
	
	@Column(name = MEMBER_TABLE_ROLE_COLUMN)
	private String role;
	
	@Column(name = MEMBER_TABLE_STATUS_COLUMN)
	private MemberStatus status;
	
	@Column(name = MEMBER_TABLE_USER_ID_COLUMN)
	private String userId;
	
	@Column(name = MEMBER_TABLE_ORG_ID_COLUMN)
	private String organisationId;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = MEMBER_TABLE_MEMBER_ID_COLUMN, unique = true)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void setStatus(MemberStatus status) {
		this.status = status;
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

	public Member withId(String id){
		setId(id); 
		return this;
	}

	public Member withOrganisationId(String organisationId){
		setOrganisationId(organisationId); 
		return this;
	}

	public Member withRole(String role){
		setRole(role); 
		return this;
	}

	public Member withStatus(MemberStatus status){
		setStatus(status); 
		return this;
	}

	public Member withUserId(String userId){
		setUserId(userId); 
		return this;
	}

}