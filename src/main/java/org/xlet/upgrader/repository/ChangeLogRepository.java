package org.xlet.upgrader.repository;

import org.xlet.upgrader.domain.ChangeLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-5 下午5:36
 * Summary:
 */
public interface ChangeLogRepository extends PagingAndSortingRepository<ChangeLog, Long> {

    @Query("select c from ChangeLog c where c.version.id=?1")
    Iterable<ChangeLog> findByVersion(Long versionId);
}
