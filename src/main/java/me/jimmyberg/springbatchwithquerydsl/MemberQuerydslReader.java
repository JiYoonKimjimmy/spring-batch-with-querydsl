package me.jimmyberg.springbatchwithquerydsl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.jimmyberg.springbatchwithquerydsl.config.custom.QuerydslJpaPagingItemReader;
import me.jimmyberg.springbatchwithquerydsl.entity.Member;
import me.jimmyberg.springbatchwithquerydsl.entity.QMember;

import javax.persistence.EntityManagerFactory;

public class MemberQuerydslReader extends QuerydslJpaPagingItemReader<Member> {

    public MemberQuerydslReader(EntityManagerFactory entityManagerFactory) {
        this.setEntityManagerFactory(entityManagerFactory);
        this.setPageSize(1);
        this.setQueryFunction(this::generateSQL);
    }

    private JPAQuery<Member> generateSQL(JPAQueryFactory jpaQueryFactory) {
        QMember member = QMember.member;
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.name.length().goe(3))
                .orderBy(member.id.desc());
    }

}
