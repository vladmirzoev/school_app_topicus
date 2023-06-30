let fieldcounter = 0;
let fileidlist = [];
let questionidlist = [];
let isA = false;
let isG = false;

function render() {
    fetchRegistrationID();
    checkRole();
    createDivs();
}

function fetchRegistrationID() {
    //removes the bsn placeholder and replaces it with the actual registration id
    //it might be because of a concurrency issue, but placing this in the registration form javascript
    //did not return the registration id in time, when it was clearly in the database
    let xhr2 = new XMLHttpRequest();
    let methodcall2 = "./api/registration/fetchRegId/" + sessionStorage.getItem("regID");
    xhr2.open('GET', methodcall2, true);
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === XMLHttpRequest.DONE) {
            if (xhr2.status === 200) {
                let obj = JSON.parse(xhr2.responseText);
                sessionStorage.removeItem("regID")
                sessionStorage.setItem("regID", obj.registration_id);

                //for the ghost account, if there are no ids
                if (!sessionStorage.getItem("id")) {
                    sessionStorage.setItem("id", obj.registration_id + "@studieportal.nl")
                    isG = true;
                }
            }
        }
    }
    xhr2.send();
}

function createDivs() {
    let schoolname = sessionStorage.getItem("school");
    let grade = sessionStorage.getItem("grade");
    let xhr = new XMLHttpRequest();
    let methodcall = "./api/form/" + schoolname + "/" + grade;
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                //TODO if there are no forms for a grade or for a school, do something else

                //parses the json object into an array to be used for the divs
                //a list of the question ids will be stored in the questionidlist global variable
                //this list will be used for making responses
                let obj = JSON.parse(xhr.responseText);
                let formfields = obj.fields;
                let fields = [];
                for (let i = 0; i < formfields.length; i++) {
                    let currentfield = [];
                    currentfield[0] = formfields[i].question_id;
                    currentfield[1] = formfields[i].input_type;
                    currentfield[2] = formfields[i].question;
                    fields[i] = currentfield;
                    questionidlist[i] = formfields[i].question_id;
                    if (formfields[i].input_type === "file") {
                        fileidlist.push(formfields[i].question_id)
                    }
                }

                //parses form details into divs and fields
                let formtitle = document.getElementById("formtitle");
                formtitle.innerText = obj.school_name + " Application Form";

                for (let i = 0; i < fields.length; i++) {
                    switch (fields[i][1]) {
                        case "text":
                            createText(fields[i][0], fields[i][2]);
                            fieldcounter++;
                            break;
                        case "email":
                            createEmail(fields[i][0], fields[i][2])
                            fieldcounter++;
                            break;
                        case "number":
                            createNumber(fields[i][0], fields[i][2]);
                            fieldcounter++;
                            break;
                        case "tel":
                            createPhone(fields[i][0], fields[i][2]);
                            fieldcounter++;
                            break;
                        case "date":
                            createDate(fields[i][0], fields[i][2]);
                            fieldcounter++;
                            break;
                        case "file":
                            createFile(fields[i][0], fields[i][2]);
                            fieldcounter++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
    xhr.send();
}

function checkRole() {
    //checks the db based on the account id what role the user has for redirects
    //for a ghost account, the role is decided if they don't have an id in the first place
    //isA defines whether they're an admin, and will redirect them back to schoolforms.html
    //if neither conditions apply, it is assumed that the user is a regular parent and will redirect back to children.html
    let id = sessionStorage.getItem("id");
    let xhr = new XMLHttpRequest();
    let methodcall = "./api/account/checkrole/" + id;
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let obj = JSON.parse(xhr.responseText);
                if (obj.role === 'A') {
                    editmode();
                    isA = true;
                }
            }
        }
    }
    xhr.send();
}

function redirect() {
    let id = sessionStorage.getItem("id");

    //if they are a ghost account, redirect to index.html
    if (id.includes("@studieportal.nl")) {
        window.location.href = "index.html";
    } else if (isA) {
        window.location.href = "schoolForms.html";
    } else {
        window.location.href = "children.html"
    }
}

function createDate(id, question) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

    // Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'dateForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = question;

// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the input element
    let inputElement = document.createElement('input');
    inputElement.setAttribute('type', 'date');
    inputElement.setAttribute('name', 'dateForm');
    inputElement.id = id;
    inputElement.setAttribute('placeholder', 'Please enter a date');
    inputElement.classList.add('formbold-form-input');
    inputElement.classList.add('required');
    inputElement.onkeyup = function () {
        enableSubmit5()
    };

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('maindiv'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function createText(id, question) {
// Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'textForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = question;

// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the input element
    let inputElement = document.createElement('input');
    inputElement.setAttribute('type', 'text');
    inputElement.setAttribute('name', 'text');
    inputElement.id = id;
    inputElement.setAttribute('placeholder', 'Enter text here');
    inputElement.classList.add('formbold-form-input');
    inputElement.classList.add('required');
    inputElement.onkeyup = function () {
        enableSubmit5()
    };

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('maindiv'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function createEmail(id, question) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'email');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = question;

// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the input element
    let inputElement = document.createElement('input');
    inputElement.setAttribute('type', 'email');
    inputElement.setAttribute('name', 'email');
    inputElement.id = id;
    inputElement.setAttribute('placeholder', 'johndoe@example.com');
    inputElement.classList.add('formbold-form-input');
    inputElement.classList.add('required');
    inputElement.onkeyup = function () {
        enableSubmit5()
    };

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('maindiv'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);

}

function createNumber(id, question) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'numberForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = question;

// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the input element
    let inputElement = document.createElement('input');
    inputElement.setAttribute('type', 'number');
    inputElement.setAttribute('name', 'numberForm');
    inputElement.id = id;
    inputElement.setAttribute('placeholder', 'Please enter a number');
    inputElement.classList.add('formbold-form-input');
    inputElement.classList.add('required');
    inputElement.onkeyup = function () {
        enableSubmit5()
    };

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('maindiv'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);


}

function createPhone(id, question) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'telForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = question;

// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the input element
    let inputElement = document.createElement('input');
    inputElement.setAttribute('type', 'text');
    inputElement.setAttribute('name', 'telForm');
    inputElement.id = id;
    inputElement.setAttribute('placeholder', 'Please enter a phone number (xxx-xxx-xxx)');
    inputElement.classList.add('formbold-form-input');
    inputElement.classList.add('required');
    inputElement.onkeyup = function () {
        enableSubmit5()
    };

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('maindiv'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);


}

function createFile(id, question) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('mb-6', 'pt-4');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.classList.add('formbold-form-label', 'formbold-form-label-2');
    labelElement.textContent = question;

// Create the file input div
    let fileInputDivElement = document.createElement('div');
    fileInputDivElement.classList.add('formbold-mb-5', 'formbold-file-input');

// Create the file input element
    let fileInputElement = document.createElement('input');
    fileInputElement.setAttribute('type', 'file');
    fileInputElement.setAttribute('name', 'file');
    fileInputElement.id = id;
    fileInputElement.classList.add('required');
    fileInputElement.onclick = function () {
        enableSubmit5()
    };

// Create the label for the file input
    let fileInputLabelElement = document.createElement('label');
    fileInputLabelElement.setAttribute('for', id);

// Create the inner div for the file input label
    let fileInputLabelDivElement = document.createElement('div');

// Create the span elements for the file input label
    let dropFileSpanElement = document.createElement('span');
    dropFileSpanElement.classList.add('formbold-drop-file');
    dropFileSpanElement.textContent = 'Drop files here';

    let orSpanElement = document.createElement('span');
    orSpanElement.classList.add('formbold-or');
    orSpanElement.textContent = 'Or';

    let browseSpanElement = document.createElement('span');
    browseSpanElement.classList.add('formbold-browse');
    browseSpanElement.textContent = 'Browse';

// Append the span elements to the inner div
    fileInputLabelDivElement.appendChild(dropFileSpanElement);
    fileInputLabelDivElement.appendChild(orSpanElement);
    fileInputLabelDivElement.appendChild(browseSpanElement);

// Append the inner div to the label for the file input
    fileInputLabelElement.appendChild(fileInputLabelDivElement);

// Append the file input to the file input div
    fileInputDivElement.appendChild(fileInputElement);
    fileInputDivElement.appendChild(fileInputLabelElement);

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Append the label element, file input div, and remove button div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(fileInputDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('maindiv'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);

}

function submit() {
    let registration_id = sessionStorage.getItem("regID");

    //iterate through all the question ids stored in the list and get their values
    //parse each value through the xhr to post a response in the db
    for (let i = 0; i < fieldcounter; i++) {
        if (fileidlist.includes(questionidlist[i])) {
            //honestly, we do not know how to approach this in time
        } else {
            let response = document.getElementById(questionidlist[i]).value;
            let methodcall = "./api/response/sendresponse/" + registration_id + "/" + questionidlist[i] + "/" + response;
            let xhr = new XMLHttpRequest();
            xhr.open('POST', methodcall, true);
            xhr.send();
        }
    }

    if (isG) {
        let popuptext = document.getElementById("popuptext");
        popuptext.innerText = "Your application has been forwarded to the school.";
        popuptext.append(document.createElement("br"));
        popuptext.append(document.createElement("br"));
        popuptext.append("You may view your registration or sign up to StudiePortal with")
        popuptext.append(document.createElement("br"));
        let boldId = document.createElement("b");
        boldId.innerText = sessionStorage.getItem("id");
        popuptext.append(boldId);
    }

    openPopup2();
}
