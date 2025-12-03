const form = document.getElementById('todo-form');
const input = document.getElementById('todo-input');
const list = document.getElementById('todo-list');

form.addEventListener('submit', async function(e){
    e.preventDefault();
    const todoText = input.value.trim();
    if(!todoText) return;

    const token = localStorage.getItem('token');

    const res = await fetch('/todos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify({text: todoText})
    });

    if(res.ok){
        const todo = await res.json();
        const li = document.createElement('li');
        li.textContent = todo.text;
        list.appendChild(li);
        input.value = '';
    } else {
        alert('Misslyckades att lägga todo');
    }
});
