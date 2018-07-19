package cachedemo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.concurrent.TimeUnit;

/**
 * @author bin.yu
 * @create 2018-01-30 17:31
 **/
public class CacheDemo {

    public static void main(String[] args) {

        Cache<String,String> cache = CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(3, TimeUnit.SECONDS).removalListener(new RemovalListener<String,String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        System.out.println("删除的回调");
                    }
                }).build();

        cache.put("1","A");
        cache.put("2","B");
        cache.put("3","C");
//        System.out.println(cache.size());
//        cache.invalidate(1);
//        cache.invalidate(3);
//        System.out.println(cache.size());
//        cache.put("3","D");
        cache.put("4","D");
        System.out.println(cache.getIfPresent("3"));

        cache.asMap().forEach((k,v)->{
            System.out.println("cache"+k+v);
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000L);
                    System.out.println(cache.size());
                    System.out.println(cache.getIfPresent("3"));
                    System.out.println( cache.asMap().values().size());
                    cache.asMap().values().forEach(i->{
                        System.out.println(i);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
