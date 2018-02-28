package dao

import model.ToDoList
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport
import javax.sql.DataSource

class ListsDao(dataSource: DataSource): JdbcDaoSupport() {
    init {
        setDataSource(dataSource)
    }

    fun addList(list: ToDoList) {
        jdbcTemplate.update("INSERT INTO TODOLISTS (name) VALUES (?)", arrayOf(list.name))
    }

    fun getLists(): List<ToDoList> =
        jdbcTemplate.query("SELECT * FROM TODOLISTS", BeanPropertyRowMapper(ToDoList::class.java))
}