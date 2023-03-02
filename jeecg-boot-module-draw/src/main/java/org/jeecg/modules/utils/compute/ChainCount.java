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
        ArrayList<Integer> ascendChain = new ArrayList<>();


        for (int i = 0; i < len-1; i++) {
            if (array[i] <= array[i+1]) {
                ascendChain.add(i+1);
                cntAscend++;
            } else {
                ascendChain.clear();
                cntAscend = 0;
            }

            if (cntAscend == n-1) {
                ascendChain.add(i+2);
                ascendChainList.add(ascendChain);
                ascendChain = new ArrayList<>();
                cntAscend = 0;
            }
        }

        return ascendChainList;
    }


    /*
    数组下降链统计
     */
    public static List<ArrayList<Integer>> descendChainCount(int n, int len, double[] array) {
        List<ArrayList<Integer> > descendChainList = new ArrayList<>();

        int cntDescend = 0;
        ArrayList<Integer> descendChain = new ArrayList<>();


        for (int i = 0; i < len-1; i++) {
            if (array[i] >= array[i+1]) {
                descendChain.add(i+1);
                cntDescend++;
            } else {
                descendChain.clear();
                cntDescend = 0;
            }

            if (cntDescend == n-1) {
                descendChain.add(i+2);
                descendChainList.add(descendChain);
                descendChain = new ArrayList<>();
                cntDescend = 0;
            }
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
        ArrayList<Integer> upperChain = new ArrayList<>();


        for (int i = 0; i < len-1; i++) {
            if (array[i] > mean && array[i+1] > mean) {
                upperChain.add(i+1);
                cntUpper++;
            } else {
                upperChain.clear();
                cntUpper = 0;
            }

            if (cntUpper == n-1) {
                upperChain.add(i+2);
                upperChainList.add(upperChain);
                upperChain = new ArrayList<>();
                cntUpper = 0;
            }
        }

        return upperChainList;
    }

    /*
    数组下侧链统计
     */
    public static List<ArrayList<Integer>> lowerChainCount(int n, int len, double[] array, double mean) {
        List<ArrayList<Integer> > lowerChainList = new ArrayList<>();

        int cntLower = 0;
        ArrayList<Integer> lowerChain = new ArrayList<>();


        for (int i = 0; i < len-1; i++) {
            if (array[i] < mean && array[i+1] < mean) {
                lowerChain.add(i+1);
                cntLower++;
            } else {
                lowerChain.clear();
                cntLower = 0;
            }

            if (cntLower == n-1) {
                lowerChain.add(i+2);
                lowerChainList.add(lowerChain);
                lowerChain = new ArrayList<>();
                cntLower = 0;
            }
        }

        return lowerChainList;
    }
}
