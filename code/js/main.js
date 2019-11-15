window.onload = () => {

  const volumeIcon = document.getElementById("volume");
  const song = document.getElementById("audio");
  let hasClicked = false;
  //song.volume = 1.0;

  volumeIcon.addEventListener('click', function() {
    if (hasClicked == false) {
      song.play();
      hasClicked = true;
    }

    if (volumeIcon.classList.contains('fa-volume-up')) {
      volumeIcon.classList.remove('fa-volume-up');
      volumeIcon.classList.add('fa-volume-mute');
      song.volume = 0.0;
    } 
    else {
      volumeIcon.classList.remove('fa-volume-up');
      volumeIcon.classList.add('fa-volume-up');
      song.volume = 1.0;
    } 
  });
}

