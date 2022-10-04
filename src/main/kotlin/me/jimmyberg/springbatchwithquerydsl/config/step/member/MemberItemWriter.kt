package me.jimmyberg.springbatchwithquerydsl.config.step.member

import me.jimmyberg.springbatchwithquerydsl.entity.Member
import org.slf4j.LoggerFactory
import org.springframework.batch.item.database.JpaItemWriter
import javax.persistence.EntityManagerFactory

class MemberItemWriter(
    entityManagerFactory: EntityManagerFactory
) : JpaItemWriter<Member>() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        this.setEntityManagerFactory(entityManagerFactory)
    }

    override fun write(items: List<Member>) {
        items.forEach { logger.info("ITEM : [ $it ]") }
        super.write(items)
    }

}