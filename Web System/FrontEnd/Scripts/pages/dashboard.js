


$(document).ready(function () {

    $('#current-project').click(function () {
        $('#projects-list').slideToggle(250);
        $('#project-dismisser').fadeToggle(250);
    });

    $('#project-tables-list li').click(function () {
        $('#project-tables-list li').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#btn-signout').click(function () {
        $.get(serverWebPath + 'users/logout', function (result) {
            if (result)
                window.location = '/index.html';
            else
                alert('error: logout');
        });
    });

    $('#project-dismisser').click(function () {
        $('#projects-list').slideUp(250);
        $('#project-dismisser').fadeOut(250);
    });
});