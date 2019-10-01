package com.smartpay.apmp2.pay;

import com.google.gson.Gson;
import com.smartpay.apmp2.request.*;
import com.smartpay.apmp2.response.*;
import com.smartpay.apmp2.util.ApmpSecurityUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ApmpClient {

    private static final String HEADER_SIGNATURE = "X-apsignature".toLowerCase();

    private static final String HEADER_APP_KEY = "X-APPKey".toLowerCase();

    public Map<String, String> resHeaderMap = new HashMap<String, String>();

    private String serverUrl;

    private String appKey;

    private String privateKey;//B私

    private String publicKey;//A公

    private String publicKeyStr;

    /**
     * 进件
     *
     * @param request
     * @return
     */
    public MerEnterResponse merEnter(MerEnterRequest request) {
        return (MerEnterResponse) send(request, MerEnterResponse.class);
    }

    /**
     * 进件查询接口
     *
     * @param request
     * @return
     */
    public EnterQueryResponse enterQuery(EnterQueryRequest request) {
        return (EnterQueryResponse) send(request, EnterQueryResponse.class);
    }

    /**
     * 对账单下载
     */
    public BillDownLoadResponse billDownload(BillDownLoadRequest request){
        return (BillDownLoadResponse) send(request,BillDownLoadResponse.class);
    }

    public String getPublicKeyStr() {
        return publicKeyStr;
    }

    public void setPublicKeyStr(String publicKeyStr) {
        this.publicKeyStr = publicKeyStr;
    }

    /**
     * json转对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    /**
     * Rest Client.
     */
    private BaseResponse send(BaseRequest request, Class className) {
        String params = "[" + new Gson().toJson(request) + "]";
        System.out.println("-- req: --");
        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add("params", params);
        System.out.println("params=" + params);

        WebResource client = com.sun.jersey.api.client.Client.create().resource(getServerUrl());

        try {
            String sign = null;
            Builder builder;
                sign = ApmpSecurityUtil.sign(params.getBytes("UTF-8"), this.privateKey);
                builder = client.type(MediaType.APPLICATION_FORM_URLENCODED)
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .acceptLanguage(Locale.SIMPLIFIED_CHINESE)
                        .header(HEADER_APP_KEY, this.getAppKey()) // APPKEY
                        .header(HEADER_SIGNATURE, sign);// 报文签名
            ClientResponse res = builder.post(ClientResponse.class, queryParams);
            MultivaluedMap<String, String> header = res.getHeaders();
            System.out.println("-- res: --");
            System.out.println("header:\t " + header);

            if (res.getType().isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
                String jsonRes = res.getEntity(String.class);
                System.out.println("body:\t" + jsonRes);

                // 验签
                sign = header.getFirst(HEADER_SIGNATURE);
                // 解析应答报文
                JSONArray arr = JSONArray.fromObject(jsonRes);
                JSONObject obj = (JSONObject) arr.get(0);
                String resCode = obj.getString("responseCode");
                if (resCode.equals("00")||resCode.equals("99")) {
                    if (ApmpSecurityUtil.verify(jsonRes.getBytes(), sign, publicKey)) {
                        System.out.println("验签成功。 ");
                    } else {
                        System.out.println("验签失败。 ");
                        return null;
                    }
                    System.out.println("正常通讯收到应答. ");
                }else {
                    System.out.println("通讯异常. ");
                }

                String responseString = jsonRes.substring(1, jsonRes.length() - 1);
                BaseResponse response = (BaseResponse) ApmpClient.fromJson(responseString, className);
                return response;

            } else {
                System.out.println("未定义的应答报文格式. ");
                System.out.println(res.getEntity(String.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

}
