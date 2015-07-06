package nz.paymark.member.model;

import java.time.LocalDateTime;

import nz.paymark.member.model.enumerator.MemberRoles;

public class MemberSearchCriteria {
	
	private String id;
	private String userId;
	private String organisationId;
	private MemberRoles role;
	private LocalDateTime creationTimeBegin;
    private LocalDateTime creationTimeEnd;
    private LocalDateTime modificationTimeBegin;
    private LocalDateTime modificationTimeEnd;
    
    public MemberSearchCriteria(String userId, String organisationId,
    		MemberRoles role, LocalDateTime creationTimeBegin,
            LocalDateTime modificationTimeBegin) {
        this.userId = userId;
        this.organisationId = organisationId;
        this.role = role;
        this.creationTimeBegin = creationTimeBegin;
        this.modificationTimeBegin = modificationTimeBegin;

    }

    public MemberSearchCriteria(String userId, String organisationId,
    		MemberRoles role, LocalDateTime creationTimeBegin,
            LocalDateTime creationTimeEnd, LocalDateTime modificationTimeBegin,
            LocalDateTime modificationTimeEnd) {
        this(userId, organisationId, role, creationTimeBegin, modificationTimeBegin);
        this.creationTimeEnd =  creationTimeEnd;
        this.modificationTimeEnd = modificationTimeEnd;
    }

    public MemberSearchCriteria(String userId, String organisationId,
    		MemberRoles role) {
        this.userId = userId;
        this.organisationId = organisationId;
        this.role = role;
    }
    
    public MemberSearchCriteria() {
    }
	
    public String getId(){
    	return id;
    }
    
    public void setId(String id){
    	this.id = id;
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
    
	public MemberRoles getRole() {
		return role;
	}
	public void setRole(MemberRoles role) {
		this.role = role;
	}
	
	public LocalDateTime getCreationTimeBegin() {
        return creationTimeBegin;
    }

    public void setCreationTimeBegin(LocalDateTime creationTimeBegin) {
        this.creationTimeBegin = creationTimeBegin;
    }

    public LocalDateTime getCreationTimeEnd() {
        return creationTimeEnd;
    }

    public void setCreationTimeEnd(LocalDateTime creationTimeEnd) {
        this.creationTimeEnd = creationTimeEnd;
    }

    public LocalDateTime getModificationTimeBegin() {
        return modificationTimeBegin;
    }

    public void setModificationTimeBegin(LocalDateTime modificationTimeBegin) {
        this.modificationTimeBegin = modificationTimeBegin;
    }

    public LocalDateTime getModificationTimeEnd() {
        return modificationTimeEnd;
    }

    public void setModificationTimeEnd(LocalDateTime modificationTimeEnd) {
        this.modificationTimeEnd = modificationTimeEnd;
    }
    
    public MemberSearchCriteria withId(String id) {
        setId(id);
        return this;
    }
    
    public MemberSearchCriteria withUserId(String userId) {
        setUserId(userId);
        return this;
    }
    
    public MemberSearchCriteria withOrganisationId(String organisationId){
    	setOrganisationId(organisationId);
    	return this;
    }
    
    public MemberSearchCriteria withRole(MemberRoles role){
    	setRole(role);
    	return this;
    }
    
    public MemberSearchCriteria withCreationTimeBegin(LocalDateTime ctb) {
        setCreationTimeBegin(ctb);
        return this;
    }
    public MemberSearchCriteria withCreationTimeEnd(LocalDateTime cte) {
        setCreationTimeEnd(cte);
        return this;
    }
    public MemberSearchCriteria withModificationTimeBegin(LocalDateTime mtb) {
        setModificationTimeBegin(mtb);
        return this;
    }
    public MemberSearchCriteria withModificationTimeEnd(LocalDateTime ctb) {
        setModificationTimeEnd(ctb);
        return this;
    }
}
