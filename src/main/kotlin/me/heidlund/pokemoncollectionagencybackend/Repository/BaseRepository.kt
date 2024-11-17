package me.heidlund.pokemoncollectionagencybackend.Repository

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * @author Tobias Heidlund
 */
abstract class BaseRepository {
    protected var connection: Connection? = null

    init {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
        try {
            connect()
        } catch (e: SQLException) {
            if (e.localizedMessage == "Unknown database 'invoicedb'") {
                createDatabase()
            } else {
                throw RuntimeException(e)
            }
        }
    }

    private fun createDatabase() {
        try {
            val tempConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "")
            tempConnection.prepareCall("Create database invoicedb").execute()
            tempConnection.close()
            connect()
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    @Throws(SQLException::class)
    private fun connect() {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "")
    }
}