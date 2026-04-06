package org.iimsa.product_service.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Bearer", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer"))
                .info(new Info()
                        .title("Product Service API")
                        .description("상품 서비스 API 테스트")
                        .version("1.0.0"));
    }

//    @Bean
//    public OpenAPI openAPI(@Value("${openapi.service.url}:http://localhost:5160") String url) {
//        return new OpenAPI()
//                .servers(List.of(new Server().url(url)))
//                .components(new Components().addSecuritySchemes("Bearer",
//                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
//                .addSecurityItem(new SecurityRequirement().addList("Bearer"))
//                .info(new Info().title("스파르타 허브")
//                        .description("Company API"));
//    }

//    @Bean
//    public OpenApiCustomizer addPrefixToPaths() {
//        return openApi -> {
//            Paths paths = openApi.getPaths();
//            if (paths == null) {
//                return;
//            }
//
//            // 기존 paths에 prefix 붙여서 추가
//            Map<String, PathItem> original = new LinkedHashMap<>(paths);
//            for (String path : original.keySet().toArray(new String[0])) {
//                String prefixed = PREFIX + path;
//                if (!paths.containsKey(prefixed)) {
//                    PathItem item = original.get(path);
//                    paths.addPathItem(prefixed, item);
//                }
//            }
//
//            // 실제 엔드포인트는 제거하고 게이트웨이를 통한 엔드포인트만 노출
//            original.keySet().forEach(s -> {
//                if (!s.startsWith(PREFIX)) {
//                    paths.remove(s);
//                }
//            });
//        };
//    }
}
