import static org.junit.jupiter.api.Assertions.*;import org.junit.jupiter.api.Test;

import java.util.List;


class TopicusAdminResourceTest {
    TopicusAdminResource tar = new TopicusAdminResource();
    AccountResource acc = new AccountResource();


    @Test
    void addSchoolTest() throws Exception{
        String schoolName = "test4";
        String address = "Enschede 4";
        int tuition = 4444;
        String contactNumber = "4444444444";
        tar.addSchool(schoolName,address, tuition,contactNumber);
        List<School> allSchools = tar.getAllSchools();

        boolean containsAddedSchool = false;
        for (School school : allSchools) {
            if (school.getSchool_name().equals(schoolName)
                    && school.getAddress().equals(address)
                    && school.getTuition() == tuition
                    && school.getContact_number().equals(contactNumber)) {
                containsAddedSchool = true;
                break;
            }
        }

        assertTrue(containsAddedSchool);


    }

    @Test
    void addAdminTest() throws Exception{
        int id = 4;
        String email = "JUnitadmin@test.com";
        String name = "Essam Sassa";
        String add = "Enschede 394";
        String pNo = "4521378924";
        String pass = "anaessamsassa";
        tar.addAdmin(id,email,name,add,pNo, pass);
        List<SchoolAdmin> allAdmins = tar.getAllAdmins();
        boolean containsAddedAdmin = false;
        for (SchoolAdmin admin : allAdmins) {
            if (admin.getAccount_id().equals(email) && admin.getSchool_id() == id){
                containsAddedAdmin = true;
                break;
            }
        }
        assertTrue(containsAddedAdmin);
    }

    @Test
    void getAdminTest() throws Exception{
        String email = "JUnitadmin@test.com";
        String name = "Essam Sassa";
        String add = "Enschede 394";
        String pNo1 = "4521378924";
        String pNo2 = null;

        tar.getAdmin(email);
        List<Account> allAcounts = acc.getAllAccounts();
        boolean containsAdmin = false;
        for (Account account: allAcounts){
            if (account.getAccount_id().equals(email) && account.getName().equals(name)
                    && account.getAddress().equals(add) && account.getPhone_number_1().equals(pNo1) &&
                    account.getPhone_number_2() == pNo2){
                containsAdmin = true;
            }
        }
        assertTrue(containsAdmin);
    }


    @Test
    void updateSchoolTest() throws Exception{
        int id = 4;
        String schoolName = "test4";
        String address = "Enschede 4";
        int tuition = 4444;// old tuition fee
        String contactNumber = "4444444444";
        int new_tuition_fee = 3000;// changed tuition fee
        String new_contactNumber = "1112131415";//Changed school number
        tar.updateSchool(id,schoolName,address,new_tuition_fee,new_contactNumber);
        List<School> allSchools = tar.getAllSchools();

        boolean containsUpdatedDetails = true; //To make sure details have been updated, check if old details don't exist anymore
        for (School school : allSchools) {
            if (school.getSchool_name().equals(schoolName)
                    && school.getAddress().equals(address)
                    && school.getTuition() == 4444
                    && school.getContact_number().equals(contactNumber)) {
                containsUpdatedDetails = false;
                break;
            }
        }
        assertTrue(containsUpdatedDetails);

    }

    @Test
    void updateSchoolAdminTest() throws Exception{
        String email = "JUnitadmin@test.com";
        String name = "Essam Sassa";
        String add = "Enschede 394";
        String pNo = "4521378924";
        String pass = "admin";
        String new_address = "Enchede 246";// new address
        String new_pNo = "1472583690";// new phone number set
        String new_pass = "admintest";// new password set
        tar.updateSchoolAdmin(email, name, new_address, new_pNo, new_pass);
        List<Account> allAccounts = acc.getAllAccounts();

        boolean containsUpdatedDetails = true; //To make sure details have been updated, check if old details don't exist anymore
        for (Account admin : allAccounts) {
            if (admin.getAccount_id().equals(email) && admin.getAddress().equals(add) && admin.getPhone_number_1().equals(pNo)
                    && admin.getPassword().equals(pass)) {
                containsUpdatedDetails = false;
                break;
            }
        }
        assertTrue(containsUpdatedDetails);
    }

    @Test
    void removeAdminTest() throws Exception{
        String email = "JUnitadmin@test.com";
        tar.removeAdmin(email);

        List<Account> allAccounts = acc.getAllAccounts();
        boolean removedAdmin = false;
        for (Account admin : allAccounts) {
            if (!admin.getAccount_id().equals(email)){
                removedAdmin = true;
                break;
            }
        }
        assertTrue(removedAdmin);
    }

    @Test
    void removeSchoolTest() throws Exception{
        int id = 4;// school name test4
        tar.removeSchool(id);

        List<School> allSchools = tar.getAllSchools();

        boolean removedSchool = false; //To make sure details have been updated, check if old details don't exist anymore
        for (School school : allSchools) {
            if (school.getSchool_id() != id) {
                removedSchool = true;
                break;
            }
        }
        assertTrue(removedSchool);

    }

}