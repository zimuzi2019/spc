package org.jeecg.modules.utils.compute;

public class CpkGradeDetermination {
    public static String CpkGraderDetermination(double cpk) {
        if (cpk >= 1.67) return "A";
        else if (cpk >= 1.33 && cpk < 1.67) return "B";
        else if (cpk >= 1    && cpk < 1.33) return "C";
        else if (cpk >= 0.67 && cpk < 1)    return "D";
        else return "E";
    }
}
