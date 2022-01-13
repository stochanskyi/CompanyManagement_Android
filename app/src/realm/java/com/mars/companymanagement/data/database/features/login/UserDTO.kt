package com.mars.companymanagement.data.database.features.login

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class UserDTO : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var username: String = ""
    var email: String = ""
    var password: String = ""

    var accessLevel: AccessLevelDTO? = null

}

open class AccessLevelDTO : RealmObject() {
    var id: Int = 0

    var name: String = ""
}