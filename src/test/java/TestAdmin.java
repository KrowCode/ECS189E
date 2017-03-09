import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdmin {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup()
    {
        this.admin = new Admin();
        this.student = new Student();
    }

    @Test
    //Tests if something is entered present year
    public void testAdminMakerYear()
    {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    //Tests if year is entered in the past
    public void testAdminMakerYear2()
    {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    //Tests for something in the future
    public void testAdminMakerYear3()
    {
        this.admin.createClass("Test", 2020, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2020));
    }

    //Write the tests that just exposes the big error, not each individual one
    //Constraints

    //Tests if maximum capacity size can be created with 0
    @Test
    public void testAdminClassSize()
    {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    //Above 0
    @Test
    public void testAdminClassSize2()
    {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    //Below 0
    @Test
    public void testAdminClassSize3()
    {
        this.admin.createClass("Test", 2017, "Instructor", -10);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    //Three in one year
    @Test
    public void testThreeSameYear()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2017, "Instructor1", 20);
        this.admin.createClass("Test3", 2017, "Instructor1", 30);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    //Two or more classes in diff year is okay
    @Test
    public void testTwoSameYear()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2017, "Instructor1", 20);
        this.admin.createClass("Test3", 2018, "Instructor1", 30);
        this.admin.createClass("Test4", 2018, "Instructor1", 40);
        assertTrue(this.admin.classExists("Test2", 2017));
    }

    //Two and two
    @Test
    public void testTwoSameYear2()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2017, "Instructor1", 20);
        this.admin.createClass("Test3", 2018, "Instructor1", 30);
        this.admin.createClass("Test4", 2018, "Instructor1", 40);
        assertTrue(this.admin.classExists("Test4", 2018));
    }

    //Two in one, one in future
    @Test
    public void testTwoSameYear3()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2017, "Instructor1", 20);
        this.admin.createClass("Test3", 2018, "Instructor1", 30);
        assertTrue(this.admin.classExists("Test3", 2018));
    }

    //Two in future, one now
    @Test
    public void testTwoSameYear4()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.createClass("Test2", 2018, "Instructor1", 20);
        this.admin.createClass("Test3", 2018, "Instructor1", 30);
        assertTrue(this.admin.classExists("Test3", 2018));
    }

    //Non-Unique className/Year
    @Test
    public void testDupName()
    {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.admin.createClass("Test", 2017, "Instructor2", 20);
        assertTrue(this.admin.getClassInstructor("Test", 2017).equals("Instructor"));
    }

    //Just Tests if changing works
    @Test
    public void testChangeAbove()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.changeCapacity("Test", 2017, 20);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 20);
    }

    //Changing capacity to same value
    @Test
    public void testChangeSame()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.admin.changeCapacity("Test", 2017, 10);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 10);
    }

    //Changing capacity to below threshold
    @Test
    public void testChangeBelow()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.student.registerForClass("Me", "Test", 2017);
        this.student.registerForClass("Him", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 1);
        assertFalse(this.admin.getClassCapacity("Test", 2017) == 1);
    }

    //Changing capacity to threshold
    @Test
    public void testChangeTo()
    {
        this.admin.createClass("Test", 2017, "Instructor1", 10);
        this.student.registerForClass("Me", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 9);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 9);
    }


    //Changing for a class that doesn't exist
    @Test
    public void testChangeNotExist()
    {
        this.admin.createClass("Test", 2017, "Instructor", 10);
        this.admin.changeCapacity("Test2", 2017, 9);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 10);

    }





}
