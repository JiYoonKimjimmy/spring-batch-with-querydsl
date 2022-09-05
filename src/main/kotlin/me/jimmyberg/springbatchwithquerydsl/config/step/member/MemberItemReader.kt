package me.jimmyberg.springbatchwithquerydsl.config.step.member

import me.jimmyberg.springbatchwithquerydsl.entity.Member
import org.springframework.batch.item.database.JpaPagingItemReader
import javax.persistence.EntityManagerFactory

class MemberItemReader(
    val entityManagerFactory: EntityManagerFactory
): JpaPagingItemReader<Member>() {

    init {
        this.pageSize = 1
        this.setName("memberItemReader")
        this.setQueryString("select m from Member m order by id")
        this.setEntityManagerFactory(entityManagerFactory)
    }

}