package com.example.administrator.testjson;

/**
 * Created by Administrator on 2017/4/23.
// */
//
//public class HttpUtil {
//    public static String httpGet(String url){
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(url);
//
//        try{
//            HttpResponse response = httpClient.execute(httpGet);
//
//            HttpEntity entity = response.getEntity();
//            if(entity!=null){
//                InputStream in = entity.getContent();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] buffer = new byte[500];
//                int len = 0;
//                while((len = in.read(buffer))>0){
//                    baos.write(buffer, 0, len);
//                }
//                String msg = new String(baos.toByteArray());
//                return msg;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
