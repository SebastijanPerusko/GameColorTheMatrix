package sample;
import java.util.Random;

public class ArrayRandom {
    int steviloX;
    int steviloY;
    int k;

    public ArrayRandom(int steviloX, int steviloY, int k) {
        this.steviloX = steviloX;
        this.steviloY = steviloY;
        this.k = k;
    }

    public int[][] Array() {
        int[][] barve = new int[steviloX][steviloY];
        for (int i = 0; i < steviloX; i++) {
            for (int j = 0; j < steviloY; j++) {
                if (i == 0 || j == 0 || i == steviloX - 1 || j == steviloY - 1) {
                    barve[i][j] = -1;
                } else {
                    Random rand = new Random();
                    barve[i][j] = rand.nextInt(k);
                }
            }
        }
        return barve;
    }
}
