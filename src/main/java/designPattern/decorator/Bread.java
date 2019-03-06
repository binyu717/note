package designPattern.decorator;

/**
 * @author bin.yu
 * @create 2019-03-06 19:59
 **/
public class Bread extends Food {
    private Food food;

    public Bread(Food food) {
        this.food = food;
    }

    @Override
    public String make() {
        return food.make() + "面包";
    }
}
