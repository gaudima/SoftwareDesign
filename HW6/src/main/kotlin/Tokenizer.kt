
interface TokenizerState {
    fun handle(tokenizer: Tokenizer)
}

class StartState(val char: Char): TokenizerState {
    override fun handle(tokenizer: Tokenizer) {
        when(char) {
            in ('0'..'9') -> {
                tokenizer.setState(NumberState(char.toString()))
            }
            '(' -> {
                tokenizer.createToken(LeftBracketToken())
                tokenizer.setState(StartState(tokenizer.next()))
            }
            ')' -> {
                tokenizer.createToken(RightBracketToken())
                tokenizer.setState(StartState(tokenizer.next()))
            }
            '+' -> {
                tokenizer.createToken(PlusToken())
                tokenizer.setState(StartState(tokenizer.next()))
            }
            '-' -> {
                tokenizer.createToken(MinusToken())
                tokenizer.setState(StartState(tokenizer.next()))
            }
            '*' -> {
                tokenizer.createToken(MulToken())
                tokenizer.setState(StartState(tokenizer.next()))
            } '/' -> {
                tokenizer.createToken(DivToken())
                tokenizer.setState(StartState(tokenizer.next()))
            }
            ' ' -> {
                tokenizer.setState(StartState(tokenizer.next()))
            }
            '\u0000' -> {
                tokenizer.setState(EndState())
            }
            else -> {
                tokenizer.setState(ErrorState())
            }
        }
    }
}

class NumberState(var acc: String): TokenizerState {
    override fun handle(tokenizer: Tokenizer) {
        val char = tokenizer.next()
        if(char in ('0'..'9')) {
            acc += char
            return tokenizer.setState(this)
        } else {
            tokenizer.createToken(NumberToken(acc.toInt()))
            return tokenizer.setState(StartState(char))
        }
    }
}

class ErrorState: TokenizerState {
    override fun handle(tokenizer: Tokenizer)  {
        throw Error("Tokenizer error")
    }
}

class EndState: TokenizerState {
    override fun handle(tokenizer: Tokenizer) {}
}

class Tokenizer(private val input: String) {
    private var index: Int = 0
    private val tokens: MutableList<Token> = mutableListOf()

    private var state: TokenizerState = EndState()

    fun setState(state: TokenizerState) {
        this.state = state
        this.state.handle(this)
    }

    fun createToken(token: Token) {
        tokens.add(token)
    }

    fun next(): Char {
        val ret: Char = if(index < input.length) {
            input[index]
        } else {
            '\u0000'
        }
        index++
        return ret
    }

    fun run(): List<Token> {
        setState(StartState(next()))
        return tokens
    }
}