package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.utils.compute.*;
import org.springframework.stereotype.Service;

@Service
public class DrawService {
    public void handleData(Draw drawData) {
        System.out.println(drawData);

        String graphType = drawData.getGraphType();
        if (graphType.equals("X-R") ) {
            XRCompute.compute(drawData);
        }
        if (graphType.equals("X-S")) {
            XSCompute.compute(drawData);
        }
        if (graphType.equals("中位数")) {
            MediumCompute.compute(drawData);
        }
        if (graphType.equals("X-MR") ) {
            XMRCompute.compute(drawData);
        }
        if (graphType.equals("C")) {
            CCompute.compute(drawData);
        }
        if (graphType.equals("nP")) {
            NPCompute.compute(drawData);
        }
        if (graphType.equals("P") ) {
            PCompute.compute(drawData);
        }
        if (graphType.equals("U")) {
            UCompute.compute(drawData);
        }
    }
}
