package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element {
    private final String value;

    public ElementString(String value) {
        this.value = value;
    }

    @Override
    public String asText() {

        String s = value.substring(1, value.length() - 1);

        s = s.replace("\\", "\\\\");
        s = s.replace("\"", "\\\"");

        s = '"' + s + '"';

        return s;
    }
}
