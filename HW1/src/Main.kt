/**
 * Created by gaudima on 10/18/17.
 */


fun main(args: Array<String>) {
    val cache = LRUCache<String, String>(5)
    cache.put("a", "a")
    cache.put("b", "b")
    cache.put("c", "c")
    cache.put("d", "d")
    cache.put("e", "e")
    cache.put("f", "f")
    assert(cache.get("f") == "f")
    assert(cache.get("e") == "e")
    assert(cache.get("d") == "d")
    assert(cache.get("c") == "c")
    assert(cache.get("b") == "b")
    cache.remove("b")
    cache.remove("c")
    cache.remove("d")
    cache.remove("e")
    cache.remove("f")
}