package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataPU implements GraphData{
    String graphType;

    int subgroupTotal;

    double avgSubgroupCapacity;

    int subgroupCapacityMax;

    int subgroupCapacityMin;

    int samplesNum;

    int defectsNum;

    double avgDefectsNum;

    double ucl;

    double lcl;

    double cl;

    double[] dataArray;

    double graduation;

    List<Integer> specialPoints;

    List<ArrayList<Integer>> descendChainList;
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

    public double getAvgSubgroupCapacity() {
        return avgSubgroupCapacity;
    }

    public void setAvgSubgroupCapacity(double avgSubgroupCapacity) {
        this.avgSubgroupCapacity = avgSubgroupCapacity;
    }

    public int getSubgroupCapacityMax() {
        return subgroupCapacityMax;
    }

    public void setSubgroupCapacityMax(int subgroupCapacityMax) {
        this.subgroupCapacityMax = subgroupCapacityMax;
    }

    public int getSubgroupCapacityMin() {
        return subgroupCapacityMin;
    }

    public void setSubgroupCapacityMin(int getSubgroupCapacityMin) {
        this.subgroupCapacityMin = getSubgroupCapacityMin;
    }

    public int getSamplesNum() {
        return samplesNum;
    }

    public void setSamplesNum(int samplesNum) {
        this.samplesNum = samplesNum;
    }

    public int getDefectsNum() {
        return defectsNum;
    }

    public void setDefectsNum(int defectsNum) {
        this.defectsNum = defectsNum;
    }

    public double getAvgDefectsNum() {
        return avgDefectsNum;
    }

    public void setAvgDefectsNum(double avgDefectsNum) {
        this.avgDefectsNum = avgDefectsNum;
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

    public double getGraduation() {
        return graduation;
    }

    public void setGraduation(double graduation) {
        this.graduation = graduation;
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

    @Override
    public String toString() {
        return "GraphDataPU{" +
                "graphType='" + graphType + '\'' +
                ", subgroupTotal=" + subgroupTotal +
                ", avgSubgroupCapacity=" + avgSubgroupCapacity +
                ", subgroupCapacityMax=" + subgroupCapacityMax +
                ", subgroupCapacityMin=" + subgroupCapacityMin +
                ", samplesNum=" + samplesNum +
                ", defectsNum=" + defectsNum +
                ", avgDefectsNum=" + avgDefectsNum +
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
