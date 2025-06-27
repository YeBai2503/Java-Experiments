package tool;

import java.util.Random;

//生成正太分布的数据
public class GenerateGaussian {
    //传入参数从左到右为均值，标准差，下限，上限
    public static double Gauss(double mean,double standardDeviation,double lowerBound,double upperBound) {
        Random ran=new Random();
        double randomValue;

        do {
            randomValue = mean + ran.nextGaussian() * standardDeviation;
        } while (randomValue <= lowerBound || randomValue >= upperBound); // 如果超出范围则重新生成

        return randomValue;

    }
}
