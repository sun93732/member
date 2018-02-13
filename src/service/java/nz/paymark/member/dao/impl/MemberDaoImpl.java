package nz.paymark.member.dao.impl;

import static nz.paymark.shared.models.validation.util.GeneralExceptionThrower.throwNullArgumentFor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nz.paymark.web.shared.exception.RecordNotFoundException;
import nz.paymark.logging.LoggerFactory;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
import nz.paymark.member.model.Member_;
import nz.paymark.member.model.enumerator.MemberRole;
import nz.paymark.shared.models.validation.SimpleValidator;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberDaoImpl implements MemberDao {
	
	@Resource
    private SimpleValidator validator;

	public static Logger logger = LoggerFactory.getLogger();
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
    public boolean touch() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Member.class)));
        return em.createQuery(cq).getSingleResult() > -1;
    }
	
	@Override
    public void validate(Member member) {
        validator.validate(member);
    }

	@Override
	@Transactional(readOnly=false)
	public Member createMember(Member member) {
		throwNullArgumentFor(member);		
		LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        member.setCreationTime(now);
        member.setModificationTime(now);
        validate(member);
        return em.merge(member);
	}
	
	@Override
	@Transactional(readOnly=false)
	public Member updateMember(Member member) {
		throwNullArgumentFor(member);
		validate(member);
		return em.merge(member);
	}

	@Override
	@Transactional(readOnly=true)
	public Member getMember(String id) {
		throwNullArgumentFor(id);
		return em.find(Member.class, id);
	}


	@Override
	@Transactional(readOnly=false)
	public void deleteMember(String id) {
		throwNullArgumentFor(id);
        Member member = em.find(Member.class, id);
        if (member == null) {
            throw new RecordNotFoundException();
        }
        em.remove(member);  		
	}

	@Override
	public List<Member> searchMembersByCriteria(MemberSearchCriteria searchCriteria) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder
				.createQuery(Member.class);

		Root<Member> memberRoot = criteriaQuery.from(Member.class);

		List<Predicate> conditions = new ArrayList<>();
		
		if (null != searchCriteria) {
			String id = searchCriteria.getId();
            if (id != null && id.length() != 0) {
                conditions.add(criteriaBuilder.equal(
                        memberRoot.get(Member_.id), id));
            }
            
            String userId = searchCriteria.getUserId();
            if(userId != null && userId.length() != 0){
            	conditions.add(criteriaBuilder.equal(
                        memberRoot.get(Member_.userId), userId));
            }
            
            String organisationId = searchCriteria.getOrganisationId();
            if(organisationId != null && organisationId.length() != 0){
            	conditions.add(criteriaBuilder.equal(
                        memberRoot.get(Member_.organisationId), organisationId));
            }
            
            MemberRole role  = searchCriteria.getRole();
			if (role != null) {
				conditions.add(criteriaBuilder.equal(memberRoot.get(Member_.role),
						role));
			}
			
			LocalDateTime cTimeBegin = searchCriteria.getCreationTimeBegin();
	        LocalDateTime cTimeEnd = searchCriteria.getCreationTimeEnd();
	
	        if (cTimeBegin != null && cTimeEnd == null) {
	            conditions.add(criteriaBuilder.greaterThanOrEqualTo(
	            		memberRoot.get(Member_.creationTime), cTimeBegin));
	        } else if (cTimeBegin == null && cTimeEnd != null) {
	            conditions.add(criteriaBuilder.lessThanOrEqualTo(
	            		memberRoot.get(Member_.creationTime), cTimeEnd));
	        } else if (cTimeBegin != null && cTimeEnd != null) {
	            conditions.add(criteriaBuilder.between(
	            		memberRoot.get(Member_.creationTime), cTimeBegin,
	                    cTimeEnd));
	        }
	
	        LocalDateTime mTimeBegin = searchCriteria
	                .getModificationTimeBegin();
	        LocalDateTime mTimeEnd = searchCriteria.getModificationTimeEnd();
	
	        if (mTimeBegin != null && mTimeEnd == null) {
	            conditions.add(criteriaBuilder.greaterThanOrEqualTo(
	            		memberRoot.get(Member_.modificationTime), mTimeBegin));
	        } else if (mTimeBegin == null && mTimeEnd != null) {
	            conditions.add(criteriaBuilder.lessThanOrEqualTo(
	            		memberRoot.get(Member_.modificationTime), mTimeEnd));
	        } else if (mTimeBegin != null && mTimeEnd != null) {
	            conditions.add(criteriaBuilder.between(
	            		memberRoot.get(Member_.modificationTime), mTimeBegin,
	                    mTimeEnd));
	        }
		}
		criteriaQuery.where(conditions.toArray(new Predicate[] {}));

		TypedQuery<Member> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	@Override
    public List<Member> searchMembers(List<String> ids) {

        if (ids == null || ids.size() == 0) {
            return Collections.emptyList();
        }

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteriaQuery = criteriaBuilder
                .createQuery(Member.class);

        Root<Member> member = criteriaQuery
                .from(Member.class);
        criteriaQuery.select(member).where(
                member.get(Member_.id).in(ids));

        TypedQuery<Member> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
