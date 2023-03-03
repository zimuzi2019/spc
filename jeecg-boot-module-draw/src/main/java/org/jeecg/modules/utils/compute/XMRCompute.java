package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.utils.TableCoefficient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class XMRCompute {
    public static void compute(Draw drawData) {
        int subgroupTotal = drawData.getSubgroupTotal();
        double usl = drawData.getUsl();
        double lsl = drawData.getLsl();

        // 单值
        double[] x = drawData.getDataArrayXMR();

        // 极差
        double[] mr = new double[subgroupTotal-1]; // 对于样本容量为2的移动极差，其移动极差（MR）的个数比单值读数的个数少1

        for (int i = 0; i < subgroupTotal-1; i++) {
            mr[i] = x[i+1] - x[i];
        }

        // 过程均值
        double xBar = DoubleStream.of(x).sum()  / subgroupTotal;

        // 移动平均极差
        double mrBar = DoubleStream.of(mr).sum() / (subgroupTotal-1);

        // 控制图刻度
        // 这里“产品的规范容差加上超过规范的读数的允许值”没搞懂
        double b = (DoubleStream.of(x).max().orElse(0) - DoubleStream.of(x).min().orElse(0))  * 2;

        // 控制界限
        double uclX = xBar + TableCoefficient.E2[2] * mrBar;
        double lclX = xBar - TableCoefficient.E2[2] * mrBar;
        double uclMR = TableCoefficient.D4[2] * mrBar;
        double lclMR = TableCoefficient.D3[2] * mrBar;

        // 过程的标准偏差
        double sigma = mrBar / TableCoefficient.d2[2];

        //单边容差
        double zUSL = (usl - xBar) / sigma;
        double zLSL = (xBar - lsl) / sigma;

        //双边容差
        double  z = Math.min(zUSL, zLSL);

        //过程能力上下限限
        double cpu = zUSL / 3;
        double cpl = zLSL / 3;
        //过程能力指数
        double cpk = z / 3;




        // 分析
        // 超出控制线的点
        List<Integer> specialPointsX = new ArrayList<Integer>();
        List<Integer> specialPointsMR = new ArrayList<Integer>();


        for (int i = 0; i < subgroupTotal-1; i++) {
            if (mr[i] < lclMR || mr[i] > uclMR)   specialPointsMR.add(i+1);
        }

        for (int i = 0; i < subgroupTotal; i++) {
            if (x[i] < lclX || x[i] > uclX)       specialPointsX.add(i+1);
        }

        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXList = ChainCount.descendChainCount(n, subgroupTotal, x);
        List<ArrayList<Integer> > descendChainMRList = ChainCount.descendChainCount(n, subgroupTotal-1, mr);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXList  = ChainCount.ascendChainCount(n, subgroupTotal, x);
        List<ArrayList<Integer> > ascendChainMRList  = ChainCount.ascendChainCount(n, subgroupTotal-1, mr);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXList = ChainCount.lowerChainCount(n, subgroupTotal, x, xBar);
        List<ArrayList<Integer> > lowerChainMRList = ChainCount.lowerChainCount(n, subgroupTotal-1, mr, mrBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXList = ChainCount.upperChainCount(n, subgroupTotal, x, xBar);
        List<ArrayList<Integer> > upperChainMRList = ChainCount.upperChainCount(n, subgroupTotal-1, mr, mrBar);




        // 明显非随机图形
        // ......
        double intervalX = (uclX - xBar) / 3;
        double intervalMR =    (uclMR - mrBar) / 3;
        double[] intervalValuesX =   new double[]{uclX,  xBar+intervalX*2,   xBar+intervalX,   xBar,  xBar-intervalX,   xBar-intervalX*2,   lclX};
        double[] intervalValuesMR  = new double[]{uclMR, mrBar+intervalMR*2, mrBar+intervalMR, mrBar, mrBar-intervalMR, mrBar-intervalMR*2, lclMR};
    }
}
