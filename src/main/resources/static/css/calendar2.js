// Retrieve saved notes from localStorage (if any)
let notes = JSON.parse(localStorage.getItem("notes")) || {};

// Month names for display
const monthNames = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
];

let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();

document.addEventListener("DOMContentLoaded", () => {
    generateCalendar(currentYear, currentMonth);
    setupModal();
});

// Generate calendar for the current month
function generateCalendar(year, month) {
    const calendarDays = document.getElementById('calendar-days');
    calendarDays.innerHTML = ''; // Clear previous calendar content

    const monthYear = document.getElementById('month-year');
    monthYear.textContent = `${monthNames[month]} ${year}`; // Display month and year

    const firstDay = new Date(year, month, 1).getDay(); // Day of the week the month starts on
    const daysInMonth = new Date(year, month + 1, 0).getDate(); // Total days in the month

    let row = document.createElement('tr');

    // Fill in empty cells before the first day of the month
    for (let i = 0; i < firstDay; i++) {
        row.appendChild(document.createElement('td'));
    }

    // Add the days of the month
    for (let day = 1; day <= daysInMonth; day++) {
        const cell = document.createElement('td');
        cell.textContent = day;
        cell.classList.add('calendar-day');

        // Add click event to open modal for notes
        cell.addEventListener('click', () => openNoteModal(year, month, day));

        row.appendChild(cell);

        if ((firstDay + day) % 7 === 0 || day === daysInMonth) {
            calendarDays.appendChild(row);
            row = document.createElement('tr');
        }
    }
}

// Handle month navigation
function changeMonth(delta) {
    currentMonth += delta;

    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    } else if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }

    generateCalendar(currentYear, currentMonth);
}

// Modal functionality
function setupModal() {
    const modal = document.getElementById("note-modal");
    const closeModal = document.getElementById("close-modal");

    closeModal.addEventListener("click", () => {
        modal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
}

// function openNoteModal(year, month, day) {
//     const modal = document.getElementById("note-modal");
//     const noteDate = document.getElementById("note-date");
//     const noteContent = document.getElementById("note-content");
//
//     const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
//     noteDate.textContent = formattedDate;
//
//     // Check if a note exists for this day
//     if (notes[formattedDate]) {
//         noteContent.innerHTML = `<p>${notes[formattedDate]}</p>`; // Display the saved note
//     } else {
//         noteContent.innerHTML = "<p>No notes for this day.</p>"; // Show message if no note exists
//     }
//
//     modal.style.display = "block";
// }

// function openNoteModal(year, month, day) {
//     const modal = document.getElementById("note-modal");
//     const noteDate = document.getElementById("note-date");
//     const noteContent = document.getElementById("note-content");
//
//     const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
//     noteDate.textContent = formattedDate;
//
//     // Fetch the note from the server
//     fetch(`/calendar/get-note?date=${formattedDate}`)
//         .then(response => response.text())
//         .then(data => {
//             noteContent.innerHTML = `<p>${data}</p>`; // Display the note or a message
//             modal.style.display = "block";
//         })
//         .catch(error => console.error('Error:', error));
// }

function openNoteModal(year, month, day) {
    const modal = document.getElementById("note-modal");
    const noteDate = document.getElementById("note-date");
    const noteContent = document.getElementById("note-content");

    const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    noteDate.textContent = formattedDate;

    // Fetch the note from the server
    fetch(`/calendar/get-note?date=${formattedDate}`)
        .then(response => response.text())
        .then(data => {
            noteContent.innerHTML = `<p>${data}</p>`; // Display the note or a message
            modal.style.display = "block";
        })
        .catch(error => console.error('Error:', error));
}

