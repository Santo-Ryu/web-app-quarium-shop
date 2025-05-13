package com.aquariumshop.aquariumshop.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = ["com.aquariumshop.aquariumshop.repository"])
@EnableTransactionManagement
class JpaConfig {
    @Bean
    fun entityManagerFactory(
        dataSource: DataSource,
        jpaVendorAdapter: JpaVendorAdapter
    ): LocalContainerEntityManagerFactoryBean {
        val factoryBean = LocalContainerEntityManagerFactoryBean()
        factoryBean.setDataSource(dataSource)
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter)
        factoryBean.setPackagesToScan("com.aquariumshop.aquariumshop.model")
        return factoryBean
    }
}