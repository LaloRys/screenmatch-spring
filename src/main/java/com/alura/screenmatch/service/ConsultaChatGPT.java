package com.alura.screenmatch.service;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ConsultaChatGPT {

    private String apiKey;

    public static String obtenerTraduccion(String texto) {
        OpenAiService service = new OpenAiService("${apiKey}");

        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var respuesta = service.createCompletion(requisicion);
        System.out.println("respuesta = " + respuesta);
        return respuesta.getChoices().get(0).getText();
    }
}
