package SC_DB.DAO;

import SC_DB.Object.Student;
import SC_DB.Object.Course;
import SC_DB.Object.SelectCourse;
import java.util.List;
import SC_DB.Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;

public class SCDaolmpl implements ISCDao{
    boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    // 添加 选课记录
    @Override
    public int addSC(SelectCourse selectcourse)
    {
        int row = 0;
        PreparedStatement pstnt = null;
        Connection conn = DBUtil.getConn();
        String sql = "insert into SC values (?,?,?)";
        try{
            pstnt = conn.prepareStatement(sql);
            pstnt.setString(1,selectcourse.getSno());
            pstnt.setString(2,selectcourse.getCno());
            pstnt.setString(3,selectcourse.getGrade());

            row = pstnt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstnt,null);
        }
        return row;
    }
    // 修改
    @Override
    public int modifySC(SelectCourse selectcourse)
    {
        int row = -1;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql ="update SC set Grade = ? where Sno = ? and Cno = ? ";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,selectcourse.getGrade());
            pstmt.setString(2,selectcourse.getSno());
            pstmt.setString(3,selectcourse.getCno());
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
    public int dropSC(String Sno, String Cno) throws SQLException
    {
        int row = -1;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        String sql ="delete from SC where Sno = ? and Cno = ?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,Sno);
            pstmt.setString(2,Cno);
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
    public Object[][] findSC(String[] keyword)
    {
        Object[][] tt = new Object[1][1];
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from SC where Sno like ? and Cno like ? and Grade like ?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+keyword[0]+"%");
            pstmt.setString(2,"%"+keyword[1]+"%");
            pstmt.setString(3,"%"+keyword[2]+"%");
            rs = pstmt.executeQuery();
            //遍历结果集rs 把结果集数据复制到studentList

            int count = 0;
            while(rs.next())
            {
                count++;
            }

            rs = pstmt.executeQuery();

            Object[][] info = new Object[count][3];

            count = 0;
            while (rs.next()){
                info[count][0] = rs.getString("Sno");
                info[count][1] = rs.getString("Cno");
                info[count][2] = rs.getString("Grade");
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
    public Object[][] findAllSC()
    {
        Object[][] tt = new Object[1][1];

        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from SC";
        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            int count = 0;
            while(rs.next())
            {
                count++;
            }

            rs = pstmt.executeQuery();

            Object[][] info = new Object[count][3];

            count = 0;
            while (rs.next()){
                info[count][0] = rs.getString("Sno");
                info[count][1] = rs.getString("Cno");
                info[count][2] = rs.getString("Grade");
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
    public SelectCourse findSCById(String Sno, String Cno)
    {
        SelectCourse s = null;
        Connection conn = DBUtil.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql ="select * from SC where Sno = ? and Cno = ?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,Sno);
            pstmt.setString(2,Cno);
            rs = pstmt.executeQuery();
            if (rs.next()){
                //封装成对象
                s = new SelectCourse();
                s.setSno(rs.getString(1));
                s.setCno(rs.getString(2));
                s.setGrade(rs.getString(3));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return s;
    }


}
