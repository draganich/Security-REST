document.addEventListener("DOMContentLoaded", usersTable);

async function usersTable() {
    let users = await fetch("http://localhost:8080/api/users").then(res => res.json());
    let table = document.getElementById("usersTable");
    let data = "";

    for (let user of users) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(role.name.toString().replaceAll("ROLE_", " "));
        }
        data += "<tr>";

        data += "<td>" + user.id + "</td>";
        data += "<td>" + user.username + "</td>";
        data += "<td>" + user.name + "</td>";
        data += "<td>" + user.surname + "</td>";
        data += "<td>" + user.email + "</td>";
        data += "<td>" + roles + "</td>";
        data += "<td>" + '<a type="button" class = "btn btn-info" data-bs-toggle = "modal"' +
            'data-bs-target = "#modalFrame" onclick = "editUser(' + user.id + ')"> Edit </a>' + "</td>";

        data += '<td>' + '<a type="button" class = "btn btn-danger" data-bs-toggle = "modal" '
            + 'data-bs-target = "#modalFrame" onclick = "deleteUser(' + user.id + ')"> Delete </a>' + "</td>";

        data += "</tr>";
    }
    table.innerHTML = data;
}

