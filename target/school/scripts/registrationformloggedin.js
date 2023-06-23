function createChild() {
    let guardianid = sessionStorage.getItem("id");
    let childname = document.getElementById("childName").value;
    let dob = document.getElementById("birth_date").value;
    let bsn = document.getElementById("bsn").value;
    let methodcall = "./api/form/createChild/" + guardianid + "/" + childname + "/" + dob + "/" + bsn

    let xhr = new XMLHttpRequest();
    xhr.open('POST', methodcall, true)
    xhr.send();
    window.location.href = "children.html";
}