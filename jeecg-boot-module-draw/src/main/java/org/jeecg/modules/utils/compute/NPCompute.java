package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataCnP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class NPCompute {
    public static GraphDataCnP compute(Draw drawData) {
        String graphType = drawData.getGraphType();

        int subgroupTotal = drawData.getSubgroupTotal();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int samplesNum = subgroupTotal * subgroupCapacity;

        double[] np = drawData.getDataArrayCnP();
        int defectsNum = (int) DoubleStream.of(np).sum();

        // 平均不合格品率
        double pBar  = defectsNum / samplesNum;  // 计算用

        // 平均不合格品数
        double npBar = defectsNum / subgroupTotal;

        // 控制图刻度
        double graduationNP = DoubleStream.of(np).max().orElse(0) * 2;

        // 控制界限
        double uclNP = subgroupCapacity * pBar + 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar));
        double lclNP = subgroupCapacity * pBar - 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar));
        double clNP = subgroupCapacity * pBar;



        // 分析
        // 超出控制线的点
        List<Integer> specialPointsNP = new ArrayList<>();

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
        double[] intervalValuesNP = new double[]{uclNP, clNP+intervalNP*2, clNP+intervalNP, clNP, clNP-intervalNP, clNP-intervalNP*2, lclNP};




        // 调试代码 ---------------------------------------------------------------------
        System.out.println("np = " + Arrays.toString(np));
        System.out.println("pBar = " + pBar);
        System.out.println("npBar = " + npBar);
        System.out.println("graduationNP = " + graduationNP);
        System.out.println("uclC = " + uclNP);
        System.out.println("lclNP = " + lclNP);

        System.out.println("clNP = " + clNP);

        System.out.println("specialPointsNP = " + specialPointsNP);
        System.out.println("descendChainNPList = " + descendChainNPList);
        System.out.println("ascendChainNPList = " + ascendChainNPList);
        System.out.println("upperChainNPList = " + upperChainNPList);
        System.out.println("lowerChainNPList = " + lowerChainNPList);
        System.out.println("intervalValuesCNP= " + Arrays.toString(intervalValuesNP));
        // -----------------------------------------------------------------------------


        // 返回体设置
        GraphDataCnP graphData = new GraphDataCnP();

        graphData.setGraphType(graphType);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSampleNum(samplesNum);
        graphData.setDefectsNum(defectsNum);
        graphData.setAvgDefectNum(npBar);
        graphData.setUcl(uclNP);
        graphData.setCl(clNP);
        graphData.setLcl(lclNP);
        graphData.setDataArray(np);
        graphData.setGraduation(graduationNP);
        graphData.setSpecialPoints(specialPointsNP);
        graphData.setDescendChainList(descendChainNPList);
        graphData.setAscendChainList(ascendChainNPList);
        graphData.setUpperChainList(upperChainNPList);
        graphData.setLowerChainList(lowerChainNPList);
        graphData.setIntervalValues(intervalValuesNP);

        return graphData;
    }
}
