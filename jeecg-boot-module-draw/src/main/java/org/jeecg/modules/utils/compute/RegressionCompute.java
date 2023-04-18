package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.entity.GraphDataRegression;
import org.jeecg.modules.business.entity.GraphDataXS;
import org.jeecg.modules.utils.TableCoefficient;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.DoubleStream;

public class RegressionCompute {
    public static GraphData compute(Draw drawData) {
        DecimalFormat df = new DecimalFormat("#.#####");

        String graphType = drawData.getGraphType();

        double[][] dataArrayRegression = drawData.getDataArrayRegressionTK();
        double[] dataArrayRegressionStandard = drawData.getDataArrayRegressionTKStandard();
        String[] dataArrayRegressionPrecision = drawData.getDataArrayRegressionTKPrecision();
        int[] dataArrayRegressionSort = drawData.getDataArrayRegressionTKSort();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int subgroupTotal = drawData.getSubgroupTotal();

        int samplesNum = subgroupTotal * subgroupCapacity;

        // 求精度数
        Set<String> precisionSet = new HashSet<>();
        for (int i = 0; i < dataArrayRegressionPrecision.length; i++) precisionSet.add(dataArrayRegressionPrecision[i]);
        int precisionNum = precisionSet.size();

        // 求品种数
        Set<Integer> SortSet = new HashSet<>();
        for (int i = 0; i < dataArrayRegressionSort.length; i++) SortSet.add(dataArrayRegressionSort[i]);
        int sortNum = SortSet.size();

        // 对样本数据做双重回归或者只使用“相对偏差方法”，后续看是否需要另做调整
        // 回归方法二："相对偏差"方法
        double[][] dataArray = new double[subgroupTotal][subgroupCapacity];
        for (int i = 0; i < subgroupTotal; i++) {
            for (int j = 0; j < subgroupCapacity; j++) {
                dataArray[i][j] = (dataArrayRegression[i][j] - dataArrayRegressionStandard[i]) / dataArrayRegressionStandard[i];
            }
        }

        if (precisionNum > 1) {
            // 回归方法一："标准正态处理"方法
            // 每种精度的均值
            Map<String, Double> precisionMu = new HashMap<>();
            // 每种精度的标准差
            Map<String, Double> precisionSigma = new HashMap<>();
            // 每种精度的数量
            Map<String, Integer> precisionSum = new HashMap<>();

            // 计算每种精度的均值
            for (int i = 0; i < subgroupTotal; i++) {
                precisionSum.put(dataArrayRegressionPrecision[i], precisionSum.getOrDefault(dataArrayRegressionPrecision[i], 0) + subgroupCapacity);
                for (int j = 0; j < subgroupCapacity; j++) {
                    precisionMu.put(dataArrayRegressionPrecision[i], precisionMu.getOrDefault(dataArrayRegressionPrecision[i], 0.0) + dataArray[i][j]);
                }
            }
            for (String key: precisionMu.keySet()) {
                double tmp = precisionMu.get(key);
                precisionMu.put(key, tmp/precisionSum.get(key));
            }

            // 计算每种精度的标准偏差
            for (int i = 0; i < subgroupTotal; i++) {
                for (int j = 0; j < subgroupCapacity; j++) {
                    precisionSigma.put(dataArrayRegressionPrecision[i], precisionSigma.getOrDefault(dataArrayRegressionPrecision[i], 0.0) + Math.pow(dataArray[i][j] - precisionMu.get(dataArrayRegressionPrecision[i]) ,2));
                }
            }
            for (String key: precisionSigma.keySet()) {
                double tmp = precisionSigma.get(key);
                precisionSigma.put(key, Math.sqrt(tmp/(precisionSum.get(key)-1)));
            }


            for (int i = 0; i < subgroupTotal; i++) {
                double tmpMu = precisionMu.get(dataArrayRegressionPrecision[i]);
                double tmpSigma = precisionSigma.get(dataArrayRegressionPrecision[i]);
                for (int j = 0; j < subgroupCapacity; j++) {
                    dataArray[i][j] = (dataArray[i][j] - tmpMu) / tmpSigma;
                }
            }
        }




        // 每个子组的均值
        double[] xBar = new double[subgroupTotal];
        // 每个子组的标准差
        double[] s = new double[subgroupTotal];

        for(int i = 0; i < subgroupTotal; i++) {
            double subgroupSum = DoubleStream.of(dataArray[i]).sum();
            xBar[i] = subgroupSum / subgroupCapacity;

            // 暂存 (x-xBar[i])^2 的和
            double sum = 0;
            for (double x : dataArray[i]) {
                sum = sum + Math.pow(x - xBar[i], 2);
            }
            s[i] = Math.sqrt(sum / (subgroupCapacity-1) );
        }

        // 过程均值
        double xDoubleBar = DoubleStream.of(xBar).sum()  / subgroupTotal;

        double sBar = DoubleStream.of(s).sum()  / subgroupTotal;

        // 控制图刻度
        // 均值图
        double xBarGraduation = 2 * DoubleStream.of(xBar).max().orElse(0) ;
        // 标准偏差图
        double sGraduation = 2 * DoubleStream.of(s).max().orElse(0);

        // 控制界限
        double uclXBar; double lclXBar; double clXBar;
        double uclS; double lclS; double clS;
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
        uclXBar = Double.parseDouble(df.format(uclXBar));
        clXBar  = Double.parseDouble(df.format(clXBar));
        lclXBar = Double.parseDouble(df.format(lclXBar));
        uclS = Double.parseDouble(df.format(uclS));
        clS  = Double.parseDouble(df.format(clS));
        lclS = Double.parseDouble(df.format(lclS));




        // 调试代码 ----------------------------------------------------------------------
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
        // --------------------------------------------------------------------------------

        // 设置返回体
        GraphDataRegression graphData = new GraphDataRegression();

        graphData.setGraphType(graphType);
        graphData.setDataArray(dataArray);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setSamplesNum(samplesNum);
        graphData.setUclXBar(uclXBar);
        graphData.setClXBar(clXBar);
        graphData.setLclXBar(lclXBar);
        graphData.setUclS(uclS);
        graphData.setClS(clS);
        graphData.setLclS(lclS);
        graphData.setPrecisionNum(precisionNum);
        graphData.setSortNum(sortNum);
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
