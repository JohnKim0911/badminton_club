// signUpForm.html, memberUpdate.html 에서 공통으로 사용됨.

const { addressChildrenMap, addressLv1, addressLv2, addressLv3 } = window.MyApp.memberForm;

// 주소 관련
const lv1Select = document.getElementById("lv1Select");
const lv2Select = document.getElementById("lv2Select");
const lv3Select = document.getElementById("lv3Select");

// DOM이 로드된 후
document.addEventListener("DOMContentLoaded", () => {

    // 이전에 선택했던 주소값 보여주기
    displayPreviousAddress();

    // 주소 lv1 선택시
    lv1Select.addEventListener("change", function () {
        populateLv2Options(null);
        resetAndHideLv3Options();
    });

    // 주소 lv2 선택시
    lv2Select.addEventListener("change", function () {
        populateLv3Options(null);
    });

    // 서버에서 전달해준 검증에러 지우기 (해당 input에서 다른 곳으로 focus 이동시 작동)
    handleRemovingErrors();

});

// 기존 주소값 보여주기
function displayPreviousAddress() {
    // Reset lv1, lv2 to "==선택==" if not selected
    if (!addressLv1) {
        lv1Select.selectedIndex = 0; // This selects the first <option>
        lv2Select.selectedIndex = 0;
    }

    if (lv1Select.value) {
        populateLv2Options(addressLv2);
    }

    if (addressLv2) {
        populateLv3Options(addressLv3);
    }
}

// 주소 lv2 조작
function populateLv2Options(addressLv2) {
    const lv1Id = lv1Select.value;
    const lv2List = (addressChildrenMap.lv1 && addressChildrenMap.lv1[lv1Id]) || [];

    lv2Select.innerHTML = '<option value="" selected disabled>==선택==</option>';
    lv2List.forEach(lv2 => {
        const option = document.createElement("option");
        option.value = lv2.id;
        option.text = lv2.name;
        if (lv2.id == addressLv2) option.selected = true;
        lv2Select.appendChild(option);
    });
}

// 주소 lv3 조작
function populateLv3Options(addressLv3) {
    const lv2Id = lv2Select.value;
    const lv3List = (addressChildrenMap.lv2 && addressChildrenMap.lv2[lv2Id]) || [];

    lv3Select.innerHTML = '<option value="" selected disabled>==선택==</option>';
    if (lv3List.length > 0) {
        lv3Select.style.display = "block";
        lv3Select.setAttribute("required", "required");
        lv3List.forEach(lv3 => {
            const option = document.createElement("option");
            option.value = lv3.id;
            option.text = lv3.name;
            if (lv3.id == addressLv3) option.selected = true;
            lv3Select.appendChild(option);
        });
    } else {
        lv3Select.style.display = "none";
        lv3Select.removeAttribute("required");
    }
}

// 주소 lv3 리셋 & 숨기기
function resetAndHideLv3Options() {
    lv3Select.innerHTML = '<option value="" selected disabled>==선택==</option>';
    lv3Select.style.display = "none";
}

// 부트스트랩 폼 검증 
// (() => { ... })(); // Immediately Invoked Function Expression (IIFE).
// It keeps the variables and functions inside it private, avoiding pollution of the global scope.
(() => {
    'use strict'  // Enables strict mode for better error handling.

    const forms = document.querySelectorAll('.needs-validation')  // Selects all form elements with the class "needs-validation".

    Array.from(forms).forEach(form => { // Converts NodeList to an array and loops over each form.

        form.addEventListener('submit', event => { // When a form is submitted...
            if (!form.checkValidity()) {  // If the form is invalid...
                event.preventDefault() // Prevent form submission.
                event.stopPropagation() // Stop event bubbling.
            }
            form.classList.add('was-validated')  // Adds Bootstrap validation styles.
        }, false)
    })
})(); // Immediately calls the function after defining it.

// 서버에서 전달해준 검증에러 지우기 (해당 input에서 다른 곳으로 focus 이동시 작동)
function handleRemovingErrors() {
    const inputs = document.querySelectorAll(".validate-input"); 

    inputs.forEach(input => {
        input.addEventListener("blur", function () {
            removeError(this);
        });
    });
}

// 서버에서 전달해준 검증에러 지우기
function removeError(input) {
    input.classList.remove("border-danger");
    let errorMessage = input.parentElement.querySelector("p.text-danger");
    if (errorMessage) {
        errorMessage.style.display = "none";
    }
}