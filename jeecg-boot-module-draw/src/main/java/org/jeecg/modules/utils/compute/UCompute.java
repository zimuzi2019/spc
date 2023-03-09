package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataPU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class UCompute {
    // 这里画的是通用单位缺陷数控制图而不是单位缺陷控制图
    public static GraphDataPU compute(Draw drawData) {
        String graphType = drawData.getGraphType();

        int subgroupTotal = drawData.getSubgroupTotal();

        int[] dataArraySubgroupsCapacity = drawData.getDataArrayPUSubgroupsCapacity();
        int[] dataArrayDefectsNum = drawData.getDataArrayPUDefectsNum();

        int samplesNum = IntStream.of(dataArraySubgroupsCapacity).sum();
        int defectsNum = IntStream.of(dataArrayDefectsNum).sum();
        double avgSubgroupCapacity = IntStream.of(dataArraySubgroupsCapacity).sum() / subgroupTotal;
        int subgroupCapactityMax = IntStream.of(dataArraySubgroupsCapacity).max().orElse(0);
        int subgroupCapactityMin = IntStream.of(dataArraySubgroupsCapacity).min().orElse(0);

        //每个子组的不合格点数
        double[] u = new double[subgroupTotal];

        for (int i = 0; i < subgroupTotal; i++) {
            u[i] = dataArrayDefectsNum[i] * 1.0 / dataArraySubgroupsCapacity[i];
        }

        // 平均不合格品数
        double uBar = defectsNum * 1.0 / samplesNum;

        // 标准化处理
        double[] ut = new double[subgroupTotal];
        for (int i = 0; i < subgroupTotal; i++) ut[i] = (u[i] - uBar) / Math.sqrt(uBar / dataArraySubgroupsCapacity[i]);

        // 控制图坐标刻度
        double graduation = DoubleStream.of(ut).max().orElse(0) * 2;

        // 上下控制限
        double uclUt = 3;
        double lclUt = -3;
        double clUt = 0;


        // 分析
        // 超出控制线的点
        List<Integer> specialPointsUt = new ArrayList<>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (ut[i] < lclUt || ut[i] > uclUt) specialPointsUt.add(i + 1);
        }

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer>> descendChainUtList = ChainCount.descendChainCount(n, subgroupTotal, ut);

        // 上升链集合
        List<ArrayList<Integer>> ascendChainUtList = ChainCount.ascendChainCount(n, subgroupTotal, ut);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer>> lowerChainUtList = ChainCount.lowerChainCount(n, subgroupTotal, ut, 0);

        // 下侧链集合
        List<ArrayList<Integer>> upperChainUtList = ChainCount.upperChainCount(n, subgroupTotal, ut, 0);


        // 明显非随机图形
        // ......
        double[] intervalValuesUt = new double[]{3, 2, 1, 0, -1, -2, -3};



        // 调试代码 -----------------------------------------------------------------------
        System.out.println("u = " + Arrays.toString(u));
        System.out.println("uBar = " + uBar);

        System.out.println("ut = " + Arrays.toString(ut));

        System.out.println("graduation = " + graduation);
        System.out.println("uclUt = " + uclUt);
        System.out.println("lclUt = " + lclUt);
        System.out.println("specialPointsUt = " + specialPointsUt);
        System.out.println("descendChainUtList = " + descendChainUtList);
        System.out.println("ascendChainUtList = " + ascendChainUtList);
        System.out.println("upperChainUtList = " + upperChainUtList);
        System.out.println("lowerChainUtList = " + lowerChainUtList);
        System.out.println("intervalValuesUt = " + Arrays.toString(intervalValuesUt));
        // -------------------------------------------------------------------------------

        // 返回体设置
        GraphDataPU graphData = new GraphDataPU();

        graphData.setGraphType(graphType);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setAvgSubgroupCapacity(avgSubgroupCapacity);
        graphData.setSubgroupCapacityMax(subgroupCapactityMax);
        graphData.setSubgroupCapacityMin(subgroupCapactityMin);
        graphData.setSamplesNum(samplesNum);
        graphData.setDefectsNum(defectsNum);
        graphData.setAvgDefectsNum(uBar);
        graphData.setUcl(uclUt);
        graphData.setCl(clUt);
        graphData.setLcl(lclUt);
        graphData.setDataArray(ut);
        graphData.setGraduation(graduation);
        graphData.setSpecialPoints(specialPointsUt);
        graphData.setAscendChainList(ascendChainUtList);
        graphData.setDescendChainList(descendChainUtList);
        graphData.setUpperChainList(upperChainUtList);
        graphData.setLowerChainList(lowerChainUtList);
        graphData.setIntervalValues(intervalValuesUt);

        return graphData;
    }
}
