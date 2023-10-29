package com.ruoyi.biz.hik.utils;

import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

public class HikClient {

    /**
     * 请根据自己的appKey和appSecret更换static静态块中的三个参数. [1 host]
     * 如果你选择的是和现场环境对接,host要修改为现场环境的ip,https端口默认为443，http端口默认为80.例如10.33.25.22:443 或者10.33.25.22:80
     * appKey和appSecret请按照或得到的appKey和appSecret更改.
     */
    static {
        ArtemisConfig.host = "223.80.104.191:81";// 代理API网关nginx服务器ip端口
        ArtemisConfig.appKey = "21150745";// 秘钥appkey
        ArtemisConfig.appSecret = "csEBIKf6PcpL8ztopaBo";// 秘钥appSecret
    }

    /**
     * 能力开放平台的网站路径
     */
    private static final String ARTEMIS_PATH = "/artemis";


    /**
     * 调用POST请求类型(application/json)接口，这里以入侵报警事件日志为例
     * https://open.hikvision.com/docs/918519baf9904844a2b608e558b21bb6#e6798840
     *
     * @return
     */
    public static String callPostStringApi(String url, String jsonString) {
        /**
         * http://10.33.47.50/artemis/api/scpms/v1/eventLogs/searches
         * 根据API文档可以看出来，这是一个POST请求的Rest接口，而且传入的参数值为一个json
         * ArtemisHttpUtil工具类提供了doPostStringArtemis这个函数，一共六个参数在文档里写明其中的意思，因为接口是https，
         * 所以第一个参数path是一个hashmap类型，请put一个key-value，query为传入的参数，body为传入的json数据
         * 传入的contentType为application/json，accept不指定为null
         * header没有额外参数可不传,指定为null
         *
         */
        String getCamsApi = ARTEMIS_PATH + url;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("http://", getCamsApi);//根据现场环境部署确认是http还是https
            }
        };


        String result = ArtemisHttpUtil.doPostStringArtemis(
                path,
                jsonString,
                null,
                null,
                "application/json",
                null);
        return result;
    }

    public static String callPostStringWithHeadApi(String url, String jsonString) {
        /**
         * http://10.33.47.50/artemis/api/scpms/v1/eventLogs/searches
         * 根据API文档可以看出来，这是一个POST请求的Rest接口，而且传入的参数值为一个json
         * ArtemisHttpUtil工具类提供了doPostStringArtemis这个函数，一共六个参数在文档里写明其中的意思，因为接口是https，
         * 所以第一个参数path是一个hashmap类型，请put一个key-value，query为传入的参数，body为传入的json数据
         * 传入的contentType为application/json，accept不指定为null
         * header没有额外参数可不传,指定为null
         *
         */
        String getCamsApi = ARTEMIS_PATH + url;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("http://", getCamsApi);//根据现场环境部署确认是http还是https
            }
        };
        Map<String, String> head = new HashMap<String, String>(2) { //get 请求的 header 参数
            {
                put("userId", "admin");
                put("tagId", "frs");
            }
        };

        String result = ArtemisHttpUtil.doPostStringArtemis(
                path,
                jsonString,
                null,
                null,
                "application/json",
                head);
        return result;
    }

    public static String callGetStringApi(String url, Map<String,String> querys){
        String getCamsApi = ARTEMIS_PATH + url;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("http://", getCamsApi);//根据现场环境部署确认是http还是https
            }
        };
        Map<String, String> head = new HashMap<String, String>(2) { //get 请求的 header 参数
            {
                put("headget", "sky-test");
            }
        };
        String result = ArtemisHttpUtil.doGetArtemis(path, querys, null, null,head);
        return result;
    }

}
