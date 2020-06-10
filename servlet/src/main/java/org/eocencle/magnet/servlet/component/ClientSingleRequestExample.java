package org.eocencle.magnet.servlet.component;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;

import java.util.concurrent.CompletionStage;

/**
 * 客户端
 * @author: huan
 * @Date: 2020-06-06
 * @Description:
 */
public class ClientSingleRequestExample {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create();

        final CompletionStage<HttpResponse> responseFuture =
                Http.get(system)
                        .singleRequest(HttpRequest.create("https://akka.io"));
    }

}
