package hr.fer.oprpp1.custom.scripting.lexer;

public class Lexer {
    private final char[] data;
    private int currentIndex;

    private Token token;

    private LexerStateEnum state = LexerStateEnum.NORMAL;

    public Lexer(char[] data) {
        this.data = data;
    }

    public void setState(LexerStateEnum st) {
        state = st;
    }

    public Token getToken() {
        return token;
    }

    public Token getNextToken() {
        if (token != null && token.getType() == TokenEnum.EOF) {
            throw new LexerException("No more tokens available.");
        }

        if (currentIndex >= data.length) {
            token = new Token(TokenEnum.EOF, null);
            return token;
        }

        if (state == LexerStateEnum.INTAG) {
            return getNextTokenInTag();
        }

        return getNextTokenNormal();
    }

    private Token getNextTokenNormal() {

        if (data[currentIndex] == '{') {
            if (currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
                currentIndex += 2;

                StringBuilder name = new StringBuilder();

                skipWhitespace();

                if (data[currentIndex] == '=') {
                    name.append('=');
                    ++currentIndex;
                } else
                    while (currentIndex < data.length &&
                            !Character.isWhitespace(data[currentIndex]) &&
                            data[currentIndex] != '$' &&
                            data[currentIndex] != '}') {
                        name.append(data[currentIndex]);
                        currentIndex++;
                    }

                token = new Token(TokenEnum.TAGSTART, name.toString());
                return token;
            }
        }

        StringBuilder sb = new StringBuilder();

        while (currentIndex < data.length) {
            char c = data[currentIndex];

            if (c == '\\') {
                if (currentIndex + 1 < data.length && data[currentIndex + 1] == '{') {
                    sb.append(data[currentIndex + 1]);
                    currentIndex += 2;
                    continue;
                }
                if (currentIndex + 1 < data.length && data[currentIndex + 1] == '\\') {
                    sb.append(data[currentIndex + 1]);
                    currentIndex += 2;
                    continue;
                }
                throw new LexerException("Ilegalan escape.");
            }

            if (c == '{') {
                if (currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
                    break;
                }
            }

            sb.append(c);
            currentIndex++;
        }

        token = new Token(TokenEnum.TEXT, sb.toString());
        return token;
    }

    public Token peekNextToken() {
        int lastIndex = currentIndex;

        Token token = getNextToken();

        currentIndex = lastIndex;

        return token;
    }

    private Token getNextTokenInTag() {
        skipWhitespace();

        if (data[currentIndex] == '$') {
            if (currentIndex + 1 < data.length && data[currentIndex + 1] == '}') {
                currentIndex += 2;

                token = new Token(TokenEnum.TAGEND, null);
                return token;
            }
        }

        if (data[currentIndex] == '"') {
            token = new Token(TokenEnum.STRING, getNextString());
            return token;
        }

        if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '-') {
            String next = getNextNumber();

            if (next.contains(".")) {
                token = new Token(TokenEnum.DOUBLE, Double.parseDouble(next));
            } else {
                token = new Token(TokenEnum.INT, Integer.parseInt(next));
            }

            return token;
        }

        if (data[currentIndex] == '@') {
            StringBuilder sb = new StringBuilder();

            sb.append(data[currentIndex++]);

            while (currentIndex < data.length &&
                    (Character.isLetter(data[currentIndex]) ||
                            Character.isDigit(data[currentIndex]))) {
                sb.append(data[currentIndex]);
                currentIndex++;
            }

            token = new Token(TokenEnum.FUNCTION, sb.toString());

            return token;
        }

        if (Character.isLetter(data[currentIndex])) {
            StringBuilder sb = new StringBuilder();

            while (currentIndex < data.length &&
                    (Character.isLetter(data[currentIndex]) ||
                            Character.isDigit(data[currentIndex]) ||
                            data[currentIndex] == '_')) {
                sb.append(data[currentIndex]);
                currentIndex++;
            }

            token = new Token(TokenEnum.VARIABLE, sb.toString());

            return token;
        }

        if (isOperator("" + data[currentIndex])) {
            token = new Token(TokenEnum.OPERATOR, data[currentIndex++]);
            return token;
        }

        throw new LexerException("Nepoznati token.");
    }

    private void skipWhitespace() {
        while (currentIndex < data.length &&
                Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^");
    }

    private String getNextString() {
        StringBuilder sb = new StringBuilder();

        sb.append(data[currentIndex++]); // must be "

        while (currentIndex < data.length) {
            char c = data[currentIndex];

            if (c == '\\') {
                if (currentIndex + 1 < data.length && data[currentIndex + 1] == '\\') {
                    sb.append(data[currentIndex + 1]);
                    currentIndex += 2;
                    continue;
                }
                if (currentIndex + 1 < data.length && data[currentIndex + 1] == '"') {
                    sb.append(data[currentIndex + 1]);
                    currentIndex += 2;
                    continue;
                }
                throw new LexerException("Ilegalan escape.");
            }

            if (c == '"') {
                sb.append(c);
                currentIndex++;

                break;
            }

            sb.append(c);
            currentIndex++;
        }

        return sb.toString();
    }

    private String getNextNumber() {
        StringBuilder sb = new StringBuilder();

        if (data[currentIndex] == '-') {
            sb.append(data[currentIndex++]);
        }

        while (currentIndex < data.length &&
                Character.isDigit(data[currentIndex])) {
            sb.append(data[currentIndex++]);
        }

        if (currentIndex < data.length && data[currentIndex] == '.') {
            sb.append(data[currentIndex++]);

            while (currentIndex < data.length &&
                    Character.isDigit(data[currentIndex])) {
                sb.append(data[currentIndex++]);
            }
        }

        return sb.toString();
    }
}
