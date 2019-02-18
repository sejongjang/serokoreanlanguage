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
//play audio function 1
function PlaySound() {
    var sound = document.getElementsByClassName("audio")[1];
    sound.play()
    console.log (sound);
    }

//play audio function 2 (jquery)
//$('.audio').click(function (e) {
    //e.preventDefault;
    //var button = $(this);
    //var audio = button.parent().find('audio')[0];
    //audio.play();
    //console.log (audio);
//});
