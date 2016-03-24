/**
 * Created by Jly_wave on 9/26/15.
 */
public class Renew {
    void weight(float[][] weightKj,float[][] weightJi,float[][]adjustWeightKj,float[][] adjustWeightJi,int inputNumber,int hiddenNumber,int outputNumber){
        for (int j = 0; j < hiddenNumber; j++)
            for (int i = 0; i < outputNumber; i++) {
                weightJi[j][i] += adjustWeightJi[j][i];
            }
        for (int k = 0; k < inputNumber; k++)
            for (int j = 0; j < hiddenNumber; j++) {
                weightKj[k][j] += adjustWeightKj[k][j];
            }
    }
    void bias(float[] biasJ,float[] biasI,float[] adjustBiasJ,float[] adjustBiasI,int inputNumber,int hiddenNumber,int outputNumber){
        for (int j = 0; j < hiddenNumber; j++) {
            biasJ[j] += adjustBiasJ[j];
        }
        for (int i = 0; i < outputNumber; i++) {
            biasI[i] += adjustBiasI[i];
        }
    }
    void soft(float[] output,float[] adjustSoft,int section,int outputNumber){
        for (int i=0;i<outputNumber;i++){
            output[i]-=adjustSoft[i]/section;
            adjustSoft[i]=0;
        }
    }
}
