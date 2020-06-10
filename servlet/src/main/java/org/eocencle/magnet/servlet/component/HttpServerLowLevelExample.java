package org.eocencle.magnet.servlet.component;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.util.ByteString;

import java.util.concurrent.CompletionStage;

/**
 * 低级API的Controller
 * @author: huan
 * @Date: 2020-06-06
 * @Description:
 */
public class HttpServerLowLevelExample {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create();

        try {
            final Materializer materializer = ActorMaterializer.create(system);
            CompletionStage<ServerBinding> serverBindingFuture =
                    Http.get(system).bindAndHandleSync(
                            request -> {
                                if (request.getUri().path().equals("/"))
                                    return HttpResponse.create().withEntity(ContentTypes.TEXT_HTML_UTF8,
                                            ByteString.fromString("<html><body>Hello world!</body></html>"));
                                else if (request.getUri().path().equals("/ping"))
                                    return HttpResponse.create().withEntity(ByteString.fromString("PONG!"));
                                else if (request.getUri().path().equals("/crash"))
                                    throw new RuntimeException("BOOM!");
                                else {
                                    request.discardEntityBytes(materializer);
                                    return HttpResponse.create().withStatus(StatusCodes.NOT_FOUND).withEntity("Unknown resource!");
                                }
                            }, ConnectHttp.toHost("localhost", 8080), materializer);

            System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
            System.in.read(); // let it run until user presses return

            serverBindingFuture
                    .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                    .thenAccept(unbound -> system.terminate()); // and shutdown when done

        } catch (RuntimeException e) {
            system.terminate();
        }
    }

}
