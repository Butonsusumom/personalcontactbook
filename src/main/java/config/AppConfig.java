package config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import services.ContactService;
import services.UserService;

import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig extends WebMvcConfigurerAdapter {
    private final static Logger logger = getLogger(AppConfig.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Value("${typeDB}")
    private String typeDB;

    @Value("${pathToDBFiles}")
    private String pathToDBFiles;

    @Value("${possibleTypesDB}")
    private String possibleTypesDB;

    @Component
    public class MyBean {
        @Autowired
        public MyBean(ApplicationArguments args) {
            Properties properties = new Properties();
            properties.setProperty("typeDB", typeDB);
            properties.setProperty("possibleTypesDB", possibleTypesDB);
            properties.setProperty("pathToDBFiles", pathToDBFiles);
            userService.setDataSource(properties);
            contactService.setDataSource(properties);

            logger.info("*** Config file has read");
            logger.info("*** All constants have initialised");
        }
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

}
