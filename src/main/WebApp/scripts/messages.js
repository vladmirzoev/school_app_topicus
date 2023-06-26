function render() {
    var id = sessionStorage.getItem("id");
    //To fetch all messages
    var methodcall1 = './api/message/fetchaccountmessages/' + id;
    var xhr1 = new XMLHttpRequest();
    xhr1.open('GET', methodcall1, true);
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === XMLHttpRequest.DONE) {
            if (xhr1.status === 200) {
                let obj = JSON.parse(xhr1.responseText);
                let messages = [];
                for (let i = 0; i < obj.length; i++) {
                    let currentmessage = [];
                    currentmessage[0] = obj[i].sender;
                    currentmessage[1] = obj[i].receiver;
                    currentmessage[2] = obj[i].dateSent;
                    currentmessage[3] = obj[i].subject;
                    currentmessage[4] = obj[i].content;
                    messages[i] = currentmessage;
                }
            }
        }
    }
    //To fetch the account name and initials for bottom left
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
// TODO BELOW IS THE MESSAGE SYSTEM JS
// //For sending a message
// function sendMessage() {
//     let senderid = sessionStorage.getItem("id");
//     let receiverid = document.getElementById("receiverid").value;
//     let subject = document.getElementById("subject").value;
//     let content = document.getElementById("content").value;
//     let receiveridsplit = receiverid.split("");
//
//     let methodcall;
//     if (receiveridsplit.includes("@")){
//         methodcall = "./api/message/sendaccountmessage/" + senderid + "/" + receiverid + "/" + subject + "/" + content;
//     } else {
//         methodcall = "./api/message/sendschoolmessage/" + senderid + "/" + receiverid + "/" + subject + "/" + content;
//     }
//     let xhr = new XMLHttpRequest();
//     xhr.open('POST', methodcall, true);
//     xhr.send();
//     location.reload();
// }