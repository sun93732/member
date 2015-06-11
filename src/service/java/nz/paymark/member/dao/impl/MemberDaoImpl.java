package nz.paymark.member.dao.impl;

import static nz.paymark.shared.validation.util.ExceptionThrower.throwNotFoundIf;
import static nz.paymark.shared.validation.util.ExceptionThrower.throwNullArgumentFor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nz.paymark.logging.LoggerFactory;
import nz.paymark.member.dao.MemberDao;
import nz.paymark.member.model.Member;
import nz.paymark.member.model.MemberSearchCriteria;
//import nz.paymark.member.model.Member_;
import nz.paymark.member.model.Member_;
import nz.paymark.member.model.enumerator.MemberStatus;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberDaoImpl implements MemberDao {

	public static Logger logger = LoggerFactory.getLogger();
	
	@PersistenceContext private EntityManager em;

	@Override
	@Transactional(readOnly=false)
	public Member createMember(Member Member) {
		throwNullArgumentFor(Member);		
		
		return em.merge(Member);
	}
	
	@Override
	@Transactional(readOnly=false)
	public Member updateMember(Member Member) {
		throwNullArgumentFor(Member);
		return em.merge(Member);
	}
	
	@Override
	@Transactional(readOnly=true)
	public boolean touch() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Member.class)));
		return em.createQuery(cq).getSingleResult() > -1; 
	}
	

	@Override
	@Transactional(readOnly=true)
	public Optional<Member> findMemberById(String MemberId) {
		throwNullArgumentFor(MemberId);
		Member member = em.find(Member.class, MemberId);
		if(null == member){
			return Optional.empty();
		}
		return Optional.of(member);
	}


	@Override
	@Transactional(readOnly=false)
	public void deleteMember(String MemberId) {
		throwNullArgumentFor(MemberId);
        Member Member = em.find(Member.class, MemberId);
        throwNotFoundIf(Member == null);
        em.remove(Member);  		
	}

	@Override
	public List<Member> searchMembers(MemberSearchCriteria searchCriteria) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder
				.createQuery(Member.class);

		Root<Member> memberRoot = criteriaQuery.from(Member.class);

		List<Predicate> conditions = new ArrayList<>();
		String role  = searchCriteria.getRole();
		if (role != null && role.length() != 0) {
			conditions.add(criteriaBuilder.equal(memberRoot.get(Member_.role),
					role));
		}
		
		MemberStatus status = searchCriteria.getStatus();
		if (status != null) {
			conditions.add(criteriaBuilder.equal(memberRoot.get(Member_.status),
					status));
		}
		
		String organisationId = searchCriteria.getOrganisationId();
		if (organisationId != null && organisationId.length() != 0) {
			conditions.add(criteriaBuilder.equal(memberRoot.get(Member_.organisationId),
					organisationId));
		}
		
		String userId = searchCriteria.getUserId();
		if (userId != null && userId.length() != 0) {
			conditions.add(criteriaBuilder.equal(memberRoot.get(Member_.userId),
					userId));
		}
		criteriaQuery.where(conditions.toArray(new Predicate[] {}));

		TypedQuery<Member> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}
}
