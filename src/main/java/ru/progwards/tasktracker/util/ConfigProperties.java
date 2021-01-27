package ru.progwards.tasktracker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Сервис для получения значений properties из файла application.properties
 * @author Pavel Khovaylo
 */
@Component
@PropertySource("classpath:/application.properties")
public class ConfigProperties {

    /**
     * среда, в которой находится данное приложение
     */
    @Autowired
    private Environment env;

    /**
     * метод для получения значения property
     * @param configKey имя property в файле application.properties
     * @return значение property
     */
    public String getConfigValue(String configKey) {
        return env.getProperty(configKey);
    }
}
