package org.jeecg.modules.business.entity;

public class DataInputTemplate {
    String  graphType;
    Integer subSamplesNum;
    Integer subgroupTotal;
    Integer fixedSubgroupCapacity;
    Double LSL;
    Double USL;
    Double SL;

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public Integer getSubSamplesNum() {
        return subSamplesNum;
    }

    public void setSubSamplesNum(Integer subSamplesNum) {
        this.subSamplesNum = subSamplesNum;
    }

    public Integer getSubgroupTotal() {
        return subgroupTotal;
    }

    public void setSubgroupTotal(Integer subgroupTotal) {
        this.subgroupTotal = subgroupTotal;
    }

    public Integer getFixedSubgroupCapacity() {
        return fixedSubgroupCapacity;
    }

    public void setFixedSubgroupCapacity(Integer fixedSubgroupCapacity) {
        this.fixedSubgroupCapacity = fixedSubgroupCapacity;
    }

    public Double getLSL() {
        return LSL;
    }

    public void setLSL(Double LSL) {
        this.LSL = LSL;
    }

    public Double getUSL() {
        return USL;
    }

    public void setUSL(Double USL) {
        this.USL = USL;
    }

    public Double getSL() {
        return SL;
    }

    public void setSL(Double SL) {
        this.SL = SL;
    }

    @Override
    public String toString() {
        return "DataInputTemplate{" +
                "graphType='" + graphType + '\'' +
                ", subSamplesNum=" + subSamplesNum +
                ", subgroupTotal=" + subgroupTotal +
                ", fixedSubgroupCapacity=" + fixedSubgroupCapacity +
                ", LSL=" + LSL +
                ", USL=" + USL +
                ", SL=" + SL +
                '}';
    }
}
