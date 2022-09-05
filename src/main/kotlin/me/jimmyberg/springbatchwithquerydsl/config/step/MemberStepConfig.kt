package me.jimmyberg.springbatchwithquerydsl.config.step

import me.jimmyberg.springbatchwithquerydsl.entity.Member
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory

@Configuration
class MemberStepConfig(
    val stepBuilderFactory: StepBuilderFactory,
    val entityManagerFactory: EntityManagerFactory
) {

    @Bean
    fun step() =
        stepBuilderFactory
            .get("BATCH_STEP")
            .tasklet(Tasklet { _, _ ->
                println("Hello Spring Batch!")
                RepeatStatus.FINISHED
            })
            .build()

    @Bean
    fun memberStep(): Step =
        stepBuilderFactory
            .get("MEMBER_STEP")
            .chunk<Member, Member>(1)
            .reader(MemberItemReader(entityManagerFactory = entityManagerFactory))
            .processor(MemberItemProcessor())
            .writer(MemberItemWriter())
            .build()

}