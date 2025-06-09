package com.churn.utils;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class ConfigLoader {
    private Map<String, Object> config;

    public void load(String path) throws Exception {
        Yaml yaml = new Yaml();
        config = yaml.load(new FileInputStream(path));
    }

    @SuppressWarnings("unchecked")
    public String getString(String section, String key) {
        Map<String, Object> sec = (Map<String, Object>) config.get(section);
        return sec != null ? String.valueOf(sec.get(key)) : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> getList(String section, String key) {
        Map<String, Object> sec = (Map<String, Object>) config.get(section);
        return sec != null ? (List<String>) sec.get(key) : List.of();
    }

    @SuppressWarnings("unchecked")
    public int getInt(String section, String key) {
        Map<String, Object> sec = (Map<String, Object>) config.get(section);
        return sec != null ? ((Number) sec.get(key)).intValue() : 0;
    }

    @SuppressWarnings("unchecked")
    public double getDouble(String section, String key) {
        Map<String, Object> sec = (Map<String, Object>) config.get(section);
        return sec != null ? ((Number) sec.get(key)).doubleValue() : 0.0;
    }
}
