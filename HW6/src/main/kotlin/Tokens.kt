interface Token {
    fun accept(visitor: TokenVisitor)
    override fun toString(): String
}

class NumberToken(val number: Int): Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = "NumberToken($number)"
}

class LeftBracketToken: Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = "LeftBracketToken"
}

class RightBracketToken: Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = "RightBracketToken"
}

abstract class OperationToken(val operation: Char, val priority: Int): Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = "OperationToken($operation)"

    abstract fun apply(a: Int, b: Int): Int
}

class PlusToken: OperationToken('+', 0) {
    override fun apply(a: Int, b: Int): Int = a + b
}

class MinusToken: OperationToken('-', 0) {
    override fun apply(a: Int, b: Int): Int = a - b
}

class MulToken: OperationToken('*', 1) {
    override fun apply(a: Int, b: Int): Int = a * b
}

class DivToken: OperationToken('/', 1) {
    override fun apply(a: Int, b: Int): Int = a / b
}