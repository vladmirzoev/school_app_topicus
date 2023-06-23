function render() {
    var id = sessionStorage.getItem("id");
    var methodcall = './api/schooladmin/getAllStudents/' + id;
    var req = new XMLHttpRequest();
    req.open('GET', methodcall, true);
    req.onreadystatechange = function () {
        if (req.readyState === XMLHttpRequest.DONE) {
            if (req.status === 200) {
                var students = JSON.parse(req.responseText);

                //gets all students into an array
                let regs = [];
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
                }
                console.log(regs)


                for (let i = 0; i < regs.length; i++) {
                    let row = document.createElement("div");
                    row.className = "row messageData light visualBox";
                    let path = document.getElementById("registrations")
                    path.append(regs[i])
                }
                //
                //
                // for (let y = 0; y < students.length; y++) {
                //     let registrations = document.createElement("div")
                //     registrations.className = "row messageData light visualBox"
                //     let path = document.getElementById("registrations")
                //     console.log(students[y])
                //     path.append(registrations)
                //     for (let i = 0; i <; i++) {
                //         let reg = [];
                //         reg[0] = students[y].id;
                //         reg[1] = students[y].grade;
                //         reg[2] = students[y].registration_date;
                //         reg[3] = students[y].student_id;
                //         reg[4] = students[y].name;
                //         reg[5] = students[y].school_id;
                //         reg[6] = students[y].status;
                //         reg[7] = students[y].allowedit;
                //         regs[i] = reg;
                //     }
                // }
            }
        }
    };
    req.send();

    // let registrations = document.createElement("div")
    // reg.className = "row messageData light visualBox"
    // let path = document.getElementById("registrations")
    // path.append(reg)
}