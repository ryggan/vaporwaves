package edu.chalmers.vaporwave.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetAddress;

public class GameServer {

    private Server server;
    private Client client;

    public boolean isServer = false;

    public GameServer() {

        try {

            if (isServer) {
                System.out.println("Server");
                this.server = new Server();
                server.start();

                server.bind(9999, 54777);


                server.addListener(new Listener() {
                    public void received(Connection connection, Object object) {
                        if (object instanceof SomeRequest) {
                            SomeRequest request = (SomeRequest) object;
                            System.out.println(request.text);

                            SomeResponse response = new SomeResponse();
                            response.text = "And here's da response! " + System.nanoTime() * 10000;
                            connection.sendTCP(response);
                        }
                    }
                });


                Kryo kryoServer = server.getKryo();
                kryoServer.register(SomeRequest.class);
                kryoServer.register(SomeResponse.class);

            } else {
                System.out.println("Client");
            }

            this.client = new Client();
            client.start();


            Kryo kryoClient = client.getKryo();
            kryoClient.register(SomeRequest.class);
            kryoClient.register(SomeResponse.class);

            client.connect(5000, "172.20.10.3", 9999, 54777);

            client.setTimeout(0);

            InetAddress address = client.discoverHost(9999, 5000);
            System.out.println(address);

            if(!isServer) {
                client.addListener(new Listener() {
                    public void received(Connection connection, Object object) {
                        if (object instanceof SomeResponse) {
                            SomeResponse response = (SomeResponse) object;
                            System.out.println(response.text);
                        }
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendRequest(String str) {

        System.out.println("getting req");

        if(client != null) {
            SomeRequest request = new SomeRequest();
            request.text = "Here is the request from Andreas " + System.nanoTime() * 10000;
            client.sendTCP(request);
        }

    }
}
