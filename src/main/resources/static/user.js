
const tableOneUser = document.getElementById('tableOneUser')
const currentUserLogin = document.getElementById('currentUserLogin')
const currentUserRoles = document.getElementById('currentUserRoles')

const url = 'http://localhost:8080/user/'


//Таблица пользователя

fetch(url)
    .then(response => response.json())
    .then(data => {
        let columnElement = ''
        let columnElement2 = ''
        if (data.username === currentUserLogin.innerText)
        {
            columnElement += `<tr>
              <td>${data.id}</td>
              <td>${data.firstName}</td>
              <td>${data.username}</td>
              <td>${data.roles.map(role => role.name.substring(5))}</td>
            </tr>
            `
            columnElement2 += `
            ${data.roles.map(role => role.name.substring(5))}
           `
        }
        tableOneUser.innerHTML = columnElement
        currentUserRoles.innerHTML = columnElement2
    })



