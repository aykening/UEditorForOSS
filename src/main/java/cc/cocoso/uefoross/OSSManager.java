package cc.cocoso.uefoross;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by ayke on 16-10-17.
 *
 */
public class OSSManager {

    private Map<String,String> configMap;
    private OSSConfigManager configManager;
    private String bucketName;


    public OSSManager(){
        if (configManager==null){
            configManager=OSSConfigManager.getInstance();
        }
        configMap=configManager.getConfig();
        bucketName=configMap.get("bucketName");

    }

    private OSSClient getClient(){

        ClientConfiguration configuration=new ClientConfiguration();
        configuration.setSupportCname(true);
        OSSClient client=new OSSClient(configMap.get("ossEndPoint"),configMap.get("secretId"),configMap.get("secretKey"));
        return client;
    }


    /**
     * 以字节数组的方式上传文件到OSS
     * @param data 文件的字节数组
     * @param path 上传文件的路径
     * @return
     */
    public State uploadFile(byte[] data,String path){
        OSSClient client=getClient();
        if (bucketName!=null&&bucketName!=""){
            return checkUploadResult( client.putObject(bucketName, path, new ByteArrayInputStream(data)));
        }
        client.shutdown();
        return null;

    }

    /**
     * 以文件的方式上传到OSS
     * @param file 本地文件
     * @param path 上传文件的路径
     * @return
     */
    public State uploadFile(File file,String path){
        OSSClient client=getClient();
        if (bucketName!=null&&bucketName!=""){
            return checkUploadResult(client.putObject(bucketName,path,file));
        }
        client.shutdown();
        return null;
    }

    /**
     * 以流的方式上传文件到OSS
     * @param is 文件流
     * @param path 上传文件的路径
     * @return
     */
    public State uploadFile(InputStream is, String path){
        OSSClient client=getClient();
        if (bucketName!=null&&bucketName!=""){
            return checkUploadResult(client.putObject(bucketName,path,is));
        }
        client.shutdown();
        return null;
    }


    private State checkUploadResult(PutObjectResult result){
        return new BaseState(true);
    }

}
