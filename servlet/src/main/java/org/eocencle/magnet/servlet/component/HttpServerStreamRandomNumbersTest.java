package org.eocencle.magnet.servlet.component;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.*;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import akka.util.ByteString;

import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * 流媒体的Controller
 * @author: huan
 * @Date: 2020-06-06
 * @Description:
 * 使用慢速的HTTP客户端连接到该服务将产生反压力，以便根据需要在服务器上保持不变的内存使用情况下生成下一个随机数。使用卷曲和限制速度可以看出这一点curl --limit-rate 50b 127.0.0.1:8080/random
 */
public class HttpServerStreamRandomNumbersTest extends AllDirectives {

    public static void main(String[] args) throws Exception {
        // boot up server using the route as defined below
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        //In order to access all directives we need an instance where the routes are define.
        HttpServerStreamRandomNumbersTest app = new HttpServerStreamRandomNumbersTest();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }


    private Route createRoute() {
        final Random rnd = new Random();
        // streams are re-usable so we can define it here
        // and use it for every request
        Source<Integer, NotUsed> numbers = Source.fromIterator(() -> Stream.generate(rnd::nextInt).iterator());

        return concat(
                path("random", () ->
                        get(() ->
                                complete(HttpEntities.create(ContentTypes.TEXT_PLAIN_UTF8,
                                        // transform each number to a chunk of bytes
                                        numbers.map(x -> ByteString.fromString(x + "\n")))))));
    }

}
