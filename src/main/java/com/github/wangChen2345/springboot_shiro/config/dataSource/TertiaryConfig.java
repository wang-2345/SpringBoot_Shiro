package com.github.wangChen2345.springboot_shiro.config.dataSource;// package com.xy.config.dataSource;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
// import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
// import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
// import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.transaction.annotation.EnableTransactionManagement;
//
// import javax.persistence.EntityManager;
// import javax.sql.DataSource;
// import java.util.Map;
//
// @Configuration
// @EnableTransactionManagement
// @EnableJpaRepositories(
//    entityManagerFactoryRef = "entityManagerFactoryTertiary",
//    transactionManagerRef = "transactionManagerTertiary",
//    basePackages = {"com.xy.baseT.repository"}) // 设置Repository所在位置
// public class TertiaryConfig {
//  @Autowired
//  @Qualifier("tertiaryDataSource")
//  private DataSource tertiaryDataSource;
//
//  @Autowired private JpaProperties jpaProperties;
//
//  @Autowired private HibernateProperties hibernateProperties;
//
//  //    private Map<String, String> getVendorProperties(DataSource dataSource) {
//  //        return jpaProperties.getHibernateProperties(dataSource);
//  //    }
//
//  @Bean(name = "entityManagerFactoryTertiary")
//  public LocalContainerEntityManagerFactoryBean entityManagerFactoryTertiary(
//      EntityManagerFactoryBuilder builder) {
//    Map<String, Object> properties =
//        hibernateProperties.determineHibernateProperties(
//            jpaProperties.getProperties(), new HibernateSettings());
//    return builder
//        .dataSource(tertiaryDataSource)
//        .properties(properties)
//        .packages("com.xy.baseT.entiy") // 设置实体类所在位置
//        .persistenceUnit("tertiaryPersistenceUnit")
//        .build();
//  }
//
//  @Bean(name = "entityManagerTertiary")
//  public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//    return entityManagerFactoryTertiary(builder).getObject().createEntityManager();
//  }
//
//  @Bean(name = "transactionManagerTertiary")
//  PlatformTransactionManager transactionManagerTertiary(EntityManagerFactoryBuilder builder) {
//    return new JpaTransactionManager(entityManagerFactoryTertiary(builder).getObject());
//  }
// }
