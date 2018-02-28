package dao

import model.ToDo
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport
import javax.sql.DataSource

class ToDoListDao(dataSource: DataSource): JdbcDaoSupport() {
    init {
        setDataSource(dataSource)
    }

    fun addToDo(toDo: ToDo) {
        jdbcTemplate.update("INSERT INTO TODOS (listId, done, text) VALUES (?, ?, ?)",
                arrayOf(toDo.listId, toDo.done, toDo.text))
    }

    fun getToDosByListId(id: Long): List<ToDo> =
            jdbcTemplate.query("SELECT * FROM TODOS WHERE listId = $id", BeanPropertyRowMapper(ToDo::class.java))
}