package baseframe.net;


import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * 网络请求管理器
 */
public class NetManager {

    public static void Post(String url,AjaxParams params,AjaxCallBack<Object> callBack)
    {
        Post(url,params,callBack,false);
    }
    public static void Post(String url,AjaxParams params,AjaxCallBack<Object> callBack,boolean isUploadFile)
    {

        FinalHttp finalHttp = new FinalHttp(); //获得httpFinal对象

        finalHttp.addHeader("Accept-Charset", "UTF-8");// 配置http请求头
        if(isUploadFile)
        {
            finalHttp.addHeader("Content-Type","multipart/form-data");
        }
        finalHttp.configCharset("UTF-8");
        finalHttp.configCookieStore(null);
        finalHttp.configRequestExecutionRetryCount(3);// 请求错误重试次数
//        finalHttp.configSSLSocketFactory(null);
        finalHttp.configTimeout(50000);// 超时时间
        finalHttp.configUserAgent("Mozilla/5.0");// 配置客户端信息

        finalHttp.post(url,params,callBack);

    }


}
