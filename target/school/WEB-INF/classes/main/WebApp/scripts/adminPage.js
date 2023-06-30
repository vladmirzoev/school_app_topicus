function render() {
    let xhr = new XMLHttpRequest();
    let methodcall = './api/school/getschools';
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let obj = JSON.parse(xhr.responseText);
                for (let i = 0; i < obj.length; i++) {
                    addSchool(obj[i])
                }
            }
        }
    };
    xhr.send();
}

function redirectNewSchool() {
    sessionStorage.setItem("schoolID", null);
    sessionStorage.setItem("school_name", null);
    sessionStorage.setItem("school_address", null);
    sessionStorage.setItem("school_tuition", null);
    sessionStorage.setItem("school_number", null);
    location.href = 'schoolPageAdmin.html';
}



function addSchool(school) {
    // Create a new row element
    var newRowElement = document.createElement('div');
    newRowElement.classList.add('row');

// Create the first column element
    var col1Element = document.createElement('div');
    col1Element.classList.add('col-4', 'form-input');

// Create the element for School Name
    var schoolNameButton = document.createElement('text');
    schoolNameButton.style.background = '#FFFFFF';
    schoolNameButton.textContent = school.school_name;

// Append the School Name button to the first column
    col1Element.appendChild(schoolNameButton);

// Create the second column element
    var col2Element = document.createElement('div');
    col2Element.classList.add('col-2', 'form-input');

// Create the Edit button
    var editButton = document.createElement('button');
    editButton.textContent = 'Edit';
    editButton.addEventListener('click', function() {
        // Redirect the user to the desired HTML page
        window.location.href = 'schoolPageAdmin.html';
        sessionStorage.setItem("schoolID", school.school_id);
        sessionStorage.setItem("school_name", school.school_name);
        sessionStorage.setItem("school_address", school.address);
        sessionStorage.setItem("school_tuition", school.tuition);
        sessionStorage.setItem("school_number", school.contact_number);
    });

// Append the Edit button to the second column
    col2Element.appendChild(editButton);

// Create the third column element
    var col3Element = document.createElement('div');
    col3Element.classList.add('col-2', 'form-input');

// Create the Delete button
    var deleteButton = document.createElement('button');
    deleteButton.style.background = '#F80000';
    deleteButton.textContent = 'Delete';

    // Add event listener to deleteButton
    deleteButton.onclick = function () {
        // Remove the newRowElement from its parent container
        newRowElement.parentNode.removeChild(newRowElement);
        let xhr = new XMLHttpRequest();
        let methodcall = './api/sysAdmin/removeSchool/' + school.school_id;
        console.log(methodcall);
        xhr.open('DELETE', methodcall, true);
        xhr.send();
    };

// Append the Delete button to the third column
    col3Element.appendChild(deleteButton);

// Create the fourth column element
    var col4Element = document.createElement('div');
    col4Element.classList.add('col-4');

    let addSchoolAdmin = document.createElement('button')
    addSchoolAdmin.textContent = 'Add School Admin'
    addSchoolAdmin.onclick = function () {
        location.href = 'signupSchoolAdmin.html';
        sessionStorage.setItem("schoolID", school.school_id)
    }

    col4Element.appendChild(addSchoolAdmin);

// Append the columns to the new row
    newRowElement.appendChild(col1Element);
    newRowElement.appendChild(col2Element);
    newRowElement.appendChild(col3Element);
    newRowElement.appendChild(col4Element);

// Get the parent container element
    var parentElement = document.getElementById('path');

// Replace the existing row element with the new row element
    parentElement.append(newRowElement);
}

function createNewAdmin() {
    let xhr = new XMLHttpRequest();
    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let phone = document.getElementById("p_no1").value;
    let address = document.getElementById("address").value;
    let password = document.getElementById("pass").value
    let methodcall = './api/sysAdmin/addAdmin/' + sessionStorage.getItem("schoolID") + '/' + email + '/' + name + '/'
        + address + '/' + phone + '/' + password;
    xhr.open('POST', methodcall, true);
    xhr.send();
    location.href = 'adminPage.html';
}

function sendSchool() {
    if(sessionStorage.getItem('schoolID')!='null') {
        let xhr = new XMLHttpRequest();
        let schoolname = document.getElementById("schoolName").value;
        let address = document.getElementById("address").value;
        let tuition = document.getElementById("tuition").value;
        let contactNumber = document.getElementById("contactNumber").value;
        let methodcall = './api/sysAdmin/updateSchool/' + sessionStorage.getItem("schoolID") + '/' + schoolname + '/' + address + '/'
            + tuition + '/' + contactNumber;
        xhr.open('POST', methodcall, true);
        xhr.send();
        location.href = 'adminPage.html';
    } else {
        let xhr = new XMLHttpRequest();
        let schoolname = document.getElementById("schoolName").value;
        let address = document.getElementById("address").value;
        let tuition = document.getElementById("tuition").value;
        let contactNumber = document.getElementById("contactNumber").value;
        let methodcall = './api/sysAdmin/addSchool/' + schoolname + '/' + address + '/'
            + tuition + '/' + contactNumber;
        xhr.open('POST', methodcall, true);
        xhr.send();
        location.href = 'adminPage.html';
    }
}

function loadPlaceholders() {
    if((sessionStorage.getItem("school_name"))!='null') {
        document.getElementById("schoolName").value = sessionStorage.getItem("school_name")
    }
    if((sessionStorage.getItem("school_address"))!='null') {
        document.getElementById("address").value = sessionStorage.getItem("school_address")
    }
    if((sessionStorage.getItem("school_tuition"))!='null') {
        document.getElementById("tuition").value = sessionStorage.getItem("school_tuition")
    }
    if((sessionStorage.getItem("school_number"))!='null') {
        document.getElementById("contactNumber").value = sessionStorage.getItem("school_number")
    }
}
