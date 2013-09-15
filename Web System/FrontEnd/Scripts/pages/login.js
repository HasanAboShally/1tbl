
function animate(element, effect, duration) {
    var ef = 'animated ' + effect;
    element.addClass(ef);
    setTimeout(function () { element.removeClass(ef) }, duration || 2100);
}

animate($("#form-login"), 'bounceInDown');
setTimeout(function () { $('#tb-username').focus(); }, 1300);


$('#form-register').hide();
$('#btn-signup').click(function () {
    animate($("#form-login"), 'bounceOutUp');
    setTimeout(function () { $('#form-login').hide(); }, 500);
    setTimeout(function () { $('#form-register').show(); animate($("#form-register"), 'bounceInUp'); }, 200);
});


$('#btn-back-to-login').click(function () {
    animate($("#form-register"), 'bounceOutDown');
    setTimeout(function () { $('#form-register').hide(); }, 500);
    setTimeout(function () { $('#form-login').show(); animate($("#form-login"), 'bounceInDown'); }, 200);
});

$('#tb-password').keypress(function (e) {
    if (e.which == 13)//the enter key
    {
        $('#btn-login').trigger('click');
    }
});

function tryAgain() {
    animate($("#form-login"), 'wobble');
    $('#tb-username').val('');
    $('#tb-password').val('');
    $('.loading').hide();
    $('#btn-login').show();
    $('#tb-username').focus();
}


// Supporting placeholders for all browsers. Damn IE!
$(function () {
    if (!$.support.placeholder) {
        var active = document.activeElement;
        $(':text').focus(function () {
            if ($(this).attr('placeholder') != '' && $(this).val() == $(this).attr('placeholder')) {
                $(this).val('').removeClass('hasPlaceholder');
            }
        }).blur(function () {
            if ($(this).attr('placeholder') != '' && ($(this).val() == '' || $(this).val() == $(this).attr('placeholder'))) {
                $(this).val($(this).attr('placeholder')).addClass('hasPlaceholder');
            }
        });
        $(':text').blur();
        $(active).focus();
        $('form').submit(function () {
            $(this).find('.hasPlaceholder').each(function () { $(this).val(''); });
        });
    }
});



$('#form-login').submit(function () {

    $('#btn-login').hide();
    $('.loading').show();

    var username = $('#tb-username').val();
    var password = $('#tb-password').val();

    if (username == "" || password == "") {
        tryAgain();
        return false;
    }

    $.post(serverWebPath + $(this).attr('action'), $(this).serialize(), function (isSuccess) {
        if (isSuccess == true) {

            animate($("#form-login"), 'bounceOutUp');

            setTimeout(function () {
                $("#form-login").hide();
                window.location = '/dashboard.html';
            }, 500);

            return false;
        }
        else {
            tryAgain();
        }
    }, 'json');


    tryAgain();
    return false;
});

$('#form-register').submit(function () {
    $.post(serverWebPath + $(this).attr('action'), $(this).serialize(), function (result) {
        
        if (result) {
            $('#tb-username').val($('#tb-register-username').val());
            $('#tb-password').val($('#tb-register-password').val());
            $('#form-login').submit();
        }

    }, 'json');

    return false;
});