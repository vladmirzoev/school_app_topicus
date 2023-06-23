function render() {
    var id = sessionStorage.getItem("id");

    var methodcall1 = './api/parent/getchildren/' + id;
    var xhr1 = new XMLHttpRequest();
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
                    children[i] = child;
                }
            }
            //TODO make cards based off the children array
            //TODO displace the 'Add child' button based on how many cards there are
            //TODO add home page button
        }
    }

    var methodcall2 = './api/account/getname/' + id;
    var xhr2 = new XMLHttpRequest();
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

function addChild(){
    //TODO change this to the actual addChild function

    let div = document.createElement('div');
    div.className = "blackbox";
    div.innerHTML = "<strong>Всем привет!</strong> Вы прочитали важное сообщение.";
    document.body.append(div);
}