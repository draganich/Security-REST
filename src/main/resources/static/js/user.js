document.addEventListener("DOMContentLoaded", userString);

async function userString() {
    let user = await fetch("http://localhost:8080/api/user/").then(res => res.json());
    let table = document.getElementById("userString");
    let data = "";

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
    data += "</tr>";

    table.innerHTML = data;
}