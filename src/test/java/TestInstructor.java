import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 3/8/2017.
 */
public class TestInstructor
{
    private IInstructor instruct;
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.instruct = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }

    //No class exists to assign a teacher to
    @Test
    public void testExistsFalse()
    {
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        assertFalse(this.instruct.homeworkExists("Test", 2017, "HwTest"));
    }

    //Should work
    @Test
    public void testExistsTrue()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        assertTrue(this.instruct.homeworkExists("Test", 2017, "HwTest"));
    }

    //Same class and teacher, but different year
    @Test
    public void testExistsFalse2()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2018,
                "HwTest", "HelloWorld");
        assertFalse(this.instruct.homeworkExists("Test", 2018, "HwTest"));
    }

    //Same year and class, but different teacher
    @Test
    public void testExistsFalse3()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor2", "Test", 2017,
                "HwTest", "HelloWorld");
        assertFalse(this.instruct.homeworkExists("Test", 2017, "HwTest"));
    }

    //AddingGrade without student
    @Test
    public void testAddGradeNull()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        this.instruct.assignGrade("Instructor", "Test", 2017,
                "HwTest", "None", 99);
        assertNull(this.instruct.getGrade("Test", 2017, "HwTest",
                "None"));
    }

    //AddingGrade works
    @Test
    public void testAddGrade()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        this.student.registerForClass("Me", "Test", 2017);
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2017);

        this.instruct.assignGrade("Instructor", "Test", 2017,
                "HwTest", "Me", 99);
        assertNotNull(this.instruct.getGrade("Test", 2017, "HwTest",
                "Me"));
    }

    //Adding Grade as different instructor
    @Test
    public void testAddGradeInstruct()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        this.student.registerForClass("Me", "Test", 2017);
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2017);

        this.instruct.assignGrade("Instructor2", "Test", 2017,
                "HwTest", "Me", 99);
        assertNull(this.instruct.getGrade("Test", 2017, "HwTest",
                "Me"));
    }

    //Adding grade with a student but hasn't submitted
    @Test
    public void testAddGradeSubmit()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        this.student.registerForClass("Me", "Test", 2017);

        this.instruct.assignGrade("Instructor", "Test", 2017,
                "HwTest", "Me", 99);
        assertNull(this.instruct.getGrade("Test", 2017, "HwTest",
                "Me"));
    }

    //Adding grade without assigned Homework
    @Test
    public void testAddGradeAssign()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.student.registerForClass("Me", "Test", 2017);
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2017);

        this.instruct.assignGrade("Instructor", "Test", 2017,
                "HwTest", "Me", 99);
        assertNull(this.instruct.getGrade("Test", 2017, "HwTest",
                "Me"));
    }

    //Grade Less than 0 given
    @Test
    public void testAddNegGrade() {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        this.student.registerForClass("Me", "Test", 2017);
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2017);

        this.instruct.assignGrade("Instructor", "Test", 2017,
                "HwTest", "Me", -100);
        assertNull(this.instruct.getGrade("Test", 2017, "HwTest",
                "Me"));
    }




}
