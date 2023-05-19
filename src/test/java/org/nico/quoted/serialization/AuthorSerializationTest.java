package org.nico.quoted.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestUtil;
import org.nico.quoted.domain.Author;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorSerializationTest {
    private Author author, author2;

    @BeforeEach
    void setUp() {
        author = new Author("John", "Doe");
        author.setId(1L);
        author2 = new Author("Jane", "Doe");
        author2.setId(2L);
    }

    @Test
    void testSerializeAuthor() {
        assertAuthorJsonEqual(author, TestUtil.resourceToString("/serialization/JohnDoe.json"));
        assertAuthorJsonEqual(author2, TestUtil.resourceToString("/serialization/JaneDoe.json"));

        // Test for special characters
        Author author3 = new Author("Heinß", "Fränk");
        String json = new Gson().toJson(author3);
        assertEquals("{\"firstName\":\"Heinß\",\"lastName\":\"Fränk\"}", json);
    }

    @Test
    @DisplayName("Test deserialization of Author")
    void testDeserializeAuthor() {
        String json = TestUtil.resourceToString("/serialization/JohnDoeDeserialize.json");
        Gson gson = new GsonBuilder().registerTypeAdapter(Author.class, new AuthorDeserializer()).create();
        Author deserializedAuthor = gson.fromJson(json, Author.class);
        assertEquals(this.author, deserializedAuthor);
        assertEquals(this.author.getId(), deserializedAuthor.getId());
    }


    private void assertAuthorJsonEqual(Author author, String expectedJson) {
            String json = new Gson().toJson(author);
            assertEquals(expectedJson, json);
    }
}
