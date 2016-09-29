package baseframe.tools;

import android.content.Context;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * xml serializable
 */
public class XMLUtil {
    private static final String XML_PROPERTY="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";






    //generate xml
    public static void genxml(Serializable object,String rootName,String path) throws IOException
    {
        XStream xstream=new XStream();
        System.out.println(object.getClass().getName());
        xstream.alias("root", object.getClass());
//		xstream.aliasAttribute(CareerProperty.class, "name", "career");//使用属性映射
        String resultstr=xstream.toXML(object);//-------------------转换
        resultstr=XML_PROPERTY+resultstr;

        System.out.println(resultstr);

        byte[] resultarr=resultstr.getBytes();
        File file=new File("xml01.xml");
        if(!file.exists())
        {
            file.createNewFile();
        }
        FileOutputStream out=new FileOutputStream(file);
        out.write(resultarr);
        out.close();

    }
    //read xml to Object
    public static Object readxml(String path,String rootName,Class<Object> cla)
    {
        Object obj=null;
        File file=new File(path);
        if(file.exists())
        {
            try {
                int len=(int)file.length();
                byte[] resultarr=new byte[len];
                FileInputStream in=new FileInputStream(file);
                in.read(resultarr);
                String resultstr=new String(resultarr);
                resultstr=resultstr.replace(XML_PROPERTY,"");
                System.out.println(resultstr);

                XStream xstream=new XStream(new DomDriver());
                xstream.alias(rootName, cla);
//				xstream.aliasAttribute(CareerProperty.class, "name", "career");//使用属性映射
                obj=xstream.fromXML(resultstr);

                in.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return obj;
    }
    public static Object readxmlFromAssets(Context context,String path,String rootName,Class<Object> cla)
    {
        Object obj=null;
        File file=new File(path);
        if(file.exists())
        {
            try {
                int len=(int)file.length();
                byte[] resultarr=new byte[len];
                InputStream in=context.getResources().getAssets().open(path);

//                FileInputStream in=new FileInputStream(file);
                in.read(resultarr);
                String resultstr=new String(resultarr);
                resultstr=resultstr.replace(XML_PROPERTY,"");
                System.out.println(resultstr);

                XStream xstream=new XStream(new DomDriver());
                xstream.alias(rootName, cla);
//				xstream.aliasAttribute(CareerProperty.class, "name", "career");//使用属性映射
                obj=xstream.fromXML(resultstr);

                in.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return obj;
    }


}
