package org.xlet.upgrader.service;

import org.xlet.upgrader.vo.dashboard.ChangeLogDTO;
import org.xlet.upgrader.vo.dashboard.form.ChangeLogForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 下午7:26
 * Summary:
 */
public interface ChangeLogService {

    public Page<ChangeLogDTO> list(String context, Pageable pageable);

    public ChangeLogDTO getChangeLog(String context, Long id);

    ChangeLogDTO save(ChangeLogForm changelog);

    void update(ChangeLogForm changelog, Long id);

    void delete(Long id);

    List<ChangeLogDTO> getByVersion(String context, Long versionId);

}
