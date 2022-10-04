package me.jimmyberg.springbatchwithquerydsl.config.step.member

import me.jimmyberg.springbatchwithquerydsl.config.custom.QuerydslJpaPagingItemReader
import me.jimmyberg.springbatchwithquerydsl.entity.Member
import me.jimmyberg.springbatchwithquerydsl.entity.QMember
import javax.persistence.EntityManagerFactory

class MemberQuerydslItemReader(
    entityManagerFactory: EntityManagerFactory,
    chunkSize: Int = 500
) : QuerydslJpaPagingItemReader<Member>() {

    init {
        this.entityManagerFactory = entityManagerFactory
        this.pageSize = chunkSize
        this.queryFunction = { jpaQueryFactory ->
            val member = QMember.member
            jpaQueryFactory
                .selectFrom(member)
                .where(
                    member.name.length().goe(3)
                )
                .orderBy(member.id.asc())
        }
    }

}