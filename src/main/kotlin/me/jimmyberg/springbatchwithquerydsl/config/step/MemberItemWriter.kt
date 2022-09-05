package me.jimmyberg.springbatchwithquerydsl.config.step

import me.jimmyberg.springbatchwithquerydsl.entity.Member
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter

class MemberItemWriter : ItemWriter<Member> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun write(items: List<Member>) {
        items.forEach { logger.info("$it") }
    }

}