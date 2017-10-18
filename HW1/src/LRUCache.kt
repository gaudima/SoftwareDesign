import java.util.*
import kotlin.collections.HashMap

/**
 * Created by gaudima on 10/18/17.
 */

class LRUCache<K, V>(private val size: Int) {
    private class DLLNode<V, K>(var value: V, var key: K, var prev: DLLNode<V, K>?, var next: DLLNode<V, K>?)
    private var actualSize = 0
    private var listHead: DLLNode<V, K>? = null
    private var listTail: DLLNode<V, K>? = null
    private val map = HashMap<K, DLLNode<V, K>?>()

    private fun addNode(node: DLLNode<V, K>) {
        if(actualSize == 0) {
            listHead = node
            listTail = node
        } else {
            node.next = listHead
            listHead?.prev = node
            listHead = node
        }
        actualSize++
    }

    private fun moveToHead(node: DLLNode<V, K>?) {
        if(node != null && node != listHead) {
            if(node == listTail) {
                listTail = node.prev
            }
            node.prev?.next = node.next
            node.next?.prev = node.prev
            node.prev = null
            node.next = listHead
            listHead?.prev = node
            listHead = node
        }
    }

    fun put(key: K, value: V) {
        if(map.containsKey(key)) {
            val node = map[key]
            moveToHead(node)
            listHead?.value = value
            listHead?.key = key
        } else {
            if(actualSize < size) {
                val node = DLLNode(value, key, null, null)
                addNode(node)
                map.put(key, listHead)
            } else {
                moveToHead(listTail)
                map.remove(listHead?.key)
                map.put(key, listHead)
                listHead?.value = value
                listHead?.key = key
            }
        }
        assert(map.containsKey(key))
        assert(listHead?.value == value)
        assert(actualSize <= size)
    }

    fun get(key: K): V? {
        if(!map.containsKey(key)) {
            return null
        } else {
            val node = map[key]
            moveToHead(node)
            assert(actualSize <= size)
            assert(map[key] == listHead)
            return listHead?.value
        }
    }

    fun remove(key: K) {
        if(map.containsKey(key)) {
            val node = map[key]
            map.remove(key)
            moveToHead(node)
            listHead?.next?.prev = null
            if (listHead == listTail) {
                listTail = null
            }
            listHead = listHead?.next
            actualSize--
        }
        assert(!map.containsKey(key))
    }
}