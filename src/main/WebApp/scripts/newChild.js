function addChild(){
    let div = document.createElement('div');
    div.className = "blackbox";
    div.innerHTML = "<strong>Всем привет!</strong> Вы прочитали важное сообщение.";
    document.body.append(div);
}