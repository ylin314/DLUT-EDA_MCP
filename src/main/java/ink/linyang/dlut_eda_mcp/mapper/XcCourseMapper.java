package ink.linyang.dlut_eda_mcp.mapper;

import ink.linyang.dlut_eda_mcp.entity.XcCourse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface XcCourseMapper {
    List<XcCourse> selectXcCourses();
    void insertXcCourses(List<XcCourse> xcCourses);
}