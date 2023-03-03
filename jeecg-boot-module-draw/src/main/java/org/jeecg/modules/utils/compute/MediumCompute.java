package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.utils.TableCoefficient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class MediumCompute {
    public static void compute(Draw drawData) {
        double[][] dataArray = drawData.getDataArrayXRXSMedium();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int subgroupTotal = drawData.getSubgroupTotal();
        double usl = drawData.getUsl();
        double lsl = drawData.getLsl();


        // 每个子组的中位数
        double[] xMedium = new double[subgroupTotal];
        // 每个子组的极差
        double[] r = new double[subgroupTotal];

        for(int i = 0; i < subgroupTotal; i++) {
            Arrays.sort(dataArray[i]);
            if (subgroupCapacity%2 == 0) {
                xMedium[i] = (dataArray[i][subgroupCapacity/2 -1] + dataArray[i][subgroupCapacity/2]) / 2;
            } else {
                xMedium[i] = dataArray[i][subgroupCapacity/2];
            }

            double xMax = dataArray[i][0];
            double xMin = dataArray[i][subgroupCapacity-1];

            r[i] = xMax - xMin;
        }

        // 过程均值
        double xMediumBar = DoubleStream.of(xMedium).sum()  / subgroupTotal;

        // 平均极差值
        double rBar = DoubleStream.of(r).sum() / subgroupTotal;

        // 控制界限
        // 这里没有写 subgroupCapacity > 25 时该如何取值
        double uclXMedium = xMediumBar + TableCoefficient.A2[subgroupCapacity] * rBar;
        double lclXMedium = xMediumBar - TableCoefficient.A2[subgroupCapacity] * rBar;
        double uclR = TableCoefficient.D4[subgroupCapacity] * rBar;
        double lclR = TableCoefficient.D3[subgroupCapacity] * rBar;

        // 过程的标准偏差
        double sigma = rBar / TableCoefficient.d2[subgroupCapacity];

        //单边容差
        double zUSL = (usl - xMediumBar) / sigma;
        double zLSL = (xMediumBar - lsl) / sigma;

        //双边容差
        double  z = Math.min(zUSL, zLSL);

        //过程能力上下限限
        double cpu = zUSL / 3;
        double cpl = zLSL / 3;
        //过程能力指数
        double cpk = z / 3;




        // 分析
        // 超出控制线的点
        List<Integer> specialPointsXMedium = new ArrayList<Integer>();
        List<Integer> specialPointsR = new ArrayList<Integer>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (r[i] < lclR || r[i] > uclR)                           specialPointsR.add(i+1);
            if (xMedium[i] < lclXMedium || xMedium[i] > uclXMedium)   specialPointsXMedium.add(i+1);
        }


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXMediumList = ChainCount.descendChainCount(n, subgroupTotal, xMedium);
        List<ArrayList<Integer> > descendChainRList = ChainCount.descendChainCount(n, subgroupTotal, r);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXMediumList  = ChainCount.ascendChainCount(n, subgroupTotal, xMedium);
        List<ArrayList<Integer> > ascendChainRList  = ChainCount.ascendChainCount(n, subgroupTotal, r);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXMediumList = ChainCount.lowerChainCount(n, subgroupTotal, xMedium, xMediumBar);
        List<ArrayList<Integer> > lowerChainRList = ChainCount.lowerChainCount(n, subgroupTotal, r, rBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXMediumList = ChainCount.upperChainCount(n, subgroupTotal, xMedium, xMediumBar);
        List<ArrayList<Integer> > upperChainRList = ChainCount.upperChainCount(n, subgroupTotal, r, rBar);




        // 明显非随机图形
        // ......
        double intervalXMedium = (uclXMedium - xMediumBar) / 3;
        double intervalR =    (uclR - rBar) / 3;
        double[] intervalValuesXBar = new double[]{uclXMedium, xMediumBar+intervalXMedium*2, xMediumBar+intervalXMedium, xMediumBar, xMediumBar-intervalXMedium, xMediumBar-intervalXMedium*2, lclXMedium};
        double[] intervalValuesR    = new double[]{uclR,       rBar+intervalR*2 ,            rBar+intervalR ,            rBar,       rBar-intervalR,             rBar-intervalR*2,             lclR};
    }
}
