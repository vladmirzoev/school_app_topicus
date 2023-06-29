//TODO function to check the role of the account, to display admin resources

function render() {
    let schoolname = sessionStorage.getItem("school");
    let grade = sessionStorage.getItem("grade");

    let xhr = new XMLHttpRequest();
    let methodcall = "./api/form/" + schoolname + "/" + grade;
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                //TODO if there are no forms for a grade or for a school, do something else

                //parses the json object into an array
                let obj = JSON.parse(xhr.responseText);
                let formfields = obj.fields;
                let fields = [];
                for (let i = 0; i < formfields.length; i++) {
                    let currentfield = [];
                    currentfield[0] = formfields[i].question_id;
                    currentfield[1] = formfields[i].input_type;
                    currentfield[2] = formfields[i].question;
                    fields[i] = currentfield;
                }
                console.log(fields);

                //parses form details into divs and fields
                let formtitle = document.getElementById("formtitle");
                formtitle.innerText = obj.school_name + " Application Form";

                for (let i = 0; i < fields.length; i++) {
                    switch (fields[i][1]) {
                        case "header":
                            createHeader(fields[i][2]);
                            break;
                        case "text":
                            createText(fields[i][2]);
                            break;
                        case "email":
                            createEmail(fields[i][2]);
                            break;
                        case "number":
                            createNumber(fields[i][2]);
                            break;
                        case "tel":
                            createPhone(fields[i][2]);
                            break;
                        case "date":
                            createDate(fields[i][2]);
                            break;
                        case "file":
                            createFile(fields[i][2]);
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

function createHeader(question) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the h4 element
    let headerElement = document.createElement('h4');
    headerElement.setAttribute('for', 'textForm');
    headerElement.classList.add('subHeader');
    headerElement.textContent = question;

// Create the row div
    let rowDivElement = document.createElement('div');
    rowDivElement.classList.add('row');

// Create the col-10 div
    let col10DivElement = document.createElement('div');
    col10DivElement.classList.add('col-10');

// Append the col-2 divs to the row div
    rowDivElement.appendChild(col10DivElement);

// Append the label element and row div to the main div
    divElement.appendChild(headerElement);

// Append the div element to the desired parent element in the DOM
    let parentElement = document.getElementById('maindiv'); // Replace 'path' with the actual ID of the desired parent element
    parentElement.appendChild(divElement);
}

function createDate(question) {
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
    inputElement.id = 'dateForm';
    inputElement.setAttribute('placeholder', 'Enter your email');
    inputElement.classList.add('formbold-form-input');

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

function createText(question) {
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
    inputElement.id = 'textForm';
    inputElement.setAttribute('placeholder', 'Enter your text');
    inputElement.classList.add('formbold-form-input');

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

function createEmail(question) {
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
    inputElement.id = 'email';
    inputElement.setAttribute('placeholder', 'Enter your email');
    inputElement.classList.add('formbold-form-input');

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

function createNumber(question) {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'numberForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = question;
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

function createPhone(question) {
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
    inputElement.setAttribute('type', 'tel');
    inputElement.setAttribute('name', 'telForm');
    inputElement.id = 'telForm';
    inputElement.setAttribute('placeholder', 'Enter your phone number');
    inputElement.classList.add('formbold-form-input');

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

function createFile(question) {
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
    fileInputElement.id = 'file';

// Create the label for the file input
    let fileInputLabelElement = document.createElement('label');
    fileInputLabelElement.setAttribute('for', 'file');

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

function editmode() {
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

    // Append the remove button div to the col-2 div
    col2DivElement.appendChild(removeBtnDivElement);

    // Append the col-2 divs to the row div
    rowDivElement.appendChild(col2DivElement);
}