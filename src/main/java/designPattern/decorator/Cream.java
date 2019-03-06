package designPattern.decorator;

/**
 * @author bin.yu
 * @create 2019-03-06 20:01
 **/
public class Cream extends Food {

    private Food food;

    public Cream(Food food) {
        this.food = food;
    }

    @Override
    public String make() {
        return food.make() + "奶油";
    }
}
