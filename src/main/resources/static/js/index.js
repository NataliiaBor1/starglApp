
let goLoginParentBtn = document.getElementById('go-login-parent')
let goLoginChildBtn = document.getElementById('go-login-child')



//const populateModal = (obj) =>{
//    taskBody.value = ''
//    taskBody.value = obj.body
//    goLoginParentBtn.setAttribute('data-task-id', obj.id)
//}

//function goLoginParent() {
//        document.location.href = "http://localhost:8080/login.html";
//    }
//    function goLoginChild() {
//        document.location.href = "http://localhost:8080/login.html";
//    }

goLoginParentBtn.addEventListener("click", (e)=>{
        document.cookie = `role=PARENT`
        window.location.replace("http://localhost:8080/login.html");
})

goLoginChildBtn.addEventListener("click", (e)=>{
        document.cookie = `role=CHILD`
        window.location.href = "http://localhost:8080/login.html";
})

//document.getElementById('mybutton').onclick = function() {
//  window.location.href = 'redirect-url';
//};