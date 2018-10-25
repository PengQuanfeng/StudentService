package elephant.studentservice.student.Web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import elephant.studentservice.student.Domain.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("StudentServiceController")
public class StudentServiceController {
    private static Map<String, List<Student>> schooDB = new HashMap<String, List<Student>>();

    static {
        schooDB = new HashMap<String, List<Student>>();

        List<Student> lst = new ArrayList<Student>();
        Student std = new Student("Sajal", "Class IV","");
        lst.add(std);
        std = new Student("Lokesh", "Class V","");
        lst.add(std);

        schooDB.put("abcschool", lst);

        lst = new ArrayList<Student>();
        std = new Student("Kajal", "Class III","");
        lst.add(std);
        std = new Student("Sukesh", "Class VI","");
        lst.add(std);

        schooDB.put("xyzschool", lst);

    }

    public List<Student> hiError(RequestEntity<String> requestEntity, @PathVariable String schoolname) {
        String studentService=requestEntity.getHeaders().getFirst("host");

        List<Student> studentList = new ArrayList<Student>();
        Student std = new Student("Not Found", "Is Error.",studentService );
        studentList.add(std);

        return studentList;
    }

    @RequestMapping(value = "/getStudentDetailsForSchool/{schoolname}", method = RequestMethod.GET)
    @ApiOperation(value = "getStudents", notes = "查找学生")
    @HystrixCommand(fallbackMethod = "hiError")
    public List<Student> getStudents(RequestEntity<String> requestEntity, @PathVariable String schoolname)
    {
        System.out.println("Getting Student details for " + schoolname);

        String studentService=requestEntity.getHeaders().getFirst("host");

        List<Student> studentList = schooDB.get(schoolname);

        if (studentList == null) {
            studentList = new ArrayList<Student>();
            Student std = new Student("Not Found", "N/A",studentService );
            studentList.add(std);
        }

        for ( Student student:studentList)
        {
            student.setHost(studentService);
        }

        return studentList;
    }
}
