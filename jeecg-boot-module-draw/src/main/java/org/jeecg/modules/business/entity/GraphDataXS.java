package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataXS implements GraphData{
    String graphType;

    String quantile;

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

    double uclS;

    double clS;

    double lclS;

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
    double[] dataArrayS;

    double graduationXBar;
    double graduationS;

    String pointsCRadioXBar;
    String pointsCRadioS;

    String pointsSpecialRadioXBar;
    String pointsSpecialRadioS;

    List<Integer> specialPointsXBar;
    List<Integer> specialPointsS;

    List<ArrayList<Integer>> descendChainXBarList;
    List<ArrayList<Integer> > ascendChainXBarList;
    List<ArrayList<Integer> > lowerChainXBarList;
    List<ArrayList<Integer> > upperChainXBarList;
    List<ArrayList<Integer>> descendChainSList;
    List<ArrayList<Integer> > ascendChainSList;
    List<ArrayList<Integer> > lowerChainSList;
    List<ArrayList<Integer> > upperChainSList;

    // double[] intervalXBarValues;
    // double[] intervalSValues;


    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public String getQuantile() {
        return quantile;
    }

    public void setQuantile(String quantile) {
        this.quantile = quantile;
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

    public double getUclS() {
        return uclS;
    }

    public void setUclS(double uclS) {
        this.uclS = uclS;
    }

    public double getClS() {
        return clS;
    }

    public void setClS(double clS) {
        this.clS = clS;
    }

    public double getLclS() {
        return lclS;
    }

    public void setLclS(double lclS) {
        this.lclS = lclS;
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

    public double[] getDataArrayS() {
        return dataArrayS;
    }

    public void setDataArrayS(double[] dataArrayS) {
        this.dataArrayS = dataArrayS;
    }

    public double getGraduationXBar() {
        return graduationXBar;
    }

    public void setGraduationXBar(double graduationXBar) {
        this.graduationXBar = graduationXBar;
    }

    public double getGraduationS() {
        return graduationS;
    }

    public void setGraduationS(double graduationS) {
        this.graduationS = graduationS;
    }

    public String getPointsCRadioXBar() {
        return pointsCRadioXBar;
    }

    public void setPointsCRadioXBar(String pointsCRadioXBar) {
        this.pointsCRadioXBar = pointsCRadioXBar;
    }

    public String getPointsCRadioS() {
        return pointsCRadioS;
    }

    public void setPointsCRadioS(String pointsCRadioS) {
        this.pointsCRadioS = pointsCRadioS;
    }

    public String getPointsSpecialRadioXBar() {
        return pointsSpecialRadioXBar;
    }

    public void setPointsSpecialRadioXBar(String pointsSpecialRadioXBar) {
        this.pointsSpecialRadioXBar = pointsSpecialRadioXBar;
    }

    public String getPointsSpecialRadioS() {
        return pointsSpecialRadioS;
    }

    public void setPointsSpecialRadioS(String pointsSpecialRadioS) {
        this.pointsSpecialRadioS = pointsSpecialRadioS;
    }

    public List<Integer> getSpecialPointsXBar() {
        return specialPointsXBar;
    }

    public void setSpecialPointsXBar(List<Integer> specialPointsXBar) {
        this.specialPointsXBar = specialPointsXBar;
    }

    public List<Integer> getSpecialPointsS() {
        return specialPointsS;
    }

    public void setSpecialPointsS(List<Integer> specialPointsS) {
        this.specialPointsS = specialPointsS;
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

    public List<ArrayList<Integer>> getDescendChainSList() {
        return descendChainSList;
    }

    public void setDescendChainSList(List<ArrayList<Integer>> descendChainSList) {
        this.descendChainSList = descendChainSList;
    }

    public List<ArrayList<Integer>> getAscendChainSList() {
        return ascendChainSList;
    }

    public void setAscendChainSList(List<ArrayList<Integer>> ascendChainSList) {
        this.ascendChainSList = ascendChainSList;
    }

    public List<ArrayList<Integer>> getLowerChainSList() {
        return lowerChainSList;
    }

    public void setLowerChainSList(List<ArrayList<Integer>> lowerChainSList) {
        this.lowerChainSList = lowerChainSList;
    }

    public List<ArrayList<Integer>> getUpperChainSList() {
        return upperChainSList;
    }

    public void setUpperChainSList(List<ArrayList<Integer>> upperChainSList) {
        this.upperChainSList = upperChainSList;
    }

    @Override
    public String toString() {
        return "GraphDataXS{" +
                "graphType='" + graphType + '\'' +
                ", quantile='" + quantile + '\'' +
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
                ", uclS=" + uclS +
                ", clS=" + clS +
                ", lclS=" + lclS +
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
                ", dataArrayS=" + Arrays.toString(dataArrayS) +
                ", graduationXBar=" + graduationXBar +
                ", graduationS=" + graduationS +
                ", pointsCRadioXBar='" + pointsCRadioXBar + '\'' +
                ", pointsCRadioS='" + pointsCRadioS + '\'' +
                ", pointsSpecialRadioXBar='" + pointsSpecialRadioXBar + '\'' +
                ", pointsSpecialRadioS='" + pointsSpecialRadioS + '\'' +
                ", specialPointsXBar=" + specialPointsXBar +
                ", specialPointsS=" + specialPointsS +
                ", descendChainXBarList=" + descendChainXBarList +
                ", ascendChainXBarList=" + ascendChainXBarList +
                ", lowerChainXBarList=" + lowerChainXBarList +
                ", upperChainXBarList=" + upperChainXBarList +
                ", descendChainSList=" + descendChainSList +
                ", ascendChainSList=" + ascendChainSList +
                ", lowerChainSList=" + lowerChainSList +
                ", upperChainSList=" + upperChainSList +
                '}';
    }
}
