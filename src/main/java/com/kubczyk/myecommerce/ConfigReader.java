package com.kubczyk.myecommerce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {

    private Map<String, String> config;

    public ConfigReader() {
        config = new HashMap<>();
        loadConfigFromEnvironment();
        loadConfigFromEnvFile(".env");
    }

    private void loadConfigFromEnvironment() {
        Map<String, String> environmentVariables = System.getenv();
        config.putAll(environmentVariables);
    }

    private void loadConfigFromEnvFile(String envFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(envFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    config.put(key, value);
                }
            }
        } catch (IOException exception) {
            System.err.println("Error reading .env file: " + exception.getMessage());
        }
    }

    public String getConfig(String key) {
        return config.get(key);
    }
}
