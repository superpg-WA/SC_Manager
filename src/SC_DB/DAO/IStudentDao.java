package SC_DB.DAO;

import SC_DB.Object.Student;
import SC_DB.Object.Course;
import SC_DB.Object.SelectCourse;
import java.sql.SQLException;
import java.util.List;

/*
    接口类 声明方法
 */


public interface IStudentDao {
    // 添加学生
    public int addStudent(Student student);
    // 修改
    public int modifyStudent(Student student);
    //删
    public int dropStudent(String id) throws SQLException;
    //查
    public Object[][] findStudents(String[] keyword);
    //所有
    public Object[][] findAllStudents();

    public Student findStudentById(String id);
}
