package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataCnP;

import java.text.DecimalFormat;
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
        double pBar  = defectsNum * 1.0 / samplesNum;  // 计算用

        // 平均不合格品数
        double npBar = defectsNum * 1.0 / subgroupTotal;

        // 控制图刻度
        double graduationNP = DoubleStream.of(np).max().orElse(0) * 2;

        // 控制界限
        String quantile = drawData.getQuantile();
        double uclNP; double lclNP; double clNP;
        if (quantile.equals("不使用")) {
            uclNP = subgroupCapacity * pBar + 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar));
            lclNP = subgroupCapacity * pBar - 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar));
            clNP = subgroupCapacity * pBar;
        } else {
            uclNP = subgroupCapacity * pBar + 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar)) + 4.0 * (1 - 2*pBar) / 3;
            lclNP = subgroupCapacity * pBar - 3 * Math.sqrt(subgroupCapacity * pBar * (1 - pBar)) + 4.0 * (1 - 2*pBar) / 3;
            clNP = subgroupCapacity * pBar - 1.0 * (1- 2*pBar)/6;
        }
        if (lclNP < 0) lclNP = 0;



        // 分析
        DecimalFormat df = new DecimalFormat("#.###");
        // 超出控制线的点
        List<Integer> specialPointsNP = new ArrayList<>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (np[i] < lclNP || np[i] > uclNP)    specialPointsNP.add(i+1);
        }
        String pointsSpecialRadio = df.format(specialPointsNP.size() * 100.0 / subgroupTotal) + "%";

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainNPList= ChainCount.descendChainCount(n, subgroupTotal, np);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainNPList  = ChainCount.ascendChainCount(n, subgroupTotal, np);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainNPList = ChainCount.lowerChainCount(n, subgroupTotal, np, clNP);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainNPList = ChainCount.upperChainCount(n, subgroupTotal, np, clNP);

        // 明显非随机图形
        // ......
        double intervalC = (uclNP -npBar) / 3 ;
        double lowerC = 0;
        double upperC = npBar + intervalC;
        if (npBar - intervalC > 0) lowerC = npBar - intervalC;

        int pointsCNum = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (np[i] > lowerC && np[i] < upperC) pointsCNum++;
        }
        double tmp = pointsCNum * 1.0 * 100 / subgroupTotal;
        tmp = Double.parseDouble(df.format(tmp));
        String pointsCRadio = tmp + "%";

        //double intervalNP = (uclNP - npBar) / 3 ;
        //List<Double> intervalsNP = new ArrayList<>();
        //intervalsNP.add(uclNP);
        //intervalsNP.add(Double.valueOf(df.format(npBar+intervalNP*2)));
        //intervalsNP.add(Double.valueOf(df.format(npBar+intervalNP)));
        //intervalsNP.add(Double.valueOf(df.format(npBar)));
        //if (npBar-intervalNP > 0) intervalsNP.add(Double.valueOf(df.format(npBar-intervalNP)));
        //if (npBar-intervalNP*2 > 0) intervalsNP.add(Double.valueOf(df.format(npBar-intervalNP*2)));
        //if (lclNP > 0) intervalsNP.add(lclNP);
        //intervalsNP.add(0.0);

        npBar = Double.parseDouble(df.format(npBar));
        uclNP = Double.parseDouble(df.format(uclNP));
        lclNP = Double.parseDouble(df.format(lclNP));
        clNP =  Double.parseDouble(df.format(clNP));




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
        // System.out.println("intervalsCNP= " + intervalsNP);
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
        graphData.setQuantile(quantile);
        graphData.setGraduation(graduationNP);
        graphData.setSpecialPoints(specialPointsNP);
        graphData.setDescendChainList(descendChainNPList);
        graphData.setAscendChainList(ascendChainNPList);
        graphData.setUpperChainList(upperChainNPList);
        graphData.setLowerChainList(lowerChainNPList);
        graphData.setPointsCRadio(pointsCRadio);
        graphData.setPointsSpecialRadio(pointsSpecialRadio);
        // graphData.setIntervalValues(intervalsNP);

        return graphData;
    }
}
