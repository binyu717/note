package dataStructureAndAlgorithm.algorithm;

/**
 * 冒泡排序
 *
 * @author bin.yu
 * @create 2018-06-30 20:02
 **/
public class BubbleSort {

    public static void main(String[] args) {
        int[] srcSort = new int[]{4,5,7,2,10,3,2,6};
        for (int i = 0; i < srcSort.length; i++) {
            if (i == srcSort.length - 1) {
                System.out.println(srcSort[i]);
            } else {
                System.out.print(srcSort[i]+",");
            }
        }
        System.out.println("============排序后============");
        maopao1(srcSort);

        for (int i = 0; i < srcSort.length; i++) {
            if (i == srcSort.length - 1) {
                System.out.println(srcSort[i]);
            } else {
                System.out.print(srcSort[i]+",");
            }
        }

    }

    /**
     * 普通冒泡排序
     * @param srcSort
     * @return
     */
    private static int[] maopao(int[] srcSort){
        int length = srcSort.length;
        for (int i = 0; i < length-1; i++) {
            for (int j = 0; j < length-1-i; j++) {
                if (srcSort[j] > srcSort[j + 1]) {
                   swap(srcSort,j,j+1);
                }
            }
        }
        return srcSort;
    }


    /**
     * 冒泡改进——鸡尾酒排序
     * @param srcSort
     * @return
     */
    private static int[] maopao1(int[] srcSort) {
        int left = 0;
        int right = srcSort.length - 1;
        while (left <= right) {
            for (int i = left; i < right; i++) {
                if (srcSort[i] > srcSort[i + 1]) {
                    swap(srcSort,i,i+1);
                }
            }
            right--;
            for (int i = right; i > left; i--) {
                if (srcSort[i] < srcSort[i - 1]) {
                    swap(srcSort,i,i-1);
                }
            }
            left++;
        }
        return srcSort;
    }

    private static void swap(int[] arr,int a,int b) {
        int temp = arr[a];
        arr[a] =arr[b];
        arr[b] = temp;
    }


}
