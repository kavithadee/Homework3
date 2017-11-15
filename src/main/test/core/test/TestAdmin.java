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


public class TestAdmin {
	private IAdmin admin;
	

    @Before
    public void setup() {
        this.admin = new Admin();   
        }
    
    @Test
    public void testMakeClass() { //base case to make sure class is created
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() { //years cannot be in past
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    public void testMakeClass3() { //className not empty string
        this.admin.createClass("", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("", 2017)); 
    }

    @Test
    public void testMakeClass4() { //Instructor not empty string
        this.admin.createClass("Test", 2017, "", 15);
        assertFalse(this.admin.classExists("Test", 2017)); //?
    }
    
    @Test
    public void testMakeClass5() { //only 2 courses per instructor
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }
    
    @Test
    public void testMakeClass6() { //capacity not negative
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void testMakeClass7() { //capacity > 0
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void testMakeClass8() { //className/year pair unique
        this.admin.createClass("Test", 2017, "Instructor1", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        assertEquals("Instructor1", this.admin.getClassInstructor("Test", 2017));
    }
    
    @Test
    public void testGetClassInstructor() { //test getClassInstructor()
    		this.admin.createClass("Test", 2017, "Instructor", 15);
        assertEquals("Instructor", this.admin.getClassInstructor("Test", 2017));
    }
    
    @Test
    public void testGetClassCapacity() { //test getClassCapacity()
    		this.admin.createClass("Test", 2017, "Instructor", 15);
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testChangeCapacity() { //test changeCapacity()
    		this.admin.createClass("Test", 2017, "Instructor", 10);
        this.admin.changeCapacity("Test", 2017, 15);
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testChangeCapacity2() { //capacity >= enrolled students
        this.admin.createClass("Test", 2017, "Instructor", 2);
        IStudent student1 = new Student();
        student1.registerForClass("student1", "Test", 2017);
        IStudent student2 = new Student();
        student2.registerForClass("student2", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 1);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testChangeCapacity3() { //changeCapacity will not allow 0
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 0);
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacity4() { //changeCapacity() will not allow a negative number
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, -1);
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    public void testChangeCapacity5() { // changeCapacity() for same capacity
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test", 2017, 15);
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacity6() { // changeCapacity() only done if class exists
        this.admin.changeCapacity("Test", 2017, 15);
        assertEquals(-1, this.admin.getClassCapacity("Test", 2017));
    }

}
