package dataStructureAndAlgorithm.algorithm;

/**
 * 快速排序
 *
 * @author bin.yu
 * @create 2018-05-17 15:32
 **/
public class QuickSort {

    public static void main(String[] args) {
        int[] srcSort = new int[]{49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};
        for (int i = 0; i < srcSort.length; i++) {
            if (i == srcSort.length - 1) {
                System.out.println(srcSort[i]);
            } else {
                System.out.print(srcSort[i]+",");
            }
        }
        System.out.println("============排序后============");
        myQuickSort(srcSort, 0, srcSort.length-1);

        for (int i = 0; i < srcSort.length; i++) {
            if (i == srcSort.length - 1) {
                System.out.println(srcSort[i]);
            } else {
                System.out.print(srcSort[i]+",");
            }
        }


    }

    /**
     *  快速排序
     *
     * @param a   a -- 待排序的数组
     * @param l   l -- 数组的左边界(例如，从起始位置开始排序，则l=0)
     * @param r   r -- 数组的右边界(例如，排序截至到数组末尾，则r=a.length-1)
     */
   static void quick_sort(int a[], int l, int r)
    {
        if (l < r)
        {
            int i,j,x;

            i = l;
            j = r;
            x = a[i];
            while (i < j)
            {
                while (i < j && a[j] > x) {
                    j--; // 从右向左找第一个小于x的数
                }
                if (i < j) {
                    a[i++] = a[j];
                }

                while (i < j && a[i] < x) {
                    i++; // 从左向右找第一个大于x的数
                }
                if (i < j) {
                    a[j--] = a[i];
                }
            }
            a[i] = x;
            /* 递归调用 */
            quick_sort(a, l, i-1);
            /* 递归调用 */
            quick_sort(a, i+1, r);
        }
    }

    static void myQuickSort(int a[], int l, int r) {
        if (l < r) {
            int temp = a[l];
            while (l < r) {
                while (l < r && a[r] > temp) {
                    r--;
                }
                if (l < r) {
                    a[l] = a[r];
                }
                while (l < r && a[l] < temp) {
                    l++;
                }
                if (l < r) {
                    a[r] = a[l];
                }
            }
            a[l] = temp;
            myQuickSort(a, 0, l - 1);
            myQuickSort(a, l + 1, r);
        }

    }


    private static void quickSort(int[] arr, int low, int high) {

        if (low < high) {
            // 找寻基准数据的正确索引
            int index = getIndex(arr, low, high);

            // 进行迭代对index之前和之后的数组进行相同的操作使整个数组变成有序
            quickSort(arr, 0, index - 1);
            quickSort(arr, index + 1, high);
        }

    }

    private static int getIndex(int[] arr, int low, int high) {
        // 基准数据
        int tmp = arr[low];
        while (low < high) {
            // 当队尾的元素大于等于基准数据时,向前挪动high指针
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 如果队尾元素小于tmp了,需要将其赋值给low
            arr[low] = arr[high];
            // 当队首元素小于等于tmp时,向前挪动low指针
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 当队首元素大于tmp时,需要将其赋值给high
            arr[high] = arr[low];

        }
        // 跳出循环时low和high相等,此时的low或high就是tmp的正确索引位置
        // 由原理部分可以很清楚的知道low位置的值并不是tmp,所以需要将tmp赋值给arr[low]
        arr[low] = tmp;
        return low; // 返回tmp的正确位置
    }


}
