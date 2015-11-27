
package com.jfouffa.vmallet.idea.capitalize;


import com.intellij.openapi.editor.actionSystem.EditorAction;


public final class CapitalizeAction extends EditorAction {

    public CapitalizeAction() {
        super(new WordTransformActionHandler(CapitalizeAction::makeStr));
    }

    private static void makeStr(StringBuffer buffer) {
        if(buffer.indexOf("_")>0){
            deleteGang(0 ,buffer);
        }else{
            appentGang(0 ,buffer);
        }
    }

    private static void deleteGang(int start, StringBuffer buffer) {
        int offset = buffer.indexOf("_", start);
        if(offset>0) {
            buffer.deleteCharAt(offset);
            buffer.setCharAt(offset, Character.toUpperCase(buffer.charAt(offset)));
            deleteGang(offset - 1, buffer);
        }
    }

    private static void appentGang(int start, StringBuffer buffer) {
        for (int i = start; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (Character.isUpperCase(c)) {
                buffer.setCharAt(i, Character.toLowerCase(c));
                buffer.insert(i,"_");
                appentGang(i+2,buffer);
                break;
            }
        }
    }


//    private static void makeStr(StringBuffer buffer) {
//        if (buffer.length() == 0) {
//            return;
//        }
//
//        boolean mustUpperNext = true;
//
//        for (int i = 0; i < buffer.length(); i++) {
//            char c = buffer.charAt(i);
//            if (Character.isLetterOrDigit(c)) {
//                if (mustUpperNext) {
//                    buffer.setCharAt(i, Character.toUpperCase(c));
//                    mustUpperNext = false;
//                } else {
//                    buffer.setCharAt(i, Character.toLowerCase(c));
//                }
//            } else {
//                mustUpperNext = true;
//            }
//        }
//    }

}
