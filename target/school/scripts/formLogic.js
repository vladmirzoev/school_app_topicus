let fieldCounter = 0
let pageHeader = document.getElementById("dashboardHeading")

function listener() {
    if (fieldCounter == 0) {
        pageHeader.innerText = "The form is empty. Click on + to add a field"
    } else {
        pageHeader.innerText = "Form is being edited"
    }
}

function changeBackgroundColor() {
    let bgColor = document.getElementById("backgroundForm").value
    let bodyBG = document.body
    bodyBG.style.backgroundColor = bgColor
}

// function changeBoxColor(){
//     let bColor = document.getElementById("backgroundForm").value
//     let bodyB = document.body
//     bodyBG.style.backgroundColor = bgColor
// }

function render() {
    let xhr = new XMLHttpRequest();
    let schoolID = sessionStorage.getItem("schoolID");
    let methodcall = './api/form/getformsbyschoolid/' + schoolID;
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let obj = JSON.parse(xhr.responseText);
                for (let i = 0; i < obj.length; i++) {
                    addForm(obj[i])
                }
            }
        }
    };
    xhr.send();


}

function renderEditForm() {
    let xhr = new XMLHttpRequest();
    let schoolID = sessionStorage.getItem("schoolID");
    let methodcall = './api/form/getbyid' + '/' + sessionStorage.getItem("formID");
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let obj = JSON.parse(xhr.responseText);
                for (let i = 0; i < obj.fields.length; i++) {
                    addFormFields(obj.fields[i])
                }
            }
        }
    };
    xhr.send();
}

function addFormFields(question) {
    switch (question.input_type) {
        case "text":
            makeText(question);
            break;
        case "email":
            makeEmail(question);
            break;
        case "number":
            makeNumber(question);
            break;
        case "tel":
            makeTel(question);
            break;
        case "date":
            makeDate(question);
            break;
        case "file":
            makeFile(question);
    }
}

function makeText(field) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'textForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = field.question;
    labelElement.setAttribute('contenteditable', 'true');


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
    inputElement.id = 'textForm';
    inputElement.setAttribute('placeholder', 'Enter your text');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })
}

function makeDate(field) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'dateForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = field.question;
    labelElement.setAttribute('contenteditable', 'true');


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
    inputElement.id = 'dateForm';
    inputElement.setAttribute('placeholder', 'Enter your email');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })

// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function makeEmail(field) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'email');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = field.question;
    labelElement.setAttribute('contenteditable', 'true');


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
    inputElement.id = 'email';
    inputElement.setAttribute('placeholder', 'Enter your email');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })

// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function makeNumber(field) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'numberForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = field.question;
    labelElement.setAttribute('contenteditable', 'true');


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
    inputElement.id = 'numberForm';
    inputElement.setAttribute('placeholder', 'xxx-xxx-xx');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })
// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function makeTel(field) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'telForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = field.question;
    labelElement.setAttribute('contenteditable', 'true');


// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the input element
    let inputElement = document.createElement('input');
    inputElement.setAttribute('type', 'tel');
    inputElement.setAttribute('name', 'telForm');
    inputElement.id = 'telForm';
    inputElement.setAttribute('placeholder', 'Enter your phone number');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })
// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function makeFile(field) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('mb-6', 'pt-4');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.classList.add('formbold-form-label', 'formbold-form-label-2');
    labelElement.textContent = field.question;
    labelElement.setAttribute('contenteditable', 'true');


// Create the file input div
    let fileInputDivElement = document.createElement('div');
    fileInputDivElement.classList.add('formbold-mb-5', 'formbold-file-input');

// Create the file input element
    let fileInputElement = document.createElement('input');
    fileInputElement.setAttribute('type', 'file');
    fileInputElement.setAttribute('name', 'file');
    fileInputElement.id = 'file';

// Create the label for the file input
    let fileInputLabelElement = document.createElement('label');
    fileInputLabelElement.setAttribute('id', 'file');

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

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })
// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the label element, file input div, and remove button div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(fileInputDivElement);
    divElement.appendChild(col2DivElement);
    col2DivElement.appendChild(removeBtnDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}


let typeChoice = document.getElementById("fieldsTypes")

function addNewField() {
    typeChoice.style.display = "block"
}

function addForm(el) {
    // Create the outer div element
    let divElement = document.createElement('div');
    divElement.classList.add('formBox', 'visualBox', 'light', 'col-5');
    divElement.style.marginBottom = "20px"

// Create the row div element
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row', 'justify-content-between');

// Create the col-10 div element
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the subheader h3 element
    let subHeaderElement = document.createElement('h3');
    subHeaderElement.classList.add('subHeader');
    subHeaderElement.textContent = el.grade;
    subHeaderElement.style.color = 'black';

// Create the col-2 div element
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the anchor element
    let anchorElement = document.createElement('a');
    anchorElement.setAttribute('href', 'editSchoolForm.html');
    anchorElement.onclick = function () {
        subHeaderElement.id = el.form_id;
        sessionStorage.setItem("formID", subHeaderElement.id)
    }

// Create the edit image element
    let editImageElement = document.createElement('img');
    editImageElement.setAttribute('src', 'images/school/children/edit.svg');
    editImageElement.setAttribute('alt', 'edit form');

// Append the edit image element to the anchor element
    anchorElement.appendChild(editImageElement);

// Append the subheader element to the col-10 div element
    col10DivElement.appendChild(subHeaderElement);

// Append the anchor element to the col-2 div element
    col2DivElement.appendChild(anchorElement);

// Append the col-10 and col-2 div elements to the row div element
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the row div element to the outer div element
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'parentElementId' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function createDate() {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'dateForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Please fill in the official number:';
    labelElement.setAttribute('contenteditable', 'true');

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
    inputElement.id = 'dateForm';
    inputElement.setAttribute('placeholder', 'Enter your email');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })

// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);


}

function createText() {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'textForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Any remarks?';
    labelElement.setAttribute('contenteditable', 'true');

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
    inputElement.id = 'textForm';
    inputElement.setAttribute('placeholder', 'Enter your text');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })

// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);


}

function createEmail() {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'email');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Your email:';
    labelElement.setAttribute('contenteditable', 'true');

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
    inputElement.id = 'email';
    inputElement.setAttribute('placeholder', 'Enter your email');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })

// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);

}

function createNumber() {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'numberForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Please fill in the official number:';
    labelElement.setAttribute('contenteditable', 'true');

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
    inputElement.id = 'numberForm';
    inputElement.setAttribute('placeholder', 'xxx-xxx-xx');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })
// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);


}

function createPhone() {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('id', 'telForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Phone number of a parent:';
    labelElement.setAttribute('contenteditable', 'true');

// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Create the input element
    let inputElement = document.createElement('input');
    inputElement.setAttribute('type', 'tel');
    inputElement.setAttribute('name', 'telForm');
    inputElement.id = 'telForm';
    inputElement.setAttribute('placeholder', 'Enter your phone number');
    inputElement.classList.add('formbold-form-input');

// Create the col-2 div
    let col2DivElement = document.createElement('div');
    col2DivElement.classList.add('col-2');

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })
// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the input element to the col-10 div
    col10DivElement.appendChild(inputElement);

// Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

// Append the col-10 and col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);
    rowDivElement.appendChild(col2DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(rowDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);


}

function createFile() {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('mb-6', 'pt-4');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.classList.add('formbold-form-label', 'formbold-form-label-2');
    labelElement.textContent = 'Upload File';
    labelElement.setAttribute('contenteditable', 'true');

// Create the file input div
    let fileInputDivElement = document.createElement('div');
    fileInputDivElement.classList.add('formbold-mb-5', 'formbold-file-input');

// Create the file input element
    let fileInputElement = document.createElement('input');
    fileInputElement.setAttribute('type', 'file');
    fileInputElement.setAttribute('name', 'file');
    fileInputElement.id = 'file';

// Create the label for the file input
    let fileInputLabelElement = document.createElement('label');
    fileInputLabelElement.setAttribute('id', 'file');

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

// Create the remove button div
    let removeBtnDivElement = document.createElement('div');
    removeBtnDivElement.classList.add('removeBtn');
    // removeBtnDivElement.setAttribute('onclick', 'deleteField()');
    removeBtnDivElement.addEventListener("click", function () {
        divElement.remove()
        fieldCounter--
        listener()
    })
// Create the remove button image
    let removeBtnImageElement = document.createElement('img');
    removeBtnImageElement.setAttribute('src', 'images/forms/binDelete.svg');

// Append the remove button image to the remove button div
    removeBtnDivElement.appendChild(removeBtnImageElement);

// Append the label element, file input div, and remove button div to the main div
    divElement.appendChild(labelElement);
    divElement.appendChild(fileInputDivElement);
    divElement.appendChild(col2DivElement);
    col2DivElement.appendChild(removeBtnDivElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('path'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);

}


function createField() {
    let selectedOption = document.querySelector('input[name="newField"]:checked').value;
    switch (selectedOption) {
        case 'text':
            createText()
            break;
        case 'email':
            createEmail()
            break;
        case 'number':
            createNumber()
            break;
        case 'phone':
            createPhone()
            break;
        case 'date':
            createDate()
            break;
        case 'file':
            createFile()
            break;
    }
    fieldCounter++
    typeChoice.style.display = "none"
    listener()
}

function submitForm() {
    let formID = sessionStorage.getItem("formID")

    //delete the form
    let deletecall = "./api/form/deleteForm/" + formID;
    let deletexhr = new XMLHttpRequest();
    deletexhr.open("DELETE", deletecall, true);
    deletexhr.send();

    let fields = document.getElementsByClassName("formbold-form-label");
    for (let i = 0; i < fields.length; i++) {
        let question = fields[i].textContent;
        let input_type;
        let label = fields[i].id;
        switch (label) {
            case 'textForm':
                input_type = 'text';
                break;
            case 'email':
                input_type = 'email';
                break;
            case 'numberForm':
                input_type = 'number';
                break;
            case 'telForm':
                input_type = 'tel';
                break;
            case 'dateForm':
                input_type = 'date';
                break;
            default:
                input_type = 'file';
        }

        //creates the form in the db
        let methodcall = './api/form/field/' + formID + '/' + question + '/' + input_type;
        console.log(methodcall);
        xhr = new XMLHttpRequest();
        xhr.open('POST', methodcall, true);
        xhr.send()
    }
    let maxgrade = document.getElementById("endingYear")
    let gradexhr = new XMLHttpRequest();
    let methodcall = "./api/setformgrade/" + formID + "/" + maxgrade;
    gradexhr.open("POST", methodcall, true);
    gradexhr.send();

    let timeout;
    setTimeout(function () { location.href = 'schoolForms.html' }, 3000);
}

function getLabel(el) {
    let idVal = el.id;
    let labels = document.getElementsByTagName('label');
    for (let i = 0; i < labels.length; i++) {
        if (labels[i].htmlFor == idVal)
            return labels[i];
    }
}

function createNewForm() {
    let schoolID = sessionStorage.getItem("schoolID")
    let methodcall = './api/form/' + schoolID;
    xhr = new XMLHttpRequest();
    xhr.open('POST', methodcall, true);
    xhr.send()

    location.reload();
}

// function deleteField() {
//     fieldCounter--
// }