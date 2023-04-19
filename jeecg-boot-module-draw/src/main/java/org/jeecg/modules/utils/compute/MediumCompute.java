package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.*;
import org.jeecg.modules.utils.TableCoefficient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class MediumCompute {
    public static GraphData compute(Draw drawData) {
        DecimalFormat df = new DecimalFormat("#.####");

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
        double xMediumGraduation = 2 * DoubleStream.of(xMedium).max().orElse(0);
        // 极差图
        double rGraduation = 2 * DoubleStream.of(r).max().orElse(0);

        // 控制界限
        // 这里没有写 subgroupCapacity > 25 时该如何取值
        String quantile = drawData.getQuantile();
        double uclXMedium; double lclXMedium; double clXMedium;
        double uclR; double lclR; double clR;
        if (quantile.equals("不使用")) {
            uclXMedium = xMediumBar + TableCoefficient.m3A2[subgroupCapacity] * rBar;
            lclXMedium = xMediumBar - TableCoefficient.m3A2[subgroupCapacity] * rBar;
            clXMedium = xMediumBar;
            uclR = TableCoefficient.D4[subgroupCapacity] * rBar;
            lclR = TableCoefficient.D3[subgroupCapacity] * rBar;
            clR = rBar;
        } else {
            double theataXMediumEstimate = xMediumBar;
            double theataREstimate = rBar;

            double tmp3 = 0; double tmp4 = 0; double tmp5 = 0; double tmp6 = 0;
            for (int i = 0; i < subgroupTotal; i++) {
                tmp3 = tmp3 + Math.pow(xMedium[i] - xMediumBar,2);
                tmp4 = tmp4 + Math.pow(xMedium[i] - xMediumBar,3);
                tmp5 = tmp5 + Math.pow(r[i] - rBar,2);
                tmp6 = tmp6 + Math.pow(r[i] - rBar,3);
            }
            double sigmaXMediumEstimate = Math.sqrt(1.0 * tmp3 / (subgroupTotal-1));
            double sigmaREstimate = Math.sqrt(1.0 * tmp5 / (subgroupTotal-1));
            double mu3XMediumEstimate = tmp4 / subgroupTotal;
            double mu3REstimate = tmp6 / subgroupTotal;

            uclXMedium = theataXMediumEstimate + 3 * sigmaXMediumEstimate + mu3XMediumEstimate * (3*3-1)/(6*Math.pow(sigmaXMediumEstimate, 2));
            lclXMedium = theataXMediumEstimate - 3 * sigmaXMediumEstimate + mu3XMediumEstimate * (3*3-1)/(6*Math.pow(sigmaXMediumEstimate, 2));
            clXMedium = theataXMediumEstimate + mu3XMediumEstimate * (-1)/(6* Math.pow(sigmaXMediumEstimate, 2));
            uclR    = theataREstimate + 3 * sigmaREstimate + mu3REstimate * (3*3-1)/(6* Math.pow(sigmaREstimate, 2));
            lclR    = theataREstimate - 3 * sigmaREstimate + mu3REstimate * (3*3-1)/(6* Math.pow(sigmaREstimate, 2));
            clR     = theataREstimate + mu3REstimate * (-1)/(6* Math.pow(sigmaREstimate, 2));
        }

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
        String ca = df.format(Math.abs((xMediumBar - (usl + lsl) / 2) / ((usl - lsl) / 2) * 100))+ "%";
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
        String pointsSpecialRadioXMedium = df.format(specialPointsXMedium.size() * 100.0 / subgroupTotal) + "%";
        String pointsSpecialRadioR =    df.format(specialPointsR.size() * 100.0 / subgroupTotal) + "%";

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

        // 上侧链集合
        List<ArrayList<Integer> > upperChainXMediumList = ChainCount.upperChainCount(n, subgroupTotal, xMedium, xMediumBar);
        List<ArrayList<Integer> > upperChainRList = ChainCount.upperChainCount(n, subgroupTotal, r, rBar);




        // 明显非随机图形
        // ......
        // double intervalXMedium = (uclXMedium - xMediumBar) / 3;
        // double intervalR =    (uclR - rBar) / 3;
        // double[] intervalValuesXMedium = new double[]{uclXMedium, xMediumBar+intervalXMedium*2, xMediumBar+intervalXMedium, xMediumBar, xMediumBar-intervalXMedium, xMediumBar-intervalXMedium*2, lclXMedium};
        // double[] intervalValuesR    = new double[]{uclR,       rBar+intervalR*2 ,            rBar+intervalR ,            rBar,       rBar-intervalR,             rBar-intervalR*2,             lclR};
        double intervalCXMedium = (uclXMedium - xMediumBar) / 3 ;
        double lowerCXMedium = xMediumBar - intervalCXMedium;
        double upperCXMedium = xMediumBar + intervalCXMedium;

        int pointsCNumXMedium = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (xMedium[i] > lowerCXMedium && xMedium[i] < upperCXMedium) pointsCNumXMedium++;
        }
        double tmpXMedium = pointsCNumXMedium * 1.0 * 100 / subgroupTotal;
        tmpXMedium = Double.parseDouble(df.format(tmpXMedium));
        String pointsCRadioXMedium = tmpXMedium + "%";

        double intervalCR = (uclR - rBar) / 3 ;
        double lowerCR = rBar - intervalCR;
        double upperCR = rBar + intervalCR;

        int pointsCNumR = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (r[i] > lowerCR && r[i] < upperCR) pointsCNumR++;
        }
        double tmpR = pointsCNumR * 1.0 * 100 / subgroupTotal;
        tmpR = Double.parseDouble(df.format(tmpR));
        String pointsCRadioR = tmpR + "%";

        for  (int i = 0; i < subgroupTotal; i++) xMedium[i] = Double.parseDouble(df.format(xMedium[i]));
        for  (int i = 0; i < subgroupTotal; i++) r[i] = Double.parseDouble(df.format(r[i]));
        xMediumBar = Double.parseDouble(df.format(xMediumBar));
        xMax = Double.parseDouble(df.format(xMax));
        xMin = Double.parseDouble(df.format(xMin));
        xAvg = Double.parseDouble(df.format(xAvg));
        avgSubgroupMid = Double.parseDouble(df.format(avgSubgroupMid));
        usl  = Double.parseDouble(df.format(usl));
        sl   = Double.parseDouble(df.format(sl));
        lsl  = Double.parseDouble(df.format(lsl));
        uclXMedium = Double.parseDouble(df.format(uclXMedium));
        clXMedium  = Double.parseDouble(df.format(clXMedium));
        lclXMedium = Double.parseDouble(df.format(lclXMedium));
        uclR = Double.parseDouble(df.format(uclR));
        clR  = Double.parseDouble(df.format(clR));
        lclR = Double.parseDouble(df.format(lclR));
        skewnessX = Double.parseDouble(df.format(skewnessX));
        kurtosisX = Double.parseDouble(df.format(kurtosisX));
        ppm   = Double.parseDouble(df.format(ppm));
        pp    = Double.parseDouble(df.format(pp));
        ppk   = Double.parseDouble(df.format(ppk));
        stdX  = Double.parseDouble(df.format(stdX));
        sigma = Double.parseDouble(df.format(sigma));
        cp    = Double.parseDouble(df.format(cp));
        cpu   = Double.parseDouble(df.format(cpu));
        cpl   = Double.parseDouble(df.format(cpl));
        cpk   = Double.parseDouble(df.format(cpk));



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
        // System.out.println("intervalValuesXMedium = " + Arrays.toString(intervalValuesXMedium));
        // System.out.println("intervalValuesR = " + Arrays.toString(intervalValuesR)); // --------------------------------------------------------------------------------

        // 设置返回体
        GraphDataMedium graphData = new GraphDataMedium();

        graphData.setGraphType(graphType);
        graphData.setDataArray(dataArray);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSubgroupTotal(subgroupTotal);
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
        graphData.setQuantile(quantile);
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
        graphData.setGraduationXMedium(xMediumGraduation);
        graphData.setGraduationR(rGraduation);
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
        // graphData.setIntervalXMediumValues(intervalValuesXMedium);
        // graphData.setIntervalRValues(intervalValuesR);
        graphData.setPointsCRadioXMedium(pointsCRadioXMedium);
        graphData.setPointsCRadioR(pointsCRadioR);
        graphData.setPointsSpecialRadioXMedium(pointsSpecialRadioXMedium);
        graphData.setPointsSpecialRadioR(pointsSpecialRadioR);

        return graphData;
    }
}
