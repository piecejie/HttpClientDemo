import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by with IntelliJ IDEA.
 * User: ${user}
 * Date: 2017/6/15
 * Time: 14:19
 * HttpClient模拟post请求
 */
public class SimpleHttpClientDemo {
    public static String send(String url, Map<String, String> map, String encoding) throws IOException {
        String body = "";
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建post请求对象
        HttpPost httpPost = new HttpPost(url);
        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        System.out.println("请求地址:" + url);
        System.out.println("请求参数:" + nvps.toString());
        //设置header信息
        //设置报文头[Content-type] [User-Agent]
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //执行请求操作,并拿到结果(同步阻塞)
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取结果实体
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(httpEntity, encoding);
        }
        EntityUtils.consume(httpEntity);
        //释放连接
        response.close();
        return body;
    }

    /**
     * 测试httpclient post请求
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String url = "https://www.baidu.com/";
        String body = send(url, null, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);
    }
}
