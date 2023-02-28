package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.Draw;
import org.springframework.stereotype.Service;

@Service
public class DrawService {
    public void handleData(Draw drawData) {
        System.out.println(drawData);
    }
}
