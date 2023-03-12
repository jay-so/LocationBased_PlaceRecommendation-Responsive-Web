$(document).ready(function () {
    $(document).ajaxError(function (e, xhr, settings) {
        if (xhr.status === 401 && localStorage.getItem("token")) {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            alert('장시간동안 접속하지 않아 로그아웃 되었습니다.');
            window.location.href = './login.html';
        }
    });
});

function logoutScript() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    alert('로그아웃을 완료했습니다.');
    window.location.href = './index.html';
}

function deleteUser() {
    if (localStorage.getItem('token')) {
        $.ajax({
            type: "DELETE",
            url: `http://localhost:8080/user`,
            data: {},
            success: function (response) {
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                alert("회원 탈퇴가 완료되었습니다.")
                window.location.href = './index.html';
            }
        });
    } else {
        alert('로그인이 필요한 서비스입니다.');
    }
}