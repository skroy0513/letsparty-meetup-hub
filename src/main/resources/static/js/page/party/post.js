let pics = document.querySelectorAll(".pic");
let vids = document.querySelectorAll(".vid");
let lightbox = document.querySelector("#lightbox");
let lightboxImage = document.querySelector("#lightboxImage");
let lightboxVideo = document.querySelector("#lightboxVideo");
let $button = $("#button");
let $pollButton = $("#poll-button");
let pollbuttons = document.querySelectorAll(".modal-body i");
let pollSelect = document.querySelector("#pollSelect");
let $vote = $("#vote");

for(i = 0; i < pics.length; i++){
    pics[i].addEventListener("click", showLightbox)
}
function showLightbox() {
    let imgLocation = this.getAttribute("src"); 
    console.log(imgLocation)
    lightboxImage.setAttribute("src", imgLocation);
    lightboxVideo.setAttribute("src", "");
    lightboxVideo.removeAttribute("controls")
    lightbox.style.display = "block";
    lightboxVideo.style.backgroundColor = "#2c2c2c00"   
}

for(i = 0; i < vids.length; i++){
    vids[i].addEventListener("click", playLightBox)
}
function playLightBox(){
    let vidLocation = this.getAttribute("src");
    console.log(vidLocation)
    lightboxVideo.setAttribute("src", vidLocation);
    lightboxVideo.setAttribute("controls", true);
    lightboxImage.setAttribute("src", "");
    lightbox.style.display = "block";
    lightboxVideo.style.backgroundColor = "#2c2c2c"
}

lightbox.onclick = function() {
    lightbox.style.display = "none";
}

$pollButton.on("click", function(){
    $button.click();
})

for (i = 0; i < pollbuttons.length; i++){
    pollbuttons[i].addEventListener("click", function(){
        pollbuttons.forEach(b => {
            b.classList.remove("fa-solid");
            b.classList.remove("fa-circle-dot");
            b.classList.add("fa-regular");
            b.classList.add("fa-circle");
        });
        this.classList.add("fa-solid");
        this.classList.add("fa-circle-dot");
        this.classList.remove("fa-regular");
        this.classList.remove("fa-circle");
        let optionNo = this.getAttribute("data");
        console.log(optionNo);
        pollSelect.value = optionNo;
    })
}

$vote.on("click", function(){
    console.log("투표 제출");
    $("#pollForm").submit();
})
