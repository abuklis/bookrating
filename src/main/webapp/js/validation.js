function validateForm(){
    //debugger;
    var form = document.forms["registrationForm"];
    var name = form["name"];
    var login = form["login"];
  // var email = form["email"];
 //   var age = form["age"];
  //  var password = form["password"];
   // var passwordConfirm = form["password-confirm"];

    var nameRegEx = /^([a-zA-Z])+[-\']?([a-zA-Z])+$/;
    var loginRegExp = /^[\w\-]{5,10}$/;
    var emailRegEx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var passwordRegEx = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,10}$/;
    var validationCheck = new Array();
    validationCheck.push(validateUsername(name, nameRegEx));
    validationCheck.push(validateWithRegExp(login, loginRegExp));
    validationCheck.push(validateWithRegExp(email, emailRegEx));
    validationCheck.push(validateAge(age));
    validationCheck.push(validateWithRegExp(password, passwordRegEx));
    validationCheck.push(validatePasswordConfirmation(passwordConfirm, password));
    if(validationCheck.includes(false)){
        return false;
    } else {
        return true;
    }
}

function validateAge(age){
    if (age.value<120 && age.value>7){
        makeValid(age);
        return true;
    } else {
        makeInvalid(age);
        return false;
    }
}

function validateUsername(username, usernameRegEx){
    if((username.value.match(usernameRegEx) == null) || (username.value.length < 2) ||
        (username.value.length > 30)){
        makeInvalid(username)
        return false;
    } else {
        makeValid(username);
        return true;
    }
}

function makeValid(element){
    if(element.className == "invalid") element.className = "";
    element.parentNode.getElementsByClassName("error-message")[0].style.display="none";
}

function makeInvalid(element){
    element.className = "invalid";
    element.parentNode.getElementsByClassName("error-message")[0].style.display="block";
}

function validateWithRegExp(element, regExp){
    if(element.value.match(regExp) == null){
        makeInvalid(element);
        return false;
    } else{
        makeValid(element);
        return true;
    }
}

function validatePasswordConfirmation(passwordConfirm, password){
    if(passwordConfirm.value != password.value){
        makeInvalid(passwordConfirm);
        return false;
    } else {
        makeValid(passwordConfirm);
        return true;
    }
}

