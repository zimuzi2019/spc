package org.jeecg.modules.business.entity;

import java.util.Arrays;

public class Draw {
    String graphType;

    int subgroupTotal;

    int subgroupCapacity;

    double usl;

    double lsl;

    double[][] dataArrayXRXSMedium;

    double[] dataArrayXMR;

    double[] dataArrayCnP;

    double[] dataArrayPU1;
    double[] dataArrayPU2;

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

    public double[] getDataArrayPU1() {
        return dataArrayPU1;
    }

    public void setDataArrayPU1(double[] dataArrayPU1) {
        this.dataArrayPU1 = dataArrayPU1;
    }

    public double[] getDataArrayPU2() {
        return dataArrayPU2;
    }

    public void setDataArrayPU2(double[] dataArrayPU2) {
        this.dataArrayPU2 = dataArrayPU2;
    }

    @Override
    public String toString() {
        return "Draw{" +
                "graphType='" + graphType + '\'' +
                ", subgroupTotal=" + subgroupTotal +
                ", subgroupCapacity=" + subgroupCapacity +
                ", usl=" + usl +
                ", lsl=" + lsl +
                ", dataArrayXRXSMedium=" + Arrays.toString(dataArrayXRXSMedium) +
                ", dataArrayXMR=" + Arrays.toString(dataArrayXMR) +
                ", dataArrayCnP=" + Arrays.toString(dataArrayCnP) +
                ", dataArrayPU1=" + Arrays.toString(dataArrayPU1) +
                ", dataArrayPU2=" + Arrays.toString(dataArrayPU2) +
                '}';
    }
}
