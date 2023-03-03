package org.jeecg.modules.utils.compute;

import org.jeecg.modules.business.entity.Draw;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class UCompute {
    // 这里画的是通用单位缺陷数控制图而不是单位缺陷控制图
    public static void compute(Draw drawData) {
        String graphType = drawData.getGraphType();
        int subgroupTotal = drawData.getSubgroupTotal();

        int[] dataArraySubgroupsCapacity = drawData.getDataArrayPUSubgroupsCapacity();
        int[] dataArrayDefectsNum = drawData.getDataArrayPUDefectsNum();

        //每个子组的不合格点数
        double[] u = new double[subgroupTotal];

        for (int i = 0; i < subgroupTotal; i++) {
            u[i] = dataArrayDefectsNum[i] * 1.0 / dataArraySubgroupsCapacity[i];
        }

        // 平均不合格品数
        double uBar = IntStream.of(dataArrayDefectsNum).sum() * 1.0 / IntStream.of(dataArraySubgroupsCapacity).sum();

        // 标准化处理
        double[] ut = new double[subgroupTotal];
        for (int i = 0; i < subgroupTotal; i++) ut[i] = (u[i] - uBar) / Math.sqrt(uBar / dataArraySubgroupsCapacity[i]);

        // 控制图坐标刻度 ...
        double graduation = DoubleStream.of(ut).max().orElse(0) * 2;

        // 上下控制限
        double uclPt = 3;
        double lclPt = -3;

        // 似乎不用计算过程能力指数
        // ......


        // 分析
        // 超出控制线的点
        List<Integer> specialPointsUt = new ArrayList<Integer>();

        for (int i = 0; i < subgroupTotal; i++) {
            if (ut[i] < lclPt || ut[i] > uclPt) specialPointsUt.add(i + 1);
        }

        // 链
        int n = 6;   // 连续6点递增或者递减

        // 下降链集合
        List<ArrayList<Integer>> descendChainUtList = ChainCount.descendChainCount(n, subgroupTotal, ut);

        // 上升链集合
        List<ArrayList<Integer>> ascendChainUtList = ChainCount.ascendChainCount(n, subgroupTotal, ut);

        n = 9;     // 连续9点落在中心线的一侧
        // 下侧链集合
        List<ArrayList<Integer>> lowerChainUtList = ChainCount.lowerChainCount(n, subgroupTotal, ut, 0);

        // 下侧链集合
        List<ArrayList<Integer>> upperChainUtList = ChainCount.upperChainCount(n, subgroupTotal, ut, 0);


        // 明显非随机图形
        // ......
        double[] intervalValuesPt = new double[]{3, 2, 1, 0, -1, -2, -3};
    }
}
