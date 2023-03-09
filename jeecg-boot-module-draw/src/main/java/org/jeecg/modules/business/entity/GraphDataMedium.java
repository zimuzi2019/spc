package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataMedium implements GraphData{
    int subgroupCapacity;

    int subTotal;

    int samplesNum;

    double avgX;

    double maxX;

    double minX;

    double avgSubgroupMid;

    double usl;

    double sl;

    double lsl;

    double uclXMedium;

    double clXMedium;

    double lclXMedium;

    double uclR;

    double clR;

    double lclR;

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

    double[] dataArrayXMedium;
    double[] dataArrayR;

    double graduationXMedium;
    double graduationR;

    List<Integer> specialPointsXMedium;
    List<Integer> specialPointsR;

    List<ArrayList<Integer>> descendChainXMediumList;
    List<ArrayList<Integer> > ascendChainXMediumList;
    List<ArrayList<Integer> > lowerChainXMediumList;
    List<ArrayList<Integer> > upperChainXMediumList;
    List<ArrayList<Integer>> descendChainRList;
    List<ArrayList<Integer> > ascendChainRList;
    List<ArrayList<Integer> > lowerChainRList;
    List<ArrayList<Integer> > upperChainRList;

    double[] intervalXMediumValues;
    double[] intervalRValues;

    public int getSubgroupCapacity() {
        return subgroupCapacity;
    }

    public void setSubgroupCapacity(int subgroupCapacity) {
        this.subgroupCapacity = subgroupCapacity;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getSamplesNum() {
        return samplesNum;
    }

    public void setSamplesNum(int samplesNum) {
        this.samplesNum = samplesNum;
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

    public double getAvgSubgroupMid() {
        return avgSubgroupMid;
    }

    public void setAvgSubgroupMid(double avgSubgroupMid) {
        this.avgSubgroupMid = avgSubgroupMid;
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

    public double getUclXMedium() {
        return uclXMedium;
    }

    public void setUclXMedium(double uclXMedium) {
        this.uclXMedium = uclXMedium;
    }

    public double getClXMedium() {
        return clXMedium;
    }

    public void setClXMedium(double clXMedium) {
        this.clXMedium = clXMedium;
    }

    public double getLclXMedium() {
        return lclXMedium;
    }

    public void setLclXMedium(double lclXMedium) {
        this.lclXMedium = lclXMedium;
    }

    public double getUclR() {
        return uclR;
    }

    public void setUclR(double uclR) {
        this.uclR = uclR;
    }

    public double getClR() {
        return clR;
    }

    public void setClR(double clR) {
        this.clR = clR;
    }

    public double getLclR() {
        return lclR;
    }

    public void setLclR(double lclR) {
        this.lclR = lclR;
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

    public double[] getDataArrayXMedium() {
        return dataArrayXMedium;
    }

    public void setDataArrayXMedium(double[] dataArrayXMedium) {
        this.dataArrayXMedium = dataArrayXMedium;
    }

    public double[] getDataArrayR() {
        return dataArrayR;
    }

    public void setDataArrayR(double[] dataArrayR) {
        this.dataArrayR = dataArrayR;
    }

    public double getGraduationXMedium() {
        return graduationXMedium;
    }

    public void setGraduationXMedium(double graduationXMedium) {
        this.graduationXMedium = graduationXMedium;
    }

    public double getGraduationR() {
        return graduationR;
    }

    public void setGraduationR(double graduationR) {
        this.graduationR = graduationR;
    }

    public List<Integer> getSpecialPointsXMedium() {
        return specialPointsXMedium;
    }

    public void setSpecialPointsXMedium(List<Integer> specialPointsXMedium) {
        this.specialPointsXMedium = specialPointsXMedium;
    }

    public List<Integer> getSpecialPointsR() {
        return specialPointsR;
    }

    public void setSpecialPointsR(List<Integer> specialPointsR) {
        this.specialPointsR = specialPointsR;
    }

    public List<ArrayList<Integer>> getDescendChainXMediumList() {
        return descendChainXMediumList;
    }

    public void setDescendChainXMediumList(List<ArrayList<Integer>> descendChainXMediumList) {
        this.descendChainXMediumList = descendChainXMediumList;
    }

    public List<ArrayList<Integer>> getAscendChainXMediumList() {
        return ascendChainXMediumList;
    }

    public void setAscendChainXMediumList(List<ArrayList<Integer>> ascendChainXMediumList) {
        this.ascendChainXMediumList = ascendChainXMediumList;
    }

    public List<ArrayList<Integer>> getLowerChainXMediumList() {
        return lowerChainXMediumList;
    }

    public void setLowerChainXMediumList(List<ArrayList<Integer>> lowerChainXMediumList) {
        this.lowerChainXMediumList = lowerChainXMediumList;
    }

    public List<ArrayList<Integer>> getUpperChainXMediumList() {
        return upperChainXMediumList;
    }

    public void setUpperChainXMediumList(List<ArrayList<Integer>> upperChainXMediumList) {
        this.upperChainXMediumList = upperChainXMediumList;
    }

    public List<ArrayList<Integer>> getDescendChainRList() {
        return descendChainRList;
    }

    public void setDescendChainRList(List<ArrayList<Integer>> descendChainRList) {
        this.descendChainRList = descendChainRList;
    }

    public List<ArrayList<Integer>> getAscendChainRList() {
        return ascendChainRList;
    }

    public void setAscendChainRList(List<ArrayList<Integer>> ascendChainRList) {
        this.ascendChainRList = ascendChainRList;
    }

    public List<ArrayList<Integer>> getLowerChainRList() {
        return lowerChainRList;
    }

    public void setLowerChainRList(List<ArrayList<Integer>> lowerChainRList) {
        this.lowerChainRList = lowerChainRList;
    }

    public List<ArrayList<Integer>> getUpperChainRList() {
        return upperChainRList;
    }

    public void setUpperChainRList(List<ArrayList<Integer>> upperChainRList) {
        this.upperChainRList = upperChainRList;
    }

    public double[] getIntervalXMediumValues() {
        return intervalXMediumValues;
    }

    public void setIntervalXMediumValues(double[] intervalXMediumValues) {
        this.intervalXMediumValues = intervalXMediumValues;
    }

    public double[] getIntervalRValues() {
        return intervalRValues;
    }

    public void setIntervalRValues(double[] intervalRValues) {
        this.intervalRValues = intervalRValues;
    }

    @Override
    public String toString() {
        return "GraphDataMedium{" +
                "subgroupCapacity=" + subgroupCapacity +
                ", subTotal=" + subTotal +
                ", samplesNum=" + samplesNum +
                ", avgX=" + avgX +
                ", maxX=" + maxX +
                ", minX=" + minX +
                ", avgSubgroupMid=" + avgSubgroupMid +
                ", usl=" + usl +
                ", sl=" + sl +
                ", lsl=" + lsl +
                ", uclXMedium=" + uclXMedium +
                ", clXMedium=" + clXMedium +
                ", lclXMedium=" + lclXMedium +
                ", uclR=" + uclR +
                ", clR=" + clR +
                ", lclR=" + lclR +
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
                ", dataArrayXMedium=" + Arrays.toString(dataArrayXMedium) +
                ", dataArrayR=" + Arrays.toString(dataArrayR) +
                ", graduationXMedium=" + graduationXMedium +
                ", graduationR=" + graduationR +
                ", specialPointsXMedium=" + specialPointsXMedium +
                ", specialPointsR=" + specialPointsR +
                ", descendChainXMediumList=" + descendChainXMediumList +
                ", ascendChainXMediumList=" + ascendChainXMediumList +
                ", lowerChainXMediumList=" + lowerChainXMediumList +
                ", upperChainXMediumList=" + upperChainXMediumList +
                ", descendChainRList=" + descendChainRList +
                ", ascendChainRList=" + ascendChainRList +
                ", lowerChainRList=" + lowerChainRList +
                ", upperChainRList=" + upperChainRList +
                ", intervalXMediumValues=" + Arrays.toString(intervalXMediumValues) +
                ", intervalRValues=" + Arrays.toString(intervalRValues) +
                '}';
    }
}
