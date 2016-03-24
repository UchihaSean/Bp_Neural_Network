import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Jly_wave on 9/26/15.
 */
public class Initial {
    //initial weights & bias
    void addition(float[][] w1,float[][] w2,float[] b1,float[] b2,int inputNumber,int hiddenNumber,int outputNumber){
        for (int i=0;i<inputNumber;i++)
            for (int j=0;j<hiddenNumber;j++)
                w1[i][j]= (float) ((-1+Math.random()*2)/Math.sqrt(inputNumber));
        for (int i=0;i<hiddenNumber;i++)
            for (int j=0;j<outputNumber;j++)
                w2[i][j]= (float) ((-1+Math.random()*2)/Math.sqrt(hiddenNumber));
        for (int i=0;i<hiddenNumber;i++){
            b1[i]=(float)(-1+Math.random());
        }
        for (int i=0;i<outputNumber;i++){
            b2[i]=(float)(-0.2+Math.random()*0.4);
        }
    }
    //initial adjust of weights & bias
    void adjust(float[][] w1,float[][] w2,float[] b1,float[] b2,float[] s,int inputNumber,int hiddenNumber,int outputNumber){
        for (int i=0;i<inputNumber;i++)
            for (int j=0;j<hiddenNumber;j++)
                w1[i][j]= 0;
        for (int i=0;i<hiddenNumber;i++)
            for (int j=0;j<outputNumber;j++)
                w2[i][j]= 0;
        for (int i=0;i<hiddenNumber;i++){
            b1[i]=0;
        }
        for (int i=0;i<outputNumber;i++){
            b2[i]=0;
        }
        for (int i=0;i<outputNumber;i++)
            s[i]=0;
    }
    //initial read of hidden & output layer
    void read(float[] hidden,float[] output,int inputNumber,int hiddenNumber,int outputNumber){
        for (int i=0;i<hiddenNumber;i++){
            hidden[i]=0;
        }
        for (int i=0;i<outputNumber;i++){
            output[i]=0;
        }
    }
    void betterData(float[][] weightKj,float[][] weightJi,float[] biasJ,float[] biasI,int inputNumber,int hiddenNumber,int outputNumber,String fileName) throws FileNotFoundException {
        Scanner inputFile=new Scanner(new File(fileName));
        //input weight
        for (int k=0;k<inputNumber;k++) {
            for (int j = 0; j < hiddenNumber; j++){
                weightKj[k][j]=inputFile.nextFloat();
            }
        }
        for (int j=0;j<hiddenNumber;j++){
            for (int i=0;i<outputNumber;i++){
                weightJi[j][i]=inputFile.nextFloat();
            }
        }
        //input bias
        for (int j=0;j<hiddenNumber;j++)
            biasJ[j]=inputFile.nextFloat();
        for (int i=0;i<outputNumber;i++)
            biasI[i]=inputFile.nextFloat();
        inputFile.close();
    }
}
