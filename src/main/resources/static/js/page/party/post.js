let pics = document.querySelectorAll(".pic");
let vids = document.querySelectorAll(".vid");
let lightbox = document.querySelector("#lightbox");
let lightboxImage = document.querySelector("#lightboxImage");
let lightboxVideo = document.querySelector("#lightboxVideo");

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