package domain.mensajeria;
import domain.Config;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// ============================================================ //
// SINGLETON //
// ============================================================ //

// ============================================================ //
// WHATSAPP BUSINESS //
// ============================================================ //

/*Param
 * token: Token que nos da Facebook
 * telefono: Nuestro teléfono
 * idNumero: Identificador de número de teléfono (lo proporciona facebook)
 */

public class WhatsAppSender {
    private static WhatsAppSender instance;
    private final String token;
    private final String idNumero;


    private WhatsAppSender() {
        // TOKEN QUE NOS DA FACEBOOK
        this.token = Config.getInstance().getProperty("TOKEN_WHATSAPP");
        // IDENTIFICADOR DE NÚMERO DE TELÉFONO
        this.idNumero = Config.getInstance().getProperty("ID_TELEFONO");
    }

    public static synchronized WhatsAppSender getInstance() {
        if (instance == null) {
            instance = new WhatsAppSender();
        }
        return instance;
    }

    public void sendMessage(String telefono, String mensaje) {
        try {

            // COLOCAMOS LA URL PARA ENVIAR EL MENSAJE
            URL url = new URL("https://graph.facebook.com/v15.0/" + idNumero + "/messages");
            // INICIALIZAMOS EL CONTENEDOR DEL ENVIO
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            // EL TIPO DE ENVIO DE DATOS VA A SER VIA POST
            httpConn.setRequestMethod("POST");
            // CODIGO DE AUTORIZACION DE JAVA
            httpConn.setRequestProperty("Authorization", "Bearer " + token);
            // DEFINIMOS QUE LOS DATOS SERAN TRATADOS COMO JSON
            httpConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // PREPARAMOS Y ENVIAMOS EL JSON
            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("{ "
                    + "\"messaging_product\": \"whatsapp\", "
                    + "\"to\": \"" + telefono + "\", "
                    + "\"type\": \"template\", "
                    + "\"template\": "
                    + "  { \"name\": \"hello_world\", "
                    + "    \"language\": { \"code\": \"en_US\" } "
                    + "  } "
                    + "}");
            // LIMPIAMOS LOS DATOS
            writer.flush();
            // CERRAMOS LOS DATOS
            writer.close();
            // CERRAMOS LA CONEXION
            httpConn.getOutputStream().close();
            // RECIBIMOS EL RESULTADO DEL ENVIO
            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            // OBTENEMOS LOS RESULTADOS
            String respuesta = s.hasNext() ? s.next() : "";
            System.out.println(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

