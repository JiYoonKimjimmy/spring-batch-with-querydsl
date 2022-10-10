package me.jimmyberg.springbatchwithquerydsl.repository

import me.jimmyberg.springbatchwithquerydsl.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>