package nz.paymark.member.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import nz.paymark.client.shared.model.AbstractRestModel;
import nz.paymark.member.model.enumerator.MemberRoles;
import nz.paymark.shared.models.usertypes.LocalDateTimePersistenceConverter;
import nz.paymark.shared.models.validation.annotation.IsUUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "member", schema = "member", uniqueConstraints = @UniqueConstraint(columnNames = {
		"user_Id", "organisation_Id" }))
@ApiModel(value = "Member", description = "Details of Members.")
public class Member extends AbstractRestModel {

	private static final String MEMBER_TABLE_MEMBER_ID_COLUMN = "id";
	private static final String MEMBER_TABLE_USER_ID_COLUMN = "user_id";
	private static final String MEMBER_TABLE_ORG_ID_COLUMN = "organisation_id";
	private static final String MEMBER_TABLE_ROLE_COLUMN = "role";
	private static final String MEMBER_TABLE_CREATION_DATE = "creation_time";
	private static final String MEMBER_TABLE_MODIFICATION_DATE = "modification_time";
	private static final String MEMBER_TABLE_MEMBER_ID_TYPE = "uuid";
	private static final String MEMBER_TABLE_MEMBER_ID_STRATEGY = "uuid2";

	@IsUUID
	private String id;
	@IsUUID
	private String userId;
	@IsUUID
	private String organisationId;
	private MemberRoles role;
	private LocalDateTime creationTime;
	private LocalDateTime modifiedTime;

	@Column(name = MEMBER_TABLE_CREATION_DATE)
	@ApiModelProperty(value = "The date when Member is created.  Date format follows ISO8601 YYYY-MM-DDThh:mm:ss.SSSZ. "
			+ "Only required for PUT operations.", required = true)
	@Convert(converter = LocalDateTimePersistenceConverter.class)
	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name = MEMBER_TABLE_MODIFICATION_DATE)
	@ApiModelProperty(value = "The date when Member is modified.  Date format follows ISO8601 YYYY-MM-DDThh:mm:ss.SSSZ."
			+ " Only required for PUT operations.", required = true)
	@Type(type = "nz.paymark.shared.models.usertypes.PersistentLocalDateTime")
	@Version
	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@Id
	@GenericGenerator(name = MEMBER_TABLE_MEMBER_ID_TYPE, strategy = MEMBER_TABLE_MEMBER_ID_STRATEGY)
	@GeneratedValue(generator = MEMBER_TABLE_MEMBER_ID_TYPE)
	@Column(name = MEMBER_TABLE_MEMBER_ID_COLUMN, unique = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@GenericGenerator(name = MEMBER_TABLE_MEMBER_ID_TYPE, strategy = MEMBER_TABLE_MEMBER_ID_STRATEGY)
	@GeneratedValue(generator = MEMBER_TABLE_MEMBER_ID_TYPE)
	@Column(name = MEMBER_TABLE_USER_ID_COLUMN)
	@NotNull
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@GenericGenerator(name = MEMBER_TABLE_MEMBER_ID_TYPE, strategy = MEMBER_TABLE_MEMBER_ID_STRATEGY)
	@GeneratedValue(generator = MEMBER_TABLE_MEMBER_ID_TYPE)
	@Column(name = MEMBER_TABLE_ORG_ID_COLUMN)
	@NotNull
	public String getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
	}

	@Column(name = MEMBER_TABLE_ROLE_COLUMN)
	@NotNull
	public MemberRoles getRole() {
		return role;
	}

	public void setRole(MemberRoles role) {
		this.role = role;
	}

	public Member withId(String id) {
		setId(id);
		return this;
	}

	public Member withOrganisationId(String organisationId) {
		setOrganisationId(organisationId);
		return this;
	}

	public Member withRole(MemberRoles role) {
		setRole(role);
		return this;
	}

	public Member withUserId(String userId) {
		setUserId(userId);
		return this;
	}

	public Member withCreationTime(LocalDateTime creationTime) {
		setCreationTime(creationTime);
		return this;
	}

	public Member withModifiedTime(LocalDateTime modifiedTime) {
		setModifiedTime(modifiedTime);
		return this;
	}
}