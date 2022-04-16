package SC_DB.DAO;

import SC_DB.Object.Course;

import SC_DB.Object.Student;
import SC_DB.Object.Course;
import SC_DB.Object.SelectCourse;
import java.sql.SQLException;
import java.util.List;

public interface ISCDao {
    // 添加 选课记录
    public int addSC(SelectCourse selectcourse);
    // 修改
    public int modifySC(SelectCourse selectcourse);
    //删
    public int dropSC(String Sno, String Cno) throws SQLException;
    //查
    public Object[][] findSC(String[] keyword);
    //所有
    public Object[][] findAllSC();

    public SelectCourse findSCById(String Sno, String Cno);
}
