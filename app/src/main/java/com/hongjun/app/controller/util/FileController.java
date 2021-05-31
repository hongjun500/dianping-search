package com.hongjun.app.controller.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/4/16 11:44
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@RequestMapping(value = "/file")
@RestController
@Slf4j
@Api(tags = "文件上传")
public class FileController extends BaseController {

    @Value(value = "${aliyun.oss.endpoint}")
    private String endpoint;
    @Value(value = "${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value(value = "${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value(value = "${aliyun.oss.bucketName}")
    private String bucketName;
    @Value(value = "${aliyun.oss.folderName}")
    private String folderName;
    @Value(value = "${aliyun.oss.header}")
    private String header;
    @Value(value = "${app-server.upload}")
    private String upload;




    /**
     * 执行文件上传
     */
    @ApiOperation("文件上传")
    @PostMapping(value = "/upload")
    public CommonReturnType<Object> handleFormUpload(@RequestParam(value = "file") MultipartFile[]  uploadfile) throws BusinessException {
        // 判断所上传文件是否存在
        if (uploadfile.length != 0) {
            List<Object> list = new ArrayList<>();
            //循环输出上传的文件
            for (MultipartFile file : uploadfile) {
                // 获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                // 设置上传文件的保存地址目录
                log.info("上传文件的服务器路径{}", upload);
                String dirPath = upload + "/" + DateUtil.format(new Date(), "yyyy/MM");
                File filePath = new File(dirPath);
                // 如果保存文件的地址不存在，就先创建目录
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                // 使用UUID重新命名上传的文件名称(uuid_原始文件名称)
                String newFilename = IdUtil.simpleUUID() + "_" + originalFilename;

                System.out.print(filePath);
                try {
                    // 使用MultipartFile接口的方法完成文件上传到指定位置
                    file.transferTo(new File(dirPath + "/" + newFilename));
                    // 传到oss
                    // oss文件地址路径 folderName + 年月文件夹 + newFilename
                    String imgUrl = folderName + "/" + DateUtil.format(new Date(), "yyyy/MM") + "/" + newFilename;
                    String uploadOss = uploadOss(dirPath + "/" + newFilename,  imgUrl);
                    list.add(uploadOss);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
                }
            }
            // 返回地址
            return CommonReturnType.create(list);
        } else {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "文件不能为空");
        }
    }

    public static void main(String[] args) {
        String format = DateUtil.format(new Date(), "yyyy/MM");
        System.out.println(format);
    }

    /**
     * 根据浏览器的不同进行编码设置，返回编码后的文件名
     */
    public String getFilename(HttpServletRequest request,
                              String filename) throws Exception {
        // IE不同版本User-Agent中出现的关键词
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : IEBrowserKeyWords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示
                return URLEncoder.encode(filename, "UTF-8");
            }
        }
        //火狐等其它浏览器统一为ISO-8859-1编码显示
        return new String(filename.getBytes("UTF-8"), "ISO-8859-1");
    }


    private String  uploadOss(String pathName, String imgUrl) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 创建PutObjectRequest对象。
            // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, imgUrl, new File(pathName));

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            log.error("阿里云oss上传失败{}",e.getMessage());
        }
        return header + "/" +  imgUrl;
    }


    // app端传图片文件拓展方法
    public Object uploadFile(MultipartFile[] files) throws BusinessException {
        CommonReturnType<Object> objectCommonReturnType = handleFormUpload(files);
        return objectCommonReturnType.getData();
    }

}
