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

    // Get today's date
    const today = new Date();
    const todayYear = today.getFullYear();
    const todayMonth = today.getMonth();
    const todayDate = today.getDate();

    // Fill in empty cells before the first day of the month
    for (let i = 0; i < firstDay; i++) {
        row.appendChild(document.createElement('td'));
    }

    // Add the days of the month
    for (let day = 1; day <= daysInMonth; day++) {
        const cell = document.createElement('td');
        cell.textContent = day;
        cell.classList.add('calendar-day');

        // Disable past dates
        if (year < todayYear || (year === todayYear && month < todayMonth) || (year === todayYear && month === todayMonth && day < todayDate)) {
            cell.style.color = "#ccc"; // Grey out past dates
            cell.style.pointerEvents = "none"; // Disable clicking
        } else {
            // Add click event to open modal for valid dates
            cell.addEventListener('click', () => openNoteModal(year, month, day));
        }

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
    const noteDate = document.getElementById("preferred-date");
    const time = document.getElementById("time-input");
    const patientName = document.getElementById("name");
    const patientEmail = document.getElementById("email");

    const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    noteDate.textContent = formattedDate;

    // Get the current date and time
    const now = new Date();
    const currentYear = now.getFullYear();
    const currentMonth = now.getMonth();
    const currentDate = now.getDate();
    const currentHour = now.getHours();
    const currentMinute = now.getMinutes();

    console.log(`Current Date & Time: ${now}`);
    console.log(`Selected Date: ${formattedDate}`);

    // Check if the selected date is today
    if (year === currentYear && month === currentMonth && day === currentDate) {
        // Calculate the min time to be current time
        const minTime = `${String(currentHour).padStart(2, '0')}:${String(currentMinute).padStart(2, '0')}`;

        console.log(`Setting min time to: ${minTime}`);

        // Ensure that the time input doesn't allow times before current time
        time.setAttribute("min", minTime);  // Set the min time as current time
    } else {
        // If it's a future date, allow any time
        time.removeAttribute("min");
    }

    // Pre-fill with existing note if available
    time.value = notes[formattedDate] || "";
    patientName.value = ""; // Clear the name field
    patientEmail.value = ""; // Clear the email field

    modal.style.display = "block";
}

function saveNote() {
    const noteDate = document.getElementById("preferred-date").textContent;
    const time = document.getElementById("time-input").value;
    const patientName = document.getElementById("name").value
    const patientLastName = document.getElementById("last-name").value
    const patientEmail = document.getElementById("email").value

    // Ensure all fields are filled out
    if (!time || !patientName || !patientLastName || !patientEmail) {
        alert("Please fill out all fields before adding the note.");
        return;
    }

    if(noteDate){
        // Send a POST request to save or update the note
        fetch(`/calendar/add-note?date=${noteDate}&time=${time}&name=${patientName}&lastName=${patientLastName}&email=${patientEmail}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(response => {
            alert(`the appointment was set for ${noteDate}`);
            document.getElementById("note-modal").style.display = "none"; // Close the modal
        }).catch(error => console.error('Error:', error));
    }else{
        alert("Please select a date before submitting!");
        return;
    }
    const preferredDateSpan = document.getElementById("preferred-date");
    preferredDateSpan.textContent = ""; // Clear the content
}

function notifyMaxLength(textarea) {
    const maxLength = textarea.maxLength; // Get the maxLength directly from the element
    const currentLength = textarea.value.length;

    if (currentLength === maxLength) {
        alert(`You have reached the maximum length of ${maxLength} characters.`);
    }
}

function closeNoteModal() {
    const modal = document.getElementById("note-modal");
    modal.style.display = "none"; // Hide the modal
}
