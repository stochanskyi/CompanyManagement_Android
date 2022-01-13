package com.mars.companymanagement.data.database.common

import io.realm.Realm
import io.realm.RealmConfiguration
import kotlin.reflect.KProperty

class RealmProvider(
    private val config: RealmConfiguration
) {

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): Realm {
        return Realm.getInstance(config)
    }

}

fun realm(config: RealmConfiguration) = RealmProvider(config)