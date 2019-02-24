//open author note
function openNav() {
    document.getElementById("myAuthornote").style.width = "250px";
}

//close author note
function closeNav() {
    document.getElementById("myAuthornote").style.width = "0";
}

//play audio function
function PlaySound(a) {
    const audio = a.previousElementSibling;
    audio.play();
}

//show translation function
function translation(h4) {
    const newText = h4.previousElementSibling.textContent;
    h4.innerHTML = newText
}

//open modal image
function blowup(img) {
    var image = img;
    console.log(image)
    var modalImg = img.nextElementSibling.querySelector(".modal-content");
    console.log(modalImg)
    var captionText = img.nextElementSibling.querySelector(".caption");
    console.log(captionText)
    img.nextElementSibling.style.dispay = "block";
    modalImg.src = img.src;
    captionText.innerHTML = captionText.alt;
    var span = img.nextElementSibling.querySelector(".close");
    console.log(span)
}

//close modal image
function shrink(span) {

}




//modal image
//var modal = document.getElementById('myModal');

// Get the image and insert it inside the modal - use its "alt" text as a caption
//var img = document.getElementById('myImg');
//var modalImg = document.getElementById("img01");
//var captionText = document.getElementById("caption");
//img.onclick = function () {
//    modal.style.display = "block";
//    modalImg.src = this.src;
//    captionText.innerHTML = this.alt;
//}

// Get the <span> element that closes the modal
//var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
//span.onclick = function () {
//    modal.style.display = "none";
//}
