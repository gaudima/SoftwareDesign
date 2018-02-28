fun main(args: Array<String>) {
    if (args.size < 1) {
        println("No expression specified")
    }
    val tokenizer = Tokenizer(args[0])
    val printVisitor = PrintVisitor()
    val parserVisitor = ParserVisitor()
    val calcVisitor = CalcVisitor()
    val infix = tokenizer.run()
    val rpn = parserVisitor.visit(infix)
    try {
        print("RPN: ")
        printVisitor.visit(rpn)
        println()
        println("Result: " + calcVisitor.visit(rpn))
    } catch (e: Error) {
        println(e.message)
    }
}