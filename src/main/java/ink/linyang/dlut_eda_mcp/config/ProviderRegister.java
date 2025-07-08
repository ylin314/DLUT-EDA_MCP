package ink.linyang.dlut_eda_mcp.config;

import ink.linyang.dlut_eda_mcp.service.XcService;
import ink.linyang.dlut_eda_mcp.service.XlService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderRegister {
    @Bean
    public ToolCallbackProvider xcTools(XcService xcService) {
        return MethodToolCallbackProvider.builder().toolObjects(xcService).build();
    }

    @Bean
    public ToolCallbackProvider xlTools(XlService xlService) {
        return MethodToolCallbackProvider.builder().toolObjects(xlService).build();
    }
}
