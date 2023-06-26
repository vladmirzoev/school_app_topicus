function storeSessionRegular(){
    var id = document.getElementById("regularEmailValue").value;
    sessionStorage.setItem("id", id)
}

function storeSessionAdmin(){
    var id = document.getElementById("adminEmailValue").value;
    sessionStorage.setItem("id", id)
}