document.querySelector("#loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    console.log("FORM SUBMITTED");

    const username = document.querySelector("#username").value;
    const password = document.querySelector("#password").value;

    const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    });

    if (response.ok) {

        const data = await response.json(); 

        localStorage.setItem("token", data.token);
        window.location.href = "reimbursements.html";
    } else {
        const error = await response.json();
        document.querySelector("#error").textContent =
            error.error || "Login failed";
    }
});