package edu.chalmers.vaporwave.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer {

    private Server server;
    private Client client;

    public GameServer() {

        try {
            this.server = new Server();
            server.start();

            server.bind(54556, 54778);


            server.addListener(new Listener() {
                public void received (Connection connection, Object object) {
                    if (object instanceof SomeRequest) {
                        SomeRequest request = (SomeRequest) object;
                        System.out.println(request.text);

                        SomeResponse response = new SomeResponse();
                        response.text = "And here's da response!";
                        connection.sendTCP(response);
                    }
                }
            });


            this.client = new Client();
            client.start();


            Kryo kryoServer = server.getKryo();
            kryoServer.register(SomeRequest.class);
            kryoServer.register(SomeResponse.class);

            Kryo kryoClient = client.getKryo();
            kryoClient.register(SomeRequest.class);
            kryoClient.register(SomeResponse.class);

            client.connect(5000, "129.16.178.67", 54555, 54777);



            client.addListener(new Listener() {
                public void received (Connection connection, Object object) {
                    if (object instanceof SomeResponse) {
                        SomeResponse response = (SomeResponse)object;
                        System.out.println(response.text);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        sendRequest("Hej");

    }

    public void sendRequest(String str) {

        System.out.println("getting req");

        if(client != null) {
            SomeRequest request = new SomeRequest();
            request.text = "Here is the request";
            client.sendTCP(request);
        }

    }
}
