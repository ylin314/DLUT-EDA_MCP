package ink.linyang.dlut_eda_mcp.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class XlService {
    @Tool(description = "获取当前学期的校历，你不需要读取url的内容，如果用户想要校历，直接返回url即可")
    public String getXqxl() {
        return "https://www.dlut.edu.cn/ggfw/xqxl.htm";
    }
}
