package jsonDemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author bin.yu
 * @create 2017-12-21 9:00
 **/
public class JsonTransform {

    public static void main(String[] args) {

        JSONObject jsonObject1 = new JSONObject(10,true);//true 类似LinkedHashMap--有序
        jsonObject1.put("receiveTimeSort", "desc");
        jsonObject1.put("expectReturnTimeSort", "asc");
        jsonObject1.put("purchaseTimeSort", "desc");
        jsonObject1.put("returnTimeSort", "desc");

        String strArray = "[{'name':'receiveTimeSort','desc':'desc'}," +
                "{'name':'expectReturnTimeSort','desc':'asc'}," +
                "{'name':'purchaseTimeSort','desc':'desc'}]";

        JSONArray jsonArray = JSONArray.parseArray(strArray);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object name = jsonObject.get("name");
            Object desc = jsonObject.get("desc");
            sb.append(",").append(name).append(" ").append(desc);
        }
        String substring = sb.substring(1);
        System.out.println(substring);
        String sql = substring.replace("receiveTimeSort", "receive_time").replace("expectReturnTimeSort", "expectReturn_time").replace("purchaseTimeSort", "purchase_time");
        System.out.println(sql);


        String strObject = "{'name':'receiveTimeSort','desc':'desc'}";
        JSONObject jsonObject = JSONObject.parseObject(strObject);

        System.out.println(jsonObject.get("name"));
    }
}
