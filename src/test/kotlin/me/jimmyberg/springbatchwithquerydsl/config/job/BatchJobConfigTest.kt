package me.jimmyberg.springbatchwithquerydsl.config.job

import me.jimmyberg.springbatchwithquerydsl.config.ApplicationTestConfig
import me.jimmyberg.springbatchwithquerydsl.config.custom.QuerydslJpaPagingItemReader
import me.jimmyberg.springbatchwithquerydsl.entity.Member
import me.jimmyberg.springbatchwithquerydsl.entity.QMember
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.batch.runtime.BatchStatus
import javax.persistence.EntityManagerFactory

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ApplicationTestConfig::class])
class BatchJobConfigTest {

    @Autowired
    lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    lateinit var emf: EntityManagerFactory

    @Test
    fun executeBatchJobTest() {
        val jobExecution = jobLauncherTestUtils.launchJob()
        val stepException = jobExecution.stepExecutions.first()

        assertEquals(jobExecution.status.name, BatchStatus.COMPLETED.name)
        assertEquals(stepException.writeCount, 2)
    }

    @Test
    fun Reader_정상_반환_테스트() {
        val itemReader = QuerydslJpaPagingItemReader<Member>()
            .apply {
                entityManagerFactory = emf
                queryFunction = {
                    val member = QMember.member
                    it
                        .selectFrom(member)
                        .where(member.age.goe(30))
                }
            }

        itemReader.open(ExecutionContext())
        val item = itemReader.read()

        assertEquals(item?.name, "JIM")
    }

}