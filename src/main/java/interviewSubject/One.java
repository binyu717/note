package interviewSubject;

/**
 * @author bin.yu
 * @create 2018-08-06 10:41
 **/
public class One {

    /**
     * 面试题：输入一个整型数组，数组中有正数也有负数。数组中一个或多个整数形成一个子数组，求所有子数组的和的最大值，要求时间复杂度为 O(n)。
     * 比如输入 {1, -2, 3, 10, -4, 7, 2, -5}，能产生子数组最大和的子数组为 {3,10,-4,7,2}，最大和为 18。
     * @param nums
     * @return
     */

    private static int findTheNumOfSubArray(int[] nums) {
        if (nums == null || nums.length == 0)
            throw new RuntimeException("the length of input must be large than 0!");
        // 用 result 存放返回结果，即最大和
        int result = Integer.MIN_VALUE;
        // 用 sum 存放当前累加结果
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // 如果小于 0，则直接说明不可能是从前面开始的。不加之前的值,直接算当前值
            if (sum < 0) {
                sum = nums[i];
            } else {
                // 如果大于 0，则相加
                sum += nums[i];
            }
            // 如果添加后的值大于之前存放的最大值，则更新最大值
            if (sum > result)
                result = sum;
        }
        return result;
    }


    public static void main(String[] args) {
        int[] nums1 = {1, -2, 3, 10, -4, 7, 2, -5};
        System.out.println(findTheNumOfSubArray(nums1));
        int[] nums2 = {1,2,3,4,5};
        System.out.println(findTheNumOfSubArray(nums2));
        int[] nums3 = {-5,-2,-3,-4 -5};
        System.out.println(findTheNumOfSubArray(nums3));
    }
}
