
fun main(args: Array<String>) {
    val connection = TwitterSearchConnection(
            "https://api.twitter.com",
            "5R9JuoFHImI9qicK6Ib7TXsbn",
            "pMwloVAq5iLBhVJPLPQZZa78Zisp1WGZFvwWSzRCDNZzoSQDzj")
    val search = TwitterSearch(connection)
    val results = search.searchForHours("#superbowl", 5)
    print("[")
    results.forEachIndexed { index, it ->
        print(it)
        if(index < results.size - 1) {
            print(", ")
        }
    }
    print("]")
}