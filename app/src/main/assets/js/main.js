//open author note
function openNav() {
    document.getElementById("myAuthornote").style.width = "250px";
}

//close author note
function closeNav() {
    document.getElementById("myAuthornote").style.width = "0";
}

//play audio
function PlaySound() {
    var sound = audio.getAttribue("src");
    sound.play();
}
