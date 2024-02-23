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

