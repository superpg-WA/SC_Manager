package SC_DB.Object;

public class SelectCourse {
    private String Sno;
    private String Cno;
    private String Grade;

    public String getSno()
    {
        return Sno;
    }

    public void setSno(String Sno)
    {
        this.Sno = Sno;
    }

    public String getCno()
    {
        return Cno;
    }

    public void setCno(String Cno)
    {
        this.Cno = Cno;
    }

    public String getGrade()
    {
        return Grade;
    }

    public void setGrade(String Grade)
    {
        this.Grade = Grade;
    }

    public SelectCourse(String Sno, String Cno, String Grade)
    {
        this.Sno = Sno;
        this.Cno = Cno;
        this.Grade = Grade;
    }

    public SelectCourse()
    {

    }
}
