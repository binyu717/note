package dataStructureAndAlgorithm;

/**
 * 选择排序
 *
 * @author bin.yu
 * @create 2018-07-04 22:28
 **/
public class SellectionSort {

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
        selection(srcSort);

        for (int i = 0; i < srcSort.length; i++) {
            if (i == srcSort.length - 1) {
                System.out.println(srcSort[i]);
            } else {
                System.out.print(srcSort[i]+",");
            }
        }
    }


    private static int[] selection(int[] srcSort) {
        int length = srcSort.length;
        for (int i=0;i<length-1;i++) {
            int min = i;
            for (int j=i;j<length;j++) {
                if (srcSort[j] < srcSort[min]) {
                    min = j;
                }
            }
            swap(srcSort,i,min);
        }
        return srcSort;
    }


    private static void swap(int[] arr,int a,int b) {
        int temp = arr[a];
        arr[a] =arr[b];
        arr[b] = temp;
    }
}
