// Cookie
const parentId = getCookie('userId');

function getCookie(name) {
   var value = "; " + document.cookie;
   var parts = value.split("; " + name + "=");
   if (parts.length == 2) return parts.pop().split(";").shift();
}

const registerForm = document.getElementById('register-form')
const registerUsername = document.getElementById('register-username')
const registerPassword = document.getElementById('register-password')
const childrenSelect = document.getElementById('children-list')
const addChild = document.getElementById('add-child')
const closeButton = document.getElementById('close-btn')
//const  = document.getElementById('"added-task-child-error"')
//const registerAddChild = document.getElementById('register-add-child')

registerUsername.value = "";
registerPassword.value = "";

//DOM Elements
const submitForm = document.getElementById("task-form")
const taskContainer = document.getElementById("task-container")

// Modal Elements
let taskBody = document.getElementById(`task-body`)
let updateTaskBtn = document.getElementById('update-task-button')

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/starglApp/'

const handleSubmitAddChild = async (e) =>{       // add child method
    e.preventDefault()

    let bodyObj = {
        username: registerUsername.value,
        password: registerPassword.value
    }

    const response = await fetch(`${baseUrl}users/addChild/${parentId}`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))

    const responseArr = await response.json()

    if (response.status === 200) {
            document.getElementById("register-add-child").innerText = responseArr[0];
            registerUsername.value = "";
            registerPassword.value = "";
    }
}

registerForm.addEventListener("submit", handleSubmitAddChild)

///////////////////////////////////

const handleSubmit = async (e) => {
    e.preventDefault()
    let curDate = getDateByString(document.getElementById("task-due-date").value)
    let bodyObj = {
        title: document.getElementById("task-title").value,
        body: document.getElementById("task-input").value,
        dueDate: curDate.toISOString()
    }
    await addTask(bodyObj);
    document.getElementById("task-input").value = ''
    document.getElementById("task-title").value = ''
    document.getElementById("task-due-date").value = ''
}

async function addTask(obj) {
    if (childrenSelect.options.length==0) {
                document.getElementById("added-task-child-error").innerText = "It looks like you do not have kids yet...";
                return ;
         }

    childId = childrenSelect.options[childrenSelect.selectedIndex].value;

    const response = await fetch(`${baseUrl}tasks/user/${parentId}/${childId}`, {  // "/user/{parentId}/{childId}"
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))

    if (response.status == 200) {
            document.getElementById("added-task-child").innerText = "Task is created successfully";

            return getTasksByParent(parentId);
    }
}

function getDateByString(str) {
    arr = str.split(".");

    return new Date(parseInt(arr[2]),parseInt(arr[0]) - 1,parseInt(arr[1]));
}

async function getTasksByParent(parentId) {  //    /assigner/{assignerId}
    await fetch(`${baseUrl}tasks/assigner/${parentId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createTaskCards(data))
        .catch(err => console.error(err))
}

async function getChildrenByParent(parentId) {  // get all list by parent Id
    await fetch(`${baseUrl}users/${parentId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createChildrenList(data))
        .catch(err => console.error(err))
}


async function getTasksByChild(childId) {
    await fetch(`${baseUrl}tasks/assignee/${childId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createTaskCards(data))
        .catch(err => console.error(err))
}

async function handleDelete(taskId){
    await fetch(`${baseUrl}tasks/${taskId}`, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getTasksByParent(parentId);
}

async function getTaskById(taskId){   // @GetMapping("/{taskId}")
    await fetch(`${baseUrl}tasks/${taskId}`, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleTaskEdit(taskId){
    let bodyObj = {
        id: taskId,
        body: taskBody.value
    }

    await fetch(`${baseUrl}tasks`, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getTasksByParent(parentId);
}

async function handleCompleteTask(taskId) {
    result = prompt("How many stars would you like to give?", 1);

    const response = await fetch(`${baseUrl}tasks/${taskId}?star=${result}`, {
        method: "PATCH",
        headers: headers
    })
        .catch(err => console.error(err.message))

    const responseArr = await response.json()

    if (response.status == 200) {
    alert(responseArr[0]);
    return getTasksByParent(parentId)
    }
}

const createTaskCards = (array) => {
    taskContainer.innerHTML = ''
    array.forEach(obj => {
    let dueDate = ""
        if (obj.dueDate !== null) {
            dueDate = new Date(obj.dueDate).toDateString()
        }
    let finalDate =""
        if (obj.finalDate !== null) {
            finalDate = new Date(obj.finalDate).toDateString()
        }
    let startDate = ""
        if (obj.startDate !== null) {
            startDate = new Date(obj.startDate).toDateString()
        }

        let taskCard = document.createElement("div")
        taskCard.classList.add("card")
        taskCard.classList.add("m-2")
        taskCard.innerHTML = `
        <div id="task-response${obj.id}" class="task-response"></div>
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                    <div class="d-flex justify-content-between">
                         <p class="card-status">${obj.status} <span class="assignee-name">by ${obj.assignee.username}</span></p>
                         <p class="final-date-info">was completed on ${finalDate}</p>
                         <button class="btn btn-success btn-sm" onclick="handleCompleteTask(${obj.id})">Complete</button>
                    </div>
                    <p class="card-title">${obj.title}<span class="due-date-info">must be completed until ${dueDate}</span></p>
                    <p class="card-text">${obj.body}</p>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-warning btn-sm" onclick="handleDelete(${obj.id})">Delete</button>
                        <p class="start-date-info">was created on ${startDate}</p>
                        <button onclick="getTaskById(${obj.id})" type="button" class="btn btn-success btn-sm"
                        data-bs-toggle="modal" data-bs-target="#task-edit-modal">
                        Edit
                        </button>
                    </div>
                </div>
            </div>
        `
        taskContainer.append(taskCard);
    })
}

const createChildrenList = (array) => {
    childrenSelect.options.length = 0

    array.forEach(obj => {
            addOption(childrenSelect, obj.username, obj.id);
    })
}

function addOption(oListbox, text, value, isDefaultSelected, isSelected)
{
  var oOption = document.createElement("option");
  oOption.appendChild(document.createTextNode(text));
  oOption.setAttribute("value", value);

  if (isDefaultSelected) oOption.defaultSelected = true;
  else if (isSelected) oOption.selected = true;

  oListbox.appendChild(oOption);
}


function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

const populateModal = (obj) =>{
    taskBody.value = ''
    taskBody.value = obj.body
    updateTaskBtn.setAttribute('data-task-id', obj.id)
}

getTasksByParent(parentId);
getChildrenByParent(parentId);


addChild.addEventListener('click', function() {

    let activeBlock = document.querySelector('.description-hidden');
    activeBlock.classList.add('active');
    document.getElementById("register-add-child").innerText = "";

})

closeButton.addEventListener('click', function(){

				let activeBlock=document.querySelector('.description-hidden');
				activeBlock.classList.remove('active');
});

submitForm.addEventListener("submit", handleSubmit)

updateTaskBtn.addEventListener("click", (e)=>{
    let taskId = e.target.getAttribute('data-task-id')
    handleTaskEdit(taskId);
})
