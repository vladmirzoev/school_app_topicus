function render() {
    let id = sessionStorage.getItem("id");

    let methodcall1 = './api/parent/getchildren/' + id;
    let xhr1 = new XMLHttpRequest();
    xhr1.open('GET', methodcall1, true);
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === XMLHttpRequest.DONE) {
            if (xhr1.status === 200) {
                let obj = JSON.parse(xhr1.responseText);
                for (let i = 0; i < obj.length; i++) {
                    addChild(obj[i].id, obj[i].name, obj[i].birthdate, obj[i].school_name, obj[i].status);
                }
            }
        }
    }

    let methodcall2 = './api/account/getname/' + id;
    let xhr2 = new XMLHttpRequest();
    xhr2.open('GET', methodcall2, true);
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === XMLHttpRequest.DONE) {
            if (xhr2.status === 200) {
                var name = JSON.parse(xhr2.responseText).name;

                const splitname = name.split(" ");
                let initials = [];
                for (let i = 0; i < splitname.length - 1; i++) {
                    initials[i] = splitname[i].split("")[0] + ". ";
                }

                document.getElementById("bottomleftname").innerText = initials.toString() + splitname[splitname.length - 1];
            }
        }
    }
    xhr1.send();
    xhr2.send();
}



function addChild(id, name, birthdate, schoolname, registration_status){
    let parent = document.getElementById("childrenCards");
    let cardSpace = document.createElement('div');
    cardSpace.className = "col-6 childrenRow justify-content-center";
    let childName = document.createElement('h3')
    let dateOfBirth = document.createElement("p")
    let childId = document.createElement("p")
    let school = document.createElement("p");
    let regstatus = document.createElement("p");
    let photoFrame = document.createElement("div");
    let bin = document.createElement('img');
    bin.src = 'images/forms/binDelete.svg'
    photoFrame.className = "align-text-center"
    photoFrame.style.borderRadius = "50%"
    photoFrame.style.height = "150px"
    photoFrame.style.width = "150px"
    photoFrame.style.display = "inline-block"
    photoFrame.style.backgroundImage = "url('images/menu/User.svg')" //TODO change placeholder avatar to proper picture
    photoFrame.style.backgroundSize = "cover"
    childName.className = "miniHeader"
    childName.innerText = name;
    dateOfBirth.innerText = birthdate;
    childId.innerText = id;
    school.innerText = schoolname;

    switch (registration_status) {
        case "Under review":
            regstatus.style.color = "orange";
            break;
        case "Accepted":
            regstatus.style.color = "green";
            break;
        case "Rejected":
            regstatus.style.color = "red";
            break;
        default:
            regstatus.style.display = "none";
            break;
    }
    regstatus.innerText = registration_status;

    let childBox = document.createElement('div');
    childBox.className = "dark visualBox text-center";
    childBox.append(childName);
    childBox.append(dateOfBirth);
    childBox.append(photoFrame);
    childBox.append(childId);
    childBox.append(school);
    childBox.append(regstatus);
    childBox.append(bin);
    cardSpace.append(childBox);
    parent.append(cardSpace);
    document.getElementsByClassName('row')

    bin.addEventListener('click', function() {
        let result = confirm('Are you sure you want to deregister this child?');
        if (result) {
            childBox.remove();
        }
    });
}
function redirect() {
    window.location.href = "registrationFormLoggedIn.html";
}