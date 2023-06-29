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

function submitform() {
    let childname = document.getElementById("childName").value;
    let guardianname = document.getElementById("guardianName").value;
    let telephone1 = document.getElementById("telephone1").value;
    let telephone2 = document.getElementById("telephone2").value;
    let bsn = document.getElementById("bsn").value;
    let birthdate = document.getElementById("birth_date").value;
    let address = document.getElementById("address").value;
    let grade = document.getElementById("grade").value;
    let schoolname = document.getElementById("dropdown").value;

    if (!telephone2) {
        telephone2 = null;
    }

    let methodcall = './api/form/uploadBasicReqNoAccount/' + childname + '/' + guardianname + '/' + telephone1 + '/' + telephone2 + '/' + bsn + '/' + birthdate + '/' + grade + '/' + schoolname + '/' + address;

    //store the school name to fetch the form in the next page
    sessionStorage.setItem("school", schoolname);

    xhr = new XMLHttpRequest();
    xhr.open('POST', methodcall, true);
    xhr.send();
    openPopup2();
}

function redirect() {
    window.location.href = "schoolForm.html";
}