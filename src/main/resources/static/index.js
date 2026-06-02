let studentList = [];
let editModal;

document.addEventListener('DOMContentLoaded', () => {
    editModal = new bootstrap.Modal(document.getElementById('editModal'));
    loadStudents();
});

function loadStudents() {
    const params = new URLSearchParams();
    const name = document.getElementById('filterName').value;
    const surname = document.getElementById('filterSurname').value;
    const age = document.getElementById('filterAge').value;
    if (name) params.append('name', name);
    if (surname) params.append('surname', surname);
    if (age) params.append('age', age);

    fetch('http://localhost:8080/students?' + params)
        .then(res => res.json())
        .then(data => {
            studentList = data;
            renderTable();
        });
}

function renderTable() {
    const tbody = document.getElementById('studentsBody');
    tbody.innerHTML = '';
    studentList.forEach((s, i) => {
        const tr = document.createElement('tr');
        tr.innerHTML =
            '<td>' + s.id + '</td>' +
            '<td>' + s.name + '</td>' +
            '<td>' + s.surname + '</td>' +
            '<td>' + s.email + '</td>' +
            '<td>' + s.age + '</td>' +
            '<td>' + s.scholarship + '</td>' +
            '<td>' + (s.university ? s.university.name : '') + '</td>' +
            '<td><button class="btn btn-sm btn-outline-primary" onclick="openEdit(' + i + ')">Edit</button></td>';
        tbody.appendChild(tr);
    });
}

function insertStudent() {
    const body = {
        name: document.getElementById('name').value,
        surname: document.getElementById('surname').value,
        email: document.getElementById('email').value,
        age: parseInt(document.getElementById('age').value),
        scholarship: parseFloat(document.getElementById('scholarship').value),
        university: { id: parseInt(document.getElementById('universityId').value) }
    };

    fetch('/students', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    })
        .then(res => res.json())
        .then(() => loadStudents());
}

function openEdit(index) {
    const s = studentList[index];
    var elementById = document.getElementById('editId');
    elementById.value = s.id;
    document.getElementById('editName').value = s.name;
    document.getElementById('editSurname').value = s.surname;
    document.getElementById('editEmail').value = s.email;
    document.getElementById('editAge').value = s.age;
    document.getElementById('editScholarship').value = s.scholarship;
    document.getElementById('editUniversityId').value = s.university ? s.university.id : '';
    editModal.show();
}

function updateStudent() {
    const id = document.getElementById('editId').value;
    const body = {
        name: document.getElementById('editName').value,
        surname: document.getElementById('editSurname').value,
        email: document.getElementById('editEmail').value,
        age: parseInt(document.getElementById('editAge').value),
        scholarship: parseFloat(document.getElementById('editScholarship').value),
        university: { id: parseInt(document.getElementById('editUniversityId').value) }
    };

    fetch('/students/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'text/plain'
        },
        body: JSON.stringify(body)
    })
        .then(() => {
            editModal.hide();
            loadStudents();
        });
}