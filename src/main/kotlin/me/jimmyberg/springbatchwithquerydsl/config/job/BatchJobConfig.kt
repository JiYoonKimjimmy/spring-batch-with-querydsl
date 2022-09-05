package me.jimmyberg.springbatchwithquerydsl.config.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableBatchProcessing
@Configuration
class BatchJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val memberStep: Step
) {

    @Bean
    fun job() =
        jobBuilderFactory
            .get("BATCH_JOB")
            .start(memberStep)
            .build()

}