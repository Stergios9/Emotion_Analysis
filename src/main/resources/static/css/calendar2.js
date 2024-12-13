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

// Generate the calendar
function generateCalendar(year, month) {
    const calendarDays = document.getElementById('calendar-days');
    calendarDays.innerHTML = ''; // Clear previous calendar content

    const monthYear = document.getElementById('month-year');
    monthYear.textContent = `${monthNames[month]} ${year}`;

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

        // Add click event to open modal and fetch data for that date
        cell.addEventListener('click', () => openNoteModal(year, month, day));

        row.appendChild(cell);

        // Start a new row after every Saturday or on the last day
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

// Open the modal and fetch notes for a specific date
function openNoteModal(year, month, day) {
    const modal = document.getElementById("note-modal");
    const noteDate = document.getElementById("note-date");
    const noteContent = document.getElementById("note-content");

    // Format the date as YYYY-MM-DD
    const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    noteDate.textContent = formattedDate;

    // Fetch records from the database for the selected date
    fetch(`/calendar/get-records?date=${formattedDate}`)
        .then(response => response.json())  // Expect a JSON response
        .then(data => {
            if (data.length > 0) {
                let recordsHtml = '';
                data.forEach(record => {
                    // Assuming your record has properties: name, noteContent, etc.
                    recordsHtml += `<p><strong>${record.name}:</strong> ${record.noteContent}</p>`;
                });
                noteContent.innerHTML = recordsHtml;  // Display the records in the modal
            } else {
                noteContent.innerHTML = "<p>No records found for this day.</p>";  // If no records found
            }
            modal.style.display = "block"; // Show the modal
        })
        .catch(error => {
            console.error('Error fetching records:', error);
            noteContent.innerHTML = "<p>Error loading records.</p>";  // Display error message
            modal.style.display = "block"; // Show the modal even if error occurs
        });
}

