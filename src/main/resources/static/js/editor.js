/*!
  Copyright (c) 2022 Daniel Gyoerffy.
*/

const textArea = document.getElementById("raw-note-area");
const notePreview = document.getElementById("note-preview");
const doubleSpace = "  ";

function initPreviewButtons() {
    const textEditorButton = document.getElementById("text-preview-btn");
    const textAreaCol = document.getElementById("text-area-col");

    const previewButton = document.getElementById("view-preview-btn");
    const previewCol = document.getElementById("preview-col");

    const initToggling = function(button, otherButton, element) {
        const toggles = {
            "none" : "block",
            "block" : "none",
            "" : "none",
        };
        button.addEventListener("click", () => {
            if (!(button.className.includes("active") || otherButton.className.includes("active"))) {
                button.className += " active";
                return;
            } 
            element.style.display = toggles[element.style.display];
        });

        window.addEventListener("resize", () => {
            if (window.innerWidth <= 768) {
                if (button.className.includes("active") && otherButton.className.includes("active")) {
                    button.className.replace("active", "");
                    element.style.display = "none";
                }
            }
        })
    };

    initToggling(textEditorButton, previewButton, textAreaCol);
    initToggling(previewButton, textEditorButton, previewCol);

    
}

/**
 * Updates the content of the note-preview area according to the content of the text-area
 */
function updateNotePreview() {
    notePreview.innerHTML = `<md-block>${textArea.value}</md-block>`;
}

/**
 * Wraps the currently selected text in the text-area with the given strings.
 * 
 * @param {*} left the string to append on the left
 * @param {*} right the string to append on the right;
 *                  it's the same as the @param left if not specified. 
 */
function wrapSelectedTextWithAbbreviation(left, right = left) {
    const s = textArea.selectionStart;
    const e = textArea.selectionEnd;
    
    const selected = textArea.value.substring(s, e);
    
    let wrapStringChunck = (it) => {
        it = it.trim();
        // avoid redundant abbrevations
        if (it.startsWith(left) && it.endsWith(right)) 
            return it;
        return `${left}${it}${right}`;
    };

    // only wrap elements that are separated by markdown line-breaks in a larger chunk 
    let replacement = selected.split(/\s{2}\n/)
        .map(wrapStringChunck)
        .join(`${doubleSpace}\n`);

    // avoid losing the original line-break appended on the end of the selected text
    if (selected.endsWith(doubleSpace)) {
        replacement += doubleSpace;
    }

    textArea.setRangeText(replacement, s, e);
    // only select the original selection, excluding the newly inserted wrappings
    textArea.setSelectionRange(s + left.length, e + left.length);
    textArea.focus();
    updateNotePreview();
}

/**
 * Configures the text-area to update the preview when it's content changes
 */
function initPreviewUpdate() {
    textArea.addEventListener("onchange", updateNotePreview);
    textArea.addEventListener("keypress", updateNotePreview);
    textArea.addEventListener("keydown", updateNotePreview);
    textArea.addEventListener("keyup", updateNotePreview);
}

/**
 * Configures the text-editing related buttons
 */
function initEditorButtons() {
    let btns = {
        "bold-btn" : "**",
        "italic-btn" : "_",
        "underline-btn" : ["<u>", "</u>"],
        "strikethrough-btn" : "~",
        "list-btn" : ["* ", ""],
        "link-btn" : ["[", "]()"]
    };
    
    for (const id in btns) {
        let left;
        let right;

        left = right = btns[id];
        
        if (Array.isArray(btns[id])) {
            left = btns[id][0];
            right = btns[id][1];
        }

        document.getElementById(id).addEventListener("click", () => {
            wrapSelectedTextWithAbbreviation(left, right);
        });
    }
}

/**
 * Configures everything needed on a newly loaded page
 */
function init() {
    initPreviewButtons();
    initPreviewUpdate();
    initEditorButtons();
    updateNotePreview();    
}

init();