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
    let btn = document.querySelector('input[type="submit"]')
    let isValid = true
    for (var i = 0; i < inputs.length; i++){
        let changedInput = inputs[i]
        if (changedInput.value.trim() === "" || changedInput.value === null){
            isValid = false
            break
        }
    }
    // if (!(typeof inputs[4] === "email")){
    //     isValid = false
    // }

    // if (!(inputs[4].includes("@"))){
    //     isValid = false
    // }
    btn.disabled = !isValid

}