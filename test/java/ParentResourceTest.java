import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

class ParentResourceTest {


    ParentResource par = new ParentResource();
    StudentResource child = new StudentResource();
    Parent p = new Parent();

    @Test
    void getChildrenTest() throws Exception{
        String childName = "TEST";
        Date bd = Date.valueOf("2001-10-20");
        String parentID = "ab@cd";
        boolean childOfABCD = false;
        List<Student> childrenOfABCD = par.getChildren(parentID);
        for (Student child : childrenOfABCD){
            if (child.getBirthdate().compareTo(String.valueOf(bd)) == 0 && child.getName().equals(childName)){
                childOfABCD = true;
                break;
            }
        }
        assertTrue(childOfABCD);
    }

    @Test
    void getGuardiansTest() throws Exception{
        String parentID = "ab@cd";
        List<Parent> allParents = par.getGuardians();
        boolean parentExists = false;
        for (Parent parent : allParents){
            if (parent.getId().equals(parentID)){
                parentExists = true;
                break;
            }
        }
        assertTrue(parentExists);
    }

    @Test
    void getParentDetails() throws Exception{
        String parentID = "ab@cd";
        String name = "A B";
        String address = "abc 123";
        String p1 = "+123";
        String p2 = "+321";


        boolean detailsAreCorrect = false;
        Parent thisParent = par.getParentDetails(parentID);
        if (thisParent.getName().equals(name) && thisParent.getAddress().equals(address) &&
                thisParent.getPhone_1().equals(p1) && thisParent.getPhone_2().equals(p2)){
            detailsAreCorrect = true;
            assertTrue(detailsAreCorrect);
        }
    }

}