package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataPTUT;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class PTCompute {
    public static GraphDataPTUT compute(Draw drawData) {
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

        // 标准化处理
        double[] pt = new double[subgroupTotal];
        for (int i = 0; i < subgroupTotal; i++) {
            pt[i] = (p[i] - pBar) / Math.sqrt(pBar * (1-pBar) / dataArraySubgroupsCapacity[i]);
            pt[i] = Double.parseDouble(df.format(pt[i]));
        }

        // 控制图坐标刻度 ...
        double graduation = DoubleStream.of(pt).max().orElse(0) * 2;
        graduation = (int) graduation + 1;
        graduation = Math.max(graduation, 3);

        // 上下控制限
        double uclPt = 3;
        double lclPt = -3;
        double clPt = 0;


        // 分析
        // 超出控制线的点
        List<Integer> specialPointsPt = new ArrayList<>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (pt[i] < lclPt || pt[i] > uclPt)    specialPointsPt.add(i+1);
        }
        String pointsSpecialRadio = df.format(specialPointsPt.size() * 100.0 / subgroupTotal) + "%";

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainPtList= ChainCount.descendChainCount(n, subgroupTotal, pt);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainPtList  = ChainCount.ascendChainCount(n, subgroupTotal, pt);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainPtList = ChainCount.lowerChainCount(n, subgroupTotal, pt, 0);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainPtList = ChainCount.upperChainCount(n, subgroupTotal, pt, 0);



        // 明显非随机图形
        // ......
        double lowerC = -1; double upperC = 1;
        int pointsCNum = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (pt[i] > lowerC && pt[i] < upperC) pointsCNum++;
        }
        double tmp = pointsCNum * 100.0 / subgroupTotal;
        tmp = Double.parseDouble(df.format(tmp));
        String pointsCRaido = tmp + "%";

        pBar = Double.parseDouble(df.format(pBar));
        avgSubgroupCapacity = Double.parseDouble(df.format(avgSubgroupCapacity));
        graduation = Double.parseDouble(df.format(graduation));
        //uclPt = Double.parseDouble(df.format(uclPt));
        //lclPt = Double.parseDouble(df.format(lclPt));
        //clCPt=  Double.parseDouble(df.format(clPt));



        // 调试代码 ----------------------------------------------------------------------
        System.out.println("p = " + Arrays.toString(p));
        System.out.println("pBar = " + pBar);

        System.out.println("pt = " + Arrays.toString(pt));

        System.out.println("graduation = " + graduation);
        System.out.println("uclPt = " + uclPt);
        System.out.println("lclPt = " + lclPt);
        System.out.println("specialPointsPt = " + specialPointsPt);
        System.out.println("descendChainPtList = " + descendChainPtList);
        System.out.println("ascendChainPtList = " + ascendChainPtList);
        System.out.println("upperChainPtList = " + upperChainPtList);
        System.out.println("lowerChainPtList = " + lowerChainPtList);
        // System.out.println("intervalValuesPt = " + Arrays.toString(intervalValuesPt));
        // ---------------------------------------------------------------------------------



        // 返回体设置
        GraphDataPTUT graphData = new GraphDataPTUT();

        graphData.setGraphType(graphType);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setAvgSubgroupCapacity(avgSubgroupCapacity);
        graphData.setSubgroupCapacityMax(subgroupCapactityMax);
        graphData.setSubgroupCapacityMin(subgroupCapactityMin);
        graphData.setSamplesNum(samplesNum);
        graphData.setDefectsNum(defectsNum);
        graphData.setAvgDefectsNum(avgDefectsNum);
        graphData.setUcl(uclPt);
        graphData.setCl(clPt);
        graphData.setLcl(lclPt);
        graphData.setDataArray(pt);
        graphData.setGraduation(graduation);
        graphData.setSpecialPoints(specialPointsPt);
        graphData.setAscendChainList(ascendChainPtList);
        graphData.setDescendChainList(descendChainPtList);
        graphData.setUpperChainList(upperChainPtList);
        graphData.setLowerChainList(lowerChainPtList);
        // graphData.setIntervalValues(intervalValuesPt);
        graphData.setPointsCRadio(pointsCRaido);
        graphData.setPointsSpecialRadio(pointsSpecialRadio);

        return graphData;
    }
}
