/* Set the width of the sidebar to 250px and the left margin of the page content to 250px */
let opButton = document.getElementById("openBtn")
opButton.style.transitionDelay = "0.2s"
let clButton = document.getElementById("closeBtn")
let text = document.getElementsByClassName("sideMenuText")
let icons = document.getElementsByClassName("menuPicture")
let logText = document.getElementById("logInText")
function openNav() {
    document.getElementById("sideBar").style.width = "250px";
    for (let i = 0; i < text.length; i++){
        text.item(i).style.display = "block"
        icons.item(i).style.width = '33.33333333%'
    }
    opButton.style.display = "none"
    clButton.style.display = "block"
    logText.style.display = "block"
}

/* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
function closeNav() {
    document.getElementById("sideBar").style.width = "96px";
    for (let i = 0; i < text.length; i++){
        text.item(i).style.display = "none"
        icons.item(i).style.width = "75px"
        icons.item(i).style.margin = "20px 0px"
    }
    clButton.style.display = "none"
    opButton.style.display = "block"
    logText.style.display = "none"
}