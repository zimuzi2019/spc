package org.jeecg.modules.utils.compute;

import org.apache.commons.math3.distribution.FDistribution;
import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.entity.GraphDataFirstOrderNested;
import org.jeecg.modules.business.entity.GraphDataSecondOrderNested;
import org.jeecg.modules.utils.TableCoefficient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class SecondOrderNestedCompute {
    public static GraphData compute(Draw drawData) {
        DecimalFormat df = new DecimalFormat("#.####");

        String graphType = drawData.getGraphType();

        double[][][] dataArraySecondOrderNested = drawData.getDataArraySecondOrderNested();
        int batchNum = drawData.getBatchNum();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int subgroupTotal = drawData.getSubgroupTotal();


        double[][] dataArray = new double[batchNum][subgroupTotal];
        for (int i = 0; i < batchNum; i++) {
            for (int j = 0; j < subgroupTotal; j++) {
                for (int k = 0; k < subgroupCapacity; k++) {
                    dataArray[i][j] = DoubleStream.of(dataArraySecondOrderNested[i][j]).sum() / subgroupCapacity;
                }
            }
        }

        // 每个批次的均值
        double[] xBar = new double[batchNum];
        // 每个批次的标准差
        double[] s = new double[batchNum];

        for(int i = 0; i < batchNum; i++) {
            double batchSum = DoubleStream.of(dataArray[i]).sum();
            xBar[i] = batchSum / subgroupTotal;

            // 暂存 (x-xBar[i])^2 的和
            double sum = 0;
            for (double x : dataArray[i]) {
                sum = sum + Math.pow(x - xBar[i], 2);
            }
            s[i] = Math.sqrt(sum / (subgroupTotal-1) );
        }

        // 过程均值
        double xDoubleBar = DoubleStream.of(xBar).sum()  / batchNum;

        double sBar = DoubleStream.of(s).sum()  / batchNum;

        // 嵌套性检验
        double tmp1 = 0, tmp2 = 0;
        for (int i = 0; i < batchNum; i++) {
            tmp1 = tmp1 + Math.pow(xBar[i] - xDoubleBar, 2);
            for (int j = 0; j < subgroupTotal; j++) {
                tmp2 = tmp2 + Math.pow(dataArray[i][j] - xBar[i], 2);
            }
        }
        double msBetweenBatch = tmp1 * subgroupTotal  / (batchNum-1);
        double msWithinBatch  = tmp2 / (batchNum * (subgroupTotal-1));

        double tmpStatistic = msBetweenBatch / msWithinBatch; String nestedness;
        FDistribution fd = new FDistribution((batchNum-1), batchNum*(subgroupTotal-1));

        if (tmpStatistic >= fd.inverseCumulativeProbability(1-0.01)) {
            nestedness = "嵌套性非常显著";
        } else if (tmpStatistic >= fd.inverseCumulativeProbability(1-0.05)) {
            nestedness = "嵌套性显著";
        } else if (tmpStatistic >= fd.inverseCumulativeProbability(1-0.1)) {
            nestedness = "具有嵌套性";
        } else {
            nestedness = "不具有嵌套性";
        }

        // 控制图刻度
        // 均值图
        double xBarGraduation = 2 * DoubleStream.of(xBar).max().orElse(0) ;
        // 标准偏差图
        double sGraduation = 2 * DoubleStream.of(s).max().orElse(0);

        // 控制界限
        double tmp3 = 0;
        for (int i = 0; i < batchNum; i++) tmp3 = tmp3 + Math.pow(xBar[i]-xDoubleBar, 2);
        double sXBar = Math.sqrt(tmp3 / (batchNum-1));

        double uclXBar = xDoubleBar + 3 * sXBar;
        double lclXBar = xDoubleBar - 3 * sXBar;
        double clXBar = xDoubleBar;

        double uclS; double lclS; double clS = sBar;
        if (subgroupTotal <= 25) {
            uclS = TableCoefficient.B4[subgroupTotal] * sBar;
            lclS = TableCoefficient.B3[subgroupTotal] * sBar;
        } else {
            double d = 1 - 3 / Math.sqrt(2 * subgroupTotal - 2.5);
            double e = 1 + 3 / Math.sqrt(2 * subgroupTotal - 2.5);

            uclS = e * sBar;
            lclS = d * sBar;
        }

        // 分析
        // 超出控制线的点
        List<Integer> specialPointsXBar = new ArrayList<>();
        List<Integer> specialPointsS = new ArrayList<>();


        for (int i = 0; i < batchNum; i++) {
            if (s[i] < lclS || s[i] > uclS)               specialPointsS.add(i+1);
            if (xBar[i] < lclXBar || xBar[i] > uclXBar)   specialPointsXBar.add(i+1);
        }
        String pointsSpecialRadioXBar = df.format(specialPointsXBar.size() * 100.0 / batchNum) + "%";
        String pointsSpecialRadioS    = df.format(specialPointsS.size() * 100.0 / batchNum) + "%";


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXBarList = ChainCount.descendChainCount(n, batchNum, xBar);
        List<ArrayList<Integer> > descendChainSList = ChainCount.descendChainCount(n, batchNum, s);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXBarList  = ChainCount.ascendChainCount(n, batchNum, xBar);
        List<ArrayList<Integer> > ascendChainSList  = ChainCount.ascendChainCount(n, batchNum, s);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXBarList = ChainCount.lowerChainCount(n, batchNum, xBar, xDoubleBar);
        List<ArrayList<Integer> > lowerChainSList = ChainCount.lowerChainCount(n, batchNum, s, sBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXBarList = ChainCount.upperChainCount(n, batchNum, xBar, xDoubleBar);
        List<ArrayList<Integer> > upperChainSList = ChainCount.upperChainCount(n, batchNum, s, sBar);


        double intervalCXBar = (uclXBar - xDoubleBar) / 3 ;
        double lowerCXBar = xDoubleBar - intervalCXBar;
        double upperCXBar = xDoubleBar + intervalCXBar;

        int pointsCNumXBar = 0;
        for (int i = 0; i < batchNum; i++) {
            if (xBar[i] > lowerCXBar && xBar[i] < upperCXBar) pointsCNumXBar++;
        }
        double tmpXBar = pointsCNumXBar * 1.0 * 100 / batchNum;
        tmpXBar = Double.parseDouble(df.format(tmpXBar));
        String pointsCRadioXBar = tmpXBar + "%";

        double intervalCS = (uclS - sBar) / 3 ;
        double lowerCS = sBar - intervalCS;
        double upperCS = sBar + intervalCS;

        int pointsCNumS = 0;
        for (int i = 0; i < batchNum; i++) {
            if (s[i] > lowerCS && s[i] < upperCS) pointsCNumS++;
        }
        double tmpS = pointsCNumS * 1.0 * 100 / batchNum;
        tmpS = Double.parseDouble(df.format(tmpS));
        String pointsCRadioS = tmpS + "%";

        for  (int i = 0; i < batchNum; i++) xBar[i] = Double.parseDouble(df.format(xBar[i]));
        for  (int i = 0; i < batchNum; i++) s[i] = Double.parseDouble(df.format(s[i]));

        // xDoubleBar = Double.parseDouble(df.format(xDoubleBar));
        uclXBar = Double.parseDouble(df.format(uclXBar));
        clXBar  = Double.parseDouble(df.format(clXBar));
        lclXBar = Double.parseDouble(df.format(lclXBar));
        uclS = Double.parseDouble(df.format(uclS));
        clS  = Double.parseDouble(df.format(clS));
        lclS = Double.parseDouble(df.format(lclS));


        System.out.println("uclXBar = " + uclXBar);
        System.out.println("clXBar = " + clXBar);
        System.out.println("lclXBar = " + lclXBar);
        System.out.println("uclS = " + uclS);
        System.out.println("clS = " + clS);
        System.out.println("lclS = " + lclS);

        // 设置返回体
        GraphDataSecondOrderNested graphData = new GraphDataSecondOrderNested();

        graphData.setGraphType(graphType);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setBatchNum(batchNum);
        graphData.setNestedness(nestedness);
        graphData.setUclXBar(uclXBar);
        graphData.setClXBar(clXBar);
        graphData.setLclXBar(lclXBar);
        graphData.setUclS(uclS);
        graphData.setClS(clS);
        graphData.setLclS(lclS);
        graphData.setGraduationXBar(xBarGraduation);
        graphData.setGraduationS(sGraduation);
        graphData.setDataArrayXBar(xBar);
        graphData.setDataArrayS(s);
        graphData.setSpecialPointsXBar(specialPointsXBar);
        graphData.setSpecialPointsS(specialPointsS);
        graphData.setDescendChainXBarList(descendChainXBarList);
        graphData.setAscendChainXBarList(ascendChainXBarList);
        graphData.setUpperChainXBarList(upperChainXBarList);
        graphData.setLowerChainXBarList(lowerChainXBarList);
        graphData.setDescendChainSList(descendChainSList);
        graphData.setAscendChainSList(ascendChainSList);
        graphData.setUpperChainSList(upperChainSList);
        graphData.setLowerChainSList(lowerChainSList);
        graphData.setPointsCRadioXBar(pointsCRadioXBar);
        graphData.setPointsCRadioS(pointsCRadioS);
        graphData.setPointsSpecialRadioXBar(pointsSpecialRadioXBar);
        graphData.setPointsSpecialRadioS(pointsSpecialRadioS);

        return graphData;
    }
}
