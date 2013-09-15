

var isShaking = false;
$('#shake-all').click(function () {

    if (isShaking)
        stopShake();
    else {
        startShake();
        setTimeout(function () { window.scrollTo(0, 0); }, 500);
    }

});


var soundEmbed = null;

function stopShake() {

    isShaking = false;

    if (soundEmbed) {
        document.body.removeChild(soundEmbed);
        soundEmbed.removed = true;
        soundEmbed = null;
    }

    $('body *').removeClass('shake');

}

function startShake() {

    isShaking = true;
    $('#site-title').addClass('shake');
    setTimeout(function () {
        if (isShaking) {
            $('body *:not(#shake-all)').addClass('shake');
            setTimeout(function () { if (isShaking) { stopShake(); } }, 14150);
        }

    }, 15750);


    soundEmbed = document.createElement("embed");
    soundEmbed.setAttribute("src", '/Files/harlem_shake.mp3');
    soundEmbed.setAttribute("hidden", true);
    soundEmbed.setAttribute("autostart", true);
    soundEmbed.removed = false;
    document.body.appendChild(soundEmbed);

}
