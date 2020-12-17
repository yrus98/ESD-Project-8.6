let login_form = document.getElementById('login-validation');

login_form.addEventListener('submit', async (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (login_form.checkValidity() === true) {
        let response = await fetch('api/students/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                email: document.getElementById('email').value,
            })
        });
        window.sessionStorage["curr_email"] = document.getElementById('email').value;
        let result = await response;
        if(result["status"]===200){
            location.href = "dashboard.html";

        }else{
            document.getElementById("login-alert").style.display = "block";
        }

    }
});