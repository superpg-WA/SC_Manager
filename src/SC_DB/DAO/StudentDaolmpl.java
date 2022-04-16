package SC_DB.DAO;

import SC_DB.Object.Student;
import SC_DB.Object.Course;
import SC_DB.Object.SelectCourse;
import java.util.List;
import SC_DB.Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;

public class StudentDaolmpl implements IStudentDao{
    //增
    @Override
    public int addStudent(Student student) {
        int row = 0;
        PreparedStatement pstnt = null;
        Connection conn = DBUtil.getConn();
        String sql = "insert into student values (?,?,?,?,?)";
        try{
            pstnt = conn.prepareStatement(sql);
            pstnt.setString(1,student.getNO());
            pstnt.setString(2,student.getName());
            pstnt.setString(3,student.getSex());
            pstnt.setString(4,student.getage());
            pstnt.setString(5,student.getdept());

            row = pstnt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstnt,null);
        }
        return row;
    }

    //改
    @Override
    public int modifyStudent(Student student) {
        int row = -1;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql ="update student set Sname = ?, Ssex = ?, Sage = ?, Sdept = ? where Sno = ? ";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,student.getName());
            pstmt.setString(2,student.getSex());
            pstmt.setString(3,student.getage());
            pstmt.setString(4,student.getdept());
            pstmt.setString(5,student.getNO());
            row = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return row;
    }

    //删
    @Override
    public int dropStudent(String id) {
        int row = -1;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql ="delete from student where Sno = ? ";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            row = pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return row;
    }



    //查单
    @Override
    public Object[][] findStudents(String[] keyword) {
        Object[][] tt = new Object[1][1];
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from student where Sno like ? and Sname like ? and Ssex like ? and Sage like ? and Sdept like ?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+keyword[0]+"%");
            pstmt.setString(2,"%"+keyword[1]+"%");
            pstmt.setString(3,"%"+keyword[2]+"%");
            pstmt.setString(4,"%"+keyword[3]+"%");
            pstmt.setString(5,"%"+keyword[4]+"%");
            rs = pstmt.executeQuery();
            //遍历结果集rs 把结果集数据复制到studentList

            int count = 0;
            while(rs.next())
            {
                count++;
            }

            rs = pstmt.executeQuery();

            Object[][] info = new Object[count][5];

            count = 0;
            while (rs.next()){
                info[count][0] = rs.getString("Sno");
                info[count][1] = rs.getString("Sname");
                info[count][2] = rs.getString("Ssex");
                info[count][3] = rs.getString("Sage");
                info[count][4] = rs.getString("Sdept");
                count++;
            }

            return info;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return tt;
    }


    //查所有
    @Override
    public Object[][] findAllStudents() {
        Object[][] tt = new Object[1][1];

        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from student";
        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            int count = 0;
            while(rs.next())
            {
                count++;
            }

            rs = pstmt.executeQuery();

            Object[][] info = new Object[count][5];

            count = 0;
            while (rs.next()){
                info[count][0] = rs.getString("Sno");
                info[count][1] = rs.getString("Sname");
                info[count][2] = rs.getString("Ssex");
                info[count][3] = rs.getString("Sage");
                info[count][4] = rs.getString("Sdept");
                count++;
            }

            //System.out.println(count);

            return  info;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return tt;
    }

    @Override
    public Student findStudentById(String id) {
        Student s = null;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from student where Sno = ? ";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                //封装成对象
                s = new Student();
                s.setNO(rs.getString(1));
                s.setName(rs.getString(2));
                s.setSex(rs.getString(3));
                s.setage(rs.getString(4));
                s.setdept(rs.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return s;
    }


}
