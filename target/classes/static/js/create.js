const createConst = document.getElementById("createForm");
createConst.addEventListener("submit", create);

async function create(event) {
    event.preventDefault();

    const target = event.currentTarget;
    const url = target.action;
    const formData = new FormData(target);

    let object = { };

    formData.forEach((value, key) => {
        if (key !== "roles") {
            object[key] = value;
        } else {
            if (Array.isArray(object[key])) {
                object[key].push(value);
            } else {
                object[key] = [];
                object[key].push(value);
            }
        }
    });

    let json = JSON.stringify(object);

    const method = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: json,
    };

    await fetch(url, method);

    target.reset();

    await tab();
    await usersTable();
}

function tab() {
    const tabUsers = document.getElementById("tab-users");
    const users = document.getElementById("users");
    const tabNewUser = document.getElementById("tab-new-user");
    const newUser = document.getElementById("new-user");

    tabUsers.setAttribute("class", "nav-link active show");
    users.setAttribute("class", "tab-pane fade active show");
    tabNewUser.setAttribute("class", "nav-link");
    newUser.setAttribute("class", "tab-pane fade");
}