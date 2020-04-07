/**
 * 表单提交时验证
 * @returns {boolean}
 */
function checkForm() {

    // var Form = document.getElementById("formId");
    var errorCode = document.getElementById("errorCode"), bool = true;
    if (!InputCodeBlur()) {
        errorCode.innerHTML="验证码不能为空";
        return false;
    }
    if (!InputUsernameBlur()) bool = false;
    if (!InputPasswordBlur()) bool = false;
    if (!InputRepasswordBlur()) bool = false;
    if (!InputEmailBlur()) bool = false;
    if (!InputPhoneBlur()) bool = false;
    if (bool === true) {
        $.post("/register",
            {
                userName: $("#InputUsername").val(),
                password: $("#password").val(),
                email: $("#InputEmail").val(),
                phone: $("#InputPhone").val(),
                sex: "",
                userImageUrl: "/static/images/userDefault.png",
                isActive: "false",
                activeCode: "activeCode",
                accountNonLocked: "",
                createTime: "",
                updateTime: "",
                defaultAddressId: "0",
                tryCode:$("#InputCode").val(),
                _csrf: document.querySelector('meta[name="_csrf"]').getAttribute('content')
            },
            function (date, status) {
                alert("状态：" + status
                    + "\n用户：" + date.extraInfo
                    + "\n行为：" + date.title
                    + "\n结果：" + date.content
                    + "\n注意：请前往对应邮箱点击账号激活链接！！")
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
        ename.innerHTML = "用户名不能为空。";
        return false;
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
        epwd.innerHTML = "密码不为空。";
        return false;
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
        eemail.innerHTML = "邮箱不为空。";
        return false;
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
    /* 确认密码不为空 */
    if (rpwd.value === "") {
        erpwd.innerHTML = "确认密码不为空。";
        return false;
    } else {
        erpwd.innerHTML = "";
    }
    /* 确认密码与密码不一致 */
    var pwd = document.getElementById("password");
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
    return true;
}

/**
 * @return {boolean}
 */
function InputCodeBlur() {
    var tryCode =$("#InputCode");
    return !(tryCode.val() === "");
}

/**
 * 快速登陆
 */
function fastLogin(){
    var username = $("input[name='username']");
    var password = $("input[name='password']");
    var getCode =$("#sendBtn");
    username.attr("placeholder","电话号码");
    username.attr("name","mobile");
    password.attr("placeholder","验证码");
    password.attr("name","smsCode");
    getCode.css("visibility","visible")
}

/**
 * 账号登录
 */
function accountLogin() {
    var username = $("input[name='mobile']");
    var password = $("input[name='smsCode']");
    var getCode =$("#sendBtn");
    username.attr("placeholder","账号");
    username.attr("name","username");
    password.attr("placeholder","密码");
    password.attr("name","password");
    getCode.css("visibility","hidden")
}

/**
 * 请求发送验证码
 * @returns {boolean}
 */
function sendCode(){
    var eMobile = document.getElementById("errorMobile");
    var mobile = $("input[name='mobile']").val();
    if(mobile === '' || mobile.length !== 11){
        eMobile.innerHTML="请输入正确的手机号！";
        return false;
    }else{
        $.ajax({
            type: 'GET',
            url: '/code/sms',
            data: {
                mobile : mobile
            },
            dataType: 'json',
            success: function(data) {
                if(data){
                    timer();
                }else{
                    timer()
                    eMobile.innerHTML="获取验证码失败";
                }
            },
            error: function(data) {
                timer()
                eMobile.innerHTML='连接超时！';
            }
        });
    }
}
//倒计时/s
var wait = 60;

/**
 * 验证码发送倒计时
 */
function timer() {
    var getCode =$("#sendBtn");
    if(wait === 0){
       getCode.val("获取验证码");
       getCode.removeAttr("disabled");
       getCode.css("background", "#84C639").css("cursor", "pointer");
        wait = 60;
    }else{
        getCode.attr("disabled","true");
        getCode.css("background", "#FA1818").css("cursor", "not-allowed");
        getCode.val(wait + "秒后重发");
        wait--;
        setTimeout(function() {timer()}, 1000);
    }
}
