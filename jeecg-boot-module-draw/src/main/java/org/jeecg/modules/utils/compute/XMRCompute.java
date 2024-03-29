package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphDataXMR;
import org.jeecg.modules.utils.TableCoefficient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class XMRCompute {
    public static GraphDataXMR compute(Draw drawData) {
        DecimalFormat df = new DecimalFormat("#.####");

        String graphType = drawData.getGraphType();
        int subgroupTotal = drawData.getSubgroupTotal();
        double usl = drawData.getUsl();
        double lsl = drawData.getLsl();
        double sl = drawData.getSl();

        // 单值
        double[] x = drawData.getDataArrayXMR();

        // 极差
        double[] mr = new double[subgroupTotal-1]; // 对于样本容量为2的移动极差，其移动极差（MR）的个数比单值读数的个数少1

        for (int i = 0; i < subgroupTotal-1; i++) {
            mr[i] = Math.abs(x[i+1] - x[i]);
            mr[i] = Double.parseDouble(df.format(mr[i]));
        }

        // 过程均值
        double xBar = DoubleStream.of(x).sum()  / subgroupTotal;
        double xMax = DoubleStream.of(x).max().orElse(0);
        double xMin = DoubleStream.of(x).min().orElse(0);

        double[] xCopy = x.clone();
        Arrays.sort(xCopy);
        int len = xCopy.length; double xMid = 0;
        if (len % 2 == 0)  xMid = (xCopy[len / 2] + xCopy[len / 2 - 1]) / 2;
        else xMid = xCopy[len / 2];

        // 样本标准差
        double tmp = 0;
        for (int i = 0; i < subgroupTotal; i++) tmp = tmp + Math.pow(x[i] - xBar ,2);
        double stdX = Math.sqrt(tmp / (subgroupTotal-1));

        // 样本偏度
        double tmp1 = 0;
        for (int i = 0; i < subgroupTotal; i++) tmp1 = tmp1 + Math.pow( (x[i] - xBar)/stdX, 3 );
        double skewnessX = subgroupTotal * tmp1 / ((subgroupTotal -1) * (subgroupTotal-2)) ;

        // 样本峰度
        double tmp2 = 0;
        for (int i = 0; i < subgroupTotal; i++) tmp2 = tmp2 + Math.pow( (x[i] - xBar)/stdX, 4);
        double kurtosisX = subgroupTotal * (subgroupTotal + 1) * tmp2 / ((subgroupTotal - 1) * (subgroupTotal - 2) * (subgroupTotal - 3)) - 3 * Math.pow( (subgroupTotal - 1), 2) / ((subgroupTotal - 2) * (subgroupTotal - 3));

        // 移动平均极差
        double mrBar = DoubleStream.of(mr).sum() / (subgroupTotal-1);

        // 控制图刻度
        // 这里“产品的规范容差加上超过规范的读数的允许值”没搞懂
        double graduationX = DoubleStream.of(x).max().orElse(0) * 2;
        graduationX = (int) graduationX + 1;
        double graduationMR = DoubleStream.of(mr).max().orElse(0) * 2;
        graduationMR = (int) graduationMR + 1;

        // 控制界限
        String quantile = drawData.getQuantile();
        double uclX; double lclX; double clX;
        double uclMR; double lclMR; double clMR;
        if (quantile.equals("不使用")) {
            uclX = xBar + TableCoefficient.E2[2] * mrBar;
            lclX = xBar - TableCoefficient.E2[2] * mrBar;
            clX = xBar;
            uclMR = TableCoefficient.D4[2] * mrBar;
            lclMR = TableCoefficient.D3[2] * mrBar;
            clMR = mrBar;
        } else {
            double theataXEstimate = xBar;
            double theataMREstimate = mrBar;

            double tmp3 = 0; double tmp4 = 0; double tmp5 = 0; double tmp6 = 0;
            for (int i = 0; i < subgroupTotal; i++) {
                tmp3 = tmp3 + Math.pow(x[i] - xBar,2);
                tmp4 = tmp4 + Math.pow(x[i] - xBar,3);
            }
            for (int i = 0; i < subgroupTotal-1; i++) {
                tmp5 = tmp5 + Math.pow(mr[i] - mrBar,2);
                tmp6 = tmp6 + Math.pow(mr[i] - mrBar,3);
            }
            double sigmaXEstimate =  Math.sqrt(1.0 * tmp3 / (subgroupTotal-1));
            double sigmaMREstimate = Math.sqrt(1.0 * tmp5 / (subgroupTotal-2));
            double mu3XEstimate = tmp4 / subgroupTotal;
            double mu3MREstimate = tmp6 / (subgroupTotal-1);

            uclX = theataXEstimate + 3 * sigmaXEstimate + mu3XEstimate * (3*3-1)/(6*Math.pow(sigmaXEstimate, 2));
            lclX = theataXEstimate - 3 * sigmaXEstimate + mu3XEstimate * (3*3-1)/(6*Math.pow(sigmaXEstimate, 2));
            clX = theataXEstimate + mu3XEstimate * (-1)/(6* Math.pow(sigmaXEstimate, 2));
            uclMR    = theataMREstimate + 3 * sigmaMREstimate + mu3MREstimate * (3*3-1)/(6* Math.pow(sigmaMREstimate, 2));
            lclMR   = theataMREstimate - 3 * sigmaMREstimate + mu3MREstimate * (3*3-1)/(6* Math.pow(sigmaMREstimate, 2));
            clMR     = theataMREstimate + mu3MREstimate * (-1)/(6* Math.pow(sigmaMREstimate, 2));

            if (lclMR < 0) lclMR = 0;
            if (lclX < 0) lclX = 0;
        }

        // 过程的标准偏差
        double sigma = mrBar / TableCoefficient.d2[2];

        //单边容差
        double zUSL = (usl - xBar) / sigma;
        double zLSL = (xBar - lsl) / sigma;

        //双边容差
        double  z = Math.min(zUSL, zLSL);

        //过程能力上下限
        double cpu = zUSL / 3;
        double cpl = zLSL / 3;
        //过程能力指数
        double cpk = z / 3;
        double pp = (usl- lsl) / (6 * stdX);
        double ppk = Math.min( (usl - xBar) / (3 * stdX), (xBar - lsl) / (3 * stdX));
        String ca = df.format(Math.abs((xBar - (usl + lsl) / 2) / ((usl - lsl) / 2) * 100)) + "%";
        double cp = (usl - lsl) / (6 * sigma);

        // 预估不良率
        int defectsNum = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (x[i] < lsl || x[i] > usl) defectsNum++;
        }
        double ppm = defectsNum * 1.0 / subgroupTotal * Math.pow(10, 6);

        // Grade
        String cpkGrade = CpkGradeDetermination.CpkGraderDetermination(cpk);



        // 分析
        // 超出控制线的点
        List<Integer> specialPointsX = new ArrayList<>();
        List<Integer> specialPointsMR = new ArrayList<>();


        for (int i = 0; i < subgroupTotal-1; i++) {
            if (mr[i] < lclMR || mr[i] > uclMR)   specialPointsMR.add(i+1);
        }
        String pointsSpecialRadioMR = df.format(specialPointsMR.size() * 100.0 / (subgroupTotal-1)) + "%";

        for (int i = 0; i < subgroupTotal; i++) {
            if (x[i] < lclX || x[i] > uclX)       specialPointsX.add(i+1);
        }
        String pointsSpecialRadioX = df.format(specialPointsX.size() * 100.0 / subgroupTotal) + "%";

        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainXList = ChainCount.descendChainCount(n, subgroupTotal, x);
        List<ArrayList<Integer> > descendChainMRList = ChainCount.descendChainCount(n, subgroupTotal-1, mr);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainXList  = ChainCount.ascendChainCount(n, subgroupTotal, x);
        List<ArrayList<Integer> > ascendChainMRList  = ChainCount.ascendChainCount(n, subgroupTotal-1, mr);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainXList = ChainCount.lowerChainCount(n, subgroupTotal, x, xBar);
        List<ArrayList<Integer> > lowerChainMRList = ChainCount.lowerChainCount(n, subgroupTotal-1, mr, mrBar);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainXList = ChainCount.upperChainCount(n, subgroupTotal, x, xBar);
        List<ArrayList<Integer> > upperChainMRList = ChainCount.upperChainCount(n, subgroupTotal-1, mr, mrBar);




        // 明显非随机图形
        // ......
        //double intervalX = (uclX - xBar) / 3;
        //double intervalMR =    (uclMR - mrBar) / 3;
        //double[] intervalValuesX =   new double[]{uclX,  xBar+intervalX*2,   xBar+intervalX,   xBar,  xBar-intervalX,   xBar-intervalX*2,   lclX};
        //double[] intervalValuesMR  = new double[]{uclMR, mrBar+intervalMR*2, mrBar+intervalMR, mrBar, mrBar-intervalMR, mrBar-intervalMR*2, lclMR};
        double intervalCX = (uclX - xBar) / 3 ;
        double lowerCX = xBar - intervalCX;
        double upperCX = xBar + intervalCX;

        int pointsCNumX = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (x[i] > lowerCX && x[i] < upperCX) pointsCNumX++;
        }
        double tmpX = pointsCNumX * 1.0 * 100 / subgroupTotal;
        tmpX = Double.parseDouble(df.format(tmpX));
        String pointsCRadioX = tmpX + "%";

        double intervalCMR = (uclMR - mrBar) / 3 ;
        double lowerCMR = mrBar - intervalCMR;
        double upperCMR = mrBar + intervalCMR;

        int pointsCNumMR = 0;
        for (int i = 0; i < subgroupTotal-1; i++) {
            if (mr[i] > lowerCMR && mr[i] < upperCMR) pointsCNumMR++;
        }
        double tmpMR = pointsCNumMR * 1.0 * 100 / (subgroupTotal-1);
        tmpMR = Double.parseDouble(df.format(tmpMR));
        String pointsCRadioMR = tmpMR + "%";


        for  (int i = 0; i < subgroupTotal; i++) x[i] = Double.parseDouble(df.format(x[i]));
        xBar = Double.parseDouble(df.format(xBar));
        xMax = Double.parseDouble(df.format(xMax));
        xMin = Double.parseDouble(df.format(xMin));
        xMid = Double.parseDouble(df.format(xMid));
        usl  = Double.parseDouble(df.format(usl));
        sl   = Double.parseDouble(df.format(sl));
        lsl  = Double.parseDouble(df.format(lsl));
        uclX = Double.parseDouble(df.format(uclX));
        clX  = Double.parseDouble(df.format(clX));
        lclX = Double.parseDouble(df.format(lclX));
        uclMR = Double.parseDouble(df.format(uclMR));
        clMR  = Double.parseDouble(df.format(clMR));
        lclMR = Double.parseDouble(df.format(lclMR));
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


        // 调试代码 -----------------------------------------------------------------
        System.out.println("x = " + Arrays.toString(x));
        System.out.println("mr = " + Arrays.toString(mr));
        System.out.println("xBar = " + xBar);
        System.out.println("mrBar = " + mrBar);
        System.out.println("stdX = " + stdX);
        System.out.println("skewnessX = " + skewnessX);
        System.out.println("kurtosisX = " + kurtosisX);
        // System.out.println("xBarGraduation = " +xBarGraduation);
        // System.out.println("RGraduation = " + RGraduation);
        System.out.println("graduationX = " + graduationX);
        System.out.println("graduationMR = " + graduationMR);
        System.out.println("sigma = " + sigma);
        System.out.println("uclX = " + uclX);
        System.out.println("lclX = " + lclX);
        System.out.println("uclMR = " + uclMR);
        System.out.println("lclMR = " + lclMR);
        System.out.println("zUSL = " + zUSL);
        System.out.println("zLSL = " + zLSL);
        System.out.println("pp = " + pp);
        System.out.println("ppk = " + ppk);
        System.out.println("ca = " + ca);
        System.out.println("cp = " + cp);
        System.out.println("ppm = " + ppm);
        System.out.println("cpu = " + cpu);
        System.out.println("cpl = " + cpl);
        System.out.println("cpk = " + cpk);
        System.out.println("cpk Grade = " + cpkGrade);
        System.out.println("specialPointsX = " + specialPointsX);
        System.out.println("specialPointsMR = " + specialPointsMR);
        System.out.println("descendChainXList = " + descendChainXList);
        System.out.println("descendChainMRList = " + descendChainMRList);
        System.out.println("ascendChainXList = " + ascendChainXList);
        System.out.println("ascendChainMRList = " + ascendChainMRList);
        System.out.println("upperChainXList = " + upperChainXList);
        System.out.println("upperChainMRList = " + upperChainMRList);
        System.out.println("lowerChainXList = " + lowerChainXList);
        System.out.println("lowerChainMRList = " + lowerChainMRList);
        // System.out.println("intervalValuesX = " + Arrays.toString(intervalValuesX));
        // System.out.println("intervalValuesMR = " + Arrays.toString(intervalValuesMR));
        // -----------------------------------------------------------------------------

        // 设置返回体
        GraphDataXMR graphData = new GraphDataXMR();

        graphData.setGraphType(graphType);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setAvgX(xBar);
        graphData.setMaxX(xMax);
        graphData.setMinX(xMin);
        graphData.setMidX(xMid);
        graphData.setUsl(usl);
        graphData.setSl(sl);
        graphData.setLsl(lsl);
        graphData.setUclX(uclX);
        graphData.setClX(clX);
        graphData.setLclX(lclX);
        graphData.setUclMR(uclMR);
        graphData.setClMR(clMR);
        graphData.setLclMR(lclMR);
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
        graphData.setDataArrayX(x);
        graphData.setDataArrayMR(mr);
        graphData.setGraduationX(graduationX);
        graphData.setGraduationMR(graduationMR);
        graphData.setSpecialPointsX(specialPointsX);
        graphData.setSpecialPointsMR(specialPointsMR);
        graphData.setDescendChainXList(descendChainXList);
        graphData.setAscendChainXList(ascendChainXList);
        graphData.setLowerChainXList(lowerChainXList);
        graphData.setUpperChainXList(upperChainXList);
        graphData.setDescendChainMRList(descendChainMRList);
        graphData.setAscendChainMRList(ascendChainMRList);
        graphData.setLowerChainMRList(lowerChainMRList);
        graphData.setUpperChainMRList(upperChainMRList);
        // graphData.setIntervalXValues(intervalValuesX);
        // graphData.setIntervalMRValues(intervalValuesMR);
        graphData.setPointsSpecialRadioX(pointsSpecialRadioX);
        graphData.setPointsSpecialRadioMR(pointsSpecialRadioMR);
        graphData.setPointsCRadioX(pointsCRadioX);
        graphData.setPointsCRadioMR(pointsCRadioMR);

        return graphData;
    }
}
