package designPattern.decorator;

/**
 * @author bin.yu
 * @create 2019-03-06 19:58
 **/
public class Food {

    private String foodName;

    public Food() {
    }

    public Food(String foodName) {
        this.foodName = foodName;
    }

    public String make(){
        return foodName;
    }
}
