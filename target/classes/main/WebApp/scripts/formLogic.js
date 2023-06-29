let fieldCounter = 0
let pageHeader = document.getElementById("dashboardHeading")

function listener() {
    if (fieldCounter !== 0) {
        pageHeader.innerText = "Form is being edited"
    } else {
        pageHeader.innerText = "The form is empty. Click a + to add a field"
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


let typeChoice = document.getElementById("fieldsTypes")

function addNewField() {
    typeChoice.style.display = "block"
}

function createDate() {
    // Create the div element
    let divElement = document.createElement('div');
    divElement.classList.add('formbold-mb-5');

// Create the label element
    let labelElement = document.createElement('label');
    labelElement.setAttribute('for', 'dateForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Please fill in the official number:';

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
    labelElement.setAttribute('for', 'textForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Any remarks?';

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
    labelElement.setAttribute('for', 'email');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Your email:';

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
    labelElement.setAttribute('for', 'numberForm');
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
    labelElement.setAttribute('for', 'telForm');
    labelElement.classList.add('formbold-form-label');
    labelElement.textContent = 'Phone number of a parent:';

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

function createHeader() {
    //TODO createHeader case
}

function createField() {
    let selectedOption = document.querySelector('input[name="newField"]:checked').value;
    switch (selectedOption) {
        case 'header':
            createHeader()
            break;
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

// function deleteField() {
//     fieldCounter--
// }