package me.jimmyberg.springbatchwithquerydsl.config

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan("me.jimmyberg.springbatchwithquerydsl.entity")
@ComponentScan("me.jimmyberg.springbatchwithquerydsl.config")
@EnableJpaRepositories("me.jimmyberg.springbatchwithquerydsl.repository")
@EnableAutoConfiguration
@EnableBatchProcessing
@Configuration
class ApplicationTestConfig {

    @Bean
    fun jobLauncherTestUtils(): JobLauncherTestUtils = JobLauncherTestUtils()

}