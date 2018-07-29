package collectionDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author bin.yu
 * @create 2017-12-21 10:36
 **/
public class ListAndString {
    public static void main(String[] args) {

//        String str = new String("一,二,三");
//        stringTransferList(str);

        List list = null;
        System.out.println(list.size());

    }

    /**
     * String List相互转换
     * @param str
     */
    private static void stringTransferList(String str){
/**
 *  Arrays.asList() 返回java.util.Arrays$ArrayList，不是ArrayList,Arrays$ArrayList和ArrayList都是继承AbstractList
 *  若要调用add，remove这些method时，需要进行转换，否则throw UnsupportedOperationException。
 */
        List<String> list = Arrays.asList(str.split(","));
//        list.add("四");会报错
        List<String> arrayList = new ArrayList<>(list);
        arrayList.add("四");
        System.out.println(arrayList);

//        List可直接用toArray()转String[]
        String[] listToStrings = (String[]) list.toArray();
        System.out.println(listToStrings);

/**
  *对于ArrayList要用toArray(T[])转换,T[]的大小<arrayList，则生成一个新的String,否则直接返回T[]
  *array若小于arrayList.size  则array与arrayListToStrings地址一样，
 */


        String[] array =new String[4];
        String[] arrayListToStrings = arrayList.toArray(array);
        System.out.println(array);
        System.out.println(arrayListToStrings);

//        一般用
        String[] s = new String[arrayList.size()];
        arrayList.toArray(s);
        System.out.println(s);
    }
}
