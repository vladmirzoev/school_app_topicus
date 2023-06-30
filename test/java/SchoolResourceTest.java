import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class SchoolResourceTest {

    SchoolResource school = new SchoolResource();

    @Test
    void getSchoolsTest() throws Exception{
        String school1 = "test1";
        String school2 = "test2";
        String school3 = "test3";

        List<School> getSchools = school.getSchools();
        boolean foundAllSchools = false;
        for(School checkschool1 : getSchools){
            if (checkschool1.getSchool_name().equals(school1)){
                for (School checkschool2 : getSchools){
                    if (checkschool2.getSchool_name().equals(school2)){
                        for (School checkschool3 : getSchools){
                            if (checkschool3.getSchool_name().equals(school3)){
                                foundAllSchools = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        assertTrue(foundAllSchools);

    }

    @Test
    void findSchoolTest() throws Exception {
        String school1 = "test1";

        List<School> getSchools = school.getSchools();
        boolean foundSchool = false;
        for (School checkschool1 : getSchools) {
            if (checkschool1.getSchool_name().equals(school1)) {
                School queriedSchool = new School();
                queriedSchool.setSchool_id(checkschool1.getSchool_id());
                queriedSchool.setSchool_name(checkschool1.getSchool_name());
                if (queriedSchool.getSchool_name().equals(school1)) {
                    foundSchool = true;
                    break;
                }
            }
        }
        assertTrue(foundSchool);
    }

}