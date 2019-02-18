//open author note
function openNav() {
    document.getElementById("myAuthornote").style.width = "250px";
}

//close author note
function closeNav() {
    document.getElementById("myAuthornote").style.width = "0";
}

//play audio
function PlaySound(item) {

    var sound = item.getElementByClassName("audio");
    console.log(sound);

}
