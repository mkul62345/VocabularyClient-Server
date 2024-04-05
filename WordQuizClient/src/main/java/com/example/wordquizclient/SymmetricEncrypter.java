package com.example.wordquizclient;

public class SymmetricEncrypter {
    private char morph(char argCharacter, String key){
        int originalAlphabetPosition;
        int newAlphabetPosition;
        char newCharacter;

        if (Character.isUpperCase(argCharacter)) {
            originalAlphabetPosition = argCharacter - 'A';
            newAlphabetPosition = (originalAlphabetPosition + Integer.parseInt(key) % 26) % 26;
            newCharacter = (char) ('A' + newAlphabetPosition);
        }
        else if (Character.isLowerCase(argCharacter)){
            originalAlphabetPosition = argCharacter - 'a';
            newAlphabetPosition = (originalAlphabetPosition + Integer.parseInt(key) % 26) % 26;
            newCharacter = (char) ('a' + newAlphabetPosition);
        } else {
            newCharacter = argCharacter;
        }

        return newCharacter;
    }


    public String transform(String argInput, String key) {
        StringBuilder result = new StringBuilder();

        //Process Characters
        for (char ch : argInput.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                result.append(morph(ch, key));
            } else {
                result.append(ch);
            }

        }

        return result.toString();
    }


}
