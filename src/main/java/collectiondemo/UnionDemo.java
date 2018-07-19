package collectiondemo;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 集合的并、交
 *
 * @author bin.yu
 * @create 2017-12-21 15:40
 **/
public class UnionDemo {

    public static void main(String[] args) {

        //权限
        List<String> read = new ArrayList<>();
        read.add("r");
        List<String> write = new ArrayList<>();
        write.add("w");
        List<String> execute = new ArrayList<>();
        execute.add("x");
        List<String> readAndWrite = new ArrayList<>();
        readAndWrite.add("r");
        readAndWrite.add("w");

        List<String> writeExecute = new ArrayList<>();
        writeExecute.add("w");
        writeExecute.add("x");
        //功能
        Featrue employeeModel1 = new Featrue("员工模块", read);
        Featrue employeeModel2 = new Featrue("员工模块", write);
        Featrue recruitModel1 = new Featrue("招聘模块", readAndWrite);
        Featrue recruitModel2 = new Featrue("招聘模块", execute);
        Featrue attenModel1 = new Featrue("考勤模块", writeExecute);

        //功能列表

        List<Featrue> featrues1 = new ArrayList<>();
        featrues1.add(employeeModel1);
        featrues1.add(attenModel1);
        featrues1.add(recruitModel2);

        List<Featrue> featrues2 = new ArrayList<>();
        featrues2.add(employeeModel2);
        featrues2.add(recruitModel1);

        AccountInfo accountA = new AccountInfo("1", "nameA", "111", featrues1);
        AccountInfo accountB = new AccountInfo("2", "nameB", "222", featrues2);

        /**
         * 并集
         */

        List<Featrue> resultFeatrue = new ArrayList<>();

        HashSet<String> resultSet = new HashSet<>();
        accountA.getFeatrues().stream().forEach(i->{
            resultSet.add(i.getName());
        });
        accountB.getFeatrues().stream().forEach(j->{
            resultSet.add(j.getName());
        });
        List<String> resultList = new ArrayList<>(resultSet);

        resultList.stream().forEach(i->{
            Featrue featrue = new Featrue();
            featrue.setName(i);
            List<String> list = new ArrayList<>();
            HashSet<String> set = new HashSet<>();
            accountA.getFeatrues().stream().forEach(j->{
                if (j.getName().equals(i)) {
                    set.addAll(j.getPrivileges());
                }
            });
            accountB.getFeatrues().stream().forEach(j->{
                if (j.getName().equals(i)) {
                    set.addAll(j.getPrivileges());
                }
            });
            list.addAll(set);
            featrue.setPrivileges(list);
            resultFeatrue.add(featrue);
        });

        accountA.setFeatrues(resultFeatrue);

        List<Featrue> featrue = accountA.getFeatrues();
        for (Featrue f :featrue){
            System.out.println(f.getName());
            System.out.println(f.getPrivileges());
        }


    }
}
