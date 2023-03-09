package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.*;
import org.jeecg.modules.utils.TableCoefficient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class MediumCompute {
    public static GraphData compute(Draw drawData) {
        String graphType = drawData.getGraphType();

        double[][] dataArray = drawData.getDataArrayXRXSMedium();

        int subgroupCapacity = drawData.getSubgroupCapacity();
        int subgroupTotal = drawData.getSubgroupTotal();
        int samplesNum = subgroupCapacity * subgroupTotal;

        double usl = drawData.getUsl();
        double lsl = drawData.getLsl();
        double sl = drawData.getSl();

        // 求样本最大值、最大值、中位数
        double[] tmpArray = new double[samplesNum];
        for (int i = 0; i < subgroupTotal; i++) {
            for (int j = 0; j < subgroupCapacity; j++) tmpArray[i * subgroupCapacity + j] = dataArray[i][j];
        }
        double xMax = DoubleStream.of(tmpArray).max().orElse(0);
        double xMin = DoubleStream.of(tmpArray).min().orElse(0);
        double xAvg = DoubleStream.of(tmpArray).sum() / samplesNum;


        // 样本标准差
        double tmp = 0;
        for (int i = 0; i < samplesNum; i++) tmp = tmp + Math.pow(tmpArray[i] - xAvg, 2);
        double stdX = Math.sqrt(tmp / (samplesNum-1));

        // 样本偏度
        double tmp1 = 0;
        for (int i = 0; i < samplesNum; i++) tmp1 = tmp1 + Math.pow( (tmpArray[i] - xAvg)/stdX, 3 );
        double skewnessX = samplesNum * tmp1 / ((samplesNum -1) * (samplesNum-2)) ;

        // 样本峰度
        double tmp2 = 0;
        for (int i = 0; i < samplesNum; i++) tmp2 = tmp2 + Math.pow( (tmpArray[i] - xAvg)/stdX, 4);
        double kurtosisX = samplesNum * (samplesNum + 1) * tmp2 / ((samplesNum - 1) * (samplesNum - 2) * (samplesNum - 3)) - 3 * Math.pow( (samplesNum - 1), 2) / ((samplesNum - 2) * (samplesNum - 3));

        // 每个子组的中位数
        double[] xMedium = new double[subgroupTotal];
        // 每个子组的极差
        double[] r = new double[subgroupTotal];

        double tmpSum = 0;
        for(int i = 0; i < subgroupTotal; i++) {
            Arrays.sort(dataArray[i]);
            if (subgroupCapacity%2 == 0) {
                xMedium[i] = (dataArray[i][subgroupCapacity/2 -1] + dataArray[i][subgroupCapacity/2]) / 2;
            } else {
                xMedium[i] = dataArray[i][subgroupCapacity/2];
            }

            double subgroupMin = dataArray[i][0];
            double subgroupMax = dataArray[i][subgroupCapacity-1];

            r[i] = subgroupMax - subgroupMin;


            double[] subgroupCopy = dataArray[i].clone();
            Arrays.sort(subgroupCopy);
            int len = subgroupCapacity; double subgroupMid = 0;
            if (len % 2 == 0)  subgroupMid = (subgroupCopy[len / 2] + subgroupCopy[len / 2 - 1]) / 2;
            else subgroupMid = subgroupCopy[len / 2];
            tmpSum = tmpSum + subgroupMid;
        }
        // 平均中位数
        double avgSubgroupMid = tmpSum / subgroupTotal;

        // 过程均值
        double xMediumBar = DoubleStream.of(xMedium).sum()  / subgroupTotal;

        // 平均极差值
        double rBar = DoubleStream.of(r).sum() / subgroupTotal;

        // 控制图刻度
        // 均值图
        double xMediumGraduation = 2 * (DoubleStream.of(xMedium).max().orElse(0) - DoubleStream.of(xMedium).min().orElse(0));
        // 极差图
        double rGraduation = 2 * DoubleStream.of(r).max().orElse(0);

        // 控制界限
        // 这里没有写 subgroupCapacity > 25 时该如何取值
        double uclXMedium = xMediumBar + TableCoefficient.A2[subgroupCapacity] * rBar;
        double lclXMedium = xMediumBar - TableCoefficient.A2[subgroupCapacity] * rBar;
        double clXMedium = xMediumBar;
        double uclR = TableCoefficient.D4[subgroupCapacity] * rBar;
        double lclR = TableCoefficient.D3[subgroupCapacity] * rBar;
        double clR = rBar;

        // 过程的标准偏差
        double sigma = rBar / TableCoefficient.d2[subgroupCapacity];

        //单边容差
        double zUSL = (usl - xMediumBar) / sigma;
        double zLSL = (xMediumBar - lsl) / sigma;

        //双边容差
        double  z = Math.min(zUSL, zLSL);

        //过程能力上下限限
        double cpu = zUSL / 3;
        double cpl = zLSL / 3;
        //过程能力指数
        double cpk = z / 3;
        double pp = (usl- lsl) / (6 * stdX);
        double ppk = Math.min( (usl - xMediumBar) / (3 * stdX), (xMax - lsl) / (3 * stdX));
        String ca = Math.abs((xMediumBar - (usl + lsl) / 2) / ((usl - lsl) / 2) * 100) + "%";
        double cp = (usl - lsl) / (6 * sigma);

        // 预估不良率
        int defectsNum = 0;
        for (int i = 0; i < samplesNum; i++) {
            if (tmpArray[i] < lsl || tmpArray[i] > usl) defectsNum++;
        }
        double ppm = defectsNum * 1.0 / samplesNum * Math.pow(10, 6);

        // Grade
        String cpkGrade = CpkGradeDetermination.CpkGraderDetermination(cpk);



        // 分析
        // 超出控制线的点
        List<Integer> specialPointsXMedium = new ArrayList<>();
        List<Integer> specialPointsR = new ArrayList<>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (r[i] < lclR || r[i] > uclR)                           specialPointsR.add(i+1);
            if (xMedium[i] < lclXMedium || xMedium[i] > uclXMedium)   specialPointsXMedium.add(i+1);
        }


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXMediumList = ChainCount.descendChainCount(n, subgroupTotal, xMedium);
        List<ArrayList<Integer> > descendChainRList = ChainCount.descendChainCount(n, subgroupTotal, r);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXMediumList  = ChainCount.ascendChainCount(n, subgroupTotal, xMedium);
        List<ArrayList<Integer> > ascendChainRList  = ChainCount.ascendChainCount(n, subgroupTotal, r);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXMediumList = ChainCount.lowerChainCount(n, subgroupTotal, xMedium, xMediumBar);
        List<ArrayList<Integer> > lowerChainRList = ChainCount.lowerChainCount(n, subgroupTotal, r, rBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXMediumList = ChainCount.upperChainCount(n, subgroupTotal, xMedium, xMediumBar);
        List<ArrayList<Integer> > upperChainRList = ChainCount.upperChainCount(n, subgroupTotal, r, rBar);




        // 明显非随机图形
        // ......
        double intervalXMedium = (uclXMedium - xMediumBar) / 3;
        double intervalR =    (uclR - rBar) / 3;
        double[] intervalValuesXMedium = new double[]{uclXMedium, xMediumBar+intervalXMedium*2, xMediumBar+intervalXMedium, xMediumBar, xMediumBar-intervalXMedium, xMediumBar-intervalXMedium*2, lclXMedium};
        double[] intervalValuesR    = new double[]{uclR,       rBar+intervalR*2 ,            rBar+intervalR ,            rBar,       rBar-intervalR,             rBar-intervalR*2,             lclR};




        // 调试代码 ----------------------------------------------------------------------
        System.out.println("xMax = " + xMax);
        System.out.println("xMin = " + xMin);
        System.out.println("xAvg = " + xAvg);
        System.out.println("avgSubgroupMid = " + avgSubgroupMid);
        System.out.println("xMedium = " + Arrays.toString(xMedium));
        System.out.println("r = " + Arrays.toString(r));
        System.out.println("xMediumBar = " + xMediumBar);
        System.out.println("rBar = " + rBar);
        System.out.println("xMediumGraduation = " + xMediumGraduation);
        System.out.println("rGraduation = " + rGraduation);
        System.out.println("uclXMedium = " + uclXMedium);
        System.out.println("lclXMedium = " + lclXMedium);
        System.out.println("uclR = " + uclR);
        System.out.println("lclR = " + lclR);
        System.out.println("stdXBar = " + stdX);
        System.out.println("skewnessX = " + skewnessX);
        System.out.println("kurtosisX = " + kurtosisX);
        System.out.println("z = " + z);
        System.out.println("cpu = " + cpu);
        System.out.println("cpl = " + cpl);
        System.out.println("cpk = " + cpk);
        System.out.println("pp = " + pp);
        System.out.println("ppk = " + ppk);
        System.out.println("ca = " + ca);
        System.out.println("cp = " + cp);
        System.out.println("ppm = " + ppm);
        System.out.println("cpkGrade = " + cpkGrade);
        System.out.println("specialPointsXMedium = " + specialPointsXMedium);
        System.out.println("specialPointsR = " + specialPointsR);
        System.out.println("descendChainXMediumList = " + descendChainXMediumList);
        System.out.println("descendChainRList = " + descendChainRList);
        System.out.println("ascendChainXMediumList = " + ascendChainXMediumList);
        System.out.println("ascendChainRList = " + ascendChainRList);
        System.out.println("upperChainXMediumList = " + upperChainXMediumList);
        System.out.println("upperChainRList = " + upperChainRList);
        System.out.println("lowerChainXMediumList = " + lowerChainXMediumList);
        System.out.println("lowerChainRList = " + lowerChainRList);
        System.out.println("intervalValuesXMedium = " + Arrays.toString(intervalValuesXMedium));
        System.out.println("intervalValuesR = " + Arrays.toString(intervalValuesR)); // --------------------------------------------------------------------------------

        // 设置返回体
        GraphDataMedium graphData = new GraphDataMedium();

        graphData.setGraphType(graphType);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSubTotal(subgroupTotal);
        graphData.setSamplesNum(samplesNum);
        graphData.setAvgX(xAvg);
        graphData.setMaxX(xMax);
        graphData.setMinX(xMin);
        graphData.setAvgSubgroupMid(avgSubgroupMid);
        graphData.setUsl(usl);
        graphData.setSl(sl);
        graphData.setLsl(lsl);
        graphData.setUclXMedium(uclXMedium);
        graphData.setClXMedium(clXMedium);
        graphData.setLclXMedium(lclXMedium);
        graphData.setUclR(uclR);
        graphData.setClR(clR);
        graphData.setLclR(lclR);
        graphData.setSkewnessX(skewnessX);
        graphData.setKurtosisX(kurtosisX);
        graphData.setPpm(ppm);
        graphData.setStdX(stdX);
        graphData.setSigma(sigma);
        graphData.setPp(pp);
        graphData.setPpk(ppk);
        graphData.setCa(ca);
        graphData.setCp(cp);
        graphData.setCpu(cpu);
        graphData.setCpl(cpl);
        graphData.setCpk(cpk);
        graphData.setCpkGrade(cpkGrade);
        graphData.setDataArrayXMedium(xMedium);
        graphData.setDataArrayR(r);
        graphData.setSpecialPointsXMedium(specialPointsXMedium);
        graphData.setSpecialPointsR(specialPointsR);
        graphData.setDescendChainXMediumList(descendChainXMediumList);
        graphData.setAscendChainXMediumList(ascendChainXMediumList);
        graphData.setUpperChainXMediumList(upperChainXMediumList);
        graphData.setLowerChainXMediumList(lowerChainXMediumList);
        graphData.setDescendChainRList(descendChainRList);
        graphData.setAscendChainRList(ascendChainRList);
        graphData.setUpperChainRList(upperChainRList);
        graphData.setLowerChainRList(lowerChainRList);
        graphData.setIntervalXMediumValues(intervalValuesXMedium);
        graphData.setIntervalRValues(intervalValuesR);

        return graphData;
    }
}
