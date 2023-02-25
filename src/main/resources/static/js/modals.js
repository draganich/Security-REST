const modalsConst = document.getElementById("modalsForm");

async function getUser(id) {
    const url = "api/users/" + id;
    let response = await fetch(url);
    return response.json();
}

async function editUser(id) {

    let data = await getUser(id);

    const {elements} = modalsConst;

    const editTitle = document.getElementById("title");
    const editButton = document.getElementById("submit");

    editTitle.innerHTML = "Edit user";
    editButton.innerHTML = "Edit";

    modalsConst.setAttribute("action", "api/users/" + id);
    modalsConst.addEventListener("submit", editData);

    for (const [key, value] of Object.entries(data)) {
        const modalElement = elements.namedItem(key);
        if (key !== "id" && modalElement !== null) {
            modalElement.removeAttribute("disabled");
        }

        modalElement && (modalElement.value = value);
        if (key === "roles") {
            let options = Array.from(modalElement);
            for (let role of value) {
                for (let option of options) {
                    if (option.value === role.name) {
                        option.selected = true;
                    }
                }
            }
        }

    }
}

async function deleteUser(id) {

    let data = await getUser(id);

    const {elements} = modalsConst;

    const deleteTitle = document.getElementById("title");
    const deleteButton = document.getElementById("submit");

    deleteTitle.innerHTML = "Delete user";
    deleteButton.innerHTML = "Delete";

    modalsConst.setAttribute("action", "api/users/" + id);
    modalsConst.addEventListener("submit", deleteData);

    let modalElements = modalsConst.elements;
    for (let modalElement of modalElements) {
        if (modalElement !== document.getElementById("submit")) {
            modalElement.setAttribute("disabled", "disabled");
        }
    }

    for (const [key, value] of Object.entries(data)) {
        const field = elements.namedItem(key);
        if (key !== "password") {
            field && (field.value = value);
        }
        if (key === "roles") {
            let options = Array.from(field);
            for (let role of value) {
                for (let option of options) {
                    if (option.value === role.name) {
                        option.selected = true;
                    }
                }
            }
        }

    }
}

async function editData(event) {
    event.preventDefault();

    const target = event.currentTarget;
    const url = target.action;

    const formData = new FormData(target);
    let object = {};
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
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: json,
    };

    await fetch(url, method);

    target.reset();
    $('#close').click();
    await usersTable();
}

async function deleteData(event) {
    event.preventDefault();

    const target = event.currentTarget;
    const url = target.action;

    const method = {
        method: "DELETE",
    };

    await fetch(url, method);

    target.reset();
    $('#close').click();
    await usersTable();
}