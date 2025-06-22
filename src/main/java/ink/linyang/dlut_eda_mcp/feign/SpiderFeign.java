package ink.linyang.dlut_eda_mcp.feign;

import ink.linyang.dlut_eda_mcp.config.FeignOkHttpConfig;
import ink.linyang.dlut_eda_mcp.entity.XcCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "SpiderFeignClient", url = "http://localhost:5555", configuration = FeignOkHttpConfig.class)
public interface SpiderFeign {

    @GetMapping("/xc_courses")
    List<XcCourse> getXcCourses();
}
