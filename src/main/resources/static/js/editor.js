/*!
  Copyright (c) 2022 Daniel Gyoerffy.
*/

const MEDIUM_SIZE = 768;
const DOUBLE_SPACE = "  ";

const textArea = document.getElementById("raw-note-area");
const notePreview = document.getElementById("note-preview");

const editToolbar = document.getElementById("edit-toolbar");

/* Preview toolbar related */

const textEditorButton = document.getElementById("text-preview-btn");
const textAreaCol = document.getElementById("text-area-col");

const previewButton = document.getElementById("view-preview-btn");
const previewCol = document.getElementById("preview-col");

const previewBothButton = document.getElementById("both-preview-btn");

/**
 * Initializes the behaviour of the preview handling buttons.
 */
function initPreviewButtons() {
    const initToggling = function (button, otherButtons, elements, otherElements) {
        button.addEventListener("click", () => {
            if (!areTherePressed(otherButtons)) {
                // don't let this button to be deselected if it's the selected option
                button.className += " active";
                return;
            }
            otherButtons.forEach(it => { it.className = it.className.replace("active", ""); });
            otherElements.forEach(it => { it.style.display = "none"; });
            elements.forEach(it => { it.style.display = "block"; });
        });
    };

    initToggling(textEditorButton, [previewButton, previewBothButton], [textAreaCol, editToolbar], [previewCol]);
    initToggling(previewButton, [textEditorButton, previewBothButton], [previewCol], [textAreaCol, editToolbar]);
    initToggling(previewBothButton, [textEditorButton, previewButton], [textAreaCol, previewCol, editToolbar], []);

    window.addEventListener("resize", () => {
        // Don't allow both panels to be visible if the window's width is below medium
        if (window.innerWidth <= MEDIUM_SIZE && isPressed(previewBothButton))
            textEditorButton.dispatchEvent(new Event("click"));
    });
}

/**
 * Evaluates whether the given toggle-button is selected or not.
 */
function isPressed(toggleButton) {
    return toggleButton.className.includes("active");
}

/**
 * Evaluates whether the collection of toggle-buttons contains at 
 * least one selected element. 
 */
function areTherePressed(toggleButtons) {
    return toggleButtons.filter(it => isPressed(it)).length > 0;
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
        .join(`${DOUBLE_SPACE}\n`);

    // avoid losing the original line-break appended on the end of the selected text
    if (selected.endsWith(DOUBLE_SPACE)) {
        replacement += DOUBLE_SPACE;
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
        "bold-btn": "**",
        "italic-btn": "_",
        "strikethrough-btn": "~",
        "list-btn": ["* ", ""],
        "link-btn": ["[", "]()"]
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