package cl.prueba.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Lambda invocada por API Gateway. Publica el cuerpo recibido en Amazon SQS.
 */
public class ProductorHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final SqsClient sqsClient = SqsClient.create();
    private final String queueUrl = System.getenv("QUEUE_URL");

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
        String mensaje = obtenerMensaje(event);

        String messageId = sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(mensaje)
                .build()).messageId();

        context.getLogger().log("Mensaje publicado en SQS. messageId=" + messageId + "\n");

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("statusCode", 202);
        respuesta.put("headers", Map.of("Content-Type", "application/json"));
        respuesta.put("body", "{\"estado\":\"EN_COLA\",\"messageId\":\"" + messageId + "\"}");
        return respuesta;
    }

    private String obtenerMensaje(Map<String, Object> event) {
        Object body = event == null ? null : event.get("body");
        if (body == null || body.toString().isBlank()) {
            return "Hola mundo desde Java, API Gateway y AWS SQS";
        }
        return body.toString();
    }
}
