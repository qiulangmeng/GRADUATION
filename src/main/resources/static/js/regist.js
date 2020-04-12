
/**
 * 表单提交时验证
 * @returns {boolean}
 */
function checkForm() {

    // var Form = document.getElementById("formId");
    var bool=true;
    if (!InputSmsCodeBlur('sendRegistBtn')) bool = false;
    if (!InputUsernameBlur()) bool = false;
    if (!InputPasswordBlur()) bool = false;
    if (!InputRepasswordBlur()) bool = false;
    if (!InputEmailBlur()) bool = false;
    if (!InputPhoneBlur()) bool = false;
    if (bool === true) {
        $.post("/register",
            {
                mobile: $("#InputPhone").val(),
                province: $("#InputProvince").val(),
                city: $("#InputCity").val(),
                area: $("#InputArea").val(),
                detailAddress: $("#InputAddress").val(),
                userName: $("#InputUsername").val(),
                password: $("#password").val(),
                email: $("#InputEmail").val(),
                sex: "",
                userImageUrl: "/static/images/userDefault.png",
                accountNonLocked: true,
                createTime: "",
                updateTime: "",
                smsCode: $("#InputSmsCode").val(),
                _csrf: document.querySelector('meta[name="_csrf"]').getAttribute('content')
            },
            function (date, status) {
                alert("状态：" + status
                    + "\n用户：" + date.extraInfo
                    + "\n行为：" + date.title
                    + "\n结果：" + date.content);
                clear({formId:'registForm'})
            },
            'json')

        // Form.submit();
    }
}

/**
 * 用户名输入框失去焦点
 * @return {boolean}
 */
function InputUsernameBlur() {
    var uname = document.getElementById("InputUsername");
    var ename = document.getElementById("errorName");
    /* 用户名为空/不为空 */
    if (uname.value === "") {
        return true;
    } else {
        ename.innerHTML = "";
    }
    /* 密码长度 */
    if (uname.value.length < 4 || uname.value.length > 16) {
        ename.innerHTML = "长度为4-16。";
        return false;
    } else {
        ename.innerHTML = "";
    }
    const url = "/checkUsername?username=" + uname.value;
    $.get(url,
        function (date, status) {
            if (String(date.jugde) === "true") {
                ename.innerHTML = "用户名已被注册。";
                return false;
            } else {
                ename.innerHTML = "";
            }
        },
        'json');
    return true;
}

/**
 * 密码输入框失去焦点
 * @return {boolean}
 */
function InputPasswordBlur() {
    var pwd = document.getElementById("password");
    var epwd = document.getElementById("errorPassword");
    /* 密码为空/不为空 */
    if (pwd.value === "") {
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

/**
 * 邮箱输入框失去焦点
 * @return {boolean}
 */
function InputEmailBlur() {
    var email = document.getElementById("InputEmail");
    var eemail = document.getElementById("errorEmail");
    /* 邮箱不为空 */
    if (email.value === "") {
        return true;
    } else {
        eemail.innerHTML = "";
    }
    /* 邮箱格式验证 */
    var reg = /^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/;
    if (reg.test(email.value) === false) {
        eemail.innerHTML = "邮箱格式错误。";
        return false;
    } else {
        eemail.innerHTML = "";
    }
    return true;
}

/**
 * 确认密码输入框失去焦点
 * @return {boolean}
 */
function InputRepasswordBlur() {
    var rpwd = document.getElementById("InputRepassword");
    var erpwd = document.getElementById("errorRepassword");
    var pwd = document.getElementById("password");
    /* 确认密码不为空 */
    if (pwd.value === "" && rpwd.value === "") {
        return true;
    } else if (pwd.value === "" && rpwd.value !== "") {
        erpwd.innerHTML = "请先填写密码";
        return false;
    } else if (pwd.value !== "" && rpwd.value === "") {
        erpwd.innerHTML = "请填写确认密码";
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

/**
 * @return {boolean}
 */
function InputPhoneBlur() {
    var phone = document.getElementById("InputPhone");
    var ephone = document.getElementById("errorPhone");
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
            if (String(date.jugde) === "true") {
                ephone.innerHTML = "电话已被注册。";
                return false;
            } else {
                ephone.innerHTML = "";
            }
        },
        'json');
    return true;
}


/**
 * 快速登陆
 */
function fastLogin() {
    var username = $("input[name='username']");
    var password = $("input[name='password']");
    var form = $("#loginForm")
    var getCode = $("#sendBtn");
    username.attr("placeholder", "电话号码");
    username.attr("name", "mobile");
    password.attr("placeholder", "验证码");
    password.attr("name", "smsCode");
    password.attr("onblur","InputSmsCodeBlur('InputLoginCode')")
    form.attr("action", "/sms/login");
    form.attr("th:action", "@{/sms/login}");
    getCode.css("visibility", "visible")
}

/**
 * 账号登录
 */
function accountLogin() {
    var mobile = $("input[name='mobile']");
    var smsCode = $("input[name='smsCode']");
    var form = $("#loginForm")
    var getCode = $("#sendBtn");
    mobile.attr("placeholder", "账号");
    mobile.attr("name", "username");
    smsCode.attr("placeholder", "密码");
    smsCode.attr("name", "password");
    smsCode.attr("onblur","");
    form.attr("action", "/account/login");
    form.attr("th:action", "@{/account/login}")
    getCode.css("visibility", "hidden")
}

//倒计时/s
var wait = 60;
var wait1 = 60;
/**
 * 登录请求发送验证码
 * @returns {boolean}
 */
function sendCode() {
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
                redisname: "smsCode",
            },
            dataType: 'json',
            success: function (data) {
                if (data) {
                    timer('sendBtn',wait);
                } else {
                    timer('sendBtn',wait);
                    eMobile.innerHTML = "获取验证码失败";
                }
            },
            error: function (data) {
                timer('sendBtn',wait);
                eMobile.innerHTML = '连接超时！';
            }
        });
    }
}

/**
 * 注册请求发送验证码
 * @returns {boolean}
 */
function sendRegistCode() {
    var eMobile = document.getElementById("errorPhone");
    var mobile = $("input[name='phone']").val();
    if (mobile === '' || mobile.length !== 11) {
        eMobile.innerHTML = "请输入正确的手机号！";
        return false;
    } else {
        $.ajax({
            type: 'GET',
            url: '/code/sms',
            data: {
                mobile: mobile,
                redisname: "smsRegistCode",
            },
            dataType: 'json',
            success: function (data) {
                if (data) {
                    timer('sendRegistBtn',wait1);
                } else {
                    timer('sendRegistBtn',wait1);
                    eMobile.innerHTML = "获取验证码失败";
                }
            },
            error: function (data) {
                timer('sendRegistBtn',wait1);
                eMobile.innerHTML = '连接超时！';
            }
        });
    }
}


/**
 * 验证码发送倒计时
 */
function timer(a,b) {
    var getCode = $("#"+a);
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
            timer(a,b)
        }, 1000);
    }
}


/**
 * 隐藏可选项
 */
function hide() {
    $("#InputUsername").css("display", "none");
    $("#errorName").css("display", "none");
    $("#complexitywrap").css("display", "none");
    $("#password").css("display", "none");
    $("#errorPassword").css("display", "none");
    $("#InputRepassword").css("display", "none");
    $("#errorRepassword").css("display", "none");
    $("#InputEmail").css("display", "none");
    $("#errorEmail").css("display", "none");

}

function show() {
    $("#InputUsername").css("display", "block");
    $("#errorName").css("display", "block");
    $("#complexitywrap").css("display", "block");
    $("#password").css("display", "block");
    $("#errorPassword").css("display", "block");
    $("#InputRepassword").css("display", "block");
    $("#errorRepassword").css("display", "block");
    $("#InputEmail").css("display", "block");
    $("#errorEmail").css("display", "block");
}

$('.toggle1').click(function () {
    const display = $("#InputUsername").css("display");
    if (display === "none") {
        show();
    } else if (display === "block") {
        hide();
    }
});

/**
 * @return {boolean}
 */
function InputSmsCodeBlur(a) {
    var smsCode = $("#"+a);
    if(smsCode.val()===""){
        alert("验证码不能为空")
        return false;
    }else {
        return true;
    }

    if (smsCode.val().length !== 6) {
        alert("请输入6未验证码")
        return false;
    } else {
        return true;
    }

}
function clear({formId:a}) {
    $("#"+a+" input").each(function () {
        $(this).val('')
    })
}
