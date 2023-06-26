let tab = document.querySelector("tab-form");

function adminLogIn() {
    let adminTab = document.getElementsByClassName('LogInAdmin')
    adminTab.item(0).style.display = "block"
    let userTab = document.getElementsByClassName("LogInParent")
    userTab.item(0).style.display = "none"
    let adminbtn = document.getElementById("adminLogIn-btn")
    adminbtn.classList.add("active")
    let parentbtn = document.getElementById("parentLogIn-btn")
    parentbtn.classList.remove("active")
}

function parentLogIn() {
    let userTab = document.getElementsByClassName('LogInParent')
    userTab.item(0).style.display = "block"
    let adminTab = document.getElementsByClassName("LogInAdmin")
    adminTab.item(0).style.display = "none"
    let adminbtn = document.getElementById("adminLogIn-btn")
    adminbtn.classList.remove("active")
    let parentbtn = document.getElementById("parentLogIn-btn")
    parentbtn.classList.add("active")
}

function logInCode() {
    let loginNormal = document.getElementsByClassName("loginNormal")
    let loginCode = document.getElementsByClassName("loginCode")
    loginNormal.item(0).style.display = "none"
    loginCode.item(0).style.display = "block"
}

function logInNormal() {
    let loginNormal = document.getElementsByClassName("loginNormal")
    let loginCode = document.getElementsByClassName("loginCode")
    loginNormal.item(0).style.display = "block"
    loginCode.item(0).style.display = "none"
}


// let tabHeader = tab.querySelector("tab-header");
// let tabHeaderElements = tab.querySelectorAll("tab-header>div");
// let tabBody = tab.querySelector("tab-body");
// let tabBodyElements = tab.querySelectorAll("tab-body>div");
//
// function switchLog() {
//     for(let i=0; i<tabHeaderElements.length; i++){
//         tabHeaderElements[i].addEventListener("click", function (){
//             tabHeader.querySelector("active").classList.remove("active");
//             tabHeaderElements[i].classList.add("active");
//             tabBody.querySelector("active").classList.remove("active");
//             tabBodyElements[i].classList.add("active");
//         })
//     }
//
// }