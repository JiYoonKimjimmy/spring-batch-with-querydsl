package me.jimmyberg.springbatchwithquerydsl.config.step.member

import me.jimmyberg.springbatchwithquerydsl.entity.Member
import org.springframework.batch.item.ItemProcessor

class MemberItemProcessor : ItemProcessor<Member, Member> {

    override fun process(item: Member): Member? =
        if (item.id!! % 2 != 0L) {
            item.apply { this.nickName = "${this.id}_${this.name}_${this.age}" }
        } else {
            null
        }

}