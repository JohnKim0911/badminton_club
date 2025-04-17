const { profileImg, loginMemberId } = window.MyApp.memberDetail;

// DOM이 로드된 후
document.addEventListener("DOMContentLoaded", () => {

    // 프로필 사진변경 modal - 이미지 업로드시
    document.getElementById("profileImgInput").addEventListener("change", function () {
        handleProfileImgChange(this);
    });
    
    // 프로필 사진변경 modal - "기본 이미지 사용" 버튼
    document.getElementById("useDefaultImgBtn").addEventListener("click", function() {
        useDefaultImg();
    });
});

// 프로필 사진변경 modal - 이미지 검증 & 미리보기
function handleProfileImgChange(input) {
    const file = input.files[0];
    const preview = document.getElementById("previewImg");
    const maxSize = 10 * 1024 * 1024; // 10MB

    if (!file) return;

    // 1. Validate file type
    if (!file.type.startsWith("image/")) {
        alert("이미지 파일만 업로드 가능합니다.");
        input.value = "";
        return;
    }

    // 2. Validate file size
    if (file.size > maxSize) {
        alert("파일이 너무 큽니다. 최대 업로드 크기 1MB를 초과했습니다.");
        input.value = "";
        
        preview.src = profileImg ? `/upload/${profileImg}` : '/images/default_profile.png';
        return;
    }

    // 3. Show preview if valid
    const reader = new FileReader();
    reader.onload = function(e) {
        preview.src = e.target.result;
    };
    reader.readAsDataURL(file);
}

// 프로필 사진변경 modal - "기본 이미지 사용" 버튼
function useDefaultImg() {
    const preview = document.getElementById("previewImg");
    const fileInput = document.getElementById("profileImgInput");

    // Clear selected file
    fileInput.value = "";

    // Change preview image to default
    preview.src = "/images/default_profile.png";
}

// 비밀번호 변경 modal - 새로운/현재 비밀번호 검증 통과시 sumbit
async function isUpdatePwdFormValid(event) {
    event.preventDefault();

    if (!isNewPwdValid()) return;

    const isValid = await isCurrentPwdValid("update");
    if (isValid) {
        document.getElementById("changePwdForm").submit();
    }
}

// 비밀번호 변경 modal - 새로운 비밀번호 양식 검증
function isNewPwdValid() {
    const newPwdEl = document.getElementById("newPwd");
    const newPwdCheckEl = document.getElementById("newPwdCheck");

    const newPwd = newPwdEl.value;
    const newPwdCheck = newPwdCheckEl.value;
    
    if (newPwd.length < 8 || newPwd.length > 20) {
        newPwdEl.classList.add("border-danger");
        const resultEl = document.getElementById("newPwdResult");
        resultEl.innerHTML = "비밀번호는 8 ~ 20자이어야 합니다.";
        resultEl.style.display = "block";
        return false;
    }

    if (newPwd !== newPwdCheck) {
        newPwdCheckEl.classList.add("border-danger");
        const resultEl = document.getElementById("newPwdCheckResult");
        resultEl.innerHTML = "입력한 비밀번호가 서로 일치하지 않습니다.";
        resultEl.style.display = "block";
        return false;
    }
    return true;
}

// (공통) 비밀번호 변경, 회원탈퇴 modal - 현재 비밀번호 일치여부 검증
function isCurrentPwdValid(keyword) {
    let currentPwd;
    let checkResultEl;

    if (keyword == "update") {
        currentPwd = document.getElementById("currentPwd").value;
        checkResultEl = document.getElementById("currentPwdResult");
    } else if (keyword == "delete") {
        currentPwd = document.getElementById("pwdToDelete").value;
        checkResultEl = document.getElementById("pwdToDeleteResult");
    }

    return new Promise((resolve, reject) => {
        $.ajax({
            type: "post",
            url: "/members/" + loginMemberId +"/checkPwd",
            data: {
                "currentPwd": currentPwd
            },
            success: function(result) {
                if (result) {
                    resolve(true);
                } else {
                    checkResultEl.innerHTML = "비밀번호가 일치하지 않습니다.";
                    checkResultEl.style.display = "block";
                    resolve(false);
                }
            },
            error: function(error) {
                console.log("에러발생", error);
                reject(false);
            }
        });
    });
}

// 회원탈퇴 modal - 현재 비밀번호 검증 통과시 sumbit
async function isDeleteFormValid(event) {
    event.preventDefault();

    const isValid = await isCurrentPwdValid("delete");
    if (isValid) {
        document.getElementById("deleteForm").submit();
    }
}

// (공통) 비밀번호 변경, 회원탈퇴 modal - 에러 효과 지우기
function removeError(input) {
    input.classList.remove("border-danger");
    let errorMessage = input.parentElement.querySelector("p.text-danger");
    if (errorMessage) {
        errorMessage.style.display = "none";
    }
}