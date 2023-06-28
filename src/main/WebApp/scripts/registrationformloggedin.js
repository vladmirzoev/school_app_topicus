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

function createChild() {
    let guardianid = sessionStorage.getItem("id");
    let childname = document.getElementById("childName").value;
    let dob = document.getElementById("birth_date").value;
    let bsn = document.getElementById("bsn").value;
    let methodcall = "./api/form/createChild/" + guardianid + "/" + childname + "/" + dob + "/" + bsn

    let xhr = new XMLHttpRequest();
    xhr.open('POST', methodcall, true)
    xhr.send();

    openPopup2();
}

function redirect() {
    let school = document.getElementById("dropdown").value;
    sessionStorage.setItem("school", school); //TODO clean this sessionstorage item at some point
    window.location.href = "schoolForm.html";
}