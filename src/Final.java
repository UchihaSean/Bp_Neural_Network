import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Jly_wave on 10/8/15.
 */
public class Final {
    public static void main(String[] args) throws FileNotFoundException {
        int inputNumber=784,hiddenNumber=74,outputNumber=10;
        float[][] weightKj=new float[inputNumber][hiddenNumber];
        float[][] weightJi=new float[hiddenNumber][outputNumber];
        float[] biasJ=new float[hiddenNumber];
        float[] biasI=new float[outputNumber];
        int[] input=new int[inputNumber];
        float[] hidden=new float[hiddenNumber];
        float[] output=new float[outputNumber];
        int test,trueAns=0,testNum=0;
        PrintWriter save = new PrintWriter("13302010038.txt");
        Scanner inputFile=new Scanner(new File("TestSave.txt"));
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
        //test

        Scanner inputFileName=new Scanner(System.in);
        String fileName=inputFileName.next();
        int fileNumber=inputFileName.nextInt();
        inputFileName.close();
        for (test=1;test<fileNumber+1;test++){
            Scanner inputTest= new Scanner(new File(fileName+test+".txt"));
            for (int i=0;i<inputNumber;i++){
                input[i]=inputTest.nextInt();
                if (input[i]>0) input[i]=1;
            }
            inputTest.close();
            for (int j=0;j<hiddenNumber;j++) {
                for (int k = 0; k < inputNumber; k++) {
                    hidden[j] += input[k] * weightKj[k][j];
                }
                hidden[j]=sigm(hidden[j]+biasJ[j]);
            }
            //calc output layer
            for (int i=0;i<outputNumber;i++){
                for (int j=0;j<hiddenNumber;j++){
                    output[i]+=hidden[j]*weightJi[j][i];
                }
                output[i]=sigm(output[i]+biasI[i]);
            }
            float max=0,outputTotal=0;
            int ans=-1;
            for (int i=0;i<outputNumber;i++){
                if (output[i]>max){
                    max=output[i];
                    ans=i;
                }
            }
            save.println(ans);
        }
        save.close();

    }
    static float sigm(float x){
        return (float)(1/(1+Math.exp(-x)));
    }
}
