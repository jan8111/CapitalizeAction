package com.jfouffa.vmallet.idea.capitalize;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;

public class WordTransformActionHandler extends EditorWriteActionHandler {
    private CaseTransformer m_transformer;

    public WordTransformActionHandler(CaseTransformer transformer) {
        this.m_transformer = transformer;
    }

    public void executeWriteAction(Editor editor, DataContext dataContext) {
        if (editor == null) {
            return;
        }

        SelectionModel selectionModel = editor.getSelectionModel();
        if ((selectionModel != null) && (selectionModel.hasSelection()))
            handleSelection(editor, selectionModel);
        else
            handleNoSelection(editor);
    }

    private void handleSelection(Editor editor, SelectionModel selectionModel) {
        Document doc = editor.getDocument();

        if (doc == null) {
            return;
        }

        int start = selectionModel.getSelectionStart();
        int end = selectionModel.getSelectionEnd();
        char[] allChars = doc.getChars();

        StringBuffer selection = new StringBuffer();

        selection.append(allChars, start, end - start);

        this.m_transformer.transform(selection);

        doc.replaceString(start, end, selection.toString());
        selectionModel.setSelection(start, start + selection.length());
    }

    private void handleNoSelection(Editor editor) {
        CaretModel caretModel = editor.getCaretModel();
        Document doc = editor.getDocument();
        if ((caretModel == null) || (doc == null) || (doc.getTextLength() == 0)) {
            return;
        }

        char[] allChars = doc.getChars();
        int maxOffset = allChars.length;

        int startOffset = caretModel.getOffset();

        while ((startOffset < maxOffset) && (!Character.isLetterOrDigit(allChars[startOffset]))) {
            startOffset++;
        }

        StringBuffer word = new StringBuffer();
        int i = startOffset;
        while ((i < maxOffset) && (Character.isLetterOrDigit(allChars[i]))) {
            word.append(allChars[i]);

            i++;
        }

        if (word.length() > 0) {
            this.m_transformer.transform(word);
            int newOffset = startOffset + word.length();
            doc.replaceString(startOffset, newOffset, word.toString());

            caretModel.moveToOffset(newOffset);
        }
    }
}