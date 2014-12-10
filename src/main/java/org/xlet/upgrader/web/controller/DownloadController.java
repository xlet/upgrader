package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.domain.RealFile;
import org.xlet.upgrader.service.VersionService;
import org.xlet.upgrader.util.Collections3;
import org.xlet.upgrader.util.Servlets;
import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-2 下午3:25
 * Summary:
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

    @Value("${base.dir}")
    private String fileBaseDir;
    @Value("${products.dir}")
    private String productsDir;

    private String FILE_SEPARATOR = "/";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private VersionService versionService;

    /**
     * complete installer download.
     * e.g. http://update.w.cn/download/im_1.0.1.exe
     *
     * @param f        product
     * @param version  the version
     * @param ext      file extension
     * @param response http servlet response
     * @param range    http header
     */
    @RequestMapping(value = "/{product}_{version}.{ext}", method = RequestMethod.GET)
    public void downloadLatest(
            @PathVariable("product") String f,
            @PathVariable("version") String version,
            @PathVariable("ext") String ext,
            HttpServletResponse response,
            @RequestHeader(required = false, value = "Range") String range) {
        String filename = f + "_" + version;
        versionService.downloadCount(f, version);
        String filePath = productsDir + FILE_SEPARATOR + f + FILE_SEPARATOR + version + FILE_SEPARATOR + filename + "." + ext;
        LOGGER.debug("path:{}", filePath);
        down(filePath, response, range);

    }

    /**
     * files for update download.
     * <p/>
     * e.g. http://update.w.cn/download/im_1.0.2_update.zip
     *
     * @param f
     * @param version
     * @param ext
     * @param response
     * @param range
     */
    @RequestMapping(value = "/{product}_{version}_update.{ext}", method = RequestMethod.GET)
    public void downloadPack(
            @PathVariable("product") String f,
            @PathVariable("version") String version,
            @PathVariable("ext") String ext,
            HttpServletResponse response,
            @RequestHeader(required = false, value = "Range") String range) {
        String filename = f + "_" + version + "_update";
        String filePath = productsDir + FILE_SEPARATOR + f + FILE_SEPARATOR + version + FILE_SEPARATOR + "update/" + filename + "." + ext;
        LOGGER.debug("path:{}", filePath);
        down(filePath, response, range);

    }


    @RequestMapping(value = "/{filename}_{version}.{ext}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("filename") String f,
            @PathVariable("version") String version,
            @PathVariable("ext") String ext) {
        String filename = f + "_" + version;
        String filePath = productsDir + FILE_SEPARATOR + f + FILE_SEPARATOR + version + FILE_SEPARATOR + filename + "." + ext;
        delete(filePath);
    }

    @RequestMapping(value = "/{product}/{version}/resource", method = RequestMethod.GET)
    public void downloadAny(HttpServletResponse response,
                            @RequestParam(value = "f") String file,
                            @RequestHeader(required = false, value = "Range") String range,
                            @PathVariable("product") String product,
                            @PathVariable("version") String version) {
        String path = new StringBuilder()
                .append(productsDir)
                .append(FILE_SEPARATOR)
                .append(product)
                .append(FILE_SEPARATOR).append(version).append(FILE_SEPARATOR).append("resource").append(FILE_SEPARATOR).append(file).toString();
        down(path, response, range);
    }

    @RequestMapping(value = "/{product}/{version}/resource", method = RequestMethod.DELETE)
    public void deleteResource(@RequestParam(value = "f") String file,
                               @PathVariable("product") String product,
                               @PathVariable("version") String version) {
        String path = new StringBuilder()
                .append(productsDir)
                .append(FILE_SEPARATOR)
                .append(product)
                .append(FILE_SEPARATOR).append(version).append(FILE_SEPARATOR).append("resource").append(FILE_SEPARATOR).append(file).toString();
        delete(path);
    }

    /**
     * 下载任意文件
     *
     * @param response
     * @param file
     * @param range
     */
    @RequestMapping(value = "/any", method = RequestMethod.GET)
    public void downAny(HttpServletResponse response,
                        @RequestParam(value = "f") String file,
                        @RequestHeader(required = false, value = "Range") String range) {
        String path = new StringBuilder(fileBaseDir).append(FILE_SEPARATOR).append(file).toString();
        down(path, response, range);
    }

    @RequestMapping(value = "/any", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAny(@RequestParam(value = "f") String file) {
        String path = new StringBuilder(fileBaseDir).append(FILE_SEPARATOR).append(file).toString();
        delete(path);
    }

    private void delete(String filePath) {
        try {
            FileUtils.forceDelete(new File(filePath));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void down(String filePath, HttpServletResponse response, String range) {
        File file = new File(filePath);
        if (!file.exists()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
        long position = 0;
        long tail = file.length() - 1;
        long length = file.length();

        if (StringUtils.isNotEmpty(range)) {
            response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
            range = StringUtils.replace(range, "bytes=", "");
            String[] positions = StringUtils.split(range, "-");
            position = Long.parseLong(positions[0]);
            if (positions.length > 1) {
                long position2 = Long.parseLong(positions[1]);
                tail = position2 >= length ? tail : position2;
            }

        }
        long total = tail - position + 1;
        StringBuilder contentRange = new StringBuilder();
        contentRange.append("bytes ").append(position).append("-").append(tail).append("/").append(length);
        LOGGER.debug("Content-range:{}", contentRange.toString());
        response.setHeader(HttpHeaders.CONTENT_RANGE, contentRange.toString());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.OCTET_STREAM.toString());
        Servlets.setFileDownloadHeader(response, file.getName());
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(total));
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            IOUtils.copyLarge(inputStream, response.getOutputStream(), position, total, buffer);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }


    @RequestMapping(value = "files", method = RequestMethod.GET)
    public List<RealFile> realFileList() {
        Collection<File> files = FileUtils.listFiles(new File(productsDir), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
        List<RealFile> list = Lists.newArrayList();
        if (Collections3.isNotEmpty(files)) {
            for (File f : files) {
                list.add(new RealFile(f.getAbsolutePath(),f.length(), sdf.format(new Date(f.lastModified()))));
            }
        }
        return list;
    }

}
