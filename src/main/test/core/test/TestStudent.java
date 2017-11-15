package core.test;

import core.api.IAdmin;
import core.api.impl.Student;
import core.api.IStudent;
import core.api.impl.Instructor;
import core.api.IInstructor;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
	
	private IInstructor instructor;
	private IAdmin admin;
	private IStudent student;
	 
	
	@Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }

    @Test
    public void testRegisterForClass() { // test that registerForClass() works
        this.admin.createClass("Test", 2017, "Instructor", 100);
        this.student.registerForClass("A", "Test", 2017);
        assertTrue(this.student.isRegisteredFor("A", "Test", 2017));
    }

    @Test
    public void testRegisterForClass2() { // student can registerForClass if not at capacity
        IStudent student2 = new Student();
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.student.registerForClass("A", "Test", 2017);
        student2.registerForClass("B", "Test", 2017);
        assertFalse(student2.isRegisteredFor("B", "Test", 2017));
    }

    @Test
    public void testRegisterForClass3() { // student can registerForClass only if exists
        this.student.registerForClass("A","Test",2017);
        assertFalse(this.student.isRegisteredFor("A","Test",2017));
    }

    @Test
    public void testDropClass() { // test that dropClass() works
    		this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("A", "Test",2017);
        assertTrue(this.student.isRegisteredFor("A","Test",2017));
        this.student.dropClass("A", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("A", "Test", 2017));
    }
  
    @Test
    public void testDropClass2() { //student must be registered for class
        this.admin.createClass("Test",2017,"Instructor",15);
        this.student.dropClass("A","Test",2017);
        assertFalse(this.student.isRegisteredFor("A","Test",2017));
    }
    
    @Test
    public void testDropClass3() { //class must not be over
        this.admin.createClass("Test",2017,"A",15);
        this.student.registerForClass("A","Test",2017);
        this.student.dropClass("A","Test",2017);
        assertTrue(this.student.isRegisteredFor("A","Test",2017));
        assertFalse(this.admin.classExists("Test",2017));
        assertFalse(this.student.isRegisteredFor("A","Test",2017));
    }

    @Test
    public void testSubmitHomework() { // test that submitHomework() works
		this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("A", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "Homework1");
        this.student.submitHomework("A", "Homework1", "Hw1Soln", "Test", 2017);
        assertTrue(this.student.hasSubmitted("A", "Homework1", "Test", 2017));
    }

    @Test
    public void testSubmitHomework2() { //submitHomework() only if registered for class
		this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "Homework1");
        this.student.submitHomework("A", "Homework1", "Hw1soln", "Test", 2017);
        assertFalse(this.student.hasSubmitted("A", "Homework1", "Test", 2017));
    }

    @Test
    public void testSubmitHomework3() { //submitHomework() only if assgined
		this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.submitHomework("A", "hw1", "hw1soln", "Test", 2017);
        assertFalse(this.student.hasSubmitted("A", "hw1", "Test", 2017));
    }

    @Test
    public void testSubmitHomework4() { //submitHomework() in current year
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2018, "hw1");
        this.student.registerForClass("A", "Test", 2018);
        this.student.submitHomework("A", "hw1", "hw1soln", "Test2", 2018);
        assertFalse(this.student.hasSubmitted("A", "hw1", "Test", 2018));
    }

}
