document.addEventListener('DOMContentLoaded', function() {
    var links = document.querySelectorAll('.animated-link');
  
    links.forEach(function(link) {
      link.addEventListener('click', function(event) {
        // Add clicked class to trigger the animation
        this.classList.add('clicked');
  
        // Remove the clicked class after the animation ends
        setTimeout(function() {
          link.classList.remove('clicked');
        }, 500);
      });
    });
  });