// Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1]

//DOM Elements
const submitForm = document.getElementById("task-form")
const taskContainer = document.getElementById("task-container")

// Modal Elements
let taskBody = document.getElementById(`task-body`)
let updateTaskBtn = document.getElementById('update-task-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/starglApp/tasks"

const handleSubmit = async (e) => {
    e.preventDefault()
    let bodyObj = {
        body: document.getElementById("task-input").value
    }
    await addTask(bodyObj);
    document.getElementById("task-input").value = ''
}

async function addTask(obj) {
    const response = await fetch(`${baseUrl}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getTasks(userId);
    }
}

async function getTasks(userId) {
    await fetch(`${baseUrl}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createTaskCards(data))
        .catch(err => console.error(err))
}

async function handleDelete(taskId){
    await fetch(baseUrl + taskId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getTasks(userId);
}

async function getTaskById(taskId){
    await fetch(baseUrl + taskId, {
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

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getTasks(userId);
}

const createTaskCards = (array) => {
    taskContainer.innerHTML = ''
    array.forEach(obj => {
        let taskCard = document.createElement("div")
        taskCard.classList.add("m-2")
        taskCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
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
function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

const populateModal = (obj) =>{
    taskBody.innerText = ''
    taskBody.innerText = obj.body
    updateTaskBtn.setAttribute('data-task-id', obj.id)
}

getTasks(userId);

submitForm.addEventListener("submit", handleSubmit)

updateTaskBtn.addEventListener("click", (e)=>{
    let taskId = e.target.getAttribute('data-task-id')
    handleTaskEdit(taskId);
})