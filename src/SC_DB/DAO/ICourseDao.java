package SC_DB.DAO;

import java.sql.SQLException;
import java.util.List;
import SC_DB.Object.Student;
import SC_DB.Object.Course;
import SC_DB.Object.SelectCourse;

public interface ICourseDao {
    // 添加课程
    public int addCourse(Course course);
    // 修改
    public int modifyCourse(Course course);
    //删
    public int dropCourse(String id) throws SQLException;
    //查
    public Object[][] findCourses(String[] keyword);
    //所有
    public Object[][] findAllCourses();

    public Course findCourseById(String id);
}
