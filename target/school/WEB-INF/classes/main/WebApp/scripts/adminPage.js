function addNewSchool(){
    // Create a new row element
    var newRowElement = document.createElement('div');
    newRowElement.classList.add('row');

// Create the first column element
    var col1Element = document.createElement('div');
    col1Element.classList.add('col-4', 'form-input');

// Create the element for School Name
    var schoolNameButton = document.createElement('text');
    schoolNameButton.style.background = '#FFFFFF';
    schoolNameButton.textContent = 'School Name';
    schoolNameButton.contentEditable = 'true';

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
    deleteButton.addEventListener('click', function() {
        // Remove the newRowElement from its parent container
        newRowElement.parentNode.removeChild(newRowElement);
    });

// Append the Delete button to the third column
    col3Element.appendChild(deleteButton);

// Create the fourth column element
    var col4Element = document.createElement('div');
    col4Element.classList.add('col-4');

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