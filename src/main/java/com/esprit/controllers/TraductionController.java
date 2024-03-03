package com.esprit.controllers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class TraductionController {

    @FXML
    private TextArea maTextArea;

    @FXML
    private ComboBox<String> languageComboBox;

    public void initialize() {
        // Ajoutez ces lignes pour initialiser le ComboBox avec les langues disponibles
        languageComboBox.getItems().addAll("fr","en", "ar");
        languageComboBox.getSelectionModel().selectFirst();
    }


    public void onTranslateButtonClicked(ActionEvent event) {
        String originalText = maTextArea.getText();
        String targetLanguage = languageComboBox.getValue();
        try {
            String translatedText = translate(originalText,targetLanguage);
            maTextArea.setText(translatedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public String translate(String originalText, String targetLanguage) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("q", originalText)
                .add("target", targetLanguage)
                .build();

        Request request = new Request.Builder()
                .url("https://google-translate1.p.rapidapi.com/language/translate/v2")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", "98a074271amsh7561f5a201962ddp12b9bdjsn33c8abacbd67")
                .addHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .build();



        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);
            String translatedText = json.getJSONObject("data")
                    .getJSONArray("translations")
                    .getJSONObject(0)
                    .getString("translatedText");
            System.out.println(translatedText);
            return translatedText;
        } else {
            System.out.println("Request failed");
        }
        return null;
    }













}
