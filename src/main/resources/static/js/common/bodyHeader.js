// DOMContentLoaded
document.addEventListener("DOMContentLoaded", () => {

    // 로그아웃 재확인 팝업
    const logoutMenu = document.getElementById("logoutMenu");
    if (logoutMenu) {
        logoutMenu.addEventListener("click", function (event) {
            confirmLogout(event);
        });
    }

});

// 로그아웃 재확인 팝업
function confirmLogout(event) {
    event.preventDefault();
    if (confirm("로그아웃 하시겠습니까?")) {
        document.getElementById('bodyHeaderLogoutForm').submit();
    }
}