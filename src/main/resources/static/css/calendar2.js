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
function openNoteModal(year, month, day) {
    const modal = document.getElementById("note-modal");
    const noteDate = document.getElementById("note-date");
    const preferredDate = document.getElementById("preferred-date");
    const noteContent = document.getElementById("note-content");

    // Format the date as YYYY-MM-DD
    const formattedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;

    // Update both the modal header and the "Create Appointment for" section with the selected date
    noteDate.textContent = formattedDate;
    preferredDate.textContent = formattedDate;

    // Fetch records from the backend for the selected date
    fetch(`/calendar/get-records?date=${formattedDate}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); // Expect JSON response
        })
        .then(data => {
            if (data && data.length > 0) {
                let recordsHtml = '<div class="record-container">';
                data.forEach(record => {
                    const regex = /Name:\s*(.*?)\s*-\s*Time:\s*(.*?)\s*-\s*Email:\s*(.*)/;
                    const match = record.match(regex);
                    if (match) {
                        const name = match[1];
                        const time = match[2];
                        const email = match[3];

                        // Create the HTML with a delete button
                        recordsHtml += `
                            <div class="record-row">
                                <div class="record-item"><strong>Name:</strong> ${name}</div>
                                <div class="record-item"><strong>Time:</strong> ${time}</div>
                                <div class="record-item"><strong>Email:</strong> ${email}</div>
                                <div class="record-item">
                                    <button onclick="deleteRecord('${name}', '${time}', '${email}')">Delete</button>
                                </div>
                            </div>
                        `;
                    }
                });
                recordsHtml += '</div>';
                noteContent.innerHTML = recordsHtml; // Display the notes
            } else {
                noteContent.innerHTML = "<p>No records found for this day.</p>"; // If no records found
            }
            modal.style.display = "block"; // Show the modal
        })
        .catch(error => {
            console.error('Error fetching records:', error);
            noteContent.innerHTML = "<p>Error loading records.</p>"; // Display error
            modal.style.display = "block"; // Show the modal even if error occurs
        });
}

// Function to delete a record
function deleteRecord(name, time, email) {
    const confirmation = confirm(`Are you sure you want to delete the record:\nName: ${name}, Time: ${time}, Email: ${email}?`);
    if (!confirmation) return;

    fetch(`/calendar/delete-record?name=${encodeURIComponent(name)}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to delete record! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(message => {
            alert(message); // Show success or failure message
            openNoteModal(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()); // Refresh the modal
        })
        .catch(error => {
            console.error('Error deleting record:', error);
            alert('Failed to delete the record. Please try again.');
        });
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
            alert(`Note saved for ${noteDate}`);
            document.getElementById("note-modal").style.display = "none"; // Close the modal
        }).catch(error => console.error('Error:', error));
    }else{
        alert("Please select a date before submitting!");
        return;
    }


}


function closeNoteModal() {
    const modal = document.getElementById("note-modal");
    modal.style.display = "none"; // Hide the modal
}


