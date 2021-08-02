package persistence;

import org.json.JSONObject;

/*
  Writable interface for CryptoTrader
  Interface taken from JsonSerializationDemo repository
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}