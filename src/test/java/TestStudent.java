/**
 * Created by Kevin on 3/8/2017.
 */
import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent
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

    //Register class works
    @Test
    public void testEnrolled()
    {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Me", "Test", 2017);
        assertTrue(this.student.isRegisteredFor("Me", "Test", 2017));
    }

    //Register for already filled class
    @Test
    public void testFull()
    {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.student.registerForClass("Me", "Test", 2017);
        this.student.registerForClass("Him", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Him", "Test", 2017));
    }

    //Register for class that doesn't exist
    @Test
    public void testExists()
    {
        this.student.registerForClass("Me", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Me", "Test", 2017));
    }

    //Drop work
    @Test
    public void testDrop()
    {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Me", "Test", 2017);
        this.student.dropClass("Me", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Me", "Test", 2017));
    }

    //Drop student if he isn't registered
    @Test
    public void testDropNotReg()
    {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.student.registerForClass("Me", "Test", 2017);
        this.student.dropClass("Him", "Test", 2017);
        assertTrue(this.student.isRegisteredFor("Me", "Test", 2017));
        assertFalse(this.student.isRegisteredFor("Him", "Test", 2017));
    }

    //SubmitHomework Works
    @Test
    public void testSubmitOk()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        this.student.registerForClass("Me", "Test", 2017);
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2017);

        assertTrue(this.student.hasSubmitted("Me", "HwTest", "Test",
                2017));
    }

    //SubmitHomework without the HW
    @Test
    public void testSubmitNoHW()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.student.registerForClass("Me", "Test", 2017);
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2017);

        assertFalse(this.student.hasSubmitted("Me", "HwTest", "Test",
                2017));
    }

    //Submitting without student being registered
    @Test
    public void testSubmitNotReg()
    {
        this.admin.createClass("Test", 2017, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2017,
                "HwTest", "HelloWorld");
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2017);

        assertFalse(this.student.hasSubmitted("Me", "HwTest", "Test",
                2017));
    }

    //SubmitHomework Works
    @Test
    public void testSubmitDiffYear()
    {
        this.admin.createClass("Test", 2018, "Instructor", 20);
        this.instruct.addHomework("Instructor", "Test", 2018,
                "HwTest", "HelloWorld");
        this.student.registerForClass("Me", "Test", 2018);
        this.student.submitHomework("Me", "HwTest", "Ayy",
                "Test", 2018);

        assertFalse(this.student.hasSubmitted("Me", "HwTest", "Test",
                2018));
    }










}
