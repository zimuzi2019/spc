package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.utils.compute.*;
import org.springframework.stereotype.Service;

@Service
public class DrawService {
    public GraphData handleData(Draw drawData) {
        System.out.println(drawData);

        String graphType = drawData.getGraphType();
        GraphData graphData = null;

        if (graphType.startsWith("X-R") )  graphData = XRCompute.compute(drawData);

        if (graphType.startsWith("X-S"))   graphData = XSCompute.compute(drawData);

        if (graphType.startsWith("中位数")) graphData = MediumCompute.compute(drawData);

        if (graphType.startsWith("X-MR") ) graphData = XMRCompute.compute(drawData);

        if (graphType.startsWith("C"))     graphData = CCompute.compute(drawData);

        if (graphType.startsWith("nP"))    graphData = NPCompute.compute(drawData);

        if (graphType.startsWith("P") && !graphType.startsWith("P_T"))    graphData = PCompute.compute(drawData);

        if (graphType.startsWith("P_T"))    graphData = PTCompute.compute(drawData);

        if (graphType.startsWith("U") && !graphType.startsWith("U_T"))    graphData = UCompute.compute(drawData);

        if (graphType.startsWith("U_T"))    graphData = UTCompute.compute(drawData);

        if (graphType.startsWith("回归")) graphData = RegressionCompute.compute(drawData);

        return graphData;
    }
}
