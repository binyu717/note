package dataStructureAndAlgorithm;

import java.util.Arrays;
import java.util.List;

/**
 * 递归
 *
 * @author bin.yu
 * @create 2018-09-25 18:12
 **/
public class RecursionDemo {

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(-1, 2, 3);
        Boolean flag = recursion(integers);
        System.out.println(flag);

    }

    /**
     * >0 return true
     * @param list
     * @return
     */
    private static Boolean recursion(List<Integer> list) {
        if (list.get(0) < 0) {
            recursion(list.subList(1, list.size()));
        } else {
            return true;
        }
        return false;
    }
}
