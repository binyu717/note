package util;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei on 2017/7/15.
 */
public class Convert {
    static {
        ConvertUtils.register(new DateConverter(), java.util.Date.class);
    }
    /**
     * 1进行深复制，<br>
     * 2必须匹配所有属性，T与R具有相同的属性<br>
     * 参看单元测试
     *
     * @param beanSrc
     * @param
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> R convertAll(T beanSrc, Class<R> descCls) {
        // 属性为null不处理 by xietian 2017/9/4
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                if (value == null) {
                    return true;
                }
                return false;
            }
        });
        JSONObject jsonObject = JSONObject.fromObject(beanSrc, jsonConfig);

        R r = (R) JSONObject.toBean(jsonObject, descCls);
        return r;
    }

    public static <R, T> R convertAllByClassMap(T beanSrc, Class<R> descCls, Map classMap) {
        JSONObject jsonObject = JSONObject.fromObject(beanSrc);
        R r = (R) JSONObject.toBean(jsonObject, descCls, classMap);
        return r;
    }

    /**
     * convertAll的list版本
     *
     * @param beanSrc
     * @param descCls
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> List<R> convertListAll(List<T> beanSrc, Class<R> descCls) {
        List<R> rList = new ArrayList<>();
        for (T t : beanSrc) {
            R r = convertAll(t, descCls);
            rList.add(r);
        }
        return rList;
    }

    /**
     * 转换匹配到的属性,参看单元测试
     *
     * @param beanSrc
     * @param descCls
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> R convertOnlyMatch(T beanSrc, Class<R> descCls) {
        if (beanSrc == null) {
            return null;
        }
        try {
            R destInstance = descCls.newInstance();
            BeanUtils.copyProperties(beanSrc, destInstance);
            return destInstance;
        } catch (Exception e) {
            LoggerFactory.getLogger(Convert.class).error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * convertOnlyMatch的list的版本
     *
     * @param beanSrc
     * @param descCls
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> List<R> convertListOnlyMatch(List<T> beanSrc, Class<R> descCls) {
        List<R> rList = new ArrayList<>();
        beanSrc.forEach(t -> {
            R r = convertOnlyMatch(t, descCls);
            rList.add(r);
        });
        return rList;
    }

}
