function storeSessionRegular(){
    let id = document.getElementById("regularEmailValue").value;
    sessionStorage.setItem("id", id)
}

function storeSessionAdmin(){
    let id = document.getElementById("adminEmailValue").value;
    sessionStorage.setItem("id", id)
}