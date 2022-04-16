/*
 * Created by JFormDesigner on Mon Mar 28 00:53:12 CST 2022
 */

package SC_DB;

import SC_DB.DAO.*;
import SC_DB.Object.Student;
import SC_DB.Object.Course;
import SC_DB.Object.SelectCourse;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 123
 */
public class SC_OP extends JFrame {
    // 调用Dao层的添加方法
    static IStudentDao studentDao = new StudentDaolmpl();
    static ICourseDao courseDao = new CourseDaolmpl();
    static ISCDao SCDao = new SCDaolmpl();
    // 类变量


    public SC_OP() {
        initComponents();
    }

    private void Insert_to_Student(ActionEvent e) {
        // TODO add your code here

        String Student_Insert_id = S_I_Studentid.getText();
        String Student_Insert_name = S_I_Studentname.getText();
        String Student_Insert_sex = (String)S_I_Studentsex.getSelectedItem();
        String Student_Insert_age = S_I_Studentage.getText();
        String Student_Insert_dept = S_I_Studentdept.getText();

        // 数据合法性检验
        int ff = 0;

        // 对 student 表的插入，只需要检查 NO 是否是纯数字，且长度为 11 位，即可。
        if(!isNumeric(Student_Insert_id)) ff = 1;
        if(Student_Insert_id.length() != 11) ff = 1;

        if(ff == 1)
        {
            String notes = "添加学生失败！请检查数据合法性！";

            new MyDialog(notes);
        }
        else
        {
            System.out.println(Student_Insert_id);

            Student student = new Student(Student_Insert_id, Student_Insert_name,Student_Insert_sex,Student_Insert_age,Student_Insert_dept);

            int row = studentDao.addStudent(student);
            if(row != 0)
            {
                String notes = "添加成功！";
                new MyDialog(notes);
            }
            else
            {
                String notes = "添加失败！";
                new MyDialog(notes);
            }
        }

    }

    // Student - 查询 按钮
    private void button2ActionPerformed(ActionEvent e) {
        // TODO add your code here
        String Student_Read_id = S_R_Studentid.getText();
        String Student_Read_name = S_R_Studentname.getText();
        String Student_Read_sex = (String)S_R_Studentsex.getSelectedItem();
        String Student_Read_age = S_R_Studentage.getText();
        String Student_Read_dept = S_R_Studentdept.getText();

        if(isEmptyString(Student_Read_id) && isEmptyString(Student_Read_name)
        && isEmptyString(Student_Read_sex) && isEmptyString(Student_Read_age)
        && isEmptyString(Student_Read_dept))
        {
            // 如果所有输入的关键字都是空值，表示对所有的学生进行查询。

            String title_of_table = "学生信息表";
            String []title= {"学号","姓名"," 性别","年龄","院系"};
            Object[][] info = studentDao.findAllStudents();

            new MyTable(title_of_table, title, info);

             System.out.println("All");

        }
        else
        {
            // 如果输入中有关键字不为空，则 按照关键字组合进行查询。
            String title_of_table = "学生信息表";
            String[] title= {"学号","姓名"," 性别","年龄","院系"};

            String[] ask = new String[5];
            ask[0] = Student_Read_id;
            ask[1] = Student_Read_name;
            ask[2] = Student_Read_sex;
            ask[3] = Student_Read_age;
            ask[4] = Student_Read_dept;

            Object[][] info = studentDao.findStudents(ask);

            new MyTable(title_of_table, title, info);
        }

    }

    boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    // 确认删除 按钮
    private void button3ActionPerformed(ActionEvent e) {
        // TODO add your code here

        String Student_Delete_id = S_D_Studentid.getText();

        try{
            String notes = "删除失败！无此学生！";;
            Student tmp = null;
            tmp = studentDao.findStudentById(Student_Delete_id);
            if(tmp != null)
            {
                notes = "删除成功！";
                studentDao.dropStudent(Student_Delete_id);
                new MyDialog(notes);
            }
            else
            {
                new MyDialog(notes);
            }
        }
        catch (SQLException ee) {
            ee.printStackTrace();
            String notes = "删除失败！";
            new MyDialog(notes);
        }

    }

    // 确认修改 按钮
    private void button4ActionPerformed(ActionEvent e) {
        // TODO add your code here

        String Student_Update_selected_id = Studentid.getText();
        String Student_Update_id = S_U_Studentid.getText();
        String Student_Update_name = S_U_Studentname.getText();
        String Student_Update_sex = (String)S_U_Studentsex.getSelectedItem();
        String Student_Update_age = S_U_Studentage.getText();
        String Student_Update_dept = S_U_Studentdept.getText();

        Student tmp = studentDao.findStudentById(Student_Update_selected_id);
        String notes;
        if(tmp == null)
        {
            notes = "更新失败！无此学生";
        }
        else
        {
            notes = "更新成功";

            int row = -1;
            if(!isEmptyString(Student_Update_id))
            tmp.setNO(Student_Update_id);
            if(!isEmptyString(Student_Update_name))
            tmp.setName(Student_Update_name);
            if(!isEmptyString(Student_Update_sex))
            tmp.setSex(Student_Update_sex);
            if(!isEmptyString(Student_Update_age))
            tmp.setage(Student_Update_age);
            if(!isEmptyString(Student_Update_dept))
            tmp.setdept(Student_Update_dept);
            row = studentDao.modifyStudent(tmp);
            if(row == -1)
            {
                notes = "更新失败！";
            }
        }

        new MyDialog(notes);
    }

    // Course 表 中插入按钮
    private void button5ActionPerformed(ActionEvent e) {
        // TODO add your code here
        String Course_Insert_cno = C_I_Cno.getText();
        String Course_Insert_cname = C_I_Cname.getText();
        String Course_Insert_cpno = C_I_Cpno.getText();
        String Course_Insert_ccredit = C_I_Ccredit.getText();

        Course course = new Course(Course_Insert_cno, Course_Insert_cname,Course_Insert_cpno,Course_Insert_ccredit);

        int row = courseDao.addCourse(course);
        if(row != 0)
        {
            String notes = "添加成功！";
            new MyDialog(notes);
        }
        else {
            String notes = "添加失败！";
            new MyDialog(notes);
        }
    }

    // Course 表 中删除按钮
    private void button6ActionPerformed(ActionEvent e) {
        // TODO add your code here
        String Course_Delete_cno = C_D_Cno.getText();

        try{
            String notes = "删除失败！无此课程！";;
            Course tmp = null;
            tmp = courseDao.findCourseById(Course_Delete_cno);
            if(tmp != null)
            {
                notes = "删除成功！";
                courseDao.dropCourse(Course_Delete_cno);
                new MyDialog(notes);
            }
            else
            {
                new MyDialog(notes);
            }
        }
        catch (SQLException ee) {
            ee.printStackTrace();
            String notes = "删除失败！";
            new MyDialog(notes);
        }
    }

    // Course 表 的查询按钮
    private void button7ActionPerformed(ActionEvent e) {
        // TODO add your code here
        String Course_Read_cno = C_R_Cno.getText();
        String Course_Read_cname = C_R_Cname.getText();
        String Course_Read_cpno = C_R_Cpno.getText();
        String Course_Read_ccredit = C_R_Ccredit.getText();

        if(isEmptyString(Course_Read_cno) && isEmptyString(Course_Read_cname)
                && isEmptyString(Course_Read_cpno) && isEmptyString(Course_Read_ccredit))
        {
            // 如果所有输入的关键字都是空值，表示对所有的学生进行查询。

            String title_of_table = "课程信息表";
            String []title= {"课程号","课程名"," 先修课编号","学分"};
            Object[][] info = courseDao.findAllCourses();

            new MyTable(title_of_table, title, info);

            System.out.println("All");

        }
        else
        {
            // 如果输入中有关键字不为空，则 按照关键字组合进行查询。
            String title_of_table = "课程信息表";
            String []title= {"课程号","课程名"," 先修课编号","学分"};

            String[] ask = new String[4];
            ask[0] = Course_Read_cno;
            ask[1] = Course_Read_cname;
            ask[2] = Course_Read_cpno;
            ask[3] = Course_Read_ccredit;

            Object[][] info = courseDao.findCourses(ask);

            new MyTable(title_of_table, title, info);
        }
    }

    // Course 表 的修改按钮
    private void button8ActionPerformed(ActionEvent e) {
        // TODO add your code here

        String Course_Update_selected_id = Course_cno.getText();
        String Course_Update_cno = C_U_Cno.getText();
        String Course_Update_cname = C_U_Cname.getText();
        String Course_Update_cpno = C_U_Cpno.getText();
        String Course_Update_ccredit = C_U_Ccredit.getText();

        Course tmp = courseDao.findCourseById(Course_Update_selected_id);
        String notes;
        if(tmp == null)
        {
            notes = "更新失败！无此课程";
        }
        else
        {
            notes = "更新成功";

            int row = -1;
            if(!isEmptyString(Course_Update_cno))
                tmp.setCno(Course_Update_cno);
            if(!isEmptyString(Course_Update_cname))
                tmp.setCname(Course_Update_cname);
            if(!isEmptyString(Course_Update_cpno))
                tmp.setCpno(Course_Update_cpno);
            if(!isEmptyString(Course_Update_ccredit))
                tmp.setCcredit(Course_Update_ccredit);
            row = courseDao.modifyCourse(tmp);
            if(row == -1)
            {
                notes = "更新失败！";
            }
        }

        new MyDialog(notes);

    }

    // SC 表 的删除按钮
    private void button10ActionPerformed(ActionEvent e) {
        // TODO add your code here

        String SelectCourse_Delete_Sno = SC_D_Sno.getText();
        String SelectCourse_Delete_Cno = SC_D_Cno.getText();

        try{
            String notes = "删除失败！无此选课记录！";;
            SelectCourse tmp = null;
            tmp = SCDao.findSCById(SelectCourse_Delete_Sno, SelectCourse_Delete_Cno);
            if(tmp != null)
            {
                notes = "删除成功！";
                SCDao.dropSC(SelectCourse_Delete_Sno, SelectCourse_Delete_Cno);
                new MyDialog(notes);
            }
            else
            {
                new MyDialog(notes);
            }
        }
        catch (SQLException ee) {
            ee.printStackTrace();
            String notes = "删除失败！";
            new MyDialog(notes);
        }

    }

    // SC 表 修改按钮
    private void button11ActionPerformed(ActionEvent e) {
        // TODO add your code here
        String SelectCourse_Update_sno = SC_U_Sno.getText();
        String SelectCourse_Update_cno = SC_U_Cno.getText();
        String SelectCourse_Update_grade = SC_U_Grade.getText();

        SelectCourse tmp = SCDao.findSCById(SelectCourse_Update_sno, SelectCourse_Update_cno);
        String notes;
        if(tmp == null)
        {
            notes = "更新失败！无此选课记录！";
        }
        else
        {
            notes = "更新成功";

            int row = -1;
            if(!isEmptyString(SelectCourse_Update_sno))
                tmp.setSno(SelectCourse_Update_sno);
            if(!isEmptyString(SelectCourse_Update_cno))
                tmp.setCno(SelectCourse_Update_cno);
            if(!isEmptyString(SelectCourse_Update_grade))
                tmp.setGrade(SelectCourse_Update_grade);
            row = SCDao.modifySC(tmp);
            if(row == -1)
            {
                notes = "更新失败！";
            }
        }

        new MyDialog(notes);

    }

    // SC 表 查询表
    private void button12ActionPerformed(ActionEvent e) {
        // TODO add your code here

        String SelectCourse_Read_Sno = SC_R_Sno.getText();
        String SelectCourse_Read_Cno = SC_R_Cnp.getText();
        String SelectCourse_Read_Grade = SC_R_Grade.getText();

        if(isEmptyString(SelectCourse_Read_Sno) && isEmptyString(SelectCourse_Read_Cno)
                && isEmptyString(SelectCourse_Read_Grade))
        {
            // 如果所有输入的关键字都是空值，表示对所有的学生进行查询。

            String title_of_table = "选课信息表";
            String []title= {"学号","课程号"," 成绩"};
            Object[][] info = SCDao.findAllSC();

            new MyTable(title_of_table, title, info);

            System.out.println("All");

        }
        else
        {
            // 如果输入中有关键字不为空，则 按照关键字组合进行查询。
            String title_of_table = "选课信息表";
            String []title= {"学号","课程号"," 成绩"};

            String[] ask = new String[3];
            ask[0] = SelectCourse_Read_Sno;
            ask[1] = SelectCourse_Read_Cno;
            ask[2] = SelectCourse_Read_Grade;

            Object[][] info = SCDao.findSC(ask);

            new MyTable(title_of_table, title, info);
        }


    }

    // SC 表 增加按钮
    private void button9ActionPerformed(ActionEvent e) {
        // TODO add your code here

        // 某个课程 和 某个学上在另一个表中是否存在？
        String SelectCourse_Insert_Sno = SC_I_Sno.getText();
        String SelectCourse_Insert_Cno = SC_I_Cno.getText();
        String SelectCourse_Insert_Grade = SC_I_Grade.getText();

        Student s1 = null;
        s1 = studentDao.findStudentById(SelectCourse_Insert_Sno);

        Course c1 = null;
        c1 = courseDao.findCourseById(SelectCourse_Insert_Cno);

        if(s1 == null || c1 == null)
        {
            String notes = "输入数据有误！请检查必填项目！";
            new MyDialog(notes);
        }
        else
        {
            SelectCourse SC = new SelectCourse(SelectCourse_Insert_Sno, SelectCourse_Insert_Cno,SelectCourse_Insert_Grade);

            int row = SCDao.addSC(SC);
            if(row != 0)
            {
                String notes = "添加成功！";
                new MyDialog(notes);
            }
            else {
                String notes = "添加失败！";
                new MyDialog(notes);
            }
        }


    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        panel9 = new JPanel();
        panel12 = new JPanel();
        panel13 = new JPanel();
        panel15 = new JPanel();
        panel17 = new JPanel();
        tabbedPane2 = new JTabbedPane();
        panel19 = new JPanel();
        panel6 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        button3 = new JButton();
        panel14 = new JPanel();
        panel16 = new JPanel();
        label12 = new JLabel();
        S_D_Studentid = new JTextField();
        panel20 = new JPanel();
        panel18 = new JPanel();
        panel28 = new JPanel();
        panel29 = new JPanel();
        panel32 = new JPanel();
        panel33 = new JPanel();
        panel34 = new JPanel();
        panel35 = new JPanel();
        label19 = new JLabel();
        button4 = new JButton();
        panel30 = new JPanel();
        panel31 = new JPanel();
        label13 = new JLabel();
        Studentid = new JTextField();
        label14 = new JLabel();
        S_U_Studentid = new JTextField();
        label15 = new JLabel();
        S_U_Studentname = new JTextField();
        label16 = new JLabel();
        S_U_Studentsex = new JComboBox<>();
        label17 = new JLabel();
        S_U_Studentage = new JTextField();
        label18 = new JLabel();
        S_U_Studentdept = new JTextField();
        panel21 = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        label11 = new JLabel();
        button2 = new JButton();
        panel4 = new JPanel();
        panel5 = new JPanel();
        label1 = new JLabel();
        S_R_Studentid = new JTextField();
        label2 = new JLabel();
        S_R_Studentname = new JTextField();
        label5 = new JLabel();
        S_R_Studentsex = new JComboBox<>();
        label6 = new JLabel();
        S_R_Studentage = new JTextField();
        label10 = new JLabel();
        S_R_Studentdept = new JTextField();
        panel22 = new JPanel();
        panel23 = new JPanel();
        panel24 = new JPanel();
        panel25 = new JPanel();
        button1 = new JButton();
        panel26 = new JPanel();
        panel27 = new JPanel();
        label3 = new JLabel();
        S_I_Studentid = new JTextField();
        label4 = new JLabel();
        S_I_Studentname = new JTextField();
        label7 = new JLabel();
        S_I_Studentsex = new JComboBox<>();
        label8 = new JLabel();
        S_I_Studentage = new JTextField();
        label9 = new JLabel();
        S_I_Studentdept = new JTextField();
        panel10 = new JPanel();
        panel36 = new JPanel();
        panel37 = new JPanel();
        panel38 = new JPanel();
        panel39 = new JPanel();
        tabbedPane3 = new JTabbedPane();
        panel40 = new JPanel();
        panel47 = new JPanel();
        panel48 = new JPanel();
        button6 = new JButton();
        panel49 = new JPanel();
        panel53 = new JPanel();
        panel54 = new JPanel();
        label27 = new JLabel();
        C_D_Cno = new JTextField();
        panel41 = new JPanel();
        panel60 = new JPanel();
        panel61 = new JPanel();
        panel62 = new JPanel();
        button8 = new JButton();
        label47 = new JLabel();
        panel63 = new JPanel();
        panel64 = new JPanel();
        label33 = new JLabel();
        Course_cno = new JTextField();
        label34 = new JLabel();
        C_U_Cno = new JTextField();
        label35 = new JLabel();
        C_U_Cname = new JTextField();
        label36 = new JLabel();
        C_U_Cpno = new JTextField();
        label37 = new JLabel();
        C_U_Ccredit = new JTextField();
        panel42 = new JPanel();
        panel55 = new JPanel();
        panel56 = new JPanel();
        panel57 = new JPanel();
        label32 = new JLabel();
        button7 = new JButton();
        panel58 = new JPanel();
        panel59 = new JPanel();
        label28 = new JLabel();
        C_R_Cno = new JTextField();
        label29 = new JLabel();
        C_R_Cname = new JTextField();
        label30 = new JLabel();
        C_R_Cpno = new JTextField();
        label31 = new JLabel();
        C_R_Ccredit = new JTextField();
        panel43 = new JPanel();
        panel44 = new JPanel();
        panel45 = new JPanel();
        button5 = new JButton();
        panel50 = new JPanel();
        panel51 = new JPanel();
        panel52 = new JPanel();
        label23 = new JLabel();
        C_I_Cno = new JTextField();
        label24 = new JLabel();
        C_I_Cname = new JTextField();
        label25 = new JLabel();
        C_I_Cpno = new JTextField();
        label26 = new JLabel();
        C_I_Ccredit = new JTextField();
        panel11 = new JPanel();
        panel74 = new JPanel();
        panel75 = new JPanel();
        panel76 = new JPanel();
        tabbedPane4 = new JTabbedPane();
        panel78 = new JPanel();
        panel86 = new JPanel();
        panel87 = new JPanel();
        panel88 = new JPanel();
        panel89 = new JPanel();
        button10 = new JButton();
        panel90 = new JPanel();
        label38 = new JLabel();
        SC_D_Sno = new JTextField();
        label39 = new JLabel();
        SC_D_Cno = new JTextField();
        panel91 = new JPanel();
        panel92 = new JPanel();
        panel93 = new JPanel();
        panel94 = new JPanel();
        button11 = new JButton();
        label48 = new JLabel();
        panel95 = new JPanel();
        panel96 = new JPanel();
        label40 = new JLabel();
        SC_U_Sno = new JTextField();
        label41 = new JLabel();
        SC_U_Cno = new JTextField();
        label42 = new JLabel();
        SC_U_Grade = new JTextField();
        panel79 = new JPanel();
        panel97 = new JPanel();
        panel98 = new JPanel();
        panel99 = new JPanel();
        label46 = new JLabel();
        button12 = new JButton();
        panel100 = new JPanel();
        panel101 = new JPanel();
        label43 = new JLabel();
        SC_R_Sno = new JTextField();
        label44 = new JLabel();
        SC_R_Cnp = new JTextField();
        label45 = new JLabel();
        SC_R_Grade = new JTextField();
        panel80 = new JPanel();
        panel81 = new JPanel();
        panel82 = new JPanel();
        panel83 = new JPanel();
        button9 = new JButton();
        panel84 = new JPanel();
        panel85 = new JPanel();
        label20 = new JLabel();
        SC_I_Sno = new JTextField();
        label21 = new JLabel();
        SC_I_Cno = new JTextField();
        label22 = new JLabel();
        SC_I_Grade = new JTextField();

        //======== this ========
        setTitle("StudentCourse_Manager");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabbedPane1 ========
        {

            //======== panel9 ========
            {
                panel9.setLayout(new GridLayout());

                //======== panel12 ========
                {
                    panel12.setLayout(new BorderLayout(0, 3));

                    //======== panel13 ========
                    {
                        panel13.setLayout(new BorderLayout());
                    }
                    panel12.add(panel13, BorderLayout.NORTH);

                    //======== panel15 ========
                    {
                        panel15.setLayout(new BorderLayout());
                    }
                    panel12.add(panel15, BorderLayout.SOUTH);

                    //======== panel17 ========
                    {
                        panel17.setLayout(new BorderLayout());

                        //======== tabbedPane2 ========
                        {

                            //======== panel19 ========
                            {
                                panel19.setLayout(new BorderLayout());

                                //======== panel6 ========
                                {
                                    panel6.setLayout(new BorderLayout());
                                }
                                panel19.add(panel6, BorderLayout.NORTH);

                                //======== panel7 ========
                                {
                                    panel7.setLayout(new BorderLayout());
                                }
                                panel19.add(panel7, BorderLayout.WEST);

                                //======== panel8 ========
                                {
                                    panel8.setLayout(new BorderLayout());

                                    //---- button3 ----
                                    button3.setText("\u786e\u8ba4\u5220\u9664");
                                    button3.addActionListener(e -> button3ActionPerformed(e));
                                    panel8.add(button3, BorderLayout.CENTER);
                                }
                                panel19.add(panel8, BorderLayout.SOUTH);

                                //======== panel14 ========
                                {
                                    panel14.setLayout(new BorderLayout());
                                }
                                panel19.add(panel14, BorderLayout.EAST);

                                //======== panel16 ========
                                {
                                    panel16.setLayout(new GridLayout(1, 2, 10, 10));

                                    //---- label12 ----
                                    label12.setText("\u8bf7\u8f93\u5165\u60a8\u60f3\u8981\u5220\u9664\u7684\u5b66\u751f\u5b66\u53f7\uff1a\uff08\u4ec5\u53ef\u901a\u8fc7\u5b66\u53f7\u5220\u9664\uff0c11\u4f4d\uff0c\u5fc5\u586b\uff09");
                                    panel16.add(label12);
                                    panel16.add(S_D_Studentid);
                                }
                                panel19.add(panel16, BorderLayout.CENTER);
                            }
                            tabbedPane2.addTab("\u5220\u9664", panel19);

                            //======== panel20 ========
                            {
                                panel20.setLayout(new BorderLayout());

                                //======== panel18 ========
                                {
                                    panel18.setLayout(new BorderLayout());
                                }
                                panel20.add(panel18, BorderLayout.NORTH);

                                //======== panel28 ========
                                {
                                    panel28.setLayout(new BorderLayout());
                                }
                                panel20.add(panel28, BorderLayout.WEST);

                                //======== panel29 ========
                                {
                                    panel29.setLayout(new BorderLayout());

                                    //======== panel32 ========
                                    {
                                        panel32.setLayout(new BorderLayout());

                                        //======== panel33 ========
                                        {
                                            panel33.setLayout(new BorderLayout());
                                        }
                                        panel32.add(panel33, BorderLayout.WEST);

                                        //======== panel34 ========
                                        {
                                            panel34.setLayout(new BorderLayout());
                                        }
                                        panel32.add(panel34, BorderLayout.EAST);

                                        //======== panel35 ========
                                        {
                                            panel35.setLayout(new BorderLayout());

                                            //---- label19 ----
                                            label19.setText("Tips\uff1a\u8bf7\u5728\u7b2c\u4e00\u884c\u8f93\u5165\u60f3\u8981\u4fee\u6539\u7684\u5b66\u751f\u5b66\u53f7\uff0c\u5e76\u5728\u540e\u97625\u884c\u586b\u5199\u60f3\u8981\u5c06\u8be5\u751f\u7684\u6539\u884c\u4fe1\u606f\u4fee\u6539\u4e3a\u4f55\u503c\u3002\uff08\u6682\u4e0d\u652f\u6301\u6279\u91cf\u4fee\u6539\uff09");
                                            panel35.add(label19, BorderLayout.CENTER);

                                            //---- button4 ----
                                            button4.setText("\u786e\u8ba4\u4fee\u6539");
                                            button4.addActionListener(e -> button4ActionPerformed(e));
                                            panel35.add(button4, BorderLayout.NORTH);
                                        }
                                        panel32.add(panel35, BorderLayout.CENTER);
                                    }
                                    panel29.add(panel32, BorderLayout.CENTER);
                                }
                                panel20.add(panel29, BorderLayout.SOUTH);

                                //======== panel30 ========
                                {
                                    panel30.setLayout(new BorderLayout());
                                }
                                panel20.add(panel30, BorderLayout.EAST);

                                //======== panel31 ========
                                {
                                    panel31.setLayout(new GridLayout(6, 2, 10, 10));

                                    //---- label13 ----
                                    label13.setText("\u8bf7\u8f93\u5165\u60a8\u9700\u8981\u4fee\u6539\u7684\u5b66\u751f\u5b66\u53f7\uff1a\uff08\u4ec5\u53ef\u901a\u8fc7\u5b66\u53f7\u786e\u5b9a\uff0c11\u4f4d\uff0c\u5fc5\u586b\uff09");
                                    panel31.add(label13);
                                    panel31.add(Studentid);

                                    //---- label14 ----
                                    label14.setText("\u5b66\u751f\u5b66\u53f7\uff08\u9009\u586b\uff09");
                                    panel31.add(label14);
                                    panel31.add(S_U_Studentid);

                                    //---- label15 ----
                                    label15.setText("\u5b66\u751f\u59d3\u540d\uff08\u9009\u586b\uff09");
                                    panel31.add(label15);
                                    panel31.add(S_U_Studentname);

                                    //---- label16 ----
                                    label16.setText("\u5b66\u751f\u6027\u522b\uff08\u9009\u586b\uff09");
                                    panel31.add(label16);

                                    //---- S_U_Studentsex ----
                                    S_U_Studentsex.setModel(new DefaultComboBoxModel<>(new String[] {
                                        "\u7537",
                                        "\u5973"
                                    }));
                                    S_U_Studentsex.setSelectedIndex(-1);
                                    panel31.add(S_U_Studentsex);

                                    //---- label17 ----
                                    label17.setText("\u5b66\u751f\u5e74\u9f84\uff08\u9009\u586b\uff09");
                                    panel31.add(label17);
                                    panel31.add(S_U_Studentage);

                                    //---- label18 ----
                                    label18.setText("\u5b66\u751f\u9662\u7cfb\uff08\u9009\u586b\uff09");
                                    panel31.add(label18);
                                    panel31.add(S_U_Studentdept);
                                }
                                panel20.add(panel31, BorderLayout.CENTER);
                            }
                            tabbedPane2.addTab("\u4fee\u6539", panel20);

                            //======== panel21 ========
                            {
                                panel21.setLayout(new BorderLayout());

                                //======== panel1 ========
                                {
                                    panel1.setLayout(new BorderLayout());
                                }
                                panel21.add(panel1, BorderLayout.NORTH);

                                //======== panel2 ========
                                {
                                    panel2.setLayout(new BorderLayout());
                                }
                                panel21.add(panel2, BorderLayout.WEST);

                                //======== panel3 ========
                                {
                                    panel3.setLayout(new BorderLayout());

                                    //---- label11 ----
                                    label11.setText("Tips\uff1a \u5982\u679c\u60f3\u8981\u67e5\u8be2\u6240\u6709\u5b66\u751f\u7684\u4fe1\u606f\uff0c\u8bf7\u4e0d\u8981\u586b\u5199\u4efb\u4f55\u67e5\u627e\u5173\u952e\u5b57\uff0c\u76f4\u63a5\u70b9\u51fb\u201c\u67e5\u8be2\u201d\u6309\u94ae\u5373\u53ef\u3002");
                                    panel3.add(label11, BorderLayout.CENTER);

                                    //---- button2 ----
                                    button2.setText("\u67e5\u8be2");
                                    button2.addActionListener(e -> button2ActionPerformed(e));
                                    panel3.add(button2, BorderLayout.NORTH);
                                }
                                panel21.add(panel3, BorderLayout.SOUTH);

                                //======== panel4 ========
                                {
                                    panel4.setLayout(new BorderLayout());
                                }
                                panel21.add(panel4, BorderLayout.EAST);

                                //======== panel5 ========
                                {
                                    panel5.setLayout(new GridLayout(5, 2, 10, 10));

                                    //---- label1 ----
                                    label1.setText("\u5b66\u751f\u5b66\u53f7\uff08\u9009\u586b\uff09");
                                    panel5.add(label1);
                                    panel5.add(S_R_Studentid);

                                    //---- label2 ----
                                    label2.setText("\u5b66\u751f\u59d3\u540d\uff08\u9009\u586b\uff09");
                                    panel5.add(label2);
                                    panel5.add(S_R_Studentname);

                                    //---- label5 ----
                                    label5.setText("\u5b66\u751f\u6027\u522b\uff08\u9009\u586b\uff09");
                                    panel5.add(label5);

                                    //---- S_R_Studentsex ----
                                    S_R_Studentsex.setModel(new DefaultComboBoxModel<>(new String[] {
                                        "\u7537",
                                        "\u5973"
                                    }));
                                    S_R_Studentsex.setSelectedIndex(-1);
                                    panel5.add(S_R_Studentsex);

                                    //---- label6 ----
                                    label6.setText("\u5b66\u751f\u5e74\u9f84\uff08\u9009\u586b\uff09");
                                    panel5.add(label6);
                                    panel5.add(S_R_Studentage);

                                    //---- label10 ----
                                    label10.setText("\u5b66\u751f\u9662\u7cfb\uff08\u9009\u586b\uff09");
                                    panel5.add(label10);
                                    panel5.add(S_R_Studentdept);
                                }
                                panel21.add(panel5, BorderLayout.CENTER);
                            }
                            tabbedPane2.addTab("\u67e5\u8be2", panel21);

                            //======== panel22 ========
                            {
                                panel22.setLayout(new BorderLayout());

                                //======== panel23 ========
                                {
                                    panel23.setLayout(new BorderLayout());
                                }
                                panel22.add(panel23, BorderLayout.NORTH);

                                //======== panel24 ========
                                {
                                    panel24.setLayout(new BorderLayout());
                                }
                                panel22.add(panel24, BorderLayout.WEST);

                                //======== panel25 ========
                                {
                                    panel25.setLayout(new BorderLayout(0, 10));

                                    //---- button1 ----
                                    button1.setText("\u63d2\u5165\u6570\u636e");
                                    button1.addActionListener(e -> Insert_to_Student(e));
                                    panel25.add(button1, BorderLayout.CENTER);
                                }
                                panel22.add(panel25, BorderLayout.SOUTH);

                                //======== panel26 ========
                                {
                                    panel26.setLayout(new BorderLayout());
                                }
                                panel22.add(panel26, BorderLayout.EAST);

                                //======== panel27 ========
                                {
                                    panel27.setLayout(new GridLayout(5, 2, 10, 10));

                                    //---- label3 ----
                                    label3.setText("\u5b66\u751f\u5b66\u53f7\uff0811\u4f4d\u5fc5\u586b\uff0c\u6570\u5b57\uff09");
                                    panel27.add(label3);
                                    panel27.add(S_I_Studentid);

                                    //---- label4 ----
                                    label4.setText("\u5b66\u751f\u59d3\u540d\uff08\u9009\u586b\uff0c\u6c49\u5b57\uff09");
                                    panel27.add(label4);
                                    panel27.add(S_I_Studentname);

                                    //---- label7 ----
                                    label7.setText("\u5b66\u751f\u6027\u522b\uff08\u9009\u62e9\uff09");
                                    panel27.add(label7);

                                    //---- S_I_Studentsex ----
                                    S_I_Studentsex.setModel(new DefaultComboBoxModel<>(new String[] {
                                        "\u7537",
                                        "\u5973"
                                    }));
                                    S_I_Studentsex.setSelectedIndex(-1);
                                    panel27.add(S_I_Studentsex);

                                    //---- label8 ----
                                    label8.setText("\u5b66\u751f\u5e74\u9f84\uff08\u9009\u586b\uff0c\u6570\u5b57\uff09");
                                    panel27.add(label8);
                                    panel27.add(S_I_Studentage);

                                    //---- label9 ----
                                    label9.setText("\u5b66\u751f\u9662\u7cfb\uff08\u9009\u586b\uff09");
                                    panel27.add(label9);
                                    panel27.add(S_I_Studentdept);
                                }
                                panel22.add(panel27, BorderLayout.CENTER);
                            }
                            tabbedPane2.addTab("\u589e\u52a0", panel22);
                        }
                        panel17.add(tabbedPane2, BorderLayout.CENTER);
                    }
                    panel12.add(panel17, BorderLayout.CENTER);
                }
                panel9.add(panel12);
            }
            tabbedPane1.addTab("\u5b66\u751f\u8868", panel9);

            //======== panel10 ========
            {
                panel10.setLayout(new GridLayout());

                //======== panel36 ========
                {
                    panel36.setLayout(new BorderLayout());

                    //======== panel37 ========
                    {
                        panel37.setLayout(new BorderLayout());
                    }
                    panel36.add(panel37, BorderLayout.NORTH);

                    //======== panel38 ========
                    {
                        panel38.setLayout(new BorderLayout());
                    }
                    panel36.add(panel38, BorderLayout.SOUTH);

                    //======== panel39 ========
                    {
                        panel39.setLayout(new BorderLayout());

                        //======== tabbedPane3 ========
                        {

                            //======== panel40 ========
                            {
                                panel40.setLayout(new BorderLayout());

                                //======== panel47 ========
                                {
                                    panel47.setLayout(new GridLayout());
                                }
                                panel40.add(panel47, BorderLayout.NORTH);

                                //======== panel48 ========
                                {
                                    panel48.setLayout(new GridLayout());

                                    //---- button6 ----
                                    button6.setText("\u786e\u8ba4\u5220\u9664");
                                    panel48.add(button6);
                                }
                                panel40.add(panel48, BorderLayout.SOUTH);

                                //======== panel49 ========
                                {
                                    panel49.setLayout(new GridLayout());
                                }
                                panel40.add(panel49, BorderLayout.WEST);

                                //======== panel53 ========
                                {
                                    panel53.setLayout(new GridLayout());
                                }
                                panel40.add(panel53, BorderLayout.EAST);

                                //======== panel54 ========
                                {
                                    panel54.setLayout(new GridLayout(1, 2, 10, 10));

                                    //---- label27 ----
                                    label27.setText("\u8bf7\u8f93\u5165\u60a8\u60f3\u8981\u5220\u9664\u7684\u8bfe\u7a0b\u7f16\u53f7\uff1a\uff08\u4ec5\u53ef\u901a\u8fc7\u8bfe\u7a0b\u53f7\u5220\u9664\uff0c\u5fc5\u586b\uff09");
                                    panel54.add(label27);
                                    panel54.add(C_D_Cno);
                                }
                                panel40.add(panel54, BorderLayout.CENTER);
                            }
                            tabbedPane3.addTab("\u5220\u9664", panel40);

                            //======== panel41 ========
                            {
                                panel41.setLayout(new BorderLayout());

                                //======== panel60 ========
                                {
                                    panel60.setLayout(new BorderLayout());
                                }
                                panel41.add(panel60, BorderLayout.NORTH);

                                //======== panel61 ========
                                {
                                    panel61.setLayout(new BorderLayout());
                                }
                                panel41.add(panel61, BorderLayout.WEST);

                                //======== panel62 ========
                                {
                                    panel62.setLayout(new BorderLayout(5, 5));

                                    //---- button8 ----
                                    button8.setText("\u786e\u8ba4\u4fee\u6539");
                                    panel62.add(button8, BorderLayout.CENTER);

                                    //---- label47 ----
                                    label47.setText("Tips\uff1a\u8bf7\u5728\u7b2c\u4e00\u884c\u8f93\u5165\u60f3\u8981\u4fee\u6539\u7684\u8bfe\u7a0b\u7f16\u53f7\uff0c\u5e76\u5728\u540e\u97624\u884c\u586b\u5199\u60f3\u8981\u5c06\u8be5\u751f\u7684\u6539\u884c\u4fe1\u606f\u4fee\u6539\u4e3a\u4f55\u503c\u3002\uff08\u6682\u4e0d\u652f\u6301\u6279\u91cf\u4fee\u6539\uff09");
                                    panel62.add(label47, BorderLayout.SOUTH);
                                }
                                panel41.add(panel62, BorderLayout.SOUTH);

                                //======== panel63 ========
                                {
                                    panel63.setLayout(new BorderLayout());
                                }
                                panel41.add(panel63, BorderLayout.EAST);

                                //======== panel64 ========
                                {
                                    panel64.setLayout(new GridLayout(5, 2, 10, 10));

                                    //---- label33 ----
                                    label33.setText("\u8bf7\u8f93\u5165\u60a8\u9700\u8981\u4fee\u6539\u7684\u8bfe\u7a0b\u7f16\u53f7\uff1a\uff08\u4ec5\u53ef\u901a\u8fc7\u8bfe\u7a0b\u53f7\u786e\u5b9a\uff0c\u5fc5\u586b\uff09");
                                    panel64.add(label33);
                                    panel64.add(Course_cno);

                                    //---- label34 ----
                                    label34.setText("\u8bfe\u7a0b\u7f16\u53f7\uff08\u9009\u586b\uff09");
                                    panel64.add(label34);
                                    panel64.add(C_U_Cno);

                                    //---- label35 ----
                                    label35.setText("\u8bfe\u7a0b\u540d\uff08\u9009\u586b\uff09");
                                    panel64.add(label35);
                                    panel64.add(C_U_Cname);

                                    //---- label36 ----
                                    label36.setText("\u8bfe\u7a0b\u5148\u4fee\u8bfe\uff08\u9009\u586b\uff09");
                                    panel64.add(label36);
                                    panel64.add(C_U_Cpno);

                                    //---- label37 ----
                                    label37.setText("\u5b66\u5206\uff08\u9009\u586b\uff09");
                                    panel64.add(label37);
                                    panel64.add(C_U_Ccredit);
                                }
                                panel41.add(panel64, BorderLayout.CENTER);
                            }
                            tabbedPane3.addTab("\u4fee\u6539", panel41);

                            //======== panel42 ========
                            {
                                panel42.setLayout(new BorderLayout());

                                //======== panel55 ========
                                {
                                    panel55.setLayout(new BorderLayout());
                                }
                                panel42.add(panel55, BorderLayout.NORTH);

                                //======== panel56 ========
                                {
                                    panel56.setLayout(new BorderLayout());
                                }
                                panel42.add(panel56, BorderLayout.WEST);

                                //======== panel57 ========
                                {
                                    panel57.setLayout(new BorderLayout());

                                    //---- label32 ----
                                    label32.setText("Tips\uff1a \u5982\u679c\u60f3\u8981\u67e5\u8be2\u6240\u6709\u8bfe\u7a0b\u7684\u4fe1\u606f\uff0c\u8bf7\u4e0d\u8981\u586b\u5199\u4efb\u4f55\u67e5\u627e\u5173\u952e\u5b57\uff0c\u76f4\u63a5\u70b9\u51fb\u201c\u67e5\u8be2\u201d\u6309\u94ae\u5373\u53ef\u3002");
                                    panel57.add(label32, BorderLayout.SOUTH);

                                    //---- button7 ----
                                    button7.setText("\u67e5\u8be2");
                                    panel57.add(button7, BorderLayout.CENTER);
                                }
                                panel42.add(panel57, BorderLayout.SOUTH);

                                //======== panel58 ========
                                {
                                    panel58.setLayout(new BorderLayout());
                                }
                                panel42.add(panel58, BorderLayout.EAST);

                                //======== panel59 ========
                                {
                                    panel59.setLayout(new GridLayout(4, 2, 10, 10));

                                    //---- label28 ----
                                    label28.setText("\u8bfe\u7a0b\u53f7\uff08\u9009\u586b\uff09");
                                    panel59.add(label28);
                                    panel59.add(C_R_Cno);

                                    //---- label29 ----
                                    label29.setText("\u8bfe\u7a0b\u540d\u79f0\uff08\u9009\u586b\uff09");
                                    panel59.add(label29);
                                    panel59.add(C_R_Cname);

                                    //---- label30 ----
                                    label30.setText("\u5148\u4fee\u8bfe\u7f16\u53f7\uff08\u9009\u586b\uff09");
                                    panel59.add(label30);
                                    panel59.add(C_R_Cpno);

                                    //---- label31 ----
                                    label31.setText("\u5b66\u5206\uff08\u9009\u586b\uff09");
                                    panel59.add(label31);
                                    panel59.add(C_R_Ccredit);
                                }
                                panel42.add(panel59, BorderLayout.CENTER);
                            }
                            tabbedPane3.addTab("\u67e5\u8be2", panel42);

                            //======== panel43 ========
                            {
                                panel43.setLayout(new BorderLayout());

                                //======== panel44 ========
                                {
                                    panel44.setLayout(new BorderLayout());
                                }
                                panel43.add(panel44, BorderLayout.NORTH);

                                //======== panel45 ========
                                {
                                    panel45.setLayout(new BorderLayout());

                                    //---- button5 ----
                                    button5.setText("\u786e\u8ba4\u63d2\u5165");
                                    panel45.add(button5, BorderLayout.CENTER);
                                }
                                panel43.add(panel45, BorderLayout.SOUTH);

                                //======== panel50 ========
                                {
                                    panel50.setLayout(new GridLayout());
                                }
                                panel43.add(panel50, BorderLayout.WEST);

                                //======== panel51 ========
                                {
                                    panel51.setLayout(new GridLayout());
                                }
                                panel43.add(panel51, BorderLayout.EAST);

                                //======== panel52 ========
                                {
                                    panel52.setLayout(new GridLayout(4, 2, 10, 10));

                                    //---- label23 ----
                                    label23.setText("\u8bfe\u7a0b\u53f7\uff08\u5fc5\u586b\uff09");
                                    panel52.add(label23);
                                    panel52.add(C_I_Cno);

                                    //---- label24 ----
                                    label24.setText("\u8bfe\u7a0b\u540d\u79f0\uff08\u9009\u586b\uff09");
                                    panel52.add(label24);
                                    panel52.add(C_I_Cname);

                                    //---- label25 ----
                                    label25.setText("\u5148\u4fee\u8bfe\u7f16\u53f7\uff08\u9009\u586b\uff09");
                                    panel52.add(label25);
                                    panel52.add(C_I_Cpno);

                                    //---- label26 ----
                                    label26.setText("\u5b66\u5206");
                                    panel52.add(label26);
                                    panel52.add(C_I_Ccredit);
                                }
                                panel43.add(panel52, BorderLayout.CENTER);
                            }
                            tabbedPane3.addTab("\u589e\u52a0", panel43);
                        }
                        panel39.add(tabbedPane3, BorderLayout.CENTER);
                    }
                    panel36.add(panel39, BorderLayout.CENTER);
                }
                panel10.add(panel36);
            }
            tabbedPane1.addTab("\u8bfe\u7a0b\u8868", panel10);

            //======== panel11 ========
            {
                panel11.setLayout(new GridLayout());

                //======== panel74 ========
                {
                    panel74.setLayout(new BorderLayout());

                    //======== panel75 ========
                    {
                        panel75.setLayout(new BorderLayout());
                    }
                    panel74.add(panel75, BorderLayout.NORTH);

                    //======== panel76 ========
                    {
                        panel76.setLayout(new BorderLayout());
                    }
                    panel74.add(panel76, BorderLayout.SOUTH);

                    //======== tabbedPane4 ========
                    {

                        //======== panel78 ========
                        {
                            panel78.setLayout(new BorderLayout());

                            //======== panel86 ========
                            {
                                panel86.setLayout(new BorderLayout());
                            }
                            panel78.add(panel86, BorderLayout.NORTH);

                            //======== panel87 ========
                            {
                                panel87.setLayout(new BorderLayout());
                            }
                            panel78.add(panel87, BorderLayout.WEST);

                            //======== panel88 ========
                            {
                                panel88.setLayout(new BorderLayout());
                            }
                            panel78.add(panel88, BorderLayout.EAST);

                            //======== panel89 ========
                            {
                                panel89.setLayout(new BorderLayout(5, 5));

                                //---- button10 ----
                                button10.setText("\u786e\u8ba4\u5220\u9664");
                                button10.addActionListener(e -> button10ActionPerformed(e));
                                panel89.add(button10, BorderLayout.CENTER);
                            }
                            panel78.add(panel89, BorderLayout.SOUTH);

                            //======== panel90 ========
                            {
                                panel90.setLayout(new GridLayout(2, 2, 10, 10));

                                //---- label38 ----
                                label38.setText("\u9009\u8bfe\u5b66\u751f\u5b66\u53f7\uff08\u5fc5\u586b\uff09");
                                panel90.add(label38);
                                panel90.add(SC_D_Sno);

                                //---- label39 ----
                                label39.setText("\u9009\u8bfe\u8bfe\u7a0b\u7f16\u53f7\uff08\u5fc5\u586b\uff09");
                                panel90.add(label39);
                                panel90.add(SC_D_Cno);
                            }
                            panel78.add(panel90, BorderLayout.CENTER);
                        }
                        tabbedPane4.addTab("\u5220\u9664", panel78);

                        //======== panel91 ========
                        {
                            panel91.setLayout(new BorderLayout());

                            //======== panel92 ========
                            {
                                panel92.setLayout(new BorderLayout());
                            }
                            panel91.add(panel92, BorderLayout.NORTH);

                            //======== panel93 ========
                            {
                                panel93.setLayout(new BorderLayout());
                            }
                            panel91.add(panel93, BorderLayout.WEST);

                            //======== panel94 ========
                            {
                                panel94.setLayout(new BorderLayout(5, 5));

                                //---- button11 ----
                                button11.setText("\u786e\u8ba4\u4fee\u6539");
                                button11.addActionListener(e -> button11ActionPerformed(e));
                                panel94.add(button11, BorderLayout.CENTER);

                                //---- label48 ----
                                label48.setText("Tips\uff1a\u8bf7\u5728\u7b2c\u4e00\u884c\u8f93\u5165\u60f3\u8981\u4fee\u6539\u7684\u5b66\u53f7\u548c\u8bfe\u7a0b\u7f16\u53f7\uff0c\u5e76\u5728\u540e\u9762\u4e00\u884c\u586b\u5199\u60f3\u8981\u5c06\u8be5\u9009\u8bfe\u4fe1\u606f\u4fee\u6539\u4e3a\u4f55\u503c\u3002\uff08\u6682\u4e0d\u652f\u6301\u6279\u91cf\u4fee\u6539\uff09");
                                panel94.add(label48, BorderLayout.SOUTH);
                            }
                            panel91.add(panel94, BorderLayout.SOUTH);

                            //======== panel95 ========
                            {
                                panel95.setLayout(new BorderLayout());
                            }
                            panel91.add(panel95, BorderLayout.EAST);

                            //======== panel96 ========
                            {
                                panel96.setLayout(new GridLayout(3, 2, 10, 10));

                                //---- label40 ----
                                label40.setText("\u9009\u8bfe\u5b66\u751f\u5b66\u53f7\uff08\u5fc5\u586b\uff09");
                                panel96.add(label40);
                                panel96.add(SC_U_Sno);

                                //---- label41 ----
                                label41.setText("\u9009\u8bfe\u8bfe\u7a0b\u7f16\u53f7\uff08\u5fc5\u586b\uff09");
                                panel96.add(label41);
                                panel96.add(SC_U_Cno);

                                //---- label42 ----
                                label42.setText("\u8bfe\u7a0b\u6210\u7ee9\uff08\u9009\u586b\uff09");
                                panel96.add(label42);
                                panel96.add(SC_U_Grade);
                            }
                            panel91.add(panel96, BorderLayout.CENTER);
                        }
                        tabbedPane4.addTab("\u4fee\u6539", panel91);

                        //======== panel79 ========
                        {
                            panel79.setLayout(new BorderLayout());

                            //======== panel97 ========
                            {
                                panel97.setLayout(new GridLayout());
                            }
                            panel79.add(panel97, BorderLayout.NORTH);

                            //======== panel98 ========
                            {
                                panel98.setLayout(new GridLayout());
                            }
                            panel79.add(panel98, BorderLayout.WEST);

                            //======== panel99 ========
                            {
                                panel99.setLayout(new BorderLayout(5, 5));

                                //---- label46 ----
                                label46.setText("Tips\uff1a \u5982\u679c\u60f3\u8981\u67e5\u8be2\u6240\u6709\u7684\u9009\u8bfe\u4fe1\u606f\uff0c\u8bf7\u4e0d\u8981\u586b\u5199\u4efb\u4f55\u67e5\u627e\u5173\u952e\u5b57\uff0c\u76f4\u63a5\u70b9\u51fb\u201c\u67e5\u8be2\u201d\u6309\u94ae\u5373\u53ef\u3002");
                                panel99.add(label46, BorderLayout.CENTER);

                                //---- button12 ----
                                button12.setText("\u786e\u8ba4\u67e5\u8be2");
                                button12.addActionListener(e -> button12ActionPerformed(e));
                                panel99.add(button12, BorderLayout.NORTH);
                            }
                            panel79.add(panel99, BorderLayout.SOUTH);

                            //======== panel100 ========
                            {
                                panel100.setLayout(new GridLayout());
                            }
                            panel79.add(panel100, BorderLayout.EAST);

                            //======== panel101 ========
                            {
                                panel101.setLayout(new GridLayout(3, 2, 10, 10));

                                //---- label43 ----
                                label43.setText("\u9009\u8bfe\u5b66\u751f\u5b66\u53f7\uff08\u9009\u586b\uff09");
                                panel101.add(label43);
                                panel101.add(SC_R_Sno);

                                //---- label44 ----
                                label44.setText("\u9009\u8bfe\u8bfe\u7a0b\u7f16\u53f7\uff08\u9009\u586b\uff09");
                                panel101.add(label44);
                                panel101.add(SC_R_Cnp);

                                //---- label45 ----
                                label45.setText("\u8bfe\u7a0b\u6210\u7ee9\uff08\u9009\u586b\uff09");
                                panel101.add(label45);
                                panel101.add(SC_R_Grade);
                            }
                            panel79.add(panel101, BorderLayout.CENTER);
                        }
                        tabbedPane4.addTab("\u67e5\u8be2", panel79);

                        //======== panel80 ========
                        {
                            panel80.setLayout(new BorderLayout());

                            //======== panel81 ========
                            {
                                panel81.setLayout(new BorderLayout());
                            }
                            panel80.add(panel81, BorderLayout.NORTH);

                            //======== panel82 ========
                            {
                                panel82.setLayout(new BorderLayout());
                            }
                            panel80.add(panel82, BorderLayout.WEST);

                            //======== panel83 ========
                            {
                                panel83.setLayout(new BorderLayout());

                                //---- button9 ----
                                button9.setText("\u786e\u8ba4\u589e\u52a0");
                                button9.addActionListener(e -> button9ActionPerformed(e));
                                panel83.add(button9, BorderLayout.NORTH);
                            }
                            panel80.add(panel83, BorderLayout.SOUTH);

                            //======== panel84 ========
                            {
                                panel84.setLayout(new BorderLayout());
                            }
                            panel80.add(panel84, BorderLayout.EAST);

                            //======== panel85 ========
                            {
                                panel85.setLayout(new GridLayout(3, 2, 10, 10));

                                //---- label20 ----
                                label20.setText("\u9009\u8bfe\u5b66\u751f\u7f16\u53f7\uff08\u5fc5\u586b\uff09");
                                panel85.add(label20);
                                panel85.add(SC_I_Sno);

                                //---- label21 ----
                                label21.setText("\u9009\u8bfe\u8bfe\u7a0b\u7f16\u53f7\uff08\u5fc5\u586b\uff09");
                                panel85.add(label21);
                                panel85.add(SC_I_Cno);

                                //---- label22 ----
                                label22.setText("\u8bfe\u7a0b\u6210\u7ee9\uff08\u9009\u586b\uff09");
                                panel85.add(label22);
                                panel85.add(SC_I_Grade);
                            }
                            panel80.add(panel85, BorderLayout.CENTER);
                        }
                        tabbedPane4.addTab("\u589e\u52a0", panel80);
                    }
                    panel74.add(tabbedPane4, BorderLayout.CENTER);
                }
                panel11.add(panel74);
            }
            tabbedPane1.addTab("\u9009\u8bfe\u8868", panel11);
        }
        contentPane.add(tabbedPane1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents



    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel panel9;
    private JPanel panel12;
    private JPanel panel13;
    private JPanel panel15;
    private JPanel panel17;
    private JTabbedPane tabbedPane2;
    private JPanel panel19;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel panel8;
    private JButton button3;
    private JPanel panel14;
    private JPanel panel16;
    private JLabel label12;
    private JTextField S_D_Studentid;
    private JPanel panel20;
    private JPanel panel18;
    private JPanel panel28;
    private JPanel panel29;
    private JPanel panel32;
    private JPanel panel33;
    private JPanel panel34;
    private JPanel panel35;
    private JLabel label19;
    private JButton button4;
    private JPanel panel30;
    private JPanel panel31;
    private JLabel label13;
    private JTextField Studentid;
    private JLabel label14;
    private JTextField S_U_Studentid;
    private JLabel label15;
    private JTextField S_U_Studentname;
    private JLabel label16;
    private JComboBox<String> S_U_Studentsex;
    private JLabel label17;
    private JTextField S_U_Studentage;
    private JLabel label18;
    private JTextField S_U_Studentdept;
    private JPanel panel21;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel label11;
    private JButton button2;
    private JPanel panel4;
    private JPanel panel5;
    private JLabel label1;
    private JTextField S_R_Studentid;
    private JLabel label2;
    private JTextField S_R_Studentname;
    private JLabel label5;
    private JComboBox<String> S_R_Studentsex;
    private JLabel label6;
    private JTextField S_R_Studentage;
    private JLabel label10;
    private JTextField S_R_Studentdept;
    private JPanel panel22;
    private JPanel panel23;
    private JPanel panel24;
    private JPanel panel25;
    private JButton button1;
    private JPanel panel26;
    private JPanel panel27;
    private JLabel label3;
    private JTextField S_I_Studentid;
    private JLabel label4;
    private JTextField S_I_Studentname;
    private JLabel label7;
    private JComboBox<String> S_I_Studentsex;
    private JLabel label8;
    private JTextField S_I_Studentage;
    private JLabel label9;
    private JTextField S_I_Studentdept;
    private JPanel panel10;
    private JPanel panel36;
    private JPanel panel37;
    private JPanel panel38;
    private JPanel panel39;
    private JTabbedPane tabbedPane3;
    private JPanel panel40;
    private JPanel panel47;
    private JPanel panel48;
    private JButton button6;
    private JPanel panel49;
    private JPanel panel53;
    private JPanel panel54;
    private JLabel label27;
    private JTextField C_D_Cno;
    private JPanel panel41;
    private JPanel panel60;
    private JPanel panel61;
    private JPanel panel62;
    private JButton button8;
    private JLabel label47;
    private JPanel panel63;
    private JPanel panel64;
    private JLabel label33;
    private JTextField Course_cno;
    private JLabel label34;
    private JTextField C_U_Cno;
    private JLabel label35;
    private JTextField C_U_Cname;
    private JLabel label36;
    private JTextField C_U_Cpno;
    private JLabel label37;
    private JTextField C_U_Ccredit;
    private JPanel panel42;
    private JPanel panel55;
    private JPanel panel56;
    private JPanel panel57;
    private JLabel label32;
    private JButton button7;
    private JPanel panel58;
    private JPanel panel59;
    private JLabel label28;
    private JTextField C_R_Cno;
    private JLabel label29;
    private JTextField C_R_Cname;
    private JLabel label30;
    private JTextField C_R_Cpno;
    private JLabel label31;
    private JTextField C_R_Ccredit;
    private JPanel panel43;
    private JPanel panel44;
    private JPanel panel45;
    private JButton button5;
    private JPanel panel50;
    private JPanel panel51;
    private JPanel panel52;
    private JLabel label23;
    private JTextField C_I_Cno;
    private JLabel label24;
    private JTextField C_I_Cname;
    private JLabel label25;
    private JTextField C_I_Cpno;
    private JLabel label26;
    private JTextField C_I_Ccredit;
    private JPanel panel11;
    private JPanel panel74;
    private JPanel panel75;
    private JPanel panel76;
    private JTabbedPane tabbedPane4;
    private JPanel panel78;
    private JPanel panel86;
    private JPanel panel87;
    private JPanel panel88;
    private JPanel panel89;
    private JButton button10;
    private JPanel panel90;
    private JLabel label38;
    private JTextField SC_D_Sno;
    private JLabel label39;
    private JTextField SC_D_Cno;
    private JPanel panel91;
    private JPanel panel92;
    private JPanel panel93;
    private JPanel panel94;
    private JButton button11;
    private JLabel label48;
    private JPanel panel95;
    private JPanel panel96;
    private JLabel label40;
    private JTextField SC_U_Sno;
    private JLabel label41;
    private JTextField SC_U_Cno;
    private JLabel label42;
    private JTextField SC_U_Grade;
    private JPanel panel79;
    private JPanel panel97;
    private JPanel panel98;
    private JPanel panel99;
    private JLabel label46;
    private JButton button12;
    private JPanel panel100;
    private JPanel panel101;
    private JLabel label43;
    private JTextField SC_R_Sno;
    private JLabel label44;
    private JTextField SC_R_Cnp;
    private JLabel label45;
    private JTextField SC_R_Grade;
    private JPanel panel80;
    private JPanel panel81;
    private JPanel panel82;
    private JPanel panel83;
    private JButton button9;
    private JPanel panel84;
    private JPanel panel85;
    private JLabel label20;
    private JTextField SC_I_Sno;
    private JLabel label21;
    private JTextField SC_I_Cno;
    private JLabel label22;
    private JTextField SC_I_Grade;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    // 判断字符串是否是纯数字，用于数据合法性检查。
    public boolean isNumeric(String str)
    {
        for(char c : str.toCharArray())
        {
            if(!Character.isDigit(c)) return false;;
        }
        return true;
    }

}

//弹窗的窗口
class MyDialog extends JDialog {
    public MyDialog(String notes) {
        this.setVisible(true);
        this.setBounds(100, 100, 500, 500);

        Container container = this.getContentPane();
        container.setLayout(null);

        JLabel jLabel = new JLabel(notes);
        jLabel.setBounds(100, 100, 100, 100);
        container.add(jLabel);
    }
}




