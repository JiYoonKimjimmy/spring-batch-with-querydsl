package me.jimmyberg.springbatchwithquerydsl.config.step

import me.jimmyberg.springbatchwithquerydsl.config.step.member.MemberItemProcessor
import me.jimmyberg.springbatchwithquerydsl.config.step.member.MemberItemWriter
import me.jimmyberg.springbatchwithquerydsl.config.step.member.MemberQuerydslItemReader
import me.jimmyberg.springbatchwithquerydsl.entity.Member
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory

@Configuration
class BatchStepConfig(
    val stepBuilderFactory: StepBuilderFactory,
    val entityManagerFactory: EntityManagerFactory
) {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val chunkSize = 100

    @Bean
    fun simpleStep() =
        stepBuilderFactory
            .get("BATCH_STEP")
            .tasklet { _, _ ->
                logger.info("Hello Spring Batch!")
                RepeatStatus.FINISHED
            }
            .build()

    @Bean
    fun memberStep(): Step =
        stepBuilderFactory
            .get("MEMBER_STEP")
            .chunk<Member, Member>(chunkSize)
            .reader(MemberQuerydslItemReader(entityManagerFactory = entityManagerFactory, chunkSize = chunkSize))
            .processor(MemberItemProcessor())
            .writer(MemberItemWriter(entityManagerFactory = entityManagerFactory))
            .build()

}