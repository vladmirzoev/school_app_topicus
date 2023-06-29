let popup = document.getElementById("popup")
let popup2 = document.getElementById("popup2")
function openPopup() {
    popup.classList.add("open-popup")
}
function closePopup() {
    popup.classList.remove("open-popup")
}

function openPopup2() {
    popup2.classList.add("open-popup")
}
function closePopup2() {
    popup2.classList.remove("open-popup")
}



function enableSubmit(){
    let inputs = document.getElementsByClassName('required')
    let btn = document.querySelector('input[type="button"]')
    const bsn = document.getElementById("bsn")
    const grade = document.getElementById("grade")
    let isValid = true
    for (let i = 0; i < inputs.length; i++){
        let changedInput = inputs[i]
        if (changedInput.value.trim() === "" || changedInput.value === null){
            document.getElementById("message1").innerHTML = "*Fill in all the required information*"
            isValid = false
            break
        }
        else if (!bsn.validity.valid){
            document.getElementById("message1").innerHTML = "*Insert a valid BSN*"
            isValid = false
            break
        }
        else if (!grade.validity.valid){
            document.getElementById("message1").innerHTML = "*Insert a valid grade (1-12th)*"
            isValid = false
            break
        }
        else if(!(changedInput.value.trim() === "") || !(changedInput.value === null)){
            document.getElementById("message1").innerHTML = ""
        }
        else if (bsn.validity.valid){
            document.getElementById("message1").innerHTML = ""
        }
        else if (grade.validity.valid){
            document.getElementById("message1").innerHTML = ""
        }
    }
    btn.disabled = !isValid
}

function enableSubmit2(){
    let inputs = document.getElementsByClassName('required')
    let btn = document.querySelector('input[type="button"]')
    const email = document.getElementById("email")
    let pass = document.getElementById("pass").value
    let conf_pass = document.getElementById("conf_pass").value
    let isValid = true
    for (var i = 0; i < inputs.length; i++){
        let changedInput = inputs[i]
        if (changedInput.value.trim() === "" || changedInput.value === null){
            document.getElementById("message2").innerHTML = "*Fill in all the required information*"
            isValid = false
            break
        }
        else if (!email.validity.valid){
            document.getElementById("message2").innerHTML = "*Insert a valid email*"
            isValid = false
            break
        }
        else if (pass !== conf_pass){
            document.getElementById("message2").innerHTML = "*Passwords are not the same!*"
            isValid = false
            break
        }
        else if (pass === conf_pass){
            document.getElementById("message2").innerHTML = ""
        }
        else if (email.validity.valid || email.value.trim() === ""){
            document.getElementById("message2").innerHTML = ""
        }
        else if (!(changedInput.value.trim() === "") || !(changedInput.value === null)){
            document.getElementById("message2").innerHTML = ""
        }
    }
    btn.disabled = !isValid
}

function enableSubmit3(){
    let inputs = document.getElementsByClassName('required')
    let btn = document.querySelector('input[type="submit"]')
    const bsn = document.getElementById("bsn")
    const grade = document.getElementById("grade")
    let isValid = true
    for (let i = 0; i < inputs.length; i++){
        let changedInput = inputs[i]
        if (changedInput.value.trim() === "" || changedInput.value === null){
            document.getElementById("message1").innerHTML = "*Fill in all the required information*"
            isValid = false
            break
        }
        else if (!bsn.validity.valid){
            document.getElementById("message1").innerHTML = "*Insert a valid BSN*"
            isValid = false
            break
        }
        else if (!grade.validity.valid){
            document.getElementById("message1").innerHTML = "*Insert a valid grade (1-12th)*"
            isValid = false
            break
        }
        else if(!(changedInput.value.trim() === "") || !(changedInput.value === null)){
            document.getElementById("message1").innerHTML = ""
        }
        else if (bsn.validity.valid){
            document.getElementById("message1").innerHTML = ""
        }
        else if (grade.validity.valid){
            document.getElementById("message1").innerHTML = ""
        }
    }
    btn.disabled = !isValid
}
function startMessage(){
    let message = document.getElementById("startMessage")
    let btn = document.getElementById("btn-message")
    message.style.display = "block"
    btn.style.display = "none"
}