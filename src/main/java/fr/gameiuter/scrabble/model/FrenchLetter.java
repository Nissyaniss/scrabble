package fr.gameiuter.scrabble.model;

public enum FrenchLetter {
    A('a', 1, 9),
    B('b', 3, 2),
    C('c', 3, 2),
    D('d', 2, 3),
    E('e', 1, 15),
    F('f', 4, 2),
    G('g', 2, 2),
    H('h', 4, 2),
    I('i', 1, 8),
    J('j', 8, 1),
    K('k', 10, 1),
    L('l', 1, 5),
    M('m', 2, 3),
    N('n', 1, 6),
    O('o', 1, 6),
    P('p', 3, 2),
    Q('q', 8, 1),
    R('r', 1, 6),
    S('s', 1, 6),
    T('t', 1, 6),
    U('u', 1, 6),
    V('v', 4, 2),
    W('w', 10, 1),
    X('x', 10, 1),
    Y('y', 10, 1),
    Z('z', 10, 1),
    BLANK('*', 0, 2);

    private final char letter;
    private final int score;
    private final int occurrences;

    FrenchLetter(char letter, int score, int occurrences) {
        this.letter = letter;
        this.score = score;
        this.occurrences = occurrences;
    }

    public char letter() {
        return this.letter;
    }

    public int score() {
        return this.score;
    }

    public int occurrences() {
        return this.occurrences;
    }
}
