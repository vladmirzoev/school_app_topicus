import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

class StudentResourceTest {

    StudentResource sr = new StudentResource();
    FormResource s1 = new FormResource(); //student 1
    FormResource s2 = new FormResource(); //student 2
    FormResource s3 = new FormResource(); //student 3

    @Test
    void getStudentsTest() throws Exception{
        String s1_bsn = "741852963";
        String s2_bsn = "963258741";
        s1.uploadRegistrationNoAccount("Manga","Khales Aha","4561237890", null, s1_bsn,Date.valueOf("2000-04-11"), 12, "test1", "Hengelovo");
        s2.uploadRegistrationNoAccount("Khara", "Alek YaZeft", "4567821390", "1532467590",s2_bsn,Date.valueOf("1999-12-31"), 12, "test1", "Hengelolo");

        List<Student> getAllStudents = sr.getStudents();
        boolean bothStudentsExist = false;

        for (Student student1 : getAllStudents) {
            if (student1.getName().equals("Manga")) {
                for (Student student2 : getAllStudents) {
                    if (student2.getName().equals("Khara")) {
                        bothStudentsExist = true;
                        break;
                    }
                }
                break;
            }
        }
        assertTrue(bothStudentsExist);
    }

    @Test
    void findStudentTest() throws Exception {
        List<Student> getAllStudents = sr.getStudents();

        Student queriedStudent = new Student(); //used to find student using their ID
        for (Student student : getAllStudents) { //finds student with the name Manga and sets him as the queried student
            if (student.getName().equals("Manga")) {
                queriedStudent.setName(student.getName());
                queriedStudent.setBirthdate(student.getBirthdate());
                queriedStudent.setId(student.getId());
                queriedStudent.setBsn(student.getBsn());
                queriedStudent.setGuardian_id(student.getGuardian_id());
                break;
            }
        }
        boolean foundStudentUsingID = false;
        if (sr.findStudent(queriedStudent.getId()).getName().equals("Manga")){ //to confirm that the student Manga corresponds to the id extracted
            foundStudentUsingID = true;
            assertTrue(foundStudentUsingID);
        }
    }

    @Test
    void editChildNameTest() throws Exception {
        List<Student> getAllStudents = sr.getStudents();
        boolean nameChanged = false;
        for (Student student : getAllStudents) { // Gets student Manga
            if (student.getName().equals("Manga")) {
                Student initial = new Student();
                initial.setName(student.getName());
                initial.setBirthdate(student.getBirthdate());
                initial.setId(student.getId());
                initial.setBsn(student.getBsn());
                initial.setGuardian_id(student.getGuardian_id());

                sr.editChildName(initial.getId(), "Mangaha"); // Change name to Mangaha

                List<Student> getAllStudents2 = sr.getStudents(); // Update list of students

                for (Student changedName : getAllStudents2) { // Sets original details to the student's new name
                    if (changedName.getName().equals("Mangaha")) {
                        Student changed = new Student();
                        changed.setName(changedName.getName());
                        changed.setBirthdate(changedName.getBirthdate());
                        changed.setId(changedName.getId());
                        changed.setBsn(changedName.getBsn());
                        changed.setGuardian_id(changedName.getGuardian_id());
                        if (changed.getId() == initial.getId() && !changed.getName().equals(initial.getName())) {
                            nameChanged = true; // Set nameChanged to true if the condition is met
                            break;
                        }
                    }
                }

            }

        }
        assertTrue(nameChanged);
    }

    @Test
    void editBirthDateTest() throws Exception {
        List<Student> getAllStudents = sr.getStudents();
        boolean dateChanged = false;
        for (Student student : getAllStudents) { // Gets student Manga
            if (student.getName().equals("Mangaha")) {
                Student initial = new Student();
                initial.setName(student.getName());
                initial.setBirthdate(student.getBirthdate());
                initial.setId(student.getId());
                initial.setBsn(student.getBsn());
                initial.setGuardian_id(student.getGuardian_id());

                sr.editBirthDate(initial.getId(), Date.valueOf("2001-11-15")); // Change name to Mangaha

                List<Student> getAllStudents2 = sr.getStudents(); // Update list of students

                for (Student changedName : getAllStudents2) { // Sets original details to the student's new name
                    if (changedName.getName().equals("Mangaha")) {
                        Student changed = new Student();
                        changed.setName(changedName.getName());
                        changed.setBirthdate(changedName.getBirthdate());
                        changed.setId(changedName.getId());
                        changed.setBsn(changedName.getBsn());
                        changed.setGuardian_id(changedName.getGuardian_id());
                        if (changed.getId() == initial.getId() && !changed.getBirthdate().equals(initial.getBirthdate())) {
                            dateChanged = true; // Set nameChanged to true if the condition is met
                            break;
                        }
                    }
                }

            }

        }
        assertTrue(dateChanged);
    }

}