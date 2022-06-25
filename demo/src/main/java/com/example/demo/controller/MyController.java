package com.example.demo.controller;

import com.example.demo.logic.controller.MyLogic;
import com.example.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MyController {
//    @Autowired
//    private MyService myService;
//    private MyLogic myLogic = new MyLogic() ;
    @RequestMapping("/getmusic")
    @ResponseBody
    public String getmusic(String musicStr,String limit,String offset) throws IOException {
        md5(musicStr);
        musicStr=java.net.URLEncoder.encode(musicStr,"UTF-8");
        System.out.println(musicStr);
//        Map<String, String> map=new LinkedHashMap<String, String>();
//        map.put("key", "起风了");
//        map.put("httpsStatus", "1");
//        map.put("reqId", "d8edc810-944c-11ec-9aa5-b154250e2782");
//        System.out.println(sendGet("http://www.kuwo.cn/api/www/search/searchKey",map,headerMap));
        int pn=Integer.parseInt(offset)/Integer.parseInt(limit);
        Map<String, String> searamap=new LinkedHashMap<String, String>();
        searamap.put("key", musicStr);
        searamap.put("pn", Integer.toString(pn+1));
        searamap.put("rn", limit);
        searamap.put("httpsStatus", "1");
        searamap.put("reqId", "12ae9dc0-9454-11ec-9aa5-b154250e2782");
        String res=(sendGet("http://www.kuwo.cn/api/www/search/searchMusicBykeyWord",searamap,getKuwoHeader()));
        return res;
    }



    @RequestMapping("/getmusicinfo")
    @ResponseBody
    static String getPlayMusicInfo(String musicRid) throws IOException {
        Map<String, String> searamap=new LinkedHashMap<String, String>();
        searamap.put("mid", musicRid);
        searamap.put("type", "music");
        searamap.put("httpsStatus", "1");
        searamap.put("reqId", "43281f10-a5d6-11ec-939e-d7a772d0ad77");
        String res=(sendGet("http://www.kuwo.cn/api/v1/www/music/playUrl",searamap,getKuwoHeader()));
        return  res;
    }
    @RequestMapping("/getsingerInfo")//获取歌手信息
    @ResponseBody
    static String getSingerInfo(String singerid) throws IOException {
        //http://www.kuwo.cn/api/www/artist/artist?artistid=336&httpsStatus=1&reqId=3a5af280-bd8d-11ec-8737-73cdd2b5076d
        Map<String, String> searamap=new LinkedHashMap<String, String>();
        searamap.put("artistid", singerid);
        searamap.put("httpsStatus", "1");
        searamap.put("reqId", "43281f10-a5d6-11ec-939e-d7a772d0ad77");
        String res=(sendGet("http://www.kuwo.cn/api/www/artist/artist",searamap,getKuwoHeader()));
        return  res;
    }
    @RequestMapping("/getsingerSongs")//获取歌手信息
    @ResponseBody
    static String getSingerSongs(String singerid,String limit,String offset) throws IOException {
        //http://www.kuwo.cn/api/www/artist/artist?artistid=336&httpsStatus=1&reqId=3a5af280-bd8d-11ec-8737-73cdd2b5076d
        Map<String, String> searamap=new LinkedHashMap<String, String>();
        int pn=Integer.parseInt(offset)/Integer.parseInt(limit);
        searamap.put("artistid", singerid);
        searamap.put("pn", Integer.toString(pn+1));
        searamap.put("rn", limit);
        searamap.put("httpsStatus", "1");
        searamap.put("reqId", "43281f10-a5d6-11ec-939e-d7a772d0ad77");
        String res=(sendGet("http://www.kuwo.cn/api/www/artist/artistMusic",searamap,getKuwoHeader()));
        return  res;
    }
    static Map<String, String> getKuwoHeader() throws IOException {
        URL obj=new URL("http://www.kuwo.cn");
        URLConnection conn=obj.openConnection();
        Map<String, List<String>> map1=conn.getHeaderFields();
        List<String> cookie=map1.get("Set-Cookie");
        String cookieString =cookie.toString();
        cookieString=cookie.toString().substring((cookie.toString().indexOf('=')+1),(cookie.toString().indexOf(';')));
        Map<String, String> headerMap=new HashMap<String, String>();
        headerMap.put("Accept", "application/json, text/plain, */*");
        //headerMap.put("Accept-Encoding", "gzip, deflate");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "application/json, text/plain, */*");
        headerMap.put("Cookie", "; ;; ;kw_token="+cookieString);
        headerMap.put("Host", "www.kuwo.cn");
        headerMap.put("Referer", "http://www.kuwo.cn/");
        headerMap.put("Host", "www.kuwo.cn");
        headerMap.put("csrf", cookieString);
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36");
        return  headerMap;
    }

    /**
     *
     * 发送GET请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    static String sendGet(String url, Map<String, String> parameters, Map<String, String> headers) {
        String result = "";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
                            .append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            // 创建URL对象
            System.out.println(full_url);
            java.net.URL connURL = new java.net.URL(full_url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
            // 设置通用属性
//			httpConn.setRequestProperty("Accept", "*/*");
//			httpConn.setRequestProperty("Connection", "Keep-Alive");
            for (String name : headers.keySet()) {
                httpConn.setRequestProperty(name, headers.get(name));
            }
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            //Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String md5(String data) {
        System.out.println(data);
        try {
            byte[] md5 = md5(data.getBytes("utf-8"));
            return toHexString(md5);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 将加密后的字节数组，转换成16进制的字符串
     * @param md5
     * @return
     */
    private static String toHexString(byte[] md5) {
        StringBuilder sb = new StringBuilder();
        for (byte b : md5) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        return sb.toString();
    }
    /**
     * 将字节数组进行 MD5 加密
     * @param data
     * @return
     */
    public static byte[] md5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

}
