package com.jzt.util;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述：
 *
 * @Author: sj
 * @Date: 2020/10/31 12:44
 */
public class FastDFSUtil {

    private static final String CONFIG_FILENAME = "fdfs_client.conf";
    private static final String GROUP_NAME = "group1";
    private TrackerClient trackerClient = null;//tracker客户端
    private TrackerServer trackerServer = null;//tracker服务端
    private StorageClient storageClient = null;//storage客户端
    private StorageServer storageServer = null;//storage服务端

    static{
        try {
            ClientGlobal.init(CONFIG_FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public FastDFSUtil() throws Exception {
        trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
        trackerServer = trackerClient.getConnection();
        storageServer = trackerClient.getStoreStorage(trackerServer);;
        storageClient = new StorageClient(trackerServer, storageServer);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param fileName 文件名
     * @return
     */
    public String[] uploadFile(File file, String fileName){
        return uploadFile(file, fileName, null);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @param extName 文件扩展名
     * @param metaList 文件元数据
     * @return
     */
    public String[] uploadFile(File file, String extName, Map<String, String> metaList){
        try{
            byte[] buff = IOUtils.toByteArray(new FileInputStream((file)));
            NameValuePair[] nameValuePairs = null;
            if(metaList != null){
                int index = 0;
                for(Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();){
                    Map.Entry<String,String> entry = iterator.next();
                    String name = entry.getKey();
                    String value = entry.getValue();
                    nameValuePairs[index++] = new NameValuePair(name,value);
                }
            }
            return storageClient.upload_file(GROUP_NAME,buff,extName,nameValuePairs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件元数据
     * @param fileId 文件ID
     * @return
     */
    public Map<String,String> getFileMetadata(String groupName, String fileId) {
        try {
            NameValuePair[] metaList = storageClient.get_metadata(groupName,fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileId 文件ID
     * @return 删除失败返回-1，否则返回0
     */
    public int deleteFile(String groupName,String fileId) {
        try {
            return storageClient.delete_file(groupName, fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 下载文件
     * @param fileId 文件ID（上传文件成功后返回的ID）
     * @param outFile 文件下载保存位置
     * @return
     */
    public int downloadFile(String groupName, String fileId, File outFile) {
        FileOutputStream fos = null;
        try {
            byte[] content = storageClient.download_file(groupName, fileId);
            fos = new FileOutputStream(outFile);
            InputStream ips = new ByteArrayInputStream(content);
            IOUtils.copy(ips, fos);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        FastDFSUtil client = new FastDFSUtil();
        //group1/M00/00/00/wKgfgF-dGKiAD17GAAChnYBaPX4308.jpg
        File file = new File("D:\\23456.png");
        String[] result = client.uploadFile(file, "png");
        System.out.println(result.length);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }
}
