package hr.fer.oprpp1.prob1;

import hr.fer.oprpp1.LexerState;

public class Lexer {

    private char[] data;
    private Token token;
    private int currentIndex;

    private LexerState state = LexerState.BASIC;

    public Lexer(String text) {
        if (text == null) {
            throw new NullPointerException("Text ne smije biti null!");
        }

        data = text.toCharArray();
        currentIndex = 0;
    }

    public Token nextToken() {
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException("Dosao do kraja ranije!");
        }

        if (currentIndex == data.length) {
            token = new Token(TokenType.EOF, null);
            return token;
        }

        if (state == LexerState.BASIC) {
            return nextTokenBasic();
        } else {
            return nextTokenExtended();
        }
    }

    // generira i vraća sljedeći token
    // baca LexerException ako dođe do pogreške
    public Token nextTokenBasic() {
        if (skipWhitespaces())
            return token = new Token(TokenType.EOF, null);

        if (Character.isLetter(data[currentIndex]) || isNextEscapedNum()) {
            return token = new Token(TokenType.WORD, getNextWord());
        }

        if (Character.isDigit(data[currentIndex])) {
            try {
                return token = new Token(TokenType.NUMBER, getNextNumber());
            } catch (NumberFormatException e) {
                throw new LexerException("Neispravan broj!");
            }
        }

        if (isSymbol(data[currentIndex])) {
            return token = new Token(TokenType.SYMBOL, getNextSymbol());
        }

        throw new LexerException("Nepoznati znak!");
    }

    public Token nextTokenExtended() {
        if (skipWhitespaces())
            return token = new Token(TokenType.EOF, null);

        if (data[currentIndex] == '#') {
            currentIndex++;
            return token = new Token(TokenType.SYMBOL, '#');
        }

        StringBuilder sb = new StringBuilder();
        while (currentIndex < data.length && data[currentIndex] != '#' && !Character.isWhitespace(data[currentIndex])) {
            sb.append(data[currentIndex++]);
        }

        return token = new Token(TokenType.WORD, sb.toString());
    }

    // vraća zadnji generirani token; može se pozivati
    // više puta; ne pokreće generiranje sljedećeg tokena
    public Token getToken() {
        return token;
    }

    public void setState(LexerState state) {
        if (state == null) {
            throw new NullPointerException("State ne smije biti null!");
        }

        this.state = state;
    }

    private boolean skipWhitespaces() {
        while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }

        return currentIndex == data.length;
    }

    private boolean isSymbol(char c) {
        if (c == '-')
            return true;
        if (c == '.')
            return true;
        if (c == '?')
            return true;
        if (c == '#')
            return true;
        if (c == '#')
            return true;
        if (c == '!')
            return true;
        if (c == ';')
            return true;

        return false;
    }

    private char getNextSymbol() {
        return data[currentIndex++];
    }

    private String getNextWord() {
        StringBuilder sb = new StringBuilder();

        boolean isLetter = Character.isLetter(data[currentIndex]);
        boolean isEscapedNum = isNextEscapedNum();
        boolean isEscapedBackslash = isNextEscapedBackslash();

        while (currentIndex < data.length && (isLetter || isEscapedNum || isEscapedBackslash)) {

            if (isLetter) {
                sb.append(data[currentIndex++]);
            } else if (isEscapedNum) {
                currentIndex++;
                sb.append(data[currentIndex++]);
            } else if (isEscapedBackslash) {
                currentIndex++;
                sb.append(data[currentIndex++]);
            } else {
                throw new LexerException("Nepoznati znak!");
            }

            if (currentIndex >= data.length) {
                break;
            }
            isLetter = Character.isLetter(data[currentIndex]);
            isEscapedNum = isNextEscapedNum();
            isEscapedBackslash = isNextEscapedBackslash();

        }

        return sb.toString();

    }

    private long getNextNumber() {
        StringBuilder sb = new StringBuilder();

        while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
            sb.append(data[currentIndex++]);
        }

        return Long.parseLong(sb.toString());
    }

    private boolean isNextEscapedNum() {
        return (data[currentIndex] == '\\') &&
                (currentIndex + 1 < data.length) &&
                Character.isDigit(data[currentIndex + 1]);
    }

    private boolean isNextEscapedBackslash() {
        return (data[currentIndex] == '\\') &&
                (currentIndex + 1 < data.length) &&
                (data[currentIndex + 1] == '\\');
    }
}
