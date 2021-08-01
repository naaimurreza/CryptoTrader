package persistence;

import model.Profile;
import org.json.JSONObject;
import java.io.*;

/*
  Represents a writer that writes JSON representation of workroom to file
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String file;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String file) {
        this.file = file;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer; throws FileNotFoundException if destination file cannot
    //          be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of workroom to file
    public void write(Profile profile) {
        JSONObject json = profile.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
