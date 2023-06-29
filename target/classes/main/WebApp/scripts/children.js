function render() {
    let id = sessionStorage.getItem("id");

    let methodcall1 = './api/parent/getchildren/' + id;
    let xhr1 = new XMLHttpRequest();
    xhr1.open('GET', methodcall1, true);
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === XMLHttpRequest.DONE) {
            if (xhr1.status === 200) {
                let obj = JSON.parse(xhr1.responseText);
                let children = [];
                for (let i = 0; i < obj.length; i++) {
                    let child = [];
                    child[0] = obj[i].id;
                    child[1] = obj[i].name;
                    child[2] = obj[i].birthdate;
                    addChild(obj[i].id, obj[i].name, obj[i].birthdate)
                    children[i] = child;
                }
            }
            //TODO make cards based off the children array
            //TODO displace the 'Add child' button based on how many cards there are
            //TODO add home page button
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



function addChild(id, name, birthdate){
    let parent = document.getElementById("childrenCards");
    let cardSpace = document.createElement('div');
    cardSpace.className = "col-6 childrenRow justify-content-center";
    let childName = document.createElement('h3')
    let dateOfBirth = document.createElement("p")
    let childId = document.createElement("p")
    let photoFrame = document.createElement("div")
    photoFrame.className = "align-text-center"
    photoFrame.style.borderRadius = "50%"
    photoFrame.style.height = "150px"
    photoFrame.style.width = "150px"
    photoFrame.style.display = "inline-block"
    photoFrame.style.backgroundImage = "url('images/children/girl.jpg')"
    photoFrame.style.backgroundSize = "cover"
    childName.className = "miniHeader"
    childName.innerText = name;
    dateOfBirth.innerText = birthdate;
    childId.innerText = id;
    let childBox = document.createElement('div');
    childBox.className = "dark visualBox text-center";
    childBox.append(childName)
    childBox.append(photoFrame)
    childBox.append(childId)
    childBox.append(dateOfBirth)
    cardSpace.append(childBox);
    parent.append(cardSpace);
    document.getElementsByClassName('row')
}
function redirect() {
    window.location.href = "registrationFormLoggedIn.html";
}