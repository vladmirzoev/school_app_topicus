function render() {
    let methodcall = './api/school/getschools/';
    let req = new XMLHttpRequest();
    req.open('GET', methodcall, true);
    req.onreadystatechange = function () {
        if (req.readyState === XMLHttpRequest.DONE) {
            if (req.status === 200) {
                let obj = JSON.parse(req.responseText);
                for (let i = 0; i < obj.length; i++) {
                    let x = document.getElementById("dropdown")
                    let y = document.createElement("option")
                    y.innerText = obj[i].school_name;
                    y.value = obj[i].school_name;
                    x.append(y)
                }
            }
        }
    };
    req.send();
}

function check() {
    let valid = true;

    let childname = document.getElementById("childname").value;
    let guardianname = document.getElementById("guardianname").value;
    let telephone1 = document.getElementById("telephone1").value;
    let telephone2 = document.getElementById("telephone2").value;
    let bsn = document.getElementById("bsn").value;
    let birthdate = document.getElementById("birthdate").value;
    let address = document.getElementById("address").value;
    let grade = document.getElementById("grade").value;
    let schoolname = document.getElementById("dropdown").value;

    //TODO elaborate on input validation

    if (!childname) {
        document.getElementById("childname").style.borderColor = "#FF0000";
        document.getElementById("childname").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (!guardianname) {
        document.getElementById("guardianname").style.borderColor = "#FF0000";
        document.getElementById("guardianname").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (!telephone1) {
        document.getElementById("telephone1").style.borderColor = "#FF0000";
        document.getElementById("telephone1").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (!telephone2) {
        document.getElementById("telephone2").style.borderColor = "#FF0000";
        document.getElementById("telephone2").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (!bsn) {
        document.getElementById("bsn").style.borderColor = "#FF0000";
        document.getElementById("bsn").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (!address) {
        document.getElementById("address").style.borderColor = "#FF0000";
        document.getElementById("address").style.backgroundColor = "#FFBFBF";
        valid = false;

    }

    if (!grade) {
        document.getElementById("grade").style.borderColor = "#FF0000";
        document.getElementById("grade").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (!birthdate) {
        document.getElementById("birthdate").style.borderColor = "#FF0000";
        document.getElementById("birthdate").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (valid) {
        submitform(childname, guardianname, telephone1, telephone2, bsn, birthdate, address, grade, schoolname);
    }
}

function submitform(childname, guardianname, telephone1, telephone2, bsn, birthdate, address, grade, schoolname) {

    //TODO this is a security issue if people inspect this script
    let methodcall = './api/form/uploadBasicReqNoAccount/' + childname + '/' + guardianname + '/' + telephone1 + '/' + telephone2 + '/' + bsn + '/' + birthdate + '/' + grade + '/' + schoolname + '/' + address;
    console.log(methodcall)
    xhr = new XMLHttpRequest();
    xhr.open('POST', methodcall, true);
    xhr.send()

    openPopup2();
}

function redirect(){
    //TODO redirect to school form
    window.location.href = "login.html";
}

//TODO get specific school form