function render() {
    //creates registration list
    let id = sessionStorage.getItem("id");
    let methodcall1 = './api/schooladmin/getschoolregistrations/' + id;
    let xhr1 = new XMLHttpRequest();
    xhr1.open('GET', methodcall1, true);
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === XMLHttpRequest.DONE) {
            if (xhr1.status === 200) {
                let blackbox = document.getElementById("registrations")
                let students = JSON.parse(xhr1.responseText);
                let regs = [];
                //gets all students into an array
                for (let i = 0; i < students.length; i++) {
                    let reg = [];
                    reg[0] = students[i].id;
                    reg[1] = students[i].grade;
                    reg[2] = students[i].registration_date;
                    reg[3] = students[i].student_id;
                    reg[4] = students[i].name;
                    reg[5] = students[i].school_id;
                    reg[6] = students[i].status;
                    reg[7] = students[i].allowedit;
                    regs[i] = reg;

                    //creates a registration entry
                    let whitebox = document.createElement("div")
                    whitebox.className = "row messageData light visualBox";
                    whitebox.id = "neededData";
                    whitebox.onclick = function () {
                        sessionStorage.setItem("regID", reg[0]);
                        sessionStorage.setItem("studentID", reg[3]);
                        openPopup2();
                    }

                    //creates the top row full of headers
                    let toprow = document.createElement("div");
                    toprow.className = "row";

                    let nameheader = document.createElement("div");
                    nameheader.className = "col-5";
                    let nameheadertext = document.createElement("p");
                    nameheadertext.className = "messageText";
                    nameheadertext.innerText = "Name";
                    nameheader.append(nameheadertext);
                    toprow.append(nameheader);

                    let registrationdateheader = document.createElement("div");
                    registrationdateheader.className = "col-2";
                    let registrationdateheadertext = document.createElement("p");
                    registrationdateheadertext.className = "date messageText";
                    registrationdateheadertext.innerText = "Registration Date";
                    registrationdateheader.append(registrationdateheadertext);
                    toprow.append(registrationdateheader);

                    let gradeheader = document.createElement("div");
                    gradeheader.className = "col-2";
                    let gradeheadertext = document.createElement("p");
                    gradeheadertext.className = "messageText";
                    gradeheadertext.innerText = "Grade";
                    gradeheader.append(gradeheadertext);
                    toprow.append(gradeheader);

                    let statusheader = document.createElement("div");
                    statusheader.className = "col-2";
                    let statusheadertext = document.createElement("p");
                    statusheadertext.className = "messageText";
                    statusheadertext.innerText = "Status";
                    statusheader.append(statusheadertext);
                    toprow.append(statusheader);

                    whitebox.append(toprow);

                    //creates the bottom row full of parsed data (from the json)
                    let bottomrow = document.createElement("div");
                    bottomrow.className = "row lastRow"

                    let namediv = document.createElement("div");
                    namediv.className = "col-5 registrationContent";
                    let name = document.createElement("h3");
                    name.className = "miniHeader messageText";
                    name.id = "name$" + students[i].student_id;
                    name.innerText = students[i].name;
                    namediv.append(name);
                    bottomrow.append(namediv);

                    let datediv = document.createElement("div");
                    datediv.className = "col-2 registrationContent";
                    let date = document.createElement("p");
                    date.className = "date messageText";
                    date.id = "date$" + students[i].student_id;
                    date.innerText = students[i].registration_date;
                    datediv.append(date);
                    bottomrow.append(datediv);

                    let gradediv = document.createElement("div");
                    gradediv.className = "col-2 registrationContent";
                    let grade = document.createElement("p");
                    grade.className = "messageText";
                    grade.id = "grade$" + students[i].student_id;
                    grade.innerText = students[i].grade;
                    gradediv.append(grade);
                    bottomrow.append(gradediv);

                    let statusdiv = document.createElement("div");
                    statusdiv.className = "col-2 registrationContent";
                    let status = document.createElement("p");
                    status.className = "status";
                    status.id = "status$" + students[i].student_id;
                    switch (students[i].status) {
                        case "Under review":
                            status.classList.add("underReview");
                            break;
                        case "Accepted":
                            status.classList.add("accepted");
                            break;
                        case "Rejected":
                            status.classList.add("rejected")
                            break;
                        default:
                            status.classList.add("bugged");
                            break;
                    }

                    statusdiv.append(status);
                    bottomrow.append(statusdiv);

                    whitebox.append(bottomrow);
                    blackbox.append(whitebox);
                }
            }
        }
    };


    let xhr2 = new XMLHttpRequest();
    let methodcall2 = "./api/school/adminSearch/" + id

    xhr2.open('GET', methodcall2, true);
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === XMLHttpRequest.DONE) {
            if (xhr2.status === 200) {
                let obj = JSON.parse(xhr2.responseText);
                sessionStorage.setItem("schoolID", obj.school_id); //TODO sessionstorage cleanup
            }
        }
    };

    xhr2.send();
    xhr1.send();
}

function sendStatusUpdate() {
    let xhr = new XMLHttpRequest();
    let status = document.getElementById("statusdropdown").value;
    let methodcall = ("./api/registration/editStatus/" + sessionStorage.getItem("regID") + '/' + status);
    console.log(status)

    xhr.open('POST', methodcall, true);
    xhr.send();

    let xhr2 = new XMLHttpRequest();
    let methodcall2 = ("./api/parent/getparentfromreg/" + sessionStorage.getItem("regID"));
    let receiver;
    xhr2.open('GET', methodcall2, true);
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === XMLHttpRequest.DONE) {
            if (xhr2.status === 200) {
                let obj = JSON.parse(xhr2.responseText);
                receiver = obj.id;
                let sender = sessionStorage.getItem("id")
                let subject = document.getElementById("subject").value;
                let content = document.getElementById("content-message").value;
                let xhr3 = new XMLHttpRequest();
                let methodcall3 = "./api/message/sendaccountmessage/" + sender + '/' + receiver + '/' + subject + '/' + content;
                xhr3.open('POST', methodcall3, true);
                xhr3.send();
            }
        }
    };
    xhr2.send();
    closePopup2();
    location.reload();
}

function reorder() {
    let dropdownvalue = document.getElementById("dropdown").value;
    let blackbox = document.getElementById("registrations")
    let id = sessionStorage.getItem("id");
    let terms = dropdownvalue.split("-");
    let column = terms[0];
    let asc_dsc = terms[1];

    let xhr = new XMLHttpRequest();
    let methodcall = './api/schooladmin/getschoolregistrations/' + id + '/' + column + '/' + asc_dsc

    //remove previous divs
    while (blackbox.firstChild) {
        blackbox.removeChild(blackbox.firstChild);
    }

    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let students = JSON.parse(xhr.responseText);

                let regs = [];
                //gets all students into an array
                for (let i = 0; i < students.length; i++) {
                    let reg = [];
                    reg[0] = students[i].id;
                    reg[1] = students[i].grade;
                    reg[2] = students[i].registration_date;
                    reg[3] = students[i].student_id;
                    reg[4] = students[i].name;
                    reg[5] = students[i].school_id;
                    reg[6] = students[i].status;
                    reg[7] = students[i].allowedit;
                    regs[i] = reg;

                    //creates a registration entry
                    let whitebox = document.createElement("div")
                    whitebox.className = "row messageData light visualBox"
                    whitebox.onclick = function () {
                        openPopup2();
                    }

                    //creates the top row full of headers
                    let toprow = document.createElement("div");
                    toprow.className = "row";

                    let nameheader = document.createElement("div");
                    nameheader.className = "col-5";
                    let nameheadertext = document.createElement("p");
                    nameheadertext.className = "messageText";
                    nameheadertext.innerText = "Name";
                    nameheader.append(nameheadertext);
                    toprow.append(nameheader);

                    let registrationdateheader = document.createElement("div");
                    registrationdateheader.className = "col-2";
                    let registrationdateheadertext = document.createElement("p");
                    registrationdateheadertext.className = "date messageText";
                    registrationdateheadertext.innerText = "Registration Date";
                    registrationdateheader.append(registrationdateheadertext);
                    toprow.append(registrationdateheader);

                    let gradeheader = document.createElement("div");
                    gradeheader.className = "col-2";
                    let gradeheadertext = document.createElement("p");
                    gradeheadertext.className = "messageText";
                    gradeheadertext.innerText = "Grade";
                    gradeheader.append(gradeheadertext);
                    toprow.append(gradeheader);

                    let statusheader = document.createElement("div");
                    statusheader.className = "col-2";
                    let statusheadertext = document.createElement("p");
                    statusheadertext.className = "messageText";
                    statusheadertext.innerText = "Status";
                    statusheader.append(statusheadertext);
                    toprow.append(statusheader);

                    whitebox.append(toprow);

                    //creates the bottom row full of parsed data (from the json)
                    let bottomrow = document.createElement("div");
                    bottomrow.className = "row lastRow"

                    let namediv = document.createElement("div");
                    namediv.className = "col-5 registrationContent";
                    let name = document.createElement("h3");
                    name.className = "miniHeader messageText";
                    name.innerText = students[i].name;
                    namediv.append(name);
                    bottomrow.append(namediv);

                    let datediv = document.createElement("div");
                    datediv.className = "col-2 registrationContent";
                    let date = document.createElement("p");
                    date.className = "date messageText";
                    date.innerText = students[i].registration_date;
                    datediv.append(date);
                    bottomrow.append(datediv);

                    let gradediv = document.createElement("div");
                    gradediv.className = "col-2 registrationContent";
                    let grade = document.createElement("p");
                    grade.className = "messageText";
                    grade.innerText = students[i].grade;
                    gradediv.append(grade);
                    bottomrow.append(gradediv);

                    let statusdiv = document.createElement("div");
                    statusdiv.className = "col-2 registrationContent";
                    let status = document.createElement("p");
                    status.className = "status";
                    switch (students[i].status) {
                        case "Under review":
                            status.classList.add("underReview");
                            break;
                        case "Accepted":
                            status.classList.add("accepted");
                            break;
                        case "Rejected":
                            status.classList.add("rejected")
                            break;
                        default:
                            status.classList.add("bugged");
                            break;
                    }

                    statusdiv.append(status);
                    bottomrow.append(statusdiv);

                    whitebox.append(bottomrow);
                    blackbox.append(whitebox);
                }
            }
        }
    };
    xhr.send();
}