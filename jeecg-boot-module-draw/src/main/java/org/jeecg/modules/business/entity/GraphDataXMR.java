package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataXMR implements GraphData{
    int subTotal;

    double avgX;

    double maxX;

    double minX;

    double MidX;

    double usl;

    double sl;

    double lsl;

    double uclX;

    double clX;

    double lclX;

    double uclMR;

    double clMR;

    double lclMR;

    double skewnessX;

    double kurtosisX;

    double ppm;

    double stdX;

    double sigma;

    double pp;

    double ppk;

    String ca;

    double cp;

    double cpu;

    double cpl;

    double cpk;

    String cpkGrade;

    double[] dataArrayX;
    double[] dataArrayMR;

    double graduationX;
    double graduationMR;

    List<Integer> specialPointsX;
    List<Integer> specialPointsMR;

    List<ArrayList<Integer>> descendChainXList;
    List<ArrayList<Integer> > ascendChainXList;
    List<ArrayList<Integer> > lowerChainXList;
    List<ArrayList<Integer> > upperChainXList;
    List<ArrayList<Integer>> descendChainMRList;
    List<ArrayList<Integer> > ascendChainMRList;
    List<ArrayList<Integer> > lowerChainMRList;
    List<ArrayList<Integer> > upperChainMRList;

    double[] intervalXValues;
    double[] intervalMRValues;

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public double getAvgX() {
        return avgX;
    }

    public void setAvgX(double avgX) {
        this.avgX = avgX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMidX() {
        return MidX;
    }

    public void setMidX(double midX) {
        MidX = midX;
    }

    public double getUsl() {
        return usl;
    }

    public void setUsl(double usl) {
        this.usl = usl;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public double getLsl() {
        return lsl;
    }

    public void setLsl(double lsl) {
        this.lsl = lsl;
    }

    public double getUclX() {
        return uclX;
    }

    public void setUclX(double uclX) {
        this.uclX = uclX;
    }

    public double getClX() {
        return clX;
    }

    public void setClX(double clX) {
        this.clX = clX;
    }

    public double getLclX() {
        return lclX;
    }

    public void setLclX(double lclX) {
        this.lclX = lclX;
    }

    public double getUclMR() {
        return uclMR;
    }

    public void setUclMR(double uclMR) {
        this.uclMR = uclMR;
    }

    public double getClMR() {
        return clMR;
    }

    public void setClMR(double clMR) {
        this.clMR = clMR;
    }

    public double getLclMR() {
        return lclMR;
    }

    public void setLclMR(double lclMR) {
        this.lclMR = lclMR;
    }

    public double getSkewnessX() {
        return skewnessX;
    }

    public void setSkewnessX(double skewnessX) {
        this.skewnessX = skewnessX;
    }

    public double getKurtosisX() {
        return kurtosisX;
    }

    public void setKurtosisX(double kurtosisX) {
        this.kurtosisX = kurtosisX;
    }

    public double getPpm() {
        return ppm;
    }

    public void setPpm(double ppm) {
        this.ppm = ppm;
    }

    public double getStdX() {
        return stdX;
    }

    public void setStdX(double stdX) {
        this.stdX = stdX;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getPp() {
        return pp;
    }

    public void setPp(double pp) {
        this.pp = pp;
    }

    public double getPpk() {
        return ppk;
    }

    public void setPpk(double ppk) {
        this.ppk = ppk;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public double getCp() {
        return cp;
    }

    public void setCp(double cp) {
        this.cp = cp;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public double getCpl() {
        return cpl;
    }

    public void setCpl(double cpl) {
        this.cpl = cpl;
    }

    public double getCpk() {
        return cpk;
    }

    public void setCpk(double cpk) {
        this.cpk = cpk;
    }

    public String getCpkGrade() {
        return cpkGrade;
    }

    public void setCpkGrade(String cpkGrade) {
        this.cpkGrade = cpkGrade;
    }

    public double[] getDataArrayX() {
        return dataArrayX;
    }

    public void setDataArrayX(double[] dataArrayX) {
        this.dataArrayX = dataArrayX;
    }

    public double[] getDataArrayMR() {
        return dataArrayMR;
    }

    public void setDataArrayMR(double[] dataArrayMR) {
        this.dataArrayMR = dataArrayMR;
    }

    public double getGraduationX() {
        return graduationX;
    }

    public void setGraduationX(double graduationX) {
        this.graduationX = graduationX;
    }

    public double getGraduationMR() {
        return graduationMR;
    }

    public void setGraduationMR(double graduationMR) {
        this.graduationMR = graduationMR;
    }

    public List<Integer> getSpecialPointsX() {
        return specialPointsX;
    }

    public void setSpecialPointsX(List<Integer> specialPointsX) {
        this.specialPointsX = specialPointsX;
    }

    public List<Integer> getSpecialPointsMR() {
        return specialPointsMR;
    }

    public void setSpecialPointsMR(List<Integer> specialPointsMR) {
        this.specialPointsMR = specialPointsMR;
    }

    public List<ArrayList<Integer>> getDescendChainXList() {
        return descendChainXList;
    }

    public void setDescendChainXList(List<ArrayList<Integer>> descendChainXList) {
        this.descendChainXList = descendChainXList;
    }

    public List<ArrayList<Integer>> getAscendChainXList() {
        return ascendChainXList;
    }

    public void setAscendChainXList(List<ArrayList<Integer>> ascendChainXList) {
        this.ascendChainXList = ascendChainXList;
    }

    public List<ArrayList<Integer>> getLowerChainXList() {
        return lowerChainXList;
    }

    public void setLowerChainXList(List<ArrayList<Integer>> lowerChainXList) {
        this.lowerChainXList = lowerChainXList;
    }

    public List<ArrayList<Integer>> getUpperChainXList() {
        return upperChainXList;
    }

    public void setUpperChainXList(List<ArrayList<Integer>> upperChainXList) {
        this.upperChainXList = upperChainXList;
    }

    public List<ArrayList<Integer>> getDescendChainMRList() {
        return descendChainMRList;
    }

    public void setDescendChainMRList(List<ArrayList<Integer>> descendChainMRList) {
        this.descendChainMRList = descendChainMRList;
    }

    public List<ArrayList<Integer>> getAscendChainMRList() {
        return ascendChainMRList;
    }

    public void setAscendChainMRList(List<ArrayList<Integer>> ascendChainMRList) {
        this.ascendChainMRList = ascendChainMRList;
    }

    public List<ArrayList<Integer>> getLowerChainMRList() {
        return lowerChainMRList;
    }

    public void setLowerChainMRList(List<ArrayList<Integer>> lowerChainMRList) {
        this.lowerChainMRList = lowerChainMRList;
    }

    public List<ArrayList<Integer>> getUpperChainMRList() {
        return upperChainMRList;
    }

    public void setUpperChainMRList(List<ArrayList<Integer>> upperChainMRList) {
        this.upperChainMRList = upperChainMRList;
    }

    public double[] getIntervalXValues() {
        return intervalXValues;
    }

    public void setIntervalXValues(double[] intervalXValues) {
        this.intervalXValues = intervalXValues;
    }

    public double[] getIntervalMRValues() {
        return intervalMRValues;
    }

    public void setIntervalMRValues(double[] intervalMRValues) {
        this.intervalMRValues = intervalMRValues;
    }

    @Override
    public String toString() {
        return "GraphDataXMR{" +
                "subTotal=" + subTotal +
                ", avgX=" + avgX +
                ", maxX=" + maxX +
                ", minX=" + minX +
                ", MidX=" + MidX +
                ", usl=" + usl +
                ", sl=" + sl +
                ", lsl=" + lsl +
                ", uclX=" + uclX +
                ", clX=" + clX +
                ", lclX=" + lclX +
                ", uclMR=" + uclMR +
                ", clMR=" + clMR +
                ", lclMR=" + lclMR +
                ", skewnessX=" + skewnessX +
                ", kurtosisX=" + kurtosisX +
                ", ppm=" + ppm +
                ", stdX=" + stdX +
                ", sigma=" + sigma +
                ", pp=" + pp +
                ", ppk=" + ppk +
                ", ca=" + ca +
                ", cp=" + cp +
                ", cpu=" + cpu +
                ", cpl=" + cpl +
                ", cpk=" + cpk +
                ", cpkGrade='" + cpkGrade + '\'' +
                ", dataArrayX=" + Arrays.toString(dataArrayX) +
                ", dataArrayMR=" + Arrays.toString(dataArrayMR) +
                ", graduationX=" + graduationX +
                ", graduationMR=" + graduationMR +
                ", specialPointsX=" + specialPointsX +
                ", specialPointsMR=" + specialPointsMR +
                ", descendChainXList=" + descendChainXList +
                ", ascendChainXList=" + ascendChainXList +
                ", lowerChainXList=" + lowerChainXList +
                ", upperChainXList=" + upperChainXList +
                ", descendChainMRList=" + descendChainMRList +
                ", ascendChainMRList=" + ascendChainMRList +
                ", lowerChainMRList=" + lowerChainMRList +
                ", upperChainMRList=" + upperChainMRList +
                ", intervalXValues=" + Arrays.toString(intervalXValues) +
                ", intervalMRValues=" + Arrays.toString(intervalMRValues) +
                '}';
    }
}
