package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataPU;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class UCompute {
    public static GraphDataPU compute(Draw drawData) {
        DecimalFormat df = new DecimalFormat("#.###");

        String graphType = drawData.getGraphType();

        int subgroupTotal = drawData.getSubgroupTotal();

        int[] dataArraySubgroupsCapacity = drawData.getDataArrayPUPTUTSubgroupsCapacity();
        int[] dataArrayDefectsNum = drawData.getDataArrayPUPTUTDefectsNum();

        int samplesNum = IntStream.of(dataArraySubgroupsCapacity).sum();
        int defectsNum = IntStream.of(dataArrayDefectsNum).sum();
        double avgSubgroupCapacity = IntStream.of(dataArraySubgroupsCapacity).sum() * 1.0 / subgroupTotal;
        int subgroupCapactityMax = IntStream.of(dataArraySubgroupsCapacity).max().orElse(0);
        int subgroupCapactityMin = IntStream.of(dataArraySubgroupsCapacity).min().orElse(0);

        //每个子组的不合格点数
        double[] u = new double[subgroupTotal];

        for (int i = 0; i < subgroupTotal; i++) {
            u[i] = dataArrayDefectsNum[i] * 1.0 / dataArraySubgroupsCapacity[i];
        }

        // 平均不合格品数
        double uBar = defectsNum * 1.0 / samplesNum;
        String avgDefectsNum = df.format(uBar * 100) + "%";

        // 控制图坐标刻度
        double graduation = DoubleStream.of(u).max().orElse(0) * 2;
        graduation = (int)graduation;

        // 上下控制限
        String quantile = drawData.getQuantile();
        double[] uclU = new double[subgroupTotal]; double[] lclU = new double[subgroupTotal]; double[] clU = new double[subgroupTotal];
        for (int i = 0; i < subgroupTotal; i++) {
            if (quantile.equals("不使用")) {
                uclU[i] = uBar + 3 * Math.sqrt(uBar / dataArraySubgroupsCapacity[i]);
                lclU[i] = uBar - 3 * Math.sqrt(uBar / dataArraySubgroupsCapacity[i]);
                 clU[i] = uBar;
            } else {
                uclU[i] = uBar + 3 * Math.sqrt(uBar / dataArraySubgroupsCapacity[i]) + 4.0 / (3*dataArraySubgroupsCapacity[i]);
                lclU[i] = uBar - 3 * Math.sqrt(uBar / dataArraySubgroupsCapacity[i]) + 4.0 / (3*dataArraySubgroupsCapacity[i]);
                 clU[i] = uBar - 1.0/(6*dataArraySubgroupsCapacity[i]);
            }
            uclU[i] = Double.parseDouble(df.format(uclU[i]));
            lclU[i] = Double.parseDouble(df.format(lclU[i]));
            clU[i] = Double.parseDouble(df.format(clU[i]));

            if(lclU[i] < 0) lclU[i] = 0;
        }


        // 分析
        // 超出控制线的点
        List<Integer> specialPointsU = new ArrayList<>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (u[i] < lclU[i] || u[i] > uclU[i]) specialPointsU.add(i + 1);
        }
        String pointsSpecialRadio = df.format(specialPointsU.size() * 100.0 / subgroupTotal) + "%";

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer>> descendChainUList = ChainCount.descendChainCount(n, subgroupTotal, u);

        // 上升链集合
        List<ArrayList<Integer>> ascendChainUList = ChainCount.ascendChainCount(n, subgroupTotal, u);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer>> lowerChainUList = ChainCount.lowerChainCountPU(n, subgroupTotal, u, clU);

        // 下侧链集合
        List<ArrayList<Integer>> upperChainUList = ChainCount.upperChainCountPU(n, subgroupTotal, u, clU);


        // 明显非随机图形
        // ......
        // double[] intervalValuesU = new double[]{3, 2, 1, 0, -1, -2, -3};
        int pointsCNum = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            double upperC = (uclU[i] - clU[i])/3 + clU[i];
            double lowerC = -(uclU[i] - clU[i])/3 + clU[i];
            if (u[i] > lowerC && u[i] < upperC) pointsCNum++;
        }
        double tmp = pointsCNum * 100.0 / subgroupTotal;
        tmp = Double.parseDouble(df.format(tmp));
        String pointsCRadio = tmp + "%";

        uBar = Double.parseDouble(df.format(uBar));
        avgSubgroupCapacity = Double.parseDouble(df.format(avgSubgroupCapacity));
        graduation = Double.parseDouble(df.format(graduation));
        // uclU = Double.parseDouble(df.format(uclU));
        // lclU = Double.parseDouble(df.format(lclU));
        // clU=  Double.parseDouble(df.format(clU));


        // 调试代码 -----------------------------------------------------------------------
        System.out.println("u = " + Arrays.toString(u));
        System.out.println("uBar = " + uBar);

        System.out.println("ut = " + Arrays.toString(u));

        System.out.println("graduation = " + graduation);
        System.out.println("uclU = " + uclU);
        System.out.println("lclU = " + lclU);
        System.out.println("specialPointsU = " + specialPointsU);
        System.out.println("descendChainUList = " + descendChainUList);
        System.out.println("ascendChainUList = " + ascendChainUList);
        System.out.println("upperChainUList = " + upperChainUList);
        System.out.println("lowerChainUList = " + lowerChainUList);
        // System.out.println("intervalValuesU = " + Arrays.toString(intervalValuesU));
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
        graphData.setAvgDefectsNum(avgDefectsNum);
        graphData.setUcl(uclU);
        graphData.setCl(clU);
        graphData.setLcl(lclU);
        graphData.setQuantile(quantile);
        graphData.setDataArray(u);
        graphData.setGraduation(graduation);
        graphData.setSpecialPoints(specialPointsU);
        graphData.setAscendChainList(ascendChainUList);
        graphData.setDescendChainList(descendChainUList);
        graphData.setUpperChainList(upperChainUList);
        graphData.setLowerChainList(lowerChainUList);
        // graphData.setIntervalValues(intervalValuesU);
        graphData.setPointsCRadio(pointsCRadio);
        graphData.setPointsSpecialRadio(pointsSpecialRadio);

        return graphData;
    }
}
