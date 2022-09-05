package me.jimmyberg.springbatchwithquerydsl.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "MEMBERS")
data class Member(

    @Id
    @GeneratedValue
    val id: Long? = null,
    val name: String,
    val age: Long,
    var nickName: String? = null

)