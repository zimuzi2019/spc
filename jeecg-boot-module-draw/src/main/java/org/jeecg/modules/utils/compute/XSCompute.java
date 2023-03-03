package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.utils.TableCoefficient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class XSCompute {
    public static void compute(Draw drawData) {
        double[][] dataArray = drawData.getDataArrayXRXSMedium();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int subgroupTotal = drawData.getSubgroupTotal();
        double usl = drawData.getUsl();
        double lsl = drawData.getLsl();

        // 每个子组的均值
        double[] xBar = new double[subgroupTotal];
        // 每个子组的标准差
        double[] s = new double[subgroupTotal];

        for(int i = 0; i < subgroupTotal; i++) {
            double subgroupSum = DoubleStream.of(dataArray[i]).sum();
            xBar[i] = subgroupSum / subgroupCapacity;

            // 暂存 (x-xBar[i])^2 的和
            double sum = 0;
            for (double x : dataArray[i]) {
                sum = sum + Math.pow(x - xBar[i], 2);
            }
            s[i] = Math.sqrt(sum / (subgroupTotal-1) );
        }

        // 过程均值
        double xDoubleBar = DoubleStream.of(xBar).sum()  / subgroupTotal;

        // 平均标准差
        double sBar = DoubleStream.of(s).sum() / subgroupTotal;

        // 控制界限
        double uclXBar; double lclXBar; double uclR; double lclR;
        if (subgroupCapacity <= 25) {
            uclXBar = xDoubleBar + TableCoefficient.A3[subgroupCapacity] * sBar;
            lclXBar = xDoubleBar - TableCoefficient.A3[subgroupCapacity] * sBar;
            uclR = TableCoefficient.B4[subgroupCapacity] * sBar;
            lclR = TableCoefficient.B3[subgroupCapacity] * sBar;
        } else {
            double a = 3 / Math.sqrt(subgroupCapacity - 0.5);
            double d = 1 - 3 / Math.sqrt(2 * subgroupCapacity - 2.5);
            double e = 1 + 3 / Math.sqrt(2 * subgroupCapacity - 2.5);
            uclXBar = xDoubleBar + a * sBar;
            lclXBar = xDoubleBar - a * sBar;
            uclR = e * sBar;
            lclR = d * sBar;
        }


        // 过程标准差估计值
        double sigma;
        if (subgroupCapacity <= 25) {
            sigma = sBar / TableCoefficient.c4[subgroupCapacity];
        } else {
            double b = (4*subgroupCapacity - 4) / (4*subgroupCapacity -3);
            sigma = sBar / b;
        }


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
        List<Integer> specialPointsXBar = new ArrayList<Integer>();
        List<Integer> specialPointsS = new ArrayList<Integer>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (s[i] < lclR || s[i] > uclR)               specialPointsS.add(i+1);
            if (xBar[i] < lclXBar || xBar[i] > uclXBar)   specialPointsXBar.add(i+1);
        }


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXBarList = ChainCount.descendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > descendChainSList = ChainCount.descendChainCount(n, subgroupTotal, s);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXBarList  = ChainCount.ascendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > ascendChainSList  = ChainCount.ascendChainCount(n, subgroupTotal, s);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXBarList = ChainCount.lowerChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > lowerChainSList = ChainCount.lowerChainCount(n, subgroupTotal, s, sBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXBarList = ChainCount.upperChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > upperChainSList = ChainCount.upperChainCount(n, subgroupTotal, s, sBar);




        // 明显非随机图形
        // ......
        double intervalXBar = (uclXBar - xDoubleBar) / 3;
        double intervalR =    (uclR - sBar) / 3;
        double[] intervalValuesXBar = new double[]{uclXBar, xDoubleBar+intervalXBar*2, xDoubleBar+intervalXBar, xDoubleBar, xDoubleBar-intervalXBar, xDoubleBar-intervalXBar*2, lclXBar};
        double[] intervalValuesR    = new double[]{uclR,    sBar+intervalR*2 ,         sBar+intervalR ,         sBar,       sBar-intervalR,          sBar-intervalR*2,          lclR};
    }
}