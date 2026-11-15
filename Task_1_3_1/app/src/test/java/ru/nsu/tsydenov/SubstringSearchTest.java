package ru.nsu.tsydenov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SubstringSearchTest {
    @TempDir
    Path tempDir;

    private Path file;

    @BeforeEach
    void setUp() {
        file = tempDir.resolve("input.txt");
    }

    private void writeText(String text) throws IOException {
        Files.writeString(file, text, StandardCharsets.UTF_8);
    }

    @Test
    void testFind_SingleOccurrence() throws IOException {
        writeText("hello world");

        assertEquals(List.of(6L),
                SubstringSearch.find(file.toString(), "world"));
    }

    @Test
    void testFind_MultipleOccurrences() throws IOException {
        writeText("абракадабра");

        assertEquals(List.of(1L, 8L),
                SubstringSearch.find(file.toString(), "бра"));
    }

    @Test
    void testFind_OverlappingOccurrences() throws IOException {
        writeText("aaaaa");

        assertEquals(List.of(0L, 1L, 2L, 3L),
                SubstringSearch.find(file.toString(), "aa"));
    }

    @Test
    void testFind_NoOccurrences() throws IOException {
        writeText("hello world");

        assertTrue(SubstringSearch.find(file.toString(), "xyz").isEmpty());
    }

    @Test
    void testFind_EmptyFile() throws IOException {
        writeText("");

        assertTrue(SubstringSearch.find(file.toString(), "test").isEmpty());
    }

    @Test
    void testFind_EmptyPattern() throws IOException {
        writeText("hello");

        assertThrows(IllegalArgumentException.class, () -> {
            SubstringSearch.find(file.toString(), "");
        });
    }

    @Test
    void testFind_NullPattern() throws IOException {
        writeText("hello");

        assertThrows(NullPointerException.class, () -> {
            SubstringSearch.find(file.toString(), null);
        });
    }

    @Test
    void testFind_PatternLongerThanText() throws IOException {
        writeText("abc");

        assertTrue(SubstringSearch.find(file.toString(), "abcdef").isEmpty());
    }

    @Test
    void testFind_WholeFile() throws IOException {
        writeText("привет");

        assertEquals(List.of(0L),
                SubstringSearch.find(file.toString(), "привет"));
    }

    @Test
    void testFind_AtStartAndEnd() throws IOException {
        writeText("abc123abc");

        assertEquals(List.of(0L, 6L),
                SubstringSearch.find(file.toString(), "abc"));
    }

    @Test
    void testFind_PrefixFallback() throws IOException {
        writeText("abababac");

        assertEquals(List.of(2L),
                SubstringSearch.find(file.toString(), "ababac"));
    }

    @Test
    void testFind_RepeatedPrefix() throws IOException {
        writeText("aaabaaab");

        assertEquals(List.of(0L, 4L),
                SubstringSearch.find(file.toString(), "aaab"));
    }

    @Test
    void testFind_DifferentAlphabets() throws IOException {
        writeText("hello мир 世界 мир");

        assertEquals(List.of(6L, 13L),
                SubstringSearch.find(file.toString(), "мир"));
    }

    @Test
    void testFind_Emoji() throws IOException {
        writeText("a😀b😀c");

        assertEquals(List.of(1L, 3L),
                SubstringSearch.find(file.toString(), "😀"));
    }

    @Test
    void testFind_UnicodeCodePointIndex() throws IOException {
        writeText("😀😀abc");

        assertEquals(List.of(2L),
                SubstringSearch.find(file.toString(), "abc"));
    }

    @Test
    void testFind_MixedUnicodePattern() throws IOException {
        writeText("a😀б😀б");

        assertEquals(List.of(1L, 3L),
                SubstringSearch.find(file.toString(), "😀б"));
    }

    @Test
    void testFind_Newlines() throws IOException {
        writeText("abc\nabc\nabc");

        assertEquals(List.of(0L, 4L, 8L),
                SubstringSearch.find(file.toString(), "abc"));
    }

    @Test
    void testFind_PatternWithNewline() throws IOException {
        writeText("abc\ndef\nabc\ndef");

        assertEquals(List.of(0L, 8L),
                SubstringSearch.find(file.toString(), "abc\ndef"));
    }

    @Test
    void testFind_MissingFile() {
        assertThrows(IOException.class, () -> {
            SubstringSearch.find(file.toString(), "test");
        });
    }

    @Test
    void testFind_NegativeHashNotRelevant() throws IOException {
        writeText("тест тест");

        assertEquals(List.of(0L, 5L),
                SubstringSearch.find(file.toString(), "тест"));
    }

    @Test
    void testFind_LargeFile() throws IOException {
        Path largeFile = tempDir.resolve("large.txt");

        String part = "абракадабра hello world ";
        int repetitions = 100_000;
        long expectedIndex = (long) part.codePointCount(0, part.length())
                * repetitions;

        try (BufferedWriter writer = Files.newBufferedWriter(
                largeFile, StandardCharsets.UTF_8)) {

            for (int i = 0; i < repetitions; i++) {
                writer.write(part);
            }

            writer.write("уникальная_строка_для_поиска");
        }

        assertEquals(List.of(expectedIndex),
                SubstringSearch.find(
                        largeFile.toString(),
                        "уникальная_строка_для_поиска"));
    }

    @Test
    void testFind_MatchAcrossBufferBoundary() throws IOException {
        writeText("a".repeat(8191) + "xyz");

        assertEquals(List.of(8191L),
                SubstringSearch.find(file.toString(), "xyz"));
    }

    @Test
    void testFind_SingleCharacterPattern() throws IOException {
        writeText("banana");

        assertEquals(List.of(1L, 3L, 5L),
                SubstringSearch.find(file.toString(), "a"));
    }

    @Test
    void testFind_NoMatchAfterPartialMatch() throws IOException {
        writeText("ababababx");

        assertTrue(SubstringSearch.find(file.toString(), "ababac").isEmpty());
    }

    @Test
    void testFind_SupplementaryCharacterAtEnd() throws IOException {
        writeText("hello😀");

        assertEquals(List.of(5L),
                SubstringSearch.find(file.toString(), "😀"));
    }
}
