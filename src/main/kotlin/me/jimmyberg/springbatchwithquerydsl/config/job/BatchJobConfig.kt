package me.jimmyberg.springbatchwithquerydsl.config.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@EnableBatchProcessing
@Configuration
class BatchJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val memberStep: Step
) {

    @Bean
    fun batchJob() =
        jobBuilderFactory
            .get("BATCH_JOB_${LocalDate.now().format(DateTimeFormatter.ISO_DATE)}")
            .start(memberStep)
            .build()

}