package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.vo.dashboard.fileupload.FileInfo;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-12 下午5:14
 * Summary:
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Value("{templates.dir}")
    private String templateDir;
    @Value("${products.dir}")
    private String productsDir;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 上传对应版本的文件
     *
     * @param request
     * @param product
     * @param version
     * @param builder
     * @return
     */
    @RequestMapping(value = "/file/{product}/{version}/{type}", method = RequestMethod.POST)
    public Map<String, List<FileInfo>> uploadProgram(
            MultipartHttpServletRequest request,
            @PathVariable(value = "product") String product,
            @PathVariable(value = "version") String version, UriComponentsBuilder builder, @PathVariable("type") String type) {
        LOGGER.debug("{} {}", product, version);
        Iterator<String> it = request.getFileNames();
        List<FileInfo> files = Lists.newArrayList();
        while (it.hasNext()) {
            MultipartFile mdf = request.getFile(it.next());
            String fName = mdf.getOriginalFilename();
            LOGGER.debug(fName);
            String name;
            File newFile = null;
            String path = null;
            if (StringUtils.equals("latest", type)) {
                name = product + "_" + version + "." + getFileExtension(fName);
                newFile = new File(productsDir + "/" + product + "/" + version + "/" + name);
                path = "/download/" + name;
            } else if (StringUtils.equals("update", type)) {
                name = product + "_" + version + "_update." + getFileExtension(fName);
                newFile = new File(productsDir + "/" + product + "/" + version + "/update/" + name);
                path = "/download/" + name;
            }
            if (newFile != null && StringUtils.isNotEmpty(path)) {
                try {
                    FileUtils.copyInputStreamToFile(mdf.getInputStream(), newFile);
                    files.add(new FileInfo(genUrl(builder, ""), path, mdf.getContentType(), mdf.getSize(), "DELETE"));
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return Collections.singletonMap("files", files);
    }

    /**
     * 上传图片
     *
     * @param request
     * @param product
     * @param version
     * @param builder
     * @return
     */
    @RequestMapping(value = "/image/{product}/{version}")
    public Map<String, List<FileInfo>> uploadImage(
            MultipartHttpServletRequest request,
            @PathVariable("product") String product,
            @PathVariable("version") String version, UriComponentsBuilder builder) {
        LOGGER.debug("{} {}", product, version);
        Iterator<String> it = request.getFileNames();
        List<FileInfo> files = Lists.newArrayList();
        while (it.hasNext()) {
            MultipartFile mdf = request.getFile(it.next());
            String fName = mdf.getOriginalFilename();
            String name = sdf.format(new Date()) + "." + getFileExtension(fName);
            File newFile = new File(productsDir + "/" + product + "/" + version + "/" + name);
            try {
                FileUtils.copyInputStreamToFile(mdf.getInputStream(), newFile);
                BufferedImage bufferedImage = ImageIO.read(newFile);
                LOGGER.debug("size:[{}x{}]", bufferedImage.getWidth(), bufferedImage.getHeight());
                String path = "/download/any?f=/products/" + product + "/" + version + "/" + name;
                FileInfo info = new FileInfo(genUrl(builder, ""), path, mdf.getContentType(), mdf.getSize(), "DELETE");
                info.setWidth(bufferedImage.getWidth());
                info.setHeight(bufferedImage.getHeight());
                files.add(info);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return Collections.singletonMap("files", files);
    }

    /**
     * 上传下载主页
     *
     * @param request
     * @param product
     * @param builder
     * @return
     */
    @RequestMapping(value = "/template/{product}")
    public Map<String, List<FileInfo>> uploadTemplate(
            MultipartHttpServletRequest request,
            @PathVariable("product") String product,
            UriComponentsBuilder builder) {
        Iterator<String> it = request.getFileNames();
        List<FileInfo> files = Lists.newArrayList();
        while (it.hasNext()) {
            MultipartFile mdf = request.getFile(it.next());
            File newFile = new File(templateDir + "/" + product + ".html");
            try {
                FileUtils.copyInputStreamToFile(mdf.getInputStream(), newFile);
                String path = "/templates/homepages/" + product + ".html";
                FileInfo info = new FileInfo(genUrl(builder, ""), path, mdf.getContentType(), mdf.getSize(), "DELETE");
                files.add(info);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return Collections.singletonMap("files", files);
    }

    private String genUrl(UriComponentsBuilder builder, String path) {
        return builder.path(path).build().toUriString();
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }


}
