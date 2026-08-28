import {
    getCurrentUser,
    getReimbursements,
    logout,
    resolveReimbursement,
    deleteReimbursement,
    createReimbursement
} from "./api.js";

import {
    renderReimbursements
} from "./reimbursements.js";

const logoutBtn = document.getElementById("logout");
const historyBtn = document.getElementById("historyButton");
const filterForm = document.getElementById("filterForm");
const errorElement = document.getElementById("error");
const reimbursementList = document.getElementById("reimbursements");
const createBtn = document.getElementById("createReimbursementButton");
const createForm = document.getElementById("createReimbursementForm");
const cancelCreateBtn = document.getElementById("cancelCreateReimbursement");
const viewOwnReimbursementsBtn = document.getElementById("ownReimbursementsButton");

let currentUser;


async function handleResolve(id, status) {
    const response = await resolveReimbursement(id, status);

    if (!response) {
        return;
    }

    if (!response.ok) {
        showError("Could not resolve reimbursement");
        return;
    }

    await loadReimbursements();
}

async function handleDelete(id) {
    const response = await deleteReimbursement(id);

    if (!response) {
        return;
    }

    if (!response.ok) {
        showError("Could not delete reimbursement");
        return;
    }

    await loadReimbursements();
}
async function handleCreateReimbursement(event) {
    event.preventDefault();

    const data = {
        amount: Number(document.getElementById("amount").value),
        type: document.getElementById("type").value,
        description: document.getElementById("description").value
    };
    const response = await createReimbursement(data);

    if (!response) { //request failed auth
        return;
    }
    if (!response.ok) { //unknown error
        const error = await response.json();
        showError(error.error || "Could not create reimbursement");
        return;
    }

    createForm.reset();
    createForm.hidden = true;
    await loadReimbursements();

}

reimbursementList.addEventListener("click", async (event) => {
    const action = event.target.dataset.action;
    const id = event.target.dataset.id;

    if (action === "approve") {
        await handleResolve(id, "APPROVED");
    }

    if (action === "deny") {
        await handleResolve(id, "DENIED");
    }

    if (action == 'delete') {
        await handleDelete(id);
    }
});

logoutBtn.addEventListener("click", logout);

historyBtn.addEventListener("click", async () => {
    await loadReimbursements("/reimbursements/history");
});

viewOwnReimbursementsBtn.addEventListener("click", async () => {
    await loadReimbursements(`/reimbursements/own`);
})

createBtn.addEventListener("click", () => {
    createForm.hidden = !createForm.hidden;
});

cancelCreateBtn.addEventListener("click", () => {
    createForm.reset();
    createForm.hidden = true;
});

createForm.addEventListener("submit", handleCreateReimbursement);

filterForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const status = document.getElementById("status").value;
    const departmentId = document.getElementById("department").value;

    const params = new URLSearchParams();

    if (status) {
        params.append("status", status);
    }

    if (departmentId) {
        params.append("departmentId", departmentId);
    }

    let endpoint = "/reimbursements";

    if (params.toString()) {
        endpoint += `?${params.toString()}`;
    }

    await loadReimbursements(endpoint);
});

async function loadCurrentUser() {
    const response = await getCurrentUser();

    if (!response) {
        return false;
    }

    if (!response.ok) {
        showError("Could not load current user");
        return false;
    }

    currentUser = await response.json();

    document.getElementById("username").textContent =
        currentUser.username;

    document.getElementById("role").textContent =
        currentUser.role;

    configureDashboard();

    return true;
}

async function loadReimbursements(endpoint = "/reimbursements") {
    const response = await getReimbursements(endpoint);

    if (!response) {
        return;
    }

    if (!response.ok) {
        showError("Could not load reimbursements");
        return;
    }

    const reimbursements = await response.json();

    renderReimbursements(
        reimbursements,
        currentUser
    );
}

function configureDashboard() {
    const isManager = currentUser.role === "MANAGER";

    historyBtn.hidden = !isManager;
    viewOwnReimbursementsBtn.hidden = !isManager;

    document.getElementById("departmentFilter").hidden =
        !isManager;
}

function showError(message) {
    errorElement.textContent = message;
}

async function initializePage() {
    const loggedIn = await loadCurrentUser();

    if (!loggedIn) {
        return;
    }

    await loadReimbursements();
}

initializePage();