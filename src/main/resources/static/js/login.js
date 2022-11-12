let loginForm = document.getElementById('login-form')
let loginUsername = document.getElementById('login-username')
let loginPassword = document.getElementById('login-password')

const role = getCookie('role');
const registerBtn = document.getElementById('register-href')

function getCookie(name) {
   var value = "; " + document.cookie;
   var parts = value.split("; " + name + "=");
   if (parts.length == 2) return parts.pop().split(";").shift();
}

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/starglApp/users'

const handleSubmit = async (e) => {
    e.preventDefault()

    let bodyObj = {
        username: loginUsername.value,
        password: loginPassword.value
    }

    const response = await fetch(`${baseUrl}/login`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))

    const responseArr = await response.json()

    if (response.status === 200) {
        document.cookie = `userId=${responseArr[1]}`
        window.location.replace(responseArr[0])
    }
}

function identifyRole(role) {
if (role === 'CHILD'){
    registerBtn.classList.add('disabled');
  }else{
    registerBtn.classList.remove('disabled');  // classList.remove('disabled')
  }
}

identifyRole(role);

    loginForm.addEventListener("submit", handleSubmit)
