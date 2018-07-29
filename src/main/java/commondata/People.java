package commondata;

import java.util.List;

/**
 * @author bin.yu
 * @create 2017-11-30 11:19
 **/
public class People {

    private String name;
    private int age;
    private List<String> friends;

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public People(String name, int age, List friends) {
        this.name = name;
        this.age = age;
        this.friends = friends;
    }

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

    public List getFriends() {
        return friends;
    }

    public void setFriends(List friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", friends=" + friends +
                '}';
    }


    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        People people = (People) o;
//        return Objects.equals(name, people.name) &&
//                Objects.equals(age, people.age);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, age);
//    }
}
