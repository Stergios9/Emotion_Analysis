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

// Clear all notes
function clearAllNotes() {
    // Reset the notes object to an empty object
    notes = {};

    // Optionally, alert the user
    alert("All notes have been cleared!");

    // Update the calendar display if needed (for example, reload the calendar or modal)
    generateCalendar(currentYear, currentMonth);
}

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

    // const saveNoteButton = document.getElementById("save-note");
    // saveNoteButton.enabled = true;

    closeModal.addEventListener("click", () => {
        modal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
}

function openNoteModal(year, month, day) {
    const modal = document.getElementById("note-modal");
    const noteDate = document.getElementById("note-date");
    const noteInput = document.getElementById("note-input");
    const patientName = document.getElementById("name");
    const patientEmail = document.getElementById("email");

    const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    noteDate.textContent = formattedDate;


    noteInput.value = notes[formattedDate] || ""; // Pre-fill with existing note if available
    patientName.value = ""; // Clear the name field
    patientEmail.value = ""; // Clear the email field


    modal.style.display = "block";
}


function saveNote() {
    const noteDate = document.getElementById("note-date").textContent;
    const noteInput = document.getElementById("note-input").value;
    const patientName = document.getElementById("name").value
    const patientEmail = document.getElementById("email").value

    // Ensure all fields are filled out
    if (!noteInput || !patientName || !patientEmail) {
        alert("All fields are mandatory. Please fill out the note, name, and email.");
        return;
    }

    if(noteDate){
        // Send a POST request to save or update the note
        fetch(`/calendar/add-note?date=${noteDate}&content=${noteInput}&name=${patientName}&email=${patientEmail}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(response => {
            alert(`Your request has been saved for ${noteDate}`);
            document.getElementById("note-modal").style.display = "none"; // Close the modal
        }).catch(error => console.error('Error:', error));
    }else{
        alert("Please select a date before submitting!");
        return;
    }


}
