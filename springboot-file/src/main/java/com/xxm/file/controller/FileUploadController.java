package com.xxm.file.controller;

import com.xxm.file.util.FastDFSFile;
import com.xxm.file.util.FastdfsClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述
 *
 * @author Mr Liu
 * @version 1.0
 * @package com.xxm.file.controller *
 * @Date 2019-11-23
 * @since 1.0
 */
@RestController

public class FileUploadController {

    /**
     * 该方法 用于接收前端发送过来的请求 将文件上传到fastdfs
     *
     * @return 图片的访问路径
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        //1.判断是否为空
        try {
            if (!file.isEmpty()) {
                //2.不为空 处理图片上传
                // file.getOriginalFilename()  --->1234.jpg
//                String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                FastDFSFile fastdfsfile = new FastDFSFile(
                        file.getOriginalFilename(),
                        file.getBytes(),
                        StringUtils.getFilenameExtension(file.getOriginalFilename()));
                String[] upload = FastdfsClient.upload(fastdfsfile);
                //返回一个图片的访问路径
                //http://192.168.211.132:8080/group1/M00/00/00/wKjThF3YosWAK6mmAAClQrJOYvs327.jpg
                //[0] group1
                //[1] M00/00/00/wKjThF3YsEOAdYfhAAClQrJOYvs661.jpg
                return FastdfsClient.getTrackerUrl() + "/" + upload[0] + "/" + upload[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //3.为空 返回null
        return null;
    }
}
