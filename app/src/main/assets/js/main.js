//open author note
function openNav() {
    document.getElementById("myAuthornote").style.width = "250px";
    document.getElementById("myArrowBox").style.margin = "0% 0% 0% 0%";
    document.getElementById("myArrowBox").style.position = "absolute";
    document.getElementById("myArrowBox").style.right = "250px";
    document.getElementById("myArrow").style.transform = "scaleX(-1) translateY(-50%)";
    document.getElementById("opener").setAttribute("onClick", "closeNav()");
}

//close author note
function closeNav() {
    document.getElementById("myAuthornote").style.width = "0";
    document.getElementById("myArrowBox").style.margin = "0% -3% 0% 0%";
    document.getElementById("myArrowBox").style.position = "static";
    document.getElementById("myArrowBox").style.right = "0px";
    document.getElementById("myArrow").style.transform = "scaleX(1) translateY(-50%)";
    document.getElementById("opener").setAttribute("onClick", "openNav()");
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

//function to change to first option in comparison grid
function columnOne() {
    var myClassesOne = document.querySelectorAll('.comparison-item'),
        i = 0,
        l = myClassesOne.length;
    for (i; i < l; i++) {
        myClassesOne[i].style.display = 'unset';
    }

    var myClassesTwo = document.querySelectorAll('.comparison-item-two'),
        i = 0,
        l = myClassesTwo.length;
    for (i; i < l; i++) {
        myClassesTwo[i].style.display = 'none';
    }

    var myClassesThree = document.querySelectorAll('.comparison-item-three'),
        i = 0,
        l = myClassesThree.length;
    for (i; i < l; i++) {
        myClassesThree[i].style.display = 'none';
    }
}

//function to change to second option in comparison grid
function columnTwo() {
    var myClassesOne = document.querySelectorAll('.comparison-item'),
        i = 0,
        l = myClassesOne.length;
    for (i; i < l; i++) {
        myClassesOne[i].style.display = 'none';
    }

    var myClassesTwo = document.querySelectorAll('.comparison-item-two'),
        i = 0,
        l = myClassesTwo.length;
    for (i; i < l; i++) {
        myClassesTwo[i].style.display = 'unset';
    }

    var myClassesThree = document.querySelectorAll('.comparison-item-three'),
        i = 0,
        l = myClassesThree.length;
    for (i; i < l; i++) {
        myClassesThree[i].style.display = 'none';
    }
}

//function to change to third option in comparison grid
function columnThree() {
    var myClassesOne = document.querySelectorAll('.comparison-item'),
        i = 0,
        l = myClassesOne.length;
    for (i; i < l; i++) {
        myClassesOne[i].style.display = 'none';
    }

    var myClassesTwo = document.querySelectorAll('.comparison-item-two'),
        i = 0,
        l = myClassesTwo.length;
    for (i; i < l; i++) {
        myClassesTwo[i].style.display = 'none';
    }

    var myClassesThree = document.querySelectorAll('.comparison-item-three'),
        i = 0,
        l = myClassesThree.length;
    for (i; i < l; i++) {
        myClassesThree[i].style.display = 'unset';
    }
}
