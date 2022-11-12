// Cookie
//const cookieArr = document.cookie.split("=")
//const parentId = cookieArr[1]
//const parentRole =
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
//const registerAddChild = document.getElementById('register-add-child')

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
//        registerAddChild.value = responseArr[0]
//        registerAddChild.innerHTML = `
//                    <div id="response-add-child">${responseArr[0]} </div>
//                `
            document.getElementById("register-add-child").innerText = responseArr[0];
            registerUsername.value = "";
            registerPassword.value = "";
    }
}

registerForm.addEventListener("submit", handleSubmitAddChild)

///////////////////////////////////

//const baseUrl = "http://localhost:8080/starglApp/tasks"

const handleSubmit = async (e) => {
    e.preventDefault()
    let bodyObj = {
        title: document.getElementById("task-title").value,
        body: document.getElementById("task-input").value
    }
    await addTask(bodyObj);
    document.getElementById("task-input").value = ''
    document.getElementById("task-title").value = ''
}

async function addTask(obj) {
    childId = childrenSelect.options[childrenSelect.selectedIndex].value;
    const response = await fetch(`${baseUrl}tasks/user/${parentId}/${childId}`, {  // "/user/{parentId}/{childId}"
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))

    if (response.status == 200) {
            document.getElementById("added-task-child").innerText = "Task is created successfully"; // ????

            return getTasksByParent(parentId);
    }
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


async function getTasksByChild(childId) {  //    /assignee/{assigneeId}  // how to get const assigneeId???
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

const createTaskCards = (array) => {
    taskContainer.innerHTML = ''
    array.forEach(obj => {
        let taskCard = document.createElement("div")
        taskCard.classList.add("m-2")
        taskCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                    <p class="card-status">${obj.status}</p>
                    <p class="card-title">${obj.title}</p>
                    <p class="card-text">${obj.body}</p>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                        <button onclick="getTaskById(${obj.id})" type="button" class="btn btn-primary"
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

const createChildrenList = (array) => {   // ? how to create list of children
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

submitForm.addEventListener("submit", handleSubmit)

updateTaskBtn.addEventListener("click", (e)=>{
    let taskId = e.target.getAttribute('data-task-id')
    handleTaskEdit(taskId);
})
