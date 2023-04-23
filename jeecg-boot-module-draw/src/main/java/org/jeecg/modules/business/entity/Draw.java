package org.jeecg.modules.business.entity;

import java.util.Arrays;

public class Draw {
    String graphType;

    String quantile;

    int batchNum;

    int subgroupTotal;

    int subgroupCapacity;

    int varNum;

    double usl;

    double sl;

    double lsl;

    double[][] dataArrayXRXSMedium;

    double[] dataArrayXMR;

    double[] dataArrayCnP;

    int[] dataArrayPUPTUTSubgroupsCapacity;
    int[] dataArrayPUPTUTDefectsNum;

    // 回归、T-K控制图数据
    double[][] dataArrayRegressionTK;
    double[] dataArrayRegressionTKStandard;
    String[] dataArrayRegressionTKPrecision;
    int[] dataArrayRegressionTKSort;

    // 一阶嵌套控制图数据
    double[][] dataArrayFirstOrderNested;

    // 二阶嵌套控制图数据
    double[][][] dataArraySecondOrderNested;

    // 多变量T2控制图数据
    double[][][] dataArrayT2;

    // 单值多变量T2控制图数据
    double[][] dataArrayT2Single;

    // 综合控制图（”嵌套-回归“控制图）数据
    double[][] dataArrayIntegratedDemo;
    double[] dataArrayIntegratedDemoStandard;

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

    public int getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(int batchNum) {
        this.batchNum = batchNum;
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

    public double[][] getDataArrayRegressionTK() {
        return dataArrayRegressionTK;
    }

    public void setDataArrayRegressionTK(double[][] dataArrayRegressionTK) {
        this.dataArrayRegressionTK = dataArrayRegressionTK;
    }

    public double[] getDataArrayRegressionTKStandard() {
        return dataArrayRegressionTKStandard;
    }

    public void setDataArrayRegressionTKStandard(double[] dataArrayRegressionTKStandard) {
        this.dataArrayRegressionTKStandard = dataArrayRegressionTKStandard;
    }

    public String[] getDataArrayRegressionTKPrecision() {
        return dataArrayRegressionTKPrecision;
    }

    public void setDataArrayRegressionTKPrecision(String[] dataArrayRegressionTKPrecision) {
        this.dataArrayRegressionTKPrecision = dataArrayRegressionTKPrecision;
    }

    public int[] getDataArrayRegressionTKSort() {
        return dataArrayRegressionTKSort;
    }

    public void setDataArrayRegressionTKSort(int[] dataArrayRegressionTKSort) {
        this.dataArrayRegressionTKSort = dataArrayRegressionTKSort;
    }

    public double[][] getDataArrayFirstOrderNested() {
        return dataArrayFirstOrderNested;
    }

    public void setDataArrayFirstOrderNested(double[][] dataArrayFirstOrderNested) {
        this.dataArrayFirstOrderNested = dataArrayFirstOrderNested;
    }

    public double[][][] getDataArraySecondOrderNested() {
        return dataArraySecondOrderNested;
    }

    public void setDataArraySecondOrderNested(double[][][] dataArraySecondOrderNested) {
        this.dataArraySecondOrderNested = dataArraySecondOrderNested;
    }

    public double[][][] getDataArrayT2() {
        return dataArrayT2;
    }

    public void setDataArrayT2(double[][][] dataArrayT2) {
        this.dataArrayT2 = dataArrayT2;
    }

    public double[][] getDataArrayT2Single() {
        return dataArrayT2Single;
    }

    public void setDataArrayT2Single(double[][] dataArrayT2Single) {
        this.dataArrayT2Single = dataArrayT2Single;
    }

    public double[][] getDataArrayIntegratedDemo() {
        return dataArrayIntegratedDemo;
    }

    public void setDataArrayIntegratedDemo(double[][] dataArrayIntegratedDemo) {
        this.dataArrayIntegratedDemo = dataArrayIntegratedDemo;
    }

    public double[] getDataArrayIntegratedDemoStandard() {
        return dataArrayIntegratedDemoStandard;
    }

    public void setDataArrayIntegratedDemoStandard(double[] dataArrayIntegratedDemoStandard) {
        this.dataArrayIntegratedDemoStandard = dataArrayIntegratedDemoStandard;
    }

    @Override
    public String toString() {
        return "Draw{" +
                "graphType='" + graphType + '\'' +
                ", quantile='" + quantile + '\'' +
                ", batchNum=" + batchNum +
                ", subgroupTotal=" + subgroupTotal +
                ", subgroupCapacity=" + subgroupCapacity +
                ", varNum=" + varNum +
                ", usl=" + usl +
                ", sl=" + sl +
                ", lsl=" + lsl +
                ", dataArrayXRXSMedium=" + Arrays.toString(dataArrayXRXSMedium) +
                ", dataArrayXMR=" + Arrays.toString(dataArrayXMR) +
                ", dataArrayCnP=" + Arrays.toString(dataArrayCnP) +
                ", dataArrayPUPTUTSubgroupsCapacity=" + Arrays.toString(dataArrayPUPTUTSubgroupsCapacity) +
                ", dataArrayPUPTUTDefectsNum=" + Arrays.toString(dataArrayPUPTUTDefectsNum) +
                ", dataArrayRegressionTK=" + Arrays.toString(dataArrayRegressionTK) +
                ", dataArrayRegressionTKStandard=" + Arrays.toString(dataArrayRegressionTKStandard) +
                ", dataArrayRegressionTKPrecision=" + Arrays.toString(dataArrayRegressionTKPrecision) +
                ", dataArrayRegressionTKSort=" + Arrays.toString(dataArrayRegressionTKSort) +
                ", dataArrayFirstOrderNested=" + Arrays.toString(dataArrayFirstOrderNested) +
                ", dataArraySecondOrderNested=" + Arrays.toString(dataArraySecondOrderNested) +
                ", dataArrayT2=" + Arrays.toString(dataArrayT2) +
                ", dataArrayT2Single=" + Arrays.toString(dataArrayT2Single) +
                ", dataArrayIntegratedDemo=" + Arrays.toString(dataArrayIntegratedDemo) +
                ", dataArrayIntegratedDemoStandard=" + Arrays.toString(dataArrayIntegratedDemoStandard) +
                '}';
    }
}
