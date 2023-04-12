package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataPU;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class PCompute {
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

        //每个子组的不合格品率
        double[] p = new double[subgroupTotal];

        for (int i = 0; i < subgroupTotal; i++) {
            p[i] = dataArrayDefectsNum[i] * 1.0 / dataArraySubgroupsCapacity[i];
        }

        // 平均不合格品率
        double pBar = IntStream.of(dataArrayDefectsNum).sum() * 1.0 / IntStream.of(dataArraySubgroupsCapacity).sum();
        String avgDefectsNum = df.format(pBar * 100) + "%";

        // 控制图坐标刻度 ...
        double graduation = DoubleStream.of(p).max().orElse(0) * 2;
        graduation = (int) graduation + 1;

        // 上下控制限
        String quantile = drawData.getQuantile();
        double[] uclP = new double[subgroupTotal]; double[] lclP = new double[subgroupTotal]; double[] clP =  new  double[subgroupTotal];
        for (int i = 0; i < subgroupTotal; i++) {
            if (quantile.equals("不使用")) {
                uclP[i] = pBar + 3 * Math.sqrt(pBar * (1-pBar) / dataArraySubgroupsCapacity[i]);
                lclP[i] = pBar - 3 * Math.sqrt(pBar * (1-pBar )/ dataArraySubgroupsCapacity[i]);
                 clP[i] = pBar;
            } else {
                uclP[i] = pBar + 3 * Math.sqrt(pBar * (1-pBar) / dataArraySubgroupsCapacity[i]) + 4.0 * (1-2*pBar)/(3 * dataArraySubgroupsCapacity[i]);
                lclP[i] = pBar - 3 * Math.sqrt(pBar * (1-pBar) / dataArraySubgroupsCapacity[i]) + 4.0 * (1-2*pBar)/(3 * dataArraySubgroupsCapacity[i]);
                 clP[i] = pBar - (1-2*pBar)/ (6*dataArraySubgroupsCapacity[i]);
            }
            uclP[i] = Double.parseDouble(df.format(uclP[i]));
            lclP[i] = Double.parseDouble(df.format(lclP[i]));
             clP[i] = Double.parseDouble(df.format(clP[i]));

            if(lclP[i] < 0) lclP[i] = 0;
        }


        // 分析
        // 超出控制线的点
        List<Integer> specialPointsP = new ArrayList<>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (p[i] < lclP[i] || p[i] > uclP[i])    specialPointsP.add(i+1);
        }
        String pointsSpecialRadio = df.format(specialPointsP.size() * 100.0 / subgroupTotal) + "%";

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainPList= ChainCount.descendChainCount(n, subgroupTotal, p);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainPList  = ChainCount.ascendChainCount(n, subgroupTotal, p);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainPList = ChainCount.lowerChainCountPU(n, subgroupTotal, p, clP);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainPList = ChainCount.upperChainCountPU(n, subgroupTotal, p, clP);



        // 明显非随机图形
        // ......
        int pointsCNum = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            double upperC = (uclP[i] - clP[i])/3 + clP[i];
            double lowerC = -(uclP[i] - clP[i])/3 + clP[i];
            if (p[i] > lowerC && p[i] < upperC) pointsCNum++;
        }
        double tmp = pointsCNum * 100.0 / subgroupTotal;
        tmp = Double.parseDouble(df.format(tmp));
        String pointsCRaido = tmp + "%";

        pBar = Double.parseDouble(df.format(pBar));
        avgSubgroupCapacity = Double.parseDouble(df.format(avgSubgroupCapacity));
        graduation = Double.parseDouble(df.format(graduation));
        //uclP = Double.parseDouble(df.format(uclP));
        //lclP = Double.parseDouble(df.format(lclP));
        //clCP=  Double.parseDouble(df.format(clP));



        // 调试代码 ----------------------------------------------------------------------
        System.out.println("p = " + Arrays.toString(p));
        System.out.println("pBar = " + pBar);

        System.out.println("pt = " + Arrays.toString(p));

        System.out.println("graduation = " + graduation);
        System.out.println("uclP = " + uclP);
        System.out.println("lclP = " + lclP);
        System.out.println("specialPointsP = " + specialPointsP);
        System.out.println("descendChainPList = " + descendChainPList);
        System.out.println("ascendChainPList = " + ascendChainPList);
        System.out.println("upperChainPList = " + upperChainPList);
        System.out.println("lowerChainPList = " + lowerChainPList);
        // System.out.println("intervalValuesP = " + Arrays.toString(intervalValuesP));
        // ---------------------------------------------------------------------------------



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
        graphData.setUcl(uclP);
        graphData.setCl(clP);
        graphData.setLcl(lclP);
        graphData.setQuantile(quantile);
        graphData.setDataArray(p);
        graphData.setGraduation(graduation);
        graphData.setSpecialPoints(specialPointsP);
        graphData.setAscendChainList(ascendChainPList);
        graphData.setDescendChainList(descendChainPList);
        graphData.setUpperChainList(upperChainPList);
        graphData.setLowerChainList(lowerChainPList);
        // graphData.setIntervalValues(intervalValuesP);
        graphData.setPointsCRadio(pointsCRaido);
        graphData.setPointsSpecialRadio(pointsSpecialRadio);

        return graphData;
    }
}
