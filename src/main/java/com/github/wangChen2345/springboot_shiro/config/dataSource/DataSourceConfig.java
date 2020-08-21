package com.github.wangChen2345.springboot_shiro.config.dataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Chen's
 * @date 2019/6/10 9:55
 */
@Configuration
public class DataSourceConfig {
  @Bean(name = "primaryDataSource")
  @Qualifier("primaryDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSource primaryDataSource() {
    return DataSourceBuilder.create().build();
  }

  //  @Bean(name = "secondaryDataSource")
  //  @Qualifier("secondaryDataSource")
  //  @Primary
  //  @ConfigurationProperties(prefix = "spring.datasource.secondary")
  //  public DataSource secondaryDataSource() {
  //    return DataSourceBuilder.create().build();
  //  }

  //  @Bean(name = "tertiaryDataSource")
  //  @Qualifier("tertiaryDataSource")
  //  @ConfigurationProperties(prefix = "spring.datasource.tertiary")
  //  public DataSource tertiaryDataSource() {
  //    return DataSourceBuilder.create().build();
  //  }
}
