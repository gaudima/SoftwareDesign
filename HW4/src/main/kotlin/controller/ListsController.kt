package controller

import dao.ListsDao
import model.ToDoList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class ListsController {
    @Autowired
    private lateinit var listsDao: ListsDao

    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun getLists(map: ModelMap): String {
        prepareModelMap(map, listsDao.getLists())
        return "index"
    }

    private fun prepareModelMap(map: ModelMap, lists: List<ToDoList>) {
        map.addAttribute("lists", lists)
    }
}