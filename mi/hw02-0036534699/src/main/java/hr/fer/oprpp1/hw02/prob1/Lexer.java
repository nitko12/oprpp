package hr.fer.oprpp1.hw02.prob1;

/*
 * Lexer u drugom djelu zadace.
 */
public class Lexer {

    private char[] data;
    private Token token;
    private int currentIndex;

    private LexerState state = LexerState.BASIC;

    /*
     * Konstruktor koji prima ulaz.
     * 
     * @param text ulaz
     */
    public Lexer(String text) {
        if (text == null) {
            throw new NullPointerException("Text ne smije biti null!");
        }

        data = text.toCharArray();
        currentIndex = 0;
    }

    /*
     * Metoda koja vraca sljedeci token.
     * 
     * @throws LexerException ako je doslo do greske
     * 
     * @return token
     */
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

    /*
     * Metoda koja vraca sljedeci token u basic stanju.
     * 
     * @throws LexerException ako je doslo do greske
     * 
     * @return token
     */
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

    /*
     * Metoda koja vraca sljedeci token u extended stanju.
     * 
     * @throws LexerException ako je doslo do greske
     * 
     * @return token
     */
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

    /*
     * Metoda koja vraca zadnji generirani token.
     * 
     * @return token
     */
    public Token getToken() {
        return token;
    }

    /*
     * Metoda koja postavlja stanje lexera.
     */
    public void setState(LexerState state) {
        if (state == null) {
            throw new NullPointerException("State ne smije biti null!");
        }

        this.state = state;
    }

    /*
     * Metoda koja preskače sve whitespace znakove.
     */
    private boolean skipWhitespaces() {
        while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }

        return currentIndex == data.length;
    }

    /*
     * Metoda koja provjerava je li sljedeći znak simbol.
     */
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

    /*
     * Metoda za dohcat sljedeceg simbola.
     */
    private char getNextSymbol() {
        return data[currentIndex++];
    }

    /*
     * Metoda za dohvat sljedece rijeci.
     * 
     * @throws LexerException ako je doslo do greske
     * 
     * @return String rijec
     */
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

    /*
     * Metoda za dohvat sljedeceg broja.
     * 
     * @throws LexerException ako je doslo do greske
     * 
     * @return long broj
     */
    private long getNextNumber() {
        StringBuilder sb = new StringBuilder();

        while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
            sb.append(data[currentIndex++]);
        }

        return Long.parseLong(sb.toString());
    }

    /*
     * Metoda koja provjerava je li sljedeći znak escapean broj.
     */
    private boolean isNextEscapedNum() {
        return (data[currentIndex] == '\\') &&
                (currentIndex + 1 < data.length) &&
                Character.isDigit(data[currentIndex + 1]);
    }

    /*
     * Metoda koja provjerava je li sljedeći znak escapean backslash.
     */
    private boolean isNextEscapedBackslash() {
        return (data[currentIndex] == '\\') &&
                (currentIndex + 1 < data.length) &&
                (data[currentIndex + 1] == '\\');
    }
}
