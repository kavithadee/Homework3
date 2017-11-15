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


public class TestInstructor {
	
	 private IInstructor instructor;
	 private IAdmin admin;
	 private IStudent student;
	 
	 @Before
	 public void setup() {
		 this.instructor = new Instructor();
		 this.admin = new Admin();
		 this.student = new Student();
	    }
	 
	 @Test
	 public void testAddHomework1() { // test addHomework()
		 this.instructor.addHomework("Instructor","Test", 2017, "Homework1");
		 assertTrue(this.instructor.homeworkExists("Test", 2017, "Homework1"));
	 }
	 
	    @Test
	    public void testAddHomework2() { //homeworkName must not be empty
	        this.admin.createClass("Test", 2017, "Instructor", 15);
	        this.instructor.addHomework("Instructor", "Test", 2017, "");
	        assertFalse(this.instructor.homeworkExists("Test", 2017, ""));
	    }

	    @Test
	    public void testAddHomework3() { //className must not be empty
	        this.admin.createClass("Test", 2017, "Instructor", 15);
	        this.instructor.addHomework("Instructor", "", 2017, "Homework1");
	        assertFalse(this.instructor.homeworkExists("", 2017, "Homework1"));
	    }

	    @Test
	    public void testAddHomework4() { //year must be current
	        this.admin.createClass("Test", 2017, "Instructor", 15);
	        this.instructor.addHomework("Instructor", "Test", 2016, "Homework1");
	        assertFalse(this.instructor.homeworkExists("Test", 2016, "Homework1"));
	    }
	 
	    @Test
	    public void testAddHomework5() { //Instructor must be assigned to class
	        this.admin.createClass("Test", 2027, "Instructor1", 15);
	        this.instructor.addHomework("Instructor2", "Test", 2017, "Homework1");
	        assertFalse(this.instructor.homeworkExists("Test", 2017, "Homework"));
	    }
	    
	    @Test
	    public void testAssignGrade1() { //assignGrade() and getGrade() should work
	        this.admin.createClass("Test", 2017, "Instructor", 15);
	        this.instructor.addHomework("Instructor", "Test", 2017, "Homework1");
	        this.student.registerForClass("A", "Test", 2017);
	        this.student.submitHomework("A","Homework1","Hw1Soln","Test", 2017);
	        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1", "A", 100);
	        assertTrue((this.instructor.getGrade("Test", 2017, "Homework1", "A")) == 100);
	    }

	    @Test
	    public void testAssignGrade2() { //Grade must be greater than 0
	    		this.admin.createClass("Test", 2017, "Instructor", 15);
	        this.instructor.addHomework("Instructor", "Test", 2017, "Homework1");
	        this.student.registerForClass("A", "Test", 2017);
	        this.student.submitHomework("A","Homework1","Hw1Soln","Test", 2017);
	        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1", "A", -1);
	        assertNull(this.instructor.getGrade("Test", 2017, "Homework1", "A"));
	    }
	    
	    @Test
	    public void testAssignGrade3() { //className must not be empty
	        this.instructor.assignGrade("Instructor", "", 2017, "Homework1", "A", 100);
	        assertNull(this.instructor.getGrade("", 2017, "Homework1", "A"));
	    }

	    @Test
	    public void testAssignGrade4() { //homeworkName must not be empty
	        this.instructor.assignGrade("Instructor", "Test", 2017, "", "A", 100);
	        assertNull(this.instructor.getGrade("Test", 2017, "", "A"));
	    }

	    @Test
	    public void testAssignGrade5() { //studentName must not be empty
	        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1", "", 100);
	        assertNull(this.instructor.getGrade("Test", 2017, "Homework1", ""));
	    }
	    
	    @Test
	    public void testAssignGrade6() { // assignGrade() only if student does submitHomework()
	    		this.student.registerForClass("A","Test", 2017);
	        this.instructor.addHomework("Instructor","Test", 2017, "Homework1");
	        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1", "A", 100);
	        assertNull(this.instructor.getGrade("Test", 2017, "Homework1", "A"));
	    }

	    @Test
	    public void testAssignGrade7() { // assignGrade() to student that exists
	    		this.instructor.addHomework("Instructor","Test", 2017, "Homework1");
	    		this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1", "A", 100);
	        assertNull(this.instructor.getGrade("Test", 2017, "Homework1", "A"));
	    }

	    @Test
	    public void testAssignGrade8() { //assignGrade() if addHomework() is done
	        this.student.registerForClass("A", "Test", 2017);
	        this.instructor.assignGrade("Instructor","Test", 2017, "Homework1", "A", 100);
	        assertNull(this.instructor.getGrade("Test", 2017, "Homework1", "A"));
	    }
}
