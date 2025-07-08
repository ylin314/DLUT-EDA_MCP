package ink.linyang.dlut_eda_mcp.service;

import cn.hutool.json.JSONUtil;
import ink.linyang.dlut_eda_mcp.entity.XcCourse;
import ink.linyang.dlut_eda_mcp.mapper.XcCourseMapper;
import ink.linyang.dlut_eda_mcp.task.SpiderTask;
import ink.linyang.dlut_eda_mcp.util.RedisUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class XcService {

    @Resource
    private XcCourseMapper xcCourseMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SpiderTask spiderTask;

    @Tool(description = "获取形势与政策开课通知清单, 返回json格式数据。形势与政策这门课又叫“形策”，json中给出的字段并没有真正的上课时间，notice_time是上课通知发送的时间。url是通知链接，title是通知标题。")
    public List<XcCourse> getXcCourses() {
        List<XcCourse> courseList = new ArrayList<>();
        // 从Redis中获取形策开课清单
        if (redisUtil.exists(RedisUtil.XC_COURSE_LIST_PREFIX)) {
            // 如果存在，则直接从缓存中获取
            String courseListStr = redisUtil.getString(RedisUtil.XC_COURSE_LIST_PREFIX);
            courseList = JSONUtil.toList(JSONUtil.parseArray(courseListStr), XcCourse.class);
        } else {
            RLock lock = redisUtil.getLock(RedisUtil.XC_COURSE_LIST_LOCK_KEY_PREFIX + ":" + Thread.currentThread().getName());
            try {
                boolean isLocked = lock.tryLock(3, TimeUnit.SECONDS);
                if (isLocked) {
                    // 如果不存在，则从数据库中查询
                    courseList = xcCourseMapper.selectXcCourses();
                    // 缓存到Redis
                    // 设置60s到180s的随机ttl
                    int ttlSeconds = ThreadLocalRandom.current().nextInt(60, 181);
                    redisUtil.setString(RedisUtil.XC_COURSE_LIST_PREFIX, JSONUtil.toJsonStr(courseList), ttlSeconds);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("获取分布式锁失败，无法获取形策开课清单", e);
            } finally {
                lock.unlock();
            }
        }
        // 格式化日期
        for (XcCourse course : courseList) {
            LocalDate dateTime = LocalDate.parse(course.getNoticeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            course.setNoticeTime(dateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
        }
        return courseList;
    }

    @Tool(description = "刷新形势与政策开课通知清单，更新开课清单，重新爬取开课清单，返回结果是重新爬取的到课程数量，如果返回0则表示爬取失败")
    public int refreshXcCourses() {
        // 重新爬取
        return spiderTask.XcTask();
    }

    @Tool(description = "获取 MCP 服务器状态")
    public String getServerStatus() {
        return "服务器正在正常运行。当前时间：" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
    }
}