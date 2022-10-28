document.onload = function() {

    alert("ON LOAD!");
    const textArea = document.getElementById("raw-note-area");
    const notePreview = document.getElementById("note-preview");

    textArea.onchange = function() {
        console.log("Note changed");
        //notePreview.innerHTML = `<md-block>${textArea.value}</md-block>`
    }
}