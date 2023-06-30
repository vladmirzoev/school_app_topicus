# StudiePortal - school administration
## Topicus Group 6

To start with, the project has **3 types of users**: 
- G (Guardian/parent)
- A (School Admin)
- T (Topicus/System Admin)

## G-Guardian Flow
### Sign-up
Account registration process implies classic sign-up with user's email, phone, home address, name and surname 
### Login
After creating an account, guardian can log in with the corresponding credentials. It should be done with the selected "Login" tab. 
### Filling a form
Guardian may fill in the form for his/her child without registering an account by clicking "Link to a form" button. After confirming it, user will be redirected to a particular school's form, which is mandatory to be filled in.
Filling a form / adding a child is also accessible to users with an account via dashboard or sidebar menu, which will be expanded in the following chapters.
### Parent dashboard
Parent Dashboard is a visual representation of StudiePortal. By clicking any of the boxes, a user will be redirected to an assigned page:
- Messages page
- Children page
- New Form page
### Adding and editing child
To add a child, a parent can fill in a new form which is accessible via dashboard or 'my children' page -> '+'.
On 'my children' page, a guardian can see the kids assign to them in boxes. It is possible to edit an existing child by clicking the corresponding box. Then, a user will be redirected to a separate page, where guardian can input new data in the field.
Guardian can remove a child from the system by using "bin" button on a kid's box.
### Communication with schools
Schools update registrations status. It means that they can notify a guardian once a registration was updated. In this case, a parent will see a designated chat on 'messages' page. A parent can ask questions via this chat window. Users can access it by double-clicking a needed box with messages;
**A parent cannot start a chat**, he/she can only reply, asking questions that arise after school's actions
### Edit user's profile
Users can also edit their profiles. A special page is accessible via the sidebar menu (user icon)
The functionality is the same as on 'edit child' page.

**NB:**
- **parent cannot change the email, since the account is linked with database via this address.**
- **a user without a normal account cannot create one after registering his/her child, since he/she already has a full access as a parent with the 'ghost account' (only with the code number provided).**


## A-School Admin Flow
### Sign-up
Sign up is done by system administrator.
### Login
Login details are supposed to be sent by system administrator. 

To login, a School Admin should access the same page as Parents, however, the tab "Admin Login" should be chosen.

### Creating and editing form
Forms overview is accessible via the sidebar menu -> forms.

To create a form, a school administrator has to press '+' button.

To edit a form, click on an existing one from the list and an admin will be redirected to edit mode. 
Edit mode functionalities:
- Add fields of diverse types
  - Text
  - Number
  - Phone
  - Email
  - Date
  - File*
- Change font
- Edit fields textual values (Questions or clarifications for user's inputs)
- Assign a form to a range of school grades from 1 to 12
After pressing "create form", the form will be published, thus will be accessible via 'forms' page and will be displayed to parents who have indicated a particular school while registering a child

*the front-end implementation, but not functioning back-end
### Registrations management
In 'registrations' page, the list with registrations to a particular school is displayed. 
By clicking a specific registration, the popup-window is opened.
Therefore, admin can export a registration or send a message to a parent while updating the status of this registration. Note, admins cannot update the status without sending a message to a parent, moreover, each new subject will create a corresponding chat.
### Communication with parents
School admins can start conversation via registrations (prev. chapter) or, if a chat is already created, can directly send a message in 'contact parents' page.

## T-Topicus Admin Flow
Topicus admin is a person that manages the whole application: creates schools, assigns administrators for them, deletes and edits the institutions' accounts
### Login
Login is done via a separate page that is not accessible to other types of users.
Login page: 'loginTopicus.html'
Credentials:
- username: admin@topicus.com
- pw: 1
### Managing schools
The admin will be redirected to 'adminPage.html' where a button 'add' is displayed.
- 'Add' -> leads to a page where admin needs to fill in the school details: name, address, tuition fee, contact number
- 'Edit' -> leads to a page where admin can edit the existing details: name, address, tuition fee, contact number
- 'Delete' -> displays the confirmation alert before deleting a school.
### Adding school Admin (A)
By clicking a button 'Add School Admin', a new page is displayed in order to create a School Admin account for the specific school. The following details should be filled in:
- Name & Surname
- Address
- Email
- Phone
- Password
- Password confirmation
