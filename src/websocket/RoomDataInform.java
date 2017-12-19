package websocket;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import model.Common;

@ServerEndpoint(value="/RoomDataInform",configurator=RoomDataInform.class)
public class RoomDataInform extends Configurator{
	
	@Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        sec.getUserProperties().put("pid", httpSession.getAttribute("pid"));
    }
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		int pid =(int)config.getUserProperties().get("pid");
        if(Common.getStatus(pid).status.equals("Gaming")) {
        	int index=Common.getRoomIndex(pid);
            Common.getRoom(pid).playerSessions[index]=session;
        }
        System.out.println("Session"+session.getId());
	}
	
	public static void sendMessage(int pid, String message) throws IOException {
		for(int i=0;i<=2;i++) {
			Common.getRoom(pid).playerSessions[i].getBasicRemote().sendText(message);
		}
	}
}
