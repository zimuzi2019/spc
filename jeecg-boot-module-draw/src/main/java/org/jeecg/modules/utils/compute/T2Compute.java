package org.jeecg.modules.utils.compute;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.entity.GraphDataT2;
import org.jeecg.modules.business.entity.GraphDataT2Single;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class T2Compute {
    public static GraphData compute(Draw drawData) {
        DecimalFormat df = new DecimalFormat("#.####");

        String graphType = drawData.getGraphType();

        double[][][] dataArrayT2 = drawData.getDataArrayT2();
        int subgroupTotal = drawData.getSubgroupTotal();
        int subgroupCapacity = drawData.getSubgroupCapacity();
        int varNum = drawData.getVarNum();


        // 保存每批每个变量的均值
        double[][] dataArray = new double[subgroupTotal][varNum];
        // 保存每批多个变量之间协方差矩阵的和
        double[][] tmp = new double[varNum][varNum];
        for (int i = 0; i < subgroupTotal; i++){
            for (int j = 0; j < varNum; j++) {
                dataArray[i][j] = DoubleStream.of(dataArrayT2[i][j]).sum() / subgroupCapacity;
            }
        }

        for (int i = 0; i < subgroupTotal; i++){
            for (int j = 0; j < varNum; j++) {
                for (int k = 0; k < varNum; k++) {
                    double tmpSum = 0;
                    for (int l = 0; l < subgroupCapacity; l++) {
                        tmpSum =  tmpSum + (dataArrayT2[i][j][l] - dataArray[i][j]) * (dataArrayT2[i][k][l] - dataArray[i][k]);
                    }
                    tmp[j][k] = tmp[j][k] + tmpSum / (subgroupCapacity-1);
                }
            }
        }


        // 样本均值向量
        double[] varBar = new double[varNum]; Arrays.fill(varBar, 0);

        for (int i = 0; i < varNum; i++) {
            for (int j = 0; j < subgroupTotal; j++) {
                varBar[i] = varBar[i] + dataArray[j][i];
            }
            varBar[i] = varBar[i] / subgroupTotal;
        }

        RealMatrix xBar = new Array2DRowRealMatrix(varBar);

        // 样本协方差矩阵
        double[][] tmp1 = new double[varNum][varNum];

        for (int i = 0; i < varNum; i++) {
            for (int j = 0; j < varNum; j++) {
                tmp1[i][j] = tmp[i][j] / subgroupTotal;
            }
        }


        // -- 测试用代码 --
        tmp1[0][0] = 1.2978; tmp1[0][1] = 0.1052; tmp1[1][0] = 0.1052; tmp1[1][1] = 0.0087;
        // --------------

        RealMatrix s = new Array2DRowRealMatrix(tmp1);


        // 计算统计量T2
        double[] t2 = new double[subgroupTotal];
        for (int i = 0; i < subgroupTotal; i++) {
            RealMatrix xi = new Array2DRowRealMatrix(dataArray[i]);

            // 求协方差矩阵的逆
            LUDecomposition LUDe = new LUDecomposition(s);
            DecompositionSolver solver = LUDe.getSolver();
            RealMatrix sInverse = solver.getInverse();

            RealMatrix tmp2 = xi.subtract(xBar);
            RealMatrix tmp3 = tmp2.transpose();

            t2[i] = tmp3.multiply(sInverse).multiply(tmp2).getEntry(0, 0) * subgroupCapacity;
        }


        // 控制图刻度
        double t2Graduation = 2 * DoubleStream.of(t2).max().orElse(0) ;

        // 控制界限
        double lclT2Analysis = 0; double lclT2Control = 0;
        
        FDistribution fd = new FDistribution(varNum, subgroupTotal*subgroupCapacity-subgroupTotal-varNum+1);
        double uclT2Analysis = varNum*(subgroupTotal-1)*(subgroupCapacity-1)*1.0 / (subgroupTotal*subgroupCapacity-subgroupTotal-varNum+1) * fd.inverseCumulativeProbability(1-0.00135);
        double uclT2Control = varNum*(subgroupTotal+1)*(subgroupCapacity-1)*1.0 / (subgroupTotal*subgroupCapacity-subgroupTotal-varNum+1) * fd.inverseCumulativeProbability(1-0.00135);

        System.out.println(Arrays.toString(t2));
        System.out.println(uclT2Analysis); System.out.println(uclT2Control);


        // 分析
        // 由于没有计算CL的公式，不计算C区点占比和连续落在中心线一侧链
        // 超出控制线的点
        List<Integer> specialPointsT2Analysis = new ArrayList<>();
        List<Integer> specialPointsT2Control = new ArrayList<>();


        for (int i = 0; i < subgroupTotal; i++) {
            if (t2[i] < lclT2Analysis || t2[i] > uclT2Analysis)  specialPointsT2Analysis.add(i+1);
            if (t2[i] < lclT2Control ||t2[i] > uclT2Control)     specialPointsT2Control.add(i+1);
        }
        String pointsSpecialRadioT2Analysis = df.format(specialPointsT2Analysis.size() * 100.0 / subgroupTotal) + "%";
        String pointsSpecialRadioT2Control  = df.format(specialPointsT2Control.size() * 100.0 / subgroupTotal) + "%";


        //链
        // 产生链需要的点数
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer> > descendChainT2AnalysisList = ChainCount.descendChainCount(n, subgroupTotal, t2);
        List<ArrayList<Integer> > descendChainT2ControlList = descendChainT2AnalysisList;

        // 上升链集合
        List<ArrayList<Integer> > ascendChainT2AnalysisList  = ChainCount.ascendChainCount(n, subgroupTotal, t2);
        List<ArrayList<Integer> > ascendChainT2ControlList  = ascendChainT2AnalysisList;


        for  (int i = 0; i < subgroupTotal; i++) t2[i] = Double.parseDouble(df.format(t2[i]));

        // xDoubleBar = Double.parseDouble(df.format(xDoubleBar));
        uclT2Analysis = Double.parseDouble(df.format(uclT2Analysis));
        lclT2Analysis = Double.parseDouble(df.format(lclT2Analysis));
        uclT2Control = Double.parseDouble(df.format(uclT2Control));
        lclT2Control = Double.parseDouble(df.format(lclT2Control));


        // 设置返回体
        GraphDataT2 graphData = new GraphDataT2();

        graphData.setGraphType(graphType);
        graphData.setSubgroupCapacity(subgroupCapacity);
        graphData.setSubgroupTotal(subgroupTotal);
        graphData.setVarNum(varNum);
        graphData.setUclT2Analysis(uclT2Analysis);
        graphData.setLclT2Analysis(lclT2Analysis);
        graphData.setUclT2Control(uclT2Control);
        graphData.setLclT2Control(lclT2Control);
        graphData.setGraduationT2(t2Graduation);
        graphData.setSpecialPointsT2Analysis(specialPointsT2Analysis);
        graphData.setDataArrayT2(t2);
        graphData.setSpecialPointsT2Control(specialPointsT2Control);
        graphData.setSpecialPointsT2Analysis(specialPointsT2Analysis);
        graphData.setDescendChainT2AnalysisList(descendChainT2AnalysisList);
        graphData.setAscendChainT2AnalysisList(ascendChainT2AnalysisList);
        graphData.setDescendChainT2ControlList(descendChainT2ControlList);
        graphData.setAscendChainT2ControlList(ascendChainT2ControlList);
        graphData.setPointsSpecialRadioT2Analysis(pointsSpecialRadioT2Analysis);
        graphData.setPointsSpecialRadioT2Control(pointsSpecialRadioT2Control);

        return graphData;
    }
}
