function render() {
    let id = sessionStorage.getItem("id");
    //To fetch all messages
    let methodcall1 = './api/message/fetchaccountmessages/' + id;
    let xhr1 = new XMLHttpRequest();
    xhr1.open('GET', methodcall1, true);
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === XMLHttpRequest.DONE) {
            if (xhr1.status === 200) {
                let obj = JSON.parse(xhr1.responseText);
                let messages = [];
                let messagebox = document.getElementById("allmessages");

                //to put messages from the json object into an array
                for (let i = 0; i < obj.length; i++) {
                    let currentmessage = [];
                    currentmessage[0] = obj[i].sender;
                    currentmessage[1] = obj[i].receiver;
                    currentmessage[2] = obj[i].dateSent;
                    currentmessage[3] = obj[i].subject;
                    currentmessage[4] = obj[i].content;
                    messages[i] = currentmessage;
                }

                let prevmessages = [];

                //to create message divs and popups
                for (let i = 0; i < messages.length; i++) {

                    //checks for duplicates
                    if (messages[i][0] !== id) {
                        //check for duplicates
                        if (prevmessages.includes("" + messages[i][0] + messages[i][3])) {
                        } else {
                            prevmessages.push("" + messages[i][0] + messages[i][3])

                            //makes a popup
                            let popupdiv = document.createElement("div");
                            popupdiv.className = "popup";
                            popupdiv.id = messages[i][0] + "$$$$$" + messages[i][3];

                            //creates the header of the popup
                            let subjectheaderdiv = document.createElement("div");
                            subjectheaderdiv.className = "fixedbar";
                            subjectheaderdiv.style.zIndex = "1";

                            let subjectheader = document.createElement("h2");
                            subjectheader.style = "line-height: 2";
                            subjectheader.id = "messageHeader";
                            subjectheader.innerText = messages[i][3];
                            subjectheaderdiv.append(subjectheader);

                            let exitbutton = document.createElement("img");
                            exitbutton.src = "images/menu/closeX.svg";
                            exitbutton.alt = "close";
                            exitbutton.className = "closeImage";
                            exitbutton.onclick = function () {
                                let x = document.getElementById(messages[i][0] + "$$$$$" + messages[i][3]);
                                x.classList.remove("open-popup")
                                x.style.overflow = "none";
                            };
                            subjectheaderdiv.append(exitbutton);

                            popupdiv.append(subjectheaderdiv);

                            //creates the divs for individual messages
                            let bigmsgdiv = document.createElement("div");
                            bigmsgdiv.style = "padding: 0 30px 30px; position: sticky";
                            let currentsender = messages[i][0];
                            let currentsubject = messages[i][3];
                            for (let k = 0; k < messages.length; k++) {
                                let bubblerow = document.createElement("div");
                                bubblerow.className = "row";
                                if (messages[k][0] === currentsender && messages[k][3] === currentsubject) {
                                    let leftbox = document.createElement("div");
                                    leftbox.className = "col-8 container";
                                    let leftcontents = document.createElement("p");
                                    leftcontents.style = "color: #000000";
                                    let leftspacing = document.createElement("div");
                                    leftspacing.className = "col-4";
                                    leftcontents.append(messages[k][4]);
                                    leftbox.append(leftcontents);
                                    bubblerow.append(leftbox)
                                    bubblerow.append(leftspacing);
                                    bigmsgdiv.append(bubblerow);
                                } else if (messages[k][0] === id && messages[k][3] === currentsubject) {
                                    let rightbox = document.createElement("div");
                                    rightbox.className = "col-8 container darker";
                                    let rightcontents = document.createElement("p");
                                    rightcontents.style = "color: #000000";
                                    let rightspacing = document.createElement("div");
                                    rightspacing.className = "col-4";
                                    rightcontents.append(messages[k][4]);
                                    rightbox.append(rightcontents);
                                    bubblerow.append(rightspacing);
                                    bubblerow.append(rightbox)
                                    bigmsgdiv.append(bubblerow);
                                }
                            }
                            popupdiv.append(bigmsgdiv);

                            //creates the bottom input field area
                            let inputdiv = document.createElement("div");
                            inputdiv.className = "fixedbar";
                            let inputfield = document.createElement("input");
                            inputfield.type = "text";
                            inputfield.id = messages[i][0] + "$$$$$" + messages[i][3] + "$$$$$input";
                            let button = document.createElement("button");
                            button.className = "btn-primary";
                            button.style = "font-size: 15px";
                            button.innerText = "Send";
                            button.onclick = function () {
                                sendMessage(messages[i][0], messages[i][3], inputfield.value)
                            }

                            inputdiv.append(inputfield);
                            inputdiv.append(button);

                            popupdiv.append(inputdiv);
                            document.getElementById("container").append(popupdiv);
                            //TODO fix the problem with the input field being at the very bottom

                            //creates a row
                            let msgrow = document.createElement("div");
                            msgrow.className = "row messageData light visualBox";
                            msgrow.style = "cursor: pointer";
                            msgrow.onclick = function () {
                                let x = document.getElementById(messages[i][0] + "$$$$$" + messages[i][3]);
                                x.classList.add("open-popup");
                                x.style.overflow = "auto";
                            };

                            //creates the circle
                            let statusdiv = document.createElement("div");
                            statusdiv.className = "col-1";
                            let statuscirclediv = document.createElement("div");
                            statuscirclediv.className = "status";
                            statusdiv.append(statuscirclediv);
                            msgrow.append(statusdiv);

                            //creates the time
                            let timediv = document.createElement("div");
                            timediv.className = "col-2";
                            let time = document.createElement("p");
                            time.className = "date messageText"
                            time.append(messages[i][2]);
                            timediv.append(time);
                            msgrow.append(timediv);

                            //creates the "from:"
                            let fromdiv = document.createElement("div");
                            fromdiv.className = "col-1";
                            let from = document.createElement("p");
                            from.className = "messageText";
                            from.append("from:");
                            fromdiv.append(from);
                            msgrow.append(fromdiv);

                            //creates the sender display
                            let senderdiv = document.createElement("div");
                            senderdiv.className = "col-4";
                            let sender = document.createElement("h3");
                            sender.className = "miniHeader messageText";
                            sender.append(messages[i][0]);
                            senderdiv.append(sender);
                            msgrow.append(senderdiv);

                            //creates the "subject:"
                            let subjecttextdiv = document.createElement("div");
                            subjecttextdiv.className = "col-1";
                            let subjecttext = document.createElement("p");
                            subjecttext.className = "messageText";
                            subjecttext.append("Subject: ");
                            subjecttextdiv.append(subjecttext)
                            msgrow.append(subjecttextdiv);

                            //creates the subject display
                            let subjectdiv = document.createElement("div");
                            subjectdiv.className = "col-3";
                            let subject = document.createElement("p");
                            subject.className = "subject messageText";
                            subject.append(messages[i][3]);
                            subjectdiv.append(subject);
                            msgrow.append(subjectdiv);

                            //appends to message box
                            messagebox.append(msgrow);
                        }
                    }
                }
            }
        }
    }

    //To fetch the account name and initials for bottom left
    let methodcall2 = './api/account/getname/' + id;
    let xhr2 = new XMLHttpRequest();
    xhr2.open('GET', methodcall2, true);
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === XMLHttpRequest.DONE) {
            if (xhr2.status === 200) {
                let name = JSON.parse(xhr2.responseText).name;
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

//For sending a message
function sendMessage(receiver, subject, content) {
    let senderid = sessionStorage.getItem("id");
    let receiveridsplit = receiver.split("");

    let methodcall;
    if (receiveridsplit.includes("@")) {
        methodcall = "./api/message/sendaccountmessage/" + senderid + "/" + receiver + "/" + subject + "/" + content;
    } else {
        methodcall = "./api/message/sendschoolmessage/" + senderid + "/" + receiver + "/" + subject + "/" + content;
    }
    let xhr = new XMLHttpRequest();
    xhr.open('POST', methodcall, true);
    xhr.send();
    location.reload();
}