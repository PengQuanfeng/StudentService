package elephant.studentservice.student.Domain;

public class Student {
    private String name;
    private String className;
    private  String host;

    public Student(String name, String className,String host) {
        super();
        this.name = name;
        this.className = className;
        this.host=host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public  String getHost(){return host;}

    public void setHost(String hostName){host=hostName;}
}
