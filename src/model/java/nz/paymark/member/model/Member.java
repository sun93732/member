package nz.paymark.member.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import nz.paymark.shared.model.AbstractRestModel;
import nz.paymark.shared.validation.annotation.IsUUID;



@Entity
@Table(name = "member", schema="member")
public class Member extends AbstractRestModel{
	@IsUUID
	private String memberId;

	@Transient
	@Override
	public String getId() {
		return getMemberId();
	}

	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "memberId_id", unique = true)
	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Member withMemberId(String memberId){
		setMemberId(memberId); 
		return this;
	}

}