package dataStructureAndAlgorithm;

/**
 * 插入排序
 *
 * @author bin.yu
 * @create 2018-07-05 21:46
 **/
public class InsertionSort {


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
//        insertionSort(srcSort);
        shellSort(srcSort);

        for (int i = 0; i < srcSort.length; i++) {
            if (i == srcSort.length - 1) {
                System.out.println(srcSort[i]);
            } else {
                System.out.print(srcSort[i]+",");
            }
        }
    }

    private static int[] insertionSort(int[] srcSort) {
        for(int i = 1; i < srcSort.length; i++) {
            for(int j = i; (j > 0) && (srcSort[j] < srcSort[j-1]); j--) {
                swap(srcSort, j, j - 1);
            }
        }
        return srcSort;
    }

    // 希尔排序
    private static void shellSort(int[] srcSort) {

        int N = srcSort.length;
        int h = 1;

        while(h < N/3){
            h = 3*h + 1;
        }
        //将数组变为h有序
        while(h >= 1){
            //将a[i]插入到a[i-h],a[i-2*h],a[i-3*h]...之中
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && srcSort[j]<srcSort[j-h]; j-=h) {
                    swap(srcSort, j, j-h);
                }
            }
            h = h/3;
        }
    }


    private static void swap(int[] arr,int a,int b) {
        int temp = arr[a];
        arr[a] =arr[b];
        arr[b] = temp;
    }
}


