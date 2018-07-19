package designPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一副扑克牌中抽出13张从a到k然后把上面的一张抽出来放最低下,第二张是1拿出来摆桌上,再把最上面的一张抽出来放底下,
 * 第二张是2放桌上,以此类推把他们按照顺序都抽出来.怎么摆?”
 * @author bin.yu
 * @create 2018-07-14 14:18
 **/
public class Example {

    public static void main(String[] args) {
        demo(13);
    }


    private static void demo(int count) {
        List<Integer> ints = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ints.add(i);
        }
        List<Integer> list = new ArrayList<>();
        Boolean flag = Boolean.TRUE;
        for (int i = 0; i < ints.size(); i++) {
            if (list.size() == count) {
                break;
            }
            if (flag) {
                ints.add(ints.get(i));
                flag = Boolean.FALSE;
            } else {
                list.add(ints.get(i));
                flag = Boolean.TRUE;
            }
        }
        System.out.println(list);
        // 假设每个数的索引是想要的1~n的排序，则按值来排序后的索引即所求
        int[] result = new int[count];
        for (int i = 0; i < list.size(); i++) {
            result[list.get(i) - 1] = i + 1;
        }
        System.out.print(Arrays.toString(result));

    }
}
