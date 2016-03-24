import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static float accuracy=0,testScale=(float)1.01;
    public static void main(String[] args) throws FileNotFoundException {

//        input    o o o o o o o o   k          784
//                      W[kj]
//        hidden      o o o o o o    j Biasj     74
//                         W[ji]
//        output         o o o o     i Biasi     10

        //parameter
        float rate=(float)0.05;
        int section=5,length=5000,trainNumber=1000;
        int leftBound=1,rightBound=1001;
        int inputNumber=784,hiddenNumber=74,outputNumber=10;

        //input
        int[] input=new int[inputNumber];
        float[] hidden=new float[hiddenNumber];
        float[] output=new float[outputNumber];
        float[] standard=new float[outputNumber];
        float[][] weightKj=new float[inputNumber][hiddenNumber];
        float[][] weightJi=new float[hiddenNumber][outputNumber];
        float[] biasJ=new float[hiddenNumber];
        float[] biasI=new float[outputNumber];
        float[][] adjustWeightKj=new float[inputNumber][hiddenNumber];
        float[][] adjustWeightJi=new float[hiddenNumber][outputNumber];
        float[] adjustBiasJ=new float[hiddenNumber];
        float[] adjustBiasI=new float[outputNumber];
        float[] adjustSoft=new float[outputNumber];
        String BetterFileName="TestSave96_5_6.txt";
        //initial all
        Initial initial=new Initial();
        initial.addition(weightKj, weightJi, biasJ, biasI,inputNumber,hiddenNumber,outputNumber);
        initial.adjust(adjustWeightKj, adjustWeightJi, adjustBiasJ, adjustBiasI,adjustSoft,inputNumber,hiddenNumber,outputNumber);


        //initial betterData
//        initial.betterData(weightKj,weightJi,biasJ,biasI,inputNumber,hiddenNumber,outputNumber,BetterFileName);

        int test,outputNum,trainNum=0;
        Scanner inputFile;
        Data data=new Data();
        Renew renew=new Renew();
        for(int trainStart=0;trainStart<trainNumber;trainStart++) {
            for (test = 1; test < 3000; test++) {
                if (test>=leftBound && test<rightBound) continue;
                //read input
                inputFile = new Scanner(new File("newTestSet/" + test + ".txt"));
                for (int i = 0; i < inputNumber; i++) {
                    input[i] = inputFile.nextInt();
                    if (input[i] > 0) input[i] = 1;
                }
                outputNum = inputFile.nextInt();
                inputFile.close();
                for (int i = 0; i < outputNumber; i++) {
                    if (i == outputNum)
                        standard[i] = 1;
                    else
                        standard[i] = 0;
                }
                initial.read(hidden, output,inputNumber,hiddenNumber,outputNumber);

                //calc hidden layer
                for (int j = 0; j < hiddenNumber; j++) {
                    for (int k = 0; k < inputNumber; k++) {
                        hidden[j] += input[k] * weightKj[k][j];
                    }
                    hidden[j] = sigm(hidden[j] + biasJ[j]);
                }

                //calc output layer
                for (int i = 0; i < outputNumber; i++) {
                    for (int j = 0; j < hiddenNumber; j++) {
                        output[i] += hidden[j] * weightJi[j][i];
                    }
                    output[i] = sigm(output[i] + biasI[i]);
                }

                float outputTotal=0;

                //adjust weight
                for (int j = 0; j < hiddenNumber; j++) {
                    for (int i = 0; i < outputNumber; i++) {
                        adjustWeightJi[j][i] += rate * (standard[i] - output[i]) * output[i] * (1 - output[i]) * hidden[j];
                    }
                }
                for (int k = 0; k < inputNumber; k++) {
                    for (int j = 0; j < hiddenNumber; j++) {
                        outputTotal = 0;
                        for (int i = 0; i < outputNumber; i++) {
                            outputTotal += (standard[i] - output[i]) * output[i] * (1 - output[i]) * weightJi[j][i];
                        }
                        adjustWeightKj[k][j] += rate * hidden[j] * (1 - hidden[j]) * input[k] * outputTotal;
                    }
                }

                //adjust bias
                for (int i = 0; i < outputNumber; i++) {
                    adjustBiasI[i] += rate * (standard[i] - output[i]) * output[i] * (1 - output[i]);

                    adjustSoft[i]+=standard[i]-output[i];
                }
                for (int j = 0; j < hiddenNumber; j++) {
                    outputTotal = 0;
                    for (int i = 0; i < outputNumber; i++) {
                        outputTotal += (standard[i] - output[i]) * output[i] * (1 - output[i]) * weightJi[j][i];
                    }
                    adjustBiasJ[j] += rate * hidden[j] * (1 - hidden[j]) * outputTotal;
                }

                //renew
                if ((test + 1) % section == 0) {
                    renew.soft(output,adjustSoft,section,outputNumber);
                    renew.weight(weightKj,weightJi,adjustWeightKj,adjustWeightJi,inputNumber,hiddenNumber,outputNumber);
                    renew.bias(biasJ,biasI,adjustBiasJ,adjustBiasI,inputNumber,hiddenNumber,outputNumber);
                    initial.adjust(adjustWeightKj, adjustWeightJi, adjustBiasJ, adjustBiasI,adjustSoft,inputNumber,hiddenNumber,outputNumber);

                }

                //check output
                trainNum++;
                if (((trainNum+1)% length==0)&& (check(weightKj,weightJi,biasJ,biasI,leftBound,rightBound,inputNumber,hiddenNumber,outputNumber,trainNum))) {
                    data.save(weightKj,weightJi,biasJ,biasI,inputNumber,hiddenNumber,outputNumber);
                }
            }
        }
    }
    //sigm function
    static float sigm(float x){
        return (float)(1/(1+Math.exp(-x)));
    }
    //check output
    static boolean check(float[][] weightKj,float[][] weightJi,float[] biasJ,float[] biasI,int leftBound,int rightBound,int inputNumber,int hiddenNumber,int outputNumber,int trainNum) throws FileNotFoundException {
        //test
        int[] input=new int[inputNumber];
        float[] hidden=new float[hiddenNumber];
        float[] output=new float[outputNumber];
        int test,trueAns=0,testNum=0;
        for (test=leftBound;test<rightBound;test++){
            if (Math.random()>testScale) continue;;
            Scanner inputTest= new Scanner(new File("newTestSet/"+test+".txt"));
            testNum++;
            for (int i=0;i<inputNumber;i++){
                input[i]=inputTest.nextInt();
                if (input[i]>0) input[i]=1;
            }
            int outputNum=inputTest.nextInt();
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

            //chose the true ans
            float max=0;
            int ans=-1;
            for (int i=0;i<outputNumber;i++){
                if (output[i]>max){
                    max=output[i];
                    ans=i;
                }
            }
            if (ans==outputNum){
                trueAns++;
            }
        }
        float nowAccuracy=(float)trueAns/(float)testNum;
        System.out.println("Train "+(trainNum+1)+" Test "+testNum+ " The true ans rate is "+(nowAccuracy*100)+"%");
        if (nowAccuracy>accuracy){
            accuracy=nowAccuracy;
            return true;
        }
        return false;
    }
}
