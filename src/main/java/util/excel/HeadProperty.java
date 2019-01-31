package util.excel;

import java.lang.annotation.*;

/**
 * The interface Head property.
 *
 * @Author ming.jin
 * @Date 2017 /8/9
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HeadProperty {

    /**
     * The enum Head type.
     */
    enum HeadType {
        /**
         * String head type.
         */
        STRING, /**
         * Date head type.
         */
        DATE, /**
         * Int head type.
         */
        INT, /**
         * Double head type.
         */
        DOUBLE};

    /**
     * 标题名称.
     *
     * @return the string
     */
    String name();

    /**
     * 值类型.
     *
     * @return the head type
     */
    HeadType type() default HeadType.STRING;

    /**
     * 日期格式.
     *
     * @return the string
     */
    String format() default "yyyy-MM-dd";

    /**
     * 是否可选列.
     *
     * @return the boolean
     */
    boolean option() default false;

    /**
     * 写排序.
     *
     * @return the int
     */
    int writeSort() default 0;
}