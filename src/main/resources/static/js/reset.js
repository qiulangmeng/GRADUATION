function checkResetMobile() {
    var phone = document.getElementById("InputResetMobile");
    var ephone = document.getElementById("errorMobile");
    /* 电话不为空 */
    if (phone.value === "") {
        ephone.innerHTML = "电话不为空。";
        return false;
    } else {
        ephone.innerHTML = "";
    }
    /* 电话格式验证 */
    var reg = /(^1\d{10}$)|(^[0-9]\d{7}$)/;
    if (reg.test(phone.value) === false) {
        ephone.innerHTML = "电话格式错误。";
        return false;
    } else {
        ephone.innerHTML = "";
    }
    const url = "/checkMobile?mobile=" + phone.value;
    $.get(url,
        function (date, status) {
            if (String(date.jugde) === "false") {
                ephone.innerHTML = "手机号未注册。";
                return false;
            } else {
                ephone.innerHTML = "";
            }
        },
        'json');
    return true;
}

function resetPasswordBlur() {
    var pwd = document.getElementById("resetPassword");
    var epwd = document.getElementById("errorResetPassword");
    /* 密码为空/不为空 */
    if (pwd.value === "") {
        epwd.innerHTML = "新密码不能为空。"
        return true;
    } else {
        epwd.innerHTML = "";
    }
    /* 密码长度 */
    if (pwd.value.length < 6 || pwd.value.length > 16) {
        epwd.innerHTML = "长度为6-16。";
        return false;
    } else {
        epwd.innerHTML = "";
    }
    return true;

}

function reResetPasswordBlur() {
    var rpwd = document.getElementById("reResetPassword");
    var erpwd = document.getElementById("errorReResetPassword");
    var pwd = document.getElementById("resetPassword");
    /* 确认密码不为空 */
    if (rpwd.value === "") {
        erpwd.innerHTML = "确认密码不能为空";
        return false;
    } else {
        erpwd.innerHTML = "";
    }
    /* 确认密码与密码不一致 */
    if (pwd.value !== rpwd.value) {
        erpwd.innerHTML = "密码不一致。";
        return false;
    } else {
        erpwd.innerHTML = "";
    }
    return true;

}

const wait = 60;

function sendResetCode() {
    var eMobile = document.getElementById("errorMobile");
    var mobile = $("input[name='mobile']").val();
    if (mobile === '' || mobile.length !== 11) {
        eMobile.innerHTML = "请输入正确的手机号！";
        return false;
    } else {
        $.ajax({
            type: 'GET',
            url: '/code/sms',
            data: {
                mobile: mobile,
                redisname: "smsResetCode",
            },
            dataType: 'json',
            success: function (data) {
                if (data) {
                    timer('sendResetBtn', wait);
                } else {
                    timer('sendResetBtn', wait);
                    eMobile.innerHTML = "获取验证码失败";
                }
            },
            error: function (data) {
                timer('sendResetBtn', wait);
                eMobile.innerHTML = '连接超时！';
            }
        });
    }
}

function timer(a, b) {
    var getCode = $("#" + a);
    if (b === 0) {
        getCode.val("获取验证码");
        getCode.removeAttr("disabled");
        getCode.css("background", "#84C639").css("cursor", "pointer");
        b = 60;
    } else {
        getCode.attr("disabled", "true");
        getCode.css("background", "#FA1818").css("cursor", "not-allowed");
        getCode.val(b + "秒后重发");
        b--;
        setTimeout(function () {
            timer(a, b)
        }, 1000);
    }
}

/**
 * @return {boolean}
 */
function InputSmsCodeBlur(a) {
    var smsCode = $("#" + a);
    if (smsCode.val() === "") {
        alert("验证码不能为空")
        return false;
    } else {
        return true;
    }
}

function checkResetForm() {
    var bool = true;
    if (!InputSmsCodeBlur('sendResetBtn')) bool = false;
    if (!checkResetMobile()) bool = false;
    if (!resetPasswordBlur()) bool = false;
    if (!reResetPasswordBlur()) bool = false;
    if (bool === true) {
        $.post("/resetByMobile",
            {
                mobile: $("#InputResetMobile").val(),
                password: $("#resetPassword").val(),
                smsCode: $("#InputResetCode").val(),
                _csrf: document.querySelector('meta[name="_csrf"]').getAttribute('content')
            },
            function (date, status) {
                alert("状态：" + status
                    + "\n用户：" + date.extraInfo
                    + "\n行为：" + date.title
                    + "\n结果：" + date.content);
                clear({formId:'resetForm'});
            },
            'json');
        // Form.submit();
    }
}

function clear({formId:a}) {
    $("#"+a+" input").each(function () {
        $(this).val('')
    })
}