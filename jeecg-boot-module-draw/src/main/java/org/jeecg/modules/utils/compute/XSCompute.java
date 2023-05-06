package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.entity.GraphDataXS;
import org.jeecg.modules.utils.TableCoefficient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class XSCompute {
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
        // 每个子组的标准差
        double[] s = new double[subgroupTotal];

        double tmpSum = 0;
        for(int i = 0; i < subgroupTotal; i++) {
            double subgroupSum = DoubleStream.of(dataArray[i]).sum();
            xBar[i] = subgroupSum / subgroupCapacity;

            // 暂存 (x-xBar[i])^2 的和
            double sum = 0;
            for (double x : dataArray[i]) {
                sum = sum + Math.pow(x - xBar[i], 2);
            }
            s[i] = Math.sqrt(sum / (subgroupCapacity-1) );

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

        // 平均标准差
        double sBar = DoubleStream.of(s).sum() / subgroupTotal;

        // 控制图刻度
        // 均值图
        double xBarGraduation = 2 * DoubleStream.of(xBar).max().orElse(0) ;
        // 极差图
        double sGraduation = 2 * DoubleStream.of(s).max().orElse(0);

        // 控制界限
        String quantile = drawData.getQuantile();
        double uclXBar; double lclXBar; double clXBar;
        double uclS; double lclS; double clS;
        if (quantile.equals("不使用")) {
            if (subgroupCapacity <= 25) {
                uclXBar = xDoubleBar + TableCoefficient.A3[subgroupCapacity] * sBar;
                lclXBar = xDoubleBar - TableCoefficient.A3[subgroupCapacity] * sBar;
                uclS = TableCoefficient.B4[subgroupCapacity] * sBar;
                lclS = TableCoefficient.B3[subgroupCapacity] * sBar;
            } else {
                double a = 3 / Math.sqrt(subgroupCapacity - 0.5);
                double d = 1 - 3 / Math.sqrt(2 * subgroupCapacity - 2.5);
                double e = 1 + 3 / Math.sqrt(2 * subgroupCapacity - 2.5);
                uclXBar = xDoubleBar + a * sBar;
                lclXBar = xDoubleBar - a * sBar;
                uclS = e * sBar;
                lclS = d * sBar;
            }
            clXBar = xDoubleBar;
            clS = sBar;
        } else {
            double theataXBarEstimate = xDoubleBar;
            double theataSEstimate = sBar;

            double tmp3 = 0;
            double tmp4 = 0;
            double tmp5 = 0;
            double tmp6 = 0;
            for (int i = 0; i < subgroupTotal; i++) {
                tmp3 = tmp3 + Math.pow(xBar[i] - xDoubleBar, 2);
                tmp4 = tmp4 + Math.pow(xBar[i] - xDoubleBar, 3);
                tmp5 = tmp5 + Math.pow(s[i] - sBar, 2);
                tmp6 = tmp6 + Math.pow(s[i] - sBar, 3);
            }
            double sigmaXBarEstimate = Math.sqrt(1.0 * tmp3 / (subgroupTotal - 1));
            double sigmaSEstimate = Math.sqrt(1.0 * tmp5 / (subgroupTotal - 1));
            double mu3XBarEstimate = tmp4 / subgroupTotal;
            double mu3SEstimate = tmp6 / subgroupTotal;

            uclXBar = theataXBarEstimate + 3 * sigmaXBarEstimate + mu3XBarEstimate * (3 * 3 - 1) / (6 * Math.pow(sigmaXBarEstimate, 2));
            lclXBar = theataXBarEstimate - 3 * sigmaXBarEstimate + mu3XBarEstimate * (3 * 3 - 1) / (6 * Math.pow(sigmaXBarEstimate, 2));
            clXBar = theataXBarEstimate + mu3XBarEstimate * (-1) / (6 * Math.pow(sigmaXBarEstimate, 2));
            uclS = theataSEstimate + 3 * sigmaSEstimate + mu3SEstimate * (3 * 3 - 1) / (6 * Math.pow(sigmaSEstimate, 2));
            lclS = theataSEstimate - 3 * sigmaSEstimate + mu3SEstimate * (3 * 3 - 1) / (6 * Math.pow(sigmaSEstimate, 2));
            clS = theataSEstimate + mu3SEstimate * (-1) / (6 * Math.pow(sigmaSEstimate, 2));

            if (lclXBar < 0 ) lclXBar = 0;
            if (lclS < 0 ) lclS = 0;
        }


        // 过程标准差估计值
        double sigma;
        if (subgroupCapacity <= 25) {
            sigma = sBar / TableCoefficient.c4[subgroupCapacity];
        } else {
            double b = (4*subgroupCapacity - 4) / (4*subgroupCapacity -3);
            sigma = sBar / b;
        }


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
        List<Integer> specialPointsS = new ArrayList<>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (s[i] < lclS || s[i] > uclS)               specialPointsS.add(i+1);
            if (xBar[i] < lclXBar || xBar[i] > uclXBar)   specialPointsXBar.add(i+1);
        }
        String pointsSpecialRadioXBar = df.format(specialPointsXBar.size() * 100.0 / subgroupTotal) + "%";
        String pointsSpecialRadioS    = df.format(specialPointsS.size() * 100.0 / subgroupTotal) + "%";


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXBarList = ChainCount.descendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > descendChainSList = ChainCount.descendChainCount(n, subgroupTotal, s);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXBarList  = ChainCount.ascendChainCount(n, subgroupTotal, xBar);
        List<ArrayList<Integer> > ascendChainSList  = ChainCount.ascendChainCount(n, subgroupTotal, s);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXBarList = ChainCount.lowerChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > lowerChainSList = ChainCount.lowerChainCount(n, subgroupTotal, s, sBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXBarList = ChainCount.upperChainCount(n, subgroupTotal, xBar, xDoubleBar);
        List<ArrayList<Integer> > upperChainSList = ChainCount.upperChainCount(n, subgroupTotal, s, sBar);




        // 明显非随机图形
        // ......
        // double intervalXBar = (uclXBar - xDoubleBar) / 3;
        // double intervalS =    (uclS - sBar) / 3;
        // double[] intervalValuesXBar = new double[]{uclXBar, xDoubleBar+intervalXBar*2, xDoubleBar+intervalXBar, xDoubleBar, xDoubleBar-intervalXBar, xDoubleBar-intervalXBar*2, lclXBar};
        // double[] intervalValuesS    = new double[]{uclS,    sBar+intervalS*2 ,         sBar+intervalS ,         sBar,       sBar-intervalS,          sBar-intervalS*2,          lclS};
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

        double intervalCS = (uclS - sBar) / 3 ;
        double lowerCS = sBar - intervalCS;
        double upperCS = sBar + intervalCS;

        int pointsCNumS = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (s[i] > lowerCS && s[i] < upperCS) pointsCNumS++;
        }
        double tmpS = pointsCNumS * 1.0 * 100 / subgroupTotal;
        tmpS = Double.parseDouble(df.format(tmpS));
        String pointsCRadioS = tmpS + "%";

        for  (int i = 0; i < subgroupTotal; i++) xBar[i] = Double.parseDouble(df.format(xBar[i]));
        for  (int i = 0; i < subgroupTotal; i++) s[i] = Double.parseDouble(df.format(s[i]));
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
        uclS = Double.parseDouble(df.format(uclS));
        clS  = Double.parseDouble(df.format(clS));
        lclS = Double.parseDouble(df.format(lclS));
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
        System.out.println("s = " + Arrays.toString(s));
        System.out.println("xDoubleBar = " + xDoubleBar);
        System.out.println("sBar = " + sBar);
        System.out.println("xBarGraduation = " + xBarGraduation);
        System.out.println("sGraduation = " + sGraduation);
        System.out.println("uclXBar = " + uclXBar);
        System.out.println("lclXBar = " + lclXBar);
        System.out.println("uclS = " + uclS);
        System.out.println("lclS = " + lclS);
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
        System.out.println("specialPointsS = " + specialPointsS);
        System.out.println("descendChainXBarList = " + descendChainXBarList);
        System.out.println("descendChainSList = " + descendChainSList);
        System.out.println("ascendChainXBarList = " + ascendChainXBarList);
        System.out.println("ascendChainSList = " + ascendChainSList);
        System.out.println("upperChainXBarList = " + upperChainXBarList);
        System.out.println("upperChainSList = " + upperChainSList);
        System.out.println("lowerChainXBarList = " + lowerChainXBarList);
        System.out.println("lowerChainSList = " + lowerChainSList);
        // System.out.println("intervalValuesXBar = " + Arrays.toString(intervalValuesXBar));
        // System.out.println("intervalValuesS = " + Arrays.toString(intervalValuesS));
        // --------------------------------------------------------------------------------

        // 设置返回体
        GraphDataXS graphData = new GraphDataXS();

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
        graphData.setUclS(uclS);
        graphData.setClS(clS);
        graphData.setLclS(lclS);
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
        // graphData.setIntervalXBarValues(intervalValuesXBar);
        // graphData.setIntervalSValues(intervalValuesS);
        graphData.setPointsCRadioXBar(pointsCRadioXBar);
        graphData.setPointsCRadioS(pointsCRadioS);
        graphData.setPointsSpecialRadioXBar(pointsSpecialRadioXBar);
        graphData.setPointsSpecialRadioS(pointsSpecialRadioS);


        return graphData;
    }
}
