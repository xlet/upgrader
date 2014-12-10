package org.xlet.upgrader.service;

import org.xlet.upgrader.domain.VersionState;
import org.xlet.upgrader.vo.LatestVersion;
import org.xlet.upgrader.vo.Response;
import org.xlet.upgrader.vo.VersionVo;
import org.xlet.upgrader.vo.dashboard.VersionDTO;
import org.xlet.upgrader.vo.dashboard.form.VersionForm;
import org.xlet.upgrader.vo.homepage.DownloadVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-10 上午10:33
 * Summary:
 */
public interface VersionService {

    public Response<VersionVo> checkUpdate(String context, VersionState state, String version, String product);

    LatestVersion getLatestVersion(VersionState state, String context, String product);

    Page<VersionDTO> list(String context, PageRequest pageRequest);

    VersionDTO getVersion(String context, Long id);

    VersionDTO saveVersion(VersionForm version);

    void update(VersionForm version, Long id);

    void delete(Long id);

    List<VersionDTO> getByProduct(String context, Long productId);

    boolean check(Long productId, String version);

    DownloadVo getDownloadInfo(String context, String product);

    void update(VersionState state, Long id);

    void downloadCount(String product, String version);
}
