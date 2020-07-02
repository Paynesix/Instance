package com.spring.security.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Description
 * @Author lizhou
 * @Version 1.0.0
 * @Since 1.0
 * @Date 2019-08-20
 */
@Entity
@Table(name = "t_billc_bill_config", schema = "spring_security")
@EntityListeners(AuditingEntityListener.class)
public class ConfigEntity {
    private Long id;
    private Long hisId;
    private String configKey;
    private String configValue;
    private Integer systemFlag;
    private String memo;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "his_id")
    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    @Basic
    @Column(name = "config_key")
    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Basic
    @Column(name = "config_value")
    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Basic
    @Column(name = "system_flag")
    public Integer getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(Integer systemFlag) {
        this.systemFlag = systemFlag;
    }

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @CreatedDate
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigEntity that = (ConfigEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(hisId, that.hisId) &&
                Objects.equals(configKey, that.configKey) &&
                Objects.equals(configValue, that.configValue) &&
                Objects.equals(systemFlag, that.systemFlag) &&
                Objects.equals(memo, that.memo) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hisId, configKey, configValue, systemFlag, memo, createTime, updateTime);
    }
}
