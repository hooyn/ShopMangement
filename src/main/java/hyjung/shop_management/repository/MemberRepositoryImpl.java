package hyjung.shop_management.repository;

import hyjung.shop_management.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final EntityManager em;


    @Override
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public Member findByUserId(String userId) {
        try{
            return em.createQuery("select m from Member m where m.userId =: userId", Member.class)
                    .setParameter("userId",userId)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
