package persistence;

import org.json.JSONObject;

/*
  Writable interface for CryptoTrader
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}