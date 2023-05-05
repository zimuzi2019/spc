package org.jeecg.modules.utils.compute;

import java.util.ArrayList;
import java.util.List;

public class ChainCount {
    /*
    数组连续上升链统计
    n: 链长
    len: 数组长度
    array: 数组
     */
    public static List<ArrayList<Integer>> ascendChainCount(int n, int len, double[] array) {
        List<ArrayList<Integer> > ascendChainList = new ArrayList<>();

        int cntAscend = 0;
        int ascendStart = -1;


        for (int i = 0; i < len-1; i++) {
            if (array[i] <= array[i+1]) {
                if (cntAscend == 0) ascendStart = i+1;
                cntAscend++;
            } else {
                if (cntAscend >= n-1) {
                    ArrayList<Integer> ascendChain = new ArrayList<>();
                    ascendChain.add(ascendStart);
                    ascendChain.add(i+1);
                    ascendChainList.add(ascendChain);
                }
                cntAscend = 0;
            }
        }

        if (cntAscend >= n-1) {
            ArrayList<Integer> ascendChain = new ArrayList<>();
            ascendChain.add(ascendStart);
            ascendChain.add(len);
            ascendChainList.add(ascendChain);
        }

        return ascendChainList;
    }


    /*
    数组下降链统计
     */
    public static List<ArrayList<Integer>> descendChainCount(int n, int len, double[] array) {
        List<ArrayList<Integer> > descendChainList = new ArrayList<>();

        int cntDescend = 0;
        int descendStart = -1;


        for (int i = 0; i < len-1; i++) {
            if (array[i] >= array[i+1]) {
                if (cntDescend == 0) descendStart = i+1;
                cntDescend++;
            } else {
                if (cntDescend >= n-1) {
                    ArrayList<Integer> descendChain = new ArrayList<>();
                    descendChain.add(descendStart);
                    descendChain.add(i+1);
                    descendChainList.add(descendChain);
                }
                cntDescend = 0;
            }
        }

        if (cntDescend >= n-1) {
            ArrayList<Integer> descendChain = new ArrayList<>();
            descendChain.add(descendStart);
            descendChain.add(len);
            descendChainList.add(descendChain);
        }

        return descendChainList;
    }

    /*
    数组上侧链统计
    mean: 均值
     */
    public static List<ArrayList<Integer>> upperChainCount(int n, int len, double[] array, double mean) {
        List<ArrayList<Integer> > upperChainList = new ArrayList<>();

        int cntUpper = 0;
        int upperStart = -1;


        for (int i = 0; i < len-1; i++) {
            if (array[i] > mean && array[i+1] > mean) {
                if (cntUpper == 0) upperStart = i+1;
                cntUpper++;
            } else {
                if (cntUpper >= n-1) {
                    ArrayList<Integer> upperChain = new ArrayList<>();
                    upperChain.add(upperStart);
                    upperChain.add(i+1);
                    upperChainList.add(upperChain);
                }
                cntUpper = 0;
            }
        }

        if (cntUpper >= n-1) {
            ArrayList<Integer> upperChain = new ArrayList<>();
            upperChain.add(upperStart);
            upperChain.add(len);
            upperChainList.add(upperChain);
        }

        return upperChainList;
    }

    /*
    数组下侧链统计
     */
    public static List<ArrayList<Integer>> lowerChainCount(int n, int len, double[] array, double mean) {
        List<ArrayList<Integer> > lowerChainList = new ArrayList<>();

        int cntLower = 0;
        int lowerStart = -1;


        for (int i = 0; i < len-1; i++) {
            if (array[i] < mean && array[i+1] < mean) {
                if (cntLower == 0) lowerStart = i+1;
                cntLower++;
            } else {
                if (cntLower >= n-1) {
                    ArrayList<Integer> lowerChain = new ArrayList<>();
                    lowerChain.add(lowerStart);
                    lowerChain.add(i+1);
                    lowerChainList.add(lowerChain);
                }
                cntLower = 0;
            }
        }

        if (cntLower >= n-1) {
            ArrayList<Integer> lowerChain = new ArrayList<>();
            lowerChain.add(lowerStart);
            lowerChain.add(len);
            lowerChainList.add(lowerChain);
        }

        return lowerChainList;
    }

    public static List<ArrayList<Integer>> upperChainCountPU(int n, int len, double[] array, double[] mean) {
        List<ArrayList<Integer> > upperChainList = new ArrayList<>();

        int cntUpper = 0;
        int upperStart = -1;


        for (int i = 0; i < len-1; i++) {
            if (array[i] > mean[i] && array[i+1] > mean[i+1]) {
                if (cntUpper == 0) upperStart = i+1;
                cntUpper++;
            } else {
                if (cntUpper >= n-1) {
                    ArrayList<Integer> upperChain = new ArrayList<>();
                    upperChain.add(upperStart);
                    upperChain.add(i+1);
                    upperChainList.add(upperChain);
                }
                cntUpper = 0;
            }
        }

        if (cntUpper >= n-1) {
            ArrayList<Integer> upperChain = new ArrayList<>();
            upperChain.add(upperStart);
            upperChain.add(len);
            upperChainList.add(upperChain);
        }

        return upperChainList;
    }

    /*
    数组下侧链统计
     */
    public static List<ArrayList<Integer>> lowerChainCountPU(int n, int len, double[] array, double[] mean) {
        List<ArrayList<Integer> > lowerChainList = new ArrayList<>();

        int cntLower = 0;
        int lowerStart = -1;


        for (int i = 0; i < len-1; i++) {
            if (array[i] < mean[i] && array[i+1] < mean[i+1]) {
                if (cntLower == 0) lowerStart = i+1;
                cntLower++;
            } else {
                if (cntLower >= n-1) {
                    ArrayList<Integer> lowerChain = new ArrayList<>();
                    lowerChain.add(lowerStart);
                    lowerChain.add(i+1);
                    lowerChainList.add(lowerChain);
                }
                cntLower = 0;
            }
        }

        if (cntLower >= n-1) {
            ArrayList<Integer> lowerChain = new ArrayList<>();
            lowerChain.add(lowerStart);
            lowerChain.add(len);
            lowerChainList.add(lowerChain);
        }

        return lowerChainList;
    }
}
