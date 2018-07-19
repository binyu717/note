package commondata;

import org.apache.commons.lang3.StringUtils;

/**
 * @author bin.yu
 * @create 2018-01-02 17:47
 **/
public class Re {

    public static void main(String[] args) {
/*        List f = new ArrayList();
        f.add("A");
        f.add("B");

        List ff = new ArrayList();
        ff.add("A");
        ff.add("Bd");
        People lisi = new People("lisi", 14,f);
        People zhangsan = new People("zhangsan", 15,ff);
        JSONObject jsonObject1 = (JSONObject) JSON.toJSON(lisi);
        JSONObject jsonObject2 = (JSONObject) JSON.toJSON(zhangsan);

        for (Map.Entry<String, Object> p : jsonObject1.entrySet()) {
            String name = p.getKey();
            Object value = p.getValue();
//            System.out.println(name+value);
           if (value != null) {
                if (!value.equals(jsonObject2.get(name))) {
                    System.out.println(name + value);

                }

            }
        }*/

/*        String str =" \"c263d006-a9d2-4fc7-baed-94474c68fa1f\",\"819a5d16-7eca-4e9a-846f-b53acc70d579\" ";
        String str1 ="c263d006-a9d2-4fc7-baed-94474c68fa1f,819a5d16-7eca-4e9a-846f-b53acc70d579 ";
        List list = Arrays.asList(str);
        List list1 = Arrays.asList("asad","asdasdf");
        System.out.println(list1.size());
        System.out.println(list1);*/
/*


        String s = new String("2017/05/02");
        String s1 = new String("1992/05/02");
        Date date = new Date(s);
        Date date1 = new Date(s1);
        System.out.println(date);
        System.out.println(date1);
*/

        String o = null;
        System.out.println(StringUtils.isNotBlank(o));

    }
}
