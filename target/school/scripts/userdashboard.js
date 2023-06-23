function render() {
    var id = sessionStorage.getItem("id");

    // to replace the header and bottom left name
    var methodcall = './api/account/getname/' + id;
    var xhr = new XMLHttpRequest();
    var date = new Date();
    var hr = date.getHours();
    xhr.open('GET', methodcall, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var name = JSON.parse(xhr.responseText).name;

                const splitname = name.split(" ");
                let initials = [];
                for (let i = 0; i < splitname.length - 1; i++) {
                    initials[i] = splitname[i].split("")[0] + ". ";
                }

                document.getElementById("bottomleftname").innerText = initials.toString() + splitname[splitname.length - 1];
                if (hr < 12) {
                    document.getElementById("dashboardHeading").innerHTML = "Good Morning, " + name;
                } else if (hr < 18) {
                    document.getElementById("dashboardHeading").innerHTML = "Good Afternoon, " + name;
                } else {
                    document.getElementById("dashboardHeading").innerHTML = "Good Evening, " + name;
                }
            }
        }
    };
    xhr.send();

    //TODO maybe add profile picture functionality
}