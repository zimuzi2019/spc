package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.utils.TableCoefficient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class XRCompute {
    public static void compute(Draw drawData) {
        double[][] dataArray = drawData.getDataArrayXRXSMedium();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int subgroupTotal = drawData.getSubgroupTotal();
        double usl = drawData.getUsl();
        double lsl = drawData.getLsl();


        // 每个子组的均值
        double[] xBar = new double[subgroupTotal];
        // 每个子组的极差
        double[] r = new double[subgroupTotal];

        for(int i = 0; i < subgroupTotal; i++) {
            double subgroupSum = DoubleStream.of(dataArray[i]).sum();
            double xMax = DoubleStream.of(dataArray[i]).max().orElse(0);
            double xMin = DoubleStream.of(dataArray[i]).min().orElse(0);

            xBar[i] = subgroupSum / subgroupCapacity;
            r[i] = xMax - xMin;
        }

        // 过程均值
        double xDoubleBar = DoubleStream.of(xBar).sum()  / subgroupTotal;

        // 平均极差值
        double rBar = DoubleStream.of(r).sum() / subgroupTotal;

        // 控制图刻度
        // 均值图
        double xBarGraduation = 2 * (DoubleStream.of(xBar).max().orElse(0) - DoubleStream.of(xBar).min().orElse(0));
        // 极差图
        double RGraduation = 2 * DoubleStream.of(r).max().orElse(0);
        // 极差图的刻度值和均值图的刻度值倍数关系未设置为2倍关系

        // 控制界限
        // 这里没有写 subgroupCapacity > 25 时该如何取值
        double uclXBar = xDoubleBar + TableCoefficient.A2[subgroupCapacity] * rBar;
        double lclXBar = xDoubleBar - TableCoefficient.A2[subgroupCapacity] * rBar;
        double uclR = TableCoefficient.D4[subgroupCapacity] * rBar;
        double lclR = TableCoefficient.D3[subgroupCapacity] * rBar;

        // 过程的标准偏差
        double sigma = rBar / TableCoefficient.d2[subgroupCapacity];

        //单边容差
        double zUSL = (usl - xDoubleBar) / sigma;
        double zLSL = (xDoubleBar - lsl) / sigma;

        //双边容差
        double  z = Math.min(zUSL, zLSL);

        //过程能力上下限限
        double cpu = zUSL / 3;
        double cpl = zLSL / 3;
        //过程能力指数
        double cpk = z / 3;




        // 分析
        // 超出控制线的点
        List<Integer> specialPointsXBar = new ArrayList<>();
        List<Integer> specialPointsR = new ArrayList<>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (r[i] < lclR || r[i] > uclR)               specialPointsR.add(i+1);
            if (xBar[i] < lclXBar || xBar[i] > uclXBar)   specialPointsXBar.add(i+1);
        }


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXBarList = ChainCount.descendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > descendChainRList = ChainCount.descendChainCount(n, subgroupTotal, r);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXBarList  = ChainCount.ascendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > ascendChainRList  = ChainCount.ascendChainCount(n, subgroupTotal, r);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXBarList = ChainCount.lowerChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > lowerChainRList = ChainCount.lowerChainCount(n, subgroupTotal, r, rBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXBarList = ChainCount.upperChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > upperChainRList = ChainCount.upperChainCount(n, subgroupTotal, r, rBar);




        // 明显非随机图形
        // ......
        double intervalXBar = (uclXBar - xDoubleBar) / 3;
        double intervalR =    (uclR - rBar) / 3;
        double[] intervalValuesXBar = new double[]{uclXBar, xDoubleBar+intervalXBar*2, xDoubleBar+intervalXBar, xDoubleBar, xDoubleBar-intervalXBar, xDoubleBar-intervalXBar*2, lclXBar};
        double[] intervalValuesR    = new double[]{uclR,    rBar+intervalR*2 ,         rBar+intervalR ,         rBar,       rBar-intervalR,          rBar-intervalR*2,          lclR};





        // 调试代码
        System.out.println("xBar = " + Arrays.toString(xBar));
        System.out.println("r = " + Arrays.toString(r));
        System.out.println("xDoubleBar = " + xDoubleBar);
        System.out.println("rBar = " + rBar);
        System.out.println("xBarGraduation = " + xBarGraduation);
        System.out.println("RGraduation = " + RGraduation);
        System.out.println("uclXBar = " + uclXBar);
        System.out.println("lclXBar = " + lclXBar);
        System.out.println("uclR = " + uclR);
        System.out.println("lclR = " + lclR);
        System.out.println("zUSL = " + zUSL);
        System.out.println("zLSL = " + zLSL);
        System.out.println("z = " + z);
        System.out.println("cpu = " + cpu);
        System.out.println("cpl = " + cpl);
        System.out.println("cpk = " + cpk);
        System.out.println("specialPointsXBar = " + specialPointsXBar);
        System.out.println("specialPointsR = " + specialPointsR);
        System.out.println("descendChainXBarList = " + descendChainXBarList);
        System.out.println("descendChainRList = " + descendChainRList);
        System.out.println("ascendChainXBarList = " + ascendChainXBarList);
        System.out.println("ascendChainRList = " + ascendChainRList);
        System.out.println("upperChainXBarList = " + upperChainXBarList);
        System.out.println("upperChainRList = " + upperChainRList);
        System.out.println("lowerChainXBarList = " + lowerChainXBarList);
        System.out.println("lowerChainRList = " + lowerChainRList);
        System.out.println("intervalValuesXBar = " + Arrays.toString(intervalValuesXBar));
        System.out.println("intervalValuesR = " + Arrays.toString(intervalValuesR));
        //
    }
}
