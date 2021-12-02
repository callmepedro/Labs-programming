import static java.lang.Math.*;


public class Lab1 {

    public static void main(String[] args) {

        short[] d = new short[19];
        double[] x = new double[11];
        double[][] heh = new double[10][11];


        for (short i = 21; i >= 3; i--) {
            d[21 - i] = i;
        }

        for (int i = 0; i < 11; i++) {
            x[i] = -13.0 + random() * 21.0;
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 11; j++) {
                if (d[i] == 7) {
                    heh[i][j] = pow((0.5 / pow(pow(x[j], 1d / 3d), 1d / 3d)), tan(pow(x[j], 1d / 3d)));
                }
                else if (d[i] == 5 || d[i] == 11 || d[i] == 13 || d[i] == 19 || d[i] == 21) {
                    heh[i][j] = sin(sin(pow(4 / x[j], x[j])));
                }
                else {
                    heh[i][j] = pow(pow(0.5 * sin(pow(x[j], (PI - x[j]) / x[j])), 2), 1d / 3d);
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            System.out.println();
            for (int j = 0; j < 11; j++) {
                System.out.printf("% .4f", heh[i][j]);
                System.out.print(" ");
            }
        }
    }
}
