import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



class SchoolAdminResourceTest {


    SchoolAdminResource sar = new SchoolAdminResource();
    SchoolAdmin adm = new SchoolAdmin();
    StudentResource st = new StudentResource();
    Registration r = new Registration();

    @Test
    void getSchoolRegistrationsTest() throws Exception{
        String admin = "admin@test.com";//admin already in the db
        adm.setAccount_id(admin);
        adm.setSchool_id(1);//admin's school ID

        boolean differentSchoolID = false;//set as false
        for (Registration student: sar.getSchoolRegistrations(admin)){//check for all students on the getSchoolRegistrations method
            if (student.getSchool_id() != adm.getSchool_id()){// check if one student contains a different school ID
                differentSchoolID = true;//found a student that the school admin manages that has a different school ID (which is not what's supposed to happen)
                break;
            }
        }
        assertFalse(differentSchoolID);
    }

    @Test
    void getSchoolRegistrationsOrderedTest() throws Exception{
        String admin = "admin@test.com";//admin already in the db
        adm.setAccount_id(admin);
        adm.setSchool_id(1);
        boolean dateNotInAscendingOrder = false;//set to true if date's not ordered in ascending order
        for (int i = 0; i < (sar.getSchoolRegistrations(admin).size() - 1); i++) {
            Registration currentRegistration = sar.getSchoolRegistrationsOrdered(admin,"registration_date","asc").get(i);
            Registration nextRegistration = sar.getSchoolRegistrationsOrdered(admin,"registration_date","asc").get(i + 1);
            if (Date.valueOf(currentRegistration.getRegistration_date()).compareTo(Date.valueOf(nextRegistration.getRegistration_date())) > 0){// > 0 means the current registration's date is after the next registration's date
                dateNotInAscendingOrder = true;
                break;
            }
        }
        assertFalse(dateNotInAscendingOrder);// returns true if all registrations are in ascending order of date
    }

    @Test
    void getParentStudentsTest() throws Exception {
        String email = "ab@cd"; //existing parent in db with 41 kids

        List<Student> getAllStudents = st.getStudents();
        List<Registration> abcdChildren = sar.getParentStudents(email);
        int count = 0;
        for (Registration abcdChild : abcdChildren) {
            for (int i = 0; i < getAllStudents.size(); i++) {
                if (getAllStudents.get(i).getId() == abcdChild.getStudent_id()) {
                    count += 1;
                }
            }
        }
        assertTrue(count == 8);
    }

    @Test
    void updateStatusTest() throws Exception{
        r.setStudent_id(7);// a student that already exists in the database
        r.setStatus("Under review");
        String status = r.getStatus();//should return Under review
        boolean statusChanged = false;
        if (status.equals("Under review")) {
            sar.updateStatus(7, "Accepted");
            if (r.getStatus().equals("Accepted")) {
                statusChanged = true;
                assertTrue(statusChanged);
            }
        }
    }

    @Test
    void updateEditTest() throws Exception{
        r.setStudent_id(7);// a student that already exists in the database
        r.setAllowedit("N");
        String status = r.getAllowedit();//should return N which means the parent can't edit the registration
        boolean statusChanged = false;
        if (status.equals("N")) {
            sar.updateEdit(7, "Y");
            if (r.getAllowedit().equals("Y")) {
                statusChanged = true;
                assertTrue(statusChanged);
            }
        }
    }

    @Test
    void deleteRegistrationTest() throws Exception{
        r.setStudent_id(7);
        String adminID = "admin@test.com";//admin in the same school as student with id 7

        sar.deleteRegistration(7);
        boolean studentNotRemovedFromDB = false;

        for (Registration reg: sar.getSchoolRegistrations(adminID)){
            if (reg.getStudent_id() == 7){
                studentNotRemovedFromDB = true;
                break;
            }
        }
        assertFalse(studentNotRemovedFromDB);
    }

}