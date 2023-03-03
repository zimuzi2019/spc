package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.utils.compute.MediumCompute;
import org.jeecg.modules.utils.compute.XMRCompute;
import org.jeecg.modules.utils.compute.XRCompute;
import org.jeecg.modules.utils.compute.XSCompute;
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

        }
        if (graphType.equals("nP")) {

        }
        if (graphType.equals("P") ) {

        }
        if (graphType.equals("U")) {

        }
    }
}
