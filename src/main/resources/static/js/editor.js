const textArea = document.getElementById("raw-note-area");
const notePreview = document.getElementById("note-preview");

function init() {
    textArea.addEventListener("keypress", updateNotePreview);
    textArea.addEventListener("keydown", updateNotePreview);
    textArea.addEventListener("keyup", updateNotePreview);
}

function updateNotePreview() {
    notePreview.innerHTML = `<md-block>${textArea.value}</md-block>`;
}

init();
updateNotePreview();