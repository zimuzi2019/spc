package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class NPCompute {
    public static void compute(Draw drawData) {
        String graphType = drawData.getGraphType();
        int subgroupTotal = drawData.getSubgroupTotal();
        int subgroupCapacity = drawData.getSubgroupCapacity();

        double[] dataArray = drawData.getDataArrayCnP();

        double[] np = dataArray;

        // 平均不合格品率
        double pBar  = DoubleStream.of(np).sum() / (subgroupTotal * subgroupCapacity);
        // 平均不良率
        double npBar = DoubleStream.of(np).sum() / subgroupTotal;

        // 控制图刻度
        double graduation = DoubleStream.of(np).max().orElse(0) * 2;

        // 控制界限
        double uclNP = subgroupCapacity * pBar + 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar));
        double lclNP = subgroupCapacity * pBar - 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar));
        double clNP = subgroupCapacity * pBar;

        // 似乎不用计算过程能力指数
        // ......



        // 分析
        // 超出控制线的点
        List<Integer> specialPointsNP = new ArrayList<Integer>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (np[i] < lclNP || np[i] > uclNP)    specialPointsNP.add(i+1);
        }

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainNPList= ChainCount.descendChainCount(n, subgroupTotal, np);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainNPList  = ChainCount.ascendChainCount(n, subgroupTotal, np);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainNPList = ChainCount.lowerChainCount(n, subgroupTotal, np, 0);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainNPList = ChainCount.upperChainCount(n, subgroupTotal, np, 0);



        // 明显非随机图形
        // ......
        double intervalNP = (uclNP - clNP) / 3;
        double[] intervalValuesPt = new double[]{uclNP, clNP+intervalNP*2, clNP+intervalNP, clNP, clNP-intervalNP, clNP-intervalNP*2, lclNP};
    }
}
