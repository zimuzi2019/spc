package org.jeecg.modules.business.entity;

import java.util.Arrays;

public class Draw {
    String graphType;

    int subgroupTotal;

    int subgroupCapacity;

    double usl;

    double sl;

    double lsl;

    double[][] dataArrayXRXSMedium;

    double[] dataArrayXMR;

    double[] dataArrayCnP;

    int[] dataArrayPUSubgroupsCapacity;
    int[] dataArrayPUDefectsNum;

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

    public double[][] getDataArrayXRXSMedium() {
        return dataArrayXRXSMedium;
    }

    public void setDataArrayXRXSMedium(double[][] dataArrayXRXSMedium) {
        this.dataArrayXRXSMedium = dataArrayXRXSMedium;
    }

    public double[] getDataArrayXMR() {
        return dataArrayXMR;
    }

    public void setDataArrayXMR(double[] dataArrayXMR) {
        this.dataArrayXMR = dataArrayXMR;
    }

    public double[] getDataArrayCnP() {
        return dataArrayCnP;
    }

    public void setDataArrayCnP(double[] dataArrayCnP) {
        this.dataArrayCnP = dataArrayCnP;
    }

    public int[] getDataArrayPUSubgroupsCapacity() {
        return dataArrayPUSubgroupsCapacity;
    }

    public void setDataArrayPUSubgroupsCapacity(int[] dataArrayPUSubgroupsCapacity) {
        this.dataArrayPUSubgroupsCapacity = dataArrayPUSubgroupsCapacity;
    }

    public int[] getDataArrayPUDefectsNum() {
        return dataArrayPUDefectsNum;
    }

    public void setDataArrayPUDefectsNum(int[] dataArrayPUDefectsNum) {
        this.dataArrayPUDefectsNum = dataArrayPUDefectsNum;
    }

    @Override
    public String toString() {
        return "Draw{" +
                "graphType='" + graphType + '\'' +
                ", subgroupTotal=" + subgroupTotal +
                ", subgroupCapacity=" + subgroupCapacity +
                ", usl=" + usl +
                ", sl=" + sl +
                ", lsl=" + lsl +
                ", dataArrayXRXSMedium=" + Arrays.toString(dataArrayXRXSMedium) +
                ", dataArrayXMR=" + Arrays.toString(dataArrayXMR) +
                ", dataArrayCnP=" + Arrays.toString(dataArrayCnP) +
                ", dataArrayPUSubgroupsCapacity=" + Arrays.toString(dataArrayPUSubgroupsCapacity) +
                ", dataArrayPUDefectsNum=" + Arrays.toString(dataArrayPUDefectsNum) +
                '}';
    }
}
