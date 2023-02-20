package org.jeecg.modules.business.entity;

public class DataLoginTemplate {
    String  graphType;

    Integer subgroupTotal;

    Integer subgroupCapacity;

    Double LSL;

    Double USL;

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public Integer getSubgroupTotal() {
        return subgroupTotal;
    }

    public void setSubgroupTotal(Integer subgroupTotal) {
        this.subgroupTotal = subgroupTotal;
    }

    public Integer getSubgroupCapacity() {
        return subgroupCapacity;
    }

    public void setSubgroupCapacity(Integer subgroupCapacity) {
        this.subgroupCapacity = subgroupCapacity;
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

    @Override
    public String toString() {
        return "DataInputTemplate{" +
                "graphType='" + graphType + '\'' +
                ", subgroupTotal=" + subgroupTotal +
                ", subgroupCapacity=" + subgroupCapacity +
                ", LSL=" + LSL +
                ", USL=" + USL +
                '}';
    }
}