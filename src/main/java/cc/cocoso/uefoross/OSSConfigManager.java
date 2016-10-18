package cc.cocoso.uefoross;

import org.apache.commons.collections.map.HashedMap;
import org.json.JSONObject;

import java.io.*;
import java.util.Map;

/**
 * Created by ayke on 16-10-17.
 * 获取OSS参数
 */
public class OSSConfigManager {
    private static JSONObject jsonConfig;
    private static OSSConfigManager manager=null;
    private static Map<String,String> configMap=null;


    private OSSConfigManager() {

    }

    public static OSSConfigManager getInstance(){
        if (manager==null){
            manager=new OSSConfigManager();
        }
        return manager;
    }


    private  String getConfigContent(){
        StringBuilder builder=new StringBuilder();
        try{
            InputStream is=OSSConfigManager.class.getResourceAsStream("/OSSConfig.json");
            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String tempContent;
            while ((tempContent=reader.readLine())!=null){
                builder.append(tempContent);
            }
            reader.close();
            is.close();
        }catch (IOException e){
            return null;
        }
        return builder.toString();
    }

    public Map<String,String> getConfig(){
        if (configMap==null){
            configMap=new HashedMap();
            jsonConfig=new JSONObject(getConfigContent());
            configMap.put("bucketName",jsonConfig.getString("bucketName"));
            configMap.put("ossEndPoint",jsonConfig.getString("ossEndPoint"));
            configMap.put("secretId",jsonConfig.getString("secretId"));
            configMap.put("secretKey",jsonConfig.getString("secretKey"));
        }
        return configMap;
    }


}
