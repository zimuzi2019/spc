package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.entity.GraphDataXR;
import org.jeecg.modules.utils.TableCoefficient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class XRCompute {
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


        // 每个子组的均值
        double[] xBar = new double[subgroupTotal];
        // 每个子组的极差
        double[] r = new double[subgroupTotal];

        double tmpSum = 0;
        for(int i = 0; i < subgroupTotal; i++) {
            double subgroupSum = DoubleStream.of(dataArray[i]).sum();
            double subgroupMax = DoubleStream.of(dataArray[i]).max().orElse(0);
            double subgroupMin = DoubleStream.of(dataArray[i]).min().orElse(0);

            xBar[i] = subgroupSum / subgroupCapacity;
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
        double xDoubleBar = DoubleStream.of(xBar).sum()  / subgroupTotal;

        // 平均极差值
        double rBar = DoubleStream.of(r).sum() / subgroupTotal;

        // 控制图刻度
        // 均值图
        double xBarGraduation = 2 * DoubleStream.of(xBar).max().orElse(0);
        // 极差图
        double rGraduation = 2 * DoubleStream.of(r).max().orElse(0);
        // 极差图的刻度值和均值图的刻度值倍数关系未设置为2倍关系

        // 控制界限
        // 这里没有写 subgroupCapacity > 25 时该如何取值
        double uclXBar = xDoubleBar + TableCoefficient.A2[subgroupCapacity] * rBar;
        double lclXBar = xDoubleBar - TableCoefficient.A2[subgroupCapacity] * rBar;
        double clXBar = xDoubleBar;
        double uclR = TableCoefficient.D4[subgroupCapacity] * rBar;
        double lclR = TableCoefficient.D3[subgroupCapacity] * rBar;
        double clR = rBar;

        // 过程的标准偏差
        double sigma = rBar / TableCoefficient.d2[subgroupCapacity];

        //单边容差
        double zUSL = (usl - xDoubleBar) / sigma;
        double zLSL = (xDoubleBar - lsl) / sigma;

        //双边容差
        double  z = Math.min(zUSL, zLSL);

        //过程能力上下限限
        double cpu = zUSL / 3;
        double cpl = zLSL / 3;
        //过程能力指数
        double cpk = z / 3;
        double pp = (usl- lsl) / (6 * stdX);
        double ppk = Math.min( (usl - xDoubleBar) / (3 * stdX), (xDoubleBar - lsl) / (3 * stdX));
        String ca = df.format(Math.abs((xDoubleBar - (usl + lsl) / 2) / ((usl - lsl) / 2) * 100)) + "%";
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
        List<Integer> specialPointsXBar = new ArrayList<>();
        List<Integer> specialPointsR = new ArrayList<>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (r[i] < lclR || r[i] > uclR)               specialPointsR.add(i+1);
            if (xBar[i] < lclXBar || xBar[i] > uclXBar)   specialPointsXBar.add(i+1);
        }
        String pointsSpecialRadioXBar = df.format(specialPointsXBar.size() * 100.0 / subgroupTotal) + "%";
        String pointsSpecialRadioR =    df.format(specialPointsR.size() * 100.0 / subgroupTotal) + "%";



        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXBarList = ChainCount.descendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > descendChainRList = ChainCount.descendChainCount(n, subgroupTotal, r);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXBarList  = ChainCount.ascendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > ascendChainRList  = ChainCount.ascendChainCount(n, subgroupTotal, r);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXBarList = ChainCount.lowerChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > lowerChainRList = ChainCount.lowerChainCount(n, subgroupTotal, r, rBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXBarList = ChainCount.upperChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > upperChainRList = ChainCount.upperChainCount(n, subgroupTotal, r, rBar);




        // 明显非随机图形
        // ......
        // double intervalXBar = (uclXBar - xDoubleBar) / 3;
        // double intervalR =    (uclR - rBar) / 3;
        // double[] intervalValuesXBar = new double[]{uclXBar, xDoubleBar+intervalXBar*2, xDoubleBar+intervalXBar, xDoubleBar, xDoubleBar-intervalXBar, xDoubleBar-intervalXBar*2, lclXBar};
        // double[] intervalValuesR    = new double[]{uclR,    rBar+intervalR*2 ,         rBar+intervalR ,         rBar,       rBar-intervalR,          rBar-intervalR*2,          lclR};
        double intervalCXBar = (uclXBar - xDoubleBar) / 3 ;
        double lowerCXBar = xDoubleBar - intervalCXBar;
        double upperCXBar = xDoubleBar + intervalCXBar;

        int pointsCNumXBar = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (xBar[i] > lowerCXBar && xBar[i] < upperCXBar) pointsCNumXBar++;
        }
        double tmpXBar = pointsCNumXBar * 1.0 * 100 / subgroupTotal;
        tmpXBar = Double.parseDouble(df.format(tmpXBar));
        String pointsCRadioXBar = tmpXBar + "%";

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

        for  (int i = 0; i < subgroupTotal; i++) xBar[i] = Double.parseDouble(df.format(xBar[i]));
        for  (int i = 0; i < subgroupTotal; i++) r[i] = Double.parseDouble(df.format(r[i]));
        xDoubleBar = Double.parseDouble(df.format(xDoubleBar));
        xMax = Double.parseDouble(df.format(xMax));
        xMin = Double.parseDouble(df.format(xMin));
        xAvg = Double.parseDouble(df.format(xAvg));
        avgSubgroupMid = Double.parseDouble(df.format(avgSubgroupMid));
        usl  = Double.parseDouble(df.format(usl));
        sl   = Double.parseDouble(df.format(sl));
        lsl  = Double.parseDouble(df.format(lsl));
        uclXBar = Double.parseDouble(df.format(uclXBar));
        clXBar  = Double.parseDouble(df.format(clXBar));
        lclXBar = Double.parseDouble(df.format(lclXBar));
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
        System.out.println("xBar = " + Arrays.toString(xBar));
        System.out.println("r = " + Arrays.toString(r));
        System.out.println("xDoubleBar = " + xDoubleBar);
        System.out.println("rBar = " + rBar);
        System.out.println("xBarGraduation = " + xBarGraduation);
        System.out.println("rGraduation = " + rGraduation);
        System.out.println("uclXBar = " + uclXBar);
        System.out.println("lclXBar = " + lclXBar);
        System.out.println("uclR = " + uclR);
        System.out.println("lclR = " + lclR);
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
        System.out.println("specialPointsXBar = " + specialPointsXBar);
        System.out.println("specialPointsR = " + specialPointsR);
        System.out.println("descendChainXBarList = " + descendChainXBarList);
        System.out.println("descendChainRList = " + descendChainRList);
        System.out.println("ascendChainXBarList = " + ascendChainXBarList);
        System.out.println("ascendChainRList = " + ascendChainRList);
        System.out.println("upperChainXBarList = " + upperChainXBarList);
        System.out.println("upperChainRList = " + upperChainRList);
        System.out.println("lowerChainXBarList = " + lowerChainXBarList);
        System.out.println("lowerChainRList = " + lowerChainRList);
        // System.out.println("intervalValuesXBar = " + Arrays.toString(intervalValuesXBar));
        // System.out.println("intervalValuesR = " + Arrays.toString(intervalValuesR));
        // ----------------------------------------------------------------------------------

        // 设置返回体
        GraphDataXR graphData = new GraphDataXR();

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
        graphData.setUclXBar(uclXBar);
        graphData.setClXBar(clXBar);
        graphData.setLclXBar(lclXBar);
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
        graphData.setGraduationXBar(xBarGraduation);
        graphData.setGraduationR(rGraduation);
        graphData.setDataArrayXBar(xBar);
        graphData.setDataArrayR(r);
        graphData.setSpecialPointsXBar(specialPointsXBar);
        graphData.setSpecialPointsR(specialPointsR);
        graphData.setDescendChainXBarList(descendChainXBarList);
        graphData.setAscendChainXBarList(ascendChainXBarList);
        graphData.setUpperChainXBarList(upperChainXBarList);
        graphData.setLowerChainXBarList(lowerChainXBarList);
        graphData.setDescendChainRList(descendChainRList);
        graphData.setAscendChainRList(ascendChainRList);
        graphData.setUpperChainRList(upperChainRList);
        graphData.setLowerChainRList(lowerChainRList);
        // graphData.setIntervalXBarValues(intervalValuesXBar);
        // graphData.setIntervalRValues(intervalValuesR);
        graphData.setPointsCRadioXBar(pointsCRadioXBar);
        graphData.setPointsCRadioR(pointsCRadioR);
        graphData.setPointsSpecialRadioXBar(pointsSpecialRadioXBar);
        graphData.setPointsSpecialRadioR(pointsSpecialRadioR);

        return graphData;
    }
}
