package SC_DB.Object;

import java.util.ArrayList;

public class Student extends ArrayList<Student>{
        private String Sno;
        private String Sname;
        private String Ssex;
        // 通过 SQL 语句插入，没必要用 int， 全部更改为 String 即可。
        private String Sage;
        private String Sdept;

        public Student() {

        }

        public String getNO() {
            return Sno;
        }

        public void setNO(String Sno) {
            this.Sno = Sno;
        }

        public String getName() {
            return Sname;
        }

        public void setName(String name) {
            this.Sname = name;
        }

        public String getSex() {
            return Ssex;
        }

        public void setSex(String sex) {
            this.Ssex = sex;
        }

        public String getage() {
            return Sage;
        }

        public void setage(String age) {
            this.Sage = age;
        }

        public String getdept() {
            return Sdept;
        }

        public void setdept(String dept) {
            this.Sdept = dept;
        }

        public Student(String id,String name, String sex, String age, String dept) {
            this.Sno = id;
            this.Sname = name;
            this.Ssex = sex;
            this.Sage = age;
            this.Sdept = dept;
}

        public Student(String name, String sex, String age, String dept) {
            this.Sname = name;
            this.Ssex = sex;
            this.Sage = age;
            this.Sdept = dept;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + Sno +
                    ", name='" + Sname + '\'' +
                    ", sex='" + Ssex + '\'' +
                    ", phone='" + Sage + '\'' +
                    ", birthplace='" + Sdept + '\'' +
                    '}';
        }



}
