package baseframe.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileOperate {
	public static String readFile(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
		BufferedReader br = new BufferedReader(read);
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            sb.append(temp);
            sb.append("\n");
            temp=br.readLine();
        }
        br.close();
        return sb.toString();
    }
	public static void writeFile(String path,String content) throws IOException{
        File file=new File(path);
        if(!file.exists())
            file.createNewFile();
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
		BufferedWriter writer=new BufferedWriter(write);
        writer.write(content);
        writer.close();
    }
       public static void main(String[] args) throws IOException{
//    	  String a= FileOperate.readFile("D:\\apache-tomcat-6.0.32\\webapps\\ROOT\\client\\tw\\IOS.xml");
//    	  System.out.println(a);
    	//  FileOperate.writeFile("D:\\apache-tomcat-6.0.32\\webapps\\ROOT\\client\\tw\\IOS.xml", a);
       }
}
