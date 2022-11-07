const textArea = document.getElementById("raw-note-area");
const notePreview = document.getElementById("note-preview");

document.getElementById("bold-btn").addEventListener("click", () => {
    let value = textArea.value;
    let length = textArea.value.length;
    let startIndex = textArea.selectionStart;
    let endIndex = textArea.selectionEnd;
    let selected = textArea.value.substring(startIndex, endIndex);
    textArea.value = value.substring(0, startIndex) + `*${selected}*` + value.substring(endIndex);
});
document.getElementById("italic-btn").addEventListener("click", () => {

});
document.getElementById("underline-btn").addEventListener("click", () => {

});
document.getElementById("strikethrough-btn").addEventListener("click", () => {

});

function init() {
    textArea.addEventListener("onchange", updateNotePreview);
    textArea.addEventListener("keypress", updateNotePreview);
    textArea.addEventListener("keydown", updateNotePreview);
    textArea.addEventListener("keyup", updateNotePreview);
}

function updateNotePreview() {
    notePreview.innerHTML = `<md-block>${textArea.value}</md-block>`;
}

init();
updateNotePreview();