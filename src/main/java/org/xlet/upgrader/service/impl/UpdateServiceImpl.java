package org.xlet.upgrader.service.impl;

import org.xlet.upgrader.domain.ChangeLog;
import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.domain.Version;
import org.xlet.upgrader.repository.ChangeLogRepository;
import org.xlet.upgrader.repository.ProductRepository;
import org.xlet.upgrader.repository.VersionRepository;
import org.xlet.upgrader.service.UpdateService;
import org.xlet.upgrader.vo.dashboard.form.AutoSubmitForm;
import com.google.common.io.Files;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-5 下午5:53
 * Summary:
 */
@Service
@Transactional
public class UpdateServiceImpl implements UpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private ChangeLogRepository logRepository;
    @Value("${base.dir}")
    private String fileBaseDir;


    @Override
    public void genVersion(AutoSubmitForm form) {
        Product product = productRepository.findByCode(form.getProduct());
        try {
            String subPath = "/" + form.getProduct() + "/" + form.getVersion() + "/" + form.getProduct() + "_" + form.getVersion() + "." + Files.getFileExtension(form.getUrl());
            LOGGER.debug(subPath);
            //下载远程文件
            File file = new File(fileBaseDir + subPath);
            //
            Request.Get(form.getUrl()).execute().saveContent(file);
            //生成新的版本
            Version version = new Version();
            version.setProduct(product);
            version.setPack(subPath);

            versionRepository.save(version);
            //change logs...
            ChangeLog changeLog = new ChangeLog();
            changeLog.setVersion(version);
            logRepository.save(changeLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
