package SC_DB.Object;

public class Course {
    private String Cno;
    private String Cname;
    private String Cpno;
    private String Ccredit;

    public Course(){

    }

    public String getCno(){
        return Cno;
    }

    public void setCno(String Cno)
    {
        this.Cno = Cno;
    }

    public String getCname(){
        return Cname;
    }

    public void setCname(String Cname)
    {
        this.Cname = Cname;
    }

    public String getCpno()
    {
        return Cpno;
    }

    public void setCpno(String Cpno)
    {
        this.Cpno = Cpno;
    }

    public String getCcredit()
    {
        return Ccredit;
    }

    public void setCcredit(String Ccredit)
    {
        this.Ccredit = Ccredit;
    }

    public Course(String Cno, String Cname, String Cpno, String Ccredit)
    {
        this.Cno = Cno;
        this.Cname = Cname;
        this.Cpno = Cpno;
        this.Ccredit = Ccredit;
    }

    public Course(String Cname, String Cpno, String Ccredit)
    {
        this.Cname = Cname;
        this.Cpno = Cpno;
        this.Ccredit = Ccredit;
    }

}
