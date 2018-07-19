package jsonDemo.fastJsonDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bin.yu
 * @create 2017-12-28 9:09
 **/

    class User {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User [name=" + name + ", age=" + age + "]";
        }
    };

    class UserGroup {
        private String name;
        private List<User> users = new ArrayList<User>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        @Override
        public String toString() {
            return "UserGroup [name=" + name + ", users=" + users + "]";
        }
    }

    class FastJsonTest {
        public static void main(String[] args) {
            // 构建用户geust
            User guestUser = new User();
            guestUser.setName("guest");
            guestUser.setAge(28);
            // 构建用户root
            User rootUser = new User();
            rootUser.setName("root");
            rootUser.setAge(35);
            JSONObject x = (JSONObject)JSONObject.toJSON(rootUser);

            // 构建用户组对象
            UserGroup group = new UserGroup();
            group.setName("admin");
            group.getUsers().add(guestUser);
            group.getUsers().add(rootUser);
            // 用户组对象转JSON串
            String jsonString = JSON.toJSONString(group);
            System.out.println("jsonString:" + jsonString);
            System.out.println("=========="+ JSONArray.toJSONString(group));
            System.out.println("=========="+ JSONArray.toJSON(group));
            // JSON串转用户组对象
            UserGroup group2 = JSON.parseObject(jsonString, UserGroup.class);
            System.out.println("group2:" + group2);

            // 构建用户对象数组
            User[] users = new User[2];
            users[0] = guestUser;
            users[1] = rootUser;
            // 用户对象数组转JSON串
            String jsonString2 = JSON.toJSONString(users);
            System.out.println("jsonString2:" + jsonString2);
            // JSON串转用户对象列表
            List<User> users2 = JSON.parseArray(jsonString2, User.class);
            System.out.println("users2:" + users2);

            System.out.println("=====================================================================");
            List<User> userList = new ArrayList<>();
            userList.add(guestUser);
            userList.add(rootUser);
            System.out.println(JSON.toJSON(userList));
            System.out.println(JSON.toJSONString(userList));
            System.out.println(JSONArray.toJSON(userList));
            System.out.println(JSONArray.toJSONString(userList));
        }
    }
