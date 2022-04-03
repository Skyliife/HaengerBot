package tictactoe;

public enum Position {
    ONE("1"),
    TOW("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");


    Position(String number) {
        this.number = number;
    }

    private String number;

    public String getNumber() {
        return number;
    }
}
