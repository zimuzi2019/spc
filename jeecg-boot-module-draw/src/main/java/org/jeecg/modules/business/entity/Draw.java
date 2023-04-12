package org.jeecg.modules.business.entity;

import java.util.Arrays;

public class Draw {
    String graphType;

    String quantile;

    int subgroupTotal;

    int subgroupCapacity;

    double usl;

    double sl;

    double lsl;

    double[][] dataArrayXRXSMedium;

    double[] dataArrayXMR;

    double[] dataArrayCnP;

    int[] dataArrayPUPTUTSubgroupsCapacity;
    int[] dataArrayPUPTUTDefectsNum;

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

    public int[] getDataArrayPUPTUTSubgroupsCapacity() {
        return dataArrayPUPTUTSubgroupsCapacity;
    }

    public void setDataArrayPUPTUTSubgroupsCapacity(int[] dataArrayPUPTUTSubgroupsCapacity) {
        this.dataArrayPUPTUTSubgroupsCapacity = dataArrayPUPTUTSubgroupsCapacity;
    }

    public int[] getDataArrayPUPTUTDefectsNum() {
        return dataArrayPUPTUTDefectsNum;
    }

    public void setDataArrayPUPTUTDefectsNum(int[] dataArrayPUPTUTDefectsNum) {
        this.dataArrayPUPTUTDefectsNum = dataArrayPUPTUTDefectsNum;
    }

    @Override
    public String toString() {
        return "Draw{" +
                "graphType='" + graphType + '\'' +
                ", quantile='" + quantile + '\'' +
                ", subgroupTotal=" + subgroupTotal +
                ", subgroupCapacity=" + subgroupCapacity +
                ", usl=" + usl +
                ", sl=" + sl +
                ", lsl=" + lsl +
                ", dataArrayXRXSMedium=" + Arrays.toString(dataArrayXRXSMedium) +
                ", dataArrayXMR=" + Arrays.toString(dataArrayXMR) +
                ", dataArrayCnP=" + Arrays.toString(dataArrayCnP) +
                ", dataArrayPUPTUTSubgroupsCapacity=" + Arrays.toString(dataArrayPUPTUTSubgroupsCapacity) +
                ", dataArrayPUPTUTDefectsNum=" + Arrays.toString(dataArrayPUPTUTDefectsNum) +
                '}';
    }
}
