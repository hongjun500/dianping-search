// header请求参数
var localStorage = window.localStorage;
function setLocalStorage(tokenHead, token){
    localStorage.setItem("tokenHead", tokenHead);
    localStorage.setItem("token", token);
}
var ajaxHeaders = {
    "Authorization": localStorage.getItem("tokenHead") + localStorage.getItem("token")
};
function clearLocalStorage(){
    localStorage.clear();
}
