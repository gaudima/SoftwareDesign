import java.util.*

interface TokenVisitor {
    fun visit(token: OperationToken)
    fun visit(token: LeftBracketToken)
    fun visit(token: RightBracketToken)
    fun visit(token: NumberToken)
}

class PrintVisitor: TokenVisitor {
    fun visit(tokens: List<Token>) {
        tokens.forEach {
            it.accept(this)
        }
    }

    override fun visit(token: OperationToken) {
        print(token.operation + " ")
    }

    override fun visit(token: LeftBracketToken) {
        print("( ")
    }

    override fun visit(token: RightBracketToken) {
        print(") ")
    }

    override fun visit(token: NumberToken) {
        print(token.number.toString() + " ")
    }
}

class ParserVisitor: TokenVisitor {
    private val stack = Stack<Token>()
    private var result = mutableListOf<Token>()

    fun visit(tokens: List<Token>): List<Token> {
        stack.clear()
        result = mutableListOf()
        tokens.forEach {
            it.accept(this)
        }
        while (!stack.empty()) {
            if(stack.peek() !is OperationToken) {
                throw Error("Wrong bracket balance")
            }
            result.add(stack.pop())
        }
        return result
    }

    override fun visit(token: OperationToken) {
        while(!stack.empty() && stack.peek() is OperationToken) {
            val operation = stack.peek() as OperationToken
            if (token.priority <= operation.priority) {
                result.add(stack.pop())
            } else {
                break
            }
        }
        stack.push(token)
    }

    override fun visit(token: LeftBracketToken) {
        stack.push(token)
    }

    override fun visit(token: RightBracketToken) {
        while(stack.peek() !is LeftBracketToken) {
            result.add(stack.pop())
            if(stack.empty()) {
                throw Error("Wrong bracket balance")
            }
        }
        stack.pop()
    }

    override fun visit(token: NumberToken) {
        result.add(token)
    }
}

class CalcVisitor: TokenVisitor {
    val stack = Stack<Token>()

    fun visit(tokens: List<Token>): Int {
        stack.clear()
        tokens.forEach {
            it.accept(this)
        }
        return (stack.pop() as NumberToken).number
    }

    override fun visit(token: OperationToken) {
        if(stack.size < 2) {
            throw Error("Not enough operands")
        }
        val b = stack.pop() as NumberToken
        val a = stack.pop() as NumberToken
        stack.push(NumberToken(token.apply(a.number, b.number)))
    }

    override fun visit(token: LeftBracketToken) {
        throw Error("There should be no brackets in RPN form")
    }

    override fun visit(token: RightBracketToken) {
        throw Error("There should be no brackets in RPN form")
    }

    override fun visit(token: NumberToken) {
        stack.push(token)
    }
}