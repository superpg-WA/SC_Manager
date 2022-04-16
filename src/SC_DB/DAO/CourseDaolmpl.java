package SC_DB.DAO;

import SC_DB.Object.Student;
import SC_DB.Object.Course;
import SC_DB.Object.SelectCourse;
import java.util.List;
import SC_DB.Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;

public class CourseDaolmpl implements ICourseDao{

    boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    // 添加课程
    @Override
    public int addCourse(Course course)
    {
        int row = 0;
        PreparedStatement pstnt = null;
        Connection conn = DBUtil.getConn();

        // 没有先修课时
        if(isEmptyString(course.getCpno()))
        {


            if(isEmptyString(course.getCcredit()))
            {
                String sql = "insert into Course (Cno, Cname) values (?,?)";
                try {
                    pstnt = conn.prepareStatement(sql);
                    pstnt.setString(1, course.getCno());
                    pstnt.setString(2, course.getCname());

                    row = pstnt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBUtil.closeAll(conn, pstnt, null);
                }
            }
            else
            {
                String sql = "insert into Course (Cno, Cname, Ccredit) values (?,?,?)";
                try {
                    pstnt = conn.prepareStatement(sql);
                    pstnt.setString(1, course.getCno());
                    pstnt.setString(2, course.getCname());
                    pstnt.setString(3, course.getCcredit());

                    row = pstnt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBUtil.closeAll(conn, pstnt, null);
                }
            }
        }
        else {
            if(isEmptyString(course.getCcredit()))
            {
                String sql = "insert into Course (Cno, Cname, Cpno) values (?,?,?)";
                try {
                    pstnt = conn.prepareStatement(sql);
                    pstnt.setString(1, course.getCno());
                    pstnt.setString(2, course.getCname());
                    pstnt.setString(3, course.getCpno());

                    row = pstnt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBUtil.closeAll(conn, pstnt, null);
                }
            }
            else {


                String sql = "insert into Course values (?,?,?,?)";
                try {
                    pstnt = conn.prepareStatement(sql);
                    pstnt.setString(1, course.getCno());
                    pstnt.setString(2, course.getCname());
                    pstnt.setString(3, course.getCpno());
                    pstnt.setString(4, course.getCcredit());

                    row = pstnt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBUtil.closeAll(conn, pstnt, null);
                }
            }

        }
        return row;
    }
    // 修改
    @Override
    public int modifyCourse(Course course)
    {
        int row = -1;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql ="update Course set Cname = ?, Cpno = ?, Ccredit = ? where Cno = ? ";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,course.getCname());
            pstmt.setString(2,course.getCpno());
            pstmt.setString(3,course.getCcredit());
            pstmt.setString(4,course.getCno());
            row = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return row;
    }
    // 删
    @Override
    public int dropCourse(String id) throws SQLException
    {
        int row = -1;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql ="delete from course where Cno = ? ";
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
    // 查
    @Override
    public Object[][] findCourses(String[] keyword)
    {
        Object[][] tt = new Object[1][1];
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from Course where Cno like ? and Cname like ? and Cpno like ? and Ccredit like ?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+keyword[0]+"%");
            pstmt.setString(2,"%"+keyword[1]+"%");
            pstmt.setString(3,"%"+keyword[2]+"%");
            pstmt.setString(4,"%"+keyword[3]+"%");
            rs = pstmt.executeQuery();

            int count = 0;
            while(rs.next())
            {
                count++;
            }

            rs = pstmt.executeQuery();

            Object[][] info = new Object[count][4];

            count = 0;
            while (rs.next()){
                info[count][0] = rs.getString("Cno");
                info[count][1] = rs.getString("Cname");
                info[count][2] = rs.getString("Cpno");
                info[count][3] = rs.getString("Ccredit");
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
    // 查所有
    @Override
    public Object[][] findAllCourses()
    {
        Object[][] tt = new Object[1][1];

        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from Course";
        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            int count = 0;
            while(rs.next())
            {
                count++;
            }

            rs = pstmt.executeQuery();

            Object[][] info = new Object[count][4];

            count = 0;
            while (rs.next()){
                info[count][0] = rs.getString("Cno");
                info[count][1] = rs.getString("Cname");
                info[count][2] = rs.getString("Cpno");
                info[count][3] = rs.getString("Ccredit");
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
    public Course findCourseById(String id)
    {
        Course c = null;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from Course where Cno = ? ";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                //封装成对象
                c = new Course();
                c.setCno(rs.getString(1));
                c.setCname(rs.getString(2));
                c.setCpno(rs.getString(3));
                c.setCcredit(rs.getString(4));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return c;
    }

}
