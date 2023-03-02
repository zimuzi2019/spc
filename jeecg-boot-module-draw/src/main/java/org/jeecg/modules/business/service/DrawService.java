package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.utils.compute.SheetXRCompute;
import org.jeecg.modules.utils.compute.SheetXSCompute;
import org.springframework.stereotype.Service;

@Service
public class DrawService {
    public void handleData(Draw drawData) {
        System.out.println(drawData);

        String graphType = drawData.getGraphType();
        if (graphType.equals("X-R") ) {
            SheetXRCompute.compute(drawData);
        }
        if (graphType.equals("X-S")) {

        }
        if (graphType.equals("中位数")) {

        }
        if (graphType.equals("X-MR") ) {

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
