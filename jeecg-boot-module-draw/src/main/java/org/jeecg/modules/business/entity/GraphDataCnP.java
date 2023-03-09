package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataCnP implements GraphData{
    String graphType;

    int subgroupTotal;

    int subgroupCapacity;

    int sampleNum;

    int defectsNum;

    double avgDefectNum;

    double ucl;

    double lcl;

    double cl;

    double[] dataArray;

    double graduation;

    List<Integer> specialPoints;

    List<ArrayList<Integer> > descendChainList;
    List<ArrayList<Integer> > ascendChainList;
    List<ArrayList<Integer> > lowerChainList;
    List<ArrayList<Integer> > upperChainList;

    double[] intervalValues;

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public int getSubgroupTotal() {
        return subgroupTotal;
    }

    public void setSubgroupTotal(int subgroupTotal) {
        this.subgroupTotal = subgroupTotal;
    }

    public int getSubgroupCapacity() {
        return subgroupCapacity;
    }

    public void setSubgroupCapacity(int subgroupCapacity) {
        this.subgroupCapacity = subgroupCapacity;
    }

    public int getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(int sampleNum) {
        this.sampleNum = sampleNum;
    }

    public int getDefectsNum() {
        return defectsNum;
    }

    public void setDefectsNum(int defectsNum) {
        this.defectsNum = defectsNum;
    }

    public double getAvgDefectNum() {
        return avgDefectNum;
    }

    public void setAvgDefectNum(double avgDefectNum) {
        this.avgDefectNum = avgDefectNum;
    }

    public double getUcl() {
        return ucl;
    }

    public void setUcl(double ucl) {
        this.ucl = ucl;
    }

    public double getLcl() {
        return lcl;
    }

    public void setLcl(double lcl) {
        this.lcl = lcl;
    }

    public double getCl() {
        return cl;
    }

    public void setCl(double cl) {
        this.cl = cl;
    }

    public double[] getDataArray() {
        return dataArray;
    }

    public void setDataArray(double[] dataArray) {
        this.dataArray = dataArray;
    }

    public List<Integer> getSpecialPoints() {
        return specialPoints;
    }

    public void setSpecialPoints(List<Integer> specialPoints) {
        this.specialPoints = specialPoints;
    }

    public List<ArrayList<Integer>> getDescendChainList() {
        return descendChainList;
    }

    public void setDescendChainList(List<ArrayList<Integer>> descendChainList) {
        this.descendChainList = descendChainList;
    }

    public List<ArrayList<Integer>> getAscendChainList() {
        return ascendChainList;
    }

    public void setAscendChainList(List<ArrayList<Integer>> ascendChainList) {
        this.ascendChainList = ascendChainList;
    }

    public List<ArrayList<Integer>> getLowerChainList() {
        return lowerChainList;
    }

    public void setLowerChainList(List<ArrayList<Integer>> lowerChainList) {
        this.lowerChainList = lowerChainList;
    }

    public List<ArrayList<Integer>> getUpperChainList() {
        return upperChainList;
    }

    public void setUpperChainList(List<ArrayList<Integer>> upperChainList) {
        this.upperChainList = upperChainList;
    }

    public double[] getIntervalValues() {
        return intervalValues;
    }

    public void setIntervalValues(double[] intervalValues) {
        this.intervalValues = intervalValues;
    }

    public double getGraduation() {
        return graduation;
    }

    public void setGraduation(double graduation) {
        this.graduation = graduation;
    }

    @Override
    public String toString() {
        return "GraphDataCnP{" +
                "graphType='" + graphType + '\'' +
                ", subgroupTotal=" + subgroupTotal +
                ", subgroupCapacity=" + subgroupCapacity +
                ", sampleNum=" + sampleNum +
                ", defectsNum=" + defectsNum +
                ", avgDefectNum=" + avgDefectNum +
                ", ucl=" + ucl +
                ", lcl=" + lcl +
                ", cl=" + cl +
                ", dataArray=" + Arrays.toString(dataArray) +
                ", graduation=" + graduation +
                ", specialPoints=" + specialPoints +
                ", descendChainList=" + descendChainList +
                ", ascendChainList=" + ascendChainList +
                ", lowerChainList=" + lowerChainList +
                ", upperChainList=" + upperChainList +
                ", intervalValues=" + Arrays.toString(intervalValues) +
                '}';
    }
}
