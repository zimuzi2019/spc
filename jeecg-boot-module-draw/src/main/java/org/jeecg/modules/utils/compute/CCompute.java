package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataCnP;

import java.text.DecimalFormat;
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
        double cBar = defectsNum * 1.0 / subgroupTotal;

        // 控制图刻度
        double graduationC = DoubleStream.of(c).max().orElse(0) * 2;

        // 控制界限
        String quantile = drawData.getQuantile();
        double uclC; double lclC; double clC;
        if (quantile.equals("不使用")) {
            uclC = cBar + 3 * Math.sqrt(cBar);
            lclC = cBar - 3 * Math.sqrt(cBar);
            clC = cBar;
        } else {
            uclC = cBar + 3 * Math.sqrt(cBar) + 4.0/3;
            lclC = cBar - 3 * Math.sqrt(cBar) + 4.0/3;
            clC =  cBar - 1.0/6;
        }
        if ( lclC < 0 ) lclC = 0;



        // 分析
        DecimalFormat df = new DecimalFormat("#.###");
        // 超出控制线的点
        List<Integer> specialPointsC = new ArrayList<>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (c[i] < lclC || c[i] > uclC)    specialPointsC.add(i+1);
        }
        String pointsSpecialRadio = df.format(specialPointsC.size() * 100.0 / subgroupTotal) + "%";

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainCList= ChainCount.descendChainCount(n, subgroupTotal, c);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainCList  = ChainCount.ascendChainCount(n, subgroupTotal, c);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainCList = ChainCount.lowerChainCount(n, subgroupTotal, c, clC);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainCList = ChainCount.upperChainCount(n, subgroupTotal, c, clC);


        // 明显非随机图形
        // ......

        double intervalC = (uclC -cBar) / 3 ;
        double lowerC = 0;
        double upperC = cBar + intervalC;
        if (cBar - intervalC > 0) lowerC = cBar - intervalC;

        int pointsCNum = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (c[i] > lowerC && c[i] < upperC) pointsCNum++;
        }
        double tmp = pointsCNum * 1.0 * 100 / subgroupTotal;
        tmp = Double.parseDouble(df.format(tmp));
        String pointsCRadio = tmp + "%";

        //List<Double> intervalsC = new ArrayList<>();
        //intervalsC.add(uclC);
        //intervalsC.add(Double.valueOf(df.format(cBar+intervalC*2)));
        //intervalsC.add(Double.valueOf(df.format(cBar+intervalC)));
        //intervalsC.add(Double.valueOf(df.format(cBar)));
        //if (cBar-intervalC > 0) intervalsC.add(Double.valueOf(df.format(cBar-intervalC)));
        //if (cBar-intervalC*2 > 0) intervalsC.add(Double.valueOf(df.format(cBar-intervalC*2)));
        //if (lclC > 0) intervalsC.add(lclC);
        //intervalsC.add(0.0);

        cBar = Double.parseDouble(df.format(cBar));
        uclC = Double.parseDouble(df.format(uclC));
        lclC = Double.parseDouble(df.format(lclC));
        clC =  Double.parseDouble(df.format(clC));


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
        // System.out.println("intervalsC = " + intervalsC);
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
        graphData.setQuantile(quantile);
        graphData.setDataArray(c);
        graphData.setGraduation(graduationC);
        graphData.setSpecialPoints(specialPointsC);
        graphData.setDescendChainList(descendChainCList);
        graphData.setAscendChainList(ascendChainCList);
        graphData.setUpperChainList(upperChainCList);
        graphData.setLowerChainList(lowerChainCList);
        graphData.setPointsCRadio(pointsCRadio);
        graphData.setPointsSpecialRadio(pointsSpecialRadio);
        // graphData.setIntervalValues(intervalsC);

        return graphData;
    }
}
