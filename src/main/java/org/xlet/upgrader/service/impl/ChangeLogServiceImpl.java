package org.xlet.upgrader.service.impl;

import org.xlet.upgrader.domain.ChangeLog;
import org.xlet.upgrader.domain.Version;
import org.xlet.upgrader.exception.TargetNotFoundException;
import org.xlet.upgrader.repository.ChangeLogRepository;
import org.xlet.upgrader.repository.VersionRepository;
import org.xlet.upgrader.service.ChangeLogService;
import org.xlet.upgrader.vo.dashboard.ChangeLogDTO;
import org.xlet.upgrader.vo.dashboard.form.ChangeLogForm;
import com.google.common.collect.Lists;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 下午7:27
 * Summary:
 */
@Service
public class ChangeLogServiceImpl implements ChangeLogService {

    @Autowired
    private Mapper dozer;
    @Autowired
    private ChangeLogRepository changeLogRepository;
    @Autowired
    private VersionRepository versionRepository;

    @Override
    public Page<ChangeLogDTO> list(String context, Pageable pageable) {
        Page<ChangeLog> changeLogs = changeLogRepository.findAll(pageable);
        Iterator<ChangeLog> iterator = changeLogs.iterator();
        List<ChangeLogDTO> dtoList = Lists.newArrayList();
        while (iterator.hasNext()) {
            ChangeLogDTO logDTO = dozer.map(iterator.next(), ChangeLogDTO.class);
            //logDTO.setImage(context + logDTO.getImage());
            dtoList.add(logDTO);
        }
        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    @Override
    public ChangeLogDTO getChangeLog(String context, Long id) {
        ChangeLog changeLog = changeLogRepository.findOne(id);
        if (changeLog == null) throw new TargetNotFoundException();
        ChangeLogDTO logDTO = dozer.map(changeLog, ChangeLogDTO.class);
        //logDTO.setImage(context + logDTO.getImage());
        return logDTO;
    }

    @Override
    public ChangeLogDTO save(ChangeLogForm changelog) {
        Version version = versionRepository.findOne(changelog.getVersionId());
        ChangeLog newChangeLog = dozer.map(changelog, ChangeLog.class);
        newChangeLog.setVersion(version);
        changeLogRepository.save(newChangeLog);
        return dozer.map(newChangeLog, ChangeLogDTO.class);
    }

    @Override
    public void update(ChangeLogForm changelog, Long id) {
        ChangeLog dbChangeLog = changeLogRepository.findOne(id);
        dozer.map(changelog, dbChangeLog);
        if (changelog.getVersionId() != null) {
            Version version = versionRepository.findOne(changelog.getVersionId());
            dbChangeLog.setVersion(version);
        }
        changeLogRepository.save(dbChangeLog);
    }

    @Override
    public void delete(Long id) {
        changeLogRepository.delete(id);
    }

    @Override
    public List<ChangeLogDTO> getByVersion(String context, Long versionId) {
        Iterable<ChangeLog> changeLogs = versionId == null ? changeLogRepository.findAll() : changeLogRepository.findByVersion(versionId);
        List<ChangeLogDTO> logDTOList = Lists.newArrayList();
        Iterator<ChangeLog> iterator = changeLogs.iterator();
        while (iterator.hasNext()) {
            ChangeLogDTO dto = dozer.map(iterator.next(), ChangeLogDTO.class);
            //dto.setImage(context + dto.getImage());
            logDTOList.add(dto);
        }
        return logDTOList;
    }
}
