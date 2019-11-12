package db;

class Pair {
    private int[] pair;

    Pair(int num1, int num2) {
        pair = new int[2];
        pair[0] = num1;
        pair[1] = num2;
    }

    int[] get() {
        return pair;
    }
    int first() {
        return pair[0];
    }
    int second() {
        return pair[1];
    }
}
