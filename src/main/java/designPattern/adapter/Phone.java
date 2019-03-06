package designPattern.adapter;

/**
 * @author bin.yu
 * @create 2019-03-06 20:14
 **/
public class Phone {

    private Transformer transformer;

    public Phone() {
    }

    public Phone(Transformer transformer) {
        this.transformer = transformer;
    }

    public void charge(){
        transformer.toSingle();
    }
}
