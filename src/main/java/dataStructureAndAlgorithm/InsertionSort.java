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
        insertionSortDichotomy(srcSort);

        for (int i = 0; i < srcSort.length; i++) {
            if (i == srcSort.length - 1) {
                System.out.println(srcSort[i]);
            } else {
                System.out.print(srcSort[i]+",");
            }
        }
    }

    private static int[] insertionSort(int[] srcSort) {
        for (int i =1; i < srcSort.length; i++) {
            int get = srcSort[i];
            int j = i - 1;
            while (  j >= 0&&srcSort[j] > get) {
                srcSort[j + 1] = srcSort[j];
                j--;
            }
            srcSort[j+1] = get;


        }
        return srcSort;
    }

    private static int[] insertionSortDichotomy(int[] srcSort) {
        for (int i = 1; i < srcSort.length; i++) {
            int get = srcSort[i];
            int left = 0;
            int right = i - 1;
            while (left <= right) {
                int mid = (right + left) / 2;
                if (srcSort[mid] > get) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            for (int j = i - 1; j >= left; j--) {
                srcSort[j + 1] = srcSort[j];
            }
            srcSort[left] = get;

        }
        return srcSort;

    }

}


