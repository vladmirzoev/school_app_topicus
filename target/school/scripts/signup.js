function check() {
    let valid = true;

    let fname = document.getElementById("fname").value;
    let lname = document.getElementById("lname").value;
    let email = document.getElementById("email").value;
    let p_no1 = document.getElementById("p_no1").value;
    let p_no2 = document.getElementById("p_no2").value;
    let address = document.getElementById("address").value;
    let pass = document.getElementById("pass").value;
    let conf_pass = document.getElementById("conf_pass").value;

    let email_format = "^(.+)@(.+)$";
    let pattern = new RegExp(email_format);
    let matcher = pattern.test(email);

    if (!fname || !lname) {
        document.getElementById("fname").style.borderColor = "#FF0000";
        document.getElementById("lname").style.borderColor = "#FF0000";
        document.getElementById("fname").style.backgroundColor = "#FFBFBF";
        document.getElementById("lname").style.backgroundColor = "#FFBFBF";

        document.getElementById("fname").placeholder = "Please enter your first name"
        document.getElementById("lname").placeholder = "Please enter your last name"
        valid = false;
    }

    //TODO matcher isnt working
    // if (!email || !matcher.test(email)) {
    if (!email) {
        console.log("Invalid email format ... Please re-enter your email.")
        document.getElementById("email").style.borderColor = "#FF0000";
        document.getElementById("email").style.backgroundColor = "#FFBFBF";

        document.getElementById("email").placeholder = "Please enter a valid e-mail"
        valid = false;
    }

    //TODO check for 8 characters, 1 number, 1 capital letter, 1 special character
    if (pass !== conf_pass) {
        console.log("The passwords do not match ... Please re-enter the same password")
        document.getElementById("pass").style.borderColor = "#FF0000";
        document.getElementById("conf_pass").style.borderColor = "#FF0000";
        document.getElementById("pass").style.backgroundColor = "#FFBFBF";
        document.getElementById("conf_pass").style.backgroundColor = "#FFBFBF";
        valid = false;

    }
    if (!address) {
        document.getElementById("address").style.borderColor = "#FF0000";
        document.getElementById("address").style.backgroundColor = "#FFBFBF";

        document.getElementById("address").placeholder = "Please enter your address"
        valid = false;

    }

    //TODO check for valid phone numbers
    // if (p_no1 !== 10) {
    if (!p_no1) {
        console.log("Invalid phone number entered ... Please re-enter a valid phone number.")
        document.getElementById("p_no1").style.borderColor = "#FF0000";
        document.getElementById("p_no1").style.backgroundColor = "#FFBFBF";
        valid = false;

    }



    // if (!(!p_no2) && p_no2.length !== 10) {
    if (!(!p_no2) && !p_no2) {
        console.log("Invalid phone number entered ... Please re-enter a valid phone number.")
        document.getElementById("p_no2").style.borderColor = "#FF0000";
        document.getElementById("p_no2").style.backgroundColor = "#FFBFBF";
        valid = false;
    }

    if (valid) {
        createAccount(fname, lname, email, p_no1, p_no2, address, pass)
    }
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