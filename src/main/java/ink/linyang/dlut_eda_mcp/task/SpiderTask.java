package ink.linyang.dlut_eda_mcp.task;

import ink.linyang.dlut_eda_mcp.entity.XcCourse;
import ink.linyang.dlut_eda_mcp.feign.SpiderFeign;
import ink.linyang.dlut_eda_mcp.mapper.XcCourseMapper;
import ink.linyang.dlut_eda_mcp.util.RedisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import java.util.List;

@Component
public class SpiderTask {
    @Resource
    private SpiderFeign spiderFeign;

    @Resource
    private XcCourseMapper xcCourseMapper;

    @Resource
    private RedisUtil redisUtil;

    private static final Logger logger = LoggerFactory.getLogger(SpiderTask.class);

    @PostConstruct
    public void init() {
        XcTask();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public int XcTask() {
        logger.info("开始执行形策开课清单爬虫任务...");
        try {
            List<XcCourse> xcCourses = spiderFeign.getXcCourses();
            if (xcCourses == null || xcCourses.isEmpty()) {
                logger.error("获取形策开课清单失败！请检查爬虫服务是否正常运行");
                return 0;
            } else {
                logger.info("获取形策开课清单成功，数量: {}", xcCourses.size());
                xcCourseMapper.insertXcCourses(xcCourses);
                redisUtil.delete(RedisUtil.XC_COURSE_LIST_PREFIX);
                return xcCourses.size();
            }
        } catch (Exception e) {
            logger.error("形策开课清单爬虫任务执行失败: {}", e.getMessage());
            return 0;
        }
    }
}
