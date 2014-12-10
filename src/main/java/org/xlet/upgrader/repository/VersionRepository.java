package org.xlet.upgrader.repository;

import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.domain.Version;
import org.xlet.upgrader.domain.VersionState;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-5 下午4:32
 * Summary:
 */
public interface VersionRepository extends PagingAndSortingRepository<Version, Long>,JpaSpecificationExecutor<Version> {

    Version findByProductAndVersion(Product product, String version);

    List<Version> findByProductAndCreateAtGreaterThanOrderByCreateAtAsc(Product product, Date createAt);

    List<Version> findByProductOrderByVersionDesc(Product product, Pageable pageable);

    @Query("select v from Version v where v.product.id=?1")
    List<Version> findByProduct(Long productId);

    @Query("select v from Version v where v.product.id=:productId and v.version=:version")
    Version findByProductIdAndVersion(@Param("productId")Long productId,@Param("version")String version);

    List<Version> findByProductAndStateIsOrderByVersionDesc(Product product, VersionState state, PageRequest pageRequest);
}
