package com.xxm.file.util;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.net.InetSocketAddress;

/**
 * 描述
 *
 * @author Mr Liu
 * @version 1.0
 * @package com.xxm.file.util *
 * @Date 2019-11-23
 * @since 1.0
 */
public class FastdfsClient {

    static {
        try {
            //从classpath下动态获取路径
            ClassPathResource classPathResource = new ClassPathResource("fdfs_client.conf");
            ClientGlobal.init(classPathResource.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //图片上传
    public static String[] upload(FastDFSFile file) throws Exception {

        //3.创建trackerClient对象 连接tracker服务器
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建StorageServer
        StorageServer storageServer = null;
        //6.创建storageClient  提供了操作图片的API 图片上传
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //7.上传图片
        //参数1 指定图片的路径本地文件路径
        //参数2 指定图片的扩展名(不要带点)
        //参数3 指定元数据: 图片的高度 图片的拍摄日期 图片的像素 作者......

        String[] jpgs = storageClient.upload_file(file.getContent(), file.getExt(), new NameValuePair[]{new NameValuePair(file.getName()), new NameValuePair(file.getAuthor())});
        //[0] group1
        //[1] M00/00/00/12313219.jpg

        return jpgs;
    }

    //图片下载

    public static byte[] downFile(String groupName, String remoteFileName) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建StorageServer
        StorageServer storageServer = null;
        //6.创建storageClient  提供了操作图片的API 图片上传
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //下载图片
        //第一个参数指定组名
        //第二个参数指定文件名(虚拟磁盘目录+文件名.....)
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return bytes;
    }

    //图片的删除

    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        //3.创建trackerClient对象 连接tracker服务器
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建StorageServer
        StorageServer storageServer = null;
        //6.创建storageClient  提供了操作图片的API 图片上传
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        int group1 = storageClient.delete_file(groupName, remoteFileName);
        if (group1 == 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    //获取文件的信息

    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建StorageServer
        StorageServer storageServer = null;
        //6.创建storageClient  提供了操作图片的API 图片上传
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        return storageClient.get_file_info(groupName, remoteFileName);
    }

    //获取组信息
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();

        ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);

        return group1s;
    }

    //获取tracker的信息


    // http://192.168.***.***:8080
    public static String getTrackerUrl() throws Exception {
        //创建TrackerClient客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient对象获取TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();

        InetSocketAddress inetSocketAddress = trackerServer.getInetSocketAddress();
        // http://192.168.211.132:8080
        return "http://" + inetSocketAddress.getHostString() + ":" + ClientGlobal.getG_tracker_http_port();// 8080

    }


    //....
}
