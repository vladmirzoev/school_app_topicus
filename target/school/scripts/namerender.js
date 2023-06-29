function namerender() {
    let schoolID = sessionStorage.getItem("schoolID");

    // to replace the header and bottom left name
    let methodcall = './api/school/' + schoolID;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let obj = JSON.parse(xhr.responseText);
                document.getElementById("bottomleftname").innerText = obj.school_name + " Admin";
            }
        }
    }
    xhr.send();
}