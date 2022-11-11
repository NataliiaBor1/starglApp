
let goLoginParentBtn = document.getElementById('go-login-parent')
let goLoginChildBtn = document.getElementById('go-login-child')

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/starglApp/users'



//<p><button id="go-login-parent" class="button" href="./login.html">Parent</button>
//        <button id="go-login-child" class="button" href="./login.html">
//            <img src="images/child.gif" alt="" style="vertical-align:middle">
//            Child
//        </button></p>

//const populateModal = (obj) =>{
//    taskBody.value = ''
//    taskBody.value = obj.body
//    goLoginParentBtn.setAttribute('data-task-id', obj.id)
//}

goLoginParentBtn.addEventListener("click", (e)=>{
        window.location.href('./login.html')
//    let taskId = e.target.getAttribute('data-task-id')
//    handleTaskEdit(taskId);
})

goLoginChildBtn.addEventListener("click", (e)=>{
        window.location.href('./login.html')
//    let taskId = e.target.getAttribute('data-task-id')
//    handleTaskEdit(taskId);
})

//document.getElementById('mybutton').onclick = function() {
//  window.location.href = 'redirect-url';
//};