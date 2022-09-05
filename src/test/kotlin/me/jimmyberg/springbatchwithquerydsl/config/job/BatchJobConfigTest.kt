package me.jimmyberg.springbatchwithquerydsl.config.job

import me.jimmyberg.springbatchwithquerydsl.config.ApplicationTestConfig
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.batch.runtime.BatchStatus

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ApplicationTestConfig::class])
class BatchJobConfigTest {

    @Autowired
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Test
    fun test() {
        println("Hello World")
        val jobExecution = jobLauncherTestUtils.launchJob()
        val stepException = jobExecution.stepExecutions.first()

        assertEquals(jobExecution.status.name, BatchStatus.COMPLETED.name)
        assertEquals(stepException.readCount, stepException.writeCount)
    }

}