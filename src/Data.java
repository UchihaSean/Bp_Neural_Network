import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Jly_wave on 9/26/15.
 */
public class Data {
    void save(float[][] weightKj,float[][] weightJi,float[] biasJ,float[] biasI,int inputNumber,int hiddenNumber,int outputNumber) throws FileNotFoundException {
        PrintWriter save = new PrintWriter("TestSave.txt");
        for (int k = 0; k < inputNumber; k++) {
            for (int j = 0; j < hiddenNumber; j++) {
                save.print(weightKj[k][j] + " ");
            }
            save.println();
        }
        save.println();
        for (int j = 0; j < hiddenNumber; j++) {
            for (int i = 0; i < outputNumber; i++) {
                save.print(weightJi[j][i] + " ");
            }
            save.println();
        }
        save.println();
        for (int j = 0; j < hiddenNumber; j++)
            save.print(biasJ[j] + " ");
        save.println();
        save.println();
        for (int i = 0; i < outputNumber; i++)
            save.print(biasI[i] + " ");
        save.close();
    }
}
