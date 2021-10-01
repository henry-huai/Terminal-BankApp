document.getElementById("login-btn").addEventListener("click", attemptLogin);


function attemptLogin(){
    const errorDiv = document.getElementById("error-div");
    errorDiv.hidden = true;


    // get input values from input fields
    const user_id = document.getElementById("user_id-input").value; 
    const password = document.getElementById("password-input").value;
    // we're using const here, rather than var or let

    console.log(`user_id: ${user_id}, password: ${password}`); // this is a template literal

    // use XMLHttpRequest to create an http request - POST to http://localhost:8082/auth-demo/login
                                                    // + request body with credentials
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8085/bankapp/login");

    // define onreadystatechange callback for the xhr object (check for readystate 4)
    xhr.onreadystatechange = function(){

        if(xhr.readyState===4){
            // look at status code (either 401 or 200)
            // if the status code is 401 - indicate to the user that their credentials are invalid
            if(xhr.status===401){
                errorDiv.hidden = false; 
                errorDiv.innerText = "invalid admin credentials";
            } else if (xhr.status===200){
                // if the status code is 200 - get auth token from response and store it in browser, navigate to another page
                const token = xhr.getResponseHeader("Authorization");
                console.log(token); //"2:ADMIN"
                localStorage.setItem("token", token);
                window.location.href="http://localhost:8085/bankapp/static/user.html";
            } else {
                console.log(xhr.status);
                errorDiv.hidden = false; 
                errorDiv.innerText = "unknown error";
            }
        }

    }
    
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    // send request, with the username and password in the request body
    const requestBody = `user_id=${user_id}&password=${password}`;
    xhr.send(requestBody);
}