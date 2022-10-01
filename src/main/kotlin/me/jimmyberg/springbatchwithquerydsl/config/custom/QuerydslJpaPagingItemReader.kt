package me.jimmyberg.springbatchwithquerydsl.config.custom

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.batch.item.database.AbstractPagingItemReader
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.util.ClassUtils
import org.springframework.util.CollectionUtils
import java.util.concurrent.CopyOnWriteArrayList
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction

open class QuerydslJpaPagingItemReader<T> : AbstractPagingItemReader<T>() {

    lateinit var entityManagerFactory: EntityManagerFactory
    lateinit var queryFunction: (JPAQueryFactory) -> JPAQuery<T>
    private val transacted: Boolean = true

    private lateinit var entityManager: EntityManager
    private val jpaPropertyMap: Map<String, Any> = mapOf()

    init {
        super.setName(ClassUtils.getShortName(QuerydslJpaPagingItemReader::class.java))
    }

    override fun doOpen() {
        super.doOpen()
        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap)
            ?: throw DataAccessResourceFailureException("Unable to obtain an EntityManager")
    }

    override fun doReadPage(): Unit =
        createQuery()
            .offset(page.toLong() * pageSize.toLong())
            .limit(pageSize.toLong())
            .let {
                initResults()
                fetchQuery(it, getTxOfNull())
            }

    override fun doJumpToPage(itemIndex: Int) {}

    override fun doClose() {
        entityManager.close()
    }

    private fun getTxOfNull(): EntityTransaction? =
        if (transacted) {
            val tx = entityManager.transaction
            tx.begin()
            entityManager.flush()
            entityManager.clear()
            tx
        } else {
            null
        }

    private fun createQuery() = queryFunction(JPAQueryFactory(entityManager))

    private fun initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = CopyOnWriteArrayList()
        } else {
            results.clear()
        }
    }

    private fun fetchQuery(query: JPAQuery<T>, tx: EntityTransaction?) =
        if (transacted) {
            results.addAll(query.fetch())
            tx?.commit()
        } else {
            val queryResult = query.fetch()
            queryResult.forEach {
                entityManager.detach(it)
                results.add(it)
            }
        }

}