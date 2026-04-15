package com.example.member.config;

import io.swagger.v3.oas.models.Paths;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer sortOperationsBySummary() {
        return openApi -> {
            Paths paths = openApi.getPaths();
            if (paths == null) return;

            Paths sortedPaths = new Paths();

            paths.entrySet().stream()
                    .sorted((entry1, entry2) -> {
                        String s1 = entry1.getValue().readOperations().isEmpty() ? ""
                                : entry1.getValue().readOperations().get(0).getSummary();
                        String s2 = entry2.getValue().readOperations().isEmpty() ? ""
                                : entry2.getValue().readOperations().get(0).getSummary();


                        return (s1 == null ? "" : s1).compareTo(s2 == null ? "" : s2);
                    })

                    .forEach(entry -> sortedPaths.addPathItem(entry.getKey(), entry.getValue()));


            openApi.setPaths(sortedPaths);
        };
    }
}