package com.aquariumshop.aquariumshop.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirestoreConfig {
    val PATH = "D:/WorkSpace/Mobile/Android/WEB_APP_AQUARIUM_SHOP_PACKAGE/SOURCE_CODE/WEB_APP_AQUARIUM_SHOP/BE/aquariumshop_2/ServiceAccountKey.json"
    @Bean
    fun firestore(): Firestore {
        val credentialsStream = FileInputStream(PATH)
        val credentials = GoogleCredentials.fromStream(credentialsStream)
        val options = FirestoreOptions.newBuilder()
            .setCredentials(credentials)
            .setProjectId("aquariumshop-1a86a")
            .build()
        return options.service
    }

}