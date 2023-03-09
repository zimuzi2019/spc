package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.entity.GraphDataCnP;
import org.jeecg.modules.business.entity.GraphDataPU;
import org.jeecg.modules.utils.compute.*;
import org.springframework.stereotype.Service;

@Service
public class DrawService {
    public GraphData handleData(Draw drawData) {
        System.out.println(drawData);

        String graphType = drawData.getGraphType();
        // 调试代码
        if (graphType.equals("X-R") || graphType.equals("X-S") || graphType.equals("中位数")) {
            double[][] dataArray = drawData.getDataArrayXRXSMedium();
            for (int i = 0; i < drawData.getSubgroupTotal(); i++) {
                for (int j = 0; j < drawData.getSubgroupCapacity(); j++){
                    System.out.print(dataArray[i][j] + " ");
                }
                System.out.println();
            }
        }
        //

        GraphData graphData = null;

        if (graphType.equals("X-R") ) {
            graphData = XRCompute.compute(drawData);
        }
        if (graphType.equals("X-S")) {
            graphData = XSCompute.compute(drawData);
        }
        if (graphType.equals("中位数")) {
            graphData = MediumCompute.compute(drawData);
        }
        if (graphType.equals("X-MR") ) {
            graphData = XMRCompute.compute(drawData);
        }
        if (graphType.equals("C")) {
            graphData = CCompute.compute(drawData);
        }
        if (graphType.equals("nP")) {
            graphData = NPCompute.compute(drawData);
        }
        if (graphType.equals("P") ) {
            graphData = PCompute.compute(drawData);
        }
        if (graphType.equals("U")) {
            graphData = UCompute.compute(drawData);
        }

        return graphData;
    }
}
