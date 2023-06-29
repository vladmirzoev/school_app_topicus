function getValues() {
    let fname = document.getElementById("fname").value;
    let lname = document.getElementById("lname").value;
    let email = document.getElementById("email").value;
    let p_no1 = document.getElementById("p_no1").value;
    let p_no2 = document.getElementById("p_no2").value;
    let address = document.getElementById("address").value;
    let pass = document.getElementById("pass").value;
    let conf_pass = document.getElementById("conf_pass").value;

    createAccount(fname, lname, email, p_no1, p_no2, address, pass)
}

function createAccount(fname, lname, email, p_no1, p_no2, address, pass) {

    //TODO security issue, people can inspect and use this method call
    let methodcall = "./api/signup/newaccount/" + fname + "/" + lname + "/" + email + "/" + p_no1 + "/" + p_no2 + "/" + address + "/" + pass;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', methodcall, true);
    xhr.send();
    openPopup2();
}

function redirect() {
    window.location.href = "login.html";
}