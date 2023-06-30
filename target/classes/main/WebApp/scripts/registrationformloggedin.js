function render() {
    let methodcall = './api/school/getschools/';
    let xhr = new XMLHttpRequest();
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let obj = JSON.parse(xhr.responseText);
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
    xhr.send();
}

function createChild() {
    let guardianid = sessionStorage.getItem("id");
    let childname = document.getElementById("childName").value;
    let dob = document.getElementById("birth_date").value;
    let bsn = document.getElementById("bsn").value;
    let grade = document.getElementById("grade").value;
    let schoolname = document.getElementById("dropdown").value;

    //store the school name and grade to fetch the form in the next page
    //the bsn stored in regID is a placeholder
    sessionStorage.setItem("school", schoolname);
    sessionStorage.setItem("grade", grade);
    sessionStorage.setItem("regID", bsn);

    let xhr = new XMLHttpRequest();
    let methodcall = "./api/form/createChild/" + guardianid + "/" + childname + "/" + dob + "/" + bsn + "/" + grade + "/" + schoolname
    xhr.open('POST', methodcall, true);
    xhr.send();

    openPopup2();
}

function redirect() {
    window.location.href = "schoolForm.html";
}
