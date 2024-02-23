const menu = document.querySelector('#mobile-menu');
const menuLinks = document.querySelector('.navbar__menu');

menu.addEventListener('click', function() {
    menu.classList.toggle('is-active');
    menuLinks.classList.toggle('active');
});

const imageUrls = [
    "/website/images/Alex.png",
    "/website/images/Alfred.png",
    "/website/images/Anita.png",
    "/website/images/Anne.png",
    "/website/images/Bernard.png",
    "/website/images/Bill.png",
    "/website/images/Charles.png",
    "/website/images/Claire.png",
    "/website/images/David.png",
    "/website/images/Eric.png",
    "/website/images/Frans.png",
    "/website/images/George.png",
    "/website/images/Herman.png",
    "/website/images/Joe.png",
    "/website/images/Maria.png",
    "/website/images/Max.png",
    "/website/images/Paul.png",
    "/website/images/Peter.png",
    "/website/images/Philip.png",
    "/website/images/Richard.png",
    "/website/images/Robert.png",
    "/website/images/Sam.png",
    "/website/images/Susan.png",
    "/website/images/Tom.png",
];

let currentIndex = 0;

function updateSidebarImage() {
    document.getElementById("sidebar-image").src = imageUrls[currentIndex];

    currentIndex = (currentIndex + 1) % imageUrls.length;
}

setInterval(updateSidebarImage, 1000);



//Stars backgrounds
const numStars = 70; 
const starsContainer = document.querySelector('.stars-container');

for (let i = 0; i < numStars; i++) {
    const star = document.createElement('div');
    star.classList.add('star');
    star.style.left = `${Math.random() * 100}vw`; 
    star.style.animationDuration = `${Math.random() * 5 + 2}s`; 
    starsContainer.appendChild(star);
}

