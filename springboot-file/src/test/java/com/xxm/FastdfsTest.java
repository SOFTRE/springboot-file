package com.xxm;

import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;

/**
 * 描述
 *
 * @author Mr Liu
 * @version 1.0
 * @package com.xxm *
 * @Date 2019-11-23
 * @since 1.0
 */

public class FastdfsTest {

    //图片上传
    @Test
    public void upload() throws Exception {
        //1.创建一个配置tracker的服务器的地址的配置文件
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\ThinkPad\\IdeaProjects\\xxm\\xxm-parent\\xxm-service\\xxm-service-file\\src\\main\\resources\\fdfs_client.conf");
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
        String[] jpgs = storageClient.upload_file("C:\\Users\\Administrator\\Pictures\\timg.jpg", "jpg", null);

        for (String jpg : jpgs) {
            System.out.println(jpg);
        }
    }

    //图片下载

    @Test
    public void downloadpic() throws Exception {
        //1.创建一个配置tracker的服务器的地址的配置文件
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\ThinkPad\\IdeaProjects\\xxm\\xxm-parent\\xxm-service\\xxm-service-file\\src\\main\\resources\\fdfs_client.conf");
        //3.创建trackerClient对象 连接tracker服务器
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
        byte[] bytes = storageClient.download_file("group1", "M00/00/00/wKjThF3Yn4qAEMgVAANdC6JX9KA170.jpg");

        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\1234.jpg"));

        fileOutputStream.write(bytes);

        fileOutputStream.close();
    }


    //图片删除

    @Test
    public void deletefile() throws Exception {
        //1.创建一个配置tracker的服务器的地址的配置文件
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\ThinkPad\\IdeaProjects\\xxm\\xxm-parent\\xxm-service\\xxm-service-file\\src\\main\\resources\\fdfs_client.conf");
        //3.创建trackerClient对象 连接tracker服务器
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建StorageServer
        StorageServer storageServer = null;
        //6.创建storageClient  提供了操作图片的API 图片上传
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        int group1 = storageClient.delete_file("group1", "M00/00/00/wKjThF3Yn4qAEMgVAANdC6JX9KA170.jpg");
        if (group1 == 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void getfileinfo() throws Exception {
        //1.创建一个配置tracker的服务器的地址的配置文件
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\ThinkPad\\IdeaProjects\\xxm\\xxm-parent\\xxm-service\\xxm-service-file\\src\\main\\resources\\fdfs_client.conf");
        //3.创建trackerClient对象 连接tracker服务器
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建StorageServer
        StorageServer storageServer = null;
        //6.创建storageClient  提供了操作图片的API 图片上传
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        FileInfo fileInfo = storageClient.get_file_info("group1", "M00/00/00/wKjThF3YosWAK6mmAAClQrJOYvs327.jpg");
        System.out.println(fileInfo.getCreateTimestamp() + ":" + fileInfo.getFileSize() + ":" + fileInfo.getSourceIpAddr());
    }

    @Test
    public void getgroupinfo() throws Exception {
        //1.创建一个配置tracker的服务器的地址的配置文件
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\ThinkPad\\IdeaProjects\\xxm\\xxm-parent\\xxm-service\\xxm-service-file\\src\\main\\resources\\fdfs_client.conf");
        //3.创建trackerClient对象 连接tracker服务器
        TrackerClient trackerClient = new TrackerClient();
        //4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();

        ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, "group1", "M00/00/00/wKjThF3YosWAK6mmAAClQrJOYvs327.jpg");

        for (ServerInfo group1 : group1s) {
            System.out.println(group1.getIpAddr() + ":" + group1.getPort());
        }

    }


    //获取tracker的ip和端口
    @Test
    public void getTrackerInfo() throws Exception {
        //加载全局的配置文件
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\beike\\xxm\\xxm-service\\xxm-service-file\\src\\main\\resources\\fdfs_client.conf");

        //创建TrackerClient客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient对象获取TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();

        InetSocketAddress inetSocketAddress = trackerServer.getInetSocketAddress();
        System.out.println(inetSocketAddress);

    }


}
