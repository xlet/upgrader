package org.xlet.upgrader.service.impl;

import org.xlet.upgrader.domain.ChangeLog;
import org.xlet.upgrader.domain.Version;
import org.xlet.upgrader.repository.ProductRepository;
import org.xlet.upgrader.repository.VersionRepository;
import org.xlet.upgrader.service.VersionService;
import org.xlet.upgrader.util.Collections3;
import org.xlet.upgrader.vo.*;
import org.xlet.upgrader.vo.dashboard.VersionDTO;
import org.xlet.upgrader.vo.dashboard.form.VersionForm;
import org.xlet.upgrader.vo.homepage.DownloadVo;
import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.domain.VersionState;
import com.google.common.collect.Lists;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Iterator;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-10 上午10:34
 * Summary:
 */
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Mapper dozer;

    @Override
    public Response<VersionVo> checkUpdate(String context, final VersionState state, final String version, final String productName) {
        final Product product = productRepository.findByCode(productName);
        List<Version> versions = Lists.newArrayList();
        Response<VersionVo> response = new Response<>();
        if (product != null) {
            versions = versionRepository.findAll(new Specification<Version>() {
                @Override
                public Predicate toPredicate(Root<Version> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate statePredicate = (state == null || state == VersionState.RELEASE) ? cb.equal(root.get("state"), VersionState.RELEASE) : cb.notEqual(root.get("state"), VersionState.INIT);
                    Expression<String> versionExp = root.get("version");
                    return cb.and(cb.equal(root.get("product"), product), statePredicate, cb.greaterThan(versionExp, version));
                }
            }, new Sort(Sort.Direction.ASC, "version"));
            response.setProduct(dozer.map(product, ProductVo.class));
        }
        List<VersionVo> versionVos = Lists.newArrayList();
        Window window = null;
        if (Collections3.isNotEmpty(versions)) {
            for (Version v : versions) {
                VersionVo versionVo = dozer.map(v, VersionVo.class);
                versionVo.setUrl(context + versionVo.getUrl());
                if (Collections3.isNotEmpty(v.getChangeLogs())) {
                    List<ChangeLogVo> changeLogVos = Lists.newArrayList();
                    for (ChangeLog log : v.getChangeLogs()) {
                        ChangeLogVo logVo = dozer.map(log, ChangeLogVo.class);
                        logVo.setImage(context + logVo.getImage());
                        changeLogVos.add(logVo);
                        if (window == null) {
                            window = new Window(log.getWidth(), log.getHeight());
                            response.setWindow(window);
                        }
                    }
                    versionVo.setChangeLogs(changeLogVos);
                }
                versionVos.add(versionVo);
            }
            response.setItems(versionVos);
        } else {
            response.setLatest(true);
        }

        return response;
    }

    @Override
    public LatestVersion getLatestVersion(final VersionState state, String context, final String productName) {
        List<Version> versions;

        versions = versionRepository.findAll(new Specification<Version>() {
            @Override
            public Predicate toPredicate(Root<Version> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (state == null || state == VersionState.RELEASE) {
                    cb.equal(root.get("state"), VersionState.RELEASE);
                } else {
                    cb.notEqual(root.get("state"), VersionState.INIT);
                }
                return cb.and(cb.equal(root.get("product").get("code"), productName));
            }
        }, new PageRequest(0, 1, Sort.Direction.DESC, "version")).getContent();
        if (Collections3.isNotEmpty(versions))

        {
            Version version = versions.get(0);
            version.setDownload(context + version.getDownload());
            version.setPack(context + version.getPack());
            return dozer.map(versions.get(0), LatestVersion.class);
        }

        return null;

    }

    @Override
    public Page<VersionDTO> list(String context, PageRequest pageRequest) {
        Page<Version> versions = versionRepository.findAll(pageRequest);
        List<VersionDTO> dtoList = Lists.newArrayList();
        Iterator<Version> iterator = versions.iterator();
        while (iterator.hasNext()) {
            VersionDTO dto = dozer.map(iterator.next(), VersionDTO.class);
            //for update
            //dto.setDownload(context + dto.getDownload());
            //dto.setPack(context + dto.getPack());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageRequest, dtoList.size());
    }

    @Override
    public VersionDTO getVersion(String context, Long id) {
        Version version = versionRepository.findOne(id);
        if (version != null) {
            VersionDTO versionDTO = dozer.map(version, VersionDTO.class);
            versionDTO.setDownload(context + versionDTO.getDownload());
            versionDTO.setPack(context + versionDTO.getPack());
            return versionDTO;
        }
        return null;
    }

    @Override
    public VersionDTO saveVersion(VersionForm version) {
        Product product = productRepository.findOne(version.getProductId());
        Version newVersion = dozer.map(version, Version.class);
        newVersion.setProduct(product);
        versionRepository.save(newVersion);
        return dozer.map(newVersion, VersionDTO.class);
    }

    @Override
    public void update(VersionForm version, Long id) {
        Version dbVersion = versionRepository.findOne(id);
        dozer.map(version, dbVersion);
        if (version.getProductId() != null) {
            Product product = productRepository.findOne(version.getProductId());
            dbVersion.setProduct(product);
        }
        versionRepository.save(dbVersion);
    }

    @Override
    public void delete(Long id) {
        versionRepository.delete(id);
    }

    @Override
    public List<VersionDTO> getByProduct(String context, Long productId) {
        List<Version> versions = productId == null ? Lists.newArrayList(versionRepository.findAll()) : versionRepository.findByProduct(productId);
        List<VersionDTO> dtoList = Lists.newArrayList();
        if (Collections3.isNotEmpty(versions)) {
            for (Version version : versions) {
                VersionDTO dto = dozer.map(version, VersionDTO.class);
                //dto.setDownload(context + dto.getDownload());
                //dto.setPack(context + dto.getPack());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public boolean check(Long productId, String version) {
        Version v = versionRepository.findByProductIdAndVersion(productId, version);
        return v == null;
    }

    @Override
    public DownloadVo getDownloadInfo(String context, String code) {
        Product product = productRepository.findByCode(code);
        List<Version> versions = versionRepository.findByProductOrderByVersionDesc(product, new PageRequest(0, 1));
        DownloadVo vo = null;
        if (Collections3.isNotEmpty(versions)) {
            Version version = versions.get(0);
            vo = dozer.map(version, DownloadVo.class);
            vo.setUrl(context + vo.getUrl());
        }
        return vo;
    }

    @Override
    public void update(VersionState state, Long id) {
        Version dbVersion = versionRepository.findOne(id);
        if (dbVersion != null && state != null) {
            dbVersion.setState(state);
            versionRepository.save(dbVersion);
        }
    }

    @Override
    public void downloadCount(final String product, final String version) {
        Version v = versionRepository.findOne(new Specification<Version>() {
            @Override
            public Predicate toPredicate(Root<Version> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get("product").get("code"), product), cb.equal(root.get("version"), version));
            }
        });

        if (v != null) {
            v.count();
            versionRepository.save(v);

        }
    }
}
