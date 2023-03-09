package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataCnP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class CCompute {
    public static GraphDataCnP compute(Draw drawData) {
        String graphType = drawData.getGraphType();
        int subgroupTotal = drawData.getSubgroupTotal();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int samplesNum = subgroupTotal * subgroupCapacity;

        double[] c = drawData.getDataArrayCnP();
        int defectsNum = (int) DoubleStream.of(c).sum();

        // 平均不合格品数
        double cBar = defectsNum / subgroupTotal;

        // 控制图刻度
        double graduationC = DoubleStream.of(c).max().orElse(0) * 2;

        // 控制界限
        double uclC = cBar + 3 * Math.sqrt(cBar);
        double lclC = cBar - 3 * Math.sqrt(cBar);
        double clC = cBar;



        // 分析
        // 超出控制线的点
        List<Integer> specialPointsC = new ArrayList<>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (c[i] < lclC || c[i] > uclC)    specialPointsC.add(i+1);
        }

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainCList= ChainCount.descendChainCount(n, subgroupTotal, c);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainCList  = ChainCount.ascendChainCount(n, subgroupTotal, c);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainCList = ChainCount.lowerChainCount(n, subgroupTotal, c, 0);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainCList = ChainCount.upperChainCount(n, subgroupTotal, c, 0);



        // 明显非随机图形
        // ......
        double intervalC = (uclC -cBar) / 3;
        double[] intervalValuesC = new double[]{uclC, cBar+intervalC*2, cBar+intervalC, cBar, cBar-intervalC, cBar-intervalC*2, lclC};




        // 调试代码 ---------------------------------------------------------------------
        System.out.println("c = " + Arrays.toString(c));
        System.out.println("cBar = " + cBar);
        System.out.println("graduationC = " + graduationC);
        System.out.println("uclC = " + uclC);
        System.out.println("clC = " + clC);
        System.out.println("lclC = " + lclC);
        System.out.println("specialPointsC = " + specialPointsC);
        System.out.println("descendChainCList = " + descendChainCList);
        System.out.println("ascendChainCList = " + ascendChainCList);
        System.out.println("upperChainCList = " + upperChainCList);
        System.out.println("lowerChainCList = " + lowerChainCList);
        System.out.println("intervalValuesC = " + Arrays.toString(intervalValuesC));
        // ------------------------------------------------------------------------------


        // 返回体设置
        GraphDataCnP graphData = new GraphDataCnP();

        graphData.setGraphType(graphType);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSampleNum(samplesNum);
        graphData.setDefectsNum(defectsNum);
        graphData.setAvgDefectNum(cBar);
        graphData.setUcl(uclC);
        graphData.setCl(clC);
        graphData.setLcl(lclC);
        graphData.setDataArray(c);
        graphData.setGraduation(graduationC);
        graphData.setSpecialPoints(specialPointsC);
        graphData.setDescendChainList(descendChainCList);
        graphData.setAscendChainList(ascendChainCList);
        graphData.setUpperChainList(upperChainCList);
        graphData.setLowerChainList(lowerChainCList);
        graphData.setIntervalValues(intervalValuesC);

        return graphData;
    }
}
