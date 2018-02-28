package config

import dao.ListsDao
import org.springframework.context.annotation.Bean
import javax.sql.DataSource
import org.springframework.jdbc.datasource.DriverManagerDataSource



class JdbcDaoContextConfiguration {
    @Bean fun listsJdbcDao(dataSource: DataSource): ListsDao = ListsDao(dataSource)

    @Bean fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.sqlite.JDBC")
        dataSource.url = "jdbc:sqlite:base.db"
        dataSource.username = ""
        dataSource.password = ""
        return dataSource
    }
}