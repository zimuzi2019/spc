package org.jeecg.modules.utils.compute;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.entity.GraphDataRegression;
import org.jeecg.modules.business.entity.GraphDataTK;
import org.jeecg.modules.utils.TableCoefficient;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.DoubleStream;

public class TKCompute {
    public static GraphData compute(Draw drawData) {
        DecimalFormat df = new DecimalFormat("#.####");

        String graphType = drawData.getGraphType();

        double[][] dataArray = drawData.getDataArrayRegressionTK();
        int[] dataArraySort = drawData.getDataArrayRegressionTKSort();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int subgroupTotal = drawData.getSubgroupTotal();

        int samplesNum = subgroupTotal * subgroupCapacity;

        // 求品种数
        Set<Integer> SortSet = new HashSet<>();
        for (int i = 0; i < dataArraySort.length; i++) SortSet.add(dataArraySort[i]);
        int sortNum = SortSet.size();


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

        // 计算统计量T、K
        int[] tmp1 = new int[sortNum+1];       Arrays.fill(tmp1, 0); // 存储每种品种当前统计到第几批
        double[] tmp2 = new double[sortNum+1]; Arrays.fill(tmp2, 0); // 存储每种品种当前均值总和
        double[] tmp3 = new double[sortNum+1]; Arrays.fill(tmp3, 0); // 存储每种品种当前样本方差总和

        double[] t = new double[subgroupTotal];
        double[] k = new double[subgroupTotal];

        for (int i = 0; i < subgroupTotal; i++) {
            if (tmp1[dataArraySort[i]] == 0) {
                t[i] = 0;
                k[i] = 0;
            } else {
                t[i] = (xBar[i] - tmp2[dataArraySort[i]]/tmp1[dataArraySort[i]]) / s[i] * Math.sqrt(subgroupCapacity * tmp1[dataArraySort[i]] * 1.0/ (tmp1[dataArraySort[i]]+1));
                System.out.println(t[i]);

                // 创建一个F分布对象，参数为自由度
                FDistribution fd = new FDistribution(subgroupCapacity-1, (subgroupCapacity-1)*tmp1[dataArraySort[i]]);
                // 创建一个标准正态分布对象
                NormalDistribution nd = new NormalDistribution();

                double l = Math.pow(s[i], 2) / (tmp3[dataArraySort[i]] / tmp1[dataArraySort[i]]);
                k[i] = nd.inverseCumulativeProbability(fd.cumulativeProbability(l));
            }

            tmp1[dataArraySort[i]]++;
            tmp2[dataArraySort[i]] = tmp2[dataArraySort[i]] + xBar[i];
            tmp3[dataArraySort[i]] = tmp3[dataArraySort[i]] + Math.pow(s[i], 2);
        }

        // 计算T控制图的控制限
        double uclT = TableCoefficient.uclT[subgroupCapacity];
        double lclT = -uclT;
        double clT  = 0;

        // 计算K控制图的控制限
        double uclK = 3;
        double lclK = -3;
        double clK  = 0;

        // 控制图刻度
        // 均值图
        double kGraduation = 2 * DoubleStream.of(k).max().orElse(0) ;
        // 标准偏差图
        double tGraduation = 2 * DoubleStream.of(t).max().orElse(0);



        // 分析
        // 超出控制线的点
        List<Integer> specialPointsK = new ArrayList<>();
        List<Integer> specialPointsT = new ArrayList<>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (k[i] <  lclK|| k[i] > uclK)   specialPointsK.add(i+1);
            if (t[i] < lclT || t[i] > uclT)   specialPointsT.add(i+1);
        }
        String pointsSpecialRadioK = df.format(specialPointsK.size() * 100.0 / subgroupTotal) + "%";
        String pointsSpecialRadioT = df.format(specialPointsT.size() * 100.0 / subgroupTotal) + "%";


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainKList = ChainCount.descendChainCount(n, subgroupTotal, k);
        List<ArrayList<Integer> > descendChainTList = ChainCount.descendChainCount(n, subgroupTotal, t);

        // 上升链集合
        List<ArrayList<Integer> > ascendChainKList  = ChainCount.ascendChainCount(n, subgroupTotal, k);
        List<ArrayList<Integer> > ascendChainTList  = ChainCount.ascendChainCount(n, subgroupTotal, t);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer> > lowerChainKList = ChainCount.lowerChainCount(n, subgroupTotal, k, 0);
        List<ArrayList<Integer> > lowerChainTList = ChainCount.lowerChainCount(n, subgroupTotal, t, 0);

        // 下侧链集合
        List<ArrayList<Integer> > upperChainKList = ChainCount.upperChainCount(n, subgroupTotal, k, 0);
        List<ArrayList<Integer> > upperChainTList = ChainCount.upperChainCount(n, subgroupTotal, t, 0);


        double upperCT = TableCoefficient.sigmaT[subgroupCapacity];
        double lowerCT = -upperCT;

        int pointsCNumT = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (k[i] > lowerCT && xBar[i] < upperCT) pointsCNumT++;
        }
        double tmpT = pointsCNumT * 1.0 * 100 / subgroupTotal;
        tmpT = Double.parseDouble(df.format(tmpT));
        String pointsCRadioT = tmpT + "%";


        double lowerCK = -1;
        double upperCK = 1;

        int pointsCNumK = 0;
        for (int i = 0; i < subgroupTotal; i++) {
            if (k[i] > lowerCK && k[i] < upperCK) pointsCNumK++;
        }
        double tmpK = pointsCNumK * 1.0 * 100 / subgroupTotal;
        tmpK = Double.parseDouble(df.format(tmpK));
        String pointsCRadioK = tmpK + "%";


        for  (int i = 0; i < subgroupTotal; i++) k[i] = Double.parseDouble(df.format(k[i]));
        for  (int i = 0; i < subgroupTotal; i++) t[i] = Double.parseDouble(df.format(t[i]));



        // 调试代码
        System.out.println("t = " + Arrays.toString(t));
        System.out.println("k = " + Arrays.toString(k));

        // 设置返回体
        GraphDataTK graphData = new GraphDataTK();

        graphData.setGraphType(graphType);
        graphData.setDataArray(dataArray);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setSamplesNum(samplesNum);
        graphData.setUclT(uclT);
        graphData.setClT(clT);
        graphData.setLclT(lclT);
        graphData.setUclK(uclK);
        graphData.setClK(clK);
        graphData.setLclK(lclK);
        graphData.setSortNum(sortNum);
        graphData.setGraduationK(kGraduation);
        graphData.setGraduationT(tGraduation);
        graphData.setDataArrayK(k);
        graphData.setDataArrayT(t);
        graphData.setSpecialPointsK(specialPointsK);
        graphData.setSpecialPointsT(specialPointsT);

        graphData.setDescendChainTList(descendChainTList);
        graphData.setAscendChainTList(ascendChainTList);
        graphData.setUpperChainTList(upperChainTList);
        graphData.setLowerChainTList(lowerChainTList);

        graphData.setDescendChainKList(descendChainKList);
        graphData.setAscendChainKList(ascendChainKList);
        graphData.setUpperChainKList(upperChainKList);
        graphData.setLowerChainKList(lowerChainKList);

        graphData.setPointsCRadioT(pointsCRadioT);
        graphData.setPointsCRadioK(pointsCRadioK);
        graphData.setPointsSpecialRadioT(pointsSpecialRadioT);
        graphData.setPointsSpecialRadioK(pointsSpecialRadioK);


        return graphData;
    }
}
