package com.firek.database

import org.jetbrains.exposed.sql.Database

object DatabaseConfig {
    fun init() {
        Database.connect(
            url = "jdbc:mysql://localhost:8889/Konkurs",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = "root"

        )
    }
}