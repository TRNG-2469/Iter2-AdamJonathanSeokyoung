import {
    getCurrentUser,
    getReimbursements,
    logout,
    resolveReimbursement,
    deleteReimbursement
} from "./api.js";

import {
    renderReimbursements
} from "./reimbursements.js";

const logoutBtn = document.getElementById("logout");
const historyBtn = document.getElementById("historyButton");
const filterForm = document.getElementById("filterForm");
const errorElement = document.getElementById("error");
const reimbursementList = document.getElementById("reimbursements");

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