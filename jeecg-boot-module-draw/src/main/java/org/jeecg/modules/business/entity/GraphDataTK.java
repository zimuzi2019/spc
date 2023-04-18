package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataTK implements GraphData{
    String graphType;

    double[][] dataArray;

    int subgroupCapacity;

    int subgroupTotal;

    int samplesNum;

    int sortNum;

    double uclT;

    double clT;

    double lclT;

    double uclK;

    double clK;

    double lclK;

    double[] dataArrayT;
    double[] dataArrayK;

    double graduationT;
    double graduationK;

    String pointsCRadioT;
    String pointsCRadioK;

    String pointsSpecialRadioT;
    String pointsSpecialRadioK;

    List<Integer> specialPointsT;
    List<Integer> specialPointsK;

    List<ArrayList<Integer>> descendChainTList;
    List<ArrayList<Integer> > ascendChainTList;
    List<ArrayList<Integer> > lowerChainTList;
    List<ArrayList<Integer> > upperChainTList;
    List<ArrayList<Integer>> descendChainKList;
    List<ArrayList<Integer> > ascendChainKList;
    List<ArrayList<Integer> > lowerChainKList;
    List<ArrayList<Integer> > upperChainKList;

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

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public double getUclT() {
        return uclT;
    }

    public void setUclT(double uclT) {
        this.uclT = uclT;
    }

    public double getClT() {
        return clT;
    }

    public void setClT(double clT) {
        this.clT = clT;
    }

    public double getLclT() {
        return lclT;
    }

    public void setLclT(double lclT) {
        this.lclT = lclT;
    }

    public double getUclK() {
        return uclK;
    }

    public void setUclK(double uclK) {
        this.uclK = uclK;
    }

    public double getClK() {
        return clK;
    }

    public void setClK(double clK) {
        this.clK = clK;
    }

    public double getLclK() {
        return lclK;
    }

    public void setLclK(double lclK) {
        this.lclK = lclK;
    }

    public double[] getDataArrayT() {
        return dataArrayT;
    }

    public void setDataArrayT(double[] dataArrayT) {
        this.dataArrayT = dataArrayT;
    }

    public double[] getDataArrayK() {
        return dataArrayK;
    }

    public void setDataArrayK(double[] dataArrayK) {
        this.dataArrayK = dataArrayK;
    }

    public double getGraduationT() {
        return graduationT;
    }

    public void setGraduationT(double graduationT) {
        this.graduationT = graduationT;
    }

    public double getGraduationK() {
        return graduationK;
    }

    public void setGraduationK(double graduationK) {
        this.graduationK = graduationK;
    }

    public String getPointsCRadioT() {
        return pointsCRadioT;
    }

    public void setPointsCRadioT(String pointsCRadioT) {
        this.pointsCRadioT = pointsCRadioT;
    }

    public String getPointsCRadioK() {
        return pointsCRadioK;
    }

    public void setPointsCRadioK(String pointsCRadioK) {
        this.pointsCRadioK = pointsCRadioK;
    }

    public String getPointsSpecialRadioT() {
        return pointsSpecialRadioT;
    }

    public void setPointsSpecialRadioT(String pointsSpecialRadioT) {
        this.pointsSpecialRadioT = pointsSpecialRadioT;
    }

    public String getPointsSpecialRadioK() {
        return pointsSpecialRadioK;
    }

    public void setPointsSpecialRadioK(String pointsSpecialRadioK) {
        this.pointsSpecialRadioK = pointsSpecialRadioK;
    }

    public List<Integer> getSpecialPointsT() {
        return specialPointsT;
    }

    public void setSpecialPointsT(List<Integer> specialPointsT) {
        this.specialPointsT = specialPointsT;
    }

    public List<Integer> getSpecialPointsK() {
        return specialPointsK;
    }

    public void setSpecialPointsK(List<Integer> specialPointsK) {
        this.specialPointsK = specialPointsK;
    }

    public List<ArrayList<Integer>> getDescendChainTList() {
        return descendChainTList;
    }

    public void setDescendChainTList(List<ArrayList<Integer>> descendChainTList) {
        this.descendChainTList = descendChainTList;
    }

    public List<ArrayList<Integer>> getAscendChainTList() {
        return ascendChainTList;
    }

    public void setAscendChainTList(List<ArrayList<Integer>> ascendChainTList) {
        this.ascendChainTList = ascendChainTList;
    }

    public List<ArrayList<Integer>> getLowerChainTList() {
        return lowerChainTList;
    }

    public void setLowerChainTList(List<ArrayList<Integer>> lowerChainTList) {
        this.lowerChainTList = lowerChainTList;
    }

    public List<ArrayList<Integer>> getUpperChainTList() {
        return upperChainTList;
    }

    public void setUpperChainTList(List<ArrayList<Integer>> upperChainTList) {
        this.upperChainTList = upperChainTList;
    }

    public List<ArrayList<Integer>> getDescendChainKList() {
        return descendChainKList;
    }

    public void setDescendChainKList(List<ArrayList<Integer>> descendChainKList) {
        this.descendChainKList = descendChainKList;
    }

    public List<ArrayList<Integer>> getAscendChainKList() {
        return ascendChainKList;
    }

    public void setAscendChainKList(List<ArrayList<Integer>> ascendChainKList) {
        this.ascendChainKList = ascendChainKList;
    }

    public List<ArrayList<Integer>> getLowerChainKList() {
        return lowerChainKList;
    }

    public void setLowerChainKList(List<ArrayList<Integer>> lowerChainKList) {
        this.lowerChainKList = lowerChainKList;
    }

    public List<ArrayList<Integer>> getUpperChainKList() {
        return upperChainKList;
    }

    public void setUpperChainKList(List<ArrayList<Integer>> upperChainKList) {
        this.upperChainKList = upperChainKList;
    }

    @Override
    public String toString() {
        return "GraphDataTK{" +
                "graphType='" + graphType + '\'' +
                ", dataArray=" + Arrays.toString(dataArray) +
                ", subgroupCapacity=" + subgroupCapacity +
                ", subgroupTotal=" + subgroupTotal +
                ", samplesNum=" + samplesNum +
                ", sortNum=" + sortNum +
                ", uclT=" + uclT +
                ", clT=" + clT +
                ", lclT=" + lclT +
                ", uclK=" + uclK +
                ", clK=" + clK +
                ", lclK=" + lclK +
                ", dataArrayT=" + Arrays.toString(dataArrayT) +
                ", dataArrayK=" + Arrays.toString(dataArrayK) +
                ", graduationT=" + graduationT +
                ", graduationK=" + graduationK +
                ", pointsCRadioT='" + pointsCRadioT + '\'' +
                ", pointsCRadioK='" + pointsCRadioK + '\'' +
                ", pointsSpecialRadioT='" + pointsSpecialRadioT + '\'' +
                ", pointsSpecialRadioK='" + pointsSpecialRadioK + '\'' +
                ", specialPointsT=" + specialPointsT +
                ", specialPointsK=" + specialPointsK +
                ", descendChainTList=" + descendChainTList +
                ", ascendChainTList=" + ascendChainTList +
                ", lowerChainTList=" + lowerChainTList +
                ", upperChainTList=" + upperChainTList +
                ", descendChainKList=" + descendChainKList +
                ", ascendChainKList=" + ascendChainKList +
                ", lowerChainKList=" + lowerChainKList +
                ", upperChainKList=" + upperChainKList +
                '}';
    }
}
