function goTo(page){
    window.location.href = page;
}

function logout(){
    localStorage.removeItem('token');
    goTo('index.html');
}