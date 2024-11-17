package me.heidlund.pokemoncollectionagencybackend.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")  // Apply to all endpoints
            .allowedOrigins("http://localhost:5173")  // Allow only this origin
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow necessary HTTP methods
            .allowedHeaders("*")  // Allow all headers
            .allowCredentials(true)
    }
}