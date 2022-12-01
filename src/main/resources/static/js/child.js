//cookie=document.cookie;
const childId = getCookie('userId');
const childUsername = getCookie('username');
document.getElementById("hello-child").innerText=`Hello ${childUsername} !`;

function getCookie(name) {
   var value = "; " + document.cookie;
   var parts = value.split("; " + name + "=");
   if (parts.length == 2) return parts.pop().split(";").shift();
}

//DOM Elements
const taskContainer = document.getElementById("task-container")
const starContainer = document.getElementById("star-container")

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/starglApp/'

async function getTasksByChild(childId) {  //    /assigner/{assignerId}
    await fetch(`${baseUrl}tasks/assignee/${childId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createTaskCards(data))
        .catch(err => console.error(err))
}

const createTaskCards = (array) => {  // create cards for child page
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
        taskCard.classList.add("d-flex")
        taskCard.innerHTML = `
                            <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                                <p class="card-status">${obj.status} <span class="assignee-name"> by ${obj.assignee.username}</span></p>
                                <p class="start-date-info">task was created on ${startDate}</p>
                                <h6 class="fw-semibold mb-0">${obj.title}</h6>
                                <p <span class="due-date-info">must be completed until ${dueDate}</span></p>
                                <p class="text-muted">${obj.body} <span class="final-date-info">was completed on ${finalDate}</span</p>
                            </div>
        `
        taskContainer.append(taskCard);
    })
}

async function getStarByChild(childId) {  //    /assigner/{assignerId}
//    await fetch(`${baseUrl}users?id=${childId}`, {
//        method: "GET",
//        headers: headers
//    })
//        .then(response => response.json())
//        .then(data => createStars(data.starNum))
//        .catch(err => console.error(err))
//
//       //
       const response = await fetch(`${baseUrl}users?id=${childId}`, { // ???????????
                                method: "GET",
                                headers: headers
                            })
               .catch(err => console.error(err.message))

           const child = await response.json()

           if (response.status == 200) {
                createStars(child.starNum)
//                document.getElementById("hello-child").innerText=`Hello ${child.username} !`;
           }
}

const createStars = (starNum) => {

    starContainer.innerHTML = ''
    let i = 0;
    while (i < starNum){
        let taskCard = document.createElement("div")
        taskCard.classList.add("block__element")
        taskCard.innerHTML = `
                              <div class="block__content"></div>
                                `
        starContainer.append(taskCard);
        i++;
    }
    document.getElementById("count-star").innerText=`All stars ${starNum}`;
}

function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

getTasksByChild(childId);
getStarByChild(childId);






