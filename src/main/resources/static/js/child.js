

//cookie=document.cookie;
const childId = getCookie('userId');

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

const createTaskCards = (array) => {
    taskContainer.innerHTML = ''
    array.forEach(obj => {
        let taskCard = document.createElement("div")
        //taskCard.classList.add("m-2")
        taskCard.innerHTML = `
            <div class="d-flex flex-column gap-2">
                        <div class="card d-flex" style="width: 18rem; height: 18rem;">
                            <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                                <p class="card-status">${obj.status}</p>
                                <h4 class="fw-semibold mb-0">${obj.title}</h4>
                                <p class="text-muted">${obj.body}</p>
                            </div>
                        </div>
                    </div>
        `
        taskContainer.append(taskCard);
    })
}

async function getStarByChild(childId) {  //    /assigner/{assignerId}
    await fetch(`${baseUrl}users?id=${childId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createStars(data.starNum))
        .catch(err => console.error(err))

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

getTasksByChild(childId);
getStarByChild(childId);






