package SC_DB;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MyTable extends JFrame
{
    {
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    private JScrollPane scpDemo;
    private JTableHeader jth;
    private JTable tabDemo;

    public MyTable(String title_of_table, String[] title, Object[][] info)
    {
        super(title_of_table);		//JFrame的标题名称
        this.setSize(620,620);		//控制窗体大小
        this.setLayout(null);		//自定义布局
        this.setLocation(400,100);	//点击运行以后，窗体在屏幕的位置
        this.scpDemo = new JScrollPane();
        this.scpDemo.setBounds(15,20,580,555);	//设置滚动框大小

        /******* 将组件加入到窗体中******/
        add(this.scpDemo);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        System.out.println(info[0][0]);

        // 创建JTable
        this.tabDemo = new JTable(info,title);
        // 显示表头
        this.jth = this.tabDemo.getTableHeader();
        // 将JTable加入到带滚动条的面板中
        this.scpDemo.getViewport().add(tabDemo);

    }
}

