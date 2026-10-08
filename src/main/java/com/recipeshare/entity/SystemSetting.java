package com.recipeshare.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "system_settings")
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setting_key", nullable = false, unique = true, length = 100)
    private String settingKey;

    @Column(name = "setting_value", nullable = false, length = 255)
    private String settingValue;

    public SystemSetting() {
    }

    public SystemSetting(Long id, String settingKey, String settingValue) {
        this.id = id;
        this.settingKey = settingKey;
        this.settingValue = settingValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public static SystemSettingBuilder builder() {
        return new SystemSettingBuilder();
    }

    public static class SystemSettingBuilder {
        private Long id;
        private String settingKey;
        private String settingValue;

        public SystemSettingBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SystemSettingBuilder settingKey(String settingKey) {
            this.settingKey = settingKey;
            return this;
        }

        public SystemSettingBuilder settingValue(String settingValue) {
            this.settingValue = settingValue;
            return this;
        }

        public SystemSetting build() {
            SystemSetting ss = new SystemSetting();
            ss.setId(this.id);
            ss.setSettingKey(this.settingKey);
            ss.setSettingValue(this.settingValue);
            return ss;
        }
    }
}
