function render() {
    let schoolname = sessionStorage.getItem("schoolname");
    let grade = sessionStorage.getItem("grade");

    let xhr = new XMLHttpRequest();
    let methodcall = "./api/form/" + schoolname + "/" + grade;
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {

            }
        }
    }
    xhr.send();
}