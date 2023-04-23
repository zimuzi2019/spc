package org.jeecg.modules.business.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDataT2 implements GraphData{
    String graphType;

    int subgroupTotal;

    int subgroupCapacity;

    int varNum;

    double uclT2Analysis;

    double lclT2Analysis;

    double uclT2Control;

    double lclT2Control;

    double[] dataArrayT2;

    double graduationT2;

    String pointsSpecialRadioT2Analysis;
    String pointsSpecialRadioT2Control;

    List<Integer> specialPointsT2Analysis;
    List<Integer> specialPointsT2Control;

    List<ArrayList<Integer>> descendChainT2AnalysisList;
    List<ArrayList<Integer> > ascendChainT2AnalysisList;
    List<ArrayList<Integer>> descendChainT2ControlList;
    List<ArrayList<Integer> > ascendChainT2ControlList;

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

    public int getVarNum() {
        return varNum;
    }

    public void setVarNum(int varNum) {
        this.varNum = varNum;
    }

    public double getUclT2Analysis() {
        return uclT2Analysis;
    }

    public void setUclT2Analysis(double uclT2Analysis) {
        this.uclT2Analysis = uclT2Analysis;
    }

    public double getLclT2Analysis() {
        return lclT2Analysis;
    }

    public void setLclT2Analysis(double lclT2Analysis) {
        this.lclT2Analysis = lclT2Analysis;
    }

    public double getUclT2Control() {
        return uclT2Control;
    }

    public void setUclT2Control(double uclT2Control) {
        this.uclT2Control = uclT2Control;
    }

    public double getLclT2Control() {
        return lclT2Control;
    }

    public void setLclT2Control(double lclT2Control) {
        this.lclT2Control = lclT2Control;
    }

    public double[] getDataArrayT2() {
        return dataArrayT2;
    }

    public void setDataArrayT2(double[] dataArrayT2) {
        this.dataArrayT2 = dataArrayT2;
    }

    public double getGraduationT2() {
        return graduationT2;
    }

    public void setGraduationT2(double graduationT2) {
        this.graduationT2 = graduationT2;
    }

    public String getPointsSpecialRadioT2Analysis() {
        return pointsSpecialRadioT2Analysis;
    }

    public void setPointsSpecialRadioT2Analysis(String pointsSpecialRadioT2Analysis) {
        this.pointsSpecialRadioT2Analysis = pointsSpecialRadioT2Analysis;
    }

    public String getPointsSpecialRadioT2Control() {
        return pointsSpecialRadioT2Control;
    }

    public void setPointsSpecialRadioT2Control(String pointsSpecialRadioT2Control) {
        this.pointsSpecialRadioT2Control = pointsSpecialRadioT2Control;
    }

    public List<Integer> getSpecialPointsT2Analysis() {
        return specialPointsT2Analysis;
    }

    public void setSpecialPointsT2Analysis(List<Integer> specialPointsT2Analysis) {
        this.specialPointsT2Analysis = specialPointsT2Analysis;
    }

    public List<Integer> getSpecialPointsT2Control() {
        return specialPointsT2Control;
    }

    public void setSpecialPointsT2Control(List<Integer> specialPointsT2Control) {
        this.specialPointsT2Control = specialPointsT2Control;
    }

    public List<ArrayList<Integer>> getDescendChainT2AnalysisList() {
        return descendChainT2AnalysisList;
    }

    public void setDescendChainT2AnalysisList(List<ArrayList<Integer>> descendChainT2AnalysisList) {
        this.descendChainT2AnalysisList = descendChainT2AnalysisList;
    }

    public List<ArrayList<Integer>> getAscendChainT2AnalysisList() {
        return ascendChainT2AnalysisList;
    }

    public void setAscendChainT2AnalysisList(List<ArrayList<Integer>> ascendChainT2AnalysisList) {
        this.ascendChainT2AnalysisList = ascendChainT2AnalysisList;
    }

    public List<ArrayList<Integer>> getDescendChainT2ControlList() {
        return descendChainT2ControlList;
    }

    public void setDescendChainT2ControlList(List<ArrayList<Integer>> descendChainT2ControlList) {
        this.descendChainT2ControlList = descendChainT2ControlList;
    }

    public List<ArrayList<Integer>> getAscendChainT2ControlList() {
        return ascendChainT2ControlList;
    }

    public void setAscendChainT2ControlList(List<ArrayList<Integer>> ascendChainT2ControlList) {
        this.ascendChainT2ControlList = ascendChainT2ControlList;
    }

    @Override
    public String toString() {
        return "GraphDataT2{" +
                "graphType='" + graphType + '\'' +
                ", subgroupTotal=" + subgroupTotal +
                ", subgroupCapacity=" + subgroupCapacity +
                ", varNum=" + varNum +
                ", uclT2Analysis=" + uclT2Analysis +
                ", lclT2Analysis=" + lclT2Analysis +
                ", uclT2Control=" + uclT2Control +
                ", lclT2Control=" + lclT2Control +
                ", dataArrayT2=" + Arrays.toString(dataArrayT2) +
                ", graduationT2=" + graduationT2 +
                ", pointsSpecialRadioT2Analysis='" + pointsSpecialRadioT2Analysis + '\'' +
                ", pointsSpecialRadioT2Control='" + pointsSpecialRadioT2Control + '\'' +
                ", specialPointsT2Analysis=" + specialPointsT2Analysis +
                ", specialPointsT2Control=" + specialPointsT2Control +
                ", descendChainT2AnalysisList=" + descendChainT2AnalysisList +
                ", ascendChainT2AnalysisList=" + ascendChainT2AnalysisList +
                ", descendChainT2ControlList=" + descendChainT2ControlList +
                ", ascendChainT2ControlList=" + ascendChainT2ControlList +
                '}';
    }
}
