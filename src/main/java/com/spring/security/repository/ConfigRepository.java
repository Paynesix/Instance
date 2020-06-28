package com.spring.security.repository;

import com.spring.security.model.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Long> {
    List<ConfigEntity> findByHisIdAndSystemFlag(Long hisId, Integer SystemFlag);

    @Transactional
    @Modifying
    @Query(value = "update ConfigEntity set configValue = :configValue where hisId = :hisId and configKey = :configKey")
    int updateConfigValue(@Param("configValue") String configValue, @Param("hisId") Long hisId, @Param("configKey") String configKey);


    List<ConfigEntity> findByHisId(Long hisId);

    ConfigEntity findByHisIdAndConfigKey(Long hisId, String configKey);

    ConfigEntity findByConfigKeyAndSystemFlag(String configKey, Integer systemFlag);

}
