const { popUpMessage } = window.MyApp.footer;

// 팝업 메시지
if (typeof popUpMessage === 'string' && popUpMessage.trim()) {
    alert(popUpMessage);
}
