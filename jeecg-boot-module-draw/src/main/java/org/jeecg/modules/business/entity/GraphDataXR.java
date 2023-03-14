package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataXR implements GraphData{
    String graphType;

    double[][] dataArray;

    int subgroupCapacity;

    int subgroupTotal;

    int samplesNum;

    double avgX;

    double maxX;

    double minX;

    double avgSubgroupMid;

    double usl;

    double sl;

    double lsl;

    double uclXBar;

    double clXBar;

    double lclXBar;

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

    double[] dataArrayXBar;
    double[] dataArrayR;

    double graduationXBar;
    double graduationR;

    String pointsCRadioXBar;
    String pointsCRadioR;

    String pointsSpecialRadioXBar;
    String pointsSpecialRadioR;

    List<Integer> specialPointsXBar;
    List<Integer> specialPointsR;

    List<ArrayList<Integer>> descendChainXBarList;
    List<ArrayList<Integer> > ascendChainXBarList;
    List<ArrayList<Integer> > lowerChainXBarList;
    List<ArrayList<Integer> > upperChainXBarList;
    List<ArrayList<Integer>> descendChainRList;
    List<ArrayList<Integer> > ascendChainRList;
    List<ArrayList<Integer> > lowerChainRList;
    List<ArrayList<Integer> > upperChainRList;

    // double[] intervalXBarValues;
    // double[] intervalRValues;


    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public double[][] getDataArray() {
        return dataArray;
    }

    public void setDataArray(double[][] dataArray) {
        this.dataArray = dataArray;
    }

    public int getSubgroupCapacity() {
        return subgroupCapacity;
    }

    public void setSubgroupCapacity(int subgroupCapacity) {
        this.subgroupCapacity = subgroupCapacity;
    }

    public int getSubgroupTotal() {
        return subgroupTotal;
    }

    public void setSubgroupTotal(int subgroupTotal) {
        this.subgroupTotal = subgroupTotal;
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

    public double getUclXBar() {
        return uclXBar;
    }

    public void setUclXBar(double uclXBar) {
        this.uclXBar = uclXBar;
    }

    public double getClXBar() {
        return clXBar;
    }

    public void setClXBar(double clXBar) {
        this.clXBar = clXBar;
    }

    public double getLclXBar() {
        return lclXBar;
    }

    public void setLclXBar(double lclXBar) {
        this.lclXBar = lclXBar;
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

    public double[] getDataArrayXBar() {
        return dataArrayXBar;
    }

    public void setDataArrayXBar(double[] dataArrayXBar) {
        this.dataArrayXBar = dataArrayXBar;
    }

    public double[] getDataArrayR() {
        return dataArrayR;
    }

    public void setDataArrayR(double[] dataArrayR) {
        this.dataArrayR = dataArrayR;
    }

    public double getGraduationXBar() {
        return graduationXBar;
    }

    public void setGraduationXBar(double graduationXBar) {
        this.graduationXBar = graduationXBar;
    }

    public double getGraduationR() {
        return graduationR;
    }

    public void setGraduationR(double graduationR) {
        this.graduationR = graduationR;
    }

    public String getPointsCRadioXBar() {
        return pointsCRadioXBar;
    }

    public void setPointsCRadioXBar(String pointsCRadioXBar) {
        this.pointsCRadioXBar = pointsCRadioXBar;
    }

    public String getPointsCRadioR() {
        return pointsCRadioR;
    }

    public void setPointsCRadioR(String pointsCRadioR) {
        this.pointsCRadioR = pointsCRadioR;
    }

    public String getPointsSpecialRadioXBar() {
        return pointsSpecialRadioXBar;
    }

    public void setPointsSpecialRadioXBar(String pointsSpecialRadioXBar) {
        this.pointsSpecialRadioXBar = pointsSpecialRadioXBar;
    }

    public String getPointsSpecialRadioR() {
        return pointsSpecialRadioR;
    }

    public void setPointsSpecialRadioR(String pointsSpecialRadioR) {
        this.pointsSpecialRadioR = pointsSpecialRadioR;
    }

    public List<Integer> getSpecialPointsXBar() {
        return specialPointsXBar;
    }

    public void setSpecialPointsXBar(List<Integer> specialPointsXBar) {
        this.specialPointsXBar = specialPointsXBar;
    }

    public List<Integer> getSpecialPointsR() {
        return specialPointsR;
    }

    public void setSpecialPointsR(List<Integer> specialPointsR) {
        this.specialPointsR = specialPointsR;
    }

    public List<ArrayList<Integer>> getDescendChainXBarList() {
        return descendChainXBarList;
    }

    public void setDescendChainXBarList(List<ArrayList<Integer>> descendChainXBarList) {
        this.descendChainXBarList = descendChainXBarList;
    }

    public List<ArrayList<Integer>> getAscendChainXBarList() {
        return ascendChainXBarList;
    }

    public void setAscendChainXBarList(List<ArrayList<Integer>> ascendChainXBarList) {
        this.ascendChainXBarList = ascendChainXBarList;
    }

    public List<ArrayList<Integer>> getLowerChainXBarList() {
        return lowerChainXBarList;
    }

    public void setLowerChainXBarList(List<ArrayList<Integer>> lowerChainXBarList) {
        this.lowerChainXBarList = lowerChainXBarList;
    }

    public List<ArrayList<Integer>> getUpperChainXBarList() {
        return upperChainXBarList;
    }

    public void setUpperChainXBarList(List<ArrayList<Integer>> upperChainXBarList) {
        this.upperChainXBarList = upperChainXBarList;
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

    @Override
    public String toString() {
        return "GraphDataXR{" +
                "graphType='" + graphType + '\'' +
                ", dataArray=" + Arrays.toString(dataArray) +
                ", subgroupCapacity=" + subgroupCapacity +
                ", subgroupTotal=" + subgroupTotal +
                ", samplesNum=" + samplesNum +
                ", avgX=" + avgX +
                ", maxX=" + maxX +
                ", minX=" + minX +
                ", avgSubgroupMid=" + avgSubgroupMid +
                ", usl=" + usl +
                ", sl=" + sl +
                ", lsl=" + lsl +
                ", uclXBar=" + uclXBar +
                ", clXBar=" + clXBar +
                ", lclXBar=" + lclXBar +
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
                ", ca='" + ca + '\'' +
                ", cp=" + cp +
                ", cpu=" + cpu +
                ", cpl=" + cpl +
                ", cpk=" + cpk +
                ", cpkGrade='" + cpkGrade + '\'' +
                ", dataArrayXBar=" + Arrays.toString(dataArrayXBar) +
                ", dataArrayR=" + Arrays.toString(dataArrayR) +
                ", graduationXBar=" + graduationXBar +
                ", graduationR=" + graduationR +
                ", pointsCRadioXBar='" + pointsCRadioXBar + '\'' +
                ", pointsCRadioR='" + pointsCRadioR + '\'' +
                ", pointsSpecialRadioXBar='" + pointsSpecialRadioXBar + '\'' +
                ", pointsSpecialRadioR='" + pointsSpecialRadioR + '\'' +
                ", specialPointsXBar=" + specialPointsXBar +
                ", specialPointsR=" + specialPointsR +
                ", descendChainXBarList=" + descendChainXBarList +
                ", ascendChainXBarList=" + ascendChainXBarList +
                ", lowerChainXBarList=" + lowerChainXBarList +
                ", upperChainXBarList=" + upperChainXBarList +
                ", descendChainRList=" + descendChainRList +
                ", ascendChainRList=" + ascendChainRList +
                ", lowerChainRList=" + lowerChainRList +
                ", upperChainRList=" + upperChainRList +
                '}';
    }
}
