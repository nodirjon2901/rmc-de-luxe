package uz.result.rmcdeluxe.util;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class SlugUtil {

    //CYRILLIC to latin
    private static final Map<Character, String> C_TO_L = new HashMap<>();

    static
    {
        C_TO_L.put('А', "a");
        C_TO_L.put('Б', "b");
        C_TO_L.put('В', "v");
        C_TO_L.put('Г', "g");
        C_TO_L.put('Д', "d");
        C_TO_L.put('Е', "e");
        C_TO_L.put('Ё', "yo");
        C_TO_L.put('Ж', "zh");
        C_TO_L.put('З', "z");
        C_TO_L.put('И', "i");
        C_TO_L.put('Й', "j");
        C_TO_L.put('К', "k");
        C_TO_L.put('Л', "l");
        C_TO_L.put('М', "m");
        C_TO_L.put('Н', "n");
        C_TO_L.put('О', "o");
        C_TO_L.put('П', "p");
        C_TO_L.put('Р', "r");
        C_TO_L.put('С', "s");
        C_TO_L.put('Т', "t");
        C_TO_L.put('У', "u");
        C_TO_L.put('Ф', "f");
        C_TO_L.put('Х', "h");
        C_TO_L.put('Ц', "c");
        C_TO_L.put('Ч', "ch");
        C_TO_L.put('Ш', "sh");
        C_TO_L.put('Щ', "sh");
        C_TO_L.put('Ы', "y");
        C_TO_L.put('Э', "e");
        C_TO_L.put('Ю', "yu");
        C_TO_L.put('Я', "ya");
        C_TO_L.put('Ь', "");
        C_TO_L.put('Ъ', "");
        // Lowercase letters are mapped similarly.
        C_TO_L.put('а', "a");
        C_TO_L.put('б', "b");
        C_TO_L.put('в', "v");
        C_TO_L.put('г', "g");
        C_TO_L.put('д', "d");
        C_TO_L.put('е', "e");
        C_TO_L.put('ё', "yo");
        C_TO_L.put('ж', "zh");
        C_TO_L.put('з', "z");
        C_TO_L.put('и', "i");
        C_TO_L.put('й', "j");
        C_TO_L.put('к', "k");
        C_TO_L.put('л', "l");
        C_TO_L.put('м', "m");
        C_TO_L.put('н', "n");
        C_TO_L.put('о', "o");
        C_TO_L.put('п', "p");
        C_TO_L.put('р', "r");
        C_TO_L.put('с', "s");
        C_TO_L.put('т', "t");
        C_TO_L.put('у', "u");
        C_TO_L.put('ф', "f");
        C_TO_L.put('х', "h");
        C_TO_L.put('ц', "c");
        C_TO_L.put('ч', "ch");
        C_TO_L.put('ш', "sh");
        C_TO_L.put('щ', "sh");
        C_TO_L.put('ы', "y");
        C_TO_L.put('э', "e");
        C_TO_L.put('ю', "yu");
        C_TO_L.put('я', "ya");
        C_TO_L.put('ь', "");
        C_TO_L.put('ъ', "");
    }

    public static String makeSlug(String input)
    {
        StringBuilder transliterated = new StringBuilder();

        for (char ch : input.toCharArray())
        {
            if (C_TO_L.containsKey(ch))
            {
                transliterated.append(C_TO_L.get(ch));
            } else
            {
                transliterated.append(ch); // Keep the character as-is if not Cyrillic
            }
        }

        // Step 2: Normalize (remove accents if any), replace special characters and spaces with hyphens
        String normalized = Normalizer.normalize(transliterated.toString(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", ""); // Remove non-ASCII characters

        // Step 3: Replace spaces and special characters with hyphens and convert to lowercase
        String slug = normalized.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")  // Replace non-alphanumeric characters with hyphen
                .replaceAll("-{2,}", "-")       // Collapse multiple hyphens
                .replaceAll("^-|-$", "");       // Remove leading or trailing hyphen

        return slug;
    }

}
