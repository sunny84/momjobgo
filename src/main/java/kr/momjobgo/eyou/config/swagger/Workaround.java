package kr.momjobgo.eyou.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class Workaround implements WebMvcOpenApiTransformationFilter {

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        Server localHttps = new Server();
        localHttps.setDescription("localHttps");
        localHttps.setUrl("https://local.devcury.kr");

        Server realServer = new Server();
        realServer.setDescription("real");
        realServer.setUrl("https://api.devcury.kr");

        Server localhost= new Server();
        localhost.setDescription("localhost");
        localhost.setUrl("http://localhost:8090");

        OpenAPI openApi = context.getSpecification();
        openApi.setServers(Arrays.asList(localHttps, realServer, localhost));
        return openApi;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType.equals(DocumentationType.OAS_30);
    }
}
