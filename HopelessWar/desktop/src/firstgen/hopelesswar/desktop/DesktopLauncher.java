package firstgen.hopelesswar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import firstgen.hopelesswar.Game;
import firstgen.hopelesswar.util.DebuggerWrapper;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = 150;

		int port = 55555;

		new LwjglApplication(new Game(), config);

//		System.out.println("Before Process starts");

//		new Thread(() -> {
//			try {
//				JavaProcess.exec(DebuggerClient.class);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}).start();


//		System.out.println("Process started");

//		try {
//			System.out.println("Waiting for debugger...");
//			ServerSocket serverSocket = new ServerSocket(port);
//
//			new ConnectionHandler(serverSocket.accept()).run();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("Done");
	}

	private static class ConnectionHandler implements Runnable {
		private final Socket socket;

		public ConnectionHandler(Socket s) {
			socket = s;
		}

		public Object processInput(String input) {
			Object object = null;

			String[] tokens = input.split(":");

			String command = tokens[0];

			if( command.equals("UPDATE")) {
				object = new DebuggerWrapper();
			}

			return object;
		}


		@Override
		public void run() {
			InetAddress info = socket.getInetAddress();
			String name = info.getHostName() + "(" + info.getHostAddress() + ")";
			System.out.println("Connection from: " + name);

			ObjectOutputStream out = null;
			BufferedReader in = null;

			try {
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String inputLine;

				while((inputLine = in.readLine()) != null) {
					Object object = processInput(inputLine);

					if(inputLine.contains("LEAVE")) {
						break;
					}

					if(object != null ) out.writeUnshared(object);
					out.reset();
				}
				System.out.println("Closing... ");
			}
			catch( IOException e) {
				System.err.println("Problem with Communication Server: " + e.getMessage() );
			} finally {
				System.out.println("Cleaning up: " + socket);

				if(out != null)
					try {
						out.close();
					} catch (IOException e) {
						System.err.println("IOException when closing ObjectOutputStream: " + e.getMessage());
					}

				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						System.err.println("IOException when closing BufferedReader: " + e.getMessage() );
					}
				}

				try {
					socket.close();
				} catch (IOException e) {
					System.err.println("IOException when closing client socket: " + e.getMessage() );
				}
			}

		}
	}
}
