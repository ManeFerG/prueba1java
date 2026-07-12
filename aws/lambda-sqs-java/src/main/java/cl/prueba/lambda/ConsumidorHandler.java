package cl.prueba.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

/**
 * Lambda consumidora. Se ejecuta automáticamente cuando llega un mensaje a SQS.
 */
public class ConsumidorHandler implements RequestHandler<SQSEvent, String> {

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        int procesados = 0;

        for (SQSEvent.SQSMessage mensaje : event.getRecords()) {
            context.getLogger().log(
                    "Hola mundo asíncrono. Mensaje recibido: " + mensaje.getBody() + "\n"
            );
            procesados++;
        }

        return "Mensajes procesados: " + procesados;
    }
}
