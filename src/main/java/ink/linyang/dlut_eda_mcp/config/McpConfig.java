//package ink.linyang.dlut_eda_mcp.config;
//
//import cn.hutool.http.HttpStatus;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import ink.linyang.dlut_eda_mcp.util.ApiKeyUtil;
//import io.modelcontextprotocol.server.transport.WebMvcSseServerTransport;
//import jakarta.annotation.Resource;
//import org.springframework.ai.autoconfigure.mcp.server.McpServerProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.function.*;
//
//import java.util.Optional;
//
//@Configuration
//public class McpConfig {
//    @Resource
//    private ApiKeyUtil apiKeyUtil;
//
//    @Bean
//    public WebMvcSseServerTransport webMvcSseServerTransport(ObjectMapper objectMapper, McpServerProperties serverProperties) {
//        return new WebMvcSseServerTransport(objectMapper, serverProperties.getSseMessageEndpoint());
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> mvcMcpRouterFunction(WebMvcSseServerTransport transport) {
//        return transport.getRouterFunction().filter((request, next) -> {
//            String path = request.path();
//            if ("/sse".equals(path)) {
//                Optional<String> key = request.param("key");
//                if (key.isPresent()) {
//                    if (apiKeyUtil.isValidApiKey(key.get())) {
//                        return next.handle(request);
//                    }
//                }
//                return ServerResponse.status(HttpStatus.HTTP_FORBIDDEN).build();
//            }
//            return next.handle(request);
//        });
//    }
//}
