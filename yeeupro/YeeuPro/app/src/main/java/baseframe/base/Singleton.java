package baseframe.base;

import java.util.HashMap;

/**
 * Created by asus on 2016/9/15.
 */
public class Singleton{

    private static HashMap<Class<? extends Singleton>, Singleton> instances = new HashMap<Class<? extends Singleton>, Singleton>();

    public synchronized static <T extends Singleton> T getInstance(Class<? extends Singleton> instanceClass)
    {
        if(instances.containsKey(instanceClass))
        {
            return (T)instances.get(instanceClass);

        }
        T in= null;
        try {
            in = (T)(instanceClass.newInstance());
            instances.put(instanceClass,(Singleton)in);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return in;

    }
}
