package com.be01.prj2.repository.purView;

import com.be01.prj2.entity.PurViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurViewRepository extends JpaRepository<PurViewEntity, Long> {

    // id를 기준으로 구매했던 물품 내역 리스트 조회
    List<PurViewEntity> findByUserIdx(Long id);
}
