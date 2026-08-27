document.querySelector("#registerForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const role = document.querySelector('input[name="role"]:checked').value;

    const user = {
        username: document.querySelector("#username").value,
        hashedPassword: document.querySelector("#password").value,
        firstName: document.querySelector("#firstName").value,
        lastName: document.querySelector("#lastName").value,
        departmentId: Number(document.querySelector("#departmentId").value),
        role: document.querySelector('input[name="role"]:checked').value
    };

    const response = await fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    });

    if (response.ok) {
        window.location.href = "index.html";
    } else {
        const error = await response.json();

        document.querySelector("#error").textContent =
            error.error || "Registration failed";
    }
});